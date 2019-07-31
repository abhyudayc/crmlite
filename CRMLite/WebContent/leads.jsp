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

    <!-- external link -->
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
            	<% if (request.getSession().getAttribute("USER_ROLE").equals("admin")) { %>
	            	<li style="position: relative; display: block; padding: 10px 15px;">
	            		<select id="re-assign-to" onchange="reassign()" style="display:none">
	            			<option disabled selected>-- Reassign to --</option>
	                      <% try {
	                      	for(String[] execRow: (ArrayList<String[]>)request.getAttribute("EXEC_DATA")) { 
		                      	if(execRow[3].equals("admin") || execRow[3].equals("l-x")) continue;%>
		                      	<option value="<%= execRow[0] %>">
		                          <%= execRow[4] %>
		                      	</option>
	                      <%} %>
	                      <%} catch(Exception e){} %>
	                     </select>
	            	</li>
            	<% } %>
	            <li style="position: relative; display: block; padding: 10px 15px;">
					<input class="ui search" id="global-lead-searchbox" type="text" placeholder="Search leads.." name="global-lead-search">
				    <button onclick="getGlobalLeads()"><i class="fa fa-search"></i></button>
				</li>
                <li><a href="home">Dashboard</a></li>
                <li class="active"><a href="lead-data">Leads</a></li>
                <li id="reminder-opener" onclick="toggleSideNav(<%=request.getSession().getAttribute("USER_ID") %>)"><a><i class="fa fa-reorder"></i></a></li>
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
                    <input onkeypress="return blockSpecialChar(event)" type="submit" class="btn btn-primary" onclick="updateTime()">
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
                <input onkeypress="return blockSpecialChar(event)" type="date" name="addReminer-date" id="addReminer-dateId" class="reminderField reset">
                <input onkeypress="return blockSpecialChar(event)" type="time" name="addReminer-time" id="addReminer-timeId" class="reminderField reset">
                <textarea cols="30" rows="2" class="reminderField reset" id="addReminer-descId" maxlength="200" placeholder="Write your reminder here in 200 characters...">

                </textarea>
                <input onkeypress="return blockSpecialChar(event)" type="submit" value="submit" class="reminderField" onclick="addreminer(<%=request.getSession().getAttribute("USER_ID") %>)">
            </div>
        </div>
    </div>
    
    	
    <button class="fa fa-plus btn btn-primary" id="add-lead-btn" onclick="addleadformdisplayer()" style="bottom: 10px;right: 10px;position: fixed;display: block;border-radius: 30%;padding: 15px;"></button>
    
    <div class="container-fluid">
        <div class="row formsec">
  			<button class="btn btn-primary btn-menu" id="switch-view-btn" onClick="switchView()" style="display:none">Switch View</button>
  			
            <button class="btn btn-success btn-menu" id="hot-lead-btn" onclick="hotLeadDisplaySwitcher(this)">Hot leads</button>
            <button class="btn btn-primary btn-menu" id="reassign-button" onClick="checkboxDisplayToggle()" style="display:none">Reassign</button>
  			
            <form action="add-lead" method="" id="formid" onsubmit="formDataSubmit()">
            
  				<input onkeypress="return blockSpecialChar(event)" name="pk-of-record" id="pk-of-record" style="display: none;">
                
                <div class="filtercls">
                    <div class="form-group" id="fetch_lead_from_date">
                        <label for="filterdate">From Date</label>
                        <input onkeypress="return blockSpecialChar(event)" name="lead-add-date" type="date" class="form-control" id="filterdate" placeholder="Enter Date"
                            onInput="edValueKeyPress()">
                    </div>
                    
                    <div class="form-group" id="fetch_lead_to_date">
                        <label for="filterdateTo">To Date</label>
                        <input onkeypress="return blockSpecialChar(event)" name="lead-to-date" type="date" class="form-control" id="filterdateTo" placeholder="Enter Date"
                            onInput="edValueKeyPress()">
                    </div>

                    <div class="form-group">
                        <label for="status">Courses</label>
                        <select class="form-control" name="lead-course" id="courses-dropdown" onInput="edValueKeyPress()">
                        	<%String courses="";
                        	for(String[] coursesRow: (ArrayList<String[]>)request.getAttribute("COURSE_DATA")) {
                        		courses += coursesRow[1] + "_";
                        	} 
                        	courses = courses.substring(0, courses.length()-1);%>
                            <option value="<%= courses%>">ALL</option>
                            <%for(String course: courses.split("_")) { %>
                            <%System.out.println("courses dropdown = " + course);%>
                            <option value="<%= course %>">
                                <%= course %>
                            </option>
                            <%} %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="status">Location</label>
                        <select class="form-control" name="lead-location" id="dropdownTwo" onInput="edValueKeyPress()">
                        	<%String locations="";
                        	for(String[] locationRow: (ArrayList<String[]>)request.getAttribute("LOCATION_DATA")) {
                        		locations += locationRow[1] + "_";
                        	} 
                        	locations = locations.substring(0, locations.length()-1);%>
                            <option value="<%= locations%>">ALL</option>
                            <%for(String location: locations.split("_")) { %>
                            <option value="<%= location %>">
                                <%= location %>
                            </option>
                            <%} %>
                        </select>
                    </div>
                     <div class="form-group" style="display: none"> 
                         <label for="status">Sublocation</label> 
                         <select class="form-control" name="lead-sublocation" id="dropdownthree" onInput="edValueKeyPress()"> 
                         	<%String sublocations="";
                         	for(String[] sublocationRow: (ArrayList<String[]>)request.getAttribute("SUBLOCATION_DATA")) {
                         		sublocations += sublocationRow[1] + "_";
                         	} 
                         	sublocations = sublocations.substring(0, sublocations.length()-1);%> 
                             <option value="ALL">ALL</option> 
                             <%for(String sublocation: sublocations.split("_")) { %> 
                             <option value="<%= sublocation %>"> 
                                 <%= sublocation %> 
                             </option> 
                             <%} %>
                         </select>
                     </div>
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select class="form-control" name="lead-status-dropdown" id="lead-status-dropdown" onInput="edValueKeyPress()">
                            <option value="ALL">ALL</option>
                            <option value="pre-sales-prospect">Pre-sales Prospect</option>
                            <option value="">No status</option>
                            <option value="not-intrested">Not Interested</option>
                            <option value="DNR">DNR</option>
                            <option value="other-course">Other Course</option>
                            <option value="demo-scheduled">Demo Scheduled</option>
                            <option value="pipeline">Pipeline</option>
                            <option value="success-stories">Customer Acquired</option>
                            <option value="follow-up">Follow up</option>
                            <option value="customer-lost">Customer lost</option>
                            <option value="reminder">Reminder</option>
                            <option value="other-status">Other</option>
                        </select>
                    </div>
                    <div class="form-group" id="quick-filter" style="display:none">
                        <label for="status"  style="display:none">Quick filter</label>
                        <input style="display:none" type="input" id="searchLeadTable" class="form-control" placeholder="Search within the table.."> 
                    </div>
                </div>

                <div id="add-new-lead-form" style="display:none;">
                    <div class="form-group">
                        <label for="name">Name</label>
                        <input onkeypress="return blockSpecialChar(event)" name="lead-name" type="text" class="form-control" id="name" placeholder="Enter name" required maxlength="45">
                    </div>
                    <div class="form-group">
                        <label for="mobile">Mobile No</label>
                        <input list="phone-numbers" onkeypress="return blockSpecialChar(event)" oninput="fillUpList(this)" name="lead-mobile" type="text" class="form-control" id="mobile" placeholder="Enter mobile No" required maxlength="13">
                        	<datalist id="phone-numbers">
						  	</datalist>
                    </div>
                    <div class="form-group">
                        <label for="email">Email address</label>
                        <input onkeypress="return blockSpecialChar(event)" name="lead-email" type="email" class="form-control" id="email" placeholder="Enter email" maxlength="30">
                    </div>
                    <div class="form-group">
                        <label for="training">Mode of training</label>
                        <select name="lead-training" class="form-control" id="training">
                            <option value="">--Select--</option>
                            <option value="classroom">Classroom</option>
                            <option value="online">Online</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select name="lead-status" class="form-control" id="status" onclick="myfun()">
                            <option value="">--Select--</option>
                            <option value="pre-sales-prospect">Pre-sales Prospect</option>
                            <option value="not-intrested">Not intrested</option>
                            <option value="DNR">Did Not Respond</option>
                            <option value="other-course">Looking for other course</option>
                            <option value="demo-scheduled">Demo scheduled</option>
                            <option value="pipeline">Pipeline</option>
                            <option value="success-stories">Customer Acquired</option>
                            <option value="follow-up">Follow up</option>
                            <option value="customer-lost">Customer lost</option>
                            <option value="reminder">Reminder</option>
                            <option value="other-status">Other (Details in Remarks)</option>
                        </select>
                    </div>
                    <!-- other course -->
                    <div class="form-group" style="display: none;" id="course">
                        <label for="name">Name of the course</label>
                        <input onkeypress="return blockSpecialChar(event)" id="otherCourseName" name="lead-course" type="text" class="form-control" placeholder="Name of the course">
                    </div>

                    <!-- demo scheduled -->
                    <div class="form-group" style="display: none;" id="demoDate">
                        <label for="name">Date for the scheduled demo</label>
                        <input onkeypress="return blockSpecialChar(event)" id="dateOfDemo" name="lead-demo-date" type="date" class="form-control" placeholder="Date" maxlength="12">
                    </div>
                    <div class="form-group" style="display: none;" id="demoTime">
                        <label for="name">Time for the scheduled demo</label>
                        <input onkeypress="return blockSpecialChar(event)" id="timeOfDemo" name="lead-demo-time" type="time" class="form-control" placeholder="Time" maxlength="12">
                    </div>
                    <div class="form-group" style="display: none;" id="demotrainername">
                        <label for="demotrainername">Name of the trainer</label>
                        <input onkeypress="return blockSpecialChar(event)" id="demoTrainer" name="lead-trainer" type="text" class="form-control" placeholder="Name of the trainer" maxlength="45">
                    </div>

                    <!-- pipeline -->
                    <div class="form-group" style="display: none;" id="followup">
                        <label for="name">Next follow up date</label>
                        <input onkeypress="return blockSpecialChar(event)" id="followupdate" name="lead-followup-date" type="date" class="form-control" placeholder="Next follow up date" maxlength="12">
                        <label for="name">Follow up time</label>
                        <input onkeypress="return blockSpecialChar(event)" id="followuptime" name="lead-followup-time" type="time" class="form-control" placeholder="Follow up time" maxlength="12">
                    </div>
                    <div class="form-group" style="display: none;" id="expectdate">
                        <label for="expectdate">Expected date of the closure</label>
                        <input onkeypress="return blockSpecialChar(event)" id="expectedclosuredate" name="lead-expected-closure-date" type="date" class="form-control" placeholder="Expected date of the closure" maxlength="12">
                    </div>

                    <!-- success stories -->
                    <div class="form-group" style="display: none;" id="quotedfees">
                        <label for="name">Closure amount</label>
                        <input onkeypress="return blockSpecialChar(event)" id="quotedfeesInput" name="lead-quoted-fees" type="number" class="form-control" placeholder="Quoted fees" maxlength="7">
                    </div>
                    <div style="display:none">
	                    <div class="form-group" style="display: none;" id="advancefees">
	                        <label for="name">Collected fee</label>
	                        <input onkeypress="return blockSpecialChar(event)" id="advancefeesInput" name="lead-advance-fees" type="number" class="form-control" placeholder="Collected fees" maxlength="7">
	                    </div>
	                    <div class="form-group" style="display: none;" id="advancepaiddate">
	                        <label for="name">Fees Paid Date</label>
	                        <input onkeypress="return blockSpecialChar(event)" id="advancepaiddateInput" name="lead-advance-paid-date" type="date" class="form-control" placeholder="Fees paid date" maxlength="12">
	                    </div>
                    </div>
                    <div class="form-group" style="display: none;" id="installment">
                        <label for="name">Installment Amount 1</label>
                        <input onkeypress="return blockSpecialChar(event)" id="leadInstallAmt1" name="lead-install-amt1" type="number" class="form-control" placeholder="Installment Amount 1" maxlength="12">
                        <label for="name">Due Date 1</label>
                        <input onkeypress="return blockSpecialChar(event)" id="leadDueDate1" name="lead-due-date1" type="date" class="form-control" placeholder="Due Date 1" maxlength="10">
                        <div class="checkbox">
						  <label><input type="checkbox" value="N" id="instlPaid_1" name="instlPaid_1" onchange="if(this.value=='N'){this.value='Y';}else{this.value='N'}">Paid</label>
						</div>
                        <label for="name">Installment Amount 2</label>
                        <input onkeypress="return blockSpecialChar(event)" id="leadInstallAmt2" name="lead-install-amt2" type="number" class="form-control" placeholder="Installment Amount 2" maxlength="12">
                        <label for="name">Due Date 2</label>
                        <input onkeypress="return blockSpecialChar(event)" id="leadDueDate2" name="lead-due-date2" type="date" class="form-control" placeholder="Due Date 2" maxlength="10">
                    	<div class="checkbox">
  						<label><input type="checkbox" value="N" id="instlPaid_2" name="instlPaid_2" onchange="if(this.value=='N'){this.value='Y';}else{this.value='N'}">Paid</label>
						</div>
						 <label for="name">Installment Amount 3</label>
                        <input onkeypress="return blockSpecialChar(event)" id="leadInstallAmt3" name="lead-install-amt3" type="number" class="form-control" placeholder="Installment Amount 3" maxlength="12">
                        <label for="name">Due Date 3</label>
                        <input onkeypress="return blockSpecialChar(event)" id="leadDueDate3" name="lead-due-date3" type="date" class="form-control" placeholder="Due Date 3" maxlength="10">
                    	<div class="checkbox">
  						<label><input type="checkbox" value="N" id="instlPaid_3" name="instlPaid_3" onchange="if(this.value=='N'){this.value='Y';}else{this.value='N'}">Paid</label>
						</div>
						 <label for="name">Installment Amount 4</label>
                        <input onkeypress="return blockSpecialChar(event)" id="leadInstallAmt4" name="lead-install-amt4" type="number" class="form-control" placeholder="Installment Amount 4" maxlength="12">
                        <label for="name">Due Date 4</label>
                        <input onkeypress="return blockSpecialChar(event)" id="leadDueDate4" name="lead-due-date4" type="date" class="form-control" placeholder="Due Date 4" maxlength="10">
                    	<div class="checkbox">
  						<label><input type="checkbox" value="N" id="instlPaid_4" name="instlPaid_4" onchange="if(this.value=='N'){this.value='Y';}else{this.value='N'}">Paid</label>
						</div>
                    </div>

                    <!-- Customer Lost -->
                    <div class="form-group" style="display:none" id="customer-lost-story">
                        <label for="status">Reason why customer is lost</label>
                        <select name="lead-lost-reason" class="form-control" id="customerlostdropdown" onclick="myanotherfun()">
                            <option value="">--Select--</option>
                            <option value="price-factor">Price factor</option>
                            <option value="trainer-issue">Trainer issue</option>
                            <option value="not-picking-call">Customer is not picking call</option>
                            <option value="demo-did-not-happen-on-time">Demo didn't happen on time</option>
                            <option value="other">Other (Details in Remarks)</option>
                        </select>
                    </div>
                    <div class="form-group" style="display: none;" id="pricelost">
                        <label for="pricelost">Price at which the customer is lost</label>
                        <input onkeypress="return blockSpecialChar(event)" id="priceLostAt" name="lead-lost-price" type="number" class="form-control" placeholder="Enter price at which the customer lost" maxlength="7">
                    </div>
                    <div class="form-group" style="display: none;" id="nameinst">
                        <label for="nameinst">Name of the institute</label>
                        <input onkeypress="return blockSpecialChar(event)" id="priceLostInst" name="lead-lost-institute" type="text" class="form-control" placeholder="Name of the institute" maxlength="20">
                    </div>
                    <div class="form-group" style="display: none;" id="trainername">
                        <label for="trainername">Name of the trainer</label>
                        <input onkeypress="return blockSpecialChar(event)" id="trainer" name="lead-trainer" type="text" class="form-control" placeholder="Name of the trainer" maxlength="45">
                    </div>

                    <!-- proposed fees -->
                    <div class="form-group">
                        <label for="fees">Proposed fees</label>
                        <input onkeypress="return blockSpecialChar(event)" name="lead-proposed-fees" type="text" class="form-control" id="fees" placeholder="Enter fees" maxlength="7">
                    </div>
                    <div class="form-group">
                        <label for="remarks">Remarks</label>
                        <input onkeypress="return blockSpecialChar(event)" name="lead-remarks" type="text" class="form-control" id="remarks" placeholder="Enter remarks" maxlength="100">
                    </div>
                    <div class="form-group" id="hot-lead-checkbox-div" style="display: flex; align-items: flex-end;">
                    	<input style="height: 20px;width: 20px;margin-right: 8px;" name="hot-lead" type="checkbox" id="hot-lead" value="hot-lead">Hot Lead
                    </div>
                    <div class="form-group">
                    	<button type="submit" id="submit-new-lead" class="btn btn-primary">Submit</button>
                    </div>
                </div>
            </form>
            <div style="display: inline-flex;">
	            <button id="show-prev-lead" class="btn btn-primary" style="display: none; margin-left: 10px;" onclick="fetchNextLead()" disabled>Previous</button>
	            <button id="update-lead" class="btn btn-primary" style="display: none; margin-left: 10px;" onclick="updateLead()">Update</button>
	            <button id="show-next-lead" class="btn btn-primary" style="display: none; margin-left: 10px;" onclick="fetchPrevLead()" disabled>Next</button>
            </div>
        </div>
    </div>


    <!-- table form -->

    <div class="ctable" id="ctable">
        <table class="table table-condensed table-striped  table-hover" style="">
            <thead>
                <tr>
                	<th style="display:none">
                		<input type="checkbox" onClick="toggleChkbox(this)">
                	</th>
                    <%String[] header = ((ArrayList<String[]>)request.getAttribute("LEAD_DATA")).get(0);
                    ArrayList<Integer> excludeIndexes = new ArrayList<Integer>();
                    for(int i = 1; i < header.length; i++) { 
                    	//if(header[i].contains("location") || header[i].contains("course") || header[i].contains("add_date")) {
                    		//excludeIndexes.add(i);
                    	//} else {
                    	%>
                    <th>
                        <%=header[i].replaceAll("_", " ").substring(10) %>
                    </th>
                    <%//}
                    }%>
                </tr>
            </thead>
            <tbody id="ctable-body">
            </tbody>
        </table>
        
    </div>
	<button class="btn btn-danger btn-menu view_more" id="view-more-btn" onClick="viewMoreRows()">View more</button>

    <script src="js/main.js"></script>
	<script src="js/hot-lead-data-fetcher.js"></script>
	<script src="js/global-search.js"></script>
    <script>
	    $(document).ready(function(){
	      $("#searchLeadTable").on("keyup", function() {
	        var value = $(this).val().toLowerCase();
	        $("#ctable-body tr").filter(function() {
	          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	        });
	      });
	    });
	    
	    $(function(){
    	  $('#mobile').bind('input', function(){
    	    $(this).val(function(_, v){
    	      return v.replace(/\s+/g, '');
    	    });
    	  });
    	});
    </script>
    <script>
    	function toggleChkbox(source) {
    		checkboxes = document.getElementsByName('lead-chkbox');
    		for(var i=0, n=checkboxes.length;i<n;i++) {
    	    	checkboxes[i].checked = source.checked;
    	  	}
    	}
    </script>
</body>

</html>