package it.uppercase.hackathon2020.screens.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import it.uppercase.hackathon2020.R;

public class FormUtil {
    private Context context;

    public FormUtil(Context context) {
        this.context = context;
    }

    public boolean validateFormRegister(EditText email, EditText password1, EditText password2) {
        // controllo preliminare
        boolean fieldCheck = validateField(email) & validateField(password1) & validateField(password2);
        if (fieldCheck) {
            boolean check = true;
            String email_txt = email.getText().toString();
            // se il controllo preliminare è passato controlla che la mail sia benformata
            if (!Patterns.EMAIL_ADDRESS.matcher(email_txt).matches()) {
                email.setError(context.getString(R.string.email_not_valid));
                check = false;
            }
            // se il controllo preliminare è passato controlla che le password 1 e 2 coincidano
            if (!password1.getText().toString().equals(password2.getText().toString())) {
                password2.setError(context.getString(R.string.not_matching_passwords));
                check = false;
            }
            // se il controllo preliminare è passato controlla che le password 1 abbia almeno 6 caratteri
            if (password1.getText().toString().length()<6) {
                password1.setError(context.getString(R.string.at_least_6_chars));
                check = false;
            }
            return check;
        }
        return false;
    }

    public boolean validateFormLogin(EditText email, EditText password) {
        // controllo preliminare
        boolean fieldCheck = validateField(email) & validateField(password);
        if (fieldCheck) {
            boolean check = true;
            String email_txt = email.getText().toString();
            // se il controllo preliminare è passato controlla che la mail sia benformata
            if (!Patterns.EMAIL_ADDRESS.matcher(email_txt).matches()) {
                email.setError(context.getString(R.string.email_not_valid));
                check = false;
            }
            return check;
        }
        return false;
    }

    public boolean validateFormWorkTopic(EditText title, EditText description) {
        // controllo preliminare
        return validateField(title) & validateField(description);
    }

    public boolean validateFormPasswordReset(EditText email) {
        // controllo preliminare
        boolean fieldCheck = validateField(email);
        if (fieldCheck) {
            boolean check = true;
            String email_txt = email.getText().toString();
            // se il controllo preliminare è passato controlla che la mail sia benformata
            if (!Patterns.EMAIL_ADDRESS.matcher(email_txt).matches()) {
                email.setError(context.getString(R.string.email_not_valid));
                check = false;
            }
            return check;
        }
        return false;
    }

    // controllo preliminare che il campo non sia vuoto
    private boolean validateField(EditText field) {
        String txt = field.getText().toString();
        if (TextUtils.isEmpty(txt)) {
            field.setError(context.getString(R.string.required_field));
            return false;
        } else {
            field.setError(null);
            return true;
        }
    }
}
