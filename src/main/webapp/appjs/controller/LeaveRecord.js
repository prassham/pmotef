angular.module('LeaveRecordCtrl', []).controller('LeaveRecordController', function ($scope, PMOHttpService, $location){
	$scope.empemail =PMOHttpService.intranetID.replace("\"", "").replace("\"", "");
	$scope.name = $scope.empemail;
	$scope.today = new Date();
	$scope.record = PMOHttpService.getLeaverecord($scope.empemail).then(function (response){
		$scope.record = response.data;
		$('#mydiv').hide();
	});
});
