angular.module('homeCtrl', []).controller('HomeController', function ($scope,PMOHttpService,$location) {
	console.log('displaying Vacation Tracker page from the controller');

	/*$scope.user = PMOHttpService;
	
	$scope.user.pending = true;
	$scope.pendingbutton = $scope.user.pending;
			$scope.loggedinuser = PMOHttpService
			.loginName().then(function (response) {
				$scope.loggedinuser = response.data;
				$scope.user.email = $scope.loggedinuser;
				var e = $scope.user.email;
				var id = "{\"email\""+":"+e +"}";
				
				$scope.logindetails = PMOHttpService
				.login(id).then(function (response) {
					$scope.loggedindetails = response.data;
					$scope.user.name = $scope.loggedindetails[0].NAME;
					
					$scope.employeews_manager = PMOHttpService
					.getEmployeeByName($scope.user.name).then(function(response) {
						$scope.user.pendingbutton = response.data;
						if($scope.user.pendingbutton == "exist"){
						$scope.user.pending= false;
						$scope.pendingbutton = $scope.user.pending;
						}
						else{
							$scope.user.pending =true;
							$scope.pendingbutton = $scope.user.pending;
						}
	});
	});
		});*/
}); 