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

@WebServlet("/get-sublocation")
public class SublocationDataFetcher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseManager databaseManager;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		try {
			ArrayList<String[]> sublocs = getSublocations(request, request.getParameter("location"));
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			try {
				for(int i = 0; i < sublocs.size(); i++) {
					jsonArray.put(Arrays.asList(sublocs.get(i)));
				}
				jsonObject.put("sublocs", jsonArray);
			} catch(Exception e) {
				jsonObject.put("sublocs", new String[] {});
			}
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {}
	}
	

	
	
	/*
	 * Get sublocations (ids and names) of a selected location from database
	 */
	
	private ArrayList<String[]> getSublocations(HttpServletRequest request, String locationName) throws Exception {
		String query = "SELECT `cdb_sublocation_id`, `cdb_soblocation_name` FROM `crmlite_db`.`crmlite_db_sublocation_mst` "
				+ "WHERE `cdb_sublocation_location_id` in ("
				+ "SELECT `cdb_location_id` FROM `crmlite_db`.`crmlite_db_location_mst`"
				+ "WHERE `cdb_location_name` = '" + locationName + "');";
		return databaseManager.executeSelectQuery(query);	
	}

}
