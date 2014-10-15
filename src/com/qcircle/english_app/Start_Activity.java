package com.qcircle.english_app;

import java.util.ArrayList;

import com.qcircle.kk_excel.R;

import dto.VocaDTO;
import fullscreen.FullScreen_startActivity;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;


public class Start_Activity extends Activity{
	public static final int EXTRA_ACCESSORY_COVER_OPENED = 0;
	public static final int EXTRA_ACCESSORY_COVER_CLOSED = 1;
	public static final String EXTRA_ACCESSORY_COVER_STATE = "com.lge.intent.extra.ACCESSORY_COVER_STATE";
	public static final String ACTION_ACCESSORY_COVER_EVENT = "com.lge.android.intent.action.ACCESSORY_COVER_EVENT";
	// [END]declared in LGIntent.java of LG Framework

	// [START] QuickCover Settings DB
	public static final String QUICKCOVERSETTINGS_QUICKCOVER_ENABLE = "quick_view_enable";
	// [END] QuickCover Settings DB

	// QuickCover Type
	public static final int QUICKCOVERSETTINGS_QUICKCIRCLE = 3;
	// [START] QuickCircle info.
	static boolean quickCircleEnabled = false;
	static int quickCaseType = 0;
	static boolean quickCircleClosed = true;
	int circleWidth = 0;
	int circleHeight = 0;
	int circleXpos = 0;
	int circleYpos = 0;
	int circleDiameter = 0;
	// [END] QuickCircle info.

	Button tv;
	// -------------------------------------------------------------------------------
	private final boolean DEBUG = true;
	private final String TAG = "[my_example_i'm hungry]";
	
	private int mQuickCoverState = 0;
	private Context mContext;
	private Window win = null;
	private ContentResolver contentResolver = null;
	
	//For buttons
	Button backBtn = null;
	Button title = null;
	Button start_study = null;
	Button start_game_select = null;
	
	boolean right_pressed = false;
	boolean left_pressed = false;
	
	SQLiteDatabase database;
	
	GestureDetector gd;
	
	ArrayList<VocaDTO> list;
	//Query the Device model
	String device = android.os.Build.DEVICE;
	Boolean isG3 = false;
	RelativeLayout rl;
	boolean flag = false;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.start_activity);
	
	//Retrieve a view for the QuickCircle window.
			final View circlemainView = findViewById(R.id.start_activity_view);
			
			//Is this G3?
			Log.d(TAG, "device:" + device );
			isG3 = device.equals("g3");
			Log.d(TAG, "isG3:" + isG3 );

		
			list = new ArrayList<VocaDTO>();
				
			//Get application context
			mContext = getApplicationContext();
			
			//Get content resolver
			contentResolver = getContentResolver();
			
			
			//Register an IntentFilter and a broadcast receiver
			registerIntentReceiver();
			
			//Set window flags
			setQuickCircleWindowParam();
			
			//Get QuickCircle window information
			initializeViewInformationFromDB();
			
			//Initialize buttons
			
			initializeBackButton();
			
			//Crops a layout for the QuickCircle window
			setCircleLayoutParam(circlemainView);
					
			rl = (RelativeLayout)circlemainView;
}

void initializeViewInformationFromDB() {
	
	Log.d(TAG, "initializeViewInformationFromDB");
	if (contentResolver == null) {
		return;
	}
	
	Log.d(TAG, "initializeViewInformationFromDB");

	
	//Check the availability of the case 
	quickCircleEnabled = Settings.Global.getInt(contentResolver,QUICKCOVERSETTINGS_QUICKCOVER_ENABLE, 0) == 0 ? true : false;
	
	if (DEBUG) {
		Log.d(TAG, "quickCircleEnabled:" + quickCircleEnabled);
	}
	
	//Get a case type
	quickCaseType = Settings.Global.getInt(contentResolver,"cover_type", 0 );
		
	
	
	
	//[START] Get the QuickCircle window information
	int id = getResources().getIdentifier("config_circle_window_width", "dimen",
			"com.lge.internal");
	circleWidth = getResources().getDimensionPixelSize(id);
	if (DEBUG) {
		Log.d(TAG, "circleWidth:" + circleWidth);
	}

	id = getResources().getIdentifier("config_circle_window_height", "dimen", "com.lge.internal");
	circleHeight = getResources().getDimensionPixelSize(id);
	if (DEBUG) {
		Log.d(TAG, "circleHeight:" + circleHeight);
	}

	id = getResources()
			.getIdentifier("config_circle_window_x_pos", "dimen", "com.lge.internal");
	circleXpos = getResources().getDimensionPixelSize(id);
	if (DEBUG) {
		Log.d(TAG, "circleXpos:" + circleXpos);
	}

	id = getResources()
			.getIdentifier("config_circle_window_y_pos", "dimen", "com.lge.internal");
	circleYpos = getResources().getDimensionPixelSize(id);
	if (DEBUG) {
		Log.d(TAG, "circleYpos:" + circleYpos);
	}

	id = getResources().getIdentifier("config_circle_diameter", "dimen", "com.lge.internal");
	circleDiameter = getResources().getDimensionPixelSize(id);
	if (DEBUG) {
		Log.d(TAG, "circleDiameter:" + circleDiameter);
	}
	//[END]
}

void setCircleLayoutParam(View view) {

RelativeLayout layout  = (RelativeLayout)view;
RelativeLayout.LayoutParams layoutParam = (RelativeLayout.LayoutParams) layout.getLayoutParams();
   
//Set layout size same as a circle window size 
layoutParam.width = circleDiameter;
layoutParam.height = circleDiameter;  

if (circleXpos < 0) {
	
	//Place a layout to the center
	layoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.CENTER_IN_PARENT);
} else {
    layoutParam.leftMargin = circleXpos;
}

//Set top margin to the offset
if(isG3) {
    layoutParam.topMargin = circleYpos ;
    Log.i(TAG , "topMargin :"+circleYpos);
} else {
    layoutParam.topMargin = circleYpos + (circleHeight - circleDiameter)/2;
    Log.i(TAG , "topMargin :"+(circleYpos + (circleHeight - circleDiameter)/2));
}


layout.setLayoutParams(layoutParam);
getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

}
private void initializeBackButton() {
	// TODO Auto-generated method stub
	
	backBtn = (Button)findViewById(R.id.start_back_btn);
	title = (Button)findViewById(R.id.title);
	start_study = (Button)findViewById(R.id.start_study);
	start_study.setTextSize(20);
	start_game_select = (Button)findViewById(R.id.start_game_select);
	start_game_select.setTextSize(20);
	
	backBtn.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			Start_Activity.this.finish();
		}
	});
	
	start_study.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Updown up = new Updown(mContext, rl,getParent(), backBtn);
			up.Updown_initialize();
			/*
			Intent call_study = new Intent(getApplicationContext(), QuickCircleAcitivity.class);
			startActivity(call_study);
			Start_Activity.this.finish();*/
		}
	});
	
	start_game_select.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Game_Select gs = new Game_Select(mContext, rl, backBtn);
			gs.Initialize_Views();
			
			/*Intent call_select = new Intent(getApplicationContext(), Game_quickCircle_select_Activity.class);
			startActivity(call_select);
			Start_Activity.this.finish();*/
		}
	});
	
}

private void registerIntentReceiver() {
	// TODO Auto-generated method stub
	IntentFilter filter = new IntentFilter();
	// Add QCircle intent to the intent filter
	filter.addAction(ACTION_ACCESSORY_COVER_EVENT);
	
	// Register a broadcast receiver with the system
	mContext.registerReceiver(mIntentReceiver, filter);
}

@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	
	if(database != null)
	database.close();
	
	Start_Activity.this.finish();
	
	if(mIntentReceiver != null)
	getApplicationContext().unregisterReceiver(mIntentReceiver);
	
}

private void setQuickCircleWindowParam() {
	win = getWindow();
	if (win != null) {
		// Show the sample application view on top
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_FULLSCREEN
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
}


private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		if (action == null) {
			return;
		}

		//Receives a LG QCirle intent for the cover event
		if (ACTION_ACCESSORY_COVER_EVENT.equals(action)) {

			if (DEBUG) {
				Log.d(TAG, "ACTION_ACCESSORY_COVER_EVENT");
			}

			//Gets the current state of the cover
			mQuickCoverState = intent.getIntExtra(EXTRA_ACCESSORY_COVER_STATE,
					EXTRA_ACCESSORY_COVER_OPENED);

			if (DEBUG) {
				Log.d(TAG, "mQuickCoverState:" + mQuickCoverState);
			}

			if (mQuickCoverState == EXTRA_ACCESSORY_COVER_CLOSED) { // closed
				//Set window flags
				setQuickCircleWindowParam();
			} 
			else if (mQuickCoverState == EXTRA_ACCESSORY_COVER_OPENED) { // opened
					//Call FullScreenActivity
				
					Intent callFullscreen = new Intent(getApplicationContext(), FullScreen_startActivity.class);
					startActivity(callFullscreen);
					
				
					
					//Finish QCircleActivity
					Start_Activity.this.finish();
			}
		}
		
	}
};
}
