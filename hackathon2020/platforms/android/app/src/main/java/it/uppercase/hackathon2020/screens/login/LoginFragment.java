package it.uppercase.hackathon2020.screens.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.screens.ScreensNavigator;
import it.uppercase.hackathon2020.screens.common.BaseFragmentSplash;
import it.uppercase.hackathon2020.screens.common.ViewMvcFactory;
import it.uppercase.hackathon2020.screens.common.dialog.DialogsFactory;
import it.uppercase.hackathon2020.screens.main.UtilCallback;
import it.uppercase.hackathon2020.user.AuthenticationUseCase;

public class LoginFragment extends BaseFragmentSplash implements
        LoginViewMvc.Listener,
        AuthenticationUseCase.Listener {
    private final String TAG = this.getClass().getSimpleName();

    private LoginViewMvc mViewMVC;
    private ViewMvcFactory mViewMvcFactory;
    private DialogsFactory mDialogsFactory;
    private ScreensNavigator mScreensNavigator;
    private AuthenticationUseCase mAuthenticationUseCase;

    private UtilCallback utilCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        utilCallback = (UtilCallback) context;
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvcFactory = getCompositionRoot().getViewMvcFactory();
        mAuthenticationUseCase = getCompositionRoot().getAuthenticationUseCase();
        mDialogsFactory = getCompositionRoot().getDialogsFactory();

        mAuthenticationUseCase.registerListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewMVC = mViewMvcFactory.newLoginViewMvc(container);
        return mViewMVC.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);
    }

    @Override
    public void onClickPasswordReset() {
        mScreensNavigator.showDialogFragment(
                mDialogsFactory.newPasswordResetDialog(
                        getString(R.string.want_reset_password_email_question),
                        getString(R.string.insert_account_email),
                        getString(R.string.send_email),
                        getString(R.string.cancel)
                ));
    }

    @Override
    public void onClickLogin(String email, String password) {
        mViewMVC.setProgressDialogVisibility(true);
        mAuthenticationUseCase.login(email, password);
    }

    @Override
    public void onShowSnackbar(int message) {
        mViewMVC.setProgressDialogVisibility(false);
        utilCallback.showSnackbar(getString(message));
    }

    @Override
    public void onAuthOperationSuccess() {
        mViewMVC.setProgressDialogVisibility(false);
        utilCallback.goToMainActivity();
    }

    @Override
    public void onEmailSendingResult(String email, boolean isSuccessful) {

    }
}
