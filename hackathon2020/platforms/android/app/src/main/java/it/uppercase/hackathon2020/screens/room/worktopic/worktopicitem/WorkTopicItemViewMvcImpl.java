package it.uppercase.hackathon2020.screens.room.worktopic.worktopicitem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.common.model.WorkTopic;
import it.uppercase.hackathon2020.screens.common.BaseViewMvc;
import it.uppercase.hackathon2020.screens.common.flexbox.MyFlexbox;

public class WorkTopicItemViewMvcImpl extends BaseViewMvc<WorkTopicItemViewMvc.Listener> implements WorkTopicItemViewMvc {
    private final String TAG = this.getClass().getSimpleName();

    private Context context;
    private Button joinBtn, leaveBtn;
    private TextView title, description;
    private MyFlexbox flexbox;
    private String mUid;

    public WorkTopicItemViewMvcImpl(LayoutInflater inflater, ViewGroup container, String mUid) {
        setRootView(inflater.inflate(R.layout.layout_work_topic_list_item, container, false));
        context = inflater.getContext();
        this.mUid = mUid;

        joinBtn = findViewById(R.id.join_btn);
        leaveBtn = findViewById(R.id.leave_btn);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        flexbox = findViewById(R.id.flexbox);

        joinBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickJoin();
            }
        });
        leaveBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickLeave();
            }
        });

    }

    private void setupViewBtn(boolean joined) {
        if (!joined) {
            joinBtn.setVisibility(View.VISIBLE);
            leaveBtn.setVisibility(View.GONE);
        }
        else {
            joinBtn.setVisibility(View.GONE);
            leaveBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setView(WorkTopic mWorkTopic) {
        HashMap<String, String> member = mWorkTopic.getMember();
        List<String> memberList = new ArrayList<>();
        boolean isJoined = false;
        if (member != null) {
            for (Map.Entry<String, String> set : member.entrySet()) {
                memberList.add(set.getValue());
                if (set.getKey().equals(this.mUid)) {
                    isJoined = true;
                }
            }
        }

        title.setText(mWorkTopic.getTitle());
        description.setText(mWorkTopic.getDescription());
        flexbox.addItemList(memberList);
        setupViewBtn(isJoined);
    }
}
