angular.module('app').controller('homeController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/market';

    $scope.showProfiling = function (){
        $http(
            {
                url: contextPath + '/adm/methods',
                method: 'GET'
            })
            .then(
                function (response) {
                    console.log(response.data);
                    $scope.methodsProf = response.data;
                }
            );

        $http(
            {
                url: contextPath + '/adm/controllers',
                method: 'GET'
            })
            .then(
                function (response) {
                    console.log(response.data);
                    $scope.controlersProf = response.data;
                }
            );
    }

    $scope.showProfiling();

});