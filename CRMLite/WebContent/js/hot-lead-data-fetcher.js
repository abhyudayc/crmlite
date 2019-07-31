var hotLeadTableRows = '';
var currentTitleOfHotLeadsButton = 'Hot leads';

function hotLeadDisplaySwitcher(hotLeadButton) {
	
	// disable all except quick filter
	var filters = [document.getElementById('filterdate'),
				   document.getElementById('filterdateTo'),
				   document.getElementById('courses-dropdown'),
				   document.getElementById('dropdownTwo'),
				   document.getElementById('dropdownthree'),
				   document.getElementById('add-lead-btn'),
				   document.getElementById('view-more-btn')];
	for(var i = 0; i < filters.length; i++)
		if(filters[i].hasAttribute('disabled'))
			filters[i].removeAttribute('disabled');
		else
			filters[i].setAttribute('disabled', '');
	
	// hot lead button text changer
	if(hotLeadButton.innerHTML.includes('Hot leads')) {
		hotLeadButton.innerHTML = "All leads";
		var tbody = document.getElementById('ctable-body');
		tbody.innerHTML="";
        tbody.insertAdjacentHTML('beforeend', hotLeadTableRows);
	} else {
		hotLeadButton.innerHTML = currentTitleOfHotLeadsButton;
		sendFilterDataInAjax(true);
	}
	
}

function sendAjaxForFetchingHotLeads() {
	console.log("fetching Hot leads");
	var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET', 'hot-lead-data', true);
    ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    ourRequest.onload = function () {
        var ourData = JSON.parse(ourRequest.responseText);
        hotLeadTableRows = '';
        for(var i = 1; i < ourData.hotleads.length; i++) { // creating rows, i.e. 'tr's
        	var row = '<tr>';
        	for(var j = 0; j < ourData.hotleads[i].length; j++) { // putting cells inside rows, i.e. 'td's
        		var cell;
        		if(j==0)
        			cell = '<td style="display:none"><input type="checkbox" name="lead-chkbox" value="' + ourData.hotleads[i][j] + '" ></td>';
        		else {
        			var colName = ourData.hotleads[0][j];
        			cell = '<td onClick="switchToFormViewForLead(' + i + ', ' + ourData.hotleads[i][0] + ')">' + ourData.hotleads[i][j] + '</td>';
        		}
        		row += cell;
        	}
        	row += '</tr>';
        	hotLeadTableRows += row; // putting the newly created 'tr' in new html called combinedRows
        }
        if(!document.getElementById('add-lead-btn').hasAttribute("disabled")) {
        	currentTitleOfHotLeadsButton = 'Hot leads (' + ourData.untouchedcount + '/' + ourData.totalcount + ')';
        	document.getElementById('hot-lead-btn').innerHTML = currentTitleOfHotLeadsButton; 
        }
    }
    ourRequest.send();
}

setInterval(function() {
	sendAjaxForFetchingHotLeads();
}, 3000);