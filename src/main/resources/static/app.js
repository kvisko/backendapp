(function() {
    'use strict';

    var app = angular.module('app_daoproject', []);

    app.controller('RealTimeReportingController', RealTimeReportingController);

    RealTimeReportingController.$inject = [ 'RealTimeReportingChartService' ];

    function RealTimeReportingController(RealTimeReportingChartService) {
        RealTimeReportingChartService.populateStackedBarChart();
    }

})();