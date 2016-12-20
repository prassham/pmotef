angular.module('o2ObserverCtrl', []).controller('o2ObserverController', function ($scope, $location, PMOHttpService, FileUploader, $sce) {
    console.log('displaying o2ObserverCtrl page');


    var uploader = $scope.uploader = new FileUploader({
        url: '/rest/FileUpload/O2ObserverFileUpload'
    });
    // CALLBACKS

    /* uploader.onWhenAddingFileFailed = function (item {File|FileLikeObject} , filter, options) {
        console.info('onWhenAddingFileFailed', item, filter, options);
    };*/
    uploader.onAfterAddingFile = function (fileItem) {
        console.info('onAfterAddingFile', fileItem);
    };
    uploader.onAfterAddingAll = function (addedFileItems) {
        console.info('onAfterAddingAll', addedFileItems);
    };
    uploader.onBeforeUploadItem = function (item) {
        console.info('onBeforeUploadItem', item);
    };
    uploader.onProgressItem = function (fileItem, progress) {
        console.info('onProgressItem', fileItem, progress);
    };
    uploader.onProgressAll = function (progress) {
        console.info('onProgressAll', progress);
    };
    uploader.onSuccessItem = function (fileItem, response, status, headers) {
        console.info('onSuccessItem', fileItem, response, status, headers);
    };
    uploader.onErrorItem = function (fileItem, response, status, headers) {
        console.info('onErrorItem', fileItem, response, status, headers);
        console.log('onErrorItem', fileItem, response, status, headers);
    };
    uploader.onCancelItem = function (fileItem, response, status, headers) {
        console.info('onCancelItem', fileItem, response, status, headers);
    };
    uploader.onCompleteItem = function (fileItem, response, status, headers) {
        console.info('onCompleteItem', fileItem, response, status, headers);
    };
    uploader.onCompleteAll = function () {
        console.info('onCompleteAll');
    };

    console.info('uploader', uploader);

    
 // Get Observer
    
    $scope.o2Observer = "";
    $('#mydiv').show(); 
    $scope.observerFile = PMOHttpService.getObserverfile().then(function (response) {
    	$('#mydiv').hide(); 
    	$scope.loading=true;
    	console.log("inside observerFile function! " );
    	var file = new Blob([response.data], {type: 'application/pdf'});
        var fileURL = URL.createObjectURL(file);
        $scope.o2Observer = $sce.trustAsResourceUrl(fileURL); 
        console.log("fileURL : " + fileURL);
        console.log("$scope.o2Observer : " + $scope.o2Observer);
        console.log("Successful retrieve observerFile response! " );
    }, function (result) {
        console.log("The retrieve observerFile request failed: " + JSON.stringify(result));
    });  
    $scope.loading=false;
       
});
