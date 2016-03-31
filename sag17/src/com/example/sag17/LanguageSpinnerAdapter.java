package com.example.sag17;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LanguageSpinnerAdapter extends ArrayAdapter<String> {
	
	private Activity activity;
    private ArrayList data;
    public Resources res;
    LanguageModel tempValues=null;
//    LanguageModel tempValLeft=null;
    LayoutInflater inflater;

    public LanguageSpinnerAdapter(
                          MainActivity activitySpinner,
                          int textViewResourceId,  
                          ArrayList objects,
                          Resources resLocal
                         )
     {
        super(activitySpinner, textViewResourceId, objects);

        activity = activitySpinner;
        data     = objects;
        res      = resLocal;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         
      }
 
    
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.spinner_rows, parent, false);
        tempValues = null;
        tempValues = (LanguageModel) data.get(position);
    
        TextView label        = (TextView)row.findViewById(R.id.company);
        TextView sub          = (TextView)row.findViewById(R.id.sub);
        ImageView companyLogo = (ImageView)row.findViewById(R.id.image);

        if(position==0){
        	row = null;
        	row = inflater.inflate(R.layout.spinnerrow1, parent, false); }
        else if (position==1) { 
        	row = null;
        	LanguageModel tempValLeft = (LanguageModel) data.get(0);
        	row = inflater.inflate(R.layout.spinnerrow2, parent, false);
        	ImageView leftFlag = (ImageView)row.findViewById(R.id.spinnerFlag1);
        	ImageView rightFlag = (ImageView)row.findViewById(R.id.spinnerFlag2);
        	leftFlag.setImageResource(res.getIdentifier
        			("com.example.sag17:drawable/"+ tempValLeft.getImage(),null,null));
            rightFlag.setImageResource(res.getIdentifier
        			("com.example.sag17:drawable/"+ tempValues.getImage(),null,null));
//            tempValLeft = null;
        }
        
        else {    
            label.setText(tempValues.getCountry());
            sub.setText(tempValues.getLanguage());
            companyLogo.setImageResource(res.getIdentifier
            			("com.example.sag17:drawable/"+ tempValues.getImage(),null,null));         
        }  	 
        return row;
      }
}
