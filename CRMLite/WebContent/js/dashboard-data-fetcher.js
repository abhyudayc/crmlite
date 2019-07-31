function sendAjaxForGettingLeaderboardData() {
	var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET', 'dashboard-details-provider', true);
    ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    document.getElementById('monthly').innerHTML = "Fetching data...";
    console.log('ajax sent');
    ourRequest.onload = function () {
    	var ourData = JSON.parse(ourRequest.responseText);
    	console.log(ourData);
    	var htmlWeek="";
    	var htmlMonth="";
    	for(var i = 0; i < ourData.leaderboard[0].length; ++i) {
    		htmlWeek += `<tr>` + 
    		`<td style="text-align: left; padding-left: 100px;">` + (i+1) + `</td>` +
    		`<td style="text-align: left; padding-left: 100px;">` + ourData.leaderboard[0][i].executiveName + `</td>` +
    		`<td style="text-align: left; padding-left: 100px;">` + ourData.leaderboard[0][i].target + `</td>` +
    		`<td style="text-align: left; padding-left: 100px;">` + ourData.leaderboard[0][i].achieved + `</td>` +
    		`<td style="text-align: left; padding-left: 100px;">` + ourData.leaderboard[0][i].gross + `</td>` +
    		`</tr>`;
    	}
    	for(var i = 0; i < ourData.leaderboard[1].length; ++i) {
    		htmlMonth += `<tr>` + 
    		`<td style="text-align: left; padding-left: 100px;">` + (i+1) + `</td>` +
    		`<td style="text-align: left; padding-left: 100px;">` + ourData.leaderboard[1][i].executiveName + `</td>` +
    		`<td style="text-align: left; padding-left: 100px;">` + ourData.leaderboard[1][i].target + `</td>` +
    		`<td style="text-align: left; padding-left: 100px;">` + ourData.leaderboard[1][i].achieved + `</td>` +
    		`<td style="text-align: left; padding-left: 100px;">` + ourData.leaderboard[1][i].gross + `</td>` +
    		`</tr>`;
    	}
    	
        document.getElementById('weekly').innerHTML = "";
        document.getElementById('weekly').insertAdjacentHTML('beforeend', htmlWeek);
        document.getElementById('monthly').innerHTML = "";
        document.getElementById('monthly').insertAdjacentHTML('beforeend', htmlMonth);
    }
    ourRequest.send();
}

function sendAjaxForGettingCurrentDashboardStats(elem) {
	var ourRequest = new XMLHttpRequest();
    ourRequest.open('POST', 'dashboard-count-keeper', true);
    ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    console.log('ajax sent');
    document.getElementById('dashboard-revenue').innerHTML = "Fetching data...";
    document.getElementById('dashboard-lead').innerHTML = "Fetching data...";
    ourRequest.onload = function () {
    	var ourData = JSON.parse(ourRequest.responseText);
    	console.log(ourData);
    	var html1 = `<tr onClick='sendAjaxForGettingCustomerAcquired()'>
                        <td>Customers Aquired</td>
                        <td>` + ourData.dashboardCounts[0][0] + `</td>
                    </tr>
                    <tr>
                        <td>Total Gross Revenue (in Rupees)</td>
                        <td>` + ourData.dashboardCounts[0][8] + `</td>
                    </tr>
                    <tr onClick='sendAjaxForGettingLeadNameForWhomPaymentIsCollected()'>
                        <td>Total Payment Received (in Rupees)</td>
                        <td>` + ourData.dashboardCounts[0][1] + `</td>
                    </tr>
                    <tr onClick='sendAjaxForGettingCustomerFullPayment()'>
                        <td># of Full Payments</td>
                        <td>` + ourData.dashboardCounts[0][9] + `</td>
                    </tr>
                    <tr onClick='sendAjaxForGettingLeadNameForWhomPaymentIsPending()'>
                        <td>Total Pending Payment (in Rupees)</td>
                        <td>` + ourData.dashboardCounts[0][2] + `</td>
                    </tr>
                    <tr onClick='sendAjaxForGettingLeadNameForWhomPaymentIsPipeline()'>
                        <td>Expected Cash in Pipeline (in Rupees)</td>
                        <td>` + ourData.dashboardCounts[0][3] + `</td>
                    </tr>`;
    	
    	var html2 = `<tr onClick='sendAjaxForGettingLeadsAssigned()'>
                        <td>Total no of Leads Assigned</td>
                        <td>` + ourData.dashboardCounts[0][7] + `</td>
                    </tr>
                    <tr onClick='sendAjaxForGettingLeadsCalled()'>
                        <td>Total no of Leads Called</td>
                        <td>` + ourData.dashboardCounts[0][4] + `</td>
                    </tr>
                    <tr>
                        <td>No of DNRs</td>
                        <td>` + ourData.dashboardCounts[0][10] + `</td>
                    </tr>
                    <tr onClick='sendAjaxForGettingLeadNameForWhomDemoIsLinedUp()'>
                        <td>Candidates lined up for Demo</td>
                        <td>` + ourData.dashboardCounts[0][11] + `</td>
                    </tr>
                    <tr onClick='sendAjaxForGettingLeadNameForWhomDemoIsScheduled()'>
                        <td>Candidates scheduled for Demo</td>
                        <td>` + ourData.dashboardCounts[0][5] + `</td>
                    </tr>
                    <tr onClick='sendAjaxForGettingCustomerAcquired()'>
                        <td>No of Candidates Closed</td>
                        <td>` + ourData.dashboardCounts[0][0] + `</td>
                    </tr>
                    <tr>
                        <td>Candidates Attended Demo</td>
                        <td>TO be done</td>
                    </tr>
                    <tr onClick='sendAjaxForGettingCustomerLost()'>
                        <td>Total no of Customer Lost</td>
                        <td>` + ourData.dashboardCounts[0][6] + `</td>
                    </tr>`;
    	
    	var html3 = ``;
    	for(var i = 1; i <ourData.dashboardCounts.length; i++) {
    		if(elem.value == ourData.dashboardCounts[i][0]) {
	        	html3 += `<option value="` + ourData.dashboardCounts[i][0] + `" selected>` +
	            		ourData.dashboardCounts[i][1] + 
	        			`</option>`
    		} else {
    			html3 += `<option value="` + ourData.dashboardCounts[i][0] + `">` +
		        		ourData.dashboardCounts[i][1] + 
		    			`</option>`
    		}
    	}
    	
        document.getElementById('dashboard-revenue').innerHTML = "";
        document.getElementById('dashboard-revenue').insertAdjacentHTML('beforeend', html1);
        document.getElementById('dashboard-lead').innerHTML = "";
        document.getElementById('dashboard-lead').insertAdjacentHTML('beforeend', html2);
        document.getElementById('business-executive-revenue-courseId').innerHTML = "";
        document.getElementById('business-executive-revenue-courseId').insertAdjacentHTML('beforeend', html3);
    }
    try {
	    console.log("from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
    } catch (x) {
    	console.log("from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
    }   
}




// getting name of leads for whom payment is collected

function sendAjaxForGettingLeadNameForWhomPaymentIsCollected() {
	var ourRequest = new XMLHttpRequest();
    ourRequest.open('POST', 'dashboard-details-provider', true);
    ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    console.log('ajax sent');
    ourRequest.onload = function () {
    	var ourData = JSON.parse(ourRequest.responseText);
    	console.log(ourData);
    	var html = "<h1>Payment Received Details</h1>"
     	html += "<table><tbody>";
     	html +=
            "<tr>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Name</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Mobile</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Course</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Total Amount</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Paid Amount</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Paid Date</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Trainer</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Installment Amount 1</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Due Date 1</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Installment Amount 2</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Due Date 2</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Executive</th>"+
    	    "</tr>";
        for(var i=0;i < ourData.paymentCollectedDetails.length;i++) {
            html+=
                "<tr>"+
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].name            +"</td>" + 
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].mobile			+"</td>" + 
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].course		  	+"</td>" + 
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].totalAmt	  	+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].feesPaidAmt   	+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].feesPaidDate   	+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].trainer		   	+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].instl1		   	+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].dueDate1	   	+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].instl2		   	+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].dueDate2	   	+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentCollectedDetails[i].executive	   	+"</td>" +
                "</tr>";
        }
        html += "</tbody></table>";
        var newWindow = window.open();
        newWindow.document.write("<html><head><title>CRMLite</title></head><body>" + html + "</body></html>");
    }
    try {
	    console.log("thing=paymentCollected&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=paymentCollected&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
    } catch (x) {
    	console.log("thing=paymentCollected&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=paymentCollected&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
    }
}





//getting names of leads for whom payment pending

function sendAjaxForGettingLeadNameForWhomPaymentIsPending() {
	var ourRequest = new XMLHttpRequest();
 ourRequest.open('POST', 'dashboard-details-provider', true);
 ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
 console.log('ajax sent');
 ourRequest.onload = function () {
 	var ourData = JSON.parse(ourRequest.responseText);
 	console.log(ourData);
	var html = "<h1>Payment Pending Details</h1>"
 	html += "<table><tbody>";
 	html +=
        "<tr>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Name</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Mobile</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Course</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Total Amount</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Pending Amount</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Installment Amount 1</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Due Date 1</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Installment Amount 2</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Due Date 2</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Executive Name</th>"+
	    "</tr>";
    for(var i=0;i < ourData.paymentPendingDetails.length;i++) {
        html+=
            "<tr>"+
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPendingDetails[i].name            +"</td>" +
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPendingDetails[i].mobile          +"</td>" +
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPendingDetails[i].course          +"</td>" +
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPendingDetails[i].totalAmt        +"</td>" + 
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPendingDetails[i].pendingAmt	  +"</td>" +
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPendingDetails[i].instl1		  +"</td>" +
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPendingDetails[i].dueDate1	  	  +"</td>" +
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPendingDetails[i].instl2		  +"</td>" +
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPendingDetails[i].dueDate2	  	  +"</td>" +
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPendingDetails[i].executive		  +"</td>" +
            "</tr>";
    }
    html += "</tbody></table>";
    var newWindow = window.open();
    newWindow.document.write("<html><head><title>CRMLite</title></head><body>" + html + "</body></html>");
 }
 try {
	    console.log("thing=paymentPending&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=paymentPending&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
 } catch (x) {
 	console.log("thing=paymentPending&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=paymentPending&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
 }
}





//getting names of leads for whom payment is in pipeline

function sendAjaxForGettingLeadNameForWhomPaymentIsPipeline() {
	var ourRequest = new XMLHttpRequest();
	ourRequest.open('POST', 'dashboard-details-provider', true);
	ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	console.log('ajax sent');
	ourRequest.onload = function () {
		var ourData = JSON.parse(ourRequest.responseText);
		console.log(ourData);

    	var html = "<h1>Payment in Pipeline Details</h1>"
     	html += "<table><tbody>";
     	html +=
            "<tr>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Name</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Mobile</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Course</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Demo Date and Time</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Trainer</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Fees Proposed</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Expected Closure Date</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Executive Name</th>"+
    	    "</tr>";
        for(var i=0;i < ourData.paymentPipelineDetails.length;i++) {
            html+=
                "<tr>"+
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPipelineDetails[i].name   		    	+"</td>" + 
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPipelineDetails[i].mobile				+"</td>" + 
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPipelineDetails[i].course				+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPipelineDetails[i].demoDate + ", " + ourData.paymentPipelineDetails[i].demoTime +"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPipelineDetails[i].trainer				+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPipelineDetails[i].proposedFees			+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPipelineDetails[i].expectedClosureDate	+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.paymentPipelineDetails[i].executiveName		+"</td>" +
                "</tr>";
        }
        html += "</tbody></table>";
        var newWindow = window.open();
        newWindow.document.write("<html><head><title>CRMLite</title></head><body>" + html + "</body></html>");	
	}
	try {
	    console.log("thing=paymentPipeline&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=paymentPipeline&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	} catch (x) {
		console.log("thing=paymentPipeline&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=paymentPipeline&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	}
}





//getting names of leads for whom payment is in pipeline

function sendAjaxForGettingCustomerAcquired() {
	var ourRequest = new XMLHttpRequest();
	ourRequest.open('POST', 'dashboard-details-provider', true);
	ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	console.log('ajax sent');
	ourRequest.onload = function () {
		var ourData = JSON.parse(ourRequest.responseText);
		console.log(ourData);

	var html = "<h1>Acquired Customer Details</h1>"
 	html += "<table><tbody>";
   	html +=
          "<tr>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Name</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Mobile</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Course</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Closed at Price</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Trainer</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Fees Collected</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Fees Pending</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Executive Name</th>"+
  	    "</tr>";
      for(var i=0;i < ourData.customerAcquired.length;i++) {
          html+=
              "<tr>"+
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerAcquired[i].name   		    	+"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerAcquired[i].mobile  		    	+"</td>" + 
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerAcquired[i].course				+"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerAcquired[i].quotedFees			+"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerAcquired[i].trainer				+"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerAcquired[i].paidFees				+"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerAcquired[i].pendingFees			+"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerAcquired[i].executiveName		+"</td>" +
              "</tr>";
      }
      html += "</tbody></table>";
      var newWindow = window.open();
      newWindow.document.write("<html><head><title>CRMLite</title></head><body>" + html + "</body></html>");	
	}
	try {
	    console.log("thing=customerAcquired&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=customerAcquired&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	} catch (x) {
		console.log("thing=customerAcquired&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=customerAcquired&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	}
}





//getting names of leads for whom demo is lined up

function sendAjaxForGettingLeadNameForWhomDemoIsLinedUp() {
	var ourRequest = new XMLHttpRequest();
	ourRequest.open('POST', 'dashboard-details-provider', true);
	ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	console.log('ajax sent');
	ourRequest.onload = function () {
		var ourData = JSON.parse(ourRequest.responseText);
		console.log(ourData);	

    	var html = "<h1>Lined Up Demo Details</h1>"
     	html += "<table><tbody>";
     	html +=
            "<tr>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Name</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Mobile</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Course</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Proposed Fees</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Demo Date / Time</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Trainer</th>"+
    	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Executive Name</th>"+
    	    "</tr>";
        for(var i=0;i < ourData.demoLinedUp.length;i++) {
            html+=
                "<tr>"+
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoLinedUp[i].name    	+"</td>" + 
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoLinedUp[i].mobile  	+"</td>" + 
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoLinedUp[i].course  	+"</td>" +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoLinedUp[i].fees		+"</td>"  +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoLinedUp[i].demoScheduledDate + ", " + ourData.demoLinedUp[i].demoScheduledTime   +"</td>"  +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoLinedUp[i].trainer   +"</td>"  +
                     "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoLinedUp[i].executiveName   +"</td>"  +
                "</tr>";
        }
        html += "</tbody></table>";
        var newWindow = window.open();
        newWindow.document.write("<html><head><title>CRMLite</title></head><body>" + html + "</body></html>");	
	}
	try {
	    console.log("thing=demoLinedUp&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=demoLinedUp&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	} catch (x) {
		console.log("thing=demoLinedUp&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=demoLinedUp&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	}
}






//getting names of leads for whom demo is scheduled

function sendAjaxForGettingLeadNameForWhomDemoIsScheduled() {
	var ourRequest = new XMLHttpRequest();
	ourRequest.open('POST', 'dashboard-details-provider', true);
	ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	console.log('ajax sent');
	ourRequest.onload = function () {
		var ourData = JSON.parse(ourRequest.responseText);
		console.log(ourData);	

  	var html = "<h1>Scheduled Demo Details</h1>"
   	html += "<table><tbody>";
   	html +=
          "<tr>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Name</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Mobile</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Course</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Proposed Fees</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Demo Date / Time</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Trainer</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Executive Name</th>"+
  	    "</tr>";
      for(var i=0;i < ourData.demoScheduled.length;i++) {
          html+=
              "<tr>"+
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoScheduled[i].name    	+"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoScheduled[i].mobile    	+"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoScheduled[i].course  	+"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoScheduled[i].fees		+"</td>"  +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoScheduled[i].demoScheduledDate + ", " + ourData.demoScheduled[i].demoScheduledTime   +"</td>"  +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoScheduled[i].trainer   +"</td>"  +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.demoScheduled[i].executiveName   +"</td>"  +
              "</tr>";
      }
      html += "</tbody></table>";
      var newWindow = window.open();
      newWindow.document.write("<html><head><title>CRMLite</title></head><body>" + html + "</body></html>");	
	}
	try {
	    console.log("thing=demoScheduled&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=demoScheduled&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	} catch (x) {
		console.log("thing=demoScheduled&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=demoScheduled&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	}
}




//getting names of leads Assigned

function sendAjaxForGettingLeadsAssigned() {
	var ourRequest = new XMLHttpRequest();
	ourRequest.open('POST', 'dashboard-details-provider', true);
	ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	console.log('ajax sent');
	ourRequest.onload = function () {
		var ourData = JSON.parse(ourRequest.responseText);
		console.log(ourData);	

  	var html = "<h1>Leads Assigned</h1>"
   	html += "<table><tbody>";
   	html +=
          "<tr>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Name</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Mobile</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Course</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Added Date</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Status</th>"+
  	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Executive Name</th>"+
  	    "</tr>";
      for(var i=0;i < ourData.leadsAssigned.length;i++) {
          html+=
              "<tr>"+
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsAssigned[i].name    	+"</td>" + 
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsAssigned[i].mobile  	+"</td>" + 
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsAssigned[i].course  	+"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsAssigned[i].addDate		+"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsAssigned[i].status	    +"</td>" +
                   "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsAssigned[i].executiveName   +"</td>"  +
              "</tr>";
      }
      html += "</tbody></table>";
      var newWindow = window.open();
      newWindow.document.write("<html><head><title>CRMLite</title></head><body>" + html + "</body></html>");	
	}
	try {
	    console.log("thing=leadsAssigned&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=leadsAssigned&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	} catch (x) {
		console.log("thing=leadsAssigned&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=leadsAssigned&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	}
}





//getting names of leads Called

function sendAjaxForGettingLeadsCalled() {
	var ourRequest = new XMLHttpRequest();
	ourRequest.open('POST', 'dashboard-details-provider', true);
	ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	console.log('ajax sent');
	ourRequest.onload = function () {
		var ourData = JSON.parse(ourRequest.responseText);
		console.log(ourData);	

	var html = "<h1>Status of Leads Called</h1>"
 	html += "<table><tbody>";
 	html +=
        "<tr>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Name</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Mobile</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Course</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Added Date</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Status</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Executive Name</th>"+
	    "</tr>";
    for(var i=0;i < ourData.leadsCalled.length;i++) {
        html+=
            "<tr>"+
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsCalled[i].name    	+"</td>" + 
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsCalled[i].mobile  	+"</td>" + 
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsCalled[i].course  	+"</td>" +
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsCalled[i].addDate		+"</td>"  +
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsCalled[i].status	    +"</td>"  +
                 "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.leadsCalled[i].executiveName   +"</td>"  +
            "</tr>";
    }
    html += "</tbody></table>";
    var newWindow = window.open();
    newWindow.document.write("<html><head><title>CRMLite</title></head><body>" + html + "</body></html>");	
	}
	try {
	    console.log("thing=leadsCalled&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=leadsCalled&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	} catch (x) {
		console.log("thing=leadsCalled&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=leadsCalled&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	}
}





//getting names of customer lost

function sendAjaxForGettingCustomerLost() {
	var ourRequest = new XMLHttpRequest();
	ourRequest.open('POST', 'dashboard-details-provider', true);
	ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	console.log('ajax sent');
	ourRequest.onload = function () {
		var ourData = JSON.parse(ourRequest.responseText);
		console.log(ourData);	

	var html = "<h1>Details of Customer Lost</h1>"
	html += "<table><tbody>";
	html +=
      "<tr>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Name</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Mobile</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Course</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lost Date</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lost Reason</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lost at Price</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lost to Institute</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Remarks</th>"+
	        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Executive Name</th>"+
	    "</tr>";
  for(var i=0;i < ourData.customerLost.length;i++) {
      html+=
          "<tr>"+
               "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerLost[i].name    	+"</td>" + 
               "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerLost[i].mobile  	+"</td>" + 
               "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerLost[i].course  	+"</td>" +
               "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerLost[i].lostDate	+"</td>"  +
               "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerLost[i].lostReason   +"</td>"  +
               "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerLost[i].lostAtPrice   +"</td>"  +
               "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerLost[i].lostToInstitute   +"</td>"  +
               "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerLost[i].remarks   +"</td>"  +
               "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.customerLost[i].executiveName   +"</td>"  +
          "</tr>";
  }
  html += "</tbody></table>";
  var newWindow = window.open();
  newWindow.document.write("<html><head><title>CRMLite</title></head><body>" + html + "</body></html>");	
	}
	try {
	    console.log("thing=customerLost&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=customerLost&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	} catch (x) {
		console.log("thing=customerLost&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=customerLost&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	}
}





//getting names of customer full payment

function sendAjaxForGettingCustomerFullPayment() {
	var ourRequest = new XMLHttpRequest();
	ourRequest.open('POST', 'dashboard-details-provider', true);
	ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	console.log('ajax sent');
	ourRequest.onload = function () {
		var ourData = JSON.parse(ourRequest.responseText);
		console.log(ourData);	

		var html = "<h1>Customers with Full Payment Completed</h1>"
		html += "<table><tbody>";
		html +=
	    "<tr>"+
		        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Lead Name</th>"+
		        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Mobile</th>"+
		        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Course</th>"+
		        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Fees</th>"+
		        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Trainer</th>"+
		        "<th style='padding: 10px; width: 100px; border: 1px solid;'>Executive Name</th>"+
		    "</tr>";
		for(var i=0;i < ourData.fullPayment.length;i++) {
		    html+=
		        "<tr>"+
		             "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.fullPayment[i].name    	+"</td>" + 
		             "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.fullPayment[i].mobile  	+"</td>" + 
		             "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.fullPayment[i].course  	+"</td>" +
		             "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.fullPayment[i].fees		+"</td>"  +
		             "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.fullPayment[i].trainer	    +"</td>"  +
		             "<td style='padding: 10px; width: 100px; border: 1px solid;'>"+ ourData.fullPayment[i].executiveName   +"</td>"  +
		        "</tr>";
		}
		html += "</tbody></table>";
		var newWindow = window.open();
		newWindow.document.write("<html><head><title>CRMLite</title></head><body>" + html + "</body></html>");	
	}
	try {
	    console.log("thing=fullPayment&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=fullPayment&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	} catch (x) {
		console.log("thing=fullPayment&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	    ourRequest.send("thing=fullPayment&from-date=" + document.getElementById('business-executive-revenue-from').value + "&to-date=" + document.getElementById('business-executive-revenue-to').value + "&course=" + document.getElementById('business-executive-revenue-courseId').value + "&executive=" + document.getElementById('business-executive-revenue-nameId').value);
	}
}




// getting the lead extraction count

function getLeadExtractionCount() {
	var leadExtractorDataRequest = new XMLHttpRequest();
	var startDate = document.getElementById('business-executive-revenue-from').value;
	var endDate = document.getElementById('business-executive-revenue-to').value;
	leadExtractorDataRequest.open('GET', 'lead-extraction-count?fromDate=' + startDate + '&toDate=' + endDate, true);
	leadExtractorDataRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	leadExtractorDataRequest.send();
	console.log('ajax sent for lead-extraction-count');
	leadExtractorDataRequest.onload = function () {
		var leadExtractorDataResponse = JSON.parse(leadExtractorDataRequest.responseText);
		console.log(leadExtractorDataResponse);
		var html = "<h1>Day-wise Lead Extraction Report</h1>"
		html += "<table><tbody>";
		html += "<tr><th style='padding: 10px; width: 100px; border: 1px solid;'>Date</th>";
		for (var i = 0; i < leadExtractorDataResponse.extractionData.length; i++) {
        	html += "<th style='padding: 10px; width: 100px; border: 1px solid;'>" + leadExtractorDataResponse.extractionData[i].name + "</th>"
        }
		html += "</tr>";
		var allDates = getDateArray(new Date(startDate), new Date(endDate));
		for (var i = 0; i < allDates.length; i++) {
			html += "<tr><td style='padding: 10px; width: 100px; border: 1px solid;'>" + allDates[i] + "</td>"; 
			for (var j = 0; j < leadExtractorDataResponse.extractionData.length; j++)
				try {
					tempDate = allDates[i].split("-");
					tempD = tempDate[0] + "_" + tempDate[1] + "_" + tempDate[2];
					console.log(tempD);
					temp = "<td style='padding: 10px; width: 100px; border: 1px solid;'>" + leadExtractorDataResponse.extractionData[j][tempD] + "</td>";
					if (temp.includes("undefined"))
						temp = temp.replace("undefined", "0");
					html += temp;
				} catch(e){}
			html += '</tr>';
		}
		html += "</tbody></table>";
		var newWindow = window.open();
		newWindow.document.write("<html><head><title>CRMLite</title></head><body>" + html + "</body></html>");	
	}
}

var getDateArray = function(start, end) {
    var arr = new Array();
    var dt = new Date(start);
    while (dt <= end) {
        arr.push((new Date(dt)).toISOString().split('T')[0]);
        dt.setDate(dt.getDate() + 1);
    }
    return arr;
}