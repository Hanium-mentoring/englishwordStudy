package com.qcircle.english_app;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.qcircle.kk_excel.R;

import database.Database;
import dto.ViewDTO;
import dto.VocaDTO;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class Updown {

	Context context;
	Activity ac;
	Button back;
	RelativeLayout rl;
	ArrayList<VocaDTO> list;
	int voca_count=0;
	SQLiteDatabase database;
	int touch=0;
	Button tv;
	boolean flag = false;
	
	Timer timer;
	
	final Handler handler  = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what)
			{
			
			case 1:
				if(voca_count < 100)
				{
				if(tv.getText().toString().equals(list.get(voca_count).getEnglish()))
			    	voca_count++;
			    	
			    	if(voca_count >= (list.size()-1))
			    	{
			    		   
			    		database.close();

			    		timer.cancel();
			    		
			    		Game_Select gs = new Game_Select(context, rl, back);
			    		
			    		gs.Initialize_Views();
			    		//Intent callFullscreen = new Intent(getApplicationContext(), Game_quickCircle_select_Activity.class);
						//startActivity(callFullscreen);
								
					
			    	}
			    	else
			    	tv.setText(list.get(voca_count).getEnglish());
				}
				break;
			
		
			}
	
		}
	};
	
	public Updown(Context context,RelativeLayout rl,Activity ac,Button back)
	{
	
		this.rl = rl;
		this.context = context;
		this.ac = ac;
		this.back = back;
		
		list = new ArrayList<VocaDTO>();
		
		rl.setBackgroundResource(R.drawable.updown_background);
	}
	
	public void Updown_initialize()
	{
		rl.removeAllViews();
		
		rl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				touch++;
				
				if(flag == false)
				{
				initializeDB();

				ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
				
				margin.setMargins(270,320,200,0);
				
				tv.setLayoutParams(new RelativeLayout.LayoutParams(margin));
				
				
				flag= true;
				}
				else
				{
					if(touch>1)
					{
					tv.setText(list.get(voca_count).getEnglish()+"\n"+list.get(voca_count).getKorean());
					ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
					
					margin.setMargins(270,320,200,0);
						
					tv.setLayoutParams(new RelativeLayout.LayoutParams(margin));
					
					}
					
					}	
			}
		});
		
		String explain = "단어가 천천히 바뀝니다. 모르시는 단어가 있으면 클릭해주세요! 뜻이 출력됩니다.";
		
		tv = new Button(context);
		tv.setText(explain);
		tv.setBackgroundColor(Color.TRANSPARENT);
		tv.setTextSize(20);
		tv.setTextColor(Color.BLACK);
		tv.setClickable(false);
		tv.setTextAlignment(Gravity.CENTER);
		rl.addView(tv);
		rl.addView(back);
		
		ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin.setMargins(80,250,0,0);
		
		tv.setLayoutParams(new RelativeLayout.LayoutParams(margin));
		
		ViewGroup.MarginLayoutParams back_margin = new ViewGroup.MarginLayoutParams(150,100);
		
		back_margin.setMargins(400,850,300,0);
		
		back.setLayoutParams(new RelativeLayout.LayoutParams(back_margin));
	}
	

class Timertask extends TimerTask
{

	int task;
	ViewDTO view;
	int margin;
	
	public Timertask(int task)
	{
		this.task = task;
	
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
					
		handler.obtainMessage(task).sendToTarget();
		

	}
	
}


void initializeDB()
{
	 database = context.openOrCreateDatabase("excel.db", context.MODE_WORLD_WRITEABLE, null);
	
	Database db = new Database();
	
	
	if(!db.exist_table(database, "study"))
	{
		tv.setText("공부할 영어 단어가 존재하지 않습니다.");
		voca_count =100;
	}
	else if(db.Load_UserData(database).size()==0)
	{

		tv.setText("공부할 영어 단어가 존재하지 않습니다.");
		voca_count =100;
	}
	else
	{
		
		
	list = db.Load_UserData(database);
	
	tv.setText(list.get(0).getEnglish());
	
	timer = new Timer();
	Timertask tt = new Timertask(1);
	timer.scheduleAtFixedRate(tt, 0, 3000);
	}
}
}
