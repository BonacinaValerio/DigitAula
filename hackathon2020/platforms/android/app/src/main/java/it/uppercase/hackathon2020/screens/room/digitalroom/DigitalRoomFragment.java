package it.uppercase.hackathon2020.screens.room.digitalroom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
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
import it.uppercase.hackathon2020.user.FetchUserInfoUseCase;

public class DigitalRoomFragment extends BaseFragmentMain implements
        DigitalRoomViewMvc.Listener,
        RoomUseCase.Listener,
        FetchUserInfoUseCase.Listener {
    private final String TAG = this.getClass().getSimpleName();
    private static final String ARG_SUBJECT_ROOM = "ARG_SUBJECT_ROOM";

    private DigitalRoomViewMvc mViewMVC;
    private ViewMvcFactory mViewMvcFactory;
    private DialogsFactory mDialogsFactory;
    private ScreensNavigator mScreensNavigator;
    private RoomUseCase mRoomUseCase;
    private FetchUserInfoUseCase mFetchUserInfoUseCase;

    private UtilCallback utilCallback;

    private SubjectRoom mSubjectRoom;
    private String mRole;

    public SubjectRoom getmSubjectRoom() {
        return mSubjectRoom;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        utilCallback = (UtilCallback) context;
    }

    public static Fragment newInstance(SubjectRoom subjectRoom) {
        DigitalRoomFragment digitalRoomFragment = new DigitalRoomFragment();
        Bundle args = new Bundle(1);
        args.putSerializable(ARG_SUBJECT_ROOM, (Serializable) subjectRoom);
        digitalRoomFragment.setArguments(args);
        return digitalRoomFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvcFactory = getCompositionRoot().getViewMvcFactory();
        mRoomUseCase = getCompositionRoot().getRoomUseCase();
        mFetchUserInfoUseCase = getCompositionRoot().getFetchUserInfoUseCase();

        mRoomUseCase.registerListener(this);
        mFetchUserInfoUseCase.registerListener(this);

        Bundle arguments = getArguments();
        if (arguments != null) {
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
        mViewMVC = mViewMvcFactory.newDigitalRoomViewMvc(container);
        return mViewMVC.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);

        mFetchUserInfoUseCase.getPermission();
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
        if (mSubjectRoom != null && mRole != null) {
            mViewMVC.setProgressDialogVisibility(false);
            mViewMVC.setView(mRole, mSubjectRoom);
        }
        Log.i(TAG, "onDBOperationSuccess: "+mSubjectRoom.toString());
    }

    @Override
    public void onUserReloaded() {
        // GOTO
    }

    @Override
    public void onUserReloadFailed(Exception e) {
        // GOTO
    }

    @Override
    public void onInfoFetched(String mRole) {
        this.mRole = mRole;

        if (mSubjectRoom != null && mRole != null) {
            mViewMVC.setProgressDialogVisibility(false);
            mViewMVC.setView(mRole, mSubjectRoom);
        }

        if (mSubjectRoom != null) {
            mRoomUseCase.getRoomDetail(mSubjectRoom.getId());
        }

    }

    @Override
    public void onClickOpenWebite(String website) {
        try {
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
            startActivity(websiteIntent);
        } catch (Exception e) {
            Log.e(TAG, "onClickOpenWebite: ", e);
        }
    }

    @Override
    public void onSaveLive(String live) {
        mRoomUseCase.saveNewLive(mSubjectRoom.getId(), live);
    }

    @Override
    public void onSaveDrive(String drive) {
        mRoomUseCase.saveNewDrive(mSubjectRoom.getId(), drive);
    }

    @Override
    public void onClickGroupWork() {
        mScreensNavigator.toWorkTopic(mSubjectRoom);
    }

    @Override
    public void onClickMessage() {
        utilCallback.showSnackbar("Work in progress");
    }

    @Override
    public void onClickBack() {
        mScreensNavigator.backToRoom(mSubjectRoom);
    }
}
