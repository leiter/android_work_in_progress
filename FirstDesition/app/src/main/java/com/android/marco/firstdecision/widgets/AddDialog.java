package com.android.marco.firstdecision.widgets;

import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.marco.firstdecision.R;
import com.android.marco.firstdecision.activities.MainActivity;

/**
 * Created by gen on 22.08.15.
 */
public class AddDialog {

    private Dialog addDialog;

    public AddDialog(MainActivity context) {
        MainActivity mContext = context;
        this.addDialog = new Dialog(mContext);
        addDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        addDialog.setTitle("Am Abend ins");
        addDialog.setContentView(R.layout.dialog_add_item);
        addDialog.findViewById(R.id.bnt_ma_add).setOnClickListener(mContext);
        int titleDividerId = mContext.getResources().getIdentifier("titleDivider", "id", "android");
        View titleDivider = addDialog.getWindow().getDecorView().findViewById(titleDividerId);
//        titleDivider.setBackgroundColor(ContextCompat.getColor(context, R.color.brown));

    }


    public void show() {
        addDialog.show();
    }

    public void dismiss() {
        EditText e = ((EditText) addDialog.findViewById(R.id.et_ma_set_hobby));
        e.setText("");
        addDialog.dismiss();
    }

    public String getText() {
        EditText e = ((EditText) addDialog.findViewById(R.id.et_ma_set_hobby));
        return e.getText().toString();
    }

}
