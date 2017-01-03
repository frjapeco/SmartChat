var filters = angular.module('smartchat.filters',[]);

filters.filter('findByName',function() {
    return function(array, name) {
        var i;

        for (i = 0; i < array.length; i++)
            if (array[i].name == name)
                return array[i];
        return null;
    }
});

filters.filter('truncate', function() {
    return function(string, max) {
        if (string.length > max)
            return string.slice(0,max) + "...";
        return string;
    }
});