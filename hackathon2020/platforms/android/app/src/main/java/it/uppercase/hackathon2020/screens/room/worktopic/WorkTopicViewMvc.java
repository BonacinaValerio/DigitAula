package it.uppercase.hackathon2020.screens.room.worktopic;

import java.util.List;

import it.uppercase.hackathon2020.common.model.WorkTopic;
import it.uppercase.hackathon2020.screens.common.ViewMvc;

public interface WorkTopicViewMvc extends ViewMvc {
    interface Listener {
        void onClickAdd();
        void onClickBack();
        void onClickJoin(int position);
        void onClickLeave(int position);
    }

    void setProgressDialogVisibility(boolean visibility);

    void setView(List<WorkTopic> mWorkTopics, String mUid);

    void registerListener(Listener listener);
    void unregisterListener(Listener listener);
}
