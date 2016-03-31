package com.android.marco.tellme.data;

import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.android.marco.tellme.activities.MainActivity;
import com.android.marco.tellme.adapters.LanguageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by gen on 10.04.15.
 */
public class InfoAboutLocales {

    ArrayList<Locale> ttsAvailable;
    ArrayList<Locale> sttAvailable;

    private MainActivity mActivity;

    public InfoAboutLocales() {

    }
    public InfoAboutLocales(TextToSpeech tts) {
        Locale[] allLocales;
        allLocales = Locale.getAvailableLocales();
//        TextToSpeech.EngineInfo ttsINFO = tts.getDefaultEngine();
        for (Locale l : allLocales) {
            switch (tts.isLanguageAvailable(l)) {
                case TextToSpeech.LANG_MISSING_DATA:
                case TextToSpeech.LANG_NOT_SUPPORTED:
                    // if test oder case behalten   TextToSpeech.LANG_AVAILABLE:
                    break;
                default:
                    if (!ttsAvailable.contains(l)) {
                        ttsAvailable.add(l);
                        Log.e("Text2SPEECH" , l.toString());
                    }
            }

        }
    }


    public InfoAboutLocales(MainActivity activity) {
        mActivity = activity;
        PackageManager pm = mActivity.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        for (ResolveInfo i : activities) {
            Log.e("InfoAboutLocales  ", i.toString());
        }
        FeatureInfo[] lll = pm.getSystemAvailableFeatures();
        for (FeatureInfo f : lll) {
            Log.e("InfoAboutLocales  xxxx", f.toString());
        }

    }

    private boolean excludeLanguage(ArrayList<Locale> l, Locale myLocal) {
        for (Locale aLocal : l) {
            if (aLocal.getLanguage().equals(myLocal.getLanguage()))
                return true;
        }
        return false;
    }

    public ArrayList<LanguageModel> getAllCountries() {
        ArrayList<Locale> cache = new ArrayList<Locale>();
        ArrayList<LanguageModel> result = new ArrayList<LanguageModel>();
        Locale al[] = Locale.getAvailableLocales();


        String qwer[] = Locale.getISOCountries();

        for (int i = 0; i < al.length; i++) {
            Locale l = al[i];
            Log.e("sdfsfs", l.getCountry() + "  " + l.getLanguage() + "_______");

            if (l.getDisplayCountry().equals("") || excludeLanguage(cache, l) ||
                    l.getDisplayLanguage().equals("") ||
                    l.getCountry().length() > 2) {
                continue;
            }
            LanguageModel sched = new LanguageModel(l);
            Log.e("language model ", sched.getImage());
            result.add(sched);
            cache.add(l);
        }
        for (int i = 0; i < qwer.length; i++) {

            Log.e("iiisssooo", qwer[i].toString().toLowerCase());
        }
        return result;
    }

    public void getCountries() {


    }

}
