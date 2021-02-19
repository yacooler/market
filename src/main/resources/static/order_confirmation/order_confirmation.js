angular.module('app').controller('orderConfirmationController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market';
    const apiPath = contextPath + "/api/v1";
    $scope.orderDetails = {deliveryAddress: ''};


    $scope.showCart = function (){
        $http({
            url: apiPath + '/cart',
            method: 'GET'
        })
            .then(function (response) {
                $scope.cart = response.data;
            })
    }

    $scope.makeOrder = function (){
        $http(
            {
                url: apiPath + '/orders/makeorder',
                method: 'POST',
                params: {
                    deliveryAddress: $scope.orderDetails.deliveryAddress
                }
            })
            .then(
                function (){
                    $scope.cartClear();
                    $location.path('/user_orders/' /*Сюда можно добавить ID*/);
                }
            );
    }

    $scope.cartClear = function (){
        $http(
            {
                url: apiPath + "/cart/clear",
                method: 'GET'
            })
            .then(function (){
                $scope.showCart();
            });
    }

    $scope.showCart();

});