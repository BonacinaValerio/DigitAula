const functions = require('firebase-functions');

// The Firebase Admin SDK to access Cloud Firestore.
const admin = require('firebase-admin');
admin.initializeApp();

exports.deleteWorkTopic = functions.database.ref('/work_topic/{id}/member/{uid}').onDelete((snapshot, context) => {
	var id_deleta = snapshot.key;
	var refmember = snapshot.ref.parent;
	return refmember.once("value", (snap) => {
	  	if (snap.numChildren() === 0) 
	  		refmember.parent.remove();
	}).then(() => {
	    return console.log('called deleteWorkTopic');
	});
});
