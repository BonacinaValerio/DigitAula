package it.uppercase.hackathon2020.screens.common;

import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import it.uppercase.hackathon2020.common.BaseObservable;

public abstract class BaseViewMvc<LISTENER> extends BaseObservable<LISTENER> implements ViewMvc {

    private View mRootView;

    protected void setRootView(View rootView) {
        mRootView = rootView;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return getRootView().findViewById(id);
    }

    protected int getColor(@ColorRes int colorId) {
        return mRootView.getContext().getColor(colorId);
    }

    public static String fromUnixTimestampToHumanReadableFormat(String timestamp) {
        SimpleDateFormat fmtOut = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return fmtOut.format(new Date(Long.valueOf(timestamp)));
    }
}
