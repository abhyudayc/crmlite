document.getElementById('global-lead-searchbox').addEventListener("keydown", function (e) {
    if (e.keyCode === 13)
    	getGlobalLeads(e);
});

function getGlobalLeads() {
	// disable all except quick filter
	var filters = [document.getElementById('filterdate'),
				   document.getElementById('filterdateTo'),
				   document.getElementById('courses-dropdown'),
				   document.getElementById('dropdownTwo'),
				   document.getElementById('dropdownthree'),
				   document.getElementById('hot-lead-btn'),
				   document.getElementById('add-lead-btn'),
				   document.getElementById('view-more-btn')];
	for(var i = 0; i < filters.length; i++)
		filters[i].setAttribute('disabled', '');
	
	searchTerm = document.getElementById('global-lead-searchbox').value;
	searchTerm = searchTerm.trim();
	if (searchTerm.length > 0)
		sendAjaxForGettingGlobalLeadNames(searchTerm);
	else {
		sendFilterDataInAjax(true, 0);
		for(var i = 0; i < filters.length; i++)
			filters[i].removeAttribute('disabled');
	}
}


function sendAjaxForGettingGlobalLeadNames(searchTerm) {
	var tbody = document.getElementById('ctable-body');
	while (tbody.firstChild)
		tbody.removeChild(tbody.firstChild);
	tbody.innerHTML = "Fetching data...";
	
	var ourRequest = new XMLHttpRequest();
	ourRequest.open('GET', 'global-lead?q='+searchTerm, true);
	ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ourRequest.onload = function () {
		var ourData = JSON.parse(ourRequest.responseText);
		console.log(ourData);
    	if(tbody.innerHTML=='Fetching data...')
    		tbody.innerHTML="";
        var combinedRows = '';
		for(var i = 0; i < ourData.globallyFetchedLeads.length; i++) {
			var row = '<tr onClick="formViewForGlobalLead(' + ourData.globallyFetchedLeads[i][0] + ')">';
        	for(var j = 0; j < ourData.globallyFetchedLeads[i].length; j++) { // putting cells inside rows, i.e. 'td's
        		var cell;
        		if(j==0)
        			cell = '<td style="display:none">' + ourData.globallyFetchedLeads[i][j] + '</td>';
        		else {
        			var colName = ourData.globallyFetchedLeads[0][j];
        			cell = '<td>' + ourData.globallyFetchedLeads[i][j] + '</td>';
        		}
        		row += cell;
        	}
        	row += '</tr>';
        	combinedRows += row; // putting the newly created 'tr' in new html called combinedRows
		}
        tbody.insertAdjacentHTML('beforeend', combinedRows);
	}
    ourRequest.send();
}


function formViewForGlobalLead(pk) {
	fetchFormDataForLead(pk);
	switchView();
}

function fillUpList(elem) {
	searchTerm = elem.value.trim();
	var datalist = document.getElementById('phone-numbers');
	if (searchTerm.length > 5 && datalist.innerHTML.trim() == '') {
		var ourRequest = new XMLHttpRequest();
		ourRequest.open('GET', 'global-lead?q='+searchTerm, true);
		ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		ourRequest.onload = function () {
			var ourData = JSON.parse(ourRequest.responseText);
			console.log(ourData);
			var html = '';
			for(var i = 0; i < ourData.globallyFetchedLeads.length; i++) {
				html += '<option ';
				html += 'value=' + ourData.globallyFetchedLeads[i][3] + '>';
				html += ourData.globallyFetchedLeads[i][2] + ', ' + ourData.globallyFetchedLeads[i][5];
	        	html += '</option>';
			}
	        datalist.insertAdjacentHTML('beforeend', html);
		}
	    ourRequest.send();
	} else if (searchTerm.length <= 5) {
		datalist.innerHTML = '';
	}
}