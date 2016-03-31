package com.android.marco.firstdecision.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import com.android.marco.firstdecision.R;
import com.android.marco.firstdecision.dataModels.ThingToDo;

import java.util.ArrayList;

/**
 * Created by gen on 01.07.15.
 *
 */

public class SharedPrefUtil {

    public SharedPrefUtil() {
    }

    public boolean saveThings(Context mContext, ArrayList<ThingToDo> list) {
        SharedPreferences prefs = mContext.getSharedPreferences("things", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String boolMark = "bool";
        String result = "";
        for (int i = list.size() - 1; i > 0; i--) {
            editor.putBoolean(boolMark + i, list.get(i).checked);
            result = "," + list.get(i).named_thing + result;
        }
        result = list.get(0).named_thing + result;
        editor.putString("thing_titles", result);
        Log.e("sdfsddfasdf", result);
        return editor.commit();
//        return true;
    }


    public ArrayList<ThingToDo> loadThings(Context mContext) {
        ArrayList<ThingToDo> result = new ArrayList<ThingToDo>();
//        SharedPreferences prefs = mContext.getSharedPreferences("things", Context.MODE_PRIVATE);
        Resources res = mContext.getResources();
        String[] start = res.getStringArray(R.array.things_evening);
        for (String aStart : start) {
            result.add(new ThingToDo(aStart, true));
        }
        return result;
    }
}
