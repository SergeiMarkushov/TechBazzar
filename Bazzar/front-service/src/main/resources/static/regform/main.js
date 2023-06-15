angular.module('store').controller('authController', function ($scope, $http, $localStorage, $location, $rootScope) {
    // использовать для локального подключения
    const contextPath = 'http://localhost:5555/auth/api/v1/';
    // использовать для удаленного подключения
    // const contextPath = 'http://95.165.90.118:443/auth/api/v1/';
    // const contextPathKeycloak = 'http://192.168.1.110:8080/auth/realms/master/protocol/openid-connect/token';
//     $scope.loadProducts = function (pageIndex = 1) {
//         $http({
//             url: contextPathCore + 'products',
//             method: 'GET',
//             params: {
//                 p: pageIndex,
//                 title_part: $scope.filter ? $scope.filter.title_part : null,
//                 max_price: $scope.filter ? $scope.filter.max_price : null,
//                 min_price: $scope.filter ? $scope.filter.min_price : null
//             }
//         }).then(function (response) {
//             $scope.filter.title_part = null;
//             $scope.filter.max_price = null;
//             $scope.filter.min_price = null;
//             $scope.ProductPage = response.data;
//             console.log(response.data);
//             $scope.indexNumber = $scope.ProductPage.totalPages;
//             $scope.generatePagesList($scope.ProductPage.totalPages);
//         });

    $scope.tryToAuth = function () {
        $http({
            url: contextPathKeycloak,
            method: 'POST',
            body: {
                username: $scope.user.username,
                password: $scope.user.password,
                client_id: 'postman',
                grant_type: 'password'
            }
        }).then(function successCallback(response) {
            console.log('here');
            if (response.data.token) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.simpleUser = {username: $scope.user.username, token: response.data.token};

                $scope.user.username = null;
                $scope.user.password = null;

                $http.get(contextPath + 'users/get_roles/' + $localStorage.simpleUser.username)
                    .then(function (response) {
                        let roles = response.data;
                        $localStorage.roleIndex = roles.findIndex(item => item.name === 'ROLE_ADMIN');
                    })

                $location.path('/');

            }
        });
    };

    // $scope.tryToAuth = function () {
    //     $http.post(contextPath + 'auth', $scope.user)
    //         .then(function successCallback(response) {
    //             if (response.data.token) {
    //                 $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
    //                 $localStorage.simpleUser = {username: $scope.user.username, token: response.data.token};
    //
    //                 $scope.user.username = null;
    //                 $scope.user.password = null;
    //
    //                 $http.get(contextPath + 'users/get_roles/' + $localStorage.simpleUser.username)
    //                     .then(function (response) {
    //                         let roles = response.data;
    //                         $localStorage.roleIndex = roles.findIndex(item => item.name === 'ROLE_ADMIN');
    //                     })
    //
    //                 $location.path('/');
    //
    //             }
    //         });
    // };

    $scope.tryToReg = function () {
        $http.post(contextPath + 'registration', $scope.new_user)
            .then(function (response) {
                alert('Аккаунт успешно создан!')
                $location.path('/auth');
            })
    };

});
