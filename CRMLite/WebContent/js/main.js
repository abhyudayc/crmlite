function blockSpecialChar(e) {
    var k = e.keyCode;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || k == 64 || k == 46 || k == 95 || (k >= 48 && k <= 57));
}

function myfun() {
    // select 
    var dropdown = document.getElementById('status');

    // customer lost dropdown
    var customerloststory = document.getElementById('customer-lost-story');

    // Name of the course for looking for other course
    var course = document.getElementById('course');

    // date for demo scheduled
    var demoDate = document.getElementById('demoDate');

    // Time for demo scheduled
    var demoTime = document.getElementById('demoTime');

    // trainer name for demo scheduled
    var demotrainername = document.getElementById('demotrainername');

    // follow up for pipeline
    var followup = document.getElementById('followup');
    var expectdate = document.getElementById('expectdate');

    // quotedfees for success stories
    var quotedfees = document.getElementById('quotedfees');

    // advancefees for success stories
    var advancefees = document.getElementById('advancefees');
    var advancepaiddate = document.getElementById('advancepaiddate')
    
    // installment for success stories
    var installment = document.getElementById('installment');
    
    var pricelost = document.getElementById('pricelost');
    var nameinst = document.getElementById('nameinst');
    var trainername = document.getElementById('trainername');

    // index which is selected
    selectedOption = dropdown.options[dropdown.selectedIndex];
    console.log(selectedOption);
    console.log(selectedOption.value);
    
    switch (selectedOption.value) {
    case "other-course":
    	course.style.display = 'block';
        demoDate.style.display = 'none';
        demoTime.style.display = 'none';
        demotrainername.style.display='none';
        followup.style.display = 'none';
        expectdate.style.display='none';
        quotedfees.style.display = 'none';
        advancefees.style.display = 'none';
        installment.style.display = 'none';
        advancepaiddate.style.display = 'none';
        customerloststory.style.display = 'none';
        pricelost.style.display = 'none';
        nameinst.style.display = 'none';
        trainername.style.display = 'none';
		
		document.getElementById('otherCourseName').setAttribute("required", "");
		document.getElementById('dateOfDemo').removeAttribute("required");
		document.getElementById('timeOfDemo').removeAttribute("required");
		document.getElementById('demoTrainer').removeAttribute("required");
		document.getElementById('fees').removeAttribute("required");
		document.getElementById('followupdate').removeAttribute("required");
		document.getElementById('followuptime').removeAttribute("required");
		document.getElementById('expectedclosuredate').removeAttribute("required");
		document.getElementById('quotedfeesInput').removeAttribute("required");
		document.getElementById('advancefeesInput').removeAttribute("required");
		document.getElementById('advancepaiddateInput').removeAttribute("required");
        
		document.getElementsByClassName('reminder-section')[0].style.display="none";
    	break;
    case "demo-scheduled":
    	course.style.display = 'none';
        demoDate.style.display = 'block';
        demoTime.style.display = 'block';
        demotrainername.style.display='block';
        followup.style.display = 'none';
        expectdate.style.display='none';
        quotedfees.style.display = 'none';
        advancefees.style.display = 'none';
        installment.style.display = 'none';
        advancepaiddate.style.display = 'none';
        customerloststory.style.display = 'none';
        pricelost.style.display = 'none';
        nameinst.style.display = 'none';
        trainername.style.display = 'none';
		
		document.getElementById('otherCourseName').removeAttribute("required");
		document.getElementById('dateOfDemo').setAttribute("required", "");
		document.getElementById('timeOfDemo').setAttribute("required", "");
		document.getElementById('demoTrainer').setAttribute("required", "");
		document.getElementById('fees').setAttribute("required", "");
		document.getElementById('followupdate').removeAttribute("required");
		document.getElementById('followuptime').removeAttribute("required");
		document.getElementById('expectedclosuredate').removeAttribute("required");
		document.getElementById('quotedfeesInput').removeAttribute("required");
		document.getElementById('advancefeesInput').removeAttribute("required");
		document.getElementById('advancepaiddateInput').removeAttribute("required");
		
		document.getElementsByClassName('reminder-section')[0].style.display="none";
        break;
    case "pipeline":
    	course.style.display = 'none';
        demoDate.style.display = 'none';
        demoTime.style.display = 'none';
        demotrainername.style.display='none';
        followup.style.display = 'block';
        expectdate.style.display='block';
        quotedfees.style.display = 'none';
        advancefees.style.display = 'none';
        installment.style.display = 'none';
        advancepaiddate.style.display = 'none';
        customerloststory.style.display = 'none';
        pricelost.style.display = 'none';
        nameinst.style.display = 'none';
        trainername.style.display = 'none';
		
		document.getElementById('otherCourseName').removeAttribute("required");
		document.getElementById('dateOfDemo').removeAttribute("required");
		document.getElementById('timeOfDemo').removeAttribute("required");
		document.getElementById('demoTrainer').removeAttribute("required");
		document.getElementById('fees').setAttribute("required", "");
		document.getElementById('followupdate').setAttribute("required", "");
		document.getElementById('followuptime').setAttribute("required", "");
		document.getElementById('expectedclosuredate').setAttribute("required", "");
		document.getElementById('quotedfeesInput').removeAttribute("required");
		document.getElementById('advancefeesInput').removeAttribute("required");
		document.getElementById('advancepaiddateInput').removeAttribute("required");
		
		document.getElementsByClassName('reminder-section')[0].style.display="none";
    	break;
    case "success-stories":
    	course.style.display = 'none';
        demoDate.style.display = 'none';
        demoTime.style.display = 'none';
        demotrainername.style.display='none';
        followup.style.display = 'none';
        expectdate.style.display='none';
        quotedfees.style.display = 'block';
        advancefees.style.display = 'block';
        installment.style.display = 'block';
        advancepaiddate.style.display = 'block';
        customerloststory.style.display = 'none';
        pricelost.style.display = 'none';
        nameinst.style.display = 'none';
        trainername.style.display = 'none';
		
		document.getElementById('otherCourseName').removeAttribute("required");
		document.getElementById('dateOfDemo').removeAttribute("required");
		document.getElementById('timeOfDemo').removeAttribute("required");
		document.getElementById('demoTrainer').removeAttribute("required");
		document.getElementById('fees').removeAttribute("required");
		document.getElementById('followupdate').removeAttribute("required");
		document.getElementById('followuptime').removeAttribute("required");
		document.getElementById('expectedclosuredate').removeAttribute("required");
		document.getElementById('quotedfeesInput').setAttribute("required", "");
		document.getElementById('advancefeesInput').setAttribute("required", "");
		document.getElementById('advancepaiddateInput').setAttribute("required", "");
		
		document.getElementsByClassName('reminder-section')[0].style.display="none";
        break;
    case "customer-lost":
    	course.style.display = 'none';
        demoDate.style.display = 'none';
        demoTime.style.display = 'none';
        demotrainername.style.display='none';
        followup.style.display = 'none';
        expectdate.style.display='none';
        quotedfees.style.display = 'none';
        advancefees.style.display = 'none';
        installment.style.display = 'none';
        advancepaiddate.style.display = 'none';
        customerloststory.style.display = 'block';
        pricelost.style.display = 'none';
        nameinst.style.display = 'none';
        trainername.style.display = 'none';
		
		document.getElementById('otherCourseName').removeAttribute("required");
		document.getElementById('dateOfDemo').removeAttribute("required");
		document.getElementById('timeOfDemo').removeAttribute("required");
		document.getElementById('demoTrainer').removeAttribute("required");
		document.getElementById('fees').removeAttribute("required");
		document.getElementById('followupdate').removeAttribute("required");
		document.getElementById('followuptime').removeAttribute("required");
		document.getElementById('expectedclosuredate').removeAttribute("required");
		document.getElementById('quotedfeesInput').removeAttribute("required");
		document.getElementById('advancefeesInput').removeAttribute("required");
		document.getElementById('advancepaiddateInput').removeAttribute("required");
		
		document.getElementsByClassName('reminder-section')[0].style.display="none";
        break;
    case "follow-up":
    	course.style.display = 'none';
        demoDate.style.display = 'none';
        demoTime.style.display = 'none';
        demotrainername.style.display='none';
        followup.style.display = 'block';
        expectdate.style.display='none';
        quotedfees.style.display = 'none';
        advancefees.style.display = 'none';
        installment.style.display = 'none';
        advancepaiddate.style.display = 'none';
        customerloststory.style.display = 'none';
        pricelost.style.display = 'none';
        nameinst.style.display = 'none';
        trainername.style.display = 'none';
		
		document.getElementById('otherCourseName').removeAttribute("required");
		document.getElementById('dateOfDemo').removeAttribute("required");
		document.getElementById('timeOfDemo').removeAttribute("required");
		document.getElementById('demoTrainer').removeAttribute("required");
		document.getElementById('fees').removeAttribute("required");
		document.getElementById('followupdate').setAttribute("required", "'");
		document.getElementById('followuptime').setAttribute("required", "");
		document.getElementById('expectedclosuredate').removeAttribute("required");
		document.getElementById('quotedfeesInput').removeAttribute("required");
		document.getElementById('advancefeesInput').removeAttribute("required");
		document.getElementById('advancepaiddateInput').removeAttribute("required");
		
		document.getElementsByClassName('reminder-section')[0].style.display="none";
        break;
    case "reminder":
    	course.style.display = 'none';
        demoDate.style.display = 'none';
        demoTime.style.display = 'none';
        demotrainername.style.display='none';
        followup.style.display = 'none';
        expectdate.style.display='none';
        quotedfees.style.display = 'none';
        advancefees.style.display = 'none';
        installment.style.display = 'none';
        advancepaiddate.style.display = 'none';
        customerloststory.style.display = 'none';
        pricelost.style.display = 'none';
        nameinst.style.display = 'none';
        trainername.style.display = 'none';
		
		document.getElementById('otherCourseName').removeAttribute("required");
		document.getElementById('dateOfDemo').removeAttribute("required");
		document.getElementById('timeOfDemo').removeAttribute("required");
		document.getElementById('demoTrainer').removeAttribute("required");
		document.getElementById('fees').removeAttribute("required");
		document.getElementById('followupdate').removeAttribute("required");
		document.getElementById('followuptime').removeAttribute("required");
		document.getElementById('expectedclosuredate').removeAttribute("required");
		document.getElementById('quotedfeesInput').removeAttribute("required");
		document.getElementById('advancefeesInput').removeAttribute("required");
		document.getElementById('advancepaiddateInput').removeAttribute("required");
		
		document.getElementsByClassName('reminder-section')[0].style.display="block";
        break;
    default:
    	course.style.display = 'none';
	    demoDate.style.display = 'none';
	    demoTime.style.display = 'none';
	    demotrainername.style.display='none';
	    followup.style.display = 'none';
	    expectdate.style.display='none';
	    quotedfees.style.display = 'none';
	    advancefees.style.display = 'none';
	    installment.style.display = 'none';
	    advancepaiddate.style.display = 'none';
	    customerloststory.style.display = 'none';
	    pricelost.style.display = 'none';
	    nameinst.style.display = 'none';
	    trainername.style.display = 'none';
		
		document.getElementById('otherCourseName').removeAttribute("required");
		document.getElementById('dateOfDemo').removeAttribute("required");
		document.getElementById('timeOfDemo').removeAttribute("required");
		document.getElementById('demoTrainer').removeAttribute("required");
		document.getElementById('fees').removeAttribute("required");
		document.getElementById('followupdate').removeAttribute("required");
		document.getElementById('followuptime').removeAttribute("required");
		document.getElementById('expectedclosuredate').removeAttribute("required");
		document.getElementById('quotedfeesInput').removeAttribute("required");
		document.getElementById('advancefeesInput').removeAttribute("required");
		document.getElementById('advancepaiddateInput').removeAttribute("required");
		
		document.getElementsByClassName('reminder-section')[0].style.display="none";
    }
}
function myanotherfun() {
    var dropdown = document.getElementById('customerlostdropdown');

    // price at which the customer is lost for price factor
    var pricelost = document.getElementById('pricelost');

    // name of the institute for price factor
    var nameinst = document.getElementById('nameinst');

    // name of the trainer for trainer issue
    var trainername = document.getElementById('trainername');

    // index which is selected
    selectedOption = dropdown.options[dropdown.selectedIndex];

    // if for price factor
    if (selectedOption.value == "price-factor") {
        pricelost.style.display = 'block';
        nameinst.style.display = 'block';
    } else {
        pricelost.style.display = 'none';
        nameinst.style.display = 'none';
    }

    // if for trainer issue 
    if (selectedOption.value == "trainer-issue") {
        trainername.style.display = 'block';
    } else {
        trainername.style.display = 'none';
    }
}

// add lead form displayer
function addleadformdisplayer() {
	buttontoggle();
    document.getElementById('show-prev-lead').style.display='none';
    document.getElementById('update-lead').style.display='none';
    document.getElementById('show-next-lead').style.display='none';
	var allInputsInForm = document.getElementById("formid").querySelectorAll("input[type='text'], input[type='email'], input[type='number']");
	for(var i = 0; i < allInputsInForm.length; i++)
		allInputsInForm[i].value="";
}

//switch view (form to table / table to form)
function switchView() {
	buttontoggle();
 var viewmore = document.getElementById('view-more-btn');//view more
 if(viewmore.style.display=='none')
     document.getElementById('submit-new-lead').style.display='none';    
}

// hide/show 
function buttontoggle(){
	var newLeadButton = document.getElementById('add-lead-btn');
    var leadform = document.getElementById('add-new-lead-form');//none
    var ctable = document.getElementById('ctable');//block
    var viewmore = document.getElementById('view-more-btn');//view more
    var prevButton = document.getElementById('show-prev-lead');
    var updateLead = document.getElementById('update-lead');
    var nextButton = document.getElementById('show-next-lead');
    var submitButton = document.getElementById('submit-new-lead');
    var switchview = document.getElementById('switch-view-btn');
	var hotLeadButton = document.getElementById('hot-lead-btn');
    var quickFilter = document.getElementById('quick-filter');
    var toDate = document.getElementById('fetch_lead_to_date');
    var fromDateLabel = document.getElementById('fetch_lead_from_date').querySelector('label');
    var reassignButton = document.getElementById('reassign-button');
    
    if(leadform.style.display=='none'){
    	leadform.style.display='block';
    	newLeadButton.style.display='none';
    	viewmore.style.display='none';
    	quickFilter.style.display='none';
    	reassignButton.style.display='none';
    	fromDateLabel.innerHTML = 'Date';
    	toDate.style.display='none';
    	hotLeadButton.style.display='none';
    	ctable.style.display='none';
        nextButton.style.display='block';
        updateLead.style.display='block';
        prevButton.style.display='block';
        switchview.style.display='block';
    }else{
    	leadform.style.display='none';
    	newLeadButton.style.display='block';
    	viewmore.style.display='block';
    	quickFilter.style.display='block';
    	if (sessionStorage.getItem('logged_in_role') == "admin")
    		reassignButton.style.display='block';
    	fromDateLabel.innerHTML = 'From Date';
    	toDate.style.display='block';
    	toDate.querySelector('input').value = sessionStorage.getItem('datesTo');
    	hotLeadButton.style.display='block';
        ctable.style.display='block';
        submitButton.style.display='block';
        nextButton.style.display='none';
        updateLead.style.display='none';
        prevButton.style.display='none'
    	switchview.style.display='none';
    	sessionStorage.setItem("isSubmit", "false");
    }
}

//Form Data Submit
function formDataSubmit(){
	sessionStorage.setItem("isSubmit", "true");
}



// called on every filter dropdown change event

function edValueKeyPress() {
    var localdate = document.getElementById("filterdate").value;
    var localdateTo = document.getElementById("filterdateTo").value;
    var location = document.getElementById('dropdownTwo');
    var sub_location = document.getElementById('dropdownthree');
    var status_filter = document.getElementById('lead-status-dropdown');
    console.log(sub_location);

    var courses_dropdown_selectedOption = document.getElementById('courses-dropdown').options[document.getElementById('courses-dropdown').selectedIndex];
    var location_dropdown_selectetdOption = location.options[location.selectedIndex];
    var sub_location_selectedOption = sub_location.options[sub_location.selectedIndex];
    var status_filter_selectedOption = status_filter.options[status_filter.selectedIndex];
    
    submitEnablerDisabler();
    
    sessionStorage.setItem('lead_courses_set', courses_dropdown_selectedOption.value);
    sessionStorage.setItem('dates',localdate);
    sessionStorage.setItem('datesTo',localdateTo);
    sessionStorage.setItem('location_set',location_dropdown_selectetdOption.value);
    //sessionStorage.setItem('sublocation_set',sub_location_selectedOption.value);
    sessionStorage.setItem('status_set',status_filter_selectedOption.value);
    sendFilterDataInAjax(true);
}

var noOfTimesClicked;

function viewMoreRows() {
	noOfTimesClicked++;
	sendFilterDataInAjax(false)
}

function submitEnablerDisabler() {
	if(document.getElementById('dropdownTwo').options[document.getElementById('dropdownTwo').selectedIndex].value.includes('_') || 
			document.getElementById('courses-dropdown').options[document.getElementById('courses-dropdown').selectedIndex].value.includes('_'))
    	document.getElementById('submit-new-lead').disabled = true;
    else
    	document.getElementById('submit-new-lead').disabled = false;
}


// called on every refresh

window.onload = function() {
	try {
		document.getElementById("business-executive-revenue-from").valueAsDate = new Date();
		document.getElementById("business-executive-revenue-to").valueAsDate = new Date();
	} catch(e){}
	
    noOfTimesClicked = 0;
    
    if (sessionStorage.getItem('logged_in_role') == "admin") {
    	var admin = document.getElementsByClassName('nav')[0];
        var listItem = document.createElement('li');
        var adminPage = document.createElement('a');
        adminPage.setAttribute('href','admin');
        adminPage.innerHTML = "Admin";
        listItem.appendChild(adminPage);
        admin.insertBefore(listItem, admin.children[2]);
        document.getElementById('reassign-button').style.display = 'block';
    }
    
    try {
	    if (sessionStorage.getItem('dates') !== null) {
	        document.getElementById('filterdate').value= sessionStorage.getItem('dates');
	    } else {
	    	// get the current date displayed
	    	document.getElementById("filterdate").valueAsDate = new Date();
	    	document.getElementById("filterdateTo").valueAsDate = new Date();
	    	sessionStorage.setItem('datesTo',document.getElementById('filterdateTo').value);
	    	sessionStorage.setItem('dates',document.getElementById('filterdate').value);
	    }
    } catch(e){}
    
    // if there is a course set in session storage, select that one, else set the current course in session storage
    if(sessionStorage.getItem('lead_courses_set') !== null && sessionStorage.getItem('lead_courses_set') !== undefined){
        for(var i = 0; i < document.getElementById('courses-dropdown').options.length; ++i) {
            if(document.getElementById('courses-dropdown').options[i].value == sessionStorage.getItem('lead_courses_set')) {
                document.getElementById('courses-dropdown').selectedIndex = i;
                break;
            }
        }
        console.log(sessionStorage.getItem('lead_courses_set').value);
    } else {
    	sessionStorage.setItem('lead_courses_set', (document.getElementById('courses-dropdown').options[document.getElementById('courses-dropdown').selectedIndex]).value);
    }

    // if there is a course set in session storage, select that one, else set the current course in session storage
    if(sessionStorage.getItem('location_set') !== null && sessionStorage.getItem('location_set') !== undefined){
        for(var i = 0; i < document.getElementById('dropdownTwo').options.length; ++i) {
            if(document.getElementById('dropdownTwo').options[i].value == sessionStorage.getItem('location_set')) {
                document.getElementById('dropdownTwo').selectedIndex = i;
                break;
            }
        }
    } else {
    	var location = document.getElementById('dropdownTwo');
    	var location_dropdown_selectetdOption = location.options[location.selectedIndex];
    	sessionStorage.setItem('location_set',location_dropdown_selectetdOption.value);
    }
    

    // if there is a course set in session storage, select that one, else set the current course in session storage
    if(sessionStorage.getItem('sublocation_set') !== null && sessionStorage.getItem('sublocation_set') !== undefined){
        for(var i = 0; i < document.getElementById('dropdownthree').options.length; ++i) {
            if(document.getElementById('dropdownthree').options[i].value == sessionStorage.getItem('sublocation_set')) {
                document.getElementById('dropdownthree').selectedIndex = i;
                break;
            }
        }
    } else {
        var sub_location = document.getElementById('dropdownthree');
        var sub_location_selectedOption = sub_location.options[sub_location.selectedIndex];
    	sessionStorage.setItem('sublocation_set',sub_location_selectedOption.value);
    }
    
    if(sessionStorage.getItem("isSubmit") == "true")
    	addleadformdisplayer();
    
    submitEnablerDisabler();
    sendFilterDataInAjax(true);
}

var paused = false;

function reminderTasks(remData) {

    document.getElementsByClassName('display')[0].innerHTML = "";
	for(var i=0 ; i < remData.reminders.length ; i++){
        
        var html = `<b id="pk" style="display:none">` + remData.reminders[i][0] + `</b>`+
        `<b id="dateButton">` + remData.reminders[i][1] + `</b>`+
        ` - <b id="timeButton">` + remData.reminders[i][2] + `</b>`+
        `<p id="textarea">` + remData.reminders[i][3] + `</p>`
        document.getElementsByClassName('display')[0].innerHTML += html + '<br>'

    }
	
	setInterval(function(){ 
		if(!paused) {
	        var d = new Date();
	        var currDay = d.getDate();
	        var currMonth = d.getMonth()+1; 
	        var currYear = d.getFullYear();
	        if(currDay<10) { currDay='0'+currDay; } 
	        if(currMonth<10) { currMonth='0'+currMonth; } 
	        var currHours = d.getHours();
	        var currMin = d.getMinutes();
	        
		    var arr = currHours+":"+currMin;
		    var timeSplit = arr.split(":");
		    var timeOne = (timeSplit[0] * (60000 * 60)) + (timeSplit[1] * 60000);
		   
		    for(var i=0; i < remData.reminders.length; i++){
		    	if(currYear+"-"+currMonth+"-"+currDay == remData.reminders[i][1]) {
			        var time  = remData.reminders[i][2];
			        var timeParts = time.split(":");
			        var timeTwo = (timeParts[0] * (60000 * 60)) + (timeParts[1] * 60000);
			        if(timeTwo==timeOne) {
			            show(remData.reminders[i][0], remData.reminders[i][3]);
			            remData.reminders.splice(i, 1);
			        }
		    	}
	        }
		}
    }, 1000);
}

function show(pk, e){
//	document.getElementById('reminderPopupPK').innerHTML += pk;
	document.getElementById('reminderPopupText').innerHTML += e;
    document.getElementById('popupcard').style.display="block";
    paused = true;
}
function updateTime(){
//    var x=document.getElementById('snoozId').value;
    document.getElementById('popupcard').style.display='none';
    paused = false;
    console.log(x);
}

function getReminders(user_id) {
	var ourRequest = new XMLHttpRequest();
    ourRequest.open('POST', 'reminder', true);
    ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    console.log('getting reminders');
    ourRequest.onload = function () {
    	var ourData = JSON.parse(ourRequest.responseText);
    	console.log(ourData);
    	reminderTasks(ourData);
    }
    ourRequest.send("getSet=get&userId="+user_id);
}

function toggleSideNav(user_id){
    if(document.getElementsByClassName('reminder-section')[0].style.display=="none"){
    	getReminders(user_id);
		document.getElementsByClassName('reminder-section')[0].style.display="block";
	}
	else{
		document.getElementsByClassName('reminder-section')[0].style.display="none";
	}
}

function addreminer(user_id){
	dateValue = document.getElementById('addReminer-dateId').value;
	timeValue = document.getElementById('addReminer-timeId').value;
	descId = document.getElementById('addReminer-descId').value;
	
	setReminder(user_id, dateValue, timeValue, descId);
	getReminders(user_id);
	
	var inputArray = document.querySelectorAll('.reset');
	inputArray.forEach(function (input){
		input.value = "";
	}); 
}


function setReminder(user_id, dateValue, timeValue, descId) {
	var ourRequest = new XMLHttpRequest();
    ourRequest.open('POST', 'reminder', true);
    ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    console.log('setting reminders');
    ourRequest.onload = function () {}
    ourRequest.send("getSet=set&userId="+user_id+"&date="+dateValue+"&time="+timeValue+"&desc="+descId);
}

// function for dynamic form submission

function sendData(path, params, method) {
	
    method = method || "post";

    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}

// function to send filter data to Servlet

function sendFilterDataInAjax(filter_changed, no_of_clicks) {
	var tbody = document.getElementById('ctable-body');
	if(filter_changed==true) {
		noOfTimesClicked = 0;
		while (tbody.firstChild)
			tbody.removeChild(tbody.firstChild);
		tbody.innerHTML = "Fetching data...";
	}
	console.log("calling AJAX");
	
	
	no_of_clicks = no_of_clicks || noOfTimesClicked;
	
	var ourRequest = new XMLHttpRequest();
    ourRequest.open('POST', 'lead-data', true);
    ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    ourRequest.onload = function () {
        var ourData = JSON.parse(ourRequest.responseText);
    	console.log(ourData);
    	if(tbody.innerHTML=='Fetching data...')
    		tbody.innerHTML="";
        var combinedRows = '';
        for(var i = 1; i < ourData.jsonLeads.length; i++) { // creating rows, i.e. 'tr's
        	var row = '<tr">';
        	for(var j = 0; j < ourData.jsonLeads[i].length; j++) { // putting cells inside rows, i.e. 'td's
        		var cell;
        		if(j==0)
        			cell = '<td style="display:none"><input type="checkbox" name="lead-chkbox" value="' + ourData.jsonLeads[i][j] + '" ></td>';
        		else {
        			var colName = ourData.jsonLeads[0][j];
        			cell = '<td onClick="switchToFormViewForLead(' + eval((no_of_clicks * 30) + i) + ', ' + ourData.jsonLeads[i][0] + ')">' + ourData.jsonLeads[i][j] + '</td>';
        		}
        		row += cell;
        	}
        	row += '</tr>';
        	combinedRows += row; // putting the newly created 'tr' in new html called combinedRows
        }
        tbody.insertAdjacentHTML('beforeend', combinedRows);
    }
    ourRequest.send("noOfClicks=" + no_of_clicks + "&filterchanged=" + filter_changed + "&date=" + sessionStorage.getItem('dates') + "&dateTo=" + sessionStorage.getItem('datesTo') + "&courses=" + sessionStorage.getItem('lead_courses_set') + "&location=" + sessionStorage.getItem('location_set') + "&sublocation=" + sessionStorage.getItem('sublocation_set') + "&status=" + sessionStorage.getItem('status_set'));
}

// function to load form view of user clicked
var currentTableRowIndex;

function switchToFormViewForLead(tableRowIndex, pk) {
	fetchFormDataForLead(pk);
	switchView();
	currentTableRowIndex = tableRowIndex - 1;
	//document.getElementById('ctable-body').children[currentTableRowIndex].style.backgroundColor="rgba(255, 200, 0, 0.3)";
}

function fetchFormDataForLead(pk) {
	
	console.log("calling AJAX for fetching data for " + pk);
	
	var ourRequest = new XMLHttpRequest();
    
	ourRequest.open('POST', 'lead-data', true);
    ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    
    ourRequest.onload = function () {
    	try {
		    var ourData = JSON.parse(ourRequest.responseText);
			console.log(ourData.formViewLeads[0]);
			document.getElementById('pk-of-record').value = pk;
			document.getElementById('name').value = ourData.formViewLeads[0][2];
			document.getElementById('mobile').value = ourData.formViewLeads[0][3];
			document.getElementById('email').value = ourData.formViewLeads[0][4];
			document.getElementById('courses-dropdown').value = ourData.formViewLeads[0][6];
			document.getElementById('dropdownTwo').value = ourData.formViewLeads[0][32];
			document.getElementById('dropdownthree').value = ourData.formViewLeads[0][33];
			document.getElementById('training').value = ourData.formViewLeads[0][7];
			document.getElementById('status').value = ourData.formViewLeads[0][5];
			document.getElementById('dateOfDemo').value = ourData.formViewLeads[0][9];
			document.getElementById('timeOfDemo').value = ourData.formViewLeads[0][10];
			document.getElementById('followupdate').value = ourData.formViewLeads[0][11];
			document.getElementById('followuptime').value = ourData.formViewLeads[0][12];
			document.getElementById('quotedfeesInput').value = ourData.formViewLeads[0][13];
			document.getElementById('advancefeesInput').value = ourData.formViewLeads[0][14];
			document.getElementById('advancepaiddateInput').value = ourData.formViewLeads[0][15];
			
			document.getElementById('leadInstallAmt1').value = ourData.formViewLeads[0][16];
			document.getElementById('leadDueDate1').value = ourData.formViewLeads[0][17];
			if (ourData.formViewLeads[0][18] == 'Y')
				document.getElementById('instlPaid_1').setAttribute('checked', 'checked');
			document.getElementById('instlPaid_1').value = ourData.formViewLeads[0][18];
			
			document.getElementById('leadInstallAmt2').value = ourData.formViewLeads[0][19];
			document.getElementById('leadDueDate2').value = ourData.formViewLeads[0][20];
			if (ourData.formViewLeads[0][21] == 'Y')
				document.getElementById('instlPaid_2').setAttribute('checked', 'checked');
			document.getElementById('instlPaid_2').value = ourData.formViewLeads[0][21];
			
			document.getElementById('leadInstallAmt3').value = ourData.formViewLeads[0][22];
			document.getElementById('leadDueDate3').value = ourData.formViewLeads[0][23];
			if (ourData.formViewLeads[0][24] == 'Y')
				document.getElementById('instlPaid_3').setAttribute('checked', 'checked');
			document.getElementById('instlPaid_3').value = ourData.formViewLeads[0][24];
			
			document.getElementById('leadInstallAmt4').value = ourData.formViewLeads[0][25];
			document.getElementById('leadDueDate4').value = ourData.formViewLeads[0][26];
			if (ourData.formViewLeads[0][27] == 'Y')
				document.getElementById('instlPaid_4').setAttribute('checked', 'checked');
			document.getElementById('instlPaid_4').value = ourData.formViewLeads[0][27];
			
			document.getElementById('demoTrainer').value = ourData.formViewLeads[0][28];
			document.getElementById('trainer').value = ourData.formViewLeads[0][28];
			document.getElementById('expectedclosuredate').value = ourData.formViewLeads[0][29];
			document.getElementById('customerlostdropdown').value = ourData.formViewLeads[0][30];
			document.getElementById('fees').value = ourData.formViewLeads[0][8];
			document.getElementById('remarks').value = ourData.formViewLeads[0][36];
			document.getElementById('priceLostAt').value = ourData.formViewLeads[0][34];
			document.getElementById('priceLostInst').value = ourData.formViewLeads[0][35];
    	} catch(e) {
    		window.location.reload(false);
    	}
    }
    
    ourRequest.send("pk=" + pk);	
}

function updateLead() {
	var form = document.getElementById('formid');
	var isReqdLeft = false;
	for(var i=0; i < form.elements.length; i++){
    	if(form.elements[i].value === '' && form.elements[i].hasAttribute('required')){
    		form.elements[i].style.border = "thick solid #f00";
    		isReqdLeft = true;
    	} else {
    		form.elements[i].style.border = "1px solid #ccc";
    	}
	}
	if(isReqdLeft == false) {
		sendAjaxForSavingCurrentData();
		switchView();
		//document.getElementById('ctable-body').children[currentTableRowIndex].style.backgroundColor="rgba(255, 0, 10, 0.4)";
	}
}

function sendAjaxForSavingCurrentData() {
	var ourRequest = new XMLHttpRequest();
    ourRequest.open('POST', 'add-lead', true);
    ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    console.log('ajax sent on next/prev button');
//    ourRequest.onload = function () {
//        var ourData = JSON.parse(ourRequest.responseText);    	
//    }
    ourRequest.send("lead-name=" + document.getElementById('name').value 
    		+ "&lead-mobile=" + document.getElementById('mobile').value 
    		+ "&lead-email=" + document.getElementById('email').value
    		+ "&lead-training=" + document.getElementById('training').value
    		+ "&lead-status=" + document.getElementById('status').value
    		+ "&lead-course=" + document.getElementById('courses-dropdown').value
    		+ "&lead-proposed-fees=" + document.getElementById('fees').value
    		+ "&lead-demo-date=" + document.getElementById('dateOfDemo').value
    		+ "&lead-demo-time=" + document.getElementById('timeOfDemo').value
    		+ "&lead-followup-date=" + document.getElementById('followupdate').value
    		+ "&lead-followup-time=" + document.getElementById('followuptime').value
    		+ "&lead-quoted-fees=" + document.getElementById('quotedfeesInput').value
    		+ "&lead-advance-fees=" + document.getElementById('advancefeesInput').value
    		+ "&lead-advance-paid-date=" + document.getElementById('advancepaiddateInput').value
    		+ "&lead-install-amt1=" + document.getElementById('leadInstallAmt1').value
    		+ "&lead-due-date1=" + document.getElementById('leadDueDate1').value
    		+ "&lead-instl-paid1=" + document.getElementById('instlPaid_1').value
    		+ "&lead-install-amt2=" + document.getElementById('leadInstallAmt2').value
    		+ "&lead-due-date2=" + document.getElementById('leadDueDate2').value
    		+ "&lead-instl-paid2=" + document.getElementById('instlPaid_2').value
    		+ "&lead-install-amt3=" + document.getElementById('leadInstallAmt3').value
    		+ "&lead-due-date3=" + document.getElementById('leadDueDate3').value
    		+ "&lead-instl-paid3=" + document.getElementById('instlPaid_3').value
    		+ "&lead-install-amt4=" + document.getElementById('leadInstallAmt4').value
    		+ "&lead-due-date4=" + document.getElementById('leadDueDate4').value
    		+ "&lead-instl-paid4=" + document.getElementById('instlPaid_4').value
    		+ "&lead-trainer=" + document.getElementById('demoTrainer').value
    		+ "&lead-lost-reason=" + document.getElementById('customerlostdropdown').value
    		+ "&lead-location=" + document.getElementById('dropdownTwo').value
    		+ "&lead-sublocation=" + document.getElementById('dropdownthree').value
    		+ "&lead-expected-closure-date=" + document.getElementById('expectedclosuredate').value
    		+ "&lead-lost-price=" + document.getElementById('priceLostAt').value
    		+ "&lead-lost-institute=" + document.getElementById('priceLostInst').value
    		+ "&lead-remarks=" + document.getElementById('remarks').value
    		+ "&pk-of-record=" + document.getElementById('pk-of-record').value
    );
}

// admin screen

function adminFormsShowHide(menuItem) {
	switch (menuItem.innerHTML) {
	case "Executive Deployment":
		document.getElementById("exec-deployment").style.display = 'block';
		document.getElementById("newUserId").style.display = 'none';
		document.getElementById("target").style.display = 'none';
		break;
	case "Add New User":
		document.getElementById("exec-deployment").style.display = 'none';
		document.getElementById("newUserId").style.display = 'block';
		document.getElementById("target").style.display = 'none';
		fetchUsers();
		break;
	case "Target Assignment":
		document.getElementById("exec-deployment").style.display = 'none';
		document.getElementById("newUserId").style.display = 'none';
		document.getElementById("target").style.display = 'block';
		fetchTargets(new Date().getFullYear() + "-" + ((new Date().getMonth() + 1) > 9 ? (new Date().getMonth() + 1) : "0" + (new Date().getMonth() + 1)));
		break;
	}
}

function fetchUsers() {
	var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET', 'add-new-user', true);
    ourRequest.onload = function () {
        var ourData = JSON.parse(ourRequest.responseText);
    	console.log(ourData);
        var combinedRows = '';
        for(var i = 0; i < ourData.jsonLeads.length; i++) { // creating rows, i.e. 'tr's
        	var row = '<tr>';
        	for(var j = 0; j < ourData.jsonLeads[i].length; j++) { // putting cells inside rows, i.e. 'td's
        		var cell;
        		cell = '<td>' + ourData.jsonLeads[i][j] + '</td>';
        		row += cell;
        	}
        	row += '</tr>';
        	combinedRows += row; // putting the newly created 'tr' in new html called combinedRows
        }
        var tbody = document.getElementById('admin-users-table');
        while(tbody.hasChildNodes())
        	tbody.removeChild(tbody.firstChild);
        tbody.insertAdjacentHTML('beforeend', combinedRows);
    }
    ourRequest.send();
}

function fetchTargets(year_month) {
	console.log(year_month)
	var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET', 'target?year-month=' + year_month, true);
    ourRequest.onload = function () {
        var ourData = JSON.parse(ourRequest.responseText);
    	console.log(ourData);

    	var inputsInTable = document.querySelector('#target > form > div:nth-child(3) > table > tbody').querySelectorAll('input');
    	for ( var i = 0; i < inputsInTable.length; i++) {
			inputsInTable[i].value = "";
		}
        for(var i = 0; i < ourData.jsonLeads.length; i++) {
        	var execId = ourData.jsonLeads[i][0];
        	document.getElementById('ta' + execId + 'w1').value = ourData.jsonLeads[i][2];
        	document.getElementById('ta' + execId + 'w2').value = ourData.jsonLeads[i][3];
        	document.getElementById('ta' + execId + 'w3').value = ourData.jsonLeads[i][4];
        	document.getElementById('ta' + execId + 'w4').value = ourData.jsonLeads[i][5];
        	document.getElementById('ta' + execId + 'm').value = parseInt(ourData.jsonLeads[i][2]) + parseInt(ourData.jsonLeads[i][3]) + parseInt(ourData.jsonLeads[i][4]) + parseInt(ourData.jsonLeads[i][5]);
        }
    }
    ourRequest.send();
}



// Dashboard section


function businesDevelopmentExecform(menuItem) {
	console.log(menuItem.innerHTML);
    switch (menuItem.innerHTML) {
	    case "Leaderboard":
	    	document.getElementById("leaderboard-id").style.display = 'block';
	        document.getElementById("bde-forms-section").style.display = 'none';
	    	document.getElementById("bdsubheading").innerHTML = "Leaderboard";
	        break;
        case "Revenue":
        	sendAjaxForGettingCurrentDashboardStats(this);
	    	document.getElementById("leaderboard-id").style.display = 'none';
	        document.getElementById("bde-forms-section").style.display = 'block';
            document.getElementById("bde-revenue").style.display = 'block';
            document.getElementById("bde-lead").style.display = 'none';
        	document.getElementById("bdsubheading").innerHTML = "Revenue";
            break;
        case "Lead":
        	sendAjaxForGettingCurrentDashboardStats(this);
	    	document.getElementById("leaderboard-id").style.display = 'none';
	        document.getElementById("bde-forms-section").style.display = 'block';
            document.getElementById("bde-revenue").style.display = 'none';
            document.getElementById("bde-lead").style.display = 'block';
        	document.getElementById("bdsubheading").innerHTML = "Lead";
            break;
    }
}


// target assignment divide amount

function divideAmt(v) {
    var val = v.value;
    var execId = v.id.substring(2,v.id.length-1);
    document.getElementById('ta' + execId + 'w1').value= val/4;
    document.getElementById('ta' + execId + 'w2').value= val/4;
    document.getElementById('ta' + execId + 'w3').value= val/4;
    document.getElementById('ta' + execId + 'w4').value= val/4;
}

function getTotalAmt(v) {
    var execId = v.id.substring(2,v.id.length-2);
    document.getElementById('ta' + execId + 'm').value = parseInt(document.getElementById('ta' + execId + 'w1').value) + parseInt(document.getElementById('ta' + execId + 'w2').value) + parseInt(document.getElementById('ta' + execId + 'w3').value) + parseInt(document.getElementById('ta' + execId + 'w4').value);
}


// checkbox and lead reassignment

function checkboxDisplayToggle() {
	if (document.querySelectorAll('tr > th:nth-child(1)')[0].style.display=='none') {
		document.querySelectorAll('tr > th:nth-child(1)')[0].removeAttribute('style');
		var i;
		for (i = 0; i < document.querySelectorAll('tr > td:nth-child(1)').length; i++)
			document.querySelectorAll('tr > td:nth-child(1)')[i].removeAttribute('style');
		document.querySelectorAll('#re-assign-to')[0].removeAttribute('style');
	} else {
		document.querySelectorAll('tr > th:nth-child(1)')[0].style.display='none';
		var i;
		for (i = 0; i < document.querySelectorAll('tr > td:nth-child(1)').length; i++)
			document.querySelectorAll('tr > td:nth-child(1)')[i].style.display = 'none';
		document.querySelectorAll('#re-assign-to')[0].style.display = 'none';
	}
}

function reassign() {
	var reassignConfirmation = confirm("Are you sure you want to reassign the selcted lead(s) to " + $("#re-assign-to").children("option").filter(":selected").text().trim() + "?");
	if (reassignConfirmation == true) {
		var list = [];
		for (var i = 0; i < document.querySelectorAll('input[name="lead-chkbox"]:checked').length; i++)
			list.push(document.querySelectorAll('input[name="lead-chkbox"]:checked')[i].value);
		sendData("reassign-lead", {'list': list, 'execId': $("#re-assign-to").children("option").filter(":selected").val()}, "POST");
		//reassignAjax();
	}	
}

//function reassignAjax() {
//	
//var ourRequest = new XMLHttpRequest();
//    
//	ourRequest.open('POST', 'reassign-lead', true);
//    ourRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//    
//    ourRequest.onload = function () {
//    	try {
//		    var ourData = JSON.parse(ourRequest.responseText);
//		    
//    	} catch(e) {}
//    }
//    
//    ourRequest.send("list=" + list + "&execId=" + $("#re-assign-to").children("option").filter(":selected").val());
//	
//	
//}