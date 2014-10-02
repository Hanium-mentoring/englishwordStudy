package acid_rain;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.example.kk_excel.R;

import database.Database;
import dto.ViewDTO;
import dto.VocaDTO;

import android.R.color;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class Acid_Rain {

	Context context;
	RelativeLayout rl;
	Button back;
	
	

	RelativeLayout rr;
	TextView text, text2,text3,text4,text5,text6,text7,text25,text8,text9,text10,text11,text12;
	TextView text13,text14,text15,text16,text17,text18,text19,text20,text21,text22,text23,text24,text26;
	
	RelativeLayout.LayoutParams pra;
	ArrayList<ViewDTO> view2;
	ArrayList<TextView> alpha;
	ArrayList<Character> answer;
	int period;
	ViewGroup.MarginLayoutParams margin;
	
	TextView tv;
	Button process_voca;
	Timer timer;
	Timertask all_timertask;
	ArrayList<ViewGroup.MarginLayoutParams> Margins;
	int processing;
	String currentString;
	private final int ANIMATION_START = 1;
	private final int MAKING = 2;

	char[] current_voca;
	boolean flag = false;
	VocaDTO THISvoca;
	boolean exist = false;
	int count=0;
	int score;
	int nono_count =0;
	
	final Handler handler  = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what)
			{
			
			case ANIMATION_START:
			
					ViewDTO v = (ViewDTO) msg.obj;
					TextView temp = (TextView) v.getView();
					int left= v.getX();
					int top = v.getY();
					
					temp.setWidth(200);
					temp.setHeight(150);
					temp.setTextColor(Color.BLACK);
					int c_left = temp.getLeft();
					int c_top = temp.getTop();
					Log.d("margins",left+" "+top+" "+c_left);
				
			
					nono_count++;
					
					if(nono_count ==3)
					{
						process_voca.setText(currentString);
						nono_count=0;
						
					}
					ViewGroup.MarginLayoutParams margin1 = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
					
				synchronized (this) {
					if(left == 0)
					{
					int Left= c_left+v.getSpeed();
					
						if(Left >= 800)
						{
							
							rr.removeView(temp);
							v.getTimer().cancel();
							
						}
						else
						{
					margin1.setMargins(Left,c_top,0,0);
					temp.setLayoutParams(new RelativeLayout.LayoutParams(margin1)); 
					
						}
					}
				}
					
					synchronized (this) {
						if(top==200)
						{
							int Bottom= c_top+v.getSpeed();
							
							if(Bottom >= 550)
							{
								
								rr.removeView(temp);
								v.getTimer().cancel();
									
							}
							else
							{
						margin1.setMargins(c_left,Bottom,0,0);
						temp.setLayoutParams(new RelativeLayout.LayoutParams(margin1)); 
							}
							}
					}
				
				break;
			case MAKING:
				
				Random ran = new Random();
				
				int nansu =0;
				int num = 0;
				
				nansu = return_random(100);
				
				Log.d("난수", nansu+" ");
				
				if(nansu<=70)
				{
					int per = return_random(100)+10;
					
					int answers_size = answer.size();
					
					int random = per % answers_size;
					Log.d("난수 2", (int)answer.get(random)+ " " + random);
					
					if((int)answer.get(random)==97)
						Log.d("a출현", "a");
					
					if(per<40)
					{
						
						if((int)answer.get(0)-97>=0)
						num = (int)answer.get(0)-97;
						else
						{
							currentString += " ";
							answer.remove(0);
							process_voca.setText(currentString);
						}
					}
					else
					{
					if((int)answer.get(random)>=97)
					num = (int)answer.get(random)-97;
					}
					//tv.setText(num+" ");
					
				}
				else
				{
					num = ran.nextInt()%(alpha.size());
					
					if(num<0)
					{
						num = num *-1;
					}	
				}
				
				if(rr.getChildCount()<8)
				making_textView(num);
				
				break;
				
			}
			
			
			
		}
	};
	
	
	
	public Acid_Rain(Context context, RelativeLayout rl, Button back)
	{
		this.context = context;
		this.rl = rl;
		this.back = back;
		
		rl.setBackgroundResource(R.drawable.pink_background);
	}
	
	public void Acid_Rain_initialize()
	{
		rl.removeAllViews();
		
		processing =0;
		score = 100;
		THISvoca = new VocaDTO();
		
		
		currentString = "";
	
		Margins = new ArrayList<ViewGroup.MarginLayoutParams>();
		view2 = new ArrayList<ViewDTO>();
		
		rl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(flag == false)
				{
					InitializeMargins();
					InitializeImages();
					
					period=0;
					VocaDTO voca = new VocaDTO();
					
					voca = Initialize_Voca();
					
					if(exist == true)
					{
					rl.setBackgroundResource(R.drawable.pink_ex);
					rl.removeView(tv);
						
					THISvoca = voca;
					Initialize_Views(voca);
					
					start_timer();
					}
					
				flag= true;
				}
					
			}
		});
		
		String explain = "상단에 뜻이 나와있어요! \n 상단의 뜻에 맞는 알파벳을 순서대로 클릭해 봅시다! \n 저를 클릭해주세요!";
		
		tv = new Button(context);
		tv.setText(explain);
		tv.setBackgroundColor(Color.TRANSPARENT);
		tv.setTextSize(20);
		tv.setTextColor(Color.BLACK);
		tv.setClickable(false);
		
		rl.addView(tv);
		
		ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin.setMargins(80,250,0,0);
		
		tv.setLayoutParams(new RelativeLayout.LayoutParams(margin));
		
		initialize_backbtn();
		
	}
	
	public void initialize_backbtn()
	{
		rl.addView(back);
	

		ViewGroup.MarginLayoutParams back_margin = new ViewGroup.MarginLayoutParams(150,100);
		
		back_margin.setMargins(450,850,300,0);
		
		back.setLayoutParams(new RelativeLayout.LayoutParams(back_margin));
	}


	public void making_textView(int num)
	{
		int flag=0;
		int margins_num = 0;
		int speed=0;
		int move_speed=0;
		ViewDTO view;
		

		Log.d("number",num+" ");
		for(int i=0; i<rr.getChildCount(); i++)
		{
			if(rr.getChildAt(i)!= null)
			{
				if(rr.getChildAt(i).equals(alpha.get(num)))
				{
					Log.d("it is","text"+num);
					//text2.setLayoutParams(new RelativeLayout.LayoutParams(Margins.get(1)));
					//rr.removeView(text2);
					flag =1;
				}
			}
		}
		
		if(flag==0)
		{
			
		alpha.get(num).setTextSize(25);
		rr.addView(alpha.get(num));
		margins_num = return_random(Margins.size());
		Log.d("it is text3",margins_num+" ");
		
		if(margins_num!=0)
			margins_num = margins_num-1;
		
		
		if(view2.size()!=0)
			while(true)
			{
		if(view2.get(view2.size()-1).getX() == Margins.get((margins_num)).leftMargin &&
				view2.get(view2.size()-1).getY() == Margins.get((margins_num)).topMargin 
				)
		{
					margins_num = return_random(Margins.size());
					
					if(margins_num!=0)
						margins_num = margins_num-1;
					
		}
		else
			break;
			}
		
		move_speed = return_random(80)+20;
		alpha.get(num).setBackgroundResource(R.drawable.cloudtn);
		alpha.get(num).setGravity(Gravity.CENTER);
		alpha.get(num).setLayoutParams(new RelativeLayout.LayoutParams(Margins.get((margins_num))));
		Timer tm = new Timer();
		view = new ViewDTO();
		
		view.setX(Margins.get((margins_num)).leftMargin);
		view.setY(Margins.get((margins_num)).topMargin);
		view.setTimer(tm);
		view.setView(alpha.get(num));
		view.setSpeed(move_speed);
		view2.add(view);
		
		Timertask tt = new Timertask(1,view);
		speed = return_random(300)+500;
		
		
		
		tm.scheduleAtFixedRate(tt, 0, speed);
		}
	}
	public int return_random(int kind)
	{
		int num =0;
		Random ran = new Random();
		
		num = ran.nextInt()%kind+1;
		
		if(num<0)
			num = num*-1;
		return num;
	}
	public VocaDTO Initialize_Voca()
	{
	
		answer = new ArrayList<Character>();
		VocaDTO voca = new VocaDTO();
		
		SQLiteDatabase database = context.openOrCreateDatabase("excel.db", context.MODE_WORLD_WRITEABLE, null);
		Database db  = new Database();
		
		if(!db.exist_table(database, "study"))
		{
			tv.setText("공부할 영어 단어가 존재하지 않습니다.");
			
		}
		else if(db.Load_UserData(database).size()==0)
		{

			tv.setText("공부할 영어 단어가 존재하지 않습니다.");
		
		}
		else
		{
			exist = true;
		ArrayList<VocaDTO> Vocas = db.Load_UserData(database);
		int Vocas_select =0 ;
		
		while(true)
		{
		 Vocas_select = return_random(Vocas.size()-1);
		
		 if(Vocas.get(Vocas_select).getEnglish().indexOf(" ")!= -1)
			 continue;
		 else if(Vocas.get(Vocas_select).getEnglish().length()<=10)	
			break;
		
		}
		
		String For_Game_Voca_english = Vocas.get(Vocas_select).getEnglish();
		String For_Game_Voca_korean = Vocas.get(Vocas_select).getKorean();
	
		voca.setKorean(For_Game_Voca_korean);
		voca.setEnglish(For_Game_Voca_english);
		
		for(int i=0; i< For_Game_Voca_english.length(); i++)
		{
			
			answer.add(For_Game_Voca_english.charAt(i));
		}
		}
		return voca;
	}
	
	public void start_timer()
	{

		timer = new Timer();
		 all_timertask = new Timertask(2,null);
				
		timer.scheduleAtFixedRate(all_timertask, 0,1000);
	}
	
	public void Initialize_Views(VocaDTO voca)
	{
	
		ViewGroup.MarginLayoutParams margin_pro = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin_pro.setMargins(300,650,100,0);
		
		process_voca = new Button(context);
		process_voca.setBackgroundColor(Color.TRANSPARENT);
		process_voca.setLayoutParams(new RelativeLayout.LayoutParams(margin_pro));
		process_voca.setClickable(false);

		process_voca.setTextColor(Color.BLACK);
		process_voca.setTextSize(20);
		
	
		
		tv = new TextView(context);
		
		
		pra = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		rr = rl;
		
		rr.addView(process_voca);
		
		tv.setTextSize(20);
		tv.setTextColor(Color.BLACK);
		tv.setTextAlignment(Gravity.CENTER_VERTICAL);
		tv.setText("\n"+voca.getKorean());
		rr.addView(tv,1,pra);
		//tv.setText((int)(voca.getEnglish().charAt(0))+" ");
		
		ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin.setMargins(350,0,100,0);
		tv.setLayoutParams(new RelativeLayout.LayoutParams(margin));
		
		
		
	}
	

	class Timertask extends TimerTask
	{

		int task;
		ViewDTO view;
		int margin;
		
		public Timertask(int task,ViewDTO v)
		{
			this.task = task;
			view = v;
			
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
						
			handler.obtainMessage(task,view).sendToTarget();
			count++;

		}
		
	}
	
	public void thisActivity_end()
	{
		timer.cancel();
		
		for(int i=0; i< view2.size(); i++)
		{
			if(view2.get(i).getTimer()!= null)
			view2.get(i).getTimer().cancel();
		}
		
	}
	
	public void InitializeImages()
	{
		
		alpha = new ArrayList<TextView>();
		text = new TextView(context);
		text.setBackgroundColor(color.holo_blue_bright);
		text.setText("a");
		text.setMinWidth(200);
		text.setHeight(200);
		text.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				select_textview(v, 'a');
				
			}
		});
		alpha.add(text);

		 text2 = new TextView(context);
		text2.setText("b");
		text2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'b');
				
				
			}
		});
		alpha.add(text2);
		text3 = new TextView(context);
		text3.setText("c");
		text3.setMinWidth(200);
		text3.setHeight(200);
		text3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'c');
				
			}
		});
		alpha.add(text3);
		text4 = new TextView(context);
		text4.setText("d");
		
		text4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'd');
			}
		});

		alpha.add(text4);
		 text5 = new TextView(context);
		text5.setText("e");
		alpha.add(text5);
		
		text5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'e');
			}
		});
		
		text6 = new TextView(context);
		text6.setText("f");
		alpha.add(text6);
		text6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'f');
			}
		});
		
		text7 = new TextView(context);
		text7.setText("g");
		alpha.add(text7);
text7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'g');
			}
		});
		
		text8 = new TextView(context);
		text8.setText("h");
		alpha.add(text8);
		
		text8.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'h');
			}
		});
		

		text9 = new TextView(context);
		text9.setText("i");
		alpha.add(text9);
		
		text10 = new TextView(context);
		text10.setText("j");
		alpha.add(text10);
		
		text11 = new TextView(context);
		text11.setText("k");
		alpha.add(text11);
		
		text12 = new TextView(context);
		text12.setText("l");
		alpha.add(text12);
		
		text13 = new TextView(context);
		text13.setText("m");
		alpha.add(text13);
		
		text14 = new TextView(context);
		text14.setText("n");
		alpha.add(text14);
		
		text15 = new TextView(context);
		text15.setText("o");
		alpha.add(text15);
		
		text16 = new TextView(context);
		text16.setText("p");
		alpha.add(text16);
		
		text17 = new TextView(context);
		text17.setText("q");
		alpha.add(text17);
		
		text18 = new TextView(context);
		text18.setText("r");
		alpha.add(text18);
		
		text19 = new TextView(context);
		text19.setText("s");
		alpha.add(text19);
		
		text20 = new TextView(context);
		text20.setText("t");
		alpha.add(text20);
		
		text21 = new TextView(context);
		text21.setText("u");
		alpha.add(text21);
		
		text22 = new TextView(context);
		text22.setText("v");
		alpha.add(text22);
		
		text23 = new TextView(context);
		text23.setText("w");
		alpha.add(text23);
		
		text24 = new TextView(context);
		text24.setText("x");
		alpha.add(text24);
		
		text25 = new TextView(context);
		text25.setText("y");
		alpha.add(text25);
		
		text26 = new TextView(context);
		text26.setText("z");
		alpha.add(text26);
		
		

		text9.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'i');
			}
		});
		
		text10.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'j');
			}
		});

		text11.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'k');
			}
		});

		text12.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'l');
			}
		});

		text13.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'm');
			}
		});

		text14.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'n');
			}
		});

		text15.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'o');
			}
		});

		text16.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'p');
			}
		});

		text17.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'q');
			}
		});

		text18.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'r');
			}
		});

		text19.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 's');
			}
		});

		text20.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 't');
			}
		});

		text21.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'u');
			}
		});

		text22.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'v');
			}
		});

		text23.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'w');
			}
		});

		text24.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'x');
			}
		});

		text25.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'y');
			}
		});

		text26.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select_textview(v, 'z');
			}
		});

	}
	
	public void select_textview(View v, char c)
	{
		TextView temp = (TextView)v;
		
		if(	answer.get(0)==c)
		{	
		currentString += c;
		
		process_voca.setText(currentString);
		
		answer.remove(0);
		
		if(answer.size() == 0)
		{
			thisActivity_end();
			
			print_result();
			Log.d("score",score+"점");	
		}		
		
		rr.removeView(v);
		}
		else
		{
			process_voca.setText("NoNo!");
			score = score -5;
			
			rr.removeView(v);
		}
		
	}
	
	void print_result()
	{
		rr.removeAllViews();
		
		initialize_backbtn();
		
		Button b1 = new Button(context);
		Button b2 = new Button(context);
		
		rr.addView(b1);
		rr.addView(b2);
		
		b2.setRotation(40);
		b2.setBackgroundResource(R.drawable.cloudtn);
		b1.setText(score+"점");
		b1.setTextColor(Color.BLACK);
		b2.setTextColor(Color.BLACK);
		
		b1.setTextSize(20);
		b1.setBackgroundResource(R.drawable.cloudtn);
		b2.setTextSize(15);
		
		ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin.setMargins(370,370,0,0);
		
		b1.setLayoutParams(new RelativeLayout.LayoutParams(margin));
		
		ViewGroup.MarginLayoutParams margin2 = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin2.setMargins(480,200,0,0);
		
		b2.setLayoutParams(new RelativeLayout.LayoutParams(margin2));
		
	
		if(score==100)
		{
			b2.setText("참 잘했어요!");
		}
		if(score>60)
		{
			b2.setText("잘하셨어요!");
		}
		else if(score>30)
		{
			b2.setText("좀 더 정진하세요!");
			
		}
		else
		{
			b2.setText("....??");
		}
	}
	
	
	public void InitializeMargins()
	{
		
		ViewGroup.MarginLayoutParams margin1 = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
	
		margin1.setMargins(250,200,0,0);
		
		Margins.add(margin1);
		ViewGroup.MarginLayoutParams margin2 = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin2.setMargins(500, 200, 0, 0);
		
		Margins.add(margin2);
		
		
		ViewGroup.MarginLayoutParams margin3 = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin3.setMargins(750, 200, 0, 0);
	
		Margins.add(margin3);
		
/*
		ViewGroup.MarginLayoutParams margin4 = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin4.setMargins(250, 0, 0, 0);
	
		Margins.add(margin4);
		
		ViewGroup.MarginLayoutParams margin5 = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin5.setMargins(0, 550, 0, 0);
	
		Margins.add(margin5);
		*/
	
		
		
	}
	
	
}
