package com.cs.crm.crmlite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/dashboard-details-provider")
public class DashboardDetailsProvider extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseManager databaseManager;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		try {
			System.out.println(getMonhlyLeaderboard());
			System.out.println(getWeeklyLeaderboard());
			ArrayList<String[]> monthlyLeaderboardData = databaseManager.executeSelectQuery(getMonhlyLeaderboard());
			ArrayList<String[]> weeklyLeaderboardData = databaseManager.executeSelectQuery(getWeeklyLeaderboard());
			JSONObject leaderboard = new JSONObject();
			JSONArray monthlyWeeklyArray = new JSONArray();
			JSONArray monthlyJsonArray = new JSONArray();
			JSONArray weeklyJsonArray = new JSONArray();
			for(String[] m: monthlyLeaderboardData) {
				JSONObject jsonObjectInner = new JSONObject();
				jsonObjectInner.put("executiveName", m[0]);
				jsonObjectInner.put("target", m[1]);
				jsonObjectInner.put("achieved", m[2]);
				jsonObjectInner.put("gross", m[3]);
				monthlyJsonArray.put(jsonObjectInner);
			}
			for(String[] w: weeklyLeaderboardData) {
				JSONObject jsonObjectInner = new JSONObject();
				jsonObjectInner.put("executiveName", w[0]);
				jsonObjectInner.put("target", w[1]);
				jsonObjectInner.put("achieved", w[2]);
				jsonObjectInner.put("gross", w[3]);
				weeklyJsonArray.put(jsonObjectInner);
			}
			monthlyWeeklyArray.put(monthlyJsonArray);
			monthlyWeeklyArray.put(weeklyJsonArray);
			leaderboard.put("leaderboard", monthlyWeeklyArray);
			response.getWriter().write(leaderboard.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String getMonhlyLeaderboard() {
		Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    String strDate= formatter.format(date);
		
	    String monthString = strDate.split("-")[0] + "-" + strDate.split("-")[1];
		System.out.println("Leaderboard for:" + monthString);
		
		String query = "select table3.executive, table2.target, table1.achieved, table1.gross from\r\n" + 
				"\r\n" + 
				"(\r\n" + 
				"select\r\n" + 
				"crmlite_db.crmlite_db_leads.cdb_leads_executive as user_id,\r\n" + 
				"sum(crmlite_db.crmlite_db_leads.cdb_leads_advance_fees) as achieved,\r\n" + 
				"sum(crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees) as gross\r\n" + 
				"from\r\n" + 
				"crmlite_db.crmlite_db_leads\r\n" + 
				"where\r\n" + 
				"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories'\r\n" + 
				"and\r\n" + 
				"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + monthString + "-01' and '" + monthString + "-31" + "'\r\n" + 
				"group by\r\n" + 
				"crmlite_db.crmlite_db_leads.cdb_leads_executive\r\n" + 
				") as table1\r\n" + 
				"\r\n" + 
				"right outer join\r\n" + 
				"\r\n" + 
				"(\r\n" + 
				"select\r\n" + 
				"crmlite_db.crmlite_db_targets.cdb_targets_user_id as user_id,\r\n" + 
				"crmlite_db.crmlite_db_targets.cdb_targets_amt_w1 + crmlite_db.crmlite_db_targets.cdb_targets_amt_w2 + crmlite_db.crmlite_db_targets.cdb_targets_amt_w3 + crmlite_db.crmlite_db_targets.cdb_targets_amt_w4 as target\r\n" + 
				"from\r\n" + 
				"crmlite_db.crmlite_db_targets\r\n" + 
				"where\r\n" + 
				"crmlite_db.crmlite_db_targets.cdb_targets_month = '" + monthString + "'\r\n" + 
				"group by user_id\r\n" + 
				") as table2\r\n" + 
				"\r\n" + 
				"on table1.user_id = table2.user_id\r\n" + 
				"\r\n" + 
				"right outer join\r\n" + 
				"\r\n" + 
				"(\r\n" + 
				"select\r\n" + 
				"crmlite_db.crmlite_db_users.cdb_users_id as user_id,\r\n" + 
				"crmlite_db.crmlite_db_users.cdb_users_full_name as executive\r\n" + 
				"from crmlite_db.crmlite_db_users\r\n" + 
				"where crmlite_db.crmlite_db_users.cdb_users_role in (\"bde\", \"bdm\")\r\n" + 
				") as table3\r\n" + 
				"\r\n" + 
				"on table2.user_id = table3.user_id\r\n" + 
				"\r\n" + 
				"order by table1.achieved desc;\r\n" + 
				"";
		
		return query;
	}
	
	private String getWeeklyLeaderboard() {
		Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    String strDate= formatter.format(date);
		
	    String monthString = strDate.split("-")[0] + "-" + strDate.split("-")[1];
		System.out.println("Leaderboard for:" + monthString);
		
		String query = "select table3.executive, table2.target, table1.achieved, table1.gross from\r\n" + 
				"\r\n" + 
				"(\r\n" + 
				"select\r\n" + 
				"crmlite_db.crmlite_db_leads.cdb_leads_executive as user_id,\r\n" + 
				"sum(crmlite_db.crmlite_db_leads.cdb_leads_advance_fees) as achieved,\r\n" + 
				"sum(crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees) as gross\r\n" + 
				"from\r\n" + 
				"crmlite_db.crmlite_db_leads\r\n" + 
				"where\r\n" + 
				"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories'\r\n" + 
				"and\r\n" + 
				"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + monthString + "-01' and '" + monthString + "-31" + "'\r\n" + 
				"group by\r\n" + 
				"crmlite_db.crmlite_db_leads.cdb_leads_executive\r\n" + 
				") as table1\r\n" + 
				"\r\n" + 
				"right outer join\r\n" + 
				"\r\n" + 
				"(\r\n" + 
				"select\r\n" + 
				"crmlite_db.crmlite_db_targets.cdb_targets_user_id as user_id,\r\n" + 
				"crmlite_db.crmlite_db_targets.cdb_targets_amt_w1 + crmlite_db.crmlite_db_targets.cdb_targets_amt_w2 + crmlite_db.crmlite_db_targets.cdb_targets_amt_w3 + crmlite_db.crmlite_db_targets.cdb_targets_amt_w4 as target\r\n" + 
				"from\r\n" + 
				"crmlite_db.crmlite_db_targets\r\n" + 
				"where\r\n" + 
				"crmlite_db.crmlite_db_targets.cdb_targets_month = '" + monthString + "'\r\n" + 
				"group by user_id\r\n" + 
				") as table2\r\n" + 
				"\r\n" + 
				"on table1.user_id = table2.user_id\r\n" + 
				"\r\n" + 
				"right outer join\r\n" + 
				"\r\n" + 
				"(\r\n" + 
				"select\r\n" + 
				"crmlite_db.crmlite_db_users.cdb_users_id as user_id,\r\n" + 
				"crmlite_db.crmlite_db_users.cdb_users_full_name as executive\r\n" + 
				"from crmlite_db.crmlite_db_users\r\n" + 
				"where crmlite_db.crmlite_db_users.cdb_users_role in (\"bde\", \"bdm\")\r\n" + 
				") as table3\r\n" + 
				"\r\n" + 
				"on table2.user_id = table3.user_id\r\n" + 
				"\r\n" + 
				"order by table1.achieved desc;\r\n" + 
				"";
		
		return query;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		
		try {
			String thing = request.getParameter("thing");
			String fromDate = request.getParameter("from-date");
			String toDate = request.getParameter("to-date");
			String course = request.getParameter("course");
			String executive = request.getParameter("executive");

			ArrayList<String[]> arrayListData;
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			int i = 0;
			
			switch(thing) {
			case "paymentCollected":
				arrayListData = databaseManager.executeSelectQuery(getNameOfLeadsForWhomPaymentIsCollected(fromDate, toDate, course, executive));
				i = 0;
				for(String[] strArr:arrayListData) {
					JSONObject jsonObjectInner = new JSONObject();
					jsonObjectInner.put("name", strArr[0]);
					jsonObjectInner.put("mobile", strArr[1]);
					jsonObjectInner.put("course", strArr[2]);
					jsonObjectInner.put("totalAmt", strArr[3]);
					jsonObjectInner.put("feesPaidAmt", strArr[4]);
					jsonObjectInner.put("feesPaidDate", strArr[5]);
					jsonObjectInner.put("trainer", strArr[6]);
					jsonObjectInner.put("instl1", strArr[7]);
					jsonObjectInner.put("dueDate1", strArr[8]);
					jsonObjectInner.put("instl2", strArr[9]);
					jsonObjectInner.put("dueDate2", strArr[10]);
					jsonObjectInner.put("executive", strArr[11]);
					jsonArray.put(jsonObjectInner);
				}
				jsonObject.put("paymentCollectedDetails", jsonArray);
				response.getWriter().write(jsonObject.toString());
				break;
				
			case "paymentPending":
				arrayListData = databaseManager.executeSelectQuery(getNameOfLeadsForWhomPaymentIsPending(fromDate, toDate, course, executive));
				i = 0;
				for(String[] strArr:arrayListData) {
					JSONObject jsonObjectInner = new JSONObject();
					jsonObjectInner.put("name", strArr[0]);
					jsonObjectInner.put("mobile", strArr[1]);
					jsonObjectInner.put("course", strArr[2]);
					jsonObjectInner.put("totalAmt", strArr[3]);
					jsonObjectInner.put("pendingAmt", strArr[4]);
					jsonObjectInner.put("instl1", strArr[5]);
					jsonObjectInner.put("dueDate1", strArr[6]);
					jsonObjectInner.put("instl2", strArr[7]);
					jsonObjectInner.put("dueDate2", strArr[8]);
					jsonObjectInner.put("executive", strArr[9]);
					jsonArray.put(jsonObjectInner);
				}
				jsonObject.put("paymentPendingDetails", jsonArray);
				response.getWriter().write(jsonObject.toString());
				break;
				
			case "paymentPipeline":
				arrayListData = databaseManager.executeSelectQuery(getNameOfLeadsForWhomPaymentIsPipeline(fromDate, toDate, course, executive));
				i = 0;
				for(String[] strArr:arrayListData) {
					JSONObject jsonObjectInner = new JSONObject();
					jsonObjectInner.put("name", strArr[0]);
					jsonObjectInner.put("mobile", strArr[1]);
					jsonObjectInner.put("course", strArr[2]);
					jsonObjectInner.put("demoDate", strArr[3]);
					jsonObjectInner.put("demoTime", strArr[4]);
					jsonObjectInner.put("trainer", strArr[5]);
					jsonObjectInner.put("proposedFees", strArr[6]);
					jsonObjectInner.put("expectedClosureDate", strArr[7]);
					jsonObjectInner.put("executiveName", strArr[8]);
					jsonArray.put(jsonObjectInner);
				}
				jsonObject.put("paymentPipelineDetails", jsonArray);
				response.getWriter().write(jsonObject.toString());
				break;
				
			case "customerAcquired":
				arrayListData = databaseManager.executeSelectQuery(getNameOfCustomerAcquired(fromDate, toDate, course, executive));
				i = 0;
				for(String[] strArr:arrayListData) {
					JSONObject jsonObjectInner = new JSONObject();
					jsonObjectInner.put("name", strArr[0]);
					jsonObjectInner.put("mobile", strArr[1]);
					jsonObjectInner.put("course", strArr[2]);
					jsonObjectInner.put("quotedFees", strArr[3]);
					jsonObjectInner.put("trainer", strArr[4]);
					jsonObjectInner.put("paidFees", strArr[5]);
					jsonObjectInner.put("pendingFees", strArr[6]);
					jsonObjectInner.put("executiveName", strArr[7]);
					jsonArray.put(jsonObjectInner);
				}
				jsonObject.put("customerAcquired", jsonArray);
				response.getWriter().write(jsonObject.toString());
				break;
				
			case "demoLinedUp":
				arrayListData = databaseManager.executeSelectQuery(getNameOfLeadsForWhomDemoIsLinedUp(fromDate, toDate, course, executive));
				i = 0;
				for(String[] strArr:arrayListData) {
					JSONObject jsonObjectInner = new JSONObject();
					jsonObjectInner.put("name", strArr[0]);
					jsonObjectInner.put("mobile", strArr[1]);
					jsonObjectInner.put("course", strArr[2]);
					jsonObjectInner.put("fees", strArr[3]);
					jsonObjectInner.put("demoScheduledDate", strArr[4]);
					jsonObjectInner.put("demoScheduledTime", strArr[5]);
					jsonObjectInner.put("trainer", strArr[6]);
					jsonObjectInner.put("executiveName", strArr[7]);
					jsonArray.put(jsonObjectInner);
				}
				jsonObject.put("demoLinedUp", jsonArray);
				response.getWriter().write(jsonObject.toString());
				break;
				
			case "demoScheduled":
				arrayListData = databaseManager.executeSelectQuery(getNameOfLeadsForWhomDemoIsScheduled(fromDate, toDate, course, executive));
				i = 0;
				for(String[] strArr:arrayListData) {
					JSONObject jsonObjectInner = new JSONObject();
					jsonObjectInner.put("name", strArr[0]);
					jsonObjectInner.put("mobile", strArr[1]);
					jsonObjectInner.put("course", strArr[2]);
					jsonObjectInner.put("fees", strArr[3]);
					jsonObjectInner.put("demoScheduledDate", strArr[4]);
					jsonObjectInner.put("demoScheduledTime", strArr[5]);
					jsonObjectInner.put("trainer", strArr[6]);
					jsonObjectInner.put("executiveName", strArr[7]);
					jsonArray.put(jsonObjectInner);
				}
				jsonObject.put("demoScheduled", jsonArray);
				response.getWriter().write(jsonObject.toString());
				break;
				
			case "leadsAssigned":
				arrayListData = databaseManager.executeSelectQuery(getNameOfLeadsAssigned(fromDate, toDate, course, executive));
				i = 0;
				for(String[] strArr:arrayListData) {
					JSONObject jsonObjectInner = new JSONObject();
					jsonObjectInner.put("name", strArr[0]);
					jsonObjectInner.put("mobile", strArr[1]);
					jsonObjectInner.put("course", strArr[2]);
					jsonObjectInner.put("addDate", strArr[3]);
					jsonObjectInner.put("status", strArr[4]);
					jsonObjectInner.put("executiveName", strArr[5]);
					jsonArray.put(jsonObjectInner);
				}
				jsonObject.put("leadsAssigned", jsonArray);
				response.getWriter().write(jsonObject.toString());
				break;
				
			case "leadsCalled":
				arrayListData = databaseManager.executeSelectQuery(getNameOfLeadsCalled(fromDate, toDate, course, executive));
				i = 0;
				for(String[] strArr:arrayListData) {
					JSONObject jsonObjectInner = new JSONObject();
					jsonObjectInner.put("name", strArr[0]);
					jsonObjectInner.put("mobile", strArr[1]);
					jsonObjectInner.put("course", strArr[2]);
					jsonObjectInner.put("addDate", strArr[3]);
					jsonObjectInner.put("status", strArr[4]);
					jsonObjectInner.put("executiveName", strArr[5]);
					jsonArray.put(jsonObjectInner);
				}
				jsonObject.put("leadsCalled", jsonArray);
				response.getWriter().write(jsonObject.toString());
				break;
				
			case "customerLost":
				arrayListData = databaseManager.executeSelectQuery(getNameOfCustomerLost(fromDate, toDate, course, executive));
				i = 0;
				for(String[] strArr:arrayListData) {
					JSONObject jsonObjectInner = new JSONObject();
					jsonObjectInner.put("name", strArr[0]);
					jsonObjectInner.put("mobile", strArr[1]);
					jsonObjectInner.put("course", strArr[2]);
					jsonObjectInner.put("lostDate", strArr[3]);
					jsonObjectInner.put("lostReason", strArr[4]);
					jsonObjectInner.put("lostAtPrice", strArr[5]);
					jsonObjectInner.put("lostToInstitute", strArr[6]);
					jsonObjectInner.put("remarks", strArr[7]);
					jsonObjectInner.put("executiveName", strArr[8]);
					jsonArray.put(jsonObjectInner);
				}
				jsonObject.put("customerLost", jsonArray);
				response.getWriter().write(jsonObject.toString());
				break;
				
			case "fullPayment":
				arrayListData = databaseManager.executeSelectQuery(getNameOfFullPaymentReceived(fromDate, toDate, course, executive));
				i = 0;
				for(String[] strArr:arrayListData) {
					JSONObject jsonObjectInner = new JSONObject();
					jsonObjectInner.put("name", strArr[0]);
					jsonObjectInner.put("mobile", strArr[1]);
					jsonObjectInner.put("course", strArr[2]);
					jsonObjectInner.put("fees", strArr[3]);
					jsonObjectInner.put("trainer", strArr[4]);
					jsonObjectInner.put("executiveName", strArr[5]);
					jsonArray.put(jsonObjectInner);
				}
				jsonObject.put("fullPayment", jsonArray);
				response.getWriter().write(jsonObject.toString());
				break;
			}
			
		} catch(Exception e) {}
	}
	
	// getting names of leads for whom payment is collected
	private String getNameOfLeadsForWhomPaymentIsCollected(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_1, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_2, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id)" +
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_1, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_2, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id)" +
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_1, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_2, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id)" +
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_1, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_2, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id)" +
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}

	// getting names of leads for whom payment pending

	private String getNameOfLeadsForWhomPaymentIsPending(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
				+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
				+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
				+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
				+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1 + crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
				+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1, "
				+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_1, "
				+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
				+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_2, "
				+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) " +
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1 + crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_1, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_2, "
			+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) " +
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1 + crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_1, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_2, "
			+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) " +
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1 + crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_1, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_due_date_2, "
			+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) " +
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}

	// getting  names of leads for whom payment is in pipeline
	private String getNameOfLeadsForWhomPaymentIsPipeline(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_time, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_expected_closure_date, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'pipeline';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_time, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_expected_closure_date, "
			+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'pipeline' " +
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_time, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_expected_closure_date, "
			+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'pipeline' " + 
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_time, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
			+ "crmlite_db.crmlite_db_leads.cdb_leads_expected_closure_date, "
			+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'pipeline' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}

	// getting names of customers acquired
	private String getNameOfCustomerAcquired(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1 + crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1 + crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1 + crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_advance_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_installment_1 + crmlite_db.crmlite_db_leads.cdb_leads_installment_2, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}

	// getting  names of leads for whom demo is scheduled
	private String getNameOfLeadsForWhomDemoIsScheduled(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
					+ "cdb_leads_demo_time, "
					+ "cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_demo_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
					+ "cdb_leads_demo_time, "
					+ "cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_demo_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
					+ "cdb_leads_demo_time, "
					+ "cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_demo_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
					+ "cdb_leads_demo_time, "
					+ "cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_demo_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}

	// getting  names of leads for whom demo is lined up
	private String getNameOfLeadsForWhomDemoIsLinedUp(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
					+ "cdb_leads_demo_time, "
					+ "cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
					+ "cdb_leads_demo_time, "
					+ "cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
					+ "cdb_leads_demo_time, "
					+ "cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_demo_date, "
					+ "cdb_leads_demo_time, "
					+ "cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}

	// getting details of lead assigned
	private String getNameOfLeadsAssigned(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_add_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_status, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE  "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_add_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_status, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_add_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_status, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_add_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_status, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}

	// getting details of lead called
	private String getNameOfLeadsCalled(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_add_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_status, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE  "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status not in (\"\") "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_add_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_status, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status not in (\"\") "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_add_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_status, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status not in (\"\") "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_add_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_status, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status not in (\"\") "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}

	// getting details of customer lost
	private String getNameOfCustomerLost(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_last_updated_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_reason, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_at_price, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_to_institute, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_remarks, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE  "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'customer-lost' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_last_updated_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_reason, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_at_price, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_to_institute, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_remarks, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE  "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'customer-lost' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_last_updated_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_reason, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_at_price, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_to_institute, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_remarks, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE  "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'customer-lost' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_last_updated_date, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_reason, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_at_price, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_lost_to_institute, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_remarks, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE  "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'customer-lost' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}
	
	// getting name of full payment received
	private String getNameOfFullPaymentReceived(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' " + 
			"AND " + 
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_fees = crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
					"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' " + 
			"AND " + 
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_fees = crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' " + 
			"AND " + 
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_fees = crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT crmlite_db.crmlite_db_leads.cdb_leads_name, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_mobile, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_course, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees, "
					+ "crmlite_db.crmlite_db_leads.cdb_leads_trainer, "
					+ "(select crmlite_db.crmlite_db_users.cdb_users_full_name from crmlite_db.crmlite_db_users where crmlite_db.crmlite_db_leads.cdb_leads_executive = crmlite_db.crmlite_db_users.cdb_users_id) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' " + 
			"AND " + 
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_fees = crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}
}









