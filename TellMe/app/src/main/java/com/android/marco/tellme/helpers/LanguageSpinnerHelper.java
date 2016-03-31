package com.android.marco.tellme.helpers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.android.marco.tellme.R;
import com.android.marco.tellme.activities.MainActivity;
import com.android.marco.tellme.adapters.LanguageModel;
import com.android.marco.tellme.adapters.LanguageSpinnerAdapter;
import com.android.marco.tellme.data.InfoAboutLocales;

import java.util.ArrayList;

/**
 * Created by gen on 14.04.15.
 */
public class LanguageSpinnerHelper {

    private Spinner LanguageSpinner;
    private MainActivity mActivity;

    public LanguageSpinnerHelper(MainActivity activity) {
        mActivity = activity;
        LanguageSpinner = (Spinner) activity.findViewById(R.id.sp_ma_lang_selector);
    }

    public void initialize() {
        ArrayList<LanguageModel> data = new InfoAboutLocales().getAllCountries();
        LanguageSpinner.setAdapter(new LanguageSpinnerAdapter(mActivity, R.layout.spinner_rows,
                data, mActivity.getResources()) {
        });
        LanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //TODO dominikanische Lösung??
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                LanguageSpinner.setVisibility(View.GONE);
//                mActivity.myRelativeLayoutLines.get(mActivity.lineIndex).setVisibility(View.VISIBLE);
//                LanguageModel lo = (LanguageModel) parent.getAdapter().getItem(position);
//                mActivity.sessionLanguageCache.add(mActivity.lineIndex, lo);
////                mySpeech[lineIndex] = lo.stringToLocale();
////                mActivity.reloadLanguageLine(lo);
//                mActivity.myRelativeLayoutLines.get
//                        (mActivity.swapIndex(mActivity.lineIndex)).getChildAt(0).setEnabled(true);
////                myTextFields.get(mActivity.lineIndex).requestFocus();
//                if (mActivity.lineIndex == 0) {
//                    mActivity.sessionLanguageCache.add(1, mActivity.sessionLanguageCache.get(2));
//                }
//                mActivity.myRelativeLayoutLines.get(mActivity.lineIndex).requestFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
