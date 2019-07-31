package com.cs.crm.crmlite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/lead-data")
public class LeadDataFetcher extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String LEADS_TO_FETCH_AT_A_TIME = "30";
	DatabaseManager databaseManager;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		try {
			// Setting location dropdown items
			if(request.getSession().getAttribute("USER_ROLE").equals("bdm") || request.getSession().getAttribute("USER_ROLE").equals("bde")) {
				//request.setAttribute("LOCATION_DATA", getLocations(request, (String)request.getSession().getAttribute("USER_ID")));
				request.setAttribute("LOCATION_DATA", getLocations(request));
			} else {
				request.setAttribute("LOCATION_DATA", getLocations(request));
				request.setAttribute("EXEC_DATA", getSalesExecs(request));
			}
			
			// Setting sublocation dropdown items
			String selectedLocation = request.getParameter("SELECTED_LOCATION");
			if(selectedLocation==null || selectedLocation.equals(null) || selectedLocation.trim().equals("")) {
				request.setAttribute("SUBLOCATION_DATA", getSublocations(request, "Bangalore"));
			} else {
				if(request.getSession().getAttribute("USER_ROLE").equals("admin") || request.getSession().getAttribute("USER_ROLE").equals("l-x"))
					request.setAttribute("SUBLOCATION_DATA", getSublocations(request, selectedLocation));
				else
					request.setAttribute("SUBLOCATION_DATA", getSublocations(request, selectedLocation, (String) request.getSession().getAttribute("USER_ID")));
			}
			
			// Setting course dropdown items
			if(request.getSession().getAttribute("USER_ROLE").equals("bdm") || request.getSession().getAttribute("USER_ROLE").equals("bde")) {
				String date = request.getParameter("date");
				if (date == null)
					date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//				request.setAttribute("COURSE_DATA", getCourses(request, date, (String)request.getSession().getAttribute("USER_ID")));
				request.setAttribute("COURSE_DATA", getCourses(request));
			}
			else
				request.setAttribute("COURSE_DATA", getCourses(request));
			
			// Setting lead data items
			request.setAttribute("LEAD_DATA", getLeads(request, "", "", "", "", "", "ALL", LEADS_TO_FETCH_AT_A_TIME, ""));
			
			// Throwing the leads page
			request.getRequestDispatcher("leads.jsp").include(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				((DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT")).closeConnection();
			} catch(Exception ex) {}
			request.getSession().invalidate();
			response.sendRedirect("user-login");
		}
	}


	/*
	 * Get all sales execs' ids and names from database
	 */
	
	
	private ArrayList<String[]> getSalesExecs(HttpServletRequest request) throws Exception {
		String query = "SELECT * FROM crmlite_db.crmlite_db_users WHERE cdb_users_role in (\"admin\", \"bde\", \"bdm\");";
		return databaseManager.executeSelectQuery(query);
	}
	
	/*
	 * Get all location ids and names for particular sales_exec(id) from database
	 */
	
	private ArrayList<String[]> getLocations(HttpServletRequest request, String salesExecId) throws Exception {
		String query = "";
		
		if(salesExecId.equals("0"))
			query = "SELECT * FROM crmlite_db.crmlite_db_location_mst;";
		else
			query = "SELECT DISTINCT\r\n" + 
					"crmlite_db.crmlite_db_location_mst.cdb_location_id, \r\n" + 
					"crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_loc \r\n" + 
					"FROM crmlite_db.crmlite_db_location_mst RIGHT OUTER JOIN  crmlite_db.crmlite_db_exec_ass\r\n" + 
					"ON crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_loc = crmlite_db.crmlite_db_location_mst.cdb_location_name\r\n" + 
					"WHERE crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_exec_id = '" + salesExecId + "'";
		return databaseManager.executeSelectQuery(query);
	}
	
	/*
	 * Get all location ids and names for particular sales_exec(id) from database
	 */
	
	private ArrayList<String[]> getLocations(HttpServletRequest request) throws Exception {
		String query = "";
		
		query = "SELECT * FROM crmlite_db.crmlite_db_location_mst;";
		return databaseManager.executeSelectQuery(query);
	}
	
	
	/*
	 * Get sublocations (ids and names) of a selected location from database
	 */
	
	private ArrayList<String[]> getSublocations(HttpServletRequest request, String locationName) throws Exception {
		String query = "SELECT cdb_sublocation_id, cdb_soblocation_name FROM crmlite_db.crmlite_db_sublocation_mst "
				+ "WHERE cdb_sublocation_location_id in ("
				+ "SELECT cdb_location_id FROM crmlite_db.crmlite_db_location_mst "
				+ "WHERE cdb_location_name = '" + locationName + "');";
//		System.out.println(query);
		return databaseManager.executeSelectQuery(query);
	}
	

	/*
	 * Get sublocations (ids and names) of a selected location, assigned to a particular executive from database
	 */
	
	private ArrayList<String[]> getSublocations(HttpServletRequest request, String locationName, String execId) throws Exception {
		String query = "SELECT cdb_sublocation_id, cdb_soblocation_name FROM crmlite_db.crmlite_db_sublocation_mst \r\n" + 
				"WHERE cdb_soblocation_name in (\r\n" + 
				"SELECT cdb_exec_ass_subloc FROM crmlite_db_exec_ass\r\n" + 
				"WHERE cdb_exec_ass_exec_id = '" + execId + "'\r\n" + 
				"AND cdb_exec_ass_loc = '" + locationName + "'\r\n" + 
				");";
//		System.out.println(query);
		return databaseManager.executeSelectQuery(query);
	}
	
	
	/*
	 * Get all courses (ids and names) for particular sales_exec(id) from database
	 */
	
	private ArrayList<String[]> getCourses(HttpServletRequest request, String date, String salesExecId) throws Exception {
		
		String query = "";
		if(salesExecId.equals("0"))
			query = "SELECT cdb_course_id, cdb_course_name FROM crmlite_db.crmlite_db_course_mst;";
		else
			query = "\r\n" + 
					"SELECT DISTINCT  \r\n" + 
					"crmlite_db.crmlite_db_course_mst.cdb_course_id,   \r\n" + 
					"eatable.cdb_exec_ass_course   \r\n" + 
					"FROM crmlite_db.crmlite_db_course_mst RIGHT OUTER JOIN\r\n" + 
					"(\r\n" + 
					"  SELECT * FROM crmlite_db.crmlite_db_exec_ass \r\n" + 
//					"  WHERE cdb_exec_ass_assigned_date <= '" + date + "' AND (cdb_exec_ass_removed_date IS NULL OR cdb_exec_ass_removed_date >='" + date + "')\r\n" + 
					") AS eatable\r\n" + 
					"ON eatable.cdb_exec_ass_course = crmlite_db.crmlite_db_course_mst.cdb_course_name  \r\n" + 
					"WHERE eatable.cdb_exec_ass_exec_id = '" + salesExecId + "'\r\n" + 
					"ORDER BY eatable.cdb_exec_ass_course;";
		return databaseManager.executeSelectQuery(query);
	}
	

	/*
	 * Get all courses (ids and names) from database
	 */
	
	private ArrayList<String[]> getCourses(HttpServletRequest request) throws Exception {
		
		String query = "";
		query = "SELECT distinct cdb_course_id, cdb_course_name FROM crmlite_db.crmlite_db_course_mst " + 
				"ORDER BY cdb_course_name;";
//		System.out.println(query);
		return databaseManager.executeSelectQuery(query);
	}
	
	
	/*
	 * Get all leads (ids and names) for particular location-sublocation from database
	 */
	
	private ArrayList<String[]> getLeads(HttpServletRequest request, String date, String dateTo, String course, String location, String sublocation, String status, String totalLeadsToFetchAtOnce, String offset) throws Exception {
		
		String query = "select " + 
				"leads.cdb_leads_id, " +  
				"users.cdb_users_full_name as 'Executive assigned', " +
				"leads.cdb_leads_add_date as 'Assigned date', " + 
				"leads.cdb_leads_name as 'Lead name', " + 
				"leads.cdb_leads_mobile as 'Mobile', " + 
				"leads.cdb_leads_email as 'Email', " + 
				"leads.cdb_leads_course as 'Course', " + 
				"leads.cdb_leads_status as 'Status', " + 
				"leads.cdb_leads_mode_of_training as 'Mode of Training', " + 
				"leads.cdb_leads_proposed_fees as 'Proposed Fees', " + 
				"leads.cdb_leads_demo_date as 'Scheduled demo date', " + 
				"leads.cdb_leads_demo_time as 'Scheduled demo time', " + 
				"leads.cdb_leads_followup_date as 'Followup date', " + 
				"leads.cdb_leads_followup_time as 'Followup time', " + 
				"leads.cdb_leads_quoted_fees as 'Fees closed at', " + 
				"leads.cdb_leads_advance_fees as 'Fees paid', " + 
				"leads.cdb_leads_advance_paid_date as 'Fees paid date', " + 
				"leads.cdb_leads_installment_1 as 'Installment 1 amount', " + 
				"leads.cdb_leads_due_date_1 as 'Installment 1 date', " + 
				"leads.cdb_leads_installment_2 as 'Installment 2 amount', " + 
				"leads.cdb_leads_due_date_2 as 'Installment 2 date', " + 
				"leads.cdb_leads_installment_3 as 'Installment 3 amount', " + 
				"leads.cdb_leads_due_date_3 as 'Installment 3 date', " + 
				"leads.cdb_leads_installment_4 as 'Installment 4 amount', " + 
				"leads.cdb_leads_due_date_4 as 'Installment 4 date', " + 
				"leads.cdb_leads_trainer as 'Assigned trainer', " + 
				"leads.cdb_leads_expected_closure_date as 'Expected date of closure', " + 
				"leads.cdb_leads_location as 'Location', " + 
				"leads.cdb_leads_sublocation as 'Sublocation', " + 
				"leads.cdb_leads_lost_reason as 'Reason for customer lost', " + 
				"leads.cdb_leads_lost_at_price as 'Lost at price', " + 
				"leads.cdb_leads_lost_to_institute as 'Lost to institute', " + 
				"leads.cdb_leads_remarks as 'Remarks', " + 
				"leads.cdb_leads_last_updated_date as 'Last updated on' " +
				"from " +
				"crmlite_db.crmlite_db_leads as leads " +
				"left outer join " +
				"crmlite_db.crmlite_db_users as users " +
				"on leads.cdb_leads_executive = users.cdb_users_id ";
		if(date.equals("") || location.equals("") || sublocation.equals("") || course.equals("") || status.equals(""))
			query += "";
		
		// the date part
		String datePartOfQuery = "cdb_leads_add_date between '" + date + "' and '" + dateTo + "'";
		
		// the courses part
		String[] courses = course.split("_");
//		System.out.println(course + " " + courses.length);
		String coursePartOfQuery = "";
		coursePartOfQuery += "cdb_leads_course IN (";
		for(String c:courses) {
			coursePartOfQuery += "'" + c + "', ";
		}
		coursePartOfQuery = coursePartOfQuery.substring(0, coursePartOfQuery.length() - 2);
		coursePartOfQuery += ")";
	
		// the location part
		String[] locations = location.split("_");
		String locationPartOfQuery = "";
		locationPartOfQuery += "cdb_leads_location IN (";
		for(String l:locations) {
			locationPartOfQuery += "'" + l + "', ";
		}
		locationPartOfQuery = locationPartOfQuery.substring(0, locationPartOfQuery.length() - 2);
		locationPartOfQuery += ")";
	
//		// the sublocation part (considering the '_' format)
//		String[] sublocations = sublocation.split("_");
//		String sublocationPartOfQuery = "";
//		sublocationPartOfQuery += "cdb_leads_sublocation IN (";
//		for(String s:sublocations) {
//			sublocationPartOfQuery += "'" + s + "', ";
//		}
//		sublocationPartOfQuery = sublocationPartOfQuery.substring(0, sublocationPartOfQuery.length() - 2);
//		sublocationPartOfQuery += ")";
		
		// the sublocation part (NOT considering the '_' format)
		String sublocationPartOfQuery = "";
		if(!sublocation.equals("ALL"))
			sublocationPartOfQuery += "cdb_leads_sublocation ='" + sublocation + "'";
		
		String statusPartOfQuery = "";
		if(!status.equals("ALL"))
			statusPartOfQuery += "cdb_leads_status ='" + status + "'";
		
		if(!coursePartOfQuery.trim().equals(""))
			query += " WHERE " + coursePartOfQuery.trim() + " AND ";

		if(!locationPartOfQuery.trim().equals(""))
			if(query.contains("WHERE"))
				query += " " + locationPartOfQuery.trim() + " AND ";
			else
				query += " WHERE " + locationPartOfQuery.trim() + " AND ";

		if(!sublocationPartOfQuery.trim().equals(""))
			if(query.contains("WHERE"))
				query += " " + sublocationPartOfQuery.trim() + " AND ";
			else
				query += " WHERE " + sublocationPartOfQuery.trim() + " AND ";

		if(!statusPartOfQuery.trim().equals(""))
			if(query.contains("WHERE"))
				query += " " + statusPartOfQuery.trim() + " AND ";
			else
				query += " WHERE " + statusPartOfQuery.trim() + " AND ";
		

		if(request.getSession().getAttribute("USER_ROLE").equals("admin") || request.getSession().getAttribute("USER_ROLE").equals("l-x")) {
			if(query.contains("WHERE"))
				query += " " + datePartOfQuery;
			else
				query += " WHERE " + datePartOfQuery;
		} else {
		
			if(query.contains("WHERE"))
				query += " " + datePartOfQuery + " AND ";
			else
				query += " WHERE " + datePartOfQuery + " AND ";
			
			if(query.contains("WHERE"))
				query += " cdb_leads_executive = '" + (String)request.getSession().getAttribute("USER_ID") + "'";
			else
				query += " WHERE cdb_leads_executive = '" + (String)request.getSession().getAttribute("USER_ID") + "'";
		}
	
		query += " LIMIT " + totalLeadsToFetchAtOnce;
		
		if(!offset.equals(""))
			query += " OFFSET " + offset;
		
		query += ";";
		
		System.out.println(query);
			
		return databaseManager.executeSelectQueryWithMetaData(query);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String date = request.getParameter("date");
		String dateTo = request.getParameter("dateTo");
		String course = request.getParameter("courses");
		String location = request.getParameter("location");
		String sublocation = request.getParameter("sublocation");
		String status = request.getParameter("status");
		String noOfClicks = request.getParameter("noOfClicks");
		
		String pk = request.getParameter("pk");
		
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		
		try {
			if (request.getSession().getAttribute("USER_ROLE").equals("bde") || request.getSession().getAttribute("USER_ROLE").equals("bdm")) {
				ArrayList<String[]> courses = getCourses(request, date, (String)request.getSession().getAttribute("USER_ID"));
				
				boolean doesCourseExist = false;
				for(String[] s : courses) {
					for(String ss: s) {
//						System.out.println(ss);
						if(course.contains(ss)) {
							doesCourseExist = true;
							break;
						}
					}
					if(doesCourseExist)
						break;
				}
				
//				System.out.println("course exists: " + doesCourseExist);
				
				if (!doesCourseExist)
					doGet(request, response);
			}
		} catch (Exception e) {}
		
		if(date != null && !date.equals(null) &&
				course != null && !course.equals(null) && 
				location != null && !location.equals(null) &&
				sublocation != null && !sublocation.equals(null)) {
			try {
				ArrayList<String[]> leads = null;
				if (request.getParameter("filterchanged").equals("false"))
					leads = getLeads(request, date, dateTo, course, location, sublocation, status, LEADS_TO_FETCH_AT_A_TIME, (Integer.parseInt(LEADS_TO_FETCH_AT_A_TIME)*Integer.parseInt(noOfClicks))+"");
				else
					leads = getLeads(request, date, dateTo, course, location, sublocation, status, LEADS_TO_FETCH_AT_A_TIME, "");
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				for(int i = 0; i < leads.size(); i++) {
					jsonArray.put(Arrays.asList(leads.get(i)));
				}
				jsonObject.put("jsonLeads", jsonArray);
				response.getWriter().write(jsonObject.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(pk != null && !pk.equals(null)) {
			try {
				String query = "SELECT * FROM crmlite_db.crmlite_db_leads "
						+ "WHERE cdb_leads_id = '" + pk + "';";
				ArrayList<String[]> leads = databaseManager.executeSelectQuery(query);
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				for(int i = 0; i < leads.size(); i++) {
					jsonArray.put(Arrays.asList(leads.get(i)));
				}
				jsonObject.put("formViewLeads", jsonArray);
				response.getWriter().write(jsonObject.toString());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
