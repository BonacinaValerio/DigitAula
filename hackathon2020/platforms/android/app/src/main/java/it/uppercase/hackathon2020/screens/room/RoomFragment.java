package it.uppercase.hackathon2020.screens.room;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.common.model.SubjectRoom;
import it.uppercase.hackathon2020.common.model.WorkTopic;
import it.uppercase.hackathon2020.room.RoomUseCase;
import it.uppercase.hackathon2020.screens.ScreensNavigator;
import it.uppercase.hackathon2020.screens.common.BaseFragmentMain;
import it.uppercase.hackathon2020.screens.common.ViewMvcFactory;
import it.uppercase.hackathon2020.screens.common.dialog.DialogsFactory;
import it.uppercase.hackathon2020.screens.main.UtilCallback;

public class RoomFragment extends BaseFragmentMain implements
        RoomViewMvc.Listener,
        RoomUseCase.Listener {
    private final String TAG = this.getClass().getSimpleName();
    private static final String ARG_ROOM_ID = "ARG_ROOM_ID";
    private static final String ARG_SUBJECT_ROOM = "ARG_SUBJECT_ROOM";

    private RoomViewMvc mViewMVC;
    private ViewMvcFactory mViewMvcFactory;
    private DialogsFactory mDialogsFactory;
    private ScreensNavigator mScreensNavigator;
    private RoomUseCase mRoomUseCase;

    private UtilCallback utilCallback;

    private String mRoomId;
    private SubjectRoom mSubjectRoom;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        utilCallback = (UtilCallback) context;
    }

    public static Fragment newInstance(String roomId) {
        RoomFragment roomFragment = new RoomFragment();
        Bundle args = new Bundle(1);
        args.putString(ARG_ROOM_ID, roomId);
        roomFragment.setArguments(args);
        return roomFragment;
    }

    public static Fragment newInstance(String roomId, SubjectRoom subjectRoom) {
        RoomFragment roomFragment = new RoomFragment();
        Bundle args = new Bundle(2);
        args.putString(ARG_ROOM_ID, roomId);
        args.putSerializable(ARG_SUBJECT_ROOM, subjectRoom);
        roomFragment.setArguments(args);
        return roomFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvcFactory = getCompositionRoot().getViewMvcFactory();
        mRoomUseCase = getCompositionRoot().getRoomUseCase();

        mRoomUseCase.registerListener(this);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mRoomId = arguments.getString(ARG_ROOM_ID);
            mSubjectRoom = (SubjectRoom) arguments.getSerializable(ARG_SUBJECT_ROOM);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewMVC = mViewMvcFactory.newRoomViewMvc(container);
        return mViewMVC.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);

        mRoomUseCase.getRoomDetail(mRoomId);

        if (mSubjectRoom == null)
            mViewMVC.setProgressDialogVisibility(true);
        else
            mViewMVC.setView(mSubjectRoom);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);
    }

    @Override
    public void onShowSnackbar(int message) {
        mViewMVC.setProgressDialogVisibility(false);
        utilCallback.showSnackbar(getString(R.string.error_network_short));
    }

    @Override
    public void onDBOperationSuccess(SubjectRoom mSubjectRoom, List<WorkTopic> workTopics) {
        this.mSubjectRoom = mSubjectRoom;
        mViewMVC.setProgressDialogVisibility(false);
        mViewMVC.setView(mSubjectRoom);
        Log.i(TAG, "onDBOperationSuccess: "+mSubjectRoom.toString());
    }

    @Override
    public void onClickCalendar() {

        utilCallback.showSnackbar("Work in progress");
    }

    @Override
    public void onClickRoom() {
        mScreensNavigator.toDigitalRoom(mSubjectRoom);
    }

    @Override
    public void onClickDispense() {

        utilCallback.showSnackbar("Work in progress");
    }

    @Override
    public void onClickHandle() {

        utilCallback.showSnackbar("Work in progress");
    }

    @Override
    public void onClickBack() {
        utilCallback.close();
    }
}
