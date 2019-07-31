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

@WebServlet("/add-new-user")
public class AddNewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DatabaseManager databaseManager;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		try {
			String query = "SELECT cdb_users_full_name, cdb_users_role, "
					+ "(select cdb_users_full_name from crmlite_db_users "
					+ "where cdb_users_id = cdb_users_reporting_to),"
					+ " cdb_users_name, cdb_users_pass "
					+ "FROM crmlite_db.crmlite_db_users "
					+ "order by cdb_users_role;";
			ArrayList<String[]> userData = databaseManager.executeSelectQuery(query);
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for(int i = 0; i < userData.size(); i++) {
				jsonArray.put(Arrays.asList(userData.get(i)));
			}
			jsonObject.put("jsonLeads", jsonArray);
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				((DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT")).closeConnection();
			} catch(Exception ex) {}
			request.getSession().invalidate();
			response.sendRedirect("user-login");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		try {
			String fullName = request.getParameter("add-new-user-full-name");
			String role = request.getParameter("add-new-user-role");
			String reportingTo = request.getParameter("add-new-user-reportingto");
			String username = request.getParameter("add-new-user-uname");
			String password = request.getParameter("add-new-user-password");
			
			
//			String query = "UPDATE `crmlite_db`.`crmlite_db_users` " + 
//					"SET " +  
//					"`cdb_users_name` = '" 		   + username 	+ "', " + 
//					"`cdb_users_pass` = '" 		   + password 	+ "', " + 
//					"`cdb_users_role` = '" 		   + role		+ "', " + 
//					"`cdb_users_full_name` = '"    + fullName + "', " + 
//					"`cdb_users_reporting_to` = '" + reportingTo + "' " + 
//					"WHERE `cdb_users_id` = '" + username + "';";
			
			
			String query = "INSERT INTO crmlite_db.crmlite_db_users ("
					+ "cdb_users_name, "
					+ "cdb_users_pass, "
					+ "cdb_users_role, "
					+ "cdb_users_full_name, "
					+ "cdb_users_reporting_to"
					+ ") VALUES ("
					+ "'" + username + "', "
					+ "'" + password + "', "
					+ "'" + role + "', "
					+ "'" + fullName + "', "
					+ "'" + reportingTo + "'"
					+ ");";
			databaseManager.executeNonSelectDMLQuery(query);
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
}
