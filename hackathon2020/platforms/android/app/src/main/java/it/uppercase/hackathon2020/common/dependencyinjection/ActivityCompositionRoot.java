package it.uppercase.hackathon2020.common.dependencyinjection;

import android.view.LayoutInflater;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import it.uppercase.hackathon2020.room.RoomUseCase;
import it.uppercase.hackathon2020.screens.ScreensNavigator;
import it.uppercase.hackathon2020.screens.common.ViewMvcFactory;
import it.uppercase.hackathon2020.screens.common.dialog.DialogsFactory;
import it.uppercase.hackathon2020.screens.common.dialog.DialogsFactoryImpl;
import it.uppercase.hackathon2020.screens.common.dialog.DialogsManager;
import it.uppercase.hackathon2020.user.AuthenticationUseCase;
import it.uppercase.hackathon2020.user.FetchUserInfoUseCase;

public class ActivityCompositionRoot {

    private final AppCompatActivity mActivity;

    private ScreensNavigator mScreensNavigator;
    private DialogsManager mDialogsManager;

    public ActivityCompositionRoot(AppCompatActivity activity) {
        mActivity = activity;
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(LayoutInflater.from(mActivity));
    }

    public ScreensNavigator getScreensNavigator() {
        if (mScreensNavigator == null) {
            mScreensNavigator = new ScreensNavigator(
                    mActivity.getSupportFragmentManager(),
                    FirebaseAuth.getInstance(),
                    getFetchUserInfoUseCase(),
                    getDialogsManager()
            );
        }
        return mScreensNavigator;
    }

    public DialogsManager getDialogsManager() {
        if (mDialogsManager == null) {
            mDialogsManager = new DialogsManager(mActivity.getSupportFragmentManager());
        }
        return mDialogsManager;
    }
    public DialogsFactory getDialogsFactory() {
        return new DialogsFactoryImpl();
    }

    public FetchUserInfoUseCase getFetchUserInfoUseCase() {
        return new FetchUserInfoUseCase(
                FirebaseDatabase.getInstance(),
                FirebaseAuth.getInstance()
        );
    }

    /*
    public StorageHelper getStorageHelper() {
        return new StorageHelper(
                FirebaseStorage.getInstance()
        );
    }

     */

    public AuthenticationUseCase getAuthenticationUseCase() {
        return new AuthenticationUseCase(
                FirebaseAuth.getInstance()
        );
    }

    public RoomUseCase getRoomUseCase() {
        return new RoomUseCase(
                FirebaseDatabase.getInstance(),
                getFetchUserInfoUseCase()
        );
    }
}
