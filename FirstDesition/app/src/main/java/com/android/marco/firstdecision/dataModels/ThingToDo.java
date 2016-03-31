package com.android.marco.firstdecision.dataModels;

import java.io.Serializable;

/**
 * Created by gen on 02.06.15.
 *
 */
public class ThingToDo implements Serializable {
    public String named_thing;
    public boolean checked;

    public ThingToDo(String thing, boolean checked) {
        this.named_thing = thing;
        this.checked = checked;
    }
}