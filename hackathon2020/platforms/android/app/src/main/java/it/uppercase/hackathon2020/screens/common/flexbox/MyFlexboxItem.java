package it.uppercase.hackathon2020.screens.common.flexbox;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import it.uppercase.hackathon2020.R;


public class MyFlexboxItem extends LinearLayout {

    public MyFlexboxItem(Context context, String label) {
        super(context);
        init(context, label);
    }

    public MyFlexboxItem(Context context, @Nullable AttributeSet attrs, String label) {
        super(context, attrs);
        init(context, label);
    }

    public MyFlexboxItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr, String label) {
        super(context, attrs, defStyleAttr);
        init(context, label);
    }

    public MyFlexboxItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, String label) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, label);
    }

    private void init(Context context, String label) {
        LayoutInflater.from(context).inflate(R.layout.layout_base_flexbox_item, this, true);
        TextView labelTextView = findViewById(R.id.label);
        labelTextView.setText(label);
    }
}
