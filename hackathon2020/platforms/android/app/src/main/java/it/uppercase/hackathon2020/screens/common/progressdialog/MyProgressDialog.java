package it.uppercase.hackathon2020.screens.common.progressdialog;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog {
    private ProgressDialog mProgressDialog;

    public MyProgressDialog(Context context, String message) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
    }

    public void show() {
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void hide() {
        if (mProgressDialog.isShowing())
            mProgressDialog.hide();
    }
}
