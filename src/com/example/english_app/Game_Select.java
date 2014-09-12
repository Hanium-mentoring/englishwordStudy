package com.example.english_app;

import oxQuiz.Ox_include_explain;
import quiz.Quiz_include_explain;
import com.example.kk_excel.R;

import acid_rain.Acid_Rain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class Game_Select {

	Context context;
	RelativeLayout rl;
	String select1, select2,select3;
	Button back;
	public Game_Select(Context context,RelativeLayout rl,Button back)
	{
		this.context = context;
		this.rl =rl;
		this.back = back;
		
		rl.setBackgroundResource(R.drawable.updown_background);
	}
	
	public void Initialize_Views()
	{

		select1 = "우주로 떠나요!";
		select2 = "하늘로 떠나요!";
		select3 = "바다로 떠나요!";
		
		Button goto1 = new Button(context);
		Button goto2 = new Button(context);
		Button goto3 = new Button(context);
		
		goto1.setTextSize(20);
		goto1.setTextColor(Color.BLACK);
		goto2.setTextSize(20);
		goto2.setTextColor(Color.BLACK);
		goto3.setTextSize(20);
		goto3.setTextColor(Color.BLACK);
		
		goto1.setText(select1);
		goto2.setText(select2);
		goto3.setText(select3);
		
		RelativeLayout.LayoutParams pra = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		rl.removeAllViews();
		
		ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin.setMargins(250,100,300,0);
		

		ViewGroup.MarginLayoutParams margin2 = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin2.setMargins(250,350,300,0);
		
		ViewGroup.MarginLayoutParams margin3 = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin3.setMargins(250,600,300,0);
		

		ViewGroup.MarginLayoutParams margin4 = new ViewGroup.MarginLayoutParams(150,100);
		
		margin4.setMargins(400,850,300,0);
		
		rl.addView(goto1, 0, pra);
		rl.addView(goto2,1,pra);
		rl.addView(goto3,2,pra);
		rl.addView(back);
		
		goto1.setBackgroundResource(R.drawable.updown_b);
		goto1.setLayoutParams(new RelativeLayout.LayoutParams(margin));
		
		goto2.setBackgroundResource(R.drawable.updown_b);
		goto2.setLayoutParams(new RelativeLayout.LayoutParams(margin2));
		
		goto3.setBackgroundResource(R.drawable.updown_b);
		goto3.setLayoutParams(new RelativeLayout.LayoutParams(margin3));
		
		back.setLayoutParams(new RelativeLayout.LayoutParams(margin4));
		
		goto1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Ox_include_explain oe = new Ox_include_explain(context, rl,back);
				oe.ox_explain_initialize();
				
			}
		});
		
		goto2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Acid_Rain ar = new Acid_Rain(context, rl, back);
				ar.Acid_Rain_initialize();
				
			}
		});
		
		goto3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Quiz_include_explain qe = new Quiz_include_explain(context, rl, back);
				qe.q_explain_initialize();
			}
		});
	
	}


}
