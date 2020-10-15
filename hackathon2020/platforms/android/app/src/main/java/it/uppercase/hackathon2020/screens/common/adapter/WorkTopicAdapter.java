package it.uppercase.hackathon2020.screens.common.adapter;

import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.uppercase.hackathon2020.common.model.WorkTopic;
import it.uppercase.hackathon2020.screens.common.ViewMvcFactory;
import it.uppercase.hackathon2020.screens.room.worktopic.worktopicitem.WorkTopicItemViewMvc;

public class WorkTopicAdapter extends RecyclerView.Adapter<WorkTopicAdapter.ViewHolder> {

    public static int LAYOUT_TYPE_WITH_BUTTON = 0;
    public static int LAYOUT_TYPE_WITHOUT_BUTTON = 1;

    private OnClickTopicListener mOnClickTopicListener;

    public interface OnClickTopicListener {
        void onClickJoin(int position);
        void onClickLeave(int position);
    }

    private List<WorkTopic> mWorkTopic;
    private String mUid;
    private ViewMvcFactory mViewMvcFactory;

    public WorkTopicAdapter(List<WorkTopic> mWorkTopic, OnClickTopicListener mOnClickTopicListener, ViewMvcFactory mViewMvcFactory, String mUid) {
        this.mUid = mUid;
        this.mOnClickTopicListener = mOnClickTopicListener;
        this.mWorkTopic = mWorkTopic;
        this.mViewMvcFactory = mViewMvcFactory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WorkTopicItemViewMvc mViewMVC = mViewMvcFactory.newWorkTopicItemViewMvc(parent, mUid);
        return new ViewHolder(mViewMVC, mOnClickTopicListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setView(mWorkTopic.get(position));
    }

    @Override
    public int getItemCount() {
        return mWorkTopic.size();
    }

    public void setValue(List<WorkTopic> mWorkTopic) {
        this.mWorkTopic.clear();
        this.mWorkTopic = mWorkTopic;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements
            WorkTopicItemViewMvc.Listener {

        private OnClickTopicListener mOnClickTopicListener;
        private WorkTopicItemViewMvc mViewMVC;

        public ViewHolder(WorkTopicItemViewMvc mViewMVC, OnClickTopicListener mOnClickTopicListener) {
            super(mViewMVC.getRootView());
            this.mViewMVC = mViewMVC;
            this.mOnClickTopicListener = mOnClickTopicListener;

            mViewMVC.registerListener(this);
        }

        @Override
        public void onClickJoin() {
            mOnClickTopicListener.onClickJoin(getAdapterPosition());
        }

        @Override
        public void onClickLeave() {
            mOnClickTopicListener.onClickLeave(getAdapterPosition());
        }

        public void setView(WorkTopic workTopic) {
            mViewMVC.setView(workTopic);
        }
    }

}
