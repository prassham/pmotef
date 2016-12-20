angular.module('LoginCtrl', []).controller('LoginController', function ($scope, $location, PMOHttpService) {
    console.log('displaying loging page from the controller');

    $scope.sidebarStatus = true;
   
    $scope.authenticate = function () {
    	 
        console.log('authenticating the user' + $scope.email + '  ' + $scope.password);
        $scope.user = PMOHttpService;
        $scope.user.email = $scope.email;
        $scope.user.password = $scope.password;
        $scope.user.name;
        $scope.user._id;
        $scope.user._rev;
        $scope.user.empid;
        $scope.user.vacationtype;
        $scope.user.startDate;
        $scope.user.endDate;
        $scope.user.requestedDate;
        //Prepare the data to be sent as JSON to backend
        var authData;
        PMOHttpService.autheticate(authData).success(function (data, status) {
            console.log(data);
    	   
            //On successful authentication.
            $location.path('/landing');
        })

        .error(function (error, status) {
            console.log(status);
            console.log(error);
            //parse the error and display on the error div
        });
    };
});
