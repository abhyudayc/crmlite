package com.cs.crm.crmlite;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add-lead")
public class AddLead extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String leadExtractorIds = "5";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("GET of Add Lead");
			insertLeadData(request);
			response.sendRedirect("lead-data");
		} catch (Exception e) {
			e.printStackTrace();
			((DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT")).closeConnection();
			request.getSession().invalidate();
			response.sendRedirect("user-login");
		}
	}
	
	private void updateLeadData(HttpServletRequest request) throws Exception {
		String updateQuery = buildLeadUpdateQuery(request);
		String insertStatusTrackQuery = buildInsertStatusTrackQuery(request);
		DatabaseManager databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		databaseManager.executeNonSelectDMLQuery(updateQuery);
		databaseManager.executeNonSelectDMLQuery(insertStatusTrackQuery);
		if(needToFollowUp(request)) {
			addFollowUpReminder(request, databaseManager, request.getParameter("lead-name"), request.getParameter("lead-followup-date"), request.getParameter("lead-followup-time"), (String) request.getSession().getAttribute("USER_ID"));
		}
		if(haveADemo(request)) {
			addDemoReminder(request, databaseManager, request.getParameter("lead-name"), request.getParameter("lead-demo-date"), request.getParameter("lead-demo-time"), (String) request.getSession().getAttribute("USER_ID"));
		}
	}
	
	private boolean needToFollowUp(HttpServletRequest request) {
		try {
			if(request.getParameter("lead-followup-date") != null && !request.getParameter("lead-followup-date").equals(null) && !request.getParameter("lead-followup-date").equals("")) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				String date1 = dateFormat.format(date);
				String date2 = request.getParameter("lead-followup-date");
				return Integer.parseInt(date2.replaceAll("-", ""))>=Integer.parseInt(date1.replaceAll("-", ""));
			}
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean haveADemo(HttpServletRequest request) {
		try {
			if(request.getParameter("lead-demo-date") != null && !request.getParameter("lead-demo-date").equals(null) && !request.getParameter("lead-demo-date").equals("")) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				String date1 = dateFormat.format(date);
				String date2 = request.getParameter("lead-demo-date");
				return Integer.parseInt(date2.replaceAll("-", ""))>=Integer.parseInt(date1.replaceAll("-", ""));
			}
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void addFollowUpReminder(HttpServletRequest request, DatabaseManager databaseManager, String userToFollowUpWith, String date, String time, String userId) throws Exception {
		String query1 = "SELECT count(*) FROM crmlite_db.crmlite_db_rem_mst WHERE "
				+ "cdb_rem_date = '" + date + "' AND "
				+ "cdb_rem_time = '" + time + "' AND "
				+ "cdb_rem_msg = 'Followup with " + userToFollowUpWith + "' AND "
				+ "cdb_rem_show_to = '" + userId + "';";
//		System.out.println(query1);
		if(databaseManager.executeSelectQuery(query1).get(0)[0].equals("0")) {
			String query = "INSERT INTO crmlite_db.crmlite_db_rem_mst"
					+ " (cdb_rem_date, cdb_rem_time, cdb_rem_msg, cdb_rem_show_to)"
					+ " values ('" 
					+ date + "', '"
					+ time + "', '"
					+ "Followup with " + userToFollowUpWith + "', '"
					+ userId + "');";
//			System.out.println(query);
			databaseManager.executeNonSelectDMLQuery(query);
		}
	}
	
	private void addDemoReminder(HttpServletRequest request, DatabaseManager databaseManager, String leadThatHasADemo, String date, String time, String userId) throws Exception {
		String query1 = "SELECT count(*) FROM crmlite_db.crmlite_db_rem_mst WHERE "
				+ "cdb_rem_date = '" + date + "' AND "
				+ "cdb_rem_time = '" + time + "' AND "
				+ "cdb_rem_msg = 'Demo scheduled for " + leadThatHasADemo + "' AND "
				+ "cdb_rem_show_to = '" + userId + "';";
//		System.out.println(query1);
		if(databaseManager.executeSelectQuery(query1).get(0)[0].equals("0")) {
			String query = "INSERT INTO crmlite_db.crmlite_db_rem_mst"
					+ " (cdb_rem_date, cdb_rem_time, cdb_rem_msg, cdb_rem_show_to)"
					+ " values ('" 
					+ date + "', '"
					+ time + "', '"
					+ "Demo scheduled for " + leadThatHasADemo + "', '"
					+ userId + "');";
//			System.out.println(query);
			databaseManager.executeNonSelectDMLQuery(query);
		}
	}
	
	private String buildInsertStatusTrackQuery(HttpServletRequest request) {
		String SQL = "INSERT INTO crmlite_db.crmlite_db_leads_status_track ("
				+ "cdb_leads_status_track_lead_id, "
				+ "cdb_leads_status_track_status, "
				+ "cdb_leads_status_track_exec_id, "
				+ "cdb_leads_status_track_date, "
				+ "cdb_leads_status_track_time"
				+ ") VALUES ("
				+ " '" + request.getParameter("pk-of-record")	+ "', "
				+ " '" + request.getParameter("lead-status") 	+ "', "
				+ " '" + request.getSession().getAttribute("USER_ID")	+ "', "
				+ " '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())	+ "', "
				+ " '" + new SimpleDateFormat("HH:mm:ss").format(new Date())	+ "' "
				+");";
		
		System.out.println(SQL);
		return SQL;
	}
	
	private String buildLeadUpdateQuery(HttpServletRequest request) {
		String SQL;
		
		String instlPaid_1, instlPaid_2, instlPaid_3, instlPaid_4;
		try {
			instlPaid_1 = request.getParameter("lead-instl-paid1");
			if ("null" == instlPaid_1 || null == instlPaid_1)
				throw new Exception();
		} catch(Exception e) {instlPaid_1 = "N";}
		try {
			instlPaid_2 = request.getParameter("lead-instl-paid2");
			if ("null" == instlPaid_2 || null == instlPaid_2)
				throw new Exception();
		} catch(Exception e) {instlPaid_2 = "N";}
		try {
			instlPaid_3 = request.getParameter("lead-instl-paid3");
			if ("null" == instlPaid_3 || null == instlPaid_3)
				throw new Exception();
		} catch(Exception e) {instlPaid_3 = "N";}
		try {
			instlPaid_4 = request.getParameter("lead-instl-paid4");
			if ("null" == instlPaid_4 || null == instlPaid_4)
				throw new Exception();
		} catch(Exception e) {instlPaid_4 = "N";}
		
		if(request.getSession().getAttribute("USER_ROLE").equals("bde") || request.getSession().getAttribute("USER_ROLE").equals("bdm"))
			SQL = "UPDATE crmlite_db.crmlite_db_leads SET "
					+ "cdb_leads_name"					+ " = '" + request.getParameter("lead-name")					+ "', "
					+ "cdb_leads_mobile"				+ " = '" + request.getParameter("lead-mobile") 					+ "', "
					+ "cdb_leads_email"					+ " = '" + request.getParameter("lead-email") 					+ "', "
					+ "cdb_leads_mode_of_training"		+ " = '" + request.getParameter("lead-training")				+ "', "
					+ "cdb_leads_status" 				+ " = '" + request.getParameter("lead-status") 					+ "', "
					+ "cdb_leads_course"				+ " = '" + request.getParameter("lead-course") 					+ "', "
					+ "cdb_leads_proposed_fees"			+ " = '" + request.getParameter("lead-proposed-fees") 			+ "', "
					+ "cdb_leads_demo_date"				+ " = '" + request.getParameter("lead-demo-date") 				+ "', "
					+ "cdb_leads_demo_time"				+ " = '" + request.getParameter("lead-demo-time") 				+ "', "
					+ "cdb_leads_followup_date"			+ " = '" + request.getParameter("lead-followup-date") 			+ "', "
					+ "cdb_leads_followup_time"			+ " = '" + request.getParameter("lead-followup-time") 			+ "', "
					+ "cdb_leads_quoted_fees"			+ " = '" + request.getParameter("lead-quoted-fees") 			+ "', "
					+ "cdb_leads_advance_fees"			+ " = '" + request.getParameter("lead-advance-fees") 			+ "', "
					+ "cdb_leads_advance_paid_date"		+ " = '" + request.getParameter("lead-advance-paid-date")		+ "', "
					+ "cdb_leads_installment_1"			+ " = '" + request.getParameter("lead-install-amt1") 			+ "', "
					+ "cdb_leads_due_date_1"			+ " = '" + request.getParameter("lead-due-date1") 				+ "', "
					+ "cdb_leads_paid_1"				+ " = '" + instlPaid_1							 				+ "', "
					+ "cdb_leads_installment_2"			+ " = '" + request.getParameter("lead-install-amt2") 			+ "', "
					+ "cdb_leads_due_date_2"			+ " = '" + request.getParameter("lead-due-date2") 				+ "', "
					+ "cdb_leads_paid_2"				+ " = '" + instlPaid_2							 				+ "', "
					+ "cdb_leads_installment_3"			+ " = '" + request.getParameter("lead-install-amt3") 			+ "', "
					+ "cdb_leads_due_date_3"			+ " = '" + request.getParameter("lead-due-date3") 				+ "', "
					+ "cdb_leads_paid_3"				+ " = '" + instlPaid_3	 										+ "', "
					+ "cdb_leads_installment_4"			+ " = '" + request.getParameter("lead-install-amt4") 			+ "', "
					+ "cdb_leads_due_date_4"			+ " = '" + request.getParameter("lead-due-date4") 				+ "', "
					+ "cdb_leads_paid_4"				+ " = '" + instlPaid_4											+ "', "
					+ "cdb_leads_trainer"				+ " = '" + request.getParameter("lead-trainer") 				+ "', "
					+ "cdb_leads_lost_reason"			+ " = '" + request.getParameter("lead-lost-reason") 			+ "', "
					+ "cdb_leads_executive"				+ " = '" + request.getSession().getAttribute("USER_ID") 		+ "', "
					+ "cdb_leads_location"				+ " = '" + request.getParameter("lead-location") 				+ "', "
					+ "cdb_leads_sublocation"			+ " = '" + request.getParameter("lead-sublocation") 			+ "', "
					+ "cdb_leads_expected_closure_date"	+ " = '" + request.getParameter("lead-expected-closure-date") 	+ "', "
					+ "cdb_leads_lost_at_price"			+ " = '" + request.getParameter("lead-lost-price") 				+ "', "
					+ "cdb_leads_lost_to_institute"		+ " = '" + request.getParameter("lead-lost-institute") 			+ "', "
					+ "cdb_leads_remarks"				+ " = '" + request.getParameter("lead-remarks") 				+ "', "
					+ "cdb_leads_last_updated_date = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 		+ "'"
					+ " WHERE "
					+ "cdb_leads_id"					+ " = '" + request.getParameter("pk-of-record") 				+ "'"
					+";";
		else
			SQL = "UPDATE crmlite_db.crmlite_db_leads SET "
					+ "cdb_leads_name"					+ " = '" + request.getParameter("lead-name")					+ "', "
					+ "cdb_leads_mobile"				+ " = '" + request.getParameter("lead-mobile") 					+ "', "
					+ "cdb_leads_email"					+ " = '" + request.getParameter("lead-email") 					+ "', "
					+ "cdb_leads_mode_of_training"		+ " = '" + request.getParameter("lead-training")				+ "', "
					+ "cdb_leads_status" 				+ " = '" + request.getParameter("lead-status") 					+ "', "
					+ "cdb_leads_course"				+ " = '" + request.getParameter("lead-course") 					+ "', "
					+ "cdb_leads_proposed_fees"			+ " = '" + request.getParameter("lead-proposed-fees") 			+ "', "
					+ "cdb_leads_demo_date"				+ " = '" + request.getParameter("lead-demo-date") 				+ "', "
					+ "cdb_leads_demo_time"				+ " = '" + request.getParameter("lead-demo-time") 				+ "', "
					+ "cdb_leads_followup_date"			+ " = '" + request.getParameter("lead-followup-date") 			+ "', "
					+ "cdb_leads_followup_time"			+ " = '" + request.getParameter("lead-followup-time") 			+ "', "
					+ "cdb_leads_quoted_fees"			+ " = '" + request.getParameter("lead-quoted-fees") 			+ "', "
					+ "cdb_leads_advance_fees"			+ " = '" + request.getParameter("lead-advance-fees") 			+ "', "
					+ "cdb_leads_advance_paid_date"		+ " = '" + request.getParameter("lead-advance-paid-date")		+ "', "
					+ "cdb_leads_installment_1"			+ " = '" + request.getParameter("lead-install-amt1") 			+ "', "
					+ "cdb_leads_due_date_1"			+ " = '" + request.getParameter("lead-due-date1") 				+ "', "
					+ "cdb_leads_paid_1"				+ " = '" + instlPaid_1							 				+ "', "
					+ "cdb_leads_installment_2"			+ " = '" + request.getParameter("lead-install-amt2") 			+ "', "
					+ "cdb_leads_due_date_2"			+ " = '" + request.getParameter("lead-due-date2") 				+ "', "
					+ "cdb_leads_paid_2"				+ " = '" + instlPaid_2							 				+ "', "
					+ "cdb_leads_installment_3"			+ " = '" + request.getParameter("lead-install-amt3") 			+ "', "
					+ "cdb_leads_due_date_3"			+ " = '" + request.getParameter("lead-due-date3") 				+ "', "
					+ "cdb_leads_paid_3"				+ " = '" + instlPaid_3	 										+ "', "
					+ "cdb_leads_installment_4"			+ " = '" + request.getParameter("lead-install-amt4") 			+ "', "
					+ "cdb_leads_due_date_4"			+ " = '" + request.getParameter("lead-due-date4") 				+ "', "
					+ "cdb_leads_paid_4"				+ " = '" + instlPaid_4											+ "', "
					+ "cdb_leads_trainer"				+ " = '" + request.getParameter("lead-trainer") 				+ "', "
					+ "cdb_leads_lost_reason"			+ " = '" + request.getParameter("lead-lost-reason") 			+ "', "
					+ "cdb_leads_location"				+ " = '" + request.getParameter("lead-location") 				+ "', "
					+ "cdb_leads_sublocation"			+ " = '" + request.getParameter("lead-sublocation") 			+ "', "
					+ "cdb_leads_expected_closure_date"	+ " = '" + request.getParameter("lead-expected-closure-date") 	+ "', "
					+ "cdb_leads_lost_at_price"			+ " = '" + request.getParameter("lead-lost-price") 				+ "', "
					+ "cdb_leads_lost_to_institute"		+ " = '" + request.getParameter("lead-lost-institute") 			+ "', "
					+ "cdb_leads_remarks"				+ " = '" + request.getParameter("lead-remarks") 				+ "', "
					+ "cdb_leads_last_updated_date = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 		+ "'"
					+ " WHERE "
					+ "cdb_leads_id"					+ " = '" + request.getParameter("pk-of-record") 				+ "'"
					+";";
		
		
				
		System.out.println(SQL);
		return SQL;
	}
	
	private void insertLeadData(HttpServletRequest request) throws Exception {
		DatabaseManager databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		String executiveId = findAssignedExecutive(databaseManager, request.getParameter("lead-add-date"), request.getParameter("lead-course"), request.getParameter("lead-location"), request.getParameter("lead-sublocation"), leadExtractorIds);
		String insertQuery = buildLeadInsertQuery(request, executiveId);
		databaseManager.executeNonSelectDMLQuery(insertQuery);
		if(needToFollowUp(request)) {
			addFollowUpReminder(request, databaseManager, request.getParameter("lead-name"), request.getParameter("lead-followup-date"), "12:00", (String) request.getSession().getAttribute("USER_ID"));
		}
		if(haveADemo(request)) {
			addDemoReminder(request, databaseManager, request.getParameter("lead-name"), request.getParameter("lead-demo-date"), request.getParameter("lead-demo-time"), (String)(request.getSession().getAttribute("USER_ID")));
		}
	}
	
	private String findAssignedExecutive(DatabaseManager databaseManager, String leadAddDate, String course, String location, String sublocation, String leadExtractorIds) throws Exception {
		String query="";
		if(sublocation.equals("ALL"))
			query = "SELECT DISTINCT cdb_exec_ass_exec_id FROM "
				+ "crmlite_db.crmlite_db_exec_ass WHERE "
				+ "cdb_exec_ass_course = '" + course + "' AND "
				+ "cdb_exec_ass_loc = '" + location + "' AND "
				+ "cdb_exec_ass_assigned_date <= '" + leadAddDate + "' AND "
				+ "cdb_exec_ass_removed_date IS NULL AND "
				+ "cdb_exec_ass_exec_id NOT IN ('" + leadExtractorIds + "');";
		else
			query = "SELECT DISTINCT cdb_exec_ass_exec_id FROM "
					+ "crmlite_db.crmlite_db_exec_ass WHERE "
					+ "cdb_exec_ass_course = '" + course + "' AND "
					+ "cdb_exec_ass_loc = '" + location + "' AND "
					+ "cdb_exec_ass_subloc = '" + sublocation + "' AND "
					+ "cdb_exec_ass_assigned_date <= '" + leadAddDate + "' AND "
					+ "cdb_exec_ass_removed_date IS NULL AND "
					+ "cdb_exec_ass_exec_id NOT IN ('" + leadExtractorIds + "');";
//		System.out.println(query);
		try {
			String execId = databaseManager.executeSelectQuery(query).get(0)[0];
			if(execId.equals("") || execId == null || execId.equals(null))
				return "0";
			else return execId;
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	private String buildLeadInsertQuery(HttpServletRequest request, String executiveId) {
		String hotLeadFlag = "";
		if(request.getParameter("hot-lead")==null)
			hotLeadFlag = "0";
		else
			hotLeadFlag = "1";
		String SQL = "INSERT INTO crmlite_db.crmlite_db_leads ("
				+ "cdb_leads_name, "
				+ "cdb_leads_mobile, "
				+ "cdb_leads_email, "
				+ "cdb_leads_mode_of_training, "
				+ "cdb_leads_status, "
				+ "cdb_leads_course, "
				+ "cdb_leads_proposed_fees, "
				+ "cdb_leads_demo_date, "
				+ "cdb_leads_demo_time, "
				+ "cdb_leads_followup_date, "
				+ "cdb_leads_followup_time, "
				+ "cdb_leads_quoted_fees, "
				+ "cdb_leads_advance_fees, "
				+ "cdb_leads_advance_paid_date, "
				+ "cdb_leads_installment_1, "
				+ "cdb_leads_due_date_1, "
				+ "cdb_leads_installment_2, "
				+ "cdb_leads_due_date_2, "
				+ "cdb_leads_trainer, "
				+ "cdb_leads_lost_reason, "
				+ "cdb_leads_executive, "
				+ "cdb_leads_location, "
				+ "cdb_leads_sublocation, "
				+ "cdb_leads_expected_closure_date, "
				+ "cdb_leads_lost_at_price, "
				+ "cdb_leads_lost_to_institute, "
				+ "cdb_leads_add_date, "
				+ "cdb_leads_remarks, "
				+ "cdb_leads_last_updated_date, "
				+ "cdb_leads_hot_lead_flag, "
				+ "cdb_leads_add_time, "
				+ "cdb_leads_added_by"
				+ ") VALUES ("
				+ "'" + request.getParameter("lead-name") + "', "
				+ "'" + request.getParameter("lead-mobile") + "', "
				+ "'" + request.getParameter("lead-email") + "', "
				+ "'" + request.getParameter("lead-training") + "', "
				+ "'" + request.getParameter("lead-status") + "', "
				+ "'" + request.getParameter("lead-course") + "', "
				+ "'" + request.getParameter("lead-proposed-fees") + "', "
				+ "'" + request.getParameter("lead-demo-date") + "', "
				+ "'" + request.getParameter("lead-demo-time") + "', "
				+ "'" + request.getParameter("lead-followup-date") + "', "
				+ "'" + request.getParameter("lead-followup-time") + "', "
				+ "'" + request.getParameter("lead-quoted-fees") + "', "
				+ "'" + request.getParameter("lead-advance-fees") + "', "
				+ "'" + request.getParameter("lead-advance-paid-date") + "', "
				+ "'" + request.getParameter("lead-install-amt1") + "', "
				+ "'" + request.getParameter("lead-due-date1") + "', "
				+ "'" + request.getParameter("lead-install-amt2") + "', "
				+ "'" + request.getParameter("lead-due-date2") + "', "
				+ "'" + request.getParameter("lead-trainer") + "', "
				+ "'" + request.getParameter("lead-lost-reason") + "', "
				+ "'" + executiveId + "', "
				+ "'" + request.getParameter("lead-location") + "', "
				+ "'" + request.getParameter("lead-sublocation") + "', "
				+ "'" + request.getParameter("lead-expected-closure-date") + "', "
				+ "'" + request.getParameter("lead-lost-price") + "', "
				+ "'" + request.getParameter("lead-lost-institute") + "', "
				+ "'" + request.getParameter("lead-add-date") + "', "
				+ "'" + request.getParameter("lead-remarks") + "', "
				+ "'" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', "
				+ "'" + hotLeadFlag + "', "
				+ "'" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "', "
				+ "'" + request.getSession().getAttribute("USER_ID") + "' "
				+ ");";
//		System.out.println(SQL);
		return SQL;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			logger("lead_update");
			updateLeadData(request);
		} catch (Exception e) {
			logger("lead_update_error");
			e.printStackTrace();
			((DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT")).closeConnection();
			request.getSession().invalidate();
			response.sendRedirect("user-login");
		}
	}
	
	private void logger(String msg) {
		System.out.println(msg + "_"+ new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()));
	}
}
