package it.uppercase.hackathon2020.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.common.dependencyinjection.ActivityCompositionRoot;
import it.uppercase.hackathon2020.screens.ScreensNavigator;
import it.uppercase.hackathon2020.user.FetchUserInfoUseCase;

public class SplashActivity extends AppCompatActivity implements
        FetchUserInfoUseCase.Listener,
        UtilCallback {
    private final String TAG = this.getClass().getSimpleName();

    private ActivityCompositionRoot mActivityCompositionRoot;

    private FetchUserInfoUseCase mFetchUserInfoUseCase;
    private ScreensNavigator mScreensNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);

        mFetchUserInfoUseCase = getCompositionRoot().getFetchUserInfoUseCase();
        mScreensNavigator = getCompositionRoot().getScreensNavigator();

        mFetchUserInfoUseCase.registerListener(this);
        mFetchUserInfoUseCase.reloadUser();
    }

    public ActivityCompositionRoot getCompositionRoot() {
        if (mActivityCompositionRoot == null) {
            mActivityCompositionRoot = new ActivityCompositionRoot(this);
        }
        return mActivityCompositionRoot;
    }

    @Override
    public void onUserReloaded() {
        if (mFetchUserInfoUseCase.getUser() == null) {
            mScreensNavigator.toLoginScreen();
        }
        else
            goToMainActivity();
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, WebActivity.class);
        //intent.putExtra(MainActivity.ROOM_ID, "5D_M");
        startActivity(intent);
        finish();
    }

    @Override
    public void onUserReloadFailed(Exception e) {
        Log.e(TAG, "onUserReloadFailed: ", e);

        Snackbar.make(
                findViewById(android.R.id.content).getRootView(),
                getString(R.string.error_try_later),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(
                        R.string.retry,
                        v -> mFetchUserInfoUseCase.reloadUser()
                ).show();
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.main), message, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.ok), v -> { })
                .show();
    }

    @Override
    public void close() {
        // GOTO
    }

    @Override
    public void onInfoFetched(String info) {
        // GOTO
    }
}
