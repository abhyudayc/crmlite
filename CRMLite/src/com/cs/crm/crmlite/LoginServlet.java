package com.cs.crm.crmlite;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("MSG_FRM_SERVLET", "Session Expired. Re-login required.");
		request.getRequestDispatcher("login.jsp").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("user-name");
		String userPassword = request.getParameter("user-password");
		DatabaseManager databaseManager = new DatabaseManager("crmlite_db", "root", "dXVYKs1eE9");
//		DatabaseManager databaseManager = new DatabaseManager("crmlite_db", "root", "root");
		try {
			String[] userRoleAndId = databaseManager.executeSelectQuery("SELECT `cdb_users_role`, `cdb_users_id`, `cdb_users_full_name` FROM `crmlite_db`.`crmlite_db_users` WHERE `cdb_users_name` = '" + userName + "' AND `cdb_users_pass` = '" + userPassword + "';").get(0);
			request.getSession().setAttribute("USER_ROLE", userRoleAndId[0]);
			request.getSession().setAttribute("USER_ID", userRoleAndId[1]);
			request.getSession().setAttribute("USER_FULL_NAME", userRoleAndId[2]);
			request.getSession().setAttribute("USER_NAME", userName);
			request.getSession().setAttribute("DATABASE_OBJECT", databaseManager);
			System.out.println(request.getSession().getAttribute("DATABASE_OBJECT"));
			response.sendRedirect("home");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("MSG_FRM_SERVLET", "Invalid credentials");
			request.getRequestDispatcher("login.jsp").include(request, response);
		}
	}

}
