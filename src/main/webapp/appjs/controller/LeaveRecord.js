angular.module('LeaveRecordCtrl', []).controller('LeaveRecordController', function ($scope, PMOHttpService, $location){
	$scope.empemail =PMOHttpService.leaverecord.EMAIL;
	$scope.name = PMOHttpService.leaverecord.NAME;
	$scope.today = new Date();
	$scope.record = PMOHttpService.getLeaverecord($scope.empemail).then(function (response){
		$scope.record = response.data;
		$('#mydiv').hide();
	});
});
