package com.android.marco.tellme.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.marco.tellme.R;
import com.android.marco.tellme.activities.MainActivity;

/**
 * Created by gen on 09.04.15.
 */
public class NavigationDrawerHelper {
    public NavigationInteractionListener mInteractionListener;
    private ActionBarDrawerToggle mDrawerToggle;
    private MainActivity mActivity;
    private DrawerLayout mDrawerLayout;
    public NavigationDrawerHelper(MainActivity mAct)
//    , DrawerLayout mDrawer,
//    Toolbar mToolbar, int mStringResourceOpen, int mStringResourceClose
    {
        mActivity = mAct;
        mDrawerLayout = ((DrawerLayout)mAct.findViewById(R.id.dl_ma_navigationdrawer));
        mDrawerToggle = new ActionBarDrawerToggle(mAct, mDrawerLayout ,
                ((Toolbar)mAct.findViewById(R.id.toolbar)),
                R.string.app_name, R.string.app_name)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(drawerView.getWindowToken(), 0);
                mActivity.invalidateOptionsMenu();

            }
        };


    }





    private void deSelectAllRows() {
        Resources res = mActivity.getResources();
//        for (int i = 0; i < selectionRows.length; i++) {
//            LinearLayout ll = (LinearLayout) mDrawerLayout.findViewById(selectionRows[i]);
//            ((TextView) ll.getChildAt(1)).setTextColor(res.getColor(R.color.baseBlack));
//        }
    }

    private void handleLogInState() {
        boolean loginState = false; //isLoggedIn();
        if (loginState) {
//            mDrawerLayout.findViewById(R.id.iv_db_navigation_more).setVisibility(View.VISIBLE);
//            mDrawerLayout.findViewById(R.id.ll_db_managecalendars).setVisibility(View.VISIBLE);
//            mDrawerLayout.findViewById(R.id.ll_db_archive).setVisibility(View.VISIBLE);
        }
        if (!loginState) {
//            mDrawerLayout.findViewById(R.id.iv_db_navigation_more).setVisibility(View.GONE);
//            mDrawerLayout.findViewById(R.id.iv_db_navigation_avatar).setVisibility(View.GONE);
//            ((TextView) mDrawerLayout.findViewById(R.id.tv_db_navigation_name)).setText(R.string.navigation_login_button_title);
//            ((TextView) mDrawerLayout.findViewById(R.id.tv_db_navigation_email))
//                    .setText(R.string.navigation_login_button_detail);
//            mDrawerLayout.findViewById(R.id.ll_db_managecalendars).setVisibility(View.GONE);
//            mDrawerLayout.findViewById(R.id.ll_db_archive).setVisibility(View.GONE);
        }
    }

//    private boolean isLoggedIn() {
//        return SharedPrefHelper.getSharedPref(mActivity)
//                .getBoolean(SharedPrefKeys.LoggedIn.LOGGEDIN, true);
//    }

    private void setTextColor(View v) {   //setRowSelected
        LinearLayout ll = (LinearLayout) v;
        ((TextView) ll.getChildAt(1))
                .setTextColor(mActivity.getResources().getColor(R.color.baseBlue));
    }

    private void showOrHideLogout(boolean show) {
        int mVisibility;
        if (show) {
            mVisibility = View.GONE;
        } else mVisibility = View.VISIBLE;

//        for (int i = 0; i < selectionRows.length; i++) {                    //
////            AlphaAnimationUtil.AnimateAlphaOnNavigationChange(mDrawerLayout.findViewById(selectionRows[i]), 600, !show);
//            mDrawerLayout.findViewById(selectionRows[i]).setVisibility(mVisibility);
//        }
//        AlphaAnimationUtil.AnimateAlphaOnNavigationChange(mDrawerLayout.findViewById(R.id.ll_db_menu_logout), 600, show);
    }

    public void initialize() {
//        if (mActivity instanceof NavigationInteractionListener) {
//            mInteractionListener = (NavigationInteractionListener) mActivity;
//        } else {
//            throw new ClassCastException("Activity must implement NavigationInteractionListener");
//        }


        View.OnClickListener toggleCollectPractice = new View.OnClickListener() {
            private boolean isCollecting = false;
            @Override
            public void onClick(View v) {
                if (isCollecting) {
                    mActivity.showTabForId(1);
                    mActivity.TAB_SELECTED_INDEX = 1;
                    ((TextView) mDrawerLayout.findViewById(R.id.tv_ma_navigation_other_tab)).setText(R.string.nd_tv_title_practice);

                } else if (!isCollecting) {
                    mActivity.showTabForId(0);
                    mActivity.TAB_SELECTED_INDEX = 0;
                    ((TextView) mDrawerLayout.findViewById(R.id.tv_ma_navigation_other_tab)).setText(R.string.nd_tv_title_collect);
                }
                mDrawerLayout.closeDrawers();
                isCollecting = !isCollecting;
            }
        };

        View.OnClickListener gotoDashboard = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteractionListener.onInteraction(NavigationInteractionListener.Interaction.IA_GOTO_DASHBOARD);
                mDrawerLayout.closeDrawers();
                deSelectAllRows();
                setTextColor(v);
            }
        };

        View.OnClickListener gotoArchive = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteractionListener.onInteraction(NavigationInteractionListener.Interaction.IA_GOTO_ARCHIVE);
                mDrawerLayout.closeDrawers();
                deSelectAllRows();
                setTextColor(v);
            }
        };

        View.OnClickListener gotoCalendars = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteractionListener.onInteraction(NavigationInteractionListener.Interaction.IA_GOTO_CALENDARS);
                mDrawerLayout.closeDrawers();
                deSelectAllRows();
                setTextColor(v);
            }
        };

        View.OnClickListener gotoAbout = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteractionListener.onInteraction(NavigationInteractionListener.Interaction.IA_GOTO_SETTINGS);
                mDrawerLayout.closeDrawers();
                deSelectAllRows();
                setTextColor(v);
            }
        };

        View.OnClickListener gotoFeedback = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteractionListener.onInteraction(NavigationInteractionListener.Interaction.IA_GOTO_FEEDBACK);
                mDrawerLayout.closeDrawers();
                deSelectAllRows();
                setTextColor(v);
            }
        };

        mDrawerLayout.findViewById(R.id.tv_ma_navigation_other_tab).setOnClickListener(toggleCollectPractice);
//        mDrawerLayout.findViewById(R.id.ll_db_dashboard).setOnClickListener(gotoDashboard);
//        mDrawerLayout.findViewById(R.id.ll_db_archive).setOnClickListener(gotoArchive);
//        mDrawerLayout.findViewById(R.id.ll_db_managecalendars).setOnClickListener(gotoCalendars);
//        mDrawerLayout.findViewById(R.id.ll_db_feedback).setOnClickListener(gotoFeedback);
//        mDrawerLayout.findViewById(R.id.ll_db_about).setOnClickListener(gotoAbout);
//        mDrawerLayout.findViewById(R.id.ll_db_dashboard).performClick();
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

//        handleLogInState();
    }

    public interface NavigationInteractionListener {
        public void onInteraction(Interaction interaction);
        public enum Interaction {
            IA_GOTO_DASHBOARD,
            IA_GOTO_ARCHIVE,
            IA_GOTO_SETTINGS,
            IA_GOTO_FEEDBACK,
            IA_GOTO_CALENDARS
        }
    }
}
