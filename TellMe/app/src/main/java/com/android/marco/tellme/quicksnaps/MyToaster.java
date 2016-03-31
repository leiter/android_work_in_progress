package com.android.marco.tellme.quicksnaps;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.marco.tellme.R;
import com.android.marco.tellme.activities.MainActivity;

/**
 * Created by gen on 11.04.15.
 */
public class MyToaster {

    private MainActivity mActivity;


    public MyToaster(MainActivity mainActivity) {
        this.mActivity = mainActivity;
    }

    public void showToast() {
        LayoutInflater myInflater = LayoutInflater.from(mActivity);
        View view = myInflater.inflate(R.layout.training, null);
        Toast mytoast = new Toast(mActivity);
//        mytoast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);  // for center horizontal
//        mytoast.setGravity(Gravity.CENTER_VERTICAL);       // for center vertical
//        mytoast.setGravity(Gravity.TOP);
        mytoast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        mytoast.setView(view);
        mytoast.setDuration(Toast.LENGTH_LONG);
        mytoast.show();

    }
}
