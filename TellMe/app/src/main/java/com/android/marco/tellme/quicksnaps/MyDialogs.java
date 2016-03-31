package com.android.marco.tellme.quicksnaps;


import android.app.AlertDialog;
import android.content.Context;

import com.android.marco.tellme.R;

/**
 * Created by gen on 11.04.15.
 */
public class MyDialogs {

    private Context mContext;


    public MyDialogs(Context mContext, int action) {
        this.mContext = mContext;

    }

    public void createAndShowDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Show to manage something");
        builder.setMessage("Here is the message to be show");
//        builder.set  pos neg neutral
        builder.setCancelable(true);

        builder.show();

    }






}
