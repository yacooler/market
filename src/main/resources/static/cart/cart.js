angular.module('app').controller('cartController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';
    const apiPath = contextPath + "/api/v1";
    $scope.allowClearCart = false;

    $scope.showCart = function (){
        $http({
            url: apiPath + '/cart',
            method: 'GET'
        })
            .then(function (response) {
                $scope.cart = response.data;
                $scope.allowClearCart = false;
            })
    }

    $scope.cartAddProduct = function (productId){
        $http(
            {
                url: apiPath + "/cart/add/" + productId,
                method: 'GET'
            })
            .then(function (){
                $scope.showCart();
            })
    }


    $scope.cartRemoveProduct = function(productId) {
        $http(
            {
                url: apiPath + "/cart/remove/" + productId,
                method: 'GET'
            })
            .then(function (){
                $scope.showCart();
            })
    }

    $scope.setAllowClearCart = function (){
        $scope.allowClearCart = true;
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

    $scope.isCartEmpty = function (){
        if (typeof $scope.cart === 'undefined') return false;
        if (typeof $scope.cart.cartItemDtos === 'undefined') return false;
        return ($scope.cart.cartItemDtos.length === 0);
    }

    $scope.showCart();
});