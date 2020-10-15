package it.uppercase.hackathon2020.screens;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.common.model.SubjectRoom;
import it.uppercase.hackathon2020.screens.common.dialog.DialogsManager;
import it.uppercase.hackathon2020.screens.login.LoginFragment;
import it.uppercase.hackathon2020.screens.room.RoomFragment;
import it.uppercase.hackathon2020.screens.room.digitalroom.DigitalRoomFragment;
import it.uppercase.hackathon2020.screens.room.worktopic.WorkTopicFragment;
import it.uppercase.hackathon2020.user.FetchUserInfoUseCase;

public class ScreensNavigator implements FirebaseAuth.AuthStateListener{
    private final String TAG = this.getClass().getSimpleName();

    private final FragmentManager mFragmentManager;
    private final FetchUserInfoUseCase mFetchUserInfoUseCase;
    private final FirebaseAuth mFirebaseAuth;
    private final DialogsManager mDialogsManager;

    public ScreensNavigator(FragmentManager mFragmentManager,
                            FirebaseAuth mFirebaseAuth,
                            FetchUserInfoUseCase mFetchUserInfoUseCase,
                            DialogsManager mDialogsManager) {
        this.mFragmentManager = mFragmentManager;
        this.mFirebaseAuth = mFirebaseAuth;
        this.mFetchUserInfoUseCase = mFetchUserInfoUseCase;
        this.mDialogsManager = mDialogsManager;
    }

    public boolean navigateBack() {
        Fragment fragmentOnTopMain = mFragmentManager.findFragmentById(R.id.main);
        if (fragmentOnTopMain != null) {
            if (fragmentOnTopMain instanceof DigitalRoomFragment) {
                ((DigitalRoomFragment) fragmentOnTopMain).onClickBack();
                return true;
            }
            else if (fragmentOnTopMain instanceof WorkTopicFragment) {
                ((WorkTopicFragment) fragmentOnTopMain).onClickBack();
                return true;
            }
            else
                return false;
        }
        else {
            return false;
        }
    }

    public boolean noFragmentOnTopMain() {
        return mFragmentManager.findFragmentById(R.id.main) == null;
    }

    public void showDialogFragment(DialogFragment dialogFragment) {
        mDialogsManager.showRetainedDialogWithTag(dialogFragment, null);
    }

    public void addAuthStateListener() {
        mFirebaseAuth.addAuthStateListener(this);
    }

    public void removeAuthStateListener() {
        mFirebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        boolean isLogged = user != null;

        if (!isLogged) {
            // back to login
        }
    }

    public void toLoginScreen() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.main, LoginFragment.newInstance()).commit();
    }

    public void toRoomScreen(String roomId) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.main, RoomFragment.newInstance(roomId)).commitNow();
    }

    public void toDigitalRoom(SubjectRoom subjectRoom) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.main, DigitalRoomFragment.newInstance(subjectRoom)).commitNow();
    }

    public void backToRoom(SubjectRoom subjectRoom) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.main, RoomFragment.newInstance(subjectRoom.getId(), subjectRoom)).commitNow();
    }

    public void backToDigitalRoom(SubjectRoom subjectRoom) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.main, DigitalRoomFragment.newInstance(subjectRoom)).commitNow();
    }

    public void toWorkTopic(SubjectRoom mSubjectRoom) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.main, WorkTopicFragment.newInstance(mSubjectRoom)).commitNow();
    }
}
