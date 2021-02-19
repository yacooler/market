angular.module('app').controller('productsController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';
    const apiPath = contextPath + "/api/v1";

    $scope.currentPage = 1;

    $scope.showProducts = function () {
        $http({
            url: apiPath + '/products',
            method: 'GET',
            params: {
                page: $scope.currentPage,
                products: 5
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
        });

    };

    $scope.nextPage = function (){
        if (!$scope.ProductsPage.last) {
            $scope.currentPage++;
            $scope.showProducts();
        }
    }

    $scope.previousPage = function (){
        if (!$scope.ProductsPage.first) {
            $scope.currentPage--;
            $scope.showProducts();
        }
    }

    $scope.cartAddProduct = function (productId){
        $http(
            {
                url: apiPath + "/cart/add/" + productId,
                method: 'GET'
            })
    }



    $scope.showProducts();
});