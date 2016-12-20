angular.module('UserUpdateCtrl', []).controller('UserUpdateController', function ($scope,$location,PMOHttpService,$filter) {
	console.log('displaying Report page from the controller');
	
	$scope.activeTab = 1;
	$scope.CurrentDate = new Date();
	
	$scope.setActiveTab = function(tabToSet) {
		$scope.activeTab = tabToSet;
	}
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
		
	$scope.insert = function(user){
		 $scope.registered = true;
		user.REVISED_EMP_ID = user.EMP_ID;
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

