var controllers = angular.module('smartchat.controllers',[]);

controllers.controller('IndexController',function ($scope,$filter,StompClient) {
    $scope.conversations = [];
    $scope.me = '';
    $scope.current = '';
    $scope.message = '';
    $scope.loading = true;

    function bootstrap(message)  {
        var bootstrap = JSON.parse(message.body);

        $scope.me = bootstrap.me;
        $scope.conversations.push({name: 'home', online: true, pendingMessages: 0, messages: []});
        bootstrap.onlineUsers.forEach(function(username) {
            if (username != $scope.me)
                $scope.conversations.push({name: username, online: true, pendingMessages: 0, messages: []});
        });
        $scope.current = 'home';
        $scope.loading = false;
    };

    function login(message) {
        var username = message.body;
        var conversation = $filter('findByName')($scope.conversations,username);

        if (conversation)
            conversation.online = true;
        else
            $scope.conversations.push({name: username, online: true, pendingMessages: 0, messages: []});
    };

    function logout(message) {
        var username = message.body;
        var conversation = $filter('findByName')($scope.conversations,username);

        conversation.online = false;
    };

    function receiveMessage(message) {
        var message = JSON.parse(message.body);
        var conversation = $filter('findByName')($scope.conversations,'home');

        conversation.messages.push({sender: message.sender, timestamp: new Date(), content: message.content});
        if (conversation.name != $scope.current)
            conversation.pendingMessages++;
    };

    function receivePrivateMessage(message) {
        var message = JSON.parse(message.body);
        var conversation = $filter('findByName')($scope.conversations,message.sender);

        conversation.messages.push({sender: message.sender, timestamp: new Date(), content: message.content});
        if (conversation.name != $scope.current)
            conversation.pendingMessages++;
    };

    $scope.init = function() {
        StompClient.init('/chatserver');
        StompClient.connect(function(frame) {
            StompClient.subscribe("/app/chat.bootstrap",bootstrap);
            StompClient.subscribe("/topic/chat.login",login);
            StompClient.subscribe("/topic/chat.logout",logout);
            StompClient.subscribe("/topic/chat.messages",receiveMessage);
            StompClient.subscribe("/user/queue/chat.private",receivePrivateMessage);
        }, function(error) {
        });
    };

    $scope.readKey = function(e) {
        if (e.keyCode == 13 && $scope.message != '') {
            if ($scope.current == 'home') {
                StompClient.send('/app/chat.messages',{},$scope.message);
            } else {
                var conversation = $filter('findByName')($scope.conversations,$scope.current);

                StompClient.send('/app/chat.messages.' + $scope.current,{},$scope.message);
                conversation.messages.push({sender: $scope.me, timestamp: new Date(), content: $scope.message});
            }
            $scope.message = '';
        }
    };

});