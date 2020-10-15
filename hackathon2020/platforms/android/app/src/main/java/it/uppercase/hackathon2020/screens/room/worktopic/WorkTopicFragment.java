package it.uppercase.hackathon2020.screens.room.worktopic;

import android.content.Context;
import android.os.Bundle;
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
import it.uppercase.hackathon2020.user.FetchUserInfoUseCase;

public class WorkTopicFragment extends BaseFragmentMain implements
        WorkTopicViewMvc.Listener,
        RoomUseCase.Listener {
    private final String TAG = this.getClass().getSimpleName();
    private static final String ARG_SUBJECT_ROOM = "ARG_SUBJECT_ROOM";

    private WorkTopicViewMvc mViewMVC;
    private ViewMvcFactory mViewMvcFactory;
    private DialogsFactory mDialogsFactory;
    private ScreensNavigator mScreensNavigator;
    private RoomUseCase mRoomUseCase;
    private FetchUserInfoUseCase mFetchUserInfoUseCase;

    private UtilCallback utilCallback;

    private SubjectRoom mSubjectRoom;
    private List<WorkTopic> mWorkTopics;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        utilCallback = (UtilCallback) context;
    }

    public static Fragment newInstance(SubjectRoom subjectRoom) {
        WorkTopicFragment workTopicFragment = new WorkTopicFragment();
        Bundle args = new Bundle(1);
        args.putSerializable(ARG_SUBJECT_ROOM, subjectRoom);
        workTopicFragment.setArguments(args);
        return workTopicFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvcFactory = getCompositionRoot().getViewMvcFactory();
        mRoomUseCase = getCompositionRoot().getRoomUseCase();
        mFetchUserInfoUseCase = getCompositionRoot().getFetchUserInfoUseCase();
        mDialogsFactory = getCompositionRoot().getDialogsFactory();

        mRoomUseCase.registerListener(this);

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
        mViewMVC = mViewMvcFactory.newWorkTopicViewMvc(container);
        return mViewMVC.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);

        mRoomUseCase.getWorksTopic(mSubjectRoom.getId());
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
        mViewMVC.setProgressDialogVisibility(false);
        if (workTopics != null) {
            this.mWorkTopics = workTopics;
            mViewMVC.setView(workTopics, mFetchUserInfoUseCase.getUid());
        }
    }

    @Override
    public void onClickJoin(int position) {
        mViewMVC.setProgressDialogVisibility(true);
        mRoomUseCase.joinWorkTopic(mWorkTopics.get(position).getId());
    }

    @Override
    public void onClickLeave(int position) {
        mViewMVC.setProgressDialogVisibility(true);
        mRoomUseCase.leaveWorkTopic(mWorkTopics.get(position).getId());
    }

    @Override
    public void onClickBack() {
        mScreensNavigator.backToDigitalRoom(mSubjectRoom);
    }

    @Override
    public void onClickAdd() {
        mScreensNavigator.showDialogFragment(
                mDialogsFactory.newAddWorkTopicDialog(
                        mSubjectRoom.getId()
                ));
    }

    public SubjectRoom getmSubjectRoom() {
        return mSubjectRoom;
    }
}
