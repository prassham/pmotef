angular.module('RequestHistoryCtrl', []).controller('RequestHistoryController', function ($scope, PMOHttpService, $window, $location){
	var vacationhistory = [];
	$scope.CurrentDate = new Date();
	$scope.user = PMOHttpService;
	$scope.pendingbutton = $scope.user.pending;
	$scope.uservacation=PMOHttpService
						.getVacationHistory($scope.user.intranetID)
						.then(
								function(response) {
									$('#mydiv').hide();
									$scope.vacationhistory = response.data;
									$scope.val = "Vacation request for";
									$scope.name = $scope.vacationhistory[0].name;
									$scope.email = $scope.vacationhistory[0].email;
								});
	 /*$scope.deleteRequest= function(_id, _rev){
		 if ($window.confirm("Are you sure, you want to delete the vacatioon request?"))
			 $scope.request = PMOHttpService
				.deleteVacation(_id, _rev)
				.then(
						function(response){
							$scope.request1 = response.data;
							alert(JSON.stringify($scope.request.message));
							javascript:window.location.reload();
						})*/
		/* else
		 $scope.result = "No";
	 }*/
	 $scope.updateRequest= function(_id){
	 	$('#mydiv').show();
		 $scope.request1 =PMOHttpService
				.updateVacation(_id)
				.then(
						function(response){
							$('#mydiv').hide();
							$scope.request = response.data;
							PMOHttpService.name = $scope.request[0].name;
							PMOHttpService._id = $scope.request[0]._id;
							PMOHttpService._rev = $scope.request[0]._rev;
							PMOHttpService.empid = $scope.request[0].empid;
							PMOHttpService.vacationtype = $scope.request[0].vacationtype;
							PMOHttpService.startDate = $scope.request[0].startDate;
							PMOHttpService.endDate = $scope.request[0].endDate;
							PMOHttpService.requestedDate = $scope.request[0].requestedDate;
							$location.path('/updateRequest');
						})
		 }
});