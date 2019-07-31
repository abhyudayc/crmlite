package com.cs.crm.crmlite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/reassign-lead")
public class ReassignLead extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseManager databaseManager;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] listOfPks = request.getParameter("list").split(",");
		String execToAssignTo = request.getParameter("execId");
		String sql = updateQueryBuilder(listOfPks, execToAssignTo);
		try {
			databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
			int leadsReassigned = databaseManager.executeNonSelectDMLQuery(sql);
			response.sendRedirect("lead-data");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				((DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT")).closeConnection();
			} catch(Exception ex) {}
			request.getSession().invalidate();
			response.sendRedirect("user-login");
		}
	}
	
	private String updateQueryBuilder(String[] listOfPks, String exec) {
		String sql = "UPDATE crmlite_db.crmlite_db_leads " + 
				"SET " + 
				"cdb_leads_executive = '" + exec + "', " + 
				"cdb_leads_add_date='" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', " +
				"cdb_leads_last_updated_date = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', " + 
				"cdb_leads_status = '', " + 
				"cdb_leads_proposed_fees = '', " +
				"cdb_leads_demo_date = '', " +
				"cdb_leads_demo_time = '', " +
				"cdb_leads_followup_date = '', " +
				"cdb_leads_followup_time = '', " +
				"cdb_leads_quoted_fees = '', " +
				"cdb_leads_advance_fees = '', " +
				"cdb_leads_advance_paid_date = '', " +
				"cdb_leads_installment_1 = '', " +
				"cdb_leads_due_date_1 = '', " +
				"cdb_leads_installment_2 = '', " +
				"cdb_leads_due_date_2 = '', " +
				"cdb_leads_trainer = '', " +
				"cdb_leads_lost_reason = '', " +
				"cdb_leads_expected_closure_date = '', " +
				"cdb_leads_lost_at_price = '', " +
				"cdb_leads_lost_to_institute = '', " +
				"cdb_leads_remarks = '' " +
				"WHERE cdb_leads_id in (";
		for (String pk: listOfPks) {
			sql += "'" + pk + "', ";
		}
		sql = sql.substring(0, sql.length() - 2);
		sql += ")";
		return sql;
	}
}
