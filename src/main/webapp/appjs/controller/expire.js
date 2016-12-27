angular.module('ExpireCtrl', []).controller('ExpireController', function ($scope, $location, $timeout,PMOHttpService,$route,$log) {
	
    console.log('displaying page');
    $scope.loading = false;
    /*$scope.$on('$routeChangeStart',function(event,next,current){
    	if(!confirm("Are you want to navigate from current page")){
    		event.preventDefault();
    	}
    	$log.info(event);
    	$log.info(next);
    	$log.info(current);
    });*/

    $scope.numPerPage = 20;
	 $scope.currentPage = 0;
	 
	 $scope.loadReferenceDataList = PMOHttpService.getEmployeeCount()
		.then(function (response) {
			$scope.count= response.data;
			console.log("Count " + JSON.stringify($scope.count));
		},function (result) {
		       console.log("The retrieve EmployeeList request failed: " + JSON.stringify(result));
		});
	 
	 
   $scope.loadReferenceDataList = PMOHttpService.getEmployeeListPage($scope.currentPage).then(function (response) {
	   $('#mydiv').hide(); 
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
    	//$('#mydiv').show();
    	$scope.loadReferenceDataList = PMOHttpService.getEmployeeListPage($scope.currentPage).then(function (response) {
    		$('#mydiv').hide(); 
    	       $scope.employeeReferenceDataList = response.data;
    	       console.log($scope.employeeReferenceDataList);
    	       console.log("Successful retrieve EmployeeList response: " + JSON.stringify(response.data));   
    	   }, function (result) {
    	       console.log("The retrieve EmployeeList request failed: " + JSON.stringify(result));
    	       
    	   });
    }
});
