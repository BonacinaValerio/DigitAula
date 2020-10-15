package it.uppercase.hackathon2020.screens.main;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.common.dependencyinjection.ActivityCompositionRoot;
import it.uppercase.hackathon2020.screens.ScreensNavigator;
import it.uppercase.hackathon2020.user.FetchUserInfoUseCase;

public class MainActivity extends AppCompatActivity implements
        UtilCallback{
    public static String ROOM_ID;
    private final String TAG = this.getClass().getSimpleName();

    private ActivityCompositionRoot mActivityCompositionRoot;

    private FetchUserInfoUseCase mFetchUserInfoUseCase;
    private ScreensNavigator mScreensNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        Intent intent = getIntent();
        String roomId = intent.getStringExtra(ROOM_ID);

        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mScreensNavigator.toRoomScreen(roomId);
    }

    public ActivityCompositionRoot getCompositionRoot() {
        if (mActivityCompositionRoot == null) {
            mActivityCompositionRoot = new ActivityCompositionRoot(this);
        }
        return mActivityCompositionRoot;
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.main), message, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.ok), v -> { })
                .show();
    }

    @Override
    public void goToMainActivity() {
        // GOTO
    }

    @Override
    public void close() {
        finish();
    }


    @Override
    public void onBackPressed() {
        if (!mScreensNavigator.navigateBack()) {
            super.onBackPressed();
        }
    }
}
