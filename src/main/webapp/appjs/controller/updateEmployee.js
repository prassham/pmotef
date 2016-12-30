angular.module('UpdateEmployeeCtrl', []).controller('UpdateEmployeeController', function ($scope,$window,$filter,$location,PMOHttpService) {
	
	console.log('displaying Update Employee page from the controller');
	console.log("Selected employee details");
	
	$scope.buildingop = [];
	$scope.CurrentDate = new Date();
	
	$scope.user = PMOHttpService.selectedEmployee;
	$scope.user.DOJ_IBM = new Date($scope.user.DOJ_IBM);
	$scope.user.DOJ_O2 = new Date($scope.user.DOJ_O2);
	$scope.user.START_DATE = new Date($scope.user.START_DATE);
	$scope.user.END_DATE_GBSTIMESTAMP = new Date($scope.user.END_DATE_GBSTIMESTAMP);
	console.log(PMOHttpService.selectedEmployee.NAME);
	
	$scope.activeTab = 1;
	
	$scope.setActiveTab = function(tabToSet) {
		$scope.activeTab = tabToSet;
	}
	
	$scope.shortEmailPattern = PMOHttpService.getShortPattern();
	$scope.emailPattern = PMOHttpService.getPattern();
	$scope.mobileNumberPattern = PMOHttpService.getMobileNumberPattern();
	$scope.numberPattern = PMOHttpService.getNumberPattern();
	$scope.textPattern = PMOHttpService.getTextPattern();
	
	$scope.options = PMOHttpService
	.getOptions().then(function mySucces(response) {
        $scope.optionsdata = response.data;
        for(var i=0;i<$scope.optionsdata.buildingoptions.length;i++){
  		  if(($scope.value= $scope.optionsdata.buildingoptions[i][$scope.user.WORK_LOCATION])!= null){
  		  //alert($scope.optionsdata.buildingoptions[i][selectedlocation]);
  		   $scope.buildingop.push($scope.value);
  		  };
  		  
  	  }
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
		 //alert(selectedlocation);
	    	  for(var i=0;i<$scope.optionsdata.wsmanageroptions.length;i++){
	    		  if(($scope.optionsdata.wsmanageroptions[i][selectedstream])!= null){
	    		  //alert($scope.optionsdata.buildingoptions[i][selectedlocation]);
	    		   $scope.user.WS_MANAGER = $scope.optionsdata.wsmanageroptions[i][selectedstream];
	    		  //alert($scope.user.WS_MANAGER);
	    	  };
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
	 //calculate Tenure and Age Tenure
	  
	  $scope.createDate = function(DOJ_O2){
		    $scope.date1 = $scope.CurrentDate;
		    $scope.date2 = new Date(DOJ_O2);
		    $scope.timeDiff = Math.abs($scope.date1.getTime() - $scope.date2.getTime());   
		    $scope.diffDays = Math.ceil($scope.timeDiff / (1000 * 3600 * 24)); 
		    //alert($scope.diffDays);
		    $scope.RANGE_EXP = Math.round($scope.diffDays/365*100)/100;
		    $scope.TENURE = Math.round(($scope.diffDays/365)*12*100)/100;
		  
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
		    //alert($scope.user.TENURE);
		    
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
	  //To calculate Expires
	  $scope.checkExpires = function(enddate){
		  $scope.date1 = enddate;
		  $scope.date2 = $scope.CurrentDate;
		  $scope.timeDiff = Math.abs($scope.date1.getTime() - $scope.date2.getTime());   
		    $scope.diffDays = Math.ceil($scope.timeDiff / (1000 * 3600 * 24)); 
		    //alert($scope.diffDays);
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
			//alert($scope.user.EXPIRES);
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
	  
	$scope.update = function(item){
		item.REVISED_EMP_ID = item.EMP_ID;
		item.DOJ_IBM = $filter('date')(item.DOJ_IBM,'yyyy-MM-dd');
		item.DOJ_O2 = $filter('date')(item.DOJ_O2,'yyyy-MM-dd');
		item.START_DATE = $filter('date')(item.START_DATE,'yyyy-MM-dd');
		item.END_DATEGBSTIMESTAMP = $filter('date')(item.END_DATEGBSTIMESTAMP,'yyyy-MM-dd');
		//item.EXPIRES = $scope.EXPIRES;
		//item.TENURE = $scope.TENURE;
		//item.RANGE_EXP = $scope.RANGE_EXP;
		//item.AGE_TENURE = $scope.AGE_TENURE;
		//alert(item.STATUS);
		var json = JSON.stringify(item);
		var jsoninsert = json;
		/*$scope.deleteEmployeeData = PMOHttpService
		.deleteEmployee(item._id,item._rev)
					.then(function(response) {
						$scope.request = response.date;
					});
		
		
	alert(json);
		 $scope.insertEmployeeData = PMOHttpService
			.postEmployee(json).then(function(response) {
				$scope.request = response.data;
				alert(JSON.stringify($scope.request));
				alert(JSON.stringify($scope.request.message));
				$location.path('/landing');
			});*/
		
		$scope.updateEmployeeData = PMOHttpService
		.updateEmployee(json).then(function(response) {
			$scope.request = response.data;
			alert(JSON.stringify($scope.request.message));
			$location.path('/landing');
		});
		
	}
	$scope.back= function(item) {
		$location.path('/landing');

}
});