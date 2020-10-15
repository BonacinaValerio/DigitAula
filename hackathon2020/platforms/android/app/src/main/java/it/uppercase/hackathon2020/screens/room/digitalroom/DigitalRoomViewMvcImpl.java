package it.uppercase.hackathon2020.screens.room.digitalroom;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.common.model.SubjectRoom;
import it.uppercase.hackathon2020.screens.common.BaseViewMvc;
import it.uppercase.hackathon2020.screens.common.ViewMvcFactory;
import it.uppercase.hackathon2020.screens.common.progressdialog.MyProgressDialog;
import it.uppercase.hackathon2020.screens.login.FormUtil;
import it.uppercase.hackathon2020.user.UserUtil;

public class DigitalRoomViewMvcImpl extends BaseViewMvc<DigitalRoomViewMvc.Listener> implements DigitalRoomViewMvc {
    private final String TAG = this.getClass().getSimpleName();

    private ImageView liveImg;
    private ImageButton backBtn;
    private LinearLayout groupWorkBtn, messageBtn;
    private EditText urlLive, urlDrive;

    private MyProgressDialog myProgressDialog;

    public DigitalRoomViewMvcImpl(LayoutInflater inflater, ViewGroup container, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.layout_digital_room, container, false));
        Context context = inflater.getContext();
        FormUtil formUtil = new FormUtil(context);
        myProgressDialog = new MyProgressDialog(context, context.getString(R.string.loading));

        backBtn = findViewById(R.id.back_btn);
        liveImg = findViewById(R.id.live_img);
        groupWorkBtn = findViewById(R.id.group_work_btn);
        messageBtn = findViewById(R.id.message_btn);
        urlLive = findViewById(R.id.live_url);
        urlDrive = findViewById(R.id.drive_url);

        urlLive.setInputType(InputType.TYPE_NULL);
        urlLive.setTextIsSelectable(true);
        urlLive.setKeyListener(null);

        urlDrive.setInputType(InputType.TYPE_NULL);
        urlDrive.setTextIsSelectable(true);
        urlDrive.setKeyListener(null);

        backBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickBack();
            }
        });
        groupWorkBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickGroupWork();
            }
        });
        messageBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickMessage();
            }
        });
    }

    @Override
    public void setProgressDialogVisibility(boolean visibility) {
        if (visibility)
            myProgressDialog.show();
        else
            myProgressDialog.hide();
    }

    @Override
    public void setView(String role, SubjectRoom subjectRoom) {
        String live = subjectRoom.getLive();
        if (!urlLive.hasFocus() && !urlDrive.hasFocus())
            setPermission(role, subjectRoom);

        if (live != null && live.length() > 0) {
            liveImg.setVisibility(View.VISIBLE);
            if (!urlLive.hasFocus())
                urlLive.setText(subjectRoom.getLive());
        }
        else if (live != null) {
            if (!urlLive.hasFocus())
                urlLive.setText(subjectRoom.getLive());

            liveImg.setVisibility(View.GONE);
        }
        else {
            liveImg.setVisibility(View.GONE);
        }

        String sharedDocument = subjectRoom.getDrive();
        if (sharedDocument != null) {
            if (!urlDrive.hasFocus())
                urlDrive.setText(sharedDocument);
        }
    }

    private void setPermission(String role, SubjectRoom subjectRoom) {

        if (UserUtil.hasPermission(role)) {
            Log.i(TAG, "setPermission: "+role);
            urlLive.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
            urlLive.setTextIsSelectable(true);
            urlLive.invalidate();
            urlLive.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus)
                    saveNewLiveValue();
            });
            urlLive.setOnKeyListener((v, keyCode, event) -> {
                saveNewLiveValue();
                return true;
            });

            urlDrive.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
            urlDrive.setTextIsSelectable(true);
            urlDrive.invalidate();
            urlDrive.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus)
                    saveNewDriveValue();
            });
            urlDrive.setOnKeyListener((v, keyCode, event) -> {
                saveNewDriveValue();
                return true;
            });
        }
        else {
            urlLive.setOnClickListener(v -> {
                String live = subjectRoom.getLive();
                if (live != null) {
                    for (Listener listener : getListeners()) {
                        listener.onClickOpenWebite(live);
                    }
                }
            });


            urlDrive.setOnClickListener(v -> {
                String drive = subjectRoom.getDrive();
                if (drive != null) {
                    for (Listener listener : getListeners()) {
                        listener.onClickOpenWebite(drive);
                    }
                }
            });
        }
    }

    private void saveNewLiveValue() {
        for (Listener listener : getListeners()) {
            listener.onSaveLive(urlLive.getText().toString());
        }
    }

    private void saveNewDriveValue() {
        for (Listener listener : getListeners()) {
            listener.onSaveDrive(urlDrive.getText().toString());
        }
    }
}
