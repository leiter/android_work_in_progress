package com.android.marco.firstdecision.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.marco.firstdecision.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by gen on 28.05.15.
 *
 *
 */

public class AnimationUtil {

    public static String TAG = AnimationUtil.class.getSimpleName();
    public static boolean isInAnimation = false;
    public static boolean listIsSliding = false;
    public static boolean isFlipping = false;

    public AnimationUtil() {
    }

    public static void AnimateBubbleSize(final ArrayList<Button> bag,
                                         final Button result) {

        isInAnimation = true;
        if (CountHandler.count != 0) {
            CountHandler.count = CountHandler.count + bag.size();
        } else {
            CountHandler.count = bag.size();
        }

        final AnimatorSet animatorSet = new AnimatorSet();
        final int randomRange = bag.size();

        for (int i = 0; i < bag.size(); i++) {
            final View v = bag.get(i);
            final ObjectAnimator scaleDownX = ObjectAnimator.ofPropertyValuesHolder(v,
                    PropertyValuesHolder.ofFloat("scaleX", 1.5f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.5f),
                    PropertyValuesHolder.ofFloat("alpha", 0.3f));
            scaleDownX.setDuration(100);
            scaleDownX.setRepeatMode(ValueAnimator.REVERSE);
            scaleDownX.setRepeatCount(10);
            scaleDownX.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    Log.d(TAG, Integer.toString(CountHandler.count));

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    new CountHandler();
                    v.setVisibility(View.GONE);
                    v.clearAnimation();
                    ((ViewManager) v.getParent()).removeView(v);

                    if (CountHandler.count == 0) {
                        Log.d(TAG, "found if clausse");
                        TextView mView = bag.get(getRandom(0, randomRange));
                        RelativeLayout aLayout = (RelativeLayout) result.getParent();
                        if (aLayout.findViewById(R.id.container_rel).getVisibility() == View.VISIBLE) {
                            slideInOut(aLayout.getContext(), aLayout.findViewById(R.id.container_rel), true);
                            aLayout.findViewById(R.id.myFAB_middle).setVisibility(View.GONE);

                        }
                        result.setText(mView.getText().toString());
                        result.setVisibility(View.VISIBLE);
                        getResultAnimation(result);
                        isInAnimation = false;
//                           v.clearAnimation();
                        bag.clear();

                    }
                    Log.d(TAG, Integer.toString(CountHandler.count));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    int m = 1, n = 1;
                    if (Math.random() > 0.5) {
                        m = -1;
                    }
                    if (Math.random() > 0.5) {
                        n = -1;
                    }
                    scaleDownX.setDuration(getRandom(150, 200));
                    v.animate().translationX(getRandom(m * 100, 20)).withLayer();
                    v.animate().translationY(getRandom(n * 100, 70)).withLayer();
                }

            });
            animatorSet.play(scaleDownX);
        }
        animatorSet.start();
    }



    public static void getResultAnimation(View v) {
        ObjectAnimator o = ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleX", 0.f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0.f, 1.0f),
                PropertyValuesHolder.ofFloat("alpha", 0.0f, 0.7f));
        o.setDuration(300);
        o.start();
    }

    public static int getRandom(int start, int delta) {
        return start + (int) (Math.random() * (start + delta));
    }

    public static void FlipImageView(final Context context, final View img_bottom, final View img_top) {

        if (!isFlipping) {
            isFlipping = true;
            int repeat_count = 12;

            Animator.AnimatorListener listener = new Animator.AnimatorListener() {
                private int counter = 0;
                private Random mRandom = new Random();

                @Override
                public void onAnimationStart(Animator animation) {
                    if (counter == 0) {
                        img_top.setBackground(ResourcesCompat.getDrawable(context.getResources(),
                                R.drawable.sta256, context.getTheme()));
                        img_bottom.setBackground(ResourcesCompat.getDrawable(context.getResources(),
                                R.drawable.tra64, context.getTheme()));
                    }
//                else {counter++;}
                }

                @Override
                public void onAnimationEnd(Animator animation) {
//                counter--;
                    if (counter < 1) {
                        if (mRandom.nextBoolean()) {
                            img_bottom.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sta256, context.getTheme()));
                        } else {
                            img_bottom.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.tra64, context.getTheme()));
                        }

                        isFlipping = false;
                    }


//                else { animation.start(); }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {


                }
            };

            final ObjectAnimator rotate_1 = ObjectAnimator.ofPropertyValuesHolder(img_top,
                    PropertyValuesHolder.ofFloat("rotationX", 0f, -180f),
                    PropertyValuesHolder.ofFloat("alpha", 1f, 0f));
            rotate_1.setDuration(200);
            rotate_1.setInterpolator(new AccelerateDecelerateInterpolator());
            rotate_1.setRepeatCount(repeat_count);

            final ObjectAnimator rotate_2 = ObjectAnimator.ofPropertyValuesHolder(img_bottom,
                    PropertyValuesHolder.ofFloat("alpha", 1f, 0f),
                    PropertyValuesHolder.ofFloat("rotationX", 180f, 0f),
                    PropertyValuesHolder.ofFloat("alpha", 0f, 1f));
            rotate_2.setDuration(200);
            rotate_2.setInterpolator(new AccelerateDecelerateInterpolator());
            rotate_2.setRepeatCount(repeat_count);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(rotate_1, rotate_2);
            animatorSet.addListener(listener);
            animatorSet.start();
        }




    }

    public static void slideInOut(Context context, final View layout, boolean isShowing) {
        final int newViewState;
        if (isShowing && !listIsSliding) {
            layout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out));
            newViewState = View.GONE;
            listIsSliding = true;
            layout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listIsSliding = false;
                }
            }, 900);

        } else {
            layout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in));
            newViewState = View.VISIBLE;
        }
        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.clearAnimation();
                layout.setVisibility(newViewState);
            }
        }, 900);

    }

}