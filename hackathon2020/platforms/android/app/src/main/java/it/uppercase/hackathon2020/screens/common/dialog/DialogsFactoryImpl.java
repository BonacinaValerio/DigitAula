package it.uppercase.hackathon2020.screens.common.dialog;

public class DialogsFactoryImpl implements DialogsFactory {

    public DialogsFactoryImpl() {
    }

    @Override
    public PasswordResetDialog newPasswordResetDialog(String title, String message, String positiveButtonCaption, String negativeButtonCaption) {
        return PasswordResetDialog.newInstance(title, message, positiveButtonCaption, negativeButtonCaption);
    }

    @Override
    public AddWorkTopicDialog newAddWorkTopicDialog(String mRoomId) {
        return AddWorkTopicDialog.newInstance(mRoomId);
    }
}
