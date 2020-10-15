package it.uppercase.hackathon2020.screens.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.common.model.SubjectRoom;
import it.uppercase.hackathon2020.common.model.WorkTopic;
import it.uppercase.hackathon2020.room.RoomUseCase;
import it.uppercase.hackathon2020.screens.common.progressdialog.MyProgressDialog;
import it.uppercase.hackathon2020.screens.login.FormUtil;
import it.uppercase.hackathon2020.screens.main.UtilCallback;
import it.uppercase.hackathon2020.user.AuthenticationUseCase;

public class AddWorkTopicDialog extends BaseDialogMain implements
        RoomUseCase.Listener {

    public static final String ARG_ROOM_ID = "ARG_ROOM_ID";

    private FormUtil formUtil;
    private MyProgressDialog myProgressDialog;
    private AuthenticationUseCase mAuthenticationUseCase;
    private RoomUseCase mRoomUseCase;

    private EditText title, description;
    private Button okBtn, cancelBtn;

    private UtilCallback utilCallback;
    private String mRoomId;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        utilCallback = (UtilCallback) context;
    }

    public static AddWorkTopicDialog newInstance(String roomId) {
        AddWorkTopicDialog addWorkTopicDialog = new AddWorkTopicDialog();
        Bundle args = new Bundle(1);
        args.putString(ARG_ROOM_ID, roomId);
        addWorkTopicDialog.setArguments(args);
        return addWorkTopicDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.Dialog);
        myProgressDialog = new MyProgressDialog(getContext(), getString(R.string.loading));
        formUtil = new FormUtil(getContext());
        mRoomUseCase = getCompositionRoot().getRoomUseCase();
        mRoomUseCase.registerListener(this);

        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_add_work_topic);

        initSubViews(dialog.getWindow().getDecorView());

        mRoomId = getArguments().getString(ARG_ROOM_ID);
        return dialog;
    }


    private void initSubViews(View rootView) {
        title = rootView.findViewById(R.id.title);
        description = rootView.findViewById(R.id.description);

        okBtn = rootView.findViewById(R.id.ok_btn);
        cancelBtn = rootView.findViewById(R.id.cancel_btn);

        cancelBtn.setOnClickListener(v -> dismiss());
        okBtn.setOnClickListener(v -> {
            if (formUtil.validateFormWorkTopic(title, description)) {
                hideKeyboard(getContext(), title.getRootView());
                hideKeyboard(getContext(), description.getRootView());
                myProgressDialog.show();
                mRoomUseCase.addWorkTopic(title.getText().toString(), description.getText().toString(), mRoomId);
            }
        });
    }


    @Override
    public void onShowSnackbar(int message) {
        myProgressDialog.hide();
        utilCallback.showSnackbar(getString(message));
    }


    @Override
    public void onDBOperationSuccess(SubjectRoom subjectRoom, List<WorkTopic> workTopics) {
        myProgressDialog.hide();
        dismiss();
        utilCallback.showSnackbar("Gruppo di lavoro creato");
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
