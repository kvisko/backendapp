(function() {
   'use strict'
 
   var app = angular.module("app_daoproject");
 
   app.factory("RealTimeDataResource", RealTimeDataResource);
 
   RealTimeDataResource.$inject = ['$http'];
 
   function RealTimeDataResource($http) {
 
       var service = {};
 
       service.getData = function() {
           return $http({
               method : 'GET',
               url : '/getClientDataById/{id}'
           });
       }
 
       return service;
   }
 
})();