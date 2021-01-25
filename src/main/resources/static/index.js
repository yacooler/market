angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market/api/v1';
    $scope.currentPage = 1;
    $scope.allowClearCart = false;


    $scope.fillTable = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                page: $scope.currentPage,
                products: 5
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
        });
    };

    $scope.fillCart = function (){
        $http({
            url: contextPath + '/cart',
            method: 'GET'
            })
        .then(function (response) {
            $scope.Cart = response.data;
            $scope.allowClearCart = false;
        })
    }

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    };

    $scope.deleteProductById = function(id) {
        $http.delete(contextPath + '/products/' + id)
            .then(function (){
                $scope.fillTable();
            })
    }

    $scope.nextPage = function (){
        if ($scope.ProductsPage == null) return;
        if (!$scope.ProductsPage.last) {
            $scope.currentPage++;
            $scope.fillTable();
        }
    }

    $scope.previousPage = function (){
        if ($scope.ProductsPage == null) return;
        if (!$scope.ProductsPage.first) {
            $scope.currentPage--;
            $scope.fillTable();
        }
    }

    $scope.cartAddProduct = function (productId){
        $http(
            {
                url: contextPath + "/cart/add/" + productId,
                method: 'GET'
            })
            .then(function (){
                $scope.fillCart();
            })
    }


    $scope.cartRemoveProduct = function(productId) {
        $http(
            {
                url: contextPath + "/cart/remove/" + productId,
                method: 'GET'
            })
            .then(function (){
                $scope.fillCart();
            })
    }

    $scope.setAllowClearCart = function (){
        $scope.allowClearCart = true;
    }

    $scope.cartClear = function (){
        $http(
            {
                url: contextPath + "/cart/clear",
                method: 'GET'
            })
            .then(function (){
                $scope.fillCart();

            });
    }

    $scope.fillTable();
    $scope.fillCart();

});