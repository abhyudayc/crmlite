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

@WebServlet("/exec-assignment-data-fetcher")
public class ExecAssignmentDataFetcher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseManager databaseManager;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		try {
			response.getWriter().write(getExecutiveAssignment(request).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JSONObject getExecutiveAssignment(HttpServletRequest request) throws Exception {
		String query = "SELECT " + 
				"crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_course, " +
				"crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_exec_id, " + 
				"crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_loc " + 
				"FROM crmlite_db.crmlite_db_exec_ass " +
				"WHERE crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_removed_date IS NULL " +
				"ORDER BY crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_course;";
		ArrayList<String[]> execAssignment =  databaseManager.executeSelectQuery(query);
		System.out.println(execAssignment.size());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("execAssData", new JSONArray(Arrays.asList(execAssignment)));
		return jsonObject;
	}

}
