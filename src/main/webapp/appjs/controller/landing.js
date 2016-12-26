angular.module('LandingCtrl', []).controller('LandingController', function ($scope, $location, $timeout,PMOHttpService,$route,$log) {
	
    console.log('displaying Landing  page');
    $scope.loading = false;
    
    $scope.tableDisply = false;
    $scope.employeeDetails = "";
    $scope.employeeReferenceDataList = [];
    $scope.employeePhoto = "";
    //alert($scope.user.resourcemanagementbutton);
	//$scope.user.resourcemanagementbutton = false;
	//$scope.resourcemanagementbutton = $scope.user.resourcemanagementbutton;
    // Data load spinner div
    console.log("Checking the commit point ...");
    $scope.loading=true;
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
