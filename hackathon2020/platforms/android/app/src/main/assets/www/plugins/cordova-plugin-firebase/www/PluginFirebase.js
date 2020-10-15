// definisco il plugin cordova
cordova.define("cordova-plugin-firebase.PluginFirebase", function(require, exports, module) {

    var exec = require('cordova/exec');

    let init = function() {
        return new Promise(function (resolve, reject) {
            exec(function (response) {
                resolve(response);
            },
            function(response) {
                var error = "Error: "+response.code+"\nMessage: "+response.message+"\nDetails: "+response.details;
                reject(error);
            }, 
            'PluginFirebase',
            'init',
            []);
        });

    };

        let toRoom = function(roomId) {
            return new Promise(function (resolve, reject) {
                exec(function (response) {
                    resolve(response);
                },
                function(response) {
                    var error = "Error: "+response.code+"\nMessage: "+response.message+"\nDetails: "+response.details;
                    reject(error);
                },
                'PluginFirebase',
                'toRoom',
                [
                    roomId
                ]);
            });

        };

    if (typeof module !== undefined && module.exports) {
        module.exports.toRoom = toRoom;
        module.exports.init = init;
    }
});
