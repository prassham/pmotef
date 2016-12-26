angular.module('updateRequestCtrl', []).controller('updateRequestController', function ($scope,PMOHttpService,$location,$filter,$window,$route) {
	console.log('displaying Vacation Tracker page from the controller');
	$scope.user = PMOHttpService;
	$scope.pendingbutton = $scope.user.pending;
	$scope.CurrentDate = new Date();
	$scope.CurrentDate = new Date();
	var currentYear =  $scope.CurrentDate.getFullYear();
	$scope.reason = "Vacation";
	var e = PMOHttpService.intranetID;
	var name = PMOHttpService.name;
	var _id = PMOHttpService._id;
	var _rev = PMOHttpService._rev;
	var empid = PMOHttpService.empid;
	var vacationtype = PMOHttpService.vacationtype;
	var startDate = PMOHttpService.startDate;
	var endDate = PMOHttpService.endDate;
	var requestedDate = PMOHttpService.requestedDate ;
	var id = "{\"email\""+":"+e +"}";
	$('#mydiv').show();
						$scope.valuationstartDate = startDate;
						$scope.valuationendDate = endDate;
						$scope.leaveType = "";
						$scope.currentdate = "";
	$scope.TodayVacationList = PMOHttpService
		.login(e).then(function (response) {
			$('#mydiv').hide();
					$scope.VacationList = response.data;
					for(var i =0;i< $scope.VacationList.length;i++){
						if(PMOHttpService.intranetID.replace("\"", "").replace("\"", "") == $scope.VacationList[i].EMAIL){
						$scope.email = $scope.VacationList[i].EMAIL; 
						$scope.loggedInUser = $scope.VacationList[i].NOTES_ID;
						$scope.team = $scope.VacationList[i].WORKSTREAM;
						$scope.approver = $scope.VacationList[i].WS_MANAGER;
						$scope.empid = $scope.VacationList[i].EMP_ID;
						$scope.name = $scope.VacationList[i].NAME;
						$scope.valuationstartDate = startDate;
						$scope.valuationendDate = endDate;
						console.log($scope.valuationstartDate);
						console.log($scope.valuationendDate);
						$scope.leaveType = vacationtype;
					}
				}
});
	  $scope.currentdate = $filter('date')(new Date(),'yyyy-MM-dd');
	  var dateArray = {};
	  $scope.dateArr = new Array();
	  var createVacationOb = {};
	  $scope.valuationDate = new Date();
	  $scope.valuationDatePickerstartdateIsOpen = false;
	  $scope.valuationDatePickerenddateIsOpen = false;

	  $scope.valuationDatePickerstartOpen = function ($event) {

	      if ($event) {
	          $event.preventDefault();
	          $event.stopPropagation(); // This is the magic
	      }
	      $scope.valuationDatePickerstartdateIsOpen = true;
	      $scope.valuationDatePickerenddateIsOpen = false;
	  };
	  $scope.valuationDatePickerendOpen = function ($event) {

	      if ($event) {
	          $event.preventDefault();
	          $event.stopPropagation();
	          // This is the magic

	      }
	      $scope.valuationDatePickerstartdaterIsOpen = false;
	      $scope.valuationDatePickerenddateIsOpen = true;

	  };
	  /*$scope.createDate = function(startDate, endDate) {
		    var millisecondsPerDay = 86400 * 1000; 
		    startDate.setHours(0,0,0,1);  
		    endDate.setHours(23,59,59,999);  
		    var diff = endDate - startDate;     
		    var days = Math.ceil(diff / millisecondsPerDay);
		    
		    // Subtract two weekend days for every week in between
		    var weeks = Math.floor(days / 7);
		    days = days - (weeks * 2);

		    // Handle special cases
		    var startDay = startDate.getDay();
		    var endDay = endDate.getDay();
		    
		    // Remove weekend not previously removed.   
		    if (startDay - endDay > 1)         
		        days = days - 2;      
		    
		    // Remove start day if span starts on Sunday but ends before Saturday
		    if (startDay === 0 && endDay != 6)
		        days = days - 1 ; 
		            
		    // Remove end day if span ends on Saturday but starts after Sunday
		    if (endDay === 6 && startDay !== 0)
		        days = days - 1  ;
		    
		    return days;
		}
	  $scope.cal = function(){
		  var a = new Date($scope.valuationstartDate); 
		  alert(a);
		  var b = new Date($scope.valuationendDate);
		  var t = $scope.createDate(a, b);
		  alert(t);
	  }*/
	  
		/*var t = $scope.createDate($scope.valuationstartDate, $scope.valuationendDate);*/
		
	$scope.createDate = function(){
		  Date.prototype.addDays = function(days) {
		       var dat = new Date(this.valueOf())
		       dat.setDate(dat.getDate() + days);
		       return dat;
		   }

		   function getDates(startDate, stopDate) {
		      var dateArray = new Array();
		      var currentDate = startDate;
		      while (currentDate <= stopDate) { 
		        dateArray.push(currentDate);
		       currentDate = currentDate.addDays(1);
		      }
		      return dateArray;
		    }
		   console.log($scope.valuationstartDate);
			console.log($scope.valuationendDate);
			var a = new Date($scope.valuationstartDate); 
			var b = new Date($scope.valuationendDate);
		   $scope.dateArray = getDates(a, b);

		function isWorkingDay(date){
			if(date.getDay() != 6 && date.getDay() != 0){
				return date;
			}
		}
		$scope.dateArr = $scope.dateArray.filter(isWorkingDay);
		$scope.noofday = $scope.dateArr.length;
		   };

	  $scope.updateRequest = function(){
	  	$('#mydiv').show();
	  	 $scope.startdate = $filter('date')($scope.valuationstartDate,'yyyy-MM-dd');
		  $scope.enddate = $filter('date')($scope.valuationendDate,'yyyy-MM-dd');
		  $scope.todaydate= $filter('date')($scope.CurrentDate,'yyyy-MM-dd');
		  var firstFour = $scope.startdate.substring(0,4);
		  var firstFour1 = $scope.enddate.substring(0,4);
		  console.log($scope.startdate);
		  console.log( $scope.enddate);
		  console.log(firstFour);
		  console.log(currentYear);
	  	if($scope.startdate>$scope.enddate){
	  		alert("Start date cannot be greater than End date");
	  		$route.reload();
	  	}
	  	else if(firstFour!=currentYear || firstFour1!=currentYear){
	  	alert("Vacation can only be claimed for current year");
	  		$route.reload();	
	  	}
	  	else if($scope.startdate<$scope.todaydate){
	  		alert("Vacation can only be claimed for current date or future dates");
	  		$route.reload();
	  		
	  	}
	  	else {
		  $scope.message = "Vacation request created successfully.";
		  createVacationOb.empid= $scope.empid;
		  createVacationOb.name= $scope.name;
		  createVacationOb.email = $scope.email;
		  createVacationOb.notes_id = $scope.loggedInUser;
		  createVacationOb.reason = $scope.reason;
		  createVacationOb.vacationtype = $scope.leaveType;
		  createVacationOb.vacationdays = $scope.leavedays;
		  createVacationOb.startDate = $scope.startdate;
		  createVacationOb.endDate = $scope.enddate;
		  createVacationOb.no_of_days = $scope.dateArr.length;
		  createVacationOb.team = $scope.team;
		  createVacationOb.approver = $scope.approver;
		  createVacationOb.requeststate = "Pending";
		  createVacationOb.requestedDate = $scope.currentdate;
		  
		  /*$scope.user.ob =createVacationOb;
		  var ins = angular.toJson($scope.user.ob);*/
		  $scope.deleteVacationList = PMOHttpService
			.deleteVacation(PMOHttpService._id, PMOHttpService._rev).then(function (response) {
				console.log("Vacation request deleted");
				$scope.request = response.data;
			
		  var json = JSON.stringify(createVacationOb);
		  $scope.insertVacationList = PMOHttpService
			.insertVacation(json).then(function (response) {
				console.log("vacation request updated");
				$scope.request = response.data;
				//alert(JSON.stringify($scope.request.message));			
		  $location.path('/RequestHistory');
			});
			});
		}

	  };
	  $scope.deleteRequest= function(_id, _rev){
	  	$('#mydiv').show();
			 if ($window.confirm("Are you sure, you want to delete the vacatioon request?"))
				 $scope.request = PMOHttpService
					.deleteVacation(PMOHttpService._id, PMOHttpService._rev)
					.then(
							function(response){
								$scope.request1 = response.data;
								$('#mydiv').hide();
								$location.path('/RequestHistory');
							})
	  }
	  $scope.cancelRequest = function(){
		  $location.path('/vacationPlanner');
	  }
	  $scope.resetRequest = function(){
		  $scope.master = {firstName: "John", lastName: "Doe"};
		  $location.path('/vacationPlanner');
	  }
}); 