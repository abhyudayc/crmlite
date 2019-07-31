package com.cs.crm.crmlite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/target")
public class TargetSetter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseManager databaseManager;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		try {
			String query = "SELECT * FROM crmlite_db.crmlite_db_targets where cdb_targets_month = '" + request.getParameter("year-month") + "';";
			ArrayList<String[]> targetData = databaseManager.executeSelectQuery(query);
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for(int i = 0; i < targetData.size(); i++) {
				jsonArray.put(Arrays.asList(targetData.get(i)));
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		try {
			ArrayList<String> deleteQueries = new ArrayList<>();
			ArrayList<String> insertQueries = new ArrayList<>();
			Set paramKeys = request.getParameterMap().keySet();
			paramKeys.remove("target-assignment-month");
			try {
				for(int i = 2; i <= getMaxUserId(paramKeys); i++) {
					System.out.println(".... " + i + " ....");
					if (request.getParameter("ta" + i + "m") == null)
						continue;
					String targetAssignmentName = "" + i;
					String targetAssignmentMonth = request.getParameter("target-assignment-month");
					String targetAssignmentWeek1 = request.getParameter("ta" + i + "w1");
					String targetAssignmentWeek2 = request.getParameter("ta" + i + "w2");
					String targetAssignmentWeek3 = request.getParameter("ta" + i + "w3");
					String targetAssignmentWeek4  = request.getParameter("ta" + i + "w4");
					
					String deleteQuery = "delete from crmlite_db.crmlite_db_targets where cdb_targets_user_id = '" + targetAssignmentName + "' "
							+ "and cdb_targets_month = '" + targetAssignmentMonth + "';";
				
					String insertQuery = "INSERT INTO crmlite_db.crmlite_db_targets ("
							+ "cdb_targets_user_id, "
							+ "cdb_targets_month, "
							+ "cdb_targets_amt_w1, "
							+ "cdb_targets_amt_w2, "
							+ "cdb_targets_amt_w3, "
							+ "cdb_targets_amt_w4 "
							+ ") VALUES ("
							+ "'" + targetAssignmentName + "', "
							+ "'" + targetAssignmentMonth+ "', "
							+ "'" + targetAssignmentWeek1+ "', "
							+ "'" + targetAssignmentWeek2 + "', "
							+ "'" + targetAssignmentWeek3 + "', "
							+ "'" + targetAssignmentWeek4 + "' "
							+ ");";
					
					System.out.println(insertQuery);
					System.out.println(deleteQuery);
					
					
					deleteQueries.add(deleteQuery);
					insertQueries.add(insertQuery);
					if(i == 4)
						i++;
				}
			} catch(Exception e) {}
			
			// first delete all the entries
			for(String query:deleteQueries)
				databaseManager.executeNonSelectDMLQuery(query);
			
			// insert all the rows
			for(String query:insertQueries)
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
	

	private static int getMaxUserId(Set<String> params) {
		Set<Integer> set = new HashSet<Integer>();
		for(String s : params) {
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			set.add(Integer.parseInt(matcher.group(0)));
		}
		return Collections.max(set);
	}

}
