package it.uppercase.hackathon2020.screens.room.digitalroom;

import it.uppercase.hackathon2020.common.model.SubjectRoom;
import it.uppercase.hackathon2020.screens.common.ViewMvc;

public interface DigitalRoomViewMvc extends ViewMvc {
    interface Listener {
        void onClickGroupWork();
        void onClickMessage();
        void onClickBack();
        void onClickOpenWebite(String website);
        void onSaveLive(String live);
        void onSaveDrive(String drive);
    }

    void setProgressDialogVisibility(boolean visibility);

    void setView(String role, SubjectRoom subjectRoom);

    void registerListener(Listener listener);
    void unregisterListener(Listener listener);
}
