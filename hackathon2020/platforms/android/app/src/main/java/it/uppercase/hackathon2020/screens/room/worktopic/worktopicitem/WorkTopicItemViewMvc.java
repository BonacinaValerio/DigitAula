package it.uppercase.hackathon2020.screens.room.worktopic.worktopicitem;


import it.uppercase.hackathon2020.common.model.WorkTopic;
import it.uppercase.hackathon2020.screens.common.ViewMvc;

public interface WorkTopicItemViewMvc extends ViewMvc {
    interface Listener {
        void onClickJoin();
        void onClickLeave();
    }

    void setView(WorkTopic mWorkTopic);

    void registerListener(Listener listener);
    void unregisterListener(Listener listener);
}
