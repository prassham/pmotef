angular.module('sidebarCtrl', []).controller('sidebarController', function ($scope,$location,PMOHttpService,$filter) {
	console.log('displaying Sidebar page from the controller');
	
	$scope.user = PMOHttpService;

	$scope.user.pending = true;
	$scope.pendingbutton = $scope.user.pending;
	$scope.user.resourcemanagementbutton = true;
	$scope.resourcemanagementbutton = $scope.user.resourcemanagementbutton;

	$scope.loggedinuser = PMOHttpService
			.loginName()
			.then(
					function(response) {
						var id = JSON.stringify(response.data);
						$scope.user.intranetID = id;
						console.log($scope.user.intranetID); // Loggedin
																// user
																// intranet
																// ID
																// display

						$scope.logindetails = PMOHttpService
								.login(id)
								.then(
										function(response) {
											$scope.loggedindetails = response.data; // checking
																					// whether
																					// user
																					// exists
																					// in
																					// employee
																					// table
																					// or
																					// not
											if ($scope.loggedindetails[0].Message == "User does not exist") {
												$location
														.path('/forbidden');
											} else { // if
														// user
														// exists
												$scope.user.name = $scope.loggedindetails[0].NAME;
												console
														.log($scope.user.name); // displaying
																				// user
																				// name
																				// if
																				// exists
												$scope.employeews_manager = PMOHttpService
														// Checking
														// if
														// employee
														// is
														// workstream
														// manager
														// or
														// not
														.getEmployeeByName(
																$scope.user.name)
														.then(
																function(
																		response) {
																	// alert(response.data)
																	$scope.user.pendingbutton = response.data;
																	console.log($scope.user.pendingbutton);
																	$scope.chekpmo = PMOHttpService
																			.checkPMO()
																			.then(
																					function(
																							response) {
																						$('#mydiv1').hide();	
																						$scope.pmo = response.data;
																						console.log($scope.pmo[0]["WORKSTREAM"]);
																						if ($scope.pmo[0]["WORKSTREAM"] == "PMO") {
																							// checking if employee is pmo or not
																							$scope.user.resourcemanagementbutton = false;
																							$scope.resourcemanagementbutton = $scope.user.resourcemanagementbutton;
																						}
																						else {
																							$scope.user.resourcemanagementbutton = true;
																							$scope.resourcemanagementbutton = $scope.user.resourcemanagementbutton;
																						}
																						if ($scope.user.pendingbutton == "exist"
																							|| $scope.user.resourcemanagementbutton == false) {
																							console.log($scope.user.pendingbutton +" " + $scope.user.resourcemanagementbutton);

																						$scope.user.pending = false;
																						$scope.pendingbutton = $scope.user.pending;
																					} else {
																						$scope.user.pending = true;
																						$scope.pendingbutton = $scope.user.pending;
																					}
																					});
																	
																});
											}
										});

					});
});

