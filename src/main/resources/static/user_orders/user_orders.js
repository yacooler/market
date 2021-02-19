angular.module('app').controller('userOrdersController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market';
    const apiPath = contextPath + "/api/v1";

    $scope.fillOrders = function () {
        $http({
            url: apiPath + '/orders',
            method: 'GET'
        })
            .then(function (response) {
                $scope.orderList = response.data;
            })
    }

    $scope.fillOrders()
});