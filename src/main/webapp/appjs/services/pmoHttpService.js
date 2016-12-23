angular.module('PMOHttp', []).factory('PMOHttpService', ['$http', function ($http) {


    /****** For Local json Data ******/
    //var _url = 'localData';

    /***** this url for server restful call ****/
    var _url = "/pmo/PMOResource";
	var shortEmailPattern =/^[^\s@]+@[^\s@][(in|uk)]+\.[^\s@][(ibm}]+\.[^\s@][(com)]{1,}$/;
    var emailPattern =/^[^@]+\/[^@][(India|Uk)]+\/[^/s@][(IBM)]{1,}$/;
    var mobilenumberPattern =/^[\d+-]+\d+$/;
    var numberPattern = /^[\d.]+$/;
    var textPattern = /^[\w\s-]+$/;

    /***** this variable is to store username of user****/

    return {
    	getShortPattern: function(){
    		return shortEmailPattern;
    	},
    	getPattern: function(){
    		return emailPattern;
    	},
    	getMobileNumberPattern: function(){
    		return mobilenumberPattern;
    	},
    	getNumberPattern: function(){
    		return numberPattern;
    	},
    	getTextPattern: function(){
    		return textPattern;
    	},
        autheticate: function (authData) {
            return $http.post('localData/login.json', authData);
        },
        someservice: function (data) {
            return $http.post('/pmo/someservice', data);
        },
        getEmployeeList: function(){
            return $http({ method:"GET", url: "pmo/employee/all" });
            //return $http({ method:"GET", url: _url+"/getEmployee.json" });
        },
        getEmployeeByName: function(empName){
        	return $http({ method:"GET", url: "pmo/employee/getws_manager/" +empName + ""});
        },
        getEmployeePhotoById: function(empId){
            return $http({ method:"GET", url: _url+"/getEmployeePhotoByID/" + empId + ""});
            //return $http({ method:"GET", url: _url+"/getEmployeePhotoByID.json"});
        },
        getEmployeeUtilization: function(){
        	return $http({ method:"GET", url:"pmo/UtilResource/getUtilization"});
        	//return $http({ method:"GET", url: _url+"/getUtilization/"});
        	//return $http({ method:"GET", url: _url+"/getUtilization.json" });
        } ,
        /*getBandMix: function(){
        	//return $http({ method:"GET",url: _url+"/getBandMix.json"});
        	return $http({ method:"GET", url:"pmo/employeeStatistics/All"});
        },*/
        
        //url: '/rest/FileUpload/O2ObserverFileUpload'
        getObserverfile : function(){
        	return $http({ method:"GET", url:"pmo/FileDownload/O2ObserverFileShow", responseType:'arraybuffer'});
        },
        getVacationList: function(){
        	return $http({ method:"GET", url:"pmo/vacation/employeeonleave"});
        },
        getVacationHistory: function(email){
        	return $http({ method:"GET", url:"pmo/vacation/vacationhistory/"+ email +""});
        },
        deleteVacation: function(_id, _rev){
        	return $http({ method:"GET", url:"pmo/vacation/delete/"+ _id +"/"+ _rev +""});
        },
        updateVacation: function(_id){
        	return $http({ method:"GET", url:"pmo/vacation/update/"+ _id +""});
        },
        login: function (id) {
        	return $http.post('pmo/vacation/login', id);
        },
        insertVacation: function(createVacationOb){
        	return $http.post('pmo/vacation/insert', createVacationOb);
        },
        getPendingList: function(email){
        	return $http({ method:"GET", url:"pmo/vacation/pendingapprovals/"+email+""});
        },
        postEmployee: function(employeejson){
        	//return $http({ method:"POST", url:"employee/insert",data:employeejson}); 
        	return $http.post('pmo/employee/insert', employeejson);

        },
        deleteEmployee: function(_id, _rev){
        	return $http({ method:"GET", url:"pmo/employee/delete/"+ _id +"/"+ _rev +""});
        },
        updateEmployee: function(updatedjson){
        	return $http.post('pmo/employee/update', updatedjson);
        },
        getBandMix: function(){        	
        	return $http({ method:"GET", url:"pmo/employeeStatistics/bandmixpercentage"});
        },
        gettotal: function(){        	
        	return $http({ method:"GET", url:"pmo/employeeStatistics/total"});
        },
        getdiversitymix: function(){
        	return $http({ method:"GET", url:"pmo/employeeStatistics/diversitymix"});
        },
        getoffonmix: function(){
        	return $http({ method:"GET", url:"pmo/employeeStatistics/offonmix"});
        },
        getemployeetype: function(){
        	return $http({ method:"GET", url:"pmo/employeeStatistics/employeetype"});
        },
        getEmployeeById:function(empid){
        	return $http({ method:"GET", url:"pmo/employee/getEmployee/"+empid+""});
        },
        loginName: function(){
        	return $http({ method:"GET", url:"pmo/PMOTest"});
        },
        getOptions: function(){
        	return $http({ method:"GET", url:"localData/dropdown.json" });
        },
        updateVacationPending: function(updatedjson){
        	return $http.post('pmo/vacation/update', updatedjson);
        },
        getSowViewData: function(json){
            //return $http({ method:"GET", url: _url+"/getEmployee/All/All" });
        	 return $http.post('pmo/sowedit/insert', json);
        },
        getHistoryList: function(){
            return $http({ method:"GET", url: "pmo/employee/history"});
            //return $http({ method:"GET", url: _url+"/getEmployee.json" });
        },
        checkPMO:function(){
        	return $http({ method:"GET", url: "pmo/PMOTest/EmpManageaccess"});
    	},
        getNotesid:function(notesid){
        	return $http({ method:"GET", url:"pmo/vacation/getnotesid/"+notesid+""});
        },
        getLeaverecord:function(empemail){
        	return $http({ method:"GET", url:"pmo/vacation/getLeaverecord/"+empemail+""});
        },
        getEmployeeCount:function(){
        	return $http({ method:"GET", url: "pmo/employeeone/employeeCount"});
        },
        getEmployeeListPage:function(currentPage){
        	return $http({ method:"GET", url: "pmo/employeeone/employeeDetails/"+currentPage+""});
        }
};

    }]);