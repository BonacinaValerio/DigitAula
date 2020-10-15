package it.uppercase.hackathon2020.screens.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.screens.common.progressdialog.MyProgressDialog;
import it.uppercase.hackathon2020.screens.login.FormUtil;
import it.uppercase.hackathon2020.screens.main.UtilCallback;
import it.uppercase.hackathon2020.user.AuthenticationUseCase;

public class PasswordResetDialog extends BaseDialogSplash implements
        AuthenticationUseCase.Listener {

    public static final String ARG_TITLE = "ARG_TITLE";
    public static final String ARG_MESSAGE = "ARG_MESSAGE";
    public static final String ARG_POSITIVE_BUTTON_CAPTION = "ARG_POSITIVE_BUTTON_CAPTION";
    public static final String ARG_NEGATIVE_BUTTON_CAPTION = "ARG_NEGATIVE_BUTTON_CAPTION";

    private FormUtil formUtil;
    private MyProgressDialog myProgressDialog;
    private AuthenticationUseCase mAuthenticationUseCase;

    private TextView title, message;
    private EditText email;
    private Button okBtn, cancelBtn;

    private UtilCallback utilCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        utilCallback = (UtilCallback) context;
    }

    public static PasswordResetDialog newInstance(String title, String message, String positiveButtonCaption, String negativeButtonCaption) {
        PasswordResetDialog passwordResetDialog = new PasswordResetDialog();
        Bundle args = new Bundle(4);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putString(ARG_POSITIVE_BUTTON_CAPTION, positiveButtonCaption);
        args.putString(ARG_NEGATIVE_BUTTON_CAPTION, negativeButtonCaption);
        passwordResetDialog.setArguments(args);
        return passwordResetDialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.Dialog);
        myProgressDialog = new MyProgressDialog(getContext(), getString(R.string.loading));
        formUtil = new FormUtil(getContext());
        mAuthenticationUseCase = getCompositionRoot().getAuthenticationUseCase();
        mAuthenticationUseCase.registerListener(this);

        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_password_reset);

        initSubViews(dialog.getWindow().getDecorView());

        populateSubViews();

        return dialog;
    }


    private void initSubViews(View rootView) {
        title = rootView.findViewById(R.id.title);
        message = rootView.findViewById(R.id.message);
        email = rootView.findViewById(R.id.email);
        okBtn = rootView.findViewById(R.id.ok_btn);
        cancelBtn = rootView.findViewById(R.id.cancel_btn);

        cancelBtn.setOnClickListener(v -> dismiss());
        okBtn.setOnClickListener(v -> {
            if (formUtil.validateFormPasswordReset(email)) {
                hideKeyboard(getContext(), email.getRootView());
                myProgressDialog.show();
                mAuthenticationUseCase.sendEmailPasswordReset(email.getText().toString());
            }
        });
    }

    private void populateSubViews() {
        String title = getArguments().getString(ARG_TITLE);
        String message = getArguments().getString(ARG_MESSAGE);
        String positiveButtonCaption = getArguments().getString(ARG_POSITIVE_BUTTON_CAPTION);
        String negativeButtonCaption = getArguments().getString(ARG_NEGATIVE_BUTTON_CAPTION);

        this.title.setText(TextUtils.isEmpty(title) ? "" : title);
        this.message.setText(TextUtils.isEmpty(message) ? "" : message);
        okBtn.setText(positiveButtonCaption);
        cancelBtn.setText(negativeButtonCaption);
    }


    @Override
    public void onShowSnackbar(int message) {
        myProgressDialog.hide();
        utilCallback.showSnackbar(getString(message));
    }

    @Override
    public void onEmailSendingResult(String email, boolean isSuccessful) {
        myProgressDialog.hide();
        if (isSuccessful) {
            dismiss();
            utilCallback.showSnackbar(getString(R.string.email_sent_to)+email);
        }
    }

    @Override
    public void onAuthOperationSuccess() {
        // nessuna azione
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
