package it.uppercase.hackathon2020.screens.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.common.model.SubjectRoom;
import it.uppercase.hackathon2020.screens.common.BaseViewMvc;
import it.uppercase.hackathon2020.screens.common.ViewMvcFactory;
import it.uppercase.hackathon2020.screens.common.progressdialog.MyProgressDialog;
import it.uppercase.hackathon2020.screens.login.FormUtil;

public class RoomViewMvcImpl extends BaseViewMvc<RoomViewMvc.Listener> implements RoomViewMvc {
    private final String TAG = this.getClass().getSimpleName();

    private TextView title;
    private ImageView liveImg;
    private ImageButton backBtn;
    private RelativeLayout roomBtn;
    private LinearLayout dispenseBtn, handleHwBtn, calendarBtn;

    private MyProgressDialog myProgressDialog;

    public RoomViewMvcImpl(LayoutInflater inflater, ViewGroup container, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.layout_room, container, false));
        Context context = inflater.getContext();
        FormUtil formUtil = new FormUtil(context);
        myProgressDialog = new MyProgressDialog(context, context.getString(R.string.loading));

        backBtn = findViewById(R.id.back_btn);
        roomBtn = findViewById(R.id.room_btn);
        liveImg = findViewById(R.id.live_img);
        dispenseBtn = findViewById(R.id.dispense_btn);
        handleHwBtn = findViewById(R.id.handle_hw_btn);
        calendarBtn = findViewById(R.id.calendar_btn);
        title = findViewById(R.id.title);

        backBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickBack();
            }
        });
        roomBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickRoom();
            }
        });
        dispenseBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickDispense();
            }
        });
        handleHwBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickHandle();
            }
        });
        calendarBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickCalendar();
            }
        });
    }

    @Override
    public void setProgressDialogVisibility(boolean visibility) {
        if (myProgressDialog != null) {
            if (visibility)
                myProgressDialog.show();
            else
                myProgressDialog.hide();
        }
    }

    @Override
    public void setView(SubjectRoom subjectRoom) {
        title.setText(subjectRoom.getName());
        String live = subjectRoom.getLive();
        if (live != null && live.length() > 0)
            liveImg.setVisibility(View.VISIBLE);
        else
            liveImg.setVisibility(View.GONE);
    }
}
