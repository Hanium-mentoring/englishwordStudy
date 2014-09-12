package database;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import dto.VocaDTO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {
	
	
	public void CreateTable(SQLiteDatabase db)
	{
		String query = "create table if not exists study (_id integer primary key autoincrement,english text,korean text)";
		
		db.execSQL(query);
	}
	
	public void add_UserData(SQLiteDatabase db,ArrayList<VocaDTO> list)
	{
		String english, korean;
		
	
		for(int i=0; i< list.size(); i++)
		{
			english = list.get(i).getEnglish();
			korean = list.get(i).getKorean();
		
			
				StringBuffer query = new StringBuffer("insert or ignore into study values (null,'");
			
					query.append(english+"','"+korean+"')");
					db.execSQL(query.toString());
		
		}
		
	}
	
	public ArrayList<VocaDTO> Load_UserData(SQLiteDatabase db)
	{
		ArrayList<VocaDTO> list = new ArrayList<VocaDTO>();
		
		String confirm = "select count(*) from sqlite_master where name='study'";
		Cursor c = db.rawQuery(confirm, null);
		c.moveToFirst();
		
		if(c.getInt(0)==0)
			return null;
		
		String query = "select * from study";
		Cursor cur = db.rawQuery(query, null);
		
		int count = cur.getCount();
		
		cur.moveToFirst();
		
		for(int i=0; i<count; i++)
		{
			VocaDTO voca = new VocaDTO();
			voca.setEnglish(cur.getString(1).trim());
			voca.setKorean(cur.getString(2).trim());
			list.add(voca);
			
			if(!cur.moveToNext())break;
			
			
		}
		return list;
	}
	
	public boolean exist_table(SQLiteDatabase db, String db_name)
	{
		String query = "select name from sqlite_master where type='table'";
		Cursor  cur = db.rawQuery(query, null);
		
		int count = cur.getCount();
		
		cur.moveToFirst();
		
		for(int i=0; i<count; i++)
		{
			
			
			if(cur.getString(0).equals(db_name))
				return true;
			
			if(!cur.moveToNext())break;
			
			
		}
		return false;
	}
	
	public void delete_table(SQLiteDatabase db)
	{
		String query = "delete from study";
		
		db.execSQL(query);
	}
}
