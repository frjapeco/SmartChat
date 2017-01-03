var services = angular.module('smartchat.services',[]);

services.factory('StompClient', function($rootScope) {
    var client;

    function init(url) {
        client = Stomp.over(new SockJS(url));
    }

    function connect(successCallback,errorCallback) {
        client.connect({},function(frame) {
            $rootScope.$apply(function() {
                successCallback(frame);
            });
        }, function(error) {
            $rootScope.$apply(function() {
                errorCallback(error);
            });
        });
    }

    function subscribe(destination,callback) {
        client.subscribe(destination,function(message) {
            $rootScope.$apply(function() {
                callback(message);
            });
        });
    }

    function send(destination,headers,message) {
        client.send(destination,headers,message);
    }

    return {
        init: init,
        connect: connect,
        subscribe: subscribe,
        send: send
    }
});
