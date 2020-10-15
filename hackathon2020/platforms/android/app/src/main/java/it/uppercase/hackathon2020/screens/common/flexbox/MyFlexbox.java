package it.uppercase.hackathon2020.screens.common.flexbox;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class MyFlexbox extends FlexboxLayout {

    public MyFlexbox(Context context) {
        super(context);
    }

    public MyFlexbox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlexbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addItemList(List<String> types) {
        removeAllViews();
        for (String type : types) {
            MyFlexboxItem flexboxItem = new MyFlexboxItem(getContext(), type);
            addView(flexboxItem);
        }
    }
}
