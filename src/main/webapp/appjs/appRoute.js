angular.module('appRoutes', []).config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {

    //console.log('Application routing logic');

    $routeProvider

        .when('/', {
            templateUrl: 'views/components/homePage.html',
            controller: 'HomeController'
        })
        .when('/forbidden', {
            templateUrl: 'views/components/forbidden.html'
        })
        .when('/landing', {
            templateUrl: 'views/components/landingPage.html',
            controller: 'LandingController'
        })
        .when('/newsletter', {
            templateUrl: 'views/components/newsLetter.html'
            //controller: 'LandingController'
        })
        .when('/home', {
            templateUrl: 'views/components/homePage.html',
            controller: 'HomeController'
        })
        .when('/utilization', {
            templateUrl: 'views/components/utilizationReport.html',
            controller: 'UtilizationController'
        })
    
        .when('/utilizationFileUpload', {
            templateUrl: 'views/components/utilizationFileUpload.html',
            controller: 'UtilizationController'
        })
    
        .when('/o2ObserverFileUpload', {
            templateUrl: 'views/components/o2ObserverFileUpload.html',
            controller: 'o2ObserverController'
        })
        
        .when('/Viewo2Observer', {
            templateUrl: 'views/components/o2ObserverDisplay.html',
            controller: 'o2ObserverController'
        }) 
    
        .when('/wip', {
            templateUrl: 'views/components/workInProgress.html',
            //controller: 'WIPController'
        })
        .when('/reportStatistics', {
            templateUrl: 'views/components/Report.html',
            controller: 'ReportController'
        }) 
        .when('/vacationPlanner', {
            templateUrl: 'views/components/vacationPlanner.html',
            controller: 'vacationPlannerController'
        }) 
        .when('/CreateVacationRequest', {
            templateUrl: 'views/components/CreateVacationRequest.html',
            controller: 'CreateVacationRequestController'
        })
        .when('/RequestHistory', {
            templateUrl: 'views/components/RequestHistory.html',
            controller: 'RequestHistoryController'
        })
        .when('/updateRequest', {
            templateUrl: 'views/components/updateRequest.html',
            controller: 'updateRequestController'
        })
        .when('/pendingApproval',{
        	templateUrl: 'views/components/pendingApproval.html',
        	controller: 'PendingApprovalController'
        })
        .when('/userUpdate',{
        	templateUrl: 'views/components/userUpdate.html',
        	controller: 'UserUpdateController'
        })
        .when('/updateEmployee',{
        	templateUrl: 'views/components/updateEmployee.html',
        	controller: 'UpdateEmployeeController'
        })
         .when('/offBoarding',{
        	templateUrl: 'views/components/offBoarding.html',
        	controller: 'offBoardingController'
        })
    	.when('/rapEntryForm',{
        	templateUrl: 'views/components/rapEntryForm.html',
        	controller: 'RAPEntryFormController'
        })
        .when('/soweditdata', {
            templateUrl: 'views/components/sowEditData.html',
            controller: 'SOWEditController'
        })
       .when('/sowviewdata', {
            templateUrl: 'views/components/sowViewtData.html',
            controller: 'SOWViewController'
        })
        .when('/TeamLeaves',{
        	templateUrl: 'views/components/TeamLeaves.html',
        	controller: 'TeamLeavesController'
        })
        .when('/LeaveRecord',{
        	templateUrl: 'views/components/LeaveRecord.html',
        	controller: 'LeaveRecordController'
        })
		.when('/expire',{
        	templateUrl: 'views/components/Expire.html',
        	controller: 'ExpireController'
        })
        .otherwise({
            redirectTo: '/'
        })		


}]);
