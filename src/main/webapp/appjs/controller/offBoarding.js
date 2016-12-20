angular.module('offBoardingCtrl', []).controller('offBoardingController', function ($scope,$location,PMOHttpService) {
	console.log('displaying Report page from the controller');
	
	$scope.tableDisplay = false;
	$('#mydiv').show();
	$scope.historyrecord = PMOHttpService.getHistoryList().then(function(response){
		$('#mydiv').hide(); 
		$scope.history = response.data;
	});
	});