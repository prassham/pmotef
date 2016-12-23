angular.module('homeCtrl', []).controller('HomeController', function ($scope,PMOHttpService,$location) {
	console.log('displaying Vacation Tracker page from the controller');
	/*var init = function () {
		$scope.loggedinuser = PMOHttpService
		.loginName().then(function(response) {
			var id = response.data;
          }, function(response) {
            $location.path('/forbidden');
        });
		};
		init();*/
		$scope.user = PMOHttpService;
		
		$scope.user.pending = true;
		$scope.pendingbutton = $scope.user.pending;
		
		 $scope.loggedinuser = PMOHttpService
			.loginName().then(function (response) {
				var id = JSON.stringify(response.data);
				console.log(id);
				
				$scope.logindetails = PMOHttpService
				.login(id).then(function (response) {
					$scope.loggedindetails = response.data;
					//alert($scope.loggedindetails[0].NAME);
					$scope.user.name = $scope.loggedindetails[0].NAME;
					console.log($scope.user.name);
					$scope.employeews_manager = PMOHttpService
					.getEmployeeByName($scope.user.name).then(function(response) {
						//alert(response.data)
						$scope.user.pendingbutton = response.data;
						console.log($scope.user.pendingbutton);
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
	});
}); 