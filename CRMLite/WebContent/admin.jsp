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
    <link rel="stylesheet" type="text/css" href="css/dashboard-style.css">
    <link rel="stylesheet" type="text/css" href="css/right-pane.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>CRMLite</title>
</head>

<body>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Coding Superstar</a>
            </div>
            <ul class="nav navbar-nav" style="float: right">
                <li><a href="home">Dashboard</a></li>
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

	<!-- The Right Reminder Pane -->
	<div class="col-lg-12 rightCol">
        <div class="reminder-section" style="display:none;">
            <div class="displayReminer">
                <div class="display">
                </div>
            </div>
            <h3 class="bdsubheading" style="padding-bottom: 0">Add Remidner</h3>
            <div class="addReminer">
                <input type="date" name="addReminer-date" id="addReminer-dateId" class="reminderField reset">
                <input type="time" name="addReminer-time" id="addReminer-timeId" class="reminderField reset">
                <textarea cols="30" rows="2" class="reminderField reset" id="addReminer-descId" maxlength="200" placeholder="Write your reminder here in 200 characters...">

                </textarea>
                <input type="submit" value="submit" class="reminderField" onclick="addreminer(<%=request.getSession().getAttribute("USER_ID") %>)">
            </div>
        </div>
    </div>


    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-2 sideNav">
                <ul>
                    <li onclick="adminFormsShowHide(this)">Executive Deployment</li>
                    <li onclick="adminFormsShowHide(this)">Add New User</li>
                    <li onclick="adminFormsShowHide(this)">Target Assignment</li>
                </ul>
            </div>
            <div class="col-lg-10" style="float:right">

                <!-- executive course location sublocation assignment -->
                
                <div id="exec-deployment">

                    <form action="" method="POST">
                    	<div style="display:inline-block; margin-top: 0px" class="form-group">
	                    	<h1 style="margin-bottom: 0px">Executive Deployment</h1>
                        </div>
                        <div class="form-group" style="overflow-x: scroll; width:80vw; height:72vh">
                            <table class="table table-bordered table-hover" style="width: 100%; margin: 20px auto;" id="execDepTable">
                                <thead>
                                    <tr>
                                        <th>
					                        <input type="input" id="searchExecAssCourse" placeholder="Search course">
				                        </th>
		                                <%for(String[] locationRow: (ArrayList<String[]>)request.getAttribute("LOCATION_DATA")) { %>
		                                	<th><%= locationRow[1] %></th>
		                                <%} %>
                                    </tr>
                                </thead>
                                <tbody>
                                   	<%for(String[] coursesRow: (ArrayList<String[]>)request.getAttribute("COURSE_DATA")) { %>
	                               		<tr>
											<th style="text-align:left; vertical-align: middle;"><%= coursesRow[1] %></th>
											<%for(String[] locationRow: (ArrayList<String[]>)request.getAttribute("LOCATION_DATA")) { %>
			                                
												<td>
		                                            <select name="executive" class="form-control" style="border:none" onChange="changedField(this)">
				                                		<option disabled value="" selected>--Select--</option>
						                                <%for(String[] execRow: (ArrayList<String[]>)request.getAttribute("EXEC_DATA")) { 
						                                	if(execRow[3].equals("admin")) continue;%>
						                                		<option value="<%= execRow[0] %>,<%= coursesRow[1] %>,<%= locationRow[1] %>">
						                                    	<%= execRow[4] %>
						                                		</option>
						                                <%} %>
						                            </select>
		                                        </td>
	                                        <%} %>
										</tr>
	                                <%} %>
                                </tbody>
                            </table>
                        </div>

                    </form>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary" onClick="submitFormChanges()">Save</button>
                        </div>
                </div>
                
                <script>
                
                function sendAjaxForGettingExecAssignment() {
                	var ourRequest = new XMLHttpRequest();
                    ourRequest.open('GET', 'exec-assignment-data-fetcher', true);
                    ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                    console.log('ajax sent');
                    ourRequest.onload = function () {
                    	var ourData = JSON.parse(ourRequest.responseText);
                    	for(var i = 0; i < ourData.execAssData[0].length; i++) {
                    		console.log(ourData);
                    		var valueOfOption = ourData.execAssData[0][i][1] + "," + ourData.execAssData[0][i][0] + "," + ourData.execAssData[0][i][2];
                    		console.log(valueOfOption);
                    		document.querySelector('#exec-deployment > form > div:nth-child(2) > table').querySelector('option[value="' + valueOfOption + '"]').setAttribute('selected', '');
                    	}
                    }
                    ourRequest.send();
                }
                sendAjaxForGettingExecAssignment();
                
                var changedFields = [];
                function changedField(element) {
                	changedFields.push(element.options[element.selectedIndex].value);
                }
                
                function submitFormChanges() {
                	var fieldsToSubmit = [];
                	var toPush = true;
                	for(var i = changedFields.length - 1; i >= 0; i--) {
               		
                		for(var j = 0; j < fieldsToSubmit.length; j++) {
                			if(fieldsToSubmit[j].includes(changedFields[i].split(',')[1] + "," + changedFields[i].split(',')[2])) {
                				toPush = false;
                				break;
                			}
                		}
                		if(toPush)
                			fieldsToSubmit.push(changedFields[i]);
                		toPush = true;
                	}
                	
                	var form = document.createElement("form");
                    form.setAttribute("method", "POST");
                    form.setAttribute("action", "admin");

                	for (var i = 0; i < fieldsToSubmit.length; i++) {
                		console.log(fieldsToSubmit[i]);
	                    var hiddenField = document.createElement("input");
	                    hiddenField.setAttribute("type", "hidden");
	                    hiddenField.setAttribute("name", "executive");
	                    hiddenField.setAttribute("value", fieldsToSubmit[i]);
	                    form.appendChild(hiddenField);
                	}

                    document.body.appendChild(form);
                    form.submit();
                }
                </script>
                

                <!-- Add new user -->

                <div style="display: none;" id="newUserId">
                	<div style="display:inline-block; margin-top: 0px" class="form-group">
                    	<h1>Add New User</h1>
                    </div>
                    <form action="add-new-user" method="POST">
                        <div class="form-group">
                            <label for="new-user-name">Name</label>
                            <input name="add-new-user-full-name" type="text" class="form-control" id="add-new-user-nameId"
                                placeholder="Enter full name" maxlength="45">
                        </div>
                        <div class="form-group">
                            <label for="role">Role</label>
                            <select class="form-control" name="add-new-user-role" id="add-new-user-roleId">
                                <option value="bde">BDE</option>
                                <option value="bdm">BDM</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="reporting">Reporting To</label>
                            <select class="form-control" name="add-new-user-reportingto" id="add-new-user-reportingId">
                                <%for(String[] execRow: (ArrayList<String[]>)request.getAttribute("EXEC_DATA")) { %>
                                <option value="<%= execRow[0] %>">
                                    <%= execRow[4] %>
                                </option>
                                <%} %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="new-user-uname">Username</label>
                            <input name="add-new-user-uname" type="text" class="form-control" id="add-new-user-unameId"
                                placeholder="Enter username (max 20 chars)" maxlength="20">
                        </div>
                        <div class="form-group">
                            <label for="new-user-password">Password</label>
                            <input name="add-new-user-password" type="text" class="form-control" id="add-new-user-password"
                                placeholder="Enter password (max 20 chars)" maxlength="20">
                        </div>
                        <div class="form-group">
                            <input type="submit" class="btn btn-primary" value="Submit">
                        </div>
                    </form>
                    <table class="table table-bordered table-hover" style="width: 60%; margin: 20px auto;">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Role</th>
                                <th>Reporting To</th>
                                <th>Username</th>
                                <th>Password</th>
                            </tr>
                        </thead>
                        <tbody id="admin-users-table">

                        </tbody>
                    </table>
                </div>


                <!-- Target Assignment -->

                <div style="display: none;" id="target">

                    <form action="target" method="POST">
	                    <div style="display:inline-block; margin-top: 0px" class="form-group">
	                        <h1>Target Assignment</h1>
                        </div>
                        
                        <div class="form-group">
                            <label for="new-user-uname">Year - Month</label>
                            <input name="target-assignment-month" type="month" class="form-control" id="add-new-user-month" onInput='fetchTargets(this.value)' required>
                        </div>
                        
                        <div class="form-group" style="overflow-x: scroll; width:80vw">
                            <table class="table table-bordered table-hover" style="width: 100%; margin: 20px auto;">
                                <thead>
                                    <tr>
                                        <th></th>
		                                <th>Month Total</th>
		                                <th>Week 1</th>
		                                <th>Week 2</th>
		                                <th>Week 3</th>
		                                <th>Week 4</th>
                                    </tr>
                                </thead>
                                <tbody>
                                   	<%for(String[] execRow: (ArrayList<String[]>)request.getAttribute("EXEC_DATA")) {
                                   		if(execRow[3].equals("admin")) continue;%>
	                               		<tr>
											<th style="text-align:left; vertical-align: middle;"><%= execRow[4] %></th>
											<% String monthlyTargetId = "ta" + execRow[0] + "m";
											   String weeklyTargetId1 = "ta" + execRow[0] + "w1";
											   String weeklyTargetId2 = "ta" + execRow[0] + "w2";
											   String weeklyTargetId3 = "ta" + execRow[0] + "w3";
											   String weeklyTargetId4 = "ta" + execRow[0] + "w4"; %>
											<td><input name=<%= monthlyTargetId %> type="number" class="form-control" id=<%= monthlyTargetId %> placeholder="Enter Target" oninput="divideAmt(this)" maxlength="10"></td>
											<td><input name=<%= weeklyTargetId1 %> type="number" class="form-control" id=<%= weeklyTargetId1 %> oninput="getTotalAmt(this)" maxlength="8"></td>
											<td><input name=<%= weeklyTargetId2 %> type="number" class="form-control" id=<%= weeklyTargetId2 %> oninput="getTotalAmt(this)" maxlength="8"></td>
                            				<td><input name=<%= weeklyTargetId3 %> type="number" class="form-control" id=<%= weeklyTargetId3 %> oninput="getTotalAmt(this)" maxlength="8"></td>
				                        	<td><input name=<%= weeklyTargetId4 %> type="number" class="form-control" id=<%= weeklyTargetId4 %> oninput="getTotalAmt(this)" maxlength="8"></td>
										</tr>
	                                <%} %>
                                </tbody>
                            </table>
                        </div>
                        <div class="form-group">
                            <input type="submit" class="btn btn-primary" value="Save">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <script src="js/main.js"></script>
    <script>
	    $(document).ready(function(){
	      $("#searchExecAssCourse").on("keyup", function() {
	        var value = $(this).val().toLowerCase();
	        $("#execDepTable tbody tr").filter(function() {
	          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	        });
	      });
	    });
    </script>
</body>

</html>