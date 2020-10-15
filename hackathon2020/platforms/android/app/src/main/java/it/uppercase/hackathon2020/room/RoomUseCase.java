package it.uppercase.hackathon2020.room;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.common.BaseObservable;
import it.uppercase.hackathon2020.common.model.SubjectRoom;
import it.uppercase.hackathon2020.common.model.WorkTopic;
import it.uppercase.hackathon2020.user.FetchUserInfoUseCase;

public class RoomUseCase extends BaseObservable<RoomUseCase.Listener> implements FetchUserInfoUseCase.Listener {
    private final String TAG = this.getClass().getSimpleName();

    private static int LEAVE_REQUEST = 1, JOIN_REQUEST = 0, ADD_WORK_TOPIC_REQUEST = 2;
    private int mRequest;
    private String mIdWorkTopic, mWorkTopicTitle, mWorkTopicDescription, mRoomId;


    public interface Listener {
        void onShowSnackbar(int message);
        void onDBOperationSuccess(SubjectRoom subjectRoom, List<WorkTopic> workTopics);
    }
    private FirebaseDatabase mFirebaseDatabase;
    private FetchUserInfoUseCase mFetchUserInfoUseCase;

    public RoomUseCase(FirebaseDatabase mFirebaseDatabase, FetchUserInfoUseCase mFetchUserInfoUseCase) {
        this.mFirebaseDatabase = mFirebaseDatabase;
        this.mFetchUserInfoUseCase = mFetchUserInfoUseCase;

        mFetchUserInfoUseCase.registerListener(this);
    }

    public void getRoomDetail(String roomId) {
        mFirebaseDatabase.getReference().child("room").child(roomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SubjectRoom subjectRoom = snapshot.getValue(SubjectRoom.class);
                notifySuccess(subjectRoom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
                notifyShowSnackbar(R.string.error_network_short);
            }

        });
    }

    public void getWorksTopic(String roomId) {
        mFirebaseDatabase.getReference().child("work_topic").orderByChild("roomId")
                .equalTo(roomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<WorkTopic> list = new ArrayList<>();
                for (DataSnapshot child: snapshot.getChildren()) {
                    list.add(child.getValue(WorkTopic.class));
                }

                notifySuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
                notifyShowSnackbar(R.string.error_network_short);
            }
        });
    }

    public void joinWorkTopic(String idWorkTopic) {
        this.mRequest = 0;
        this.mIdWorkTopic = idWorkTopic;
        mFetchUserInfoUseCase.getName();
    }

    public void leaveWorkTopic(String idWorkTopic) {
        this.mRequest = 1;
        this.mIdWorkTopic = idWorkTopic;
        mFetchUserInfoUseCase.getName();
    }

    public void addWorkTopic(String title, String description, String mRoomId) {
        this.mRequest = 2;
        this.mRoomId = mRoomId;
        this.mWorkTopicTitle = title;
        this.mWorkTopicDescription = description;
        mFetchUserInfoUseCase.getName();
    }

    @Override
    public void onUserReloaded() {
        // GOTO
    }

    @Override
    public void onUserReloadFailed(Exception e) {
        // GOTO
    }

    @Override
    public void onInfoFetched(String name) {
        DatabaseReference workTopicRef = mFirebaseDatabase.getReference().child("work_topic");

        if (mRequest == JOIN_REQUEST || mRequest ==  LEAVE_REQUEST) {
            DatabaseReference memberRef = workTopicRef
                    .child(mIdWorkTopic).child("member").child(mFetchUserInfoUseCase.getUid());
            if (mRequest == JOIN_REQUEST) {
                memberRef.setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notifySuccess();
                        } else {
                            Log.e(TAG, "onCancelled: ", task.getException());
                            notifyShowSnackbar(R.string.error_network_short);
                        }
                    }
                });
            } else if (mRequest == LEAVE_REQUEST) {
                memberRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notifySuccess();
                        } else {
                            Log.e(TAG, "onCancelled: ", task.getException());
                            notifyShowSnackbar(R.string.error_network_short);
                        }
                    }
                });
            }
        }
        else if (mRequest == ADD_WORK_TOPIC_REQUEST) {
            String mKey = workTopicRef.push().getKey();
            HashMap<String, String> member = new HashMap<>();
            member.put(mFetchUserInfoUseCase.getUid(), name);
            WorkTopic workTopic = new WorkTopic(mKey, mRoomId, mWorkTopicDescription, mWorkTopicTitle, member);
            workTopicRef.child(mKey).setValue(workTopic).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        notifySuccess();
                    }
                    else {
                        Log.e(TAG, "onCancelled: ", task.getException());
                        notifyShowSnackbar(R.string.error_network_short);
                    }
                }
            });
        }
        resetValue();
    }

    private void resetValue() {
        mRequest = -1;
        mIdWorkTopic = null;
        mWorkTopicDescription = null;
        mWorkTopicTitle = null;
    }

    public void saveNewLive(String id, String live) {
        mFirebaseDatabase.getReference().child("room").child(id).child("live").setValue(live);
    }

    public void saveNewDrive(String id, String live) {
        mFirebaseDatabase.getReference().child("room").child(id).child("drive").setValue(live);
    }

    private void notifyShowSnackbar(int message) {
        for (Listener listener : getListeners()) {
            listener.onShowSnackbar(message);
        }
    }

    private void notifySuccess(SubjectRoom subjectRoom) {
        for (Listener listener : getListeners()) {
            listener.onDBOperationSuccess(subjectRoom, null);
        }
    }

    private void notifySuccess() {
        for (Listener listener : getListeners()) {
            listener.onDBOperationSuccess(null, null);
        }
    }

    private void notifySuccess(List<WorkTopic> workTopics) {
        for (Listener listener : getListeners()) {
            listener.onDBOperationSuccess(null, workTopics);
        }
    }
}
