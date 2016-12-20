angular.module('TeamLeavesCtrl', []).controller('TeamLeavesController', function ($scope, PMOHttpService, $location){
	$scope.user = PMOHttpService;
	$scope.email =PMOHttpService.email;
	 $scope.today = new Date();
	/*$scope.name = $scope.user.name;*/
	$scope.pendingbutton = $scope.user.pending;
	$scope.notesid = null;
	
	$scope.pmNotesid = PMOHttpService.getNotesid(PMOHttpService.email).then(function (response){
		$scope.notes = response.data;
		$('#mydiv').hide();
	});
	$scope.empDetail = function(item){
		PMOHttpService.leaverecord= item;
		$location.path("/LeaveRecord");
	}
});
