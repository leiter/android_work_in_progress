package com.android.marco.tellme.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.marco.tellme.R;
import com.android.marco.tellme.activities.MainActivity;
import com.android.marco.tellme.adapters.LanguageModel;
import com.android.marco.tellme.utils.SharedPrefKeys;

import java.util.Locale;

/**
 * Created by gen on 15.04.15.
 */
public class SharedPrefManager {

    public SharedPreferences mSharedPreferences;
    private MainActivity mActivity;

    public SharedPrefManager(MainActivity mActivity) {
        this.mActivity = mActivity;
        this.mSharedPreferences = mActivity.getPreferences(Context.MODE_PRIVATE);
    }

    public void storeAllValues() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        // if tab 1 or tab2

        editor.putInt(SharedPrefKeys.SelectedTab.TAB_SELECTED_INDEX, mActivity.TAB_SELECTED_INDEX);

        if (mActivity.TAB_SELECTED_INDEX==0) {
            editor.putInt(SharedPrefKeys.ScrollPosition.TAB_1, mActivity.findViewById(R.id.hs_ma_buttons).getScrollX());
//        editor.putInt(SharedPrefKeys.ScrollPosition.TAB_2, myScrollView_TAB2.getScrollX());
            editor.putString(SharedPrefKeys.SessionLanguages.LANG_1, mActivity.sessionLanguageCache.get(0).getImage());
            editor.putString(SharedPrefKeys.SessionLanguages.LANG_2, mActivity.sessionLanguageCache.get(1).getImage());
//        editor.putBoolean("showWordList",wordlistButton.isChecked() );
        }

        editor.commit();

    }

    public void restorValues() {
        mActivity.TAB_SELECTED_INDEX = mSharedPreferences.getInt(SharedPrefKeys.SelectedTab.TAB_SELECTED_INDEX,0);

        if (mActivity.TAB_SELECTED_INDEX==0) {
            mActivity.sessionLanguageCache.add(
                    new LanguageModel(StringToLocale(mSharedPreferences.getString(SharedPrefKeys.SessionLanguages.LANG_1,
                            Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry()))));
            mActivity.findViewById(R.id.hs_ma_buttons).post(new Runnable() {
                public void run() {
                    mActivity.findViewById(R.id.hs_ma_buttons).scrollTo(
                            mSharedPreferences.getInt(SharedPrefKeys.ScrollPosition.TAB_1, 180), 0);}
            });}


    }

    public int getValueForKey(String key, int default_value) {
//        mSharedPreferences.getInt(key,);
        return 0;
    }

    public Locale StringToLocale(String s) {
        String[] p = s.split("_");
        return new Locale(p[0], p[1]);
    }

    public void getAsecondLocale() {
        Locale[] result;
        Locale[] myLocales = Locale.getAvailableLocales();
        String dLocale = Locale.getDefault().getLanguage();
        int i = 0;
        for (Locale l : myLocales) {
            if (!l.equals(myLocales[i])) {
            }
        }
    }
}
