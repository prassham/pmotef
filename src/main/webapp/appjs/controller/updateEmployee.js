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
	$scope.update = function(item){
		item.REVISED_EMP_ID = item.EMP_ID;
		item.DOJ_IBM = $filter('date')(item.DOJ_IBM,'yyyy-MM-dd');
		item.DOJ_O2 = $filter('date')(item.DOJ_O2,'yyyy-MM-dd');
		item.START_DATE = $filter('date')(item.START_DATE,'yyyy-MM-dd');
		item.END_DATEGBSTIMESTAMP = $filter('date')(item.END_DATEGBSTIMESTAMP,'yyyy-MM-dd');
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