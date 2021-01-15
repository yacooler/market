angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';
    $scope.currentPage = 0;


    $scope.fillTable = function () {

        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                page: $scope.currentPage,
                products: 10
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
        });
    };

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

    $scope.fillTable();
});