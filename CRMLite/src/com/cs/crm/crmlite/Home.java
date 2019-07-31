package com.cs.crm.crmlite;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DatabaseManager databaseManager;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
			if(request.getSession().getAttribute("USER_ROLE").equals("admin")) {
				request.setAttribute("COURSE_DATA", getCourses(request));
				request.setAttribute("EXEC_DATA", getSalesExecs(request));
				request.getRequestDispatcher("index.jsp").include(request, response);
			} else if(request.getSession().getAttribute("USER_ROLE").equals("bdm")) {
				request.setAttribute("EXEC_DATA", getSalesExecs(request, (String)request.getSession().getAttribute("USER_ID")));
				request.setAttribute("COURSE_DATA", getCourses(request, (String)request.getSession().getAttribute("USER_ID")));
				request.getRequestDispatcher("index.jsp").include(request, response);
			} else if(request.getSession().getAttribute("USER_ROLE").equals("bde")) {
				request.setAttribute("COURSE_DATA", getCourses(request, (String)request.getSession().getAttribute("USER_ID")));
				request.getRequestDispatcher("index.jsp").include(request, response);
			} else if(request.getSession().getAttribute("USER_ROLE").equals("l-x")) {
				request.setAttribute("COURSE_DATA", getCourses(request));
				request.getRequestDispatcher("index.jsp").include(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
			try {
				((DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT")).closeConnection();
			} catch(Exception ex) {}
			request.getSession().invalidate();
			response.sendRedirect("user-login");
		}
	}
	
	
	/*
	 * Get all courses (ids and names) for particular sales_exec(id) from database
	 */
	
	private ArrayList<String[]> getCourses(HttpServletRequest request, String salesExecId) throws Exception {
		
		String query = "";
		query = "SELECT distinct cdb_course_id, cdb_course_name\r\n" + 
				"FROM crmlite_db.crmlite_db_course_mst INNER JOIN crmlite_db.crmlite_db_exec_ass\r\n" + 
				"ON crmlite_db.crmlite_db_course_mst.cdb_course_name = crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_course\r\n" + 
				"WHERE crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_exec_id = '" + salesExecId + "';";
//		System.out.println(query);
		return databaseManager.executeSelectQuery(query);
	}
	

	/*
	 * Get all courses (ids and names) from database
	 */
	
	private ArrayList<String[]> getCourses(HttpServletRequest request) throws Exception {
		
		String query = "";
		query = "SELECT distinct cdb_course_id, cdb_course_name FROM crmlite_db.crmlite_db_course_mst;";
		return databaseManager.executeSelectQuery(query);
	}

	/*
	 * Get all sales execs' ids and names from database
	 */
	
	private ArrayList<String[]> getSalesExecs(HttpServletRequest request) throws Exception {
		String query = "SELECT * FROM crmlite_db.crmlite_db_users WHERE cdb_users_role in (\"admin\", \"bde\", \"bdm\");";
		return databaseManager.executeSelectQuery(query);
	}

	/*
	 * Get sales execs' ids and names from database, reporting to particular exec
	 */
	
	private ArrayList<String[]> getSalesExecs(HttpServletRequest request, String reportingTo) throws Exception {
		String query = "SELECT * FROM crmlite_db.crmlite_db_users where cdb_users_reporting_to = '" + reportingTo + "' or cdb_users_id = '" + reportingTo + "';";
		return databaseManager.executeSelectQuery(query);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
