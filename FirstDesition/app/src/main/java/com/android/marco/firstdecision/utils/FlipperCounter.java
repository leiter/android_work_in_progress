package com.android.marco.firstdecision.utils;

/**
 * Created by gen on 22.11.15.
 */
public class FlipperCounter {
    static int count = 0;

    public FlipperCounter() {
        count = count - 1;
    }

    public FlipperCounter(boolean b) {
        if (b) {
            count = count + 1;
        }
    }

}
