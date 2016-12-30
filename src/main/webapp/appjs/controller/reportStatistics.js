angular.module('ReportCtrl', []).controller('ReportController', function ($scope,PMOHttpService) {
	console.log('displaying Report page from the controller');
	$scope.labels=[];
	$scope.dat=[];
	$scope.percentage=[];
	$scope.BandMix = null;
	$scope.labels1=[];
	$scope.dat1=[];
	$scope.percentage1=[];
	$scope.BandMixData = [];
	$scope.Netvalue=[];
	$scope.colors = ['#1C4165', '#FF0000'];
	
	$scope.total = PMOHttpService.gettotal().then(function(response) {
		$('#mydiv').hide();
		$scope.tot = response.data.rows[0].value; 
		
		
		$scope.diversitymix = PMOHttpService.getdiversitymix().then(function(response) {
			$scope.labels3 = [];
			$scope.data3 =[];
			for(var n=0;n<response.data.rows.length;n++){
				$scope.labels3.push(response.data.rows[n].key);
				$scope.data3.push(response.data.rows[n].value);		
			}
			
		});
		$scope.offonmix = PMOHttpService.getoffonmix().then(function(response) {
			$scope.labels4 =[];
			$scope.data4 =[];
			for(var n=0;n<response.data.rows.length;n++){
				$scope.labels4.push(response.data.rows[n].key);
				$scope.data4.push(response.data.rows[n].value);
				}
		});
		$scope.employeetype = PMOHttpService.getemployeetype().then(function(response) {
			$scope.labels5 =[];
			$scope.data5 =[];
			for(var n=0;n<response.data.rows.length;n++){
				$scope.labels5.push(response.data.rows[n].key);
				$scope.data5.push(response.data.rows[n].value);	
			}
		});
    $scope.BandMix = PMOHttpService
    					.getBandMix()
									.then(
									function(response) {
										$scope.BandMix = response.data;
										
										
										for(var i=0;i<response.data.rows.length;i++){
											 $scope.labels.push(response.data.rows[i].key);
											 $scope.dat.push(response.data.rows[i].value);
											 $scope.percentage.push((response.data.rows[i].value*100/$scope.tot).toFixed(1));
											if(response.data.rows[i].key!='6A' && response.data.rows[i].key!='6B' && response.data.rows[i].key!='6C' && response.data.rows[i].key!='6G' && response.data.rows[i].key!='7A' && response.data.rows[i].key!='7B'){
												 $scope.Netvalue.push(response.data.rows[i].value*response.data.rows[i].key);
											}
											else if(response.data.rows[i].key=='6A'){
												$scope.Netvalue.push(response.data.rows[i].value*6.25.toFixed(1));
											}
											else if(response.data.rows[i].key=='6B'){
												$scope.Netvalue.push(response.data.rows[i].value*6.5.toFixed(1));
											}
											else if(response.data.rows[i].key=='6C'){
												$scope.Netvalue.push(response.data.rows[i].value*6.75.toFixed(1));
											}
											else if(response.data.rows[i].key=='6G'){
												$scope.Netvalue.push(response.data.rows[i].value*6);
											}
											else if(response.data.rows[i].key=='7A'){
												$scope.Netvalue.push(response.data.rows[i].value*7);
											}
											else if(response.data.rows[i].key=='7B'){
												$scope.Netvalue.push(response.data.rows[i].value*7.5.toFixed(1));
											}
										}
											
										$scope.series = ['Count', 'Percentage(%)'];
										  $scope.data = [ $scope.dat,$scope.percentage];
										console.log(JSON.stringify(JSON.stringify($scope.BandMix)));
										console.log($scope.labels.indexOf("7A"));
										
										
										for( var j =0;j<$scope.labels.length;j++){
											if($scope.labels.indexOf("6A")==-1 || $scope.labels.indexOf("6B")==-1){
												if($scope.labels[j]=='6A'){
													$scope.labels1.push($scope.labels[j]);
													$scope.dat1.push($scope.dat[j]);
													$scope.percentage1.push($scope.percentage[j]);
												}
												else if($scope.labels[j]=='6B'){
													$scope.labels1.push($scope.labels[j]);
													$scope.dat1.push($scope.dat[j]);
													$scope.percentage1.push($scope.percentage[j]);
												}
												
											}
											else if($scope.labels[j]=='6A' && $scope.labels.indexOf("6B")!=-1){
												for( var k=0; k<$scope.labels.length;k++){
													if($scope.labels[k]=='6B'){
														$scope.labels1.push($scope.labels[j]+"/" +$scope.labels[k]);
														$scope.dat1.push(parseFloat($scope.dat[j])+parseFloat($scope.dat[k]));
														$scope.sum=parseFloat($scope.percentage[j])+parseFloat($scope.percentage[k]);
														$scope.percentage1.push($scope.sum.toFixed(1));
													}
												}
											}
											else if($scope.labels.indexOf("7A")==-1 || $scope.labels.indexOf("7B")==-1){
												if($scope.labels[j]=='7A'){
													$scope.labels1.push($scope.labels[j]);
													$scope.dat1.push($scope.dat[j]);
													$scope.percentage1.push($scope.percentage[j]);
												}
												else if($scope.labels[j]=='7B'){
													$scope.labels1.push($scope.labels[j]);
													$scope.dat1.push($scope.dat[j]);
													$scope.percentage1.push($scope.percentage[j]);
												}
											}
											else if($scope.labels[j]=='7A' && $scope.labels.indexOf("7B")!=-1){
												for( var k=0; k<$scope.labels.length;k++){
													if($scope.labels[k]=='7B'){
														$scope.labels1.push($scope.labels[j]+"/" +$scope.labels[k]);
														$scope.dat1.push(parseFloat($scope.dat[j])+parseFloat($scope.dat[k]));
														$scope.sum=parseFloat($scope.percentage[j])+parseFloat($scope.percentage[k]);
														$scope.percentage1.push($scope.sum);
													}
												}
											}
											else if($scope.labels[j]!='6A' && $scope.labels[j]!='6B' && $scope.labels[j]!='6C' && $scope.labels[j]!='7A' && $scope.labels[j]!='7B'){
												$scope.labels1.push($scope.labels[j]);
												$scope.dat1.push($scope.dat[j]);
												$scope.percentage1.push($scope.percentage[j]);
											}
										}			
											$scope.series1 = ['Count', 'Percentage(%)'];
											  $scope.data1 = [ $scope.dat1,$scope.percentage1];
											  
											  
											  console.log($scope.Netvalue);
											  $scope.series2 = ['Count', 'Netvalue(Total)'];
											  $scope.data2 = [$scope.dat,$scope.Netvalue];
											  
											  var Grandtotal = $scope.Netvalue.reduce(add, 0);
											  
											  function add(a, b) {
												    return a + b;
												}
											  $scope.Bandmix = (Grandtotal/$scope.tot).toFixed(2);
											  
									});
										});
});