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

@WebServlet("/hot-lead-data")
public class HotLeadDataFetcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DatabaseManager databaseManager;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		int untouchedHotLeads = 0;
		try {
			ArrayList<String[]> hotLeads = getHotLeads(request, date);
			for(String[] s: hotLeads) {
				if(s[6]=="" || s[6].equals(""))
					untouchedHotLeads++;
			}
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for(int i = 0; i < hotLeads.size(); i++) {
				jsonArray.put(Arrays.asList(hotLeads.get(i)));
			}
			jsonObject.put("untouchedcount", untouchedHotLeads);
			jsonObject.put("totalcount", hotLeads.size()-1);
			jsonObject.put("hotleads", jsonArray);
			response.getWriter().write(jsonObject.toString());
		
		} catch (Exception e) {e.printStackTrace();}
	}
	
private ArrayList<String[]> getHotLeads(HttpServletRequest request, String date) throws Exception {
		
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
		
		// the date part
		String datePartOfQuery = "cdb_leads_add_date = '" + date + "'";		if(request.getSession().getAttribute("USER_ROLE").equals("admin") || request.getSession().getAttribute("USER_ROLE").equals("l-x")) {
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
		
		query += " AND leads.cdb_leads_hot_lead_flag = '1';";
		
		return databaseManager.executeSelectQueryWithMetaData(query);
	}
}
