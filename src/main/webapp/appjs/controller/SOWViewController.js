angular.module('sowViewDataCtrl', []).controller('SOWViewController', function ($scope,PMOHttpService,$location,$http,$filter) {
	$scope.items = ['SG1','SG2','SG3','SG4'];
		
		 $scope.service_loop =[];
		 $scope.showdetails = function(startdate,selectedTeam) { 
	        	   $http.get("pmo/sowedit/view/"+ selectedTeam +"/"+ startdate +"").then(function(response){ 
	    		 	    	   $scope.service_loop = response.data;
	    		 	    	  $scope.callouts=$scope.service_loop[0].weekendcallouts;
	    		 	    	  $scope.src=  $scope.service_loop[0].sunrisechecks;
	    		 	    	$scope.pjt=  $scope.service_loop[0].projectsdeployed;
	    		 	    	$scope.defects=  $scope.service_loop[0].defectsdeployed;
	    		 	    	$scope.deployments=  $scope.service_loop[0].upcomingdeployments;
	    		 	    	$scope.highlights=  $scope.service_loop[0].anythingelsetohighlight;
	    		 	        	   });
		 }	
	});
