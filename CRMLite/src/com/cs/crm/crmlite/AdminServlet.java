package com.cs.crm.crmlite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DatabaseManager databaseManager;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		try {
			request.setAttribute("EXEC_DATA", getSalesExecs(request));
			request.setAttribute("COURSE_DATA", getCourses(request, "0"));
			request.setAttribute("LOCATION_DATA", getLocations(request, "0"));
			request.setAttribute("SUBLOCATION_DATA", getSublocations(request, null));
			request.getRequestDispatcher("admin.jsp").include(request, response);
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
	 * Get all courses (ids and names) for particular sales_exec(id) from database
	 */
	
	private ArrayList<String[]> getCourses(HttpServletRequest request, String salesExecId) throws Exception {
		
		String query = "SELECT cdb_course_id, cdb_course_name FROM crmlite_db.crmlite_db_course_mst ORDER BY cdb_course_name";
		if(salesExecId.equals("0"))
			query += ";";
		else
			query += "WHERE cdb_course_exec_id = '" + salesExecId + "';";
		return databaseManager.executeSelectQuery(query);
	}

	/*
	 * Get all location ids and names for particular sales_exec(id) from database
	 */
	
	private ArrayList<String[]> getLocations(HttpServletRequest request, String salesExecId) throws Exception {
		String query = "SELECT * FROM crmlite_db.crmlite_db_location_mst";
		if(salesExecId.equals("0"))
			query += ";";
		else
			query += "WHERE cdb_course_exec_id = '" + salesExecId + "';";
		return databaseManager.executeSelectQuery(query);
	}
	
	
	/*
	 * Get sublocations (ids and names) of a selected location from database
	 */
	
	private ArrayList<String[]> getSublocations(HttpServletRequest request, String locationName) throws Exception {
		if (locationName!=null) {
			String query = "SELECT cdb_sublocation_id, cdb_soblocation_name FROM crmlite_db.crmlite_db_sublocation_mst "
				+ "WHERE cdb_sublocation_location_id in ("
				+ "SELECT cdb_location_id FROM crmlite_db.crmlite_db_location_mst"
				+ "WHERE cdb_location_name = '" + locationName + "');";
			return databaseManager.executeSelectQuery(query);
		} else {
			String query = "SELECT cdb_sublocation_id, cdb_soblocation_name FROM crmlite_db.crmlite_db_sublocation_mst";
				return databaseManager.executeSelectQuery(query);
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] execId = request.getParameterValues("executive");
		
		String assignedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String removedDate = assignedDate;
		
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		try {
			for(String e: execId) {
				databaseManager.executeNonSelectDMLQuery(getUpdateQuery(e.split(",")[2], "ALL", e.split(",")[1], removedDate));
//				System.out.println(getInsertQuery(e.split(",")[0], e.split(",")[2], "ALL", e.split(",")[1], assignedDate));
				databaseManager.executeNonSelectDMLQuery(getInsertQuery(e.split(",")[0], e.split(",")[2], "ALL", e.split(",")[1], assignedDate));
				
			}
			response.sendRedirect("admin");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				((DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT")).closeConnection();
			} catch(Exception ex) {}
			request.getSession().invalidate();
			response.sendRedirect("user-login");
		}
	}
	
	private String getInsertQuery(String execId, String location, String sublocation, String course, String assignedDate) {
		return "INSERT INTO crmlite_db.crmlite_db_exec_ass ("
				+ "cdb_exec_ass_exec_id, "
				+ "cdb_exec_ass_loc, "
				+ "cdb_exec_ass_subloc, "
				+ "cdb_exec_ass_course, "
				+ "cdb_exec_ass_assigned_date"
				+ ") "
			+ "VALUES ('" + execId + "', '" + location + "', '" + sublocation + "', '" + course + "', '" + assignedDate + "');";
	}

	private String getUpdateQuery(String location, String sublocation, String course, String removedDate) {
		return "UPDATE crmlite_db.crmlite_db_exec_ass " +
				"SET cdb_exec_ass_removed_date = '" + removedDate + "' " +
				"WHERE " +
				"(cdb_exec_ass_removed_date IS NULL OR cdb_exec_ass_removed_date = ' ') AND " +
				"cdb_exec_ass_course = '"  + course      + "' AND " +
				"cdb_exec_ass_loc = '" 	   + location    + "' AND " +
				"cdb_exec_ass_subloc = '"  + sublocation + "';";
	}
}
