angular.module('TeamLeavesCtrl', []).controller('TeamLeavesController', function ($scope, PMOHttpService, $location){
	$scope.user = PMOHttpService;
	$scope.email =$scope.user.intranetID.replace("\"", "").replace("\"", "");
	 $scope.today = new Date();
	/*$scope.name = $scope.user.name;*/
	$scope.pendingbutton = $scope.user.pending;
	$scope.notesid = null;
	
	$scope.pmNotesid = PMOHttpService.getNotesid($scope.email).then(function (response){
		$scope.notes = response.data;
		$('#mydiv').hide();
	});
	$scope.empDetail = function(item){
		PMOHttpService.leaverecord= item;
		$location.path("/LeaveRecord");
	}
});
