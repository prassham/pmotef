angular
		.module('UtilizationCtrl', [])
		.controller(
				'UtilizationController',
				function($scope, $location, PMOHttpService, FileUploader) {
					console.log('displaying UtilizationCtrl page');

					$scope.employeeDetails = [];
					$scope.search = '';
					console.log("team json" + $scope.teamDetails);
					// $scope.accountDetails
					var uploader = $scope.uploader = new FileUploader(
							{
								url : 'restUtilization/PMOFileUpload/utilizationFileUpload'
							});
					// CALLBACKS

					/*
					 * uploader.onWhenAddingFileFailed = function (item
					 * {File|FileLikeObject} , filter, options) {
					 * console.info('onWhenAddingFileFailed', item, filter,
					 * options); };
					 */
					uploader.onAfterAddingFile = function(fileItem) {
						console.info('onAfterAddingFile', fileItem);
					};
					uploader.onAfterAddingAll = function(addedFileItems) {
						console.info('onAfterAddingAll', addedFileItems);
					};
					uploader.onBeforeUploadItem = function(item) {
						console.info('onBeforeUploadItem', item);
					};
					uploader.onProgressItem = function(fileItem, progress) {
						console.info('onProgressItem', fileItem, progress);
					};
					uploader.onProgressAll = function(progress) {
						console.info('onProgressAll', progress);
					};
					uploader.onSuccessItem = function(fileItem, response,
							status, headers) {
						console.info('onSuccessItem', fileItem, response,
								status, headers);
					};
					uploader.onErrorItem = function(fileItem, response, status,
							headers) {
						console.info('onErrorItem', fileItem, response, status,
								headers);
						console.log('onErrorItem', fileItem, response, status,
								headers);
					};
					uploader.onCancelItem = function(fileItem, response,
							status, headers) {
						console.info('onCancelItem', fileItem, response,
								status, headers);
					};
					uploader.onCompleteItem = function(fileItem, response,
							status, headers) {
						console.info('onCompleteItem', fileItem, response,
								status, headers);
					};
					uploader.onCompleteAll = function() {
						console.info('onCompleteAll');
					};

					console.info('uploader', uploader);

					/** ******Searching data********* */
					$scope.nameOrId = function(emp) {
						return (emp.id + emp.name + emp.workstream)
								.indexOf($scope.search) >= 0;
					};

					/** ****Display Utilization****** */

					$scope.employeeUtilization = PMOHttpService
							.getEmployeeUtilization()
							.then(
									function(response) {

										$scope.employeeUtilizationList = response.data.utilization;
										$scope.teamList=response.data.workstream[0];
										console.log("team data "+ (JSON.stringify($scope.teamList)));
										console.log("response data "+ (JSON.stringify($scope.employeeUtilizationList)));

										var qtd1BillableActuals = 0.0e-10, qtd2BillableActuals = 0.0e-10, qtd3BillableActuals = 0.0e-10, qtd4BillableActuals = 0.0e-10;
										var qtd1TotalAvailable = 0.0e-10, qtd2TotalAvailable = 0.0e-10, qtd3TotalAvailable = 0.0e-10, qtd4TotalAvailable = 0.0e-10;
										var qtd1UtilPer = 0.0e-10, qtd2UtilPer = 0.0e-10, qtd3UtilPer = 0.0e-10, qtd4UtilPer = 0.0e-10, ytdUtilPer = 0.0e-10;
										/**
										 * ******** calculating individual
										 * level*********
										 */
										// qtdAvailableActuals =
										// 160*3*$scope.employeeUtilizationList.length;
										for (var i = 0; i < $scope.employeeUtilizationList.length; i++) {
											var JANPer = 0.0e-10, FEBPer = 0.0e-10, MARPer = 0.0e-10, APRPer = 0.0e-10, MAYPer = 0.0e-10, JUNPer = 0.0e-10, JULPer = 0.0e-10, AUGPer = 0.0e-10, SEPPer = 0.0e-10, OCTPer = 0.0e-10, NOVPer = 0.0e-10, DECPer = 0.0e-10;
											var Q1Per = 0.0e-10, Q2Per = 0.0e-10, Q3Per = 0.0e-10, Q4Per = 0.0e-10, yearlyPer = 0.0e-10;
											var q1Util = parseFloat($scope.employeeUtilizationList[i].utilization.JAN)
													+ parseFloat($scope.employeeUtilizationList[i].utilization.FEB)
													+ parseFloat($scope.employeeUtilizationList[i].utilization.MAR);
											var q2Util = parseFloat($scope.employeeUtilizationList[i].utilization.APR)
													+ parseFloat($scope.employeeUtilizationList[i].utilization.MAY)
													+ parseFloat($scope.employeeUtilizationList[i].utilization.JUN);
											var q3Util = parseFloat($scope.employeeUtilizationList[i].utilization.JUL)
													+ parseFloat($scope.employeeUtilizationList[i].utilization.AUG)
													+ parseFloat($scope.employeeUtilizationList[i].utilization.SEP);
											var q4Util = parseFloat($scope.employeeUtilizationList[i].utilization.OCT)
													+ parseFloat($scope.employeeUtilizationList[i].utilization.NOV)
													+ parseFloat($scope.employeeUtilizationList[i].utilization.DEC);

											var q1Avail = parseFloat($scope.employeeUtilizationList[i].availHours.JAN)
													+ parseFloat($scope.employeeUtilizationList[i].availHours.FEB)
													+ parseFloat($scope.employeeUtilizationList[i].availHours.MAR);
											var q2Avail = parseFloat($scope.employeeUtilizationList[i].availHours.APR)
													+ parseFloat($scope.employeeUtilizationList[i].availHours.MAY)
													+ parseFloat($scope.employeeUtilizationList[i].availHours.JUN);
											var q3Avail = parseFloat($scope.employeeUtilizationList[i].availHours.JUL)
													+ parseFloat($scope.employeeUtilizationList[i].availHours.AUG)
													+ parseFloat($scope.employeeUtilizationList[i].availHours.SEP);
											var q4Avail = parseFloat($scope.employeeUtilizationList[i].availHours.OCT)
													+ parseFloat($scope.employeeUtilizationList[i].availHours.NOV)
													+ parseFloat($scope.employeeUtilizationList[i].availHours.DEC);

											if ($scope.employeeUtilizationList[i].availHours.JAN != 0)
												JANPer = $scope.employeeUtilizationList[i].utilization.JAN
														/ $scope.employeeUtilizationList[i].availHours.JAN
														* 100;
											if ($scope.employeeUtilizationList[i].availHours.FEB != 0)
												FEBPer = $scope.employeeUtilizationList[i].utilization.FEB
														* 100
														/ $scope.employeeUtilizationList[i].availHours.FEB;
											if ($scope.employeeUtilizationList[i].availHours.MAR != 0)
												MARPer = $scope.employeeUtilizationList[i].utilization.MAR
														* 100
														/ $scope.employeeUtilizationList[i].availHours.MAR;
											if ($scope.employeeUtilizationList[i].availHours.APR != 0)
												APRPer = $scope.employeeUtilizationList[i].utilization.APR
														* 100
														/ $scope.employeeUtilizationList[i].availHours.APR;
											if ($scope.employeeUtilizationList[i].availHours.MAY != 0)
												MAYPer = $scope.employeeUtilizationList[i].utilization.MAY
														* 100
														/ $scope.employeeUtilizationList[i].availHours.MAY;
											if ($scope.employeeUtilizationList[i].availHours.JUN != 0)
												JUNPer = $scope.employeeUtilizationList[i].utilization.JUN
														* 100
														/ $scope.employeeUtilizationList[i].availHours.JUN;
											if ($scope.employeeUtilizationList[i].availHours.JUL != 0)
												JULPer = $scope.employeeUtilizationList[i].utilization.JUL
														* 100
														/ $scope.employeeUtilizationList[i].availHours.JUL;
											if ($scope.employeeUtilizationList[i].availHours.AUG != 0)
												AUGPer = $scope.employeeUtilizationList[i].utilization.AUG
														* 100
														/ $scope.employeeUtilizationList[i].availHours.AUG;
											if ($scope.employeeUtilizationList[i].availHours.SEP != 0)
												SEPPer = $scope.employeeUtilizationList[i].utilization.SEP
														* 100
														/ $scope.employeeUtilizationList[i].availHours.SEP;
											if ($scope.employeeUtilizationList[i].availHours.OCT != 0)
												OCTPer = $scope.employeeUtilizationList[i].utilization.OCT
														* 100
														/ $scope.employeeUtilizationList[i].availHours.OCT;
											if ($scope.employeeUtilizationList[i].availHours.NOV != 0)
												NOVPer = $scope.employeeUtilizationList[i].utilization.NOV
														* 100
														/ $scope.employeeUtilizationList[i].availHours.NOV;
											if ($scope.employeeUtilizationList[i].availHours.DEC != 0)
												DECPer = $scope.employeeUtilizationList[i].utilization.DEC
														* 100
														/ $scope.employeeUtilizationList[i].availHours.DEC;
											if (q1Avail != 0)
												Q1Per = (q1Util * 100)
														/ q1Avail;
											if (q2Avail != 0)
												Q2Per = (q2Util * 100)
														/ q2Avail;
											if (q3Avail != 0)
												Q3Per = (q3Util * 100)
														/ q3Avail;
											if (q4Avail != 0)
												Q4Per = (q4Util * 100)
														/ q4Avail;
											if (q1Avail + q2Avail + q3Avail
													+ q4Avail != 0)
												yearlyPer = ((q1Util + q2Util
														+ q3Util + q4Util) * 100)
														/ (q1Avail + q2Avail
																+ q3Avail + q4Avail);

											
											item = {};
											item["name"] = $scope.employeeUtilizationList[i].name;
											item["id"] = $scope.employeeUtilizationList[i].emp_id;
											item["workstream"] = $scope.employeeUtilizationList[i].workstream;
											item["JANPer"] = JANPer;
											item["FEBPer"] = FEBPer;
											item["MARPer"] = MARPer;
											item["APRPer"] = APRPer;
											item["MAYPer"] = MAYPer;
											item["JUNPer"] = JUNPer;
											item["JULPer"] = JULPer;
											item["AUGPer"] = AUGPer;
											item["SEPPer"] = SEPPer;
											item["OCTPer"] = OCTPer;
											item["NOVPer"] = NOVPer;
											item["DECPer"] = DECPer;
											item["Q1Per"] = Q1Per;
											item["Q2Per"] = Q2Per;
											item["Q3Per"] = Q3Per;
											item["Q4Per"] = Q4Per;
											item["yearlyPer"] = yearlyPer;

											$scope.employeeDetails.push(item);
											
											/*********** Team level calculation**********/
											
											
											$scope.teamList[item["workstream"]].q1 +=Q1Per;
											$scope.teamList[item["workstream"]].q2 +=Q2Per;
											$scope.teamList[item["workstream"]].q3 +=Q3Per;
											$scope.teamList[item["workstream"]].q4 +=Q4Per;
											$scope.teamList[item["workstream"]].ytd +=yearlyPer;
											$scope.teamList[item["workstream"]].count+=1;	

											/**
											 * ******** calculating account
											 * level*********
											 */
											/**
											 * ***********Total billable hours
											 * *****************
											 */
											qtd1BillableActuals += q1Util;
											qtd2BillableActuals += q2Util;
											qtd3BillableActuals += q3Util;
											qtd4BillableActuals += q4Util;
											// console.log(q1+" "+ q2+" "+q3+"
											// "+q4);

											/**
											 * ***********Total available hours
											 * *****************
											 */
											qtd1TotalAvailable += q1Avail;
											qtd2TotalAvailable += q2Avail;
											qtd3TotalAvailable += q3Avail;
											qtd4TotalAvailable += q4Avail;
											// console.log("Total Available
											// hours " +q1Avail+ " "+ q2Avail+"
											// "+q3Avail+" "+q4Avail);
										}

										/**
										 * ***********Percentage of QTD & YTD
										 * utilization *****************
										 */
										if (qtd1TotalAvailable != 0)
											qtd1UtilPer = qtd1BillableActuals
													* 100 / qtd1TotalAvailable;
										if (qtd2TotalAvailable != 0)
											qtd2UtilPer = qtd2BillableActuals
													* 100 / qtd2TotalAvailable;
										if (qtd3TotalAvailable != 0)
											qtd3UtilPer = qtd3BillableActuals
													* 100 / qtd3TotalAvailable;
										if (qtd4TotalAvailable != 0)
											qtd4UtilPer = qtd4BillableActuals
													* 100 / qtd4TotalAvailable;
										if ((qtd1TotalAvailable
												+ qtd2TotalAvailable
												+ qtd3TotalAvailable + qtd4TotalAvailable) != 0)
											ytdUtilPer = (qtd1BillableActuals
													+ qtd2BillableActuals
													+ qtd3BillableActuals + qtd4BillableActuals)
													* 100
													/ (qtd1TotalAvailable
															+ qtd2TotalAvailable
															+ qtd3TotalAvailable + qtd4TotalAvailable);
										console.log(" Q1 :"
												+ qtd1BillableActuals + " Q2 :"
												+ qtd2BillableActuals + " Q3: "
												+ qtd3BillableActuals + " Q4 :"
												+ qtd4BillableActuals);
										$scope.accountDetails = [ {
											"qtd1BillableActuals" : qtd1BillableActuals,
											"qtd2BillableActuals" : qtd2BillableActuals,
											"qtd3BillableActuals" : qtd3BillableActuals,
											"qtd4BillableActuals" : qtd4BillableActuals,
											"qtd1TotalAvailable" : qtd1TotalAvailable,
											"qtd2TotalAvailable" : qtd2TotalAvailable,
											"qtd3TotalAvailable" : qtd3TotalAvailable,
											"qtd4TotalAvailable" : qtd4TotalAvailable,
											"qtd1UtilPer" : qtd1UtilPer,
											"qtd2UtilPer" : qtd2UtilPer,
											"qtd3UtilPer" : qtd3UtilPer,
											"qtd4UtilPer" : qtd4UtilPer,
											"ytdUtilPer" : ytdUtilPer
										} ];

										//console.log(JSON.stringify($scope.accountDetails));
										//console.log("Percentage data"+ JSON.stringify($scope.employeeDetails));
									},
									function(result) {
										//console.log("The retrieve Employee Utilization request failed: "+ JSON.stringify(result));

									});

				});
