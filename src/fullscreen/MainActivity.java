package fullscreen;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.qcircle.kk_excel.R;
import com.qcircle.kk_excel.R.id;
import com.qcircle.kk_excel.R.layout;

import database.Database;
import dto.VocaDTO;

import adapter.VocaAdapter;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity implements View.OnClickListener{

	Button b1, b2, b3,b4,b5;
	ArrayAdapter<VocaDTO> adapter;
	ArrayList<VocaDTO> list;
	ArrayList<VocaDTO> select;
	ArrayAdapter<VocaDTO> aa;
	InputStream is;
	ListView lv;
	ReadExcel re;
	SQLiteDatabase database;
	int number = 0;
	final String db_name = "excel.db";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = this.getIntent();
		int pos = intent.getIntExtra("day", 0);
		
		Log.d("pos", pos+"위치");
		select = new ArrayList<VocaDTO>();
		
		re= null;
	
		list= new ArrayList<VocaDTO>();
		lv = (ListView)findViewById(R.id.listView1);
		
		try {
			is = getBaseContext().getResources().getAssets().open("Book1.xls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		re = new ReadExcel(is);
		
		list = re.Return_Data(pos);
		
		
	
		b4 = (Button)findViewById(R.id.button4);
		b5 = (Button)findViewById(R.id.button5);
		
		
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		
		
		
		aa = new VocaAdapter(this,list);
		
		lv.setAdapter(aa);
	
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		is = null;
		database = openOrCreateDatabase(db_name, MODE_WORLD_WRITEABLE, null);
		
		Database db = new Database();
		
		
		switch(v.getId())
		{
		/*
		case R.id.button1:
		{
			 
				try {
					is = getBaseContext().getResources().getAssets().open("Book1.xls");
					re = new ReadExcel(is);
					
					list = re.Return_Data(0);
			 
					aa = new VocaAdapter(this,list);
					
					lv.setAdapter(aa);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
			
			 break;
				
		}
		
		case R.id.button2:
		{
			 try {
					is = getBaseContext().getResources().getAssets().open("Book1.xls");
				
					re = new ReadExcel(is);
					
					list = re.Return_Data(30);
			 
					aa = new VocaAdapter(this, list);
					
					lv.setAdapter(aa);
			 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 break;
					
		}
		
		case R.id.button3:
			
			if(db.Load_UserData(database)!= null)
			db.delete_table(database);
			
			Intent in = new Intent(this,FullScreen_startActivity.class);
			startActivity(in);
			/*
			 try {
					is = getBaseContext().getResources().getAssets().open("words3.xls");
				
					re = new ReadExcel(is);
					
					list = re.Return_Data();
			 
					aa = new VocaAdapter(this,list);
					
					lv.setAdapter(aa);
			 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			
			select = db.Load_UserData(database);
			
			tv.setText(select.get(0).getKorean());
			 break;
		*/
		
		case R.id.button4:
			
		
			VocaAdapter adapter =  (VocaAdapter)aa;
			
			
			
			
			
				
				
			db.CreateTable(database);
		
			
			if(db.Load_UserData(database)!= null)
				db.delete_table(database);
			
			
			if(adapter.selected.size() !=0)
			{
				db.add_UserData(database, adapter.selected);
				Toast.makeText(getApplicationContext(), "단어가 추가되었습니다. 케이스를  닫아주세요!", Toast.LENGTH_LONG).show();
						
			}
			else
				Toast.makeText(getApplicationContext(), "단어를 선택해주세요!", Toast.LENGTH_LONG).show();
			
			
			
			break;

		case R.id.button5:
		
			moveTaskToBack(true);
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			
			break;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
	
		super.onDestroy();
	}


}
