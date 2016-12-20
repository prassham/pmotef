angular.module('sidebarCtrl', []).controller('sidebarController', function ($scope,$location,PMOHttpService,$filter) {
	console.log('displaying Report page from the controller');
	
	$scope.user = PMOHttpService;
	$scope.resourcemanagementbutton = true;
	$scope.user.resourcemanagementbutton = $scope.resourcemanagementbutton;
	
	$scope.loginid = PMOHttpService.
								checkPMO().then(function(response){
									$scope.pmo = response.data;
									//alert(JSON.stringify($scope.pmo));
									//alert($scope.pmo[0]["WORKSTREAM"]);
									if($scope.pmo[0]["WORKSTREAM"] == "PMO"){
										$scope.resourcemanagementbutton = false;
										console.log("checking pmo" +$scope.resourcemanagement);
										$scope.user.resourcemanagementbutton = $scope.resourcemanagementbutton;
										//alert($scope.resourcemanagementbutton);
									}
	});
});

