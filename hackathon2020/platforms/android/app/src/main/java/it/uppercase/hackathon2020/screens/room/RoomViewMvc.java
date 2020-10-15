package it.uppercase.hackathon2020.screens.room;

import it.uppercase.hackathon2020.common.model.SubjectRoom;
import it.uppercase.hackathon2020.screens.common.ViewMvc;

public interface RoomViewMvc extends ViewMvc {
    interface Listener {
        void onClickCalendar();
        void onClickRoom();
        void onClickDispense();
        void onClickHandle();
        void onClickBack();
    }

    void setProgressDialogVisibility(boolean visibility);

    void setView(SubjectRoom subjectRoom);

    void registerListener(Listener listener);
    void unregisterListener(Listener listener);
}
