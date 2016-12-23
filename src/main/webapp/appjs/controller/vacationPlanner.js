angular.module('vacationPlannerCtrl', []).controller('vacationPlannerController', function ($scope,PMOHttpService,$location) {
	console.log('displaying Vacation Tracker page from the controller');
	
	$scope.user = PMOHttpService;
	
	$scope.pendingbutton = $scope.user.pending;
	$scope.loginid = $scope.user.intranetID;
	/*$scope.loggedinuser = PMOHttpService
	.loginidCheck().then(function (response) {
		$scope.loginid = response.data;
		
});*/

	 $scope.TodayVacationList = PMOHttpService
	 								.getVacationList().then(function (response) {
	 									$scope.VacationList = response.data;
	 									$('#mydiv').hide();	
	 									
     });
	 
	 $scope.user = PMOHttpService;
	 $scope.createRequest= function(){
		 $location.path('/CreateVacationRequest');
	 }
	 $scope.vacationHistory= function(){
		 $location.path('/RequestHistory');
	 }
	 
});