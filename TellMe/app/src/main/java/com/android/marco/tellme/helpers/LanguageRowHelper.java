package com.android.marco.tellme.helpers;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.android.marco.tellme.R;
import com.android.marco.tellme.activities.MainActivity;
import com.android.marco.tellme.adapters.LanguageModel;

/**
 * Created by gen on 26.04.15.
 */
public class LanguageRowHelper {

    public View.OnTouchListener mOnTouchListener;
    private MainActivity mActivity;

    public LanguageRowHelper(MainActivity mActivity) {
        this.mActivity = mActivity;
        mOnTouchListener = new View.OnTouchListener() {  //
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (v.getRight() - ((EditText) v).getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Log.e("dsdfasfkjkf", "asdfkijkgf");
                        event.setAction(MotionEvent.ACTION_CANCEL);
                        return false;
                    }
                    if (event.getRawX() <= ((EditText) v).getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()) {    //(v.getLeft() +
                        Log.e("LEFFFTTT", "LEFFFERRRr");
                        event.setAction(MotionEvent.ACTION_CANCEL);
                        return false;
                    }
                }
                return false;
            }
        };
    }


    public void setLanguageRows() {
        mActivity.input_1 = (android.widget.EditText) mActivity.findViewById(R.id.et_ma_input_1);
        mActivity.input_2 = (android.widget.EditText) mActivity.findViewById(R.id.et_ma_input_2);
        setLanguage(mActivity.input_1, mActivity.sessionLanguageCache.get(0));
        setLanguage(mActivity.input_2, mActivity.sessionLanguageCache.get(1));
        mActivity.input_1.setOnTouchListener(mOnTouchListener);
        mActivity.input_2.setOnTouchListener(mOnTouchListener);

    }

    public void setLanguage(EditText e, LanguageModel l) {
        int resID = mActivity.getResources().getIdentifier(
                l.getImage(), "drawable", mActivity.getPackageName());
        if (resID == 0) {
            resID = R.drawable.ba;
        }
        Drawable d = mActivity.getResources().getDrawable(resID);
        Drawable r = mActivity.getResources().getDrawable(R.drawable.ic_action_volume_on);
        int dim = (int) (d.getIntrinsicHeight() * 1.5);

        r.setBounds(0, 0, dim, dim);
        d.setBounds(0, 0, dim, dim);

//        Drawable drawable = getResources().getDrawable(R.drawable.s_vit);
//        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.5),
//                (int)(drawable.getIntrinsicHeight()*0.5));
//        ScaleDrawable sd = new ScaleDrawable(drawable, 0, scaleWidth, scaleHeight);
//        Button btn = findViewbyId(R.id.yourbtnID);
//        btn.setCompoundDrawables(sd.getDrawable(), null, null, null);
//        e.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ch,0,0,0);
        e.setCompoundDrawables(d, null, r, null);
        e.setHint(l.getLanguage());
    }

}
