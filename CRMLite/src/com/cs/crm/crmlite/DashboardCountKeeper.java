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

@WebServlet("/dashboard-count-keeper")
public class DashboardCountKeeper extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseManager databaseManager;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	// getting count of lead assigned
	private String getCountOfLeadsAssigned(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*)  "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE  "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*)  "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}
	
	// getting count of success stories
	private String getCountOfSuccessStories(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*)  "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE  "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories'  "+
			"AND  "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*)  "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
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
	
	// getting count of DNRs
	private String getCountOfDNRs(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*)  "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE  "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'DNR'  "+
			"AND  "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*)  "+
			"FROM  "+
			"crmlite_db.crmlite_db_leads  "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'DNR' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'DNR' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'DNR' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}
	
	// getting count of gross revenue
	private String getTotalGrossRevenue(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return  " select "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_1 between '"  + fromDate +  "' and '"  + toDate +  "' and cdb_leads_paid_1='Y', "  + 
					 " 			(cdb_leads_installment_1), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_2 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_2='Y', "  + 
					 " 			(cdb_leads_installment_2), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_3 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_3='Y', "  + 
					 " 			(cdb_leads_installment_3), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_4 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_4='Y', "  + 
					 " 			(cdb_leads_installment_4), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     ) as a "  + 
					 " from "  + 
					 " 	crmlite_db.crmlite_db_leads "  + 
					 " where "  + 
					 " 	crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories'; " ;
		else if(!course.equals( "ALL" ) && executive.equals( "ALL" ))
			return  " select "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_1 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_1='Y', "  + 
					 " 			(cdb_leads_installment_1), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_2 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_2='Y', "  + 
					 " 			(cdb_leads_installment_2), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_3 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_3='Y', "  + 
					 " 			(cdb_leads_installment_3), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_4 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_4='Y', "  + 
					 " 			(cdb_leads_installment_4), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     ) as a "  + 
					 " from "  + 
					 " 	crmlite_db.crmlite_db_leads "  + 
					 " where "  + 
					 " 	crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "  +
					 " and  " +
					 " crmlite_db.crmlite_db_leads.cdb_leads_course = '"  + course +  "'; " ;
		else if(course.equals( "ALL" ) && !executive.equals( "ALL" ))
			return  " select "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_1 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_1='Y', "  + 
					 " 			(cdb_leads_installment_1), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_2 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_2='Y', "  + 
					 " 			(cdb_leads_installment_2), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_3 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_3='Y', "  + 
					 " 			(cdb_leads_installment_3), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_4 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_4='Y', "  + 
					 " 			(cdb_leads_installment_4), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     ) as a "  + 
					 " from "  + 
					 " 	crmlite_db.crmlite_db_leads "  + 
					 " where "  + 
					 " 	crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "  +
					 " and  " +
					 " crmlite_db.crmlite_db_leads.cdb_leads_executive = '"  + executive +  "'; " ;
		else if(!course.equals( "ALL" ) && !executive.equals( "ALL" ))
			return  " select "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_1 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_1='Y', "  + 
					 " 			(cdb_leads_installment_1), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_2 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_2='Y', "  + 
					 " 			(cdb_leads_installment_2), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_3 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_3='Y', "  + 
					 " 			(cdb_leads_installment_3), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     )+  "  + 
					 " 	sum( "  + 
					 " 		if( "  + 
					 " 			crmlite_db.crmlite_db_leads.cdb_leads_due_date_4 between '" + fromDate + "' and '" + toDate + "' and cdb_leads_paid_4='Y', "  + 
					 " 			(cdb_leads_installment_4), "  + 
					 " 			0 "  + 
					 " 		) "  + 
					 "     ) as a  "  + 
					 " 	from  "  + 
					 " 	crmlite_db.crmlite_db_leads  "  + 
					 " where  "  + 
					 " 	crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories'  "  +
					 " and  " +
					 " 	crmlite_db.crmlite_db_leads.cdb_leads_executive = '"  + executive +  "'  "  +
					 " and  " +
					 " crmlite_db.crmlite_db_leads.cdb_leads_course = '"  + course +  "'; " ;
		else return null;
	}

	// getting total payment collected
	private String getTotalPaymentCollected(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_advance_fees)" +
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_advance_fees)" +
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_advance_fees)" +
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_advance_fees)" +
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


	// getting total no of full payment received
	private String getTotalNoOfFullPaymentReceived(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' " + 
			"AND " + 
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_fees = crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return  "SELECT count(*) "+
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
			return  "SELECT count(*) "+
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
			return  "SELECT count(*) "+
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

	// getting total payment pending
	private String getTotalPaymentPending(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees - crmlite_db.crmlite_db_leads.cdb_leads_advance_fees) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees - crmlite_db.crmlite_db_leads.cdb_leads_advance_fees) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees - crmlite_db.crmlite_db_leads.cdb_leads_advance_fees) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'success-stories' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_advance_paid_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_quoted_fees - crmlite_db.crmlite_db_leads.cdb_leads_advance_fees) "+
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

	// getting total payment in pipeline
	private String getTotalPaymentInPipeline(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'pipeline';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'pipeline' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'pipeline' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT sum(crmlite_db.crmlite_db_leads.cdb_leads_proposed_fees) "+
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



	// getting total no of leads called
	private String getTotalNoOfLeadsCalled(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status not in (\"\") "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status not in (\"\") "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status not in (\"\") "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
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


	// getting total no of demo scheduled
	private String getTotalNoOfDemoScheduled(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_demo_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_demo_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_demo_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
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


	// getting total no of demo lined up
	private String getTotalNoOfDemoLinedUp(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'demo-scheduled' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
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


	// getting total no of customer lost
	private String getTotalNoOfCustomerLost(String fromDate, String toDate, String course, String executive) {
		if(course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'customer-lost' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "';";
		else if(!course.equals("ALL") && executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'customer-lost' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else if(course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'customer-lost' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "';";
		else if(!course.equals("ALL") && !executive.equals("ALL"))
			return "SELECT count(*) "+
			"FROM "+
			"crmlite_db.crmlite_db_leads "+
			"WHERE "+
			"crmlite_db.crmlite_db_leads.cdb_leads_status = 'customer-lost' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_add_date between '" + fromDate + "' and '" + toDate + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_executive = '" + executive + "' "+
			"AND "+
			"crmlite_db.crmlite_db_leads.cdb_leads_course = '" + course + "';";
		else return null;
	}

	/*
	 * Get all courses (ids and names) for particular sales_exec(id) from database
	 */
	
	private ArrayList<String[]> getCourses(HttpServletRequest request, String salesExecId) throws Exception {
		
		String query = "";
	
		query = "SELECT distinct cdb_course_name, cdb_course_name\r\n" + 
					"FROM crmlite_db.crmlite_db_course_mst INNER JOIN crmlite_db.crmlite_db_exec_ass\r\n" + 
					"ON crmlite_db.crmlite_db_course_mst.cdb_course_name = crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_course\r\n" + 
					"WHERE crmlite_db.crmlite_db_exec_ass.cdb_exec_ass_exec_id = '" + salesExecId + "';";
	
//		System.out.println(query);
		return databaseManager.executeSelectQuery(query);
	}
	
	private ArrayList<String[]> getCourses(HttpServletRequest request) throws Exception {
		
		String query = "";
		
		query = "SELECT `cdb_course_name`, `cdb_course_name` FROM `crmlite_db`.`crmlite_db_course_mst`";
		
//		System.out.println(query);
		return databaseManager.executeSelectQuery(query);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		databaseManager = (DatabaseManager) request.getSession().getAttribute("DATABASE_OBJECT");
		
		try {
			String fromDate = request.getParameter("from-date");
			String toDate = request.getParameter("to-date");
			String course = request.getParameter("course");
			String executive = request.getParameter("executive");
			
			ArrayList<String[]> successStories = databaseManager.executeSelectQuery(getCountOfSuccessStories(fromDate, toDate, course, executive));
			ArrayList<String[]> totalGrossRevenue = databaseManager.executeSelectQuery(getTotalGrossRevenue(fromDate, toDate, course, executive));
			ArrayList<String[]> totalPmtCollected = databaseManager.executeSelectQuery(getTotalPaymentCollected(fromDate, toDate, course, executive));
			ArrayList<String[]> totalNoOfFullPmtReceived = databaseManager.executeSelectQuery(getTotalNoOfFullPaymentReceived(fromDate, toDate, course, executive));
			ArrayList<String[]> totalPmtPending = databaseManager.executeSelectQuery(getTotalPaymentPending(fromDate, toDate, course, executive));
			ArrayList<String[]> totalPmtPipeline = databaseManager.executeSelectQuery(getTotalPaymentInPipeline(fromDate, toDate, course, executive));
			ArrayList<String[]> totalLeadsCalled = databaseManager.executeSelectQuery(getTotalNoOfLeadsCalled(fromDate, toDate, course, executive));
			ArrayList<String[]> totalDemoScheduled = databaseManager.executeSelectQuery(getTotalNoOfDemoScheduled(fromDate, toDate, course, executive));
			ArrayList<String[]> totalCustomerLost = databaseManager.executeSelectQuery(getTotalNoOfCustomerLost(fromDate, toDate, course, executive));
			ArrayList<String[]> totalCountOfLeadsAssigned = databaseManager.executeSelectQuery(getCountOfLeadsAssigned(fromDate, toDate, course, executive));
			ArrayList<String[]> totalCountOfDNRs = databaseManager.executeSelectQuery(getCountOfDNRs(fromDate, toDate, course, executive));
			ArrayList<String[]> totalDemoLinedUp = databaseManager.executeSelectQuery(getTotalNoOfDemoLinedUp(fromDate, toDate, course, executive));
			ArrayList<String[]> courses;
			if(executive.equals("ALL"))
				courses = getCourses(request);
			else
				courses = getCourses(request, executive);
			
			String[] everythingTogether = {
					successStories.get(0)[0],
					totalPmtCollected.get(0)[0],
					totalPmtPending.get(0)[0],
					totalPmtPipeline.get(0)[0],
					totalLeadsCalled.get(0)[0],
					totalDemoScheduled.get(0)[0],
					totalCustomerLost.get(0)[0],
					totalCountOfLeadsAssigned.get(0)[0],
					totalGrossRevenue.get(0)[0],
					totalNoOfFullPmtReceived.get(0)[0],
					totalCountOfDNRs.get(0)[0],
					totalDemoLinedUp.get(0)[0]
			};
			
			ArrayList<String[]> data = new ArrayList();
			data.add(everythingTogether);
			data.add(new String[] {"ALL", "ALL"});
			try {
				for(int i = 0; i < courses.size(); i++)
					data.add(courses.get(i));
			} catch(Exception e) {}
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for(int i = 0; i < data.size(); i++) {
				jsonArray.put(Arrays.asList(data.get(i)));
			}
			jsonObject.put("dashboardCounts", jsonArray);
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

}
