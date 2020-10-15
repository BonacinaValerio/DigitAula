package it.uppercase.hackathon2020.screens.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import it.uppercase.hackathon2020.screens.login.LoginViewMvc;
import it.uppercase.hackathon2020.screens.login.LoginViewMvcImpl;
import it.uppercase.hackathon2020.screens.room.RoomViewMvc;
import it.uppercase.hackathon2020.screens.room.RoomViewMvcImpl;
import it.uppercase.hackathon2020.screens.room.digitalroom.DigitalRoomViewMvc;
import it.uppercase.hackathon2020.screens.room.digitalroom.DigitalRoomViewMvcImpl;
import it.uppercase.hackathon2020.screens.room.worktopic.WorkTopicViewMvc;
import it.uppercase.hackathon2020.screens.room.worktopic.WorkTopicViewMvcImpl;
import it.uppercase.hackathon2020.screens.room.worktopic.worktopicitem.WorkTopicItemViewMvc;
import it.uppercase.hackathon2020.screens.room.worktopic.worktopicitem.WorkTopicItemViewMvcImpl;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    public LoginViewMvc newLoginViewMvc(@Nullable ViewGroup parent) {
        return new LoginViewMvcImpl(mLayoutInflater, parent, this);
    }

    public RoomViewMvc newRoomViewMvc(@Nullable ViewGroup parent) {
        return new RoomViewMvcImpl(mLayoutInflater, parent, this);
    }

    public DigitalRoomViewMvc newDigitalRoomViewMvc(@Nullable ViewGroup parent) {
        return new DigitalRoomViewMvcImpl(mLayoutInflater, parent, this);
    }

    public WorkTopicViewMvc newWorkTopicViewMvc(@Nullable ViewGroup parent) {
        return new WorkTopicViewMvcImpl(mLayoutInflater, parent, this);
    }

    public WorkTopicItemViewMvc newWorkTopicItemViewMvc(@Nullable ViewGroup parent, String mUid) {
        return new WorkTopicItemViewMvcImpl(mLayoutInflater, parent, mUid);
    }
}
