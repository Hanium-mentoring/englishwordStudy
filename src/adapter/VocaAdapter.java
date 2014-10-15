package adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.qcircle.kk_excel.R;

import dto.VocaDTO;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class VocaAdapter extends ArrayAdapter<VocaDTO>{
	Activity context;
	ArrayList<VocaDTO> array;
	public ArrayList<VocaDTO> selected;
	ArrayList<Integer> flags;
	private LayoutInflater _inflater;   
	CheckBox photo;
	int position;
    private int _layout;   

    /*
    public VocaAdapter(Context context, int layout,ArrayList<VocaDAO> list) {   
    	_inflater = (LayoutInflater)context.getSystemService(   
      Context.LAYOUT_INFLATER_SERVICE);   
    	this.context = context;
    	array = list;
    	position =0;
    	_layout = layout;
    	selected = new ArrayList<VocaDAO>();
    	flags = new ArrayList<Integer>();
    	
    } */  

    static class ViewHolder{
    	protected TextView name;
    	protected TextView name2;
    	protected CheckBox check;
    }
    
	public VocaAdapter(Activity context,ArrayList<VocaDTO> array)
	{
		super(context, R.layout.myadapter, array);
		this.context = context;
		this.array = array;
		selected = new ArrayList<VocaDTO>();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	/*
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return array.get(arg0);
	}*/

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
       
		ViewHolder view = null;
		
		if(convertView == null)
		{
			LayoutInflater inflator = context.getLayoutInflater();
			convertView = inflator.inflate(R.layout.myadapter, null);
			view = new ViewHolder();
			view.name = (TextView)convertView.findViewById(R.id.english);
			view.name2 = (TextView)convertView.findViewById(R.id.korean);
			view.check = (CheckBox)convertView.findViewById(R.id.checkBox1);
			view.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					int pos = (Integer)buttonView.getTag();
					array.get(pos).setChecked(buttonView.isChecked());
					
					if(isChecked)
					{
						selected.add(array.get(pos));
					}
					else
					{
						for(int i=0; i<selected.size(); i++)
						{
							if(selected.get(i).getEnglish().equals(array.get(pos).getEnglish()))
							{
								selected.remove(i);
								return;
							}
						}
					}
				}
			});
			
			convertView.setTag(view);
			convertView.setTag(R.id.english, view.name);
			convertView.setTag(R.id.korean, view.name2);
			convertView.setTag(R.id.checkBox1, view.check);
			
			
		}
		else
		{
			view = (ViewHolder)convertView.getTag();
		}
		
		view.check.setTag(arg0);
		
		view.name.setText(array.get(arg0).getEnglish());
		view.name2.setText(array.get(arg0).getKorean());
		view.check.setChecked(array.get(arg0).isChecked());
		return convertView;
		
		
	
	}
	
	public ArrayList<VocaDTO> return_Data()
	{
		return selected;
	}
}
