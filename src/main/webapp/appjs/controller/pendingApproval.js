angular.module('pendingApprovalCtrl', []).controller('PendingApprovalController', function ($scope,PMOHttpService,$location,$window,$route) {
	console.log('displaying pending approval page from the controller');
	
	$scope.user = PMOHttpService;
	$scope.email = $scope.user.email;
	$scope.name = $scope.user.name;
	$scope.pendingbutton = $scope.user.pending;
	//alert($scope.email);
	//alert($scope.name);
	var pendingLeaveList = {};
	var ApprovalList = {};
	if($scope.user.pendingbutton !="exist"){
		alert("You are not allowed to access this page");
		$location.path("/vacationPlanner");
	}
	$scope.PendingApprovalList = PMOHttpService
	.getPendingList($scope.name).then(function (response) {
				$scope.pendingList = response.data;
				console.log("$scope.pendingList");
				
	});
	$scope.dropdown = {
		"values":["Approved","Rejected"]
	};
	
	/*$scope.dropdown = {
			"status1":"Approved",
		    "status2" :"Rejected"
		};*/
	$scope.requestState = function(selectedValue,item){
		//alert(selectedValue);
		item.requeststate = selectedValue;
		//alert(item.requeststate);
		//alert(item._id);
		var json = JSON.stringify(item);
		$scope.updateVacationPending = PMOHttpService
		.updateVacationPending(json).then(function(response) {
			$scope.request = response.data;
			alert(JSON.stringify($scope.request.message));
			var link = "mailto:"+item.name
            + "?subject=Regarding Leave%20Request " 
            + "&body=Hi%20" +item.name+",%0a%0aYour vacation request from%20"+item.startDate+"%20to%20"+item.endDate+"has been"+item.requeststate+".%0a%0a"; 

   window.location.href = link;
			$route.reload();
		});
		//update by passing state and id
	}
	
});