package it.uppercase.hackathon2020.screens.login;

import it.uppercase.hackathon2020.screens.common.ViewMvc;

public interface LoginViewMvc extends ViewMvc {
    interface Listener {
        void onClickPasswordReset();
        void onClickLogin(String email, String password);
    }

    void setProgressDialogVisibility(boolean visibility);

    void registerListener(Listener listener);
    void unregisterListener(Listener listener);
}
