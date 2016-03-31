package com.android.marco.tellme.data;

import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.android.marco.tellme.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gen on 23.04.15.
 */
public class AsyncJSON extends AsyncTask<String, String, JSONObject> {

    public static String TAG = AsyncJSON.class.getSimpleName();
    private MainActivity mActivity;
    private int TRANSLATION_DIRECTION = -1;

    public AsyncJSON(MainActivity mainActivity) {
        mActivity = mainActivity;
    }

    public String concatUrl(String str) {
        String url = "http://mymemory.translated.net/api/get?q=%s";
        return String.format(url, str);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mActivity.input_1.hasFocus()) {
            TRANSLATION_DIRECTION = 0;
        } else if (mActivity.input_2.hasFocus()) {
            TRANSLATION_DIRECTION = 1;
        } else Log.d("  onPreExecute()", "Textfield focus orientation seem to Fail.");
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        JSONParser jParser = new JSONParser();
        String from, to;
        String msg;
        if (TRANSLATION_DIRECTION == 0) {
            from = mActivity.sessionLanguageCache.get(TRANSLATION_DIRECTION).getLanguageCode();
            to = mActivity.sessionLanguageCache.get(1).getLanguageCode();
            msg = mActivity.input_1.getText().toString();
        } else if (TRANSLATION_DIRECTION == 1) {
            from = mActivity.sessionLanguageCache.get(TRANSLATION_DIRECTION).getLanguageCode();
            to = mActivity.sessionLanguageCache.get(0).getLanguageCode();
            msg = mActivity.input_2.getText().toString();
        } else {
            Log.e(TAG, "Index out of Bound. There are only two text fields.");
            return null;
        }
        String call_url = concatUrl(Uri.encode(msg));
        JSONObject json;
        json = jParser.getJSONFromUrl(call_url + "&langpair=" + from + "%7C" + to);
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        try {
            String result = json.getJSONObject("responseData").getString("translatedText");
            if (TRANSLATION_DIRECTION == 0) {
                mActivity.input_2.setText(Html.fromHtml(result));
            } else {
                mActivity.input_1.setText(Html.fromHtml(result));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
