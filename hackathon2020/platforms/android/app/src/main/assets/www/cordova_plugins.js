cordova.define('cordova/plugin_list', function(require, exports, module) {
  module.exports = [
    {
      "id": "cordova-plugin-firebase.PluginFirebase",
      "file": "plugins/cordova-plugin-firebase/www/PluginFirebase.js",
      "pluginId": "cordova-plugin-firebase",
      "clobbers": [
        "PluginFirebase"
      ]
    }
  ];
  module.exports.metadata = {
    "cordova-plugin-whitelist": "1.3.4",
    "cordova-plugin-firebase": "0.0.1"
  };
});