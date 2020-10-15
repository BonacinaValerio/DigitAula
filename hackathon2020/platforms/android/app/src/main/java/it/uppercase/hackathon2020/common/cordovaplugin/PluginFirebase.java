package it.uppercase.hackathon2020.common.cordovaplugin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import it.uppercase.hackathon2020.screens.main.MainActivity;

/**
 * This class echoes a string called from JavaScript.
 */
public class PluginFirebase extends CordovaPlugin {
    private static final String TAG = "PluginFirebase";

    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseFunctions mFunctions;

    @Override
    protected void pluginInitialize() {
        // istanzio le variabili del plugin
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mFunctions = FirebaseFunctions.getInstance();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        switch (action) {
            case "init":
                return init(args, callbackContext);
            case "toRoom":
                return toRoom(args, callbackContext);
            default:
                return false;
        }
    }

    private boolean toRoom(JSONArray args, CallbackContext callbackContext) {
        try {
            // avvia l'activity MainActivity
            Context context = cordova.getActivity().getApplicationContext();
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(MainActivity.ROOM_ID, args.optString(0));
            this.cordova.getActivity().startActivity(intent);
            // ritorna stato di OK
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, 0));
        } catch (Exception e) {
            // ritorna stato di ERRORE
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, 1));
        }
        return true;
    }

    private boolean init(JSONArray args, CallbackContext callbackContext) {
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference mClassRef = database.getReference().child("users").child(user.getUid()).child("class");
        mClassRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String idClass = (String) snapshot.getValue();
                getAllDaySubject(idClass, callbackContext);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
                callbackContext.sendPluginResult(convertToPluginResult(error));
            }
        });
        return true;
    }

    private void getAllDaySubject(String idClass, CallbackContext callbackContext) {
        DatabaseReference mScheduleRef = database.getReference().child("schedule");
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String dayIdStart = idClass + (day-1) + "_";
        String dayIdEnd = idClass + (day);
        Log.i(TAG, "getAllDaySubject: "+dayIdStart);
        mScheduleRef.orderByKey().startAt(dayIdStart).endAt(dayIdEnd).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                JSONArray jsonArr = new JSONArray();
                for(DataSnapshot child : snapshot.getChildren()) {
                    jsonArr.put(new JSONObject((Map) child.getValue()));
                }

                JSONArray sortedJsonArray = new JSONArray();

                List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                for (int i = 0; i < jsonArr.length(); i++) {
                    try {
                        jsonValues.add(jsonArr.getJSONObject(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Collections.sort( jsonValues, new Comparator<JSONObject>() {
                    //You can change "Name" with "ID" if you want to sort by ID
                    private static final String KEY_NAME = "startAtHour";

                    @Override
                    public int compare(JSONObject a, JSONObject b) {
                        String valA = new String();
                        String valB = new String();

                        try {
                            valA = (String) a.get(KEY_NAME);
                            valB = (String) b.get(KEY_NAME);
                        }
                        catch (JSONException e) {
                            //do something
                        }

                        return valA.compareTo(valB);
                        //if you want to change the sort order, simply use the following:
                        //return -valA.compareTo(valB);
                    }
                });

                for (int i = 0; i < jsonArr.length(); i++) {
                    if (jsonValues.get(i).has("prof"))
                        sortedJsonArray.put(jsonValues.get(i));
                }

                Log.i(TAG, "onDataChange: "+sortedJsonArray.toString());

                getAllClassSubject(sortedJsonArray, idClass, callbackContext);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
                callbackContext.sendPluginResult(convertToPluginResult(error));
            }
        });
    }

    private void getAllClassSubject(JSONArray sortedJsonArray, String idClass, CallbackContext callbackContext) {
        DatabaseReference mRoomRef = database.getReference().child("room");
        mRoomRef.orderByChild("idClass").equalTo(idClass).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                JSONArray jsonArr = new JSONArray();
                for(DataSnapshot child : snapshot.getChildren()) {
                    jsonArr.put(new JSONObject((Map) child.getValue()));
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("rooms", jsonArr);
                    jsonObject.put("schedules", sortedJsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "onDataChange: "+jsonObject.toString());
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, jsonObject));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
                callbackContext.sendPluginResult(convertToPluginResult(error));
            }
        });
    }


    // converti in oggetto manipolabile da javascript nella WebView
    private PluginResult convertToPluginResult(Object error) {
        String code;
        String message, detail = "-";
        if (error instanceof DatabaseError) {
            code = String.valueOf(((DatabaseError) error).getCode());
            message = ((DatabaseError) error).getMessage();
            detail = ((DatabaseError) error).getDetails();
        }
        else {
            code = ((FirebaseFunctionsException) error).getCode().name();
            message = ((FirebaseFunctionsException) error).getMessage();
        }
        JSONObject data = new JSONObject();
        try {
            data.put("code", code);
            data.put("message", message);
            data.put("details", detail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PluginResult result = new PluginResult(PluginResult.Status.ERROR, data);

        return result;
    }
}
