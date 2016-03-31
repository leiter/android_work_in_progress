package com.android.marco.tellme.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;

import java.util.List;

/**
 * Created by gen on 09.04.15.
 */
public abstract class BaseActivity extends ActionBarActivity implements TextToSpeech.OnInitListener {

    public TextToSpeech tts_1;
    public TextToSpeech tts_2;
    public boolean connectionAvailable;
    private IntentFilter mNetworkStateChangedFilter;
    private BroadcastReceiver mNetworkStateIntentReceiver;

    @Override
    protected void onResume() {
        super.onResume();
        mNetworkStateChangedFilter = new IntentFilter();
        mNetworkStateChangedFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetworkStateIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                connectionAvailable = checkInternetConnection();
            }
        };
        registerReceiver(mNetworkStateIntentReceiver, mNetworkStateChangedFilter);
        tts_1 = new TextToSpeech(this, this);
        tts_2 = new TextToSpeech(this, this);
    }

    public boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null && cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isAvailable() &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting()) {
            connectionAvailable = true;
            return true;
        }
        connectionAvailable = false;
        return false;
    }

    public boolean isSpeechToTextAvailable() {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        return (activities.size() == 0) ? false : true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts_1 != null) {
            tts_1.stop();
            tts_1.shutdown();
        }
        if (tts_2 != null) {
            tts_2.stop();
            tts_2.shutdown();
        }
        if (mNetworkStateIntentReceiver != null) {
            unregisterReceiver(mNetworkStateIntentReceiver);
        }

    }
}
