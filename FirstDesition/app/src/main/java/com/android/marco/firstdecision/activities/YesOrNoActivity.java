package com.android.marco.firstdecision.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.marco.firstdecision.R;
import com.android.marco.firstdecision.utils.AnimationUtil;


public class YesOrNoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes_or_no);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        findViewById(R.id.yn_fab).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                exit();
                return true;
            }
        });

        flip();
    }

    public void go_flip(View v) {
        flip();
    }

    public void flip() {
        AnimationUtil.FlipImageView(this,
                findViewById(R.id.flipper_1),
                findViewById(R.id.flipper_2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_yes_or_no, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
            exit();
//            finish();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exit();
//        super.onBackPressed();
    }

    private void exit() {
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.present_final_bubble);
        final View b = findViewById(R.id.container);
        b.startAnimation(animation);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setVisibility(View.GONE);
                b.clearAnimation();
                finish();
            }
        }, 1500);
    }

}
