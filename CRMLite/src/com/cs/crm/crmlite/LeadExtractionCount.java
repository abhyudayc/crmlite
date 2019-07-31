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

@WebServlet("/lead-extraction-count")
public class LeadExtractionCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DatabaseManager databaseManager;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		
		try {
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String[] leadExtractors = getLeadExtractors().split("__");
			ArrayList<JSONObject> jsonObjects = new ArrayList<>();
			for(int i = 0; i < leadExtractors.length; i++) {
				ArrayList<String[]> leadExtrData = getExtractionResult(leadExtractors[i].split("_")[0], fromDate, toDate);
				jsonObjects.add(getJSONObject(leadExtrData, leadExtractors[i].split("_")[1]));
			}
			response.getWriter().write(makeJSON(jsonObjects));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String getLeadExtractors() throws Exception {
		ArrayList<String[]> lxUsers = databaseManager.executeSelectQuery("SELECT cdb_users_id, cdb_users_full_name FROM crmlite_db.crmlite_db_users where cdb_users_role = 'l-x';");
		String returnString = "";
		for (int i = 0; i < lxUsers.size(); i++) {
			returnString += lxUsers.get(i)[0] + "_" + lxUsers.get(i)[1] + "__";
		}
		return returnString;
	}
	
	private ArrayList<String[]> getExtractionResult(String userId, String fromDate, String toDate) throws Exception {
		String query = "SELECT\r\n" + 
				"	cdb_leads_add_date, count(*)\r\n" + 
				"FROM\r\n" + 
				"	crmlite_db.crmlite_db_leads\r\n" + 
				"where\r\n" + 
				"	cdb_leads_added_by = '" + userId + "'	\r\n" + 
				"	and cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "'\r\n" + 
				"group by\r\n" + 
				"	cdb_leads_add_date";
		return databaseManager.executeSelectQuery(query);
	}
	
	private JSONObject getJSONObject(ArrayList<String[]> data, String leadExtractor) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", leadExtractor);
		for (String[] row : data) {
			System.out.println(row[0].replaceAll("-", "_"));
			jsonObject.put(row[0].replaceAll("-", "_"), row[1]);
		}
		return jsonObject;
	}
	
	private String makeJSON(ArrayList<JSONObject> jsonObjects) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < jsonObjects.size(); i++) {
			jsonArray.put(jsonObjects.get(i));
		}
		jsonObject.put("extractionData", jsonArray);
		return jsonObject.toString();
	}
}
