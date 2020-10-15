
// var jsobject = ['{"endAtHour":"11","startAtHour":"10","startAtMinutes":"00","endAtMinutes":"00","parent_id":"5D3","name":"Matematica","id":"5D3_M","prof":"Carlo Jacobelli"}',
// '{"endAtHour":"12","startAtHour":"11","startAtMinutes":"00","endAtMinutes":"00","parent_id":"5D3","name":"Scienze","id":"5D3_S","prof":"Mario Rossi"}',
// '{"endAtHour":"15","startAtHour":"14","startAtMinutes":"00","endAtMinutes":"00","parent_id":"5D3","name":"Geografia","id":"5D3_S","prof":"Mario Rossi"}',
// '{"endAtHour":"16","startAtHour":"15","startAtMinutes":"00","endAtMinutes":"00","parent_id":"5D3","name":"Storia","id":"5D3_S","prof":"Mario Rossi"}',
// '{"endAtHour":"17","startAtHour":"16","startAtMinutes":"00","endAtMinutes":"00","parent_id":"5D3","name":"Filosofia","id":"5D3_S","prof":"Mario Rossi"}']
var firebase = cordova.require('cordova-plugin-firebase.PluginFirebase');


//var j = {"rooms":[{"idClass":"5D","name":"Matematica","id":"5D_M","drive":"","live":"http://www.google.it"},{"idClass":"5D","name":"Geografia","id":"5D_M","drive":"","live":"http://www.google.it"},{"idClass":"5D","name":"Storia","id":"5D_M","drive":"","live":"http://www.google.it"}],"schedules":[{"subject_id":"5D_G","endAtHour":"09","startAtHour":"08","startAtMinutes":"00","endAtMinutes":"00","parent_id":"5D4","name":"Geografia","id":"5D4_G","prof":"Marco Gatti"},{"subject_id":"5D_G","endAtHour":"17","startAtHour":"16","startAtMinutes":"00","endAtMinutes":"00","parent_id":"5D4","name":"Matematica","id":"5D4_G","prof":"Marco Gatti"},{"subject_id":"5D_G","endAtHour":"09","startAtHour":"08","startAtMinutes":"00","endAtMinutes":"00","parent_id":"5D4","name":"Geografia","id":"5D4_G","prof":"Marco Gatti"},{"subject_id":"5D_G","endAtHour":"09","startAtHour":"08","startAtMinutes":"00","endAtMinutes":"00","parent_id":"5D4","name":"Geografia","id":"5D4_G","prof":"Marco Gatti"},{"subject_id":"5D_G","endAtHour":"09","startAtHour":"08","startAtMinutes":"00","endAtMinutes":"00","parent_id":"5D4","name":"Geografia","id":"5D4_G","prof":"Marco Gatti"}]}
var j = {};
var done = "false";

// salva il punteggio della partita e estrai la classifica aggiornata
function init() {
      firebase.init().then(function(result) {
        j = result;
        done = "true";
      }).catch(alert);
}


function schedule_length() {
  return j.schedules.length;
}

function rooms_length() {
  return j.rooms.length;
}


function getSubjectName(i) {
  var obj = j.rooms[i];
  return obj.name;
}


function getIdFromRoom(i) {
  return j.rooms[i].id;
}

function getIdFromSchedule(i) {
  return j.schedules[i].subject_id;
}




function getStartingHour(i) {
  var obj = j.schedules[i];
  return obj.startAtHour + ":" + obj.startAtMinutes;
}

function getName(i){
  var obj = j.schedules[i];
  return obj.name;
}

function getProf(i){
  var obj = j.schedules[i];
  return obj.prof;
}

function isSomethingLive() {
  var d = new Date();
  var n = parseInt(d.getHours() + "." + d.getMinutes());

  for( i=0; i< j.schedules.length; i++) {
    var obj = j.schedules[i];
    var orarioInizio = parseInt(obj.startAtHour + "." + obj.startAtMinutes);
    var orarioFine = parseInt(obj.endAtHour + "." + obj.endAtMinutes);

    if(n < orarioFine && n >= orarioInizio ){
      return i;
    }
  }
  return -1;
}

function nextEvent() {
    var d = new Date();
    var n = parseInt(d.getHours() + "." + d.getMinutes());

    for( i=0; i< j.schedules.length; i++) {
      var obj = j.schedules[i];
      var orarioInizio = parseInt(obj.startAtHour + "." + obj.startAtMinutes);
      var orarioFine = parseInt(obj.endAtHour + "." + obj.endAtMinutes);

      if(n < orarioFine && n >= orarioInizio ){
        return i;
      }
    }

    for( i=0; i< j.schedules.length; i++) {
      var obj = j.schedules[i];
      var orarioInizio = parseInt(obj.startAtHour + "." + obj.startAtMinutes);
      var orarioFine = parseInt(obj.endAtHour + "." + obj.endAtMinutes);

      if( n <= orarioInizio ){
        return i;
      }
    }
    return -2;
}



function toRoom(id) {
  firebase.toRoom(id);
}
