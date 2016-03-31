package com.android.marco.firstdecision.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.marco.firstdecision.R;
import com.android.marco.firstdecision.adapters.ListThingsAdapter;
import com.android.marco.firstdecision.dataModels.ThingToDo;
import com.android.marco.firstdecision.fragments.OneOfManyFragment;
import com.android.marco.firstdecision.helpers.ListDataHelper;
import com.android.marco.firstdecision.utils.AnimationUtil;
import com.android.marco.firstdecision.utils.SharedPrefUtil;
import com.android.marco.firstdecision.views.BubbleButton;
import com.android.marco.firstdecision.widgets.AddDialog;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected static String TAG = MainActivity.class.getSimpleName();
    private final int THREE_FABS = 1;
    private final int TWO_FABS = 0;
    public ArrayList<ThingToDo> listData = new ArrayList<ThingToDo>();
    public Button resultButton;
    private AddDialog addThingDialog;
    private boolean showTrash = false;
    private int CURRENT_FABS_STATE = TWO_FABS;
    private RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout mainContainer;
    private Typeface myType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            ActionBar a = getSupportActionBar();
            a.setDisplayShowHomeEnabled(true);
            a.setDisplayShowCustomEnabled(true);
            a.setCustomView(R.layout.actionbar_background);
            TextView t = (TextView) a.getCustomView().findViewById(R.id.ab_title);
            t.setText("Am Abend ins ");
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new OneOfManyFragment())
                    .commit();
        }
        setListData();

        findViewById(R.id.myFAB_right).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startFlipping(v);
                return true;
            }
        });

        resultButton = (Button) findViewById(R.id.btn_ma_result);
        mainContainer = (RelativeLayout) findViewById(R.id.container);
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.my_center);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        myType = Typeface.createFromAsset(getAssets(), "fonts/An_Original_Font_By_Davi.ttf");
        addThingDialog = new AddDialog(this);
    }


    private void startFlipping(View view) {
        Intent i = new Intent(this, YesOrNoActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight());
        startActivity(i, optionsCompat.toBundle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((Button) findViewById(R.id.btn_ma_result)).setTextAppearance(this, R.style.AppBubbles);
        ((Button) findViewById(R.id.btn_ma_result))
                .setTypeface(Typeface.createFromAsset(getAssets(), "fonts/James_Fajardo.ttf"));
        AnimationUtil.slideInOut(this, findViewById(R.id.container_rel), true);
        showMiddleFAB(false);
    }

    public void setListData() {
        SharedPrefUtil s = new SharedPrefUtil();
        listData = s.loadThings(this);

    }

    public void animate_now() {
        if (resultButton.getVisibility() == View.VISIBLE) {
            resultButton.setVisibility(View.INVISIBLE);
        }
        final ArrayList<Button> bubbleBag = new ArrayList<Button>();
        int j = 0;
        for (int i = 0; i < listData.size() * 4; i++) {
            if (listData.get(j).checked) {
                final Button b = new BubbleButton(this, listData.get(j).named_thing, myType, layoutParams);
                bubbleBag.add(b);
                mainContainer.addView(b);
                j++;
                if (j > listData.size() - 1) {
                    j = 0;
                }
            } else if (!listData.get(j).checked) {
                j++;
                if (j > listData.size() - 1) {
                    j = 0;
                }
            }
        }
        AnimationUtil.AnimateBubbleSize(bubbleBag, resultButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SharedPreferences prefs = getSharedPreferences("things_5", Context.MODE_PRIVATE);
        ListDataHelper helpe = new ListDataHelper();

        switch (id) {

            case R.id.action_settings:
                try {
                    SharedPreferences.Editor e = prefs.edit();
                    e.putString("myListData", helpe.serialize(listData));
                    e.apply();
                } catch (IOException e) {
                    Log.e(TAG, "IOExeption on action_settingID" + e.getMessage());
                }
                return true;

        }

        if (id == R.id.action_use_touch_gestures) {
            try {
                ListView l = (ListView) findViewById(R.id.lv_oneofmany_things);
                listData = (ArrayList<ThingToDo>) helpe.deserialize(prefs.getString("myListData", null));
                l.setAdapter(new ListThingsAdapter(this, R.id.lv_oneofmany_things, listData, showTrash));
            } catch (IOException e) {
                Log.e(TAG, "iiiioooo" + e.getMessage());
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (AnimationUtil.isInAnimation) {
            animate_now();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bnt_ma_add:
                addThing();
                break;
            case R.id.show_delete_items:
                ListView l = (ListView) findViewById(R.id.lv_oneofmany_things);
                l.setAdapter(new ListThingsAdapter(this, R.id.lv_oneofmany_things, listData, showTrash));
                showTrash = !showTrash;
                break;
            case R.id.ib_oneofmany_edit:

                break;
            case R.id.myFAB_right:
                if (findViewById(R.id.container_rel).getVisibility() == View.VISIBLE && !AnimationUtil.listIsSliding) {
                    showMiddleFAB(false);
                    AnimationUtil.slideInOut(this, findViewById(R.id.container_rel), true);
                }
                animate_now();
                break;

            case R.id.myFAB_left0:
                if (findViewById(R.id.container_rel).getVisibility() > View.VISIBLE) {
                    showMiddleFAB(true);
                    AnimationUtil.slideInOut(this, findViewById(R.id.container_rel), false);
                    resultButton.setVisibility(View.GONE);
                }
                break;

            case R.id.myFAB_middle:
                addThingDialog.show();
                break;

        }
    }

    private void showMiddleFAB(boolean showMiddleFAB) {
        final FloatingActionButton midFAB = (FloatingActionButton) findViewById(R.id.myFAB_left0);
        if (showMiddleFAB) {
            findViewById(R.id.myFAB_middle).setVisibility(View.VISIBLE);
            midFAB.setImageResource(R.drawable.ic_menu_add);
            CURRENT_FABS_STATE = THREE_FABS;
        } else {
            findViewById(R.id.myFAB_middle).setVisibility(View.GONE);
            midFAB.setImageResource(android.R.drawable.ic_menu_mylocation);
            CURRENT_FABS_STATE = TWO_FABS;
        }
    }

    public void addThing() {
        ListView lv = (ListView) findViewById(R.id.lv_oneofmany_things);
        listData.add(0, new ThingToDo(addThingDialog.getText(), true));
        ListThingsAdapter adapter = (ListThingsAdapter) lv.getAdapter();
        adapter.notifyDataSetChanged();
        addThingDialog.dismiss();
    }
}
