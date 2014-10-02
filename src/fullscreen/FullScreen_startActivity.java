package fullscreen;

import java.util.ArrayList;

import com.example.kk_excel.R;
import com.example.kk_excel.R.id;
import com.example.kk_excel.R.layout;

import adapter.StudyAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class FullScreen_startActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullscreen_start);
		
		ArrayList<String> days = new ArrayList<String>();
	
		
		for(int i=1; i<=20; i++)
		{
			days.add(i+"ÀÏÂ÷");
		}
		
		StudyAdapter sa = new StudyAdapter(this, days);
		ListView lv = (ListView)findViewById(R.id.days_listView1);
		
		lv.setAdapter(sa);
		
		Button b1 =(Button)findViewById(R.id.exit);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				moveTaskToBack(true);
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
	}
}
