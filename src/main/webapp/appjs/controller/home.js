angular
		.module('homeCtrl', [])
		.controller(
				'HomeController',
				function($scope, PMOHttpService, $location) {
					console.log('displaying Home Page from the controller');
				});