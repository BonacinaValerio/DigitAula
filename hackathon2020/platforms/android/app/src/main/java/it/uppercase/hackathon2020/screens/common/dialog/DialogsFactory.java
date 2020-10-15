package it.uppercase.hackathon2020.screens.common.dialog;

public interface DialogsFactory {
    PasswordResetDialog newPasswordResetDialog(String title, String message, String positiveButtonCaption, String negativeButtonCaption);
    AddWorkTopicDialog newAddWorkTopicDialog(String mRoomId);
}
