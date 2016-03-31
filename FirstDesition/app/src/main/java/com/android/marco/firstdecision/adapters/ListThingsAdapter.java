package com.android.marco.firstdecision.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.android.marco.firstdecision.R;
import com.android.marco.firstdecision.activities.MainActivity;
import com.android.marco.firstdecision.dataModels.ThingToDo;
import com.android.marco.firstdecision.helpers.ListTextViewHelper;

import java.util.ArrayList;

/**
 * Created by gen on 02.06.15.
 *
 */

public class ListThingsAdapter extends ArrayAdapter<ThingToDo> {

    private MainActivity activity;
    private LayoutInflater inflater;
    private boolean showDelete = false;

    public ListThingsAdapter(MainActivity act, int LayoutResourceId, ArrayList<ThingToDo> objects, boolean trashVisible) {
        super(act, LayoutResourceId, objects);
        activity = act;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        showDelete = trashVisible;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView, ViewGroup parent) {
        //TODO use converview
        final ThingToDo tempValues = getItem(position);
        View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_RIGHT = 2;

                if (!((EditText) v).getCompoundDrawables()[DRAWABLE_RIGHT].isVisible())
                    return false;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (v.getRight() - ((EditText) v).getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() * 1.5)) {
                        ListTextViewHelper ll = new ListTextViewHelper(activity);
                        ll.prepareTextView(((EditText) v), !tempValues.checked, showDelete);
                        getItem(position).checked = !tempValues.checked;
                        notifyDataSetChanged();
                        event.setAction(MotionEvent.ACTION_CANCEL);
                        return true;
                    }
                    if (event.getRawX() <= ((EditText) v).getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width() * 1.5) {
                        remove(getItem(position));
                        notifyDataSetChanged();
                        event.setAction(MotionEvent.ACTION_CANCEL);
                        return true;
                    }
                }
                return false;
            }
        };

        EditText row = (EditText) inflater.inflate(R.layout.list_edit_text, parent, false);
        ListTextViewHelper l = new ListTextViewHelper(activity);
        l.prepareTextView(row, tempValues.checked, showDelete);
        row.setText(tempValues.named_thing);
        row.setOnTouchListener(mOnTouchListener);
        return row;

    }


}
