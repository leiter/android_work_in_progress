package com.android.marco.tellme.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.marco.tellme.R;
import com.android.marco.tellme.activities.MainActivity;

import java.util.ArrayList;

/**
 * Created by gen on 14.04.15.
 */
public class LanguageSpinnerAdapter extends ArrayAdapter<LanguageModel> {

    public Resources res;
    private MainActivity mActivity;
    private ArrayList<LanguageModel> data;
    //    private LanguageModel tempValues=null;
    private LayoutInflater inflater;

    public LanguageSpinnerAdapter(MainActivity activity, int textViewResourceId,
                                  ArrayList<LanguageModel> objects, Resources resource) {
        super(activity, textViewResourceId, objects);
        mActivity = activity;
        data = objects;
        res = resource;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_rows, parent, false);
        LanguageModel tempValues;
        tempValues = data.get(position);
        TextView country_name = (TextView) row.findViewById(R.id.tv_ma_country);
        TextView language_name = (TextView) row.findViewById(R.id.tv_ma_language);
        ImageView flag = (ImageView) row.findViewById(R.id.iv_ma_flag);

        if (position == 0) {
//            row = null;
            row = inflater.inflate(R.layout.spinnerrow_1, parent, false);
        } else if (position == 1) {
//            row = null;
            LanguageModel tempValLeft = data.get(0);
            row = inflater.inflate(R.layout.spinnerrow_2, parent, false);
            ImageView leftFlag = (ImageView) row.findViewById(R.id.iv_ma_lang_1);
            ImageView rightFlag = (ImageView) row.findViewById(R.id.iv_ma_lang_2);
            leftFlag.setImageResource(res.getIdentifier
                    (tempValLeft.getImage(), "drawable", mActivity.getPackageName()));
            rightFlag.setImageResource(res.getIdentifier
                    (tempValues.getImage(), "drawable", mActivity.getPackageName()));
//            tempValLeft = null;
        } else {
            country_name.setText(tempValues.getCountry());
            language_name.setText(tempValues.getLanguage());
            int resID = res.getIdentifier
                    (tempValues.getImage(), "drawable", mActivity.getPackageName());
            if (resID != 0) {
                flag.setImageResource(resID);
            } else flag.setImageResource(android.R.drawable.ic_delete);
        }
        return row;
    }
}
