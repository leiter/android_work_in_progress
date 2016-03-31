package com.example.sag17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends FragmentActivity implements TextToSpeech.OnInitListener {
	// static ProgressDialog pro;	
	private IntentFilter mNetworkStateChangedFilter;
    private BroadcastReceiver mNetworkStateIntentReceiver;	
	private static final int REQUEST_CODE = 1234;
	private final Hashtable<String, Locale> supportedLanguages = new Hashtable<String, Locale>();
	private static final String TAG = MainActivity.class.getSimpleName();	
	private static String url = "http://mymemory.translated.net/api/get?q=%s";
	private DatabaseOpenHandler                                                                                                                                                                                                                                      openHandler;
	private TextToSpeech tts;
		
	TextView quest, showCorrect;
	Button a1, a2, a3, a4;
	String correct;
	Random myRandom = new Random();
	
	private int switchQuestionDirection = 0;
	private boolean connectionAvailable;
    private int textViewIndex = 0;
    private int lineIndex = 0;
    private int questIndex = 4;
    private int postWordListItemTo;
	private Button listen;
	private EditText txtText, ttxText; 
	private ListView wordsList;
	private ToggleButton showFeedBack;
	private int scrollPosition, scrollPosition_TAB2;
	private HorizontalScrollView myScrollView, myScrollView_TAB2;
	
    LanguageSpinnerAdapter adapter;
    MainActivity activity = null;
    Spinner  LanguageSpinner;
    Button swapLangs;
    SeekBar mySeekBar;
    RelativeLayout myAnswers;
    private EditText pasteBoard;
    InputMethodManager imm;
    
    Locale mySpeech[] = { Locale.GERMANY, new Locale("iw") , Locale.UK,  Locale.CHINA };
	ArrayList<Locale> myLocaleArr = new ArrayList<Locale>();

    public  ArrayList<LanguageModel> CustomListViewValuesArr = new ArrayList<LanguageModel>();    
    public  ArrayList<LanguageModel> sessionLanguageCache = new ArrayList<LanguageModel>(2);
    ArrayList<RelativeLayout> myRelativeLayoutLines = new ArrayList<RelativeLayout>();
    ArrayList<EditText> myTextFields = new ArrayList<EditText>();
	ArrayList<Button> questoinButtonArr = new ArrayList<Button>() ;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);			
		setContentView(R.layout.activity_main);
		openHandler = new DatabaseOpenHandler(this);				
		tts = new TextToSpeech(this, this);	
	
		pasteBoard = (EditText) findViewById(R.id.pasteme);
		myAnswers = (RelativeLayout) findViewById(R.id.answerbuttons);
		mySeekBar = (SeekBar) findViewById(R.id.seekBar1);
		showCorrect = (TextView) findViewById(R.id.correctAnswer);
		myScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		myScrollView_TAB2 = (HorizontalScrollView) findViewById(R.id.horizontalScrollView2);
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		scrollPosition = preferences.getInt("sViewX", 180);
		scrollPosition_TAB2 = preferences.getInt("sViewX_TAB2", 90);
		
		sessionLanguageCache.add(new LanguageModel(controllerStringToLocale( preferences.getString("line1", 
				Locale.getDefault().getLanguage()+"_"+Locale.getDefault().getCountry()))));
		sessionLanguageCache.add(new LanguageModel(controllerStringToLocale( preferences.getString("line2", 
				Locale.FRANCE.getLanguage()+"_"+Locale.FRANCE.getCountry()))));
		
		if (!preferences.getBoolean("showWordList", true)) {
			ToggleButton wordlistButton = (ToggleButton) findViewById(R.id.keepSttList);
			wordlistButton.performClick();}

		updateScrollPosition();
		
		RelativeLayout t = (RelativeLayout)  findViewById (R.id.r);		
		RelativeLayout z = (RelativeLayout)  findViewById (R.id.t);
		myRelativeLayoutLines.add(t);
		myRelativeLayoutLines.add(z);

		listen = (Button) findViewById(R.id.android_listen);
		wordsList = (ListView) findViewById(R.id.list);	
		
		txtText = (EditText) findViewById(R.id.input);
		ttxText = (EditText) findViewById(R.id.input2);	
		OnFocusChangeListener listener;
		listener = new OnFocusChangeListener() {          
		    public void onFocusChange(View v, boolean hasFocus) {
		    	
		    	if (v.getParent().equals(myRelativeLayoutLines.get(0))) {
		    		textViewIndex = 0 ;} else textViewIndex = 1;
		    	if(hasFocus){	    		
		    		ColorDrawable cd = (ColorDrawable) v.getBackground();
//		    		int c = cd.getColor();
		    		int c = getResources().getColor(R.color.yellow_light);
		    		Button tr = (Button) findViewById(R.id.translate);	    		
		    		Button l = (Button) findViewById(R.id.android_listen);
		    		Button clear = (Button) findViewById(R.id.clear);		    		
		    		l.setTextColor(c);   //  setShadowLayer(5.2f, 0, 0, c);   tr.setBackgroundColor(c);	
		    		clear.setTextColor(c);
		    		tr.setTextColor(c);
		    	}
		       }		    
		};
		txtText.setOnFocusChangeListener(listener);
		ttxText.setOnFocusChangeListener(listener);		
		myTextFields.add(txtText);
		myTextFields.add(ttxText);
	
		setupCheckAndSetButtons();	
		
		activity  = this;       
        LanguageSpinner = (Spinner)findViewById(R.id.testspinner);
        setListData(0);
        Resources res = getResources();
        adapter = new LanguageSpinnerAdapter(activity, R.layout.spinner_rows, CustomListViewValuesArr,res);
        LanguageSpinner.setAdapter(adapter);       
        LanguageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				
				LanguageSpinner.setVisibility(View.GONE);
				myRelativeLayoutLines.get(lineIndex).setVisibility(View.VISIBLE);
				LanguageModel lo = (LanguageModel) parent.getAdapter().getItem(position);
				sessionLanguageCache.add(lineIndex, lo);
				mySpeech[lineIndex] = lo.stringToLocale();		
				reloadLanguageLine(lo);
				myRelativeLayoutLines.get(swapIndex(lineIndex)).getChildAt(0).setEnabled(true);
				myTextFields.get(lineIndex).requestFocus();
				if (lineIndex==0) {sessionLanguageCache.add(1,sessionLanguageCache.get(2));}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) { }
		});
        
        reloadLanguageLine(sessionLanguageCache.get(0));
        lineIndex++;
        reloadLanguageLine(sessionLanguageCache.get(1)); // only reload the altered     
        
        showFeedBack = (ToggleButton) findViewById(R.id.feedBack);
       
        Handler h = new Handler();

//        h.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                // DO DELAYED STUFF
//            	getActionBar().hide();
//            } }, 1500);
        
//        ActionBar actionBar = getSupportActionBar();      //   
//        actionBar.hide();
        
//        pasteBoard.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if(hasFocus && v.isActivated()){
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
//                imm.hideSoftInputFromWindow(pasteBoard.getWindowToken(), 0);
//                }
//            }
//        });
        
//        pasteBoard.setImeOptions(InputMethodManager.RESULT_HIDDEN);
        
        
	}
	public void t (View v){
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
      imm.hideSoftInputFromWindow(pasteBoard.getWindowToken(), 0);
	}
	public void setupCheckAndSetButtons() {		
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		
		if (activities.size() == 0) {
			listen.setEnabled(false); } //  listen.setBackground();   listen.setImageAlpha(30);											
	
		mNetworkStateChangedFilter = new IntentFilter();
		mNetworkStateChangedFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		mNetworkStateIntentReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
//				Button trans = (Button) findViewById(R.id.translate);
//				if (!checkInternetConnection()) {
//					trans.setEnabled(false);
//				} else trans.setEnabled(true);
				connectionAvailable=checkInternetConnection();
			}
		};
		
		quest = (TextView)findViewById(R.id.question);
		quest.setSelected(true);
		a1 = (Button)findViewById(R.id.answer1);
		a2 = (Button)findViewById(R.id.answer2);
		a3 = (Button)findViewById(R.id.answer3);
		a4 = (Button)findViewById(R.id.answer4);
		
		
		questoinButtonArr.add(a1);
		questoinButtonArr.add(a2);
		questoinButtonArr.add(a3);
		questoinButtonArr.add(a4);
		mySeekBar.setProgress(0);
		mySeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//			int progress =0;
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				if (progress<16){
					setAllAnswerButtonsFalse();
					questIndex=4;
					quest.setSelected(true);}
				else if (progress>32 && progress<48){
					setAllAnswerButtonsFalse();
					questIndex=0;
					a1.setSelected(true);
				}
				else if (progress>48 && progress<64){
					setAllAnswerButtonsFalse();
					questIndex=1;
					a2.setSelected(true);
				}
				else if (progress>64 && progress<80){
					setAllAnswerButtonsFalse();
					questIndex=2;
					a3.setSelected(true);
				}
				else if (progress>80){
					setAllAnswerButtonsFalse();
					questIndex=3;
					a4.setSelected(true);
				}			
			}
		});
		
		Resources res = getResources();
		int color1 = res.getColor(R.color.yellow_light);
		int color2 = res.getColor(R.color.yellow_bright);
		ttxText.setBackgroundColor(color2);
		txtText.setBackgroundColor(color1);			
	}	
	public void setAllAnswerButtonsFalse() {
		quest.setSelected(false);
		for (Button b : questoinButtonArr) {
			b.setSelected(false);
		}
	}	
	public void deleteThisRow(View v) {
		openHandler.deleteRow();
		Toast myToast = Toast.makeText(this, "wurde gelöscht", Toast.LENGTH_SHORT);
		myToast.setGravity(Gravity.TOP, 0, 0);
		myToast.show();
		getAQuiz();
	}
	public void notAskThisAgain(View v){
		openHandler.dontAskAgain();
		getAQuiz();
	}
	public void changeQuestionDirection(View v) {
		switchQuestionDirection = swapIndex(switchQuestionDirection);
		getAQuiz();
	}
	public void langSelector2(View v) {			
		if (v.getParent().equals(myRelativeLayoutLines.get(0))) {
			lineIndex = 0; } else lineIndex = 1;		
		setListData(0);		
		adapter.notifyDataSetChanged();		
		myRelativeLayoutLines.get(swapIndex(lineIndex)).getChildAt(0).setEnabled(false);
		myRelativeLayoutLines.get(lineIndex).setVisibility(View.GONE);	
		LanguageSpinner.setVisibility(View.VISIBLE);
		LanguageSpinner.performClick();
	}
	public void saveToDB(View view) {
		int time = (int) System.currentTimeMillis();
		openHandler.createTableAndOrInsert
		(sessionLanguageCache, txtText.getText().toString(), ttxText.getText().toString(),time);
		Toast myToast = Toast.makeText(this, "Abgespeichert !", Toast.LENGTH_SHORT);
		myToast.setGravity(Gravity.TOP, 0, 0);
		myToast.show();	
		
	}	
	public void postQuiz(View v) {
		getAQuiz();
	}
	public void getAQuiz() {	
		String s = openHandler.createTableName(sessionLanguageCache);
		if (!openHandler.getTableNames(s)){  
			Toast.makeText(activity, "Zu wenig Wörter im Wörterbuch", Toast.LENGTH_LONG).show();
			return; }
		String[][] e = openHandler.getQuizVolume(sessionLanguageCache);
		ArrayList<Integer> excludeArr = new ArrayList<Integer>();

		if ( e.length < 20  ) {
			Toast.makeText(activity, "Zu wenig Wörter im Wörterbuch", Toast.LENGTH_LONG).show();
		}
		else {		int anti = swapIndex(switchQuestionDirection);
					int p = myRandom.nextInt(3 - 0 + 1) + 0;			
					excludeArr.add(p);
					int j = 0;
					for (int i = 0; i<50;i++ ) {     // TODO make easier but save 
						int t = myRandom.nextInt(10 + 0 + 1) + 0;
						if (excludeArr.contains(t)) { continue;}
						if (j>3) { break;}
						questoinButtonArr.get(j).setText(e[t][anti]);
						excludeArr.add(t);
						j++; 
					}
					correct=e[p][anti];					
					quest.setText(e[p][switchQuestionDirection]);
					questoinButtonArr.get(p).setText(correct);
					openHandler.close();								}
			
	}
	public void showPasteBoard(View v) {
		if ( pasteBoard.getVisibility() == View.GONE ) {
			 pasteBoard.setVisibility(View.VISIBLE);}
		else pasteBoard.setVisibility(View.GONE);
	}
	public void respondToGuess (View v) {
		Button b = ((Button)v);   
		// add findview by id test i t mit oder ohne find /cast findViewById(v.getId()
		if (b.getText().toString().length() == 0) { 
			Toast myToast = Toast.makeText(this, "Es stehen noch keine Wortpaare in dieser Sprache bereit.", Toast.LENGTH_SHORT); 
			myToast.show();
			return;
		}
		
		String w = b.getText().toString();
		if (w.equals(correct)) {
			openHandler.updateQuestedRow(1);			
			showFeedBack.setChecked(true);
			showFeedBack.setVisibility(View.VISIBLE);
			showCorrect.setText("");
			showCorrect.setVisibility(View.VISIBLE);
			
			
	      Handler h = new Handler();
	      h.postDelayed(new Runnable() {
	          @Override
	          public void run() {
	        	  showFeedBack.post(new Runnable() { public void run() 
	        	  { showFeedBack.setVisibility(View.GONE); 
	        	    showCorrect.setVisibility(View.GONE); } }); } }, 1500);
			getAQuiz();
								}		
		else {
			myAnswers.setVisibility(View.INVISIBLE);
			myScrollView_TAB2.setVisibility(View.INVISIBLE);
			mySeekBar.setVisibility(View.INVISIBLE);
			showCorrect.setText(correct);
			showCorrect.setVisibility(View.VISIBLE);
			openHandler.updateQuestedRow(0);
			Handler h = new Handler();
		      h.postDelayed(new Runnable() {
		          @Override
		          public void run() {
		        	  showFeedBack.post(new Runnable() { public void run() 
		        	  { showFeedBack.setVisibility(View.GONE); 
		        	  
		        	  myAnswers.setVisibility(View.VISIBLE);
		        	  myScrollView_TAB2.setVisibility(View.VISIBLE);
		        	  mySeekBar.setVisibility(View.VISIBLE);
		        	  showCorrect.setVisibility(View.GONE); getAQuiz();} }); } }, 4500);			
							}
	}	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches));
			wordsList.setVisibility(View.VISIBLE);
			wordsList.setOnItemClickListener(new OnItemClickListener() {				
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					postWordListItemTo=textViewIndex;
					String text = wordsList.getAdapter().getItem(position).toString();					
					myTextFields.get(textViewIndex).requestFocus();  
					myTextFields.get(textViewIndex).setText(text); 
					
					if (!connectionAvailable ) {
						Toast myToast = Toast.makeText(activity, "Internet nicht verfügbar", Toast.LENGTH_LONG); 
						myToast.setGravity(Gravity.TOP, 0, 0);
						myToast.show();
					}   
					else getTranslation(myTextFields.get(textViewIndex));
						
					ToggleButton kt = (ToggleButton) findViewById(R.id.keepSttList);
					if (!kt.isChecked()) {
						wordsList.setVisibility(View.GONE);
					}}});
		}
	}
	public void listenButtonClicked(View v) {startVoiceRecognitionActivity(mySpeech[textViewIndex].toString());} 
	private void startVoiceRecognitionActivity(String str) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, str);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getResources().getString(R.string.voiceregognition) + "Voice Recognition ..."); 	// TODO tiltel ändern   
		startActivityForResult(intent, REQUEST_CODE);
	}
	public void speakNow(View view) {
		speakOut();
	}
	public void tellQuizContent(View v){
		int index = 0;
		String content = "";
		if (switchQuestionDirection==1) {
			index = swapIndex(index);
		}
		if (questIndex==4) {
			content = quest.getText().toString();
			tts.setLanguage(mySpeech[swapIndex(index)]);
			tts.speak(content, TextToSpeech.QUEUE_FLUSH, null);		
		}
		else {
			Button b = questoinButtonArr.get(questIndex);
			content = b.getText().toString();
			tts.setLanguage(mySpeech[index]);
			tts.speak(content, TextToSpeech.QUEUE_FLUSH, null);
		}
	}
	private void speakOut() {	
		tts.setLanguage(mySpeech[1]);
		tts.speak(ttxText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
	}
	public void clearTextFieldInFocus (View v) { myTextFields.get(textViewIndex).setText("");} 	
	public void getTranslation(View view) {		
		if (!connectionAvailable) { 
			Toast myToast = Toast.makeText(activity, 
				"Internet nicht verfügbar", Toast.LENGTH_LONG); 
			myToast.setGravity(Gravity.TOP, 0, 0);
			myToast.show();
			
			return; }
		if (myTextFields.get(textViewIndex).hasFocus()
				&& !myTextFields.get(textViewIndex).getText().toString().equals("")) { new JSONParse().execute();}
		else if (!myTextFields.get(swapIndex(textViewIndex)).getText().toString().equals("")) {
			textViewIndex = swapIndex(textViewIndex);
			myTextFields.get(textViewIndex).requestFocus();
			new JSONParse().execute();}
		else { 
			Toast myToast = Toast.makeText(activity, "Textfelder sind leer.", Toast.LENGTH_SHORT);
			myToast.setGravity(Gravity.TOP, 0, 0);
			myToast.show();		
		}
	}	

	public Locale controllerStringToLocale(String s) {
		String[] p = s.split("_");
	    return new Locale(p[0],p[1]);
	}
	public void updateScrollPosition() {
      myScrollView.post(new Runnable() { public void run() { myScrollView.scrollTo(scrollPosition, 0); } });
//      Handler h = new Handler();
//      h.postDelayed(new Runnable() {
//          @Override
//          public void run() {
//        	  myScrollView_TAB2.post(new Runnable() { public void run() 
//        	  { myScrollView_TAB2.scrollTo(scrollPosition_TAB2, 0); } }); } }, 2000);
    }
	public void updateScroll2 () {
		myScrollView_TAB2.post(new Runnable() { public void run() { myScrollView_TAB2.scrollTo(scrollPosition_TAB2, 0); } });
	}
	public void swapLanguageLines(View v) {
		LanguageSpinner.setVisibility(View.GONE);
	
		if (lineIndex==0){
		LanguageModel l = sessionLanguageCache.get(1);
		sessionLanguageCache.add(0, l);}
		else if (lineIndex==1) {
			LanguageModel m = CustomListViewValuesArr.get(1);
			LanguageModel l = CustomListViewValuesArr.get(0);			
			sessionLanguageCache.clear();
			sessionLanguageCache.add(0,m);
			sessionLanguageCache.add(1,l);
		}
		
		setListData(0);
		
		reloadLanguageLine(sessionLanguageCache.get(0));
		lineIndex=swapIndex(lineIndex);
		reloadLanguageLine(sessionLanguageCache.get(1));		
		lineIndex=swapIndex(lineIndex);
		
		String t1 = ((EditText)myRelativeLayoutLines.get(lineIndex).getChildAt(1)).getText().toString();
		String t2 = ((EditText)myRelativeLayoutLines.get(swapIndex(lineIndex)).getChildAt(1)).getText().toString();		
		((EditText)myRelativeLayoutLines.get(lineIndex).getChildAt(1)).setText(t2);
		((EditText)myRelativeLayoutLines.get(swapIndex(lineIndex)).getChildAt(1)).setText(t1);	
		adapter.notifyDataSetChanged();
		myRelativeLayoutLines.get(lineIndex).setVisibility(View.VISIBLE);
		myRelativeLayoutLines.get(swapIndex(lineIndex)).getChildAt(0).setEnabled(true);
		postWordListItemTo=swapIndex(postWordListItemTo);
	}
	public void reloadLanguageLine( LanguageModel l) {
		int resID = getResources().getIdentifier(l.getImage(), "drawable", "com.example.sag17");
		ImageButton i = (ImageButton)findViewById(myRelativeLayoutLines.get(lineIndex).getChildAt(0).getId());
		if (resID==0) {i.setImageDrawable(getResources().getDrawable(R.drawable.unknown));}
		else { i.setImageResource(resID); }
		((EditText)myRelativeLayoutLines.get(lineIndex).getChildAt(1)).setHint(l.getLanguage());
	
	}
	public boolean excludeLanguage(ArrayList<Locale> l, Locale myLocal){		
		for (Locale aLocal : l) {
			if (aLocal.getLanguage().equals(myLocal.getLanguage()))
			return true;
		}
		return false;	
	}	
	public void setListData(int mode) {  
		CustomListViewValuesArr.clear();
		Locale al[]  = Locale.getAvailableLocales();
		ArrayList<Locale> cache = new ArrayList<Locale>(); 
		mySpeech[0]=sessionLanguageCache.get(0).stringToLocale();
		mySpeech[1]=sessionLanguageCache.get(1).stringToLocale();
		cache.add(mySpeech[0]);			//TODO make global
		cache.add(mySpeech[1]);
		CustomListViewValuesArr.add(sessionLanguageCache.get(0));
		CustomListViewValuesArr.add(sessionLanguageCache.get(1));
		
		for (int i = 0; i < al.length; i++) 			{
			Locale l = al[i];
			if (l.getDisplayCountry().equals("") || excludeLanguage(cache, l))  { continue; }  
			if (l.equals(Locale.UK) || l.equals(new Locale("de", "de")) || l.equals(new Locale("ru", "RU")) ||
					l.equals(new Locale("es", "es")) || l.equals(Locale.FRANCE) || l.equals(new Locale("ar", "IQ")) || 
				    l.equals(Locale.ITALY) || l.equals(Locale.CHINA)) {
			final LanguageModel sched = new LanguageModel(l);
			cache.add(l);				
			CustomListViewValuesArr.add(sched);	
			}	
		} 
		final LanguageModel t = new LanguageModel(new Locale("iw", "IL"));
		CustomListViewValuesArr.add(t);	

    }	
	public int getViewPositionInParent() {
		int i=0;		
		return i;	
	}	
	public boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);		
		if(cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() &&
					cm.getActiveNetworkInfo().isConnectedOrConnecting()) {    connectionAvailable=true;  return true;		}	
		connectionAvailable=false;
		return false;  // .isConnected()
	} 	
	public int swapIndex(int position) {
		if (position == 0){
			return 1;
		}   return 0;	
	}	
	public int[] myThreeRandoms(int i) {
		Random aRandom = new Random();
		int result[] = {};
		result[0] = aRandom.nextInt( i - 2 + 1) + 2;		
		int pos = 1;
		while (result.length < 3 ) {
				int p = aRandom.nextInt( i - 1 + 1) + 1 ;	
				if (result[0] != p ) { 
					result[pos]=p;
					pos++;}
				if (pos > 1) {
					if (result[0] != p && result[1] != p ) {
						result[pos]=p;
					pos++;
					}	
				}
				else { continue;
								}
		}
		return result;			
	}
	public String concatUrl(String str) {return String.format(url, str);}
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		
		private int viewIndex1 = -1;
		private int viewIndex2 = -1;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (textViewIndex==0) { viewIndex1=0; viewIndex2 = 1;}
			else if (textViewIndex==1) { viewIndex1=1; viewIndex2 = 0;}
			else Log.d(TAG + "  onPreExecute()", "Textfield focus orientation seem to Fail.");	
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			JSONParser jParser = new JSONParser();
			String from = "", to = "", msg = "";
			from = sessionLanguageCache.get(viewIndex1).getLanguageCode();
			to = sessionLanguageCache.get(viewIndex2).getLanguageCode();
			msg = myTextFields.get(viewIndex1).getText().toString();
			String call_url = concatUrl(Uri.encode(msg));
			JSONObject json = jParser.getJSONFromUrl(call_url + "&langpair="
					+ from + "%7C" + to);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {			
			try {String result = json.getJSONObject("responseData").getString(
						"translatedText");
				 myTextFields.get(viewIndex2).setText(Html.fromHtml(result));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}	
								  			 	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		Log.d("Menu created", "cant tell");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {  // btnSpeak.setEnabled(true); }
		Locale[] myLocales = Locale.getAvailableLocales();
		for (Locale lang : myLocales) {
			Locale loc = lang;
			switch (tts.isLanguageAvailable(loc)) {
			case TextToSpeech.LANG_MISSING_DATA:
			case TextToSpeech.LANG_NOT_SUPPORTED: 
				// if test oder case behalten   TextToSpeech.LANG_AVAILABLE:
				break;
			default:
				String key = loc.getDisplayCountry() + "   " + loc.getDisplayLanguage();
				if (!supportedLanguages.containsKey(key) && !key.endsWith("_") && !key.equals("")) {
					supportedLanguages.put(key, loc); 				
					}				
				}
			}
		for (Locale l : supportedLanguages.values()) {
			Log.d("IIISSSSSSSS", l.toString());
		}
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(mNetworkStateIntentReceiver, mNetworkStateChangedFilter);
		unregisterReceiver(mNetworkStateIntentReceiver);
		registerReceiver(mNetworkStateIntentReceiver,mNetworkStateChangedFilter);
	}
	@Override
	protected void onPause() {
		super.onPause();			// unregisterReceiver(mNetworkStateIntentReceiver);
		ToggleButton wordlistButton = (ToggleButton) findViewById(R.id.keepSttList);
		  SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		  SharedPreferences.Editor editor = preferences.edit();
		  editor.putInt("sViewX", myScrollView.getScrollX());
		  editor.putInt("sViewX_TAB2", myScrollView_TAB2.getScrollX());
		  editor.putString("line1", sessionLanguageCache.get(0).getImage());
		  editor.putString("line2", sessionLanguageCache.get(1).getImage());
		  editor.putBoolean("showWordList",wordlistButton.isChecked() );
		  editor.commit();	
	}
	@Override
	public void onDestroy() {
		if (tts != null ) {
			tts.stop();
			tts.shutdown();}
		super.onDestroy();
	}
}
