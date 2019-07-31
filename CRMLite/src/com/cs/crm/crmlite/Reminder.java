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

@WebServlet("/reminder")
public class Reminder extends HttpServlet {

	String date, time, msg, showTo;
	DatabaseManager databaseManager;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		try {
			if(request.getParameter("getSet").equals("get")) {
				String query = "SELECT * FROM crmlite_db.crmlite_db_rem_mst where cdb_rem_show_to = '" + request.getParameter("userId") + "' order by cdb_rem_date desc, cdb_rem_time desc;";
				ArrayList<String[]> reminders = databaseManager.executeSelectQuery(query);
				System.out.println(query);
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				for(int i = 0; i < reminders.size(); i++) {
					jsonArray.put(Arrays.asList(reminders.get(i)));
				}
				jsonObject.put("reminders", jsonArray);
				response.getWriter().write(jsonObject.toString());
			} else if(request.getParameter("getSet").equals("set")) {
				String query = "INSERT INTO crmlite_db.crmlite_db_rem_mst"
						+ " (cdb_rem_date, cdb_rem_time, cdb_rem_msg, cdb_rem_show_to)"
						+ " values ('" 
						+ request.getParameter("date") + "', '"
						+ request.getParameter("time") + "', '"
						+ request.getParameter("desc") + "', '"
						+ request.getParameter("userId") + "');";
				databaseManager.executeNonSelectDMLQuery(query);
			}
		} catch(Exception e) {}
	}
}
