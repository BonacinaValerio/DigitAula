package it.uppercase.hackathon2020.screens.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.screens.common.BaseViewMvc;
import it.uppercase.hackathon2020.screens.common.ViewMvcFactory;
import it.uppercase.hackathon2020.screens.common.progressdialog.MyProgressDialog;

public class LoginViewMvcImpl extends BaseViewMvc<LoginViewMvc.Listener> implements LoginViewMvc {
    private final String TAG = this.getClass().getSimpleName();

    private EditText email, password1;
    private Button interactBtn;
    private MaterialButton forgotBtn;

    private MyProgressDialog myProgressDialog;

    public LoginViewMvcImpl(LayoutInflater inflater, ViewGroup container, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.layout_authentication_email, container, false));
        Context context = inflater.getContext();
        FormUtil formUtil = new FormUtil(context);
        myProgressDialog = new MyProgressDialog(context, context.getString(R.string.loading));

        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password_1);
        forgotBtn = findViewById(R.id.forgot_btn);
        interactBtn = findViewById(R.id.interact_btn);

        TextView title = findViewById(R.id.title);
        title.setText(context.getString(R.string.login));
        interactBtn.setText(context.getString(R.string.login));
        forgotBtn.setVisibility(View.VISIBLE);

        interactBtn.setOnClickListener(v -> {
            if (formUtil.validateFormLogin(email, password1))
                notifyClickLogin(email.getText().toString(), password1.getText().toString());
        });

        forgotBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickPasswordReset();
            }
        });
    }

    private void notifyClickLogin(String email, String password) {
        for (Listener listener : getListeners()) {
            listener.onClickLogin(email, password);
        }
    }

    @Override
    public void setProgressDialogVisibility(boolean visibility) {
        if (visibility)
            myProgressDialog.show();
        else
            myProgressDialog.hide();
    }
}
