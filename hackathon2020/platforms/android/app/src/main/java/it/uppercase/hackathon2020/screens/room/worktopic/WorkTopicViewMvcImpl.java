package it.uppercase.hackathon2020.screens.room.worktopic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.uppercase.hackathon2020.R;
import it.uppercase.hackathon2020.common.model.WorkTopic;
import it.uppercase.hackathon2020.screens.common.BaseViewMvc;
import it.uppercase.hackathon2020.screens.common.ViewMvcFactory;
import it.uppercase.hackathon2020.screens.common.adapter.WorkTopicAdapter;
import it.uppercase.hackathon2020.screens.common.progressdialog.MyProgressDialog;

public class WorkTopicViewMvcImpl extends BaseViewMvc<WorkTopicViewMvc.Listener> implements
        WorkTopicViewMvc,
        WorkTopicAdapter.OnClickTopicListener {
    private final String TAG = this.getClass().getSimpleName();

    private ImageButton backBtn, addBtn;
    private RecyclerView recyclerView;
    private WorkTopicAdapter adapter;
    private ViewMvcFactory viewMvcFactory;

    private MyProgressDialog myProgressDialog;

    public WorkTopicViewMvcImpl(LayoutInflater inflater, ViewGroup container, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.layout_work_topic, container, false));
        this.viewMvcFactory = viewMvcFactory;

        Context context = inflater.getContext();
        myProgressDialog = new MyProgressDialog(context, context.getString(R.string.loading));

        backBtn = findViewById(R.id.back_btn);
        addBtn = findViewById(R.id.add_btn);
        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        backBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickBack();
            }
        });
        addBtn.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onClickAdd();
            }
        });
    }

    @Override
    public void setProgressDialogVisibility(boolean visibility) {
        if (visibility)
            myProgressDialog.show();
        else
            myProgressDialog.hide();
    }

    @Override
    public void setView(List<WorkTopic> mWorkTopics, String mUid) {
        Log.i(TAG, "setView: "+mWorkTopics.toString());
        if (adapter == null) {
            adapter = new WorkTopicAdapter(mWorkTopics, this, viewMvcFactory, mUid);
            recyclerView.setAdapter(adapter);
        }
        else {
            adapter.setValue(mWorkTopics);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickJoin(int position) {
        for (Listener listener : getListeners()) {
            listener.onClickJoin(position);
        }
    }

    @Override
    public void onClickLeave(int position) {
        for (Listener listener : getListeners()) {
            listener.onClickLeave(position);
        }
    }
}
