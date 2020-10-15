package it.uppercase.hackathon2020.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import it.uppercase.hackathon2020.common.BaseObservable;

public class FetchUserInfoUseCase extends BaseObservable<FetchUserInfoUseCase.Listener> {
    private final String TAG = this.getClass().getSimpleName();
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_STUDENT = "student";
    public static final String ROLE_PROF = "prof";

    public interface Listener {
        void onUserReloaded();
        void onUserReloadFailed(Exception e);
        void onInfoFetched(String info);
    }

    private final FirebaseDatabase mFirebaseDatabase;
    private final FirebaseAuth mFirebaseAuth;

    public FetchUserInfoUseCase(FirebaseDatabase mFirebaseDatabase,
                                FirebaseAuth mFirebaseAuth) {
        this.mFirebaseDatabase = mFirebaseDatabase;
        this.mFirebaseAuth = mFirebaseAuth;
    }

    public FirebaseUser getUser() {
        return mFirebaseAuth.getCurrentUser();
    }

    public String getUid() {
        return getUser().getUid();
    }

    public void getName() {
        mFirebaseDatabase.getReference().child("users").child(getUid()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (Listener listener : getListeners()) {
                    listener.onInfoFetched((String) snapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                notifyFailure(error.toException());
            }
        });
    }

    public void reloadUser() {
        FirebaseUser firebaseUser = getUser();
        if (firebaseUser != null) {
            firebaseUser.reload().addOnCompleteListener(task -> {
                if (task.isSuccessful())
                    notifySuccess();
                else
                    notifyFailure(task.getException());
            });
        }
        else
            notifySuccess();
    }

    public void getPermission() {
        FirebaseUser firebaseUser = getUser();
        mFirebaseDatabase.getReference().child("users")
                .child(firebaseUser.getUid())
                .child("role")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (Listener listener : getListeners()) {
                    listener.onInfoFetched((String) snapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                notifyFailure(error.toException());
            }
        });
    }

    private void notifyFailure(Exception e) {
        for (Listener listener : getListeners()) {
            listener.onUserReloadFailed(e);
        }
    }

    private void notifySuccess() {
        for (Listener listener : getListeners()) {
            listener.onUserReloaded();
        }
    }
}
