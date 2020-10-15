package it.uppercase.hackathon2020.user;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.common.BaseObservable;

public class AuthenticationUseCase extends BaseObservable<AuthenticationUseCase.Listener> {
    private final String TAG = this.getClass().getSimpleName();

    public interface Listener {
        void onShowSnackbar(int message);
        void onAuthOperationSuccess();
        void onEmailSendingResult(String email, boolean isSuccessful);
    }
    private FirebaseAuth mFirebaseAuth;

    public AuthenticationUseCase(FirebaseAuth mFirebaseAuth) {
        this.mFirebaseAuth = mFirebaseAuth;
    }

    public void login(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            notifySuccess();
                        }
                        else {
                            Exception e = task.getException();
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithEmail:failure", e);
                            // gestione di ogni tipo di errore che può risultare
                            int defaultError = R.string.unknown_error;
                            int message;
                            if (e instanceof FirebaseAuthInvalidCredentialsException)
                                message = R.string.wrong_credentials;
                            else if (e instanceof FirebaseAuthInvalidUserException) {
                                String code = ((FirebaseAuthInvalidUserException) e).getErrorCode();
                                switch (code) {
                                    case "ERROR_USER_NOT_FOUND":
                                        message = R.string.user_not_exist;
                                        break;
                                    case "ERROR_USER_DISABLED":
                                        message = R.string.account_disabled;
                                        break;
                                    default:
                                        message = defaultError;
                                }
                            }
                            else
                                message = defaultError;
                            notifyShowSnackbar(message);
                        }
                    }
                });
    }

    public void register(String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        notifySuccess();
                    } else {
                        Exception e = task.getException();
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", e);
                        // gestione di ogni tipo di errore che può risultare
                        int defaultError = R.string.unknown_error;
                        int message;
                        if (e instanceof FirebaseAuthWeakPasswordException)
                            message = R.string.weak_password; // controllo ulteriore gestito ANCHE da firebase
                        else if (e instanceof FirebaseAuthInvalidCredentialsException)
                            message = R.string.wrong_credentials; // controllo ulteriore gestito ANCHE da firebase
                        else if (e instanceof FirebaseAuthUserCollisionException)
                            message = R.string.email_already_registered;
                        else
                            message = defaultError;
                        notifyShowSnackbar(message);
                    }
                });
    }

    public void sendEmailPasswordReset(String email) {
        mFirebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "sendEmailPasswordReset:success");
                        notifyEmailSendingResult(email, true);
                    }
                    else {
                        Log.e(TAG, "sendPasswordResetEmail:failure", task.getException());
                        notifyShowSnackbar(R.string.error_mail_not_sent);
                    }
                });
    }

    public void sendEmailVerification() {
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        firebaseUser.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    notifyEmailSendingResult(firebaseUser.getEmail(), task.isSuccessful());
                });
    }

    public void logout() {
        mFirebaseAuth.signOut();
    }

    private void notifyShowSnackbar(int message) {
        for (Listener listener : getListeners()) {
            listener.onShowSnackbar(message);
        }
    }

    private void notifySuccess() {
        for (Listener listener : getListeners()) {
            listener.onAuthOperationSuccess();
        }
    }

    private void notifyEmailSendingResult(String email, boolean isSuccessful) {
        for (Listener listener : getListeners()) {
            listener.onEmailSendingResult(email, isSuccessful);
        }
    }
}
