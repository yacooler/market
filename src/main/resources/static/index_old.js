
angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contentPath = 'http://localhost:8189/market/'
    let appPath = contentPath + 'api/v1';
    let appOpenApiPath = contentPath + 'openapi/v1';
    let authPath = contentPath +'auth'

    $scope.currentPage = 1;
    $scope.allowClearCart = false;
    $scope.authPresent = false;
    $scope.newUserPage = false;
    $scope.user = {name:'',password:''};
    $scope.orderDetails = {deliveryAddress: ''}
    $scope.newUser = {name: '', password:'', email:'', error:''}

    $scope.fillProducts = function () {
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

    $scope.fillOrders = function (){
        $http({
            url: appPath + '/orders',
            method: 'GET'
        })
            .then(function (response) {
                $scope.orderList = response.data;
                $scope.allowClearCart = false;
            })
    }


    $scope.submitCreateNewProduct = function () {
        $http.post(appPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillProducts();
            });
    };

    $scope.deleteProductById = function(id) {
        $http.delete(appPath + '/products/' + id)
            .then(function (){
                $scope.fillProducts();
            })
    }

    $scope.nextPage = function (){
        if ($scope.ProductsPage == null) return;
        if (!$scope.ProductsPage.last) {
            $scope.currentPage++;
            $scope.fillProducts();
        }
    }

    $scope.previousPage = function (){
        if ($scope.ProductsPage == null) return;
        if (!$scope.ProductsPage.first) {
            $scope.currentPage--;
            $scope.fillProducts();
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

                        $scope.fillProducts();
                        $scope.fillCart();
                        $scope.fillOrders();

                        $scope.authPresent = true;
                        $scope.newUserPage = false;
                    }
                },
                function errorCallback(response) {
                    window.alert("Error");
                    $scope.authPresent = false;
                });
    }


    $scope.makeOrder = function (){
        console.log($scope.orderDetails.deliveryAddress)
        $http(
            {
                url: appPath + '/orders/makeorder',
                method: 'POST',
                params: {
                    deliveryAddress: $scope.orderDetails.deliveryAddress
                }
            })
            .then(
                function (){
                    $scope.fillOrders();
                    $scope.cartClear();
                }
            );
    }

    $scope.showNewUserPage = function (){
        $scope.newUserPage = true;
    }

    $scope.hideNewUserPage = function (){
        $scope.newUserPage = false;
    }


    $scope.newUserRequest = function (){

        if ($scope.newUser === null) {
            return;
        }

        var haserr = false;
        $scope.newUser.error = '';

        if ($scope.newUser.name === null || $scope.newUser.name.trim() === ''){
            createLoginInput.placeholder = 'Необходимо ввести логин!'
            haserr = true;
        }
        if ($scope.newUser.password === null || $scope.newUser.password.trim() === ''){
            createPasswordInput.placeholder = 'Необходимо ввести пароль!'
            haserr = true;
        }
        if ($scope.newUser.email === null || $scope.newUser.email.trim() === ''){
            createEmailInput.placeholder = 'Необходимо ввести адрес электронной почты!'
            haserr = true;
        }



        if (haserr) {
            $scope.newUser.error = 'При регистрации пользователя произошли ошибки!';
            return;
        }

        $http.post(appOpenApiPath + '/users', $scope.newUser)
            .then(
                function (response){
                    if (response.data.error === ''){
                        $scope.hideNewUserPage();
                        $scope.user.username = response.data.name;
                        $scope.user.password = response.data.password;
                        $scope.newUser.password = '';
                    } else {
                        $scope.newUser.error = response.data.error;
                    }

                }
            )
    }


});