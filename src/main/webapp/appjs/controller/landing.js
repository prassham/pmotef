angular.module('LandingCtrl', []).controller('LandingController', function ($scope, $location, $timeout,PMOHttpService,$route,$log) {
	
    console.log('displaying Landing  page');
    $scope.loading = false;
    
    $scope.tableDisply = false;
    $scope.employeeDetails = "";
    $scope.employeeReferenceDataList = [];
    $scope.employeePhoto = "";
    $scope.user = PMOHttpService;
    //alert($scope.user.resourcemanagementbutton);
    $scope.resourcemanagementbutton = false;
    // Data load spinner div
    console.log("Checking the commit point ...");
    $scope.loading=true;
    
    
$scope.user = PMOHttpService;
	
	$scope.user.pending = true;
	$scope.pendingbutton = $scope.user.pending;
	
 $scope.loggedinuser = PMOHttpService
		.loginName().then(function (response) {
			var id = JSON.stringify(response.data);
			console.log(id);
			
			$scope.logindetails = PMOHttpService
			.login(id).then(function (response) {
				$scope.loggedindetails = response.data;
				//alert($scope.loggedindetails[0].NAME);
				$scope.user.name = $scope.loggedindetails[0].NAME;
				console.log($scope.user.name);
				$scope.employeews_manager = PMOHttpService
				.getEmployeeByName($scope.user.name).then(function(response) {
					//alert(response.data)
					$scope.user.pendingbutton = response.data;
					console.log($scope.user.pendingbutton);
					if($scope.user.pendingbutton == "exist"){
					$scope.user.pending= false;
					$scope.pendingbutton = $scope.user.pending;
					}
					else{
						$scope.user.pending =true;
						$scope.pendingbutton = $scope.user.pending;
					}
				});
				
			});
});
	 
	 $scope.loginid = PMOHttpService.
		checkPMO().then(function(response){
			$scope.pmo = response.data;
			console.log(JSON.stringify($scope.pmo));
			if($scope.pmo["WORKSTREAM"] == "PMO"){
				console.log($scope.pmo[WORKSTREAM]);
				$scope.resourcemanagementbutton = true;
			}
			
});
	 
	 
	 $scope.numPerPage = 20;
	 $scope.currentPage = 0;
	 
	 
   $scope.loadReferenceDataList = PMOHttpService.getEmployeeCount()
   										.then(function (response) {
   											$scope.count= response.data;
   											console.log("Count " + JSON.stringify($scope.count));
   										},function (result) {
   										       console.log("The retrieve EmployeeList request failed: " + JSON.stringify(result));
   										});
   
   $('#mydiv').show();
   $scope.loadReferenceDataList = PMOHttpService.getEmployeeListPage($scope.currentPage).then(function (response) {
	   $('#mydiv').hide(); 
	   $scope.loading = false;
       $scope.tableDisply = false;
       $scope.employeeReferenceDataList = response.data;
       console.log($scope.employeeReferenceDataList);
       console.log("Successful retrieve EmployeeList response: " + JSON.stringify(response.data));   
   }, function (result) {
       console.log("The retrieve EmployeeList request failed: " + JSON.stringify(result));
   });
   
   $scope.loading = false;
   
    $scope.getEmployeePerCurrentPage = function(currentNo){
    	if(currentNo >= 1){
    	$scope.currentPage = currentNo -1 ;
    	}
    	else{
    		$scope.currenPage = currentNo;
    	}
    	$('#mydiv').show();
    	$scope.loadReferenceDataList = PMOHttpService.getEmployeeListPage($scope.currentPage).then(function (response) {
    		$('#mydiv').hide(); 
    		$scope.loading = false;
    	       $scope.tableDisply = false;
    	       $scope.employeeReferenceDataList = response.data;
    	       console.log($scope.employeeReferenceDataList);
    	       console.log("Successful retrieve EmployeeList response: " + JSON.stringify(response.data));   
    	   }, function (result) {
    	       console.log("The retrieve EmployeeList request failed: " + JSON.stringify(result));
    	       
    	   });
    }
    $scope.loading = false;
    
	$scope.showEmployee = function(item){
		console.log($scope.user.resourcemanagementbutton);
		if($scope.user.resourcemanagementbutton){
			console.log("Not authorized to edit");
		}
		else{
			PMOHttpService.selectedEmployee= item;
			$location.path("/updateEmployee");
		}
	}
});
