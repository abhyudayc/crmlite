package com.cs.crm.crmlite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/global-lead")
public class GlobalLead extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String leadNameNumber = request.getParameter("q");
		try {
			DatabaseManager databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
			String query = "";
			if(request.getSession().getAttribute("USER_ROLE").equals("admin") || request.getSession().getAttribute("USER_ROLE").equals("l-x")) {
				query = "select " + 
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
						"on leads.cdb_leads_executive = users.cdb_users_id " +
						"where cdb_leads_name like '%" + leadNameNumber + "%' or cdb_leads_mobile like '%" + leadNameNumber + "%';";
			} else {
				query = "select " + 
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
						"on leads.cdb_leads_executive = users.cdb_users_id " +
						"where cdb_leads_executive = '" + request.getSession().getAttribute("USER_ID") + "' AND (cdb_leads_name like '%" + leadNameNumber + "%' or cdb_leads_mobile like '%" + leadNameNumber + "%');";
			}
			ArrayList<String[]> leads = databaseManager.executeSelectQuery(query);
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for(int i = 0; i < leads.size(); i++) {
				jsonArray.put(Arrays.asList(leads.get(i)));
			}
			jsonObject.put("globallyFetchedLeads", jsonArray);
			response.getWriter().write(jsonObject.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
