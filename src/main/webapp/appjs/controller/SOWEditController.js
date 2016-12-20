angular.module('sowEditDataCtrl', []).controller('SOWEditController', function ($scope,PMOHttpService,$location,$http,$filter) {

	$scope.items = ['SG1','SG2','SG3','SG4'];
	   $scope.startdate = "";
	   $scope.selectedTeam= "";
	   $scope.callouts= "";
	   $scope.src= "";
	   $scope.pjt= "";
	   $scope.defects= "";
	   $scope.deployment= "";
	   $scope.highlights= "";
	$scope.showalertmessage = function(isValid) {
		 if (isValid) {
		     
		$scope.sow = {};
		var createVacationOb = {}; 
		 createVacationOb.date= $scope.startdate;
		createVacationOb.team= $scope.selectedTeam;
		createVacationOb.weekendcallouts=  $scope.callouts;
		createVacationOb.sunrisechecks= $scope.src;
		createVacationOb.projectsdeployed= $scope.pjt;
		createVacationOb.defectsdeployed=$scope.defects;
		createVacationOb.upcomingdeployments= $scope.deployment;
		createVacationOb.anythingelsetohighlight= $scope.highlights;
		
		 var json = JSON.stringify(createVacationOb); 
		 $scope.insertSowList = PMOHttpService.getSowViewData(json).then(function(response){
			 $scope.insertresponse = response.data;
					 });
		 }
	
		
	}	
});