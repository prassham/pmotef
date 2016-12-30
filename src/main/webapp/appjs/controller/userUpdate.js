angular.module('UserUpdateCtrl', []).controller('UserUpdateController', function ($scope,$location,PMOHttpService,$filter) {
	console.log('displaying Report page from the controller');
	
	$scope.activeTab = 1;
	$scope.CurrentDate = new Date();
	$scope.user = PMOHttpService;
	
	$scope.setActiveTab = function(tabToSet) {
		$scope.activeTab = tabToSet;
	}
	 $scope.user.START_DATE = $scope.CurrentDate;
	  //alert($scope.user.START_DATE);
	$scope.count = 0;
	
	/*$scope.typeoptions = {1:"RF",2:"CF"}

	$scope.shoreoptions = {1:"Onshore",
					  	   2:"Offshore"}
	$scope.workstreamoptions ={1:"SG1",
	 					  	   2:"SG2",
	 					  	   3:"SG3",
	 					  	   4:"SG4",
	 					  	   5:"E2E",
	 					  	   6:"TEST",
	 					  	   7:"TDA",
	 					  	   8:"ENVIRONMENTAL SERVICES",
	 					  	   9:"PM",
	 					  	   10:"TIG",
	 					  	   11:"DPE",
	 					  	   12:"CIM"}
	$scope.genderoptions ={1:"Male",
	 					  2:"Female"}
	 
	$scope.laptopoptions = {1:"Yes",
	 					  2:"No"}*/
	$scope.shortEmailPattern = PMOHttpService.getShortPattern();
	$scope.emailPattern = PMOHttpService.getPattern();
	$scope.mobileNumberPattern = PMOHttpService.getMobileNumberPattern();
	$scope.numberPattern = PMOHttpService.getNumberPattern();
	$scope.textPattern = PMOHttpService.getTextPattern();
	
	 $scope.options = PMOHttpService
		.getOptions().then(function mySucces(response) {
	        $scope.optionsdata = response.data;
	        //alert(JSON.stringify($scope.optionsdata.ELTPoptions));
	    }, function myError(response) {
	        $scope.optionsdata = response.statusText;
	        alert($scope.optionsdata);
	 });
	 
	 $scope.selectedWorkLocation = function(selectedlocation){
		 //alert(selectedlocation);
	      $scope.buildingop = [];
	    	  for(var i=0;i<$scope.optionsdata.buildingoptions.length;i++){
	    		  if(($scope.value= $scope.optionsdata.buildingoptions[i][selectedlocation])!= null){
	    		  //alert($scope.optionsdata.buildingoptions[i][selectedlocation]);
	    		   $scope.buildingop.push($scope.value);
	    		  
	    	  };
	    	  }
	    	  //alert($scope.buildingop);
	 
	 }
	 $scope.selectedWorkstream = function(selectedstream){
		 if(!selectedstream){
			  //alert("Inside elseif"+!selectedstream);
    		  $scope.user.WS_MANAGER =" ";
		 }
	    for(var i=0;i<$scope.optionsdata.wsmanageroptions.length;i++){
	    	if(($scope.optionsdata.wsmanageroptions[i][selectedstream])!= undefined && ($scope.optionsdata.wsmanageroptions[i][selectedstream])!=null ){
	    		  //alert($scope.optionsdata.buildingoptions[i][selectedlocation]);
	    		   $scope.user.WS_MANAGER = $scope.optionsdata.wsmanageroptions[i][selectedstream];
	    		  //alert($scope.user.WS_MANAGER);
	    	  }
	    }
	    	  //alert($scope.buildingop);
	 
	 }
	 
	 $scope.valuationDatePickerdoj_ibmIsOpen = false;
	 $scope.valuationDatePickerdoj_o2IsOpen = false;
	 $scope.valuationDatePickerstartdateIsOpen = false;
	 $scope.valuationDatePickerenddateIsOpen = false;

	  $scope.valuationDatePickerdojibmOpen = function ($event) {

	      if ($event) {
	          $event.preventDefault();
	          $event.stopPropagation(); // This is the magic
	      }
	      $scope.valuationDatePickerdoj_ibmIsOpen = true;
	      $scope.valuationDatePickerdoj_o2IsOpen = false;
	      $scope.valuationDatePickerstartdateIsOpen = false;
	 	 $scope.valuationDatePickerenddateIsOpen = false;
	  };
	  $scope.valuationDatePickerdojo2Open = function ($event) {

	      if ($event) {
	          $event.preventDefault();
	          $event.stopPropagation();
	          // This is the magic

	      }
	      $scope.valuationDatePickerdoj_ibmIsOpen = false;
	      $scope.valuationDatePickerdoj_o2IsOpen = true;
	      $scope.valuationDatePickerstartdateIsOpen = false;
	 	 $scope.valuationDatePickerenddateIsOpen = false;

	  };
	  $scope.valuationDatePickerstartOpen = function ($event) {

	      if ($event) {
	          $event.preventDefault();
	          $event.stopPropagation();
	          // This is the magic

	      }
	      $scope.valuationDatePickerdoj_ibmIsOpen = false;
	      $scope.valuationDatePickerdoj_o2IsOpen = false;
	      $scope.valuationDatePickerstartdateIsOpen = true;
	 	 $scope.valuationDatePickerenddateIsOpen = false;

	  };
	  $scope.valuationDatePickerendOpen = function ($event) {

	      if ($event) {
	          $event.preventDefault();
	          $event.stopPropagation();
	          // This is the magic

	      }
	      $scope.valuationDatePickerdoj_ibmIsOpen = false;
	      $scope.valuationDatePickerdoj_o2IsOpen = false;
	      $scope.valuationDatePickerstartdateIsOpen = false;
	 	 $scope.valuationDatePickerenddateIsOpen = true;

	  };
	  
	  //calculate Tenure and AgeTenure
	  $scope.createDate = function(DOJ_O2){
			    $scope.date1 = $scope.CurrentDate;
			    $scope.date2 = new Date(DOJ_O2);
			    $scope.timeDiff = Math.abs($scope.date1.getTime() - $scope.date2.getTime());   
			    $scope.diffDays = Math.ceil($scope.timeDiff / (1000 * 3600 * 24)); 
			    
			    $scope.RANGE_EXP = Math.round($scope.diffDays/365*100)/100;
			    $scope.TENURE = Math.round(($scope.diffDays/365)*12*100)/100;
			  
			   //Calculating Age Tenure
			    if($scope.TENURE > 48){
			    	$scope.AGE_TENURE = "Greater than 48 Months";
			    }
			    else if($scope.TENURE < 48 && $scope.TENURE > 24){
			    	$scope.AGE_TENURE = "24-48 Months";
			    }
			    else if($scope.TENURE < 24 && $scope.TENURE > 18){
			    	$scope.AGE_TENURE = "18-24 Months";
			    }
			    else {
			    	$scope.AGE_TENURE = "18 Months";
			    }
			    $scope.user.AGE_TENURE = $scope.AGE_TENURE;
			    $scope.user.TENURE = $scope.TENURE;
			    
			    //calculating Previous Experience
			    if($scope.RANGE_EXP > 5){
			    	$scope.RANGE_EXP = "Greater than 5 Years";
			    }
			    else if($scope.RANGE_EXP > 4){
			    	$scope.RANGE_EXP = "4-5 Years";
			    }
			    else if($scope.RANGE_EXP > 3){
			    	$scope.RANGE_EXP = "3-4 Years";
			    }
			    else if($scope.RANGE_EXP > 2){
			    	$scope.RANGE_EXP = "2-3 Years";
			    }
			    else if($scope.RANGE_EXP > 1){
			    	$scope.RANGE_EXP = "1-2 Years";
			    }
			    else{
			    	$scope.RANGE_EXP = "Less than one Year";
			    }
			    $scope.user.RANGE_EXP = $scope.RANGE_EXP;
	  }
	  
	  $scope.checktab = function(user){
		  if(user.EMP_ID == undefined ||user.NAME == undefined || user.NOTES_ID == undefined || user.EMAIL == undefined || user.TYPE == undefined){
			  $scope.activeTab = 1;
		  }
		  else if(user.WORK_LOCATION == undefined || user.BUILDING == undefined || user.ON_OFF_SHORE == undefined){
			  $scope.activeTab = 2;
		  }
		  else if(user.WORKSTREAM == undefined || user.SKILL_SET == undefined || user.CURRENT_ROLE == undefined || user.PEM == undefined || user.DOJ_IBM == undefined || user.DOJ_O2 == undefined){
			  $scope.activeTab = 3;
		  }
		  else{
			  $scope.activeTab = 5;
		  }
	  }
	  
	//calculate Expires
	  $scope.checkExpires = function(enddate){
		  $scope.date1 = enddate;
		  $scope.date2 = $scope.CurrentDate;
		  $scope.timeDiff = Math.abs($scope.date1.getTime() - $scope.date2.getTime());   
		    $scope.diffDays = Math.ceil($scope.timeDiff / (1000 * 3600 * 24)); 
		    alert($scope.diffDays);
			if($scope.diffDays > 31){
				$scope.EXPIRES ="green";
			}
			else if($scope.diffDays > 7 && $scope.diffDays <= 31){
				$scope.EXPIRES = "#FFA500";
			}
			else{
				$scope.EXPIRES = "red";
			}
			$scope.user.EXPIRES = $scope.EXPIRES;
			alert($scope.user.EXPIRES);
	  }
	  
	$scope.insert = function(user){
		 $scope.registered = true;
		user.REVISED_EMP_ID = user.EMP_ID;
		user.STATUS = "Active";
		user.EXPIRES = $scope.user.EXPIRES;
		user.DOJ_IBM = $filter('date')(user.DOJ_IBM,'yyyy-MM-dd');
		user.DOJ_O2 = $filter('date')(user.DOJ_O2,'yyyy-MM-dd');
		user.START_DATE = $filter('date')(user.START_DATE,'yyyy-MM-dd');
		user.END_DATE_GBSTIMESTAMP = $filter('date')(user.END_DATE_GBSTIMESTAMP,'yyyy-MM-dd');
		var employeejson = JSON.stringify(user);
		 $scope.insertEmployeeData = PMOHttpService
			.postEmployee(employeejson).then(function(response) {
				$scope.request = response.data;
				alert(JSON.stringify($scope.request.message));
				$location.path('/landing');
			});
	}
});

