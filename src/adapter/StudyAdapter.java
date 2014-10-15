package adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.qcircle.kk_excel.R;

import database.Database;
import dto.VocaDTO;
import fullscreen.MainActivity;
import fullscreen.ReadExcel;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class StudyAdapter extends ArrayAdapter<String>{
	Activity context;
	ArrayList<String> array;
	ArrayList<Integer> flags;
	private LayoutInflater _inflater;   
	CheckBox photo;
	int position;
    private int _layout;   

 
    static class Holder{
    	protected TextView name;
    	protected Button b1;
    	protected Button b2;
    }
    
	public StudyAdapter(Activity context,ArrayList<String> array)
	{
		super(context, R.layout.days_adapter, array);
		this.context = context;
		this.array = array;
	
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
       
		Holder view = null;
		
		if(convertView == null)
		{
			LayoutInflater inflator = context.getLayoutInflater();
			convertView = inflator.inflate(R.layout.days_adapter, null);
			view = new Holder();
			view.name = (TextView)convertView.findViewById(R.id.textView1);
			view.name.setTextSize(20);
			view.b1 = (Button)convertView.findViewById(R.id.button1);
			view.b2 = (Button)convertView.findViewById(R.id.button2);
		
			view.b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SQLiteDatabase database = context.openOrCreateDatabase("excel.db", context.MODE_WORLD_WRITEABLE, null);
					
					Database db = new Database();
					InputStream is = null;
					try {
						is = context.getResources().getAssets().open("Book1.xls");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					ReadExcel re = new ReadExcel(is);

					ArrayList<VocaDTO> list = re.Return_Data((((Integer)v.getTag())));
					
					db.CreateTable(database);
				
					if(db.Load_UserData(database)!= null)
						db.delete_table(database);
					
					db.add_UserData(database, list);
					
				
					Toast.makeText(context, "단어가 추가되었습니다. 케이스를 닫아주세요!", Toast.LENGTH_LONG).show();
					
					
				}
			});
			
		
			view.b2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int pos = (Integer)v.getTag();
					Log.d("pos", pos+" ");
					
					Intent in = new Intent(context,MainActivity.class);
					in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					in.putExtra("day", pos);
					context.startActivity(in);
					
				}
			});
			convertView.setTag(view);
			convertView.setTag(R.id.textView1, view.name);
			convertView.setTag(R.id.button1, view.b1);
			convertView.setTag(R.id.button2, view.b2);
			
			
		}
		else
		{
			view = (Holder)convertView.getTag();
		}
		
		view.b1.setTag(arg0);
		view.b2.setTag(arg0);
		
		view.name.setText(array.get(arg0));
		view.b1.setText("공부하기");
		view.b2.setText("세부설정");
		return convertView;
		
	}
	
	
}
