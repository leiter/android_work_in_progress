package com.android.marco.firstdecision.dataModels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gen on 02.06.15.
 *
 */
public class ThingsListModel implements Serializable {

    private String listTitle;
    private ArrayList<ThingToDo> things = new ArrayList<ThingToDo>();

    public ThingsListModel() {
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public ArrayList<ThingToDo> getThings() {
        return things;
    }

    public void setThings(ArrayList<ThingToDo> things) {
        this.things = things;
    }

    public void add(ThingToDo t) {
        things.add(t);
    }

    public ThingToDo get(int index) {
        return things.get(index);
    }

    public int size() {
        return things.size();
    }

    public void addAtPosition(int pos, ThingToDo t) {
        things.add(pos, t);

    }
}

