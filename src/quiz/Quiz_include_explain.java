package quiz;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk_excel.R;

import database.Database;
import dto.VocaDTO;

public class Quiz_include_explain {
	
	Context context;
	RelativeLayout rl;
	Button back;
	ArrayList<VocaDTO> list;
	boolean flagP = false;
	boolean flag = false;
	TextView tv;
	boolean flags = false;
	int voca_count =0;
	Button example1,example2,example3,example4,word;
	private ArrayList<VocaDTO> wordset;
	private ArrayList<VocaDTO> quizset;
	private int quiznumber = 0;
	private int point = 0;
	String answer;
	int version = 0;
	
	VocaDTO quiz;
	
	Random random = new Random();
	
	
	public Quiz_include_explain(Context context,RelativeLayout rl,Button back)
	{

		this.context = context;
		this.rl = rl;
		this.back = back;
		
		
	}
	
OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Button clicked = (Button) v;
			if (clicked.getText() == answer) {
				point++;
			}
		
			quiz = makingquiz(list);
			quiznumber++;
			showQuiz();
			
			tv.setText("맞힌개수 : "+point);
			
			if(quiznumber>4){		
						
				print_result();
				//문제 갯수 정하고  화면 바뀌기 
			}
			
		}
	};
	
	public void showQuiz() {
		version = random.nextInt(2);
		//quiznumber++;

		if(flag==false)
		{
		if (version == 0) {
			word.setText(quiz.getKorean());
			flag = true;
			answer = quiz.getEnglish();
			
			example1.setText(getexample().getEnglish());
			example2.setText(getexample().getEnglish());
			example3.setText(getexample().getEnglish());
			example4.setText(getexample().getEnglish());
			
			example1.setVisibility(View.INVISIBLE);
			example2.setVisibility(View.INVISIBLE);
			
			example3.setVisibility(View.INVISIBLE);
			example4.setVisibility(View.INVISIBLE);
			
			
			
			
			
		} else {
			word.setText(quiz.getEnglish());
			
			flag = true;
			answer = quiz.getKorean();
			
			example1.setText(getexample().getKorean());
			example2.setText(getexample().getKorean());
			example3.setText(getexample().getKorean());
			example4.setText(getexample().getKorean());
			
			example1.setVisibility(View.INVISIBLE);
			example2.setVisibility(View.INVISIBLE);
			
			example3.setVisibility(View.INVISIBLE);
			example4.setVisibility(View.INVISIBLE);
			
			
		}
		}
	}
	
	void print_result()
	{
		rl.setBackgroundResource(R.drawable.fishb);
		rl.removeView(example1);
		rl.removeView(example2);
		rl.removeView(example3);
		rl.removeView(example4);
		rl.removeView(word);
		
		ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin.setMargins(100,240,0,0);
		
		tv.setText("게임이 끝났습니다. 맞춘개수는 : "+point+"입니다.");
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
			flags =true;
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
					flags = true;
				}	
			
		}
	}

	public void q_explain_initialize()
	{
		
		rl.removeAllViews();
		list = new ArrayList<VocaDTO>();
		quizset = new ArrayList<VocaDTO>();
		
		rl.setBackgroundResource(R.drawable.fishb);
		
		rl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(flagP == false)
				{
					tv.setText("click");
					initializeDB();
					
					initialize_view();
					//initializeButton();
					flagP = true;
				}
				else if(flag == true)
				{
						flag = false;
						
						example1.setVisibility(View.VISIBLE);
						example2.setVisibility(View.VISIBLE);
							
						example3.setVisibility(View.VISIBLE);
						example4.setVisibility(View.VISIBLE);
						
				}
		
				
				}
			
		});
		
		String explain = "5개의 문제 중, 문제를 누르신 후 나타난 4개의 답 중에 옳은 답을 선택해 주세요 .";
		
		tv = new TextView(context);
		tv.setText(explain);
		tv.setTextSize(20);
		
		ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		margin.setMargins(150,200,0,0);
		ViewGroup.MarginLayoutParams margin4 = new ViewGroup.MarginLayoutParams(150,100);
		
		margin4.setMargins(400,850,300,0);
		
		
		rl.addView(tv);
		rl.addView(back);
		
		tv.setLayoutParams(new RelativeLayout.LayoutParams(margin));
		back.setLayoutParams(new RelativeLayout.LayoutParams(margin4));
		
	}
	
void initialize_view()
{
	
	example1 = new Button(context);
	example2 = new Button(context);
	example3 = new Button(context);
	example4 = new Button(context);
	word = new Button(context);
	rl.addView(word);
	rl.addView(example1);
	rl.addView(example2);
	rl.addView(example3);
	rl.addView(example4);
	
	
	word.setTextSize(20);
	word.setTextColor(Color.BLACK);
	
	word.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(flag == true)
			{
				flag = false;
				
				
					example1.setVisibility(View.VISIBLE);
					example2.setVisibility(View.VISIBLE);
					
					example3.setVisibility(View.VISIBLE);
					example4.setVisibility(View.VISIBLE);
					
				
			}
			else
			{
				flag = true;
				
				example1.setVisibility(View.INVISIBLE);
				example2.setVisibility(View.INVISIBLE);
					
				example3.setVisibility(View.INVISIBLE);
				example4.setVisibility(View.INVISIBLE);
				
			}
		}
	});
	

	if(flags == false)
	{
	quiz = makingquiz(list);
	//quiznumber++;
	showQuiz();
	}
	
	ViewGroup.MarginLayoutParams margin_c = new ViewGroup.MarginLayoutParams(700,400);
	
	margin_c.setMargins(300, 30, 0, 0);

	tv.setText("맞힌 개수 : 0");
	tv.setTextSize(20);
	tv.setTextColor(Color.BLACK);
	tv.setLayoutParams(new RelativeLayout.LayoutParams(margin_c));
	
	ViewGroup.MarginLayoutParams margin_voca = new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	
	margin_voca.setMargins(310, 400, 300, 0);

	word.setBackgroundColor(Color.TRANSPARENT);
	//word.setBackgroundResource(R.drawable.bubble);
	word.setLayoutParams(new RelativeLayout.LayoutParams(margin_voca));
	
	ViewGroup.MarginLayoutParams margin1 = new ViewGroup.MarginLayoutParams(370,300);
	
	margin1.setMargins(0, 200, 0, 0);
	example1.setBackgroundResource(R.drawable.fish1);
	example1.setLayoutParams(new RelativeLayout.LayoutParams(margin1));
	


	ViewGroup.MarginLayoutParams margin2 = new ViewGroup.MarginLayoutParams(370,270);
	
	margin2.setMargins(650, 200, 0, 0);

	example2.setBackgroundResource(R.drawable.fish2);
	example2.setLayoutParams(new RelativeLayout.LayoutParams(margin2));
	
	ViewGroup.MarginLayoutParams margin3 = new ViewGroup.MarginLayoutParams(370,300);
	
	margin3.setMargins(0, 570, 0, 0);

	example3.setBackgroundResource(R.drawable.fish3);
	example3.setLayoutParams(new RelativeLayout.LayoutParams(margin3));
	
	ViewGroup.MarginLayoutParams margin4 = new ViewGroup.MarginLayoutParams(370,300);
	
	margin4.setMargins(650, 570, 0, 0);

	example4.setBackgroundResource(R.drawable.fish4);
	example4.setLayoutParams(new RelativeLayout.LayoutParams(margin4));
	
	example1.setTextSize(15);
	example2.setTextSize(15);
	example2.setTextColor(Color.BLACK);
	example3.setTextSize(15);
	example4.setTextSize(15);
	
	example1.setOnClickListener(listener);
	
	example2.setOnClickListener(listener);
	example3.setOnClickListener(listener);
	example4.setOnClickListener(listener);
	
	
}

public boolean contains(ArrayList<VocaDTO> l, VocaDTO a) {
	for (int i = 0; i < l.size()-1; i++) {
		if (a.getEnglish() == l.get(i).getEnglish())
			return true;
	}
	return false;
}

public VocaDTO makingquiz(ArrayList<VocaDTO> list) {
	VocaDTO q;

	while (true) {
		q = get(list);
		if (!contains(quizset, q))
			break;
	}

	quizset.add(q);

	wordset = new ArrayList<VocaDTO>();
	wordset.add(q);

	for (int i = 0; i < 3; i++) {
		makingexample(list);
	}

	return q;

}

public VocaDTO get(ArrayList<VocaDTO> list) {
	return (VocaDTO) list.get(random.nextInt(list.size()));
}

public void makingexample(ArrayList<VocaDTO> list) {
	VocaDTO q;

	while (true) {
		q = get(list);
		if (!contains(wordset, q))
			break;
	}

	wordset.add(q);
}

public VocaDTO getexample() {
	VocaDTO q;
	int key = random.nextInt(wordset.size());

	q = wordset.get(key);
	wordset.remove(key);

	return q;
}


}
