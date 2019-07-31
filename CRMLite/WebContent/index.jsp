<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Dancing+Script" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- external link -->
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/dashboard-style.css">
    <link rel="stylesheet" type="text/css" href="css/right-pane.css">
    <title>CRMLite</title>
</head>
<body>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
          <div class="navbar-header">
            <a class="navbar-brand" href="#">Coding Superstar</a>
          </div>
          <ul class="nav navbar-nav" style="float: right">
            <li class="active"><a href="#">Dashboard</a></li>
            <li><a href="lead-data">Leads</a></li>
            <li onclick="toggleSideNav(<%=request.getSession().getAttribute("USER_ID") %>)"><a><i class="fa fa-reorder"></i></a></li>
          </ul>
        </div>
      </nav>  
    
    <!-- Reminder popup -->
    <div class="container-fluid card" id="popupcard" style="display: none;">
       <div class="row">
           <div class="col-md-12 popupDisplay">
              <p style="display:none" id="reminderPopupPK"></p> 
              <p id="reminderPopupText"></p> 
<!--               <div class="form-group"> -->
<!--                     <label for="status">Snooze</label> -->
<!--                     <select class="form-control" name="lead-course" id="snoozId"> -->
<!--                         <option value="15">15 min</option> -->
<!--                         <option value="30">30 min</option> -->
<!--                     </select> -->
<!--                 </div> -->
                <div class="form-group">
                    <input type="submit" class="btn btn-primary" onclick="updateTime()">
                </div>
           </div>
       </div>
   </div>
      
      
      <div class="container-fluid">
        <div class="row">
            <div class="col-lg-2 sideNav">
                <ul>
                	<li style="text-decoration: none;" onclick="businesDevelopmentExecform(this)">Leaderboard</li>
                    <li style="text-decoration: none;" onclick="businesDevelopmentExecform(this)">Revenue</li>
                    <li style="text-decoration: none;" onclick="businesDevelopmentExecform(this)">Lead</li>
                </ul>
            </div>
            <div class="col-lg-10">
            	<div id="leaderboard-id">
			        <div class="row">
			            <div class="col-md-12" style="display:none">
			                <h1>Weekly</h1> 
			                <table class="table">
			                	<thead>
			                		<th>Rank</th>
			                		<th>Name</th>
			                		<th>Target</th>
			                		<th>Achieved</th>
			                		<th>Gross</th>
			                	</thead>
			                	<tbody id="weekly">
			                	
			                	</tbody>
			                </table>   
			            </div>
			            <div class="col-md-12">
			            	<h1>Monthly</h1>
			            	<table class="table">
			                	<thead>
			                		<th>Rank</th>
			                		<th>Name</th>
			                		<th>Target</th>
			                		<th>Achieved</th>
			                		<th>Gross</th>
			                	</thead>
			                	<tbody id="monthly">
			                	
			                	</tbody>
			                </table>
			            </div>
			        </div>
			        </div>
                <!-- business development executive  -->
                <div id="bde-forms-section" style="display:none;">
                    <!-- revenue -->
                        <h1 class="bdheading"><%=request.getSession().getAttribute("USER_FULL_NAME") %>'s Dashboard</h1>
                        <h5 class="bdsubheading" id="bdsubheading">Click options on the left pane to see data</h5>
                        
                         <table style="margin: 0px auto;">
	                         <thead>
			                    <%if (request.getSession().getAttribute("USER_ROLE").equals("admin")) { %>
			                    	<th style="cursor: pointer;text-decoration: none;background: #d6d6c8c2;padding: 10px;" onclick="getLeadExtractionCount()">Show Lead-Extraction Report for the below date-range</th>
			                    <%} %>
	                         </thead>
                         </table>
                         
                        <div class="filter-group">
                            
                            <!-- Executive selector -->
                            <%if(request.getSession().getAttribute("USER_ROLE").equals("admin")) { %>
                            <div class="form-group">
                                <label for="course">Executive</label>
                                <select class="form-control" name="business-executive-revenue-name" id="business-executive-revenue-nameId" onInput="sendAjaxForGettingCurrentDashboardStats(this)"  style="width: 120px;">
	                                <option value="ALL">ALL</option>
	                                <%for(String[] execRow: (ArrayList<String[]>)request.getAttribute("EXEC_DATA")) { 
	                                	if(execRow[3].equals("admin")) continue;%>
	                                <option value="<%= execRow[0] %>">
	                                    <%= execRow[4] %>
	                                </option>
	                                <%} %>
                                </select>
                            </div>
                            <%} else if(request.getSession().getAttribute("USER_ROLE").equals("bdm")) { %>
                            <div class="form-group">
                                <label for="course">Executive</label>
                                <select class="form-control" name="business-executive-revenue-name" id="business-executive-revenue-nameId" onInput="sendAjaxForGettingCurrentDashboardStats(this)"  style="width: 120px;">
<!-- 	                                <option value="ALL">ALL</option> -->
	                                <%for(String[] execRow: (ArrayList<String[]>)request.getAttribute("EXEC_DATA")) { 
	                                	if(execRow[3].equals("admin")) continue;%>
	                                <option value="<%= execRow[0] %>">
	                                    <%= execRow[4] %>
	                                </option>
	                                <%} %>
                                </select>
                            </div>
                            <%} else if(request.getSession().getAttribute("USER_ROLE").equals("bde")) { %>
                            <div class="form-group" style="display:none">
                                <label for="course">Executive</label>
                                <input class="form-control" value=<%=request.getSession().getAttribute("USER_ID") %> name="business-executive-revenue-name" id="business-executive-revenue-nameId">
                            </div>
                            <%} %>
                            
                            <!-- Course selector -->
                            <div class="form-group">
                                <label for="course">Course</label>
                                <select class="form-control" name="business-executive-revenue-courses" id="business-executive-revenue-courseId" onInput="sendAjaxForGettingCurrentDashboardStats(this)"  style="width: 150px;">
	                                <option value="ALL">ALL</option>
	                                <%for(String[] coursesRow: (ArrayList<String[]>)request.getAttribute("COURSE_DATA")) { %>
	                                <option value="<%= coursesRow[1] %>">
	                                    <%= coursesRow[1] %>
	                                </option>
	                                <%} %>
                                </select>
                            </div>
                            
                            <!-- Date selector -->
                            <div class="form-group">
                                <label for="course">From date</label>
                                <input name="business-executive-revenue-from" type="date" class="form-control" id="business-executive-revenue-from" onInput="sendAjaxForGettingCurrentDashboardStats(this)">
                            </div>
                            
                            <!-- Date selector -->
                            <div class="form-group">
                                <label for="course">To date</label>
                                <input name="business-executive-revenue-to" type="date" class="form-control" id="business-executive-revenue-to" onInput="sendAjaxForGettingCurrentDashboardStats(this)">
                            </div>
                            
                        </div>
                        
                    <div style="display:none;" id="bde-revenue">
                        <table class="table table-bordered table-hover" style="width: 60%; margin: 20px auto;">
                            <thead>
                            </thead>
                            <tbody id="dashboard-revenue">
                            </tbody>
                        </table>
                    </div>
                    <!-- lead -->
                    <div style="display: none;" id="bde-lead">
                            <div>
                            <table class="table table-bordered table-hover" style="width: 60%; margin: 20px auto;">
	                            <thead>
	                            </thead>
	                            <tbody id="dashboard-lead">
	                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        	</div>
			
			<!-- The Right Reminder Pane -->
			<div class="col-lg-12 rightCol">
                <div class="reminder-section" style="display:none;">
                    <div class="displayReminer">
                        <div class="display">
                        </div>
                    </div>
                    <h3 class="bdsubheading" style="padding-bottom: 0">Add Reminder</h3>
                    <div class="addReminer">
                        <input type="date" name="addReminer-date" id="addReminer-dateId" class="reminderField reset">
                        <input type="time" name="addReminer-time" id="addReminer-timeId" class="reminderField reset">
                        <textarea cols="30" rows="2" class="reminderField reset" id="addReminer-descId" maxlength="200" placeholder="Write your reminder here in 200 characters...">

                        </textarea>
                        <input type="submit" value="submit" class="reminderField" onclick="addreminer(<%=request.getSession().getAttribute("USER_ID") %>)">
                    </div>
                </div>
            </div>
		</div>	
    </div>
      
	
    <script src="js/dashboard-data-fetcher.js"></script>
    <script src="js/main.js"></script>
    <script>
    	sessionStorage.setItem("logged_in_role", '<%=request.getSession().getAttribute("USER_ROLE")%>');
    	sendAjaxForGettingLeaderboardData();
    </script>
</body>
</html>