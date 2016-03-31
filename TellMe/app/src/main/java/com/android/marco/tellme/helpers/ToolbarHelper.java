package com.android.marco.tellme.helpers;

import android.support.v7.widget.Toolbar;

import com.android.marco.tellme.activities.MainActivity;

/**
 * Created by gen on 09.04.15.
 */
public class ToolbarHelper {

    private MainActivity mActivity;
    private Toolbar mToolbar;

    public ToolbarHelper(MainActivity mainActivity) {
        this.mActivity = mainActivity;
    }

    public void initialize() {
//        mToolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
//        mActivity.setSupportActionBar(mToolbar);
//        mActivity.getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void showOrHideToolbar() {
//        if (mToolbar.isShown()) {
//            mToolbar.setVisibility(View.GONE);
//        } else mToolbar.setVisibility(View.VISIBLE);
    }
}
