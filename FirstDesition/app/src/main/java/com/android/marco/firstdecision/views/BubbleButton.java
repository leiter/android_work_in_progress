package com.android.marco.firstdecision.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.marco.firstdecision.R;

/**
 * Created by gen on 08.11.15.
 *
 */
public class BubbleButton extends Button {

    public BubbleButton(Context context) {
        super(context);
    }

    public BubbleButton(Context context, String title, Typeface typeface, RelativeLayout.LayoutParams lp) {
        super(context);
        this.setTextAppearance(context, R.style.AppBubbles);
        this.setClickable(false);
        this.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bubble, context.getTheme()));
        this.setTypeface(typeface);
        this.setLayoutParams(lp);
        this.setText(title);
        this.setPadding(48, 48, 48, 48);
    }

    public BubbleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public BubbleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
}
