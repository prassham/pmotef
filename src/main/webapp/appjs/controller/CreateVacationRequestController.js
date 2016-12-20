angular.module('CreateVacationRequestCtrl', []).controller('CreateVacationRequestController', function ($scope,PMOHttpService,$location,$filter,$window,$route) {
	console.log('displaying Vacation Tracker page from the controller');

	$scope.user = PMOHttpService;
	$scope.pendingbutton = $scope.user.pending;
	$scope.vacation = {};
	$scope.CurrentDate = new Date();
	var currentYear =  $scope.CurrentDate.getFullYear();
	$scope.reason = "Vacation";
	var e = PMOHttpService.email;
	var id = "{\"email\""+":"+e +"}";
	$('#mydiv').show();
	
	$scope.TodayVacationList = PMOHttpService
		.login(id).then(function (response) {
					$('#mydiv').hide();
					$scope.VacationList = response.data;
					for(var i =0;i< $scope.VacationList.length;i++){
						if($scope.user.email == $scope.VacationList[i].EMAIL){
						$scope.email = $scope.VacationList[i].EMAIL; 
						$scope.loggedInUser = $scope.VacationList[i].NOTES_ID;
						$scope.team = $scope.VacationList[i].WORKSTREAM;
						$scope.approver = $scope.VacationList[i].WS_MANAGER;
						$scope.empid = $scope.VacationList[i].EMP_ID;
						$scope.name = $scope.VacationList[i].NAME;
						
					}
				}
});
	  $scope.currentdate = $filter('date')(new Date(),'yyyy-MM-dd');
	  var dateArray = {};
	  var dateArr = new Array();
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
		        dateArray.push(currentDate)
		        currentDate = currentDate.addDays(1);
		      }
		      return dateArray;
		    }

		   $scope.vacation.dateArray = getDates($scope.vacation.valuationstartDate, $scope.vacation.valuationendDate);

		function isWorkingDay(date){
			if(date.getDay() != 6 && date.getDay() != 0){
				return date;
			}
		}
		$scope.vacation.dateArr = $scope.vacation.dateArray.filter(isWorkingDay);
		$scope.vacation.noofday = $scope.vacation.dateArr.length;
		   }

	  $scope.createRequest = function(){
	  	 $scope.startdate = $filter('date')($scope.vacation.valuationstartDate,'yyyy-MM-dd');
		  $scope.enddate = $filter('date')($scope.vacation.valuationendDate,'yyyy-MM-dd');
		  $scope.todaydate= $filter('date')($scope.CurrentDate,'yyyy-MM-dd');
	  	if($scope.vacation.valuationstartDate>$scope.vacation.valuationendDate){
	  		alert("Start date cannot be greater than End date");
	  		$route.reload();
	  	}
	  	else if($scope.vacation.valuationstartDate.getFullYear()!==currentYear || $scope.vacation.valuationendDate.getFullYear()!==currentYear){
	  	alert("Vacation can only be claimed for current year");
	  		$route.reload();	
	  	}
	  	else if($scope.startdate<$scope.todaydate){
	  		alert("Vacation can only be claimed for current date or future dates");
	  		$route.reload();
	  		
	  	}
	  	else {
		 
		  $scope.message = "Vacation request created successfully.";
		   if($scope.vacation.dateArr.length==0)
	   {
	   	alert("Please select a weekday");
		$route.reload(); 
	   }
		
		  createVacationOb.empid= $scope.empid;
		  createVacationOb.name= $scope.name;
		  createVacationOb.email = $scope.email;
		  createVacationOb.notes_id = $scope.loggedInUser;
		  createVacationOb.reason = $scope.reason;
		  createVacationOb.vacationtype = $scope.vacation.leaveType;
		  createVacationOb.startDate = $scope.startdate;
		  createVacationOb.endDate = $scope.enddate;
		  createVacationOb.no_of_days = $scope.vacation.dateArr.length;
		  createVacationOb.team = $scope.team;
		  createVacationOb.approver = $scope.approver;
		  createVacationOb.requeststate = "Pending";
		  createVacationOb.requestedDate = $scope.currentdate;
		 
		  /*$scope.user.ob =createVacationOb;
		  var ins = angular.toJson($scope.user.ob);*/
		  var json = JSON.stringify(createVacationOb);
		  $scope.insertVacationList = PMOHttpService
			.insertVacation(json).then(function (response) {
				$scope.request = response.data;
				//alert(JSON.stringify($scope.request.message));
				/*var link = "mailto:"+$scope.approver
				             + "?subject=Leave%20Request " 
				             + "&body=Hi%20" +$scope.approver+",%0a%0aBelow are the vacation details submitted by%20"+$scope.name+"%0a%0aReason:%20"+$scope.reason+"%20%20%20%20%20%20%20%20%20%20%20%20%20%20 No of Days:%20"+$scope.vacation.dateArr.length+"%0a%0aStart Date:%20"+$scope.startdate+"%20%20%20%20%20%20%20%20 Team:%20"+$scope.team+"%0a%0a End date:%20"+$scope.enddate+"%20%20%20%20%20%20%20%20 Leave Type:%20"+$scope.vacation.leaveType+"%0a%0a"; 
*/
				    //window.location.href = link;
				   // alert("Please navigate to your lotus notes to complete your vacation request");
		  $location.path('/RequestHistory');
			}, function(response) {
            $scope.request = response.data || "Request failed";
            this.status = response.status;
            alert(JSON.stringify($scope.request.message));
        });

	  }
	  }
	  $scope.cancelRequest = function(){
		  $location.path('/vacationPlanner');
	  }
	  $scope.reset = function(vacation){
			 $scope.vacationReset = {};
			 $scope.vacation = angular.copy($scope.vacationReset);
		  }
}); 