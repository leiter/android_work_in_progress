package com.android.marco.firstdecision.helpers;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.EditText;

import com.android.marco.firstdecision.R;
import com.android.marco.firstdecision.activities.MainActivity;

/**
 * Created by gen on 01.07.15.
 */

public class ListTextViewHelper {

    private MainActivity mActivity;

    public ListTextViewHelper(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void prepareTextView(EditText e, boolean checked, boolean trashVisibility) {
        Drawable r;
        Drawable d = ResourcesCompat.getDrawable(mActivity.getResources(),
                android.R.drawable.ic_menu_delete, mActivity.getTheme());
        Typeface t = Typeface.createFromAsset(e.getContext().getResources().getAssets(),
                "fonts/Tangerine_Bold.ttf");

        if (checked) {
            r = ResourcesCompat.getDrawable(mActivity.getResources(),
                    R.drawable.btn_check_buttonless_on, mActivity.getTheme());
        } else r = ResourcesCompat.getDrawable(mActivity.getResources(),
                R.drawable.btn_check_buttonless_off, mActivity.getTheme());

        int dimX = (int) (d.getIntrinsicWidth() * 1.);
        int dimY = (int) (d.getIntrinsicHeight() * 1.);
        r.setBounds(0, 0, dimX, dimY + 30);
        d.setBounds(0, 0, dimX, dimY);
        e.setCompoundDrawables(d, null, r, null);
        e.setTypeface(t);

        Rect mRec = new Rect(0, 0, 0, 0);
        if (!trashVisibility) {
            e.getCompoundDrawables()[0].setBounds(mRec);
        }

    }


}
