package com.android.marco.tellme.activities;

/**
 * Created by gen on 09.04.15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.marco.tellme.R;
import com.android.marco.tellme.adapters.LanguageModel;
import com.android.marco.tellme.data.AsyncJSON;
import com.android.marco.tellme.helpers.LanguageRowHelper;
import com.android.marco.tellme.helpers.LanguageSpinnerHelper;
import com.android.marco.tellme.helpers.NavigationDrawerHelper;
import com.android.marco.tellme.helpers.SharedPrefManager;
import com.android.marco.tellme.utils.SharedPrefKeys;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 1234;
    public ArrayList<LanguageModel> sessionLanguageCache = new ArrayList<LanguageModel>(2);
    public int TAB_SELECTED_INDEX;
    public int textViewIndex;
    public EditText input_1, input_2;
    public SharedPrefManager mSharedPrefManager;
    public HorizontalScrollView scroll_Tab_1;
    public int TEXTVIEW_INDEX;
    private NavigationDrawerHelper mNavigationDrawerHelper;
    private ListView wordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(null);
        setUp();
    }

    public void showTabForId(int mode) {
        TAB_SELECTED_INDEX = mode;
        ViewGroup myContainer = (ViewGroup)findViewById(R.id.container);
        myContainer.removeViewAt(0);
        switch (TAB_SELECTED_INDEX) {
            case 0:
                LayoutInflater.from(this).inflate(R.layout.fragment_quiz, myContainer);
                mSharedPrefManager.mSharedPreferences.edit()
                .putInt(SharedPrefKeys.ScrollPosition.TAB_1,scroll_Tab_1.getScrollX()).commit();
                break;
            case 1:
                LayoutInflater.from(this).inflate(R.layout.fragment_question, myContainer);
                setUp();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void langSelector2(View v) {
//        if (v.getParent().equals(myRelativeLayoutLines.get(0))) {
//            lineIndex = 0;
//        } else lineIndex = 1;
////        setListData(0);
////        adapter.notifyDataSetChanged();
//        myRelativeLayoutLines.get(swapIndex(lineIndex)).getChildAt(0).setEnabled(false);
//        myRelativeLayoutLines.get(lineIndex).setVisibility(View.GONE);
//        findViewById(R.id.sp_ma_lang_selector).setVisibility(View.VISIBLE);
//        findViewById(R.id.sp_ma_lang_selector).performClick();
    }

    public int swapIndex(int position) {
        if (position == 0) {return 1;}
        return 0;
    }

    public void getTranslationButton(View v) {
        getTranslation();
    }

    public void getTranslation() {
        if (!connectionAvailable) {
            Toast myToast = Toast.makeText(this, "Internet nicht verfügbar", Toast.LENGTH_LONG);
            myToast.setGravity(Gravity.TOP, 0, 0);
            myToast.show();
            return;
        }
        if (input_1.hasFocus()
                && !input_1.getText().toString().equals("")) {
            new AsyncJSON(this).execute();}
        else if (!input_2.getText().toString().equals("")) {
            textViewIndex = swapIndex(textViewIndex);
            new AsyncJSON(this).execute();}
        else {
            Toast myToast = Toast.makeText(this, "Textfelder sind leer.", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 0);
            myToast.show();
        }
    }

    private void setTextviewFocusIndex() {
        if (input_1.hasFocus()) {
            TEXTVIEW_INDEX = 0;
        } else if (input_2.hasFocus()) {
            TEXTVIEW_INDEX = 1;
        } else TEXTVIEW_INDEX = 3;
    }

    public void clearTextFieldInFocus(View v) {
        if (findViewById(R.id.et_ma_input_1).hasFocus()) {
            ((EditText) findViewById(R.id.et_ma_input_1)).setText("");
        } else ((EditText) findViewById(R.id.et_ma_input_2)).setText("");
    }

    public void setUp(){
        LanguageSpinnerHelper l = new LanguageSpinnerHelper(this);
        l.initialize();
        mNavigationDrawerHelper = new NavigationDrawerHelper(this);
        mNavigationDrawerHelper.initialize();
        sessionLanguageCache.add(new LanguageModel(Locale.GERMANY));
        sessionLanguageCache.add(new LanguageModel(Locale.US));
        LanguageRowHelper textViews = new LanguageRowHelper(this);
        textViews.setLanguageRows();
        mSharedPrefManager = new SharedPrefManager(this);
        mSharedPrefManager.restorValues();
        scroll_Tab_1 = (HorizontalScrollView) findViewById(R.id.hs_ma_buttons);
        wordsList = (ListView) findViewById(R.id.lv_ma_results);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInit(int status) {
        if (!connectionAvailable) {
            Log.e(TAG, "hello from init");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches));
            wordsList.setVisibility(View.VISIBLE);
            wordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String text = wordsList.getAdapter().getItem(position).toString();
                    if (!connectionAvailable) {
                        Toast myToast = Toast.makeText(getApplicationContext(), "Internet nicht verfügbar", Toast.LENGTH_LONG);
                        myToast.setGravity(Gravity.TOP, 0, 0);
                        myToast.show();}
                    else {
                        setTextviewFocusIndex();
                        if (TEXTVIEW_INDEX == 0) {input_1.setText(text);}
                        else if (TEXTVIEW_INDEX == 1) {input_2.setText(text); } }
                    getTranslation();
//                    ToggleButton kt = (ToggleButton) findViewById(R.id.keepSttList);
//                    if (!kt.isChecked()) {
//                        wordsList.setVisibility(View.GONE);
                }
            });
        }
    }



    public void listenButtonClicked(View v) {
        setTextviewFocusIndex();
        if (TEXTVIEW_INDEX == 3) {input_1.requestFocus();}
        startVoiceRecognitionActivity(sessionLanguageCache.get(TEXTVIEW_INDEX).getSpeechLocal());
    }

    private void startVoiceRecognitionActivity(String str) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, str);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getResources().getString(R.string.voiceregognition) + "Voice Recognition ...");    // TODO tiltel ändern
        startActivityForResult(intent, REQUEST_CODE);
    }
}