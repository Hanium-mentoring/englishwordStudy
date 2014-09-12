package oxQuiz;

import java.util.ArrayList;
import java.util.Random;

import com.example.kk_excel.R;

import database.Database;
import dto.VocaDTO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class Ox_include_explain {

	Context context;
	RelativeLayout rl;
	Button back;
	GestureDetector gd;
	boolean flag = false;
	TextView tv;
	int voca_count = 0;
	ArrayList<VocaDTO> list;
	private static final Random RNG = new Random();
	private static final Random RNG2 = new Random();
	private static final Random RNG3 = new Random();
	
	private static int itmp = 0;
	private static String tmp = null;
	private static int tf = 0;
	Button xBtn,oBtn,score;
	private int correct_count = 0;
	Activity ac;
	public Ox_include_explain(Context context,RelativeLayout rl,Button back)
	{

		this.context = context;
		this.rl = rl;
		this.back = back;
		tf=0;
		
	}
	
	public void ox_explain_initialize()
	{
		flag = false;
		rl.removeAllViews();
		list = new ArrayList<VocaDTO>();
		
		rl.setBackgroundResource(R.drawable.moon_background);
		rl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(flag == false)
				{
					tv.setText("click");
					initializeDB();
					
					initializeButton();
					flag = true;
				}
			}
		});
		
		String explain = "5개의 문제 중, 뜻과 영어 단어가 같으면  o, 아니면 x를 선택하세요.";
		
		tv = new TextView(context);
		tv.setText(explain);
		tv.setTextSize(20);
		tv.setTextColor(Color.WHITE);
		
		ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin.setMargins(150,200,0,0);
		ViewGroup.MarginLayoutParams margin4 = new ViewGroup.MarginLayoutParams(150,100);
		
		margin4.setMargins(400,850,300,0);
		
		
		rl.addView(tv);
		rl.addView(back);
		
		tv.setLayoutParams(new RelativeLayout.LayoutParams(margin));
		back.setLayoutParams(new RelativeLayout.LayoutParams(margin4));
		
	}

	private void initializeButton() {
		// TODO Auto-generated method stub
		
		rl.setBackgroundResource(R.drawable.ox);
		xBtn = new Button(context);
		oBtn = new Button(context);
		score = new Button(context);
		

		ViewGroup.MarginLayoutParams margin_voca = new ViewGroup.MarginLayoutParams(700,400);
		
		margin_voca.setMargins(300, 0, 300, 0);

		ViewGroup.MarginLayoutParams margin_o = new ViewGroup.MarginLayoutParams(300,500);
		
		margin_o.setMargins(200, 400, 0, 0);


		ViewGroup.MarginLayoutParams margin_x = new ViewGroup.MarginLayoutParams(300,500);
		
		margin_x.setMargins(550, 400, 0, 0);

		
		
		rl.addView(oBtn);
		rl.addView(xBtn);
		
		tv.setGravity(Gravity.CENTER);
		tv.setLayoutParams(new RelativeLayout.LayoutParams(margin_voca));
		oBtn.setText("O!");
		oBtn.setBackgroundColor(Color.TRANSPARENT);
		xBtn.setBackgroundColor(Color.TRANSPARENT);
		oBtn.setLayoutParams(new RelativeLayout.LayoutParams(margin_o));
		
		xBtn.setLayoutParams(new RelativeLayout.LayoutParams(margin_x));
		
		
		ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		
		
		margin.setMargins(400, 670, 0, 0);

		score.setLayoutParams(new RelativeLayout.LayoutParams(margin));
		score.setText("맞춘 개수 : 0");
		
		oBtn.setTextSize(20);
		xBtn.setTextSize(20);
		
		if(voca_count ==100)
		{
			xBtn.setVisibility(View.INVISIBLE);
			oBtn.setVisibility(View.INVISIBLE);
		}
		
		xBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				if(voca_count<100)
				{
				if(list.get(itmp).getKorean() != tmp)
				{	
					correct_count++;
					
					score.setText("맞힌 개수 : "+correct_count);
				}
				voca_count++;
				
				tf = RNG3.nextInt(2);
				itmp = RNG2.nextInt(list.size()-1);
				
				if(tf == 0)
				{
					tmp = list.get(itmp).getKorean();
				}
				
				else
				{
					tmp = list.get(RNG.nextInt(list.size()-1)).getKorean();
				}
				
				tv.setText("\n"+list.get(itmp).getEnglish()+"\n"+tmp);
		    	
		    	if(voca_count > 4)
		    	{
		    		print_result();
		    	}
				}
			}
		});
		oBtn.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			if(voca_count <100)
			{
			if(list.get(itmp).getKorean() == tmp)
			{
				correct_count++;
				score.setText("맞힌 개수 : "+correct_count);
			}
			
			voca_count++;
			
			tf = RNG3.nextInt(2);
			itmp = RNG2.nextInt(list.size()-1);
			
			if(tf == 0)
			{
				tmp = list.get(itmp).getKorean();
			}
			
			else
			{
				tmp = list.get(RNG.nextInt(list.size()-1)).getKorean();
			}
			
			tv.setText("\n"+list.get(itmp).getEnglish()+"\n"+tmp);
	    	
	    	if(voca_count > 4)
	    	{
	    		print_result();
	    	}
		}
		}
	});
	}

	void print_result()
	{
		rl.setBackgroundResource(R.drawable.moon_background);
		rl.removeView(oBtn);
		rl.removeView(xBtn);
		
		ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin.setMargins(70,240,0,0);
		
		tv.setText("게임이 끝났습니다. 맞춘개수는 : "+correct_count+"입니다.");
		tv.setTextSize(20);
		tv.setLayoutParams(new RelativeLayout.LayoutParams(margin));
	}
	void initializeDB()
	{
		SQLiteDatabase database = context.openOrCreateDatabase("excel.db", context.MODE_WORLD_WRITEABLE, null);
		
		Database db = new Database();
		
		if(!db.exist_table(database, "study"))
		{
			tv.setText("\n\n공부할 영어 단어가 존재하지 않습니다.");
			voca_count =100;
		}
		else
		{
			
				list = db.Load_UserData(database);
			
				
				if(list.size()<5)
				{
					ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
					
					margin.setMargins(150,50,0,0);
					
					tv.setText("\n\n공부할 영어 단어 수가 	부족합니다. \n 5개이상! 케이스를 열어 추가해주세요");
					tv.setLayoutParams(new RelativeLayout.LayoutParams(margin));
					voca_count =100;
				}
				else
				{
				Log.d("g3", "isG3:" + list.get(0).getEnglish() );

				tf = RNG3.nextInt(2);
				itmp = RNG2.nextInt(list.size()-1);
				
				if(tf == 0)
				{	
					tmp = list.get(itmp).getKorean();
				}
				else
				{
					tmp = list.get(RNG.nextInt(list.size()-1)).getKorean();
					
				}
				
				tv.setText("\n"+list.get(itmp).getEnglish()+"\n"+tmp);
				}
			
		}
	}
	
}
