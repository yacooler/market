angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contentPath = 'http://localhost:8189/market/'
    let appPath = contentPath + 'api/v1';
    let authPath = contentPath +'auth'

    $scope.currentPage = 1;
    $scope.allowClearCart = false;
    $scope.authPresent = false;
    $scope.user = {name:'',password:''};

    $scope.fillTable = function () {
        $http({
            url: appPath + '/products',
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
            url: appPath + '/cart',
            method: 'GET'
            })
        .then(function (response) {
            $scope.Cart = response.data;
            $scope.allowClearCart = false;
        })
    }

    $scope.submitCreateNewProduct = function () {
        $http.post(appPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    };

    $scope.deleteProductById = function(id) {
        $http.delete(appPath + '/products/' + id)
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
                url: appPath + "/cart/add/" + productId,
                method: 'GET'
            })
            .then(function (){
                $scope.fillCart();
            })
    }


    $scope.cartRemoveProduct = function(productId) {
        $http(
            {
                url: appPath + "/cart/remove/" + productId,
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
                url: appPath + "/cart/clear",
                method: 'GET'
            })
            .then(function (){
                $scope.fillCart();

            });
    }

    $scope.isUserLoggedIn = function (){
        return $scope.authPresent;
    }

    $scope.authorizationRequest = function (){
        $http.post(authPath,$scope.user)
            .then(
                function successCallback(response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $scope.user.password = null;
                        $scope.authorized = true;
                        $scope.fillTable();
                        $scope.authPresent = true;
                    }
                },
                function errorCallback(response) {
                    window.alert("Error");
                    $scope.authPresent = false;
                });
    }

    $scope.makeOrder = function (){
        $http(
            {
                url: appPath + '/cart/makeorder',
                method: 'GET'
            })
            .then(
                function (){
                    alert("Заказ сделан!")
                }
            );
    }

    $scope.fillTable();
    $scope.fillCart();

});