package fullscreen;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import dto.VocaDTO;

import android.content.Context;
import jxl.*;
import jxl.read.biff.BiffException;

public class ReadExcel {

	InputStream is;
	public ReadExcel(InputStream is)
	{
		this.is = is;
	}
	
	public ArrayList<VocaDTO> Return_Data(int number)
	{
		Workbook workbook = null;
		Sheet sheet[] = null;
		Sheet sheets = null;
		int count =0;
		int i=0;
		String data = "";
		ArrayList<VocaDTO> array = new ArrayList<VocaDTO>();
		try {
			workbook = Workbook.getWorkbook(is);
			sheets = workbook.getSheet(0);
			
			for(i=number; i<(number+20) ;i++)
			{
				VocaDTO voca = new VocaDTO();
				
					data  = sheets.getCell(1, i).getContents();
					voca.setChecked(false);
					voca.setEnglish(data);
					data = "";
					data  = new String(sheets.getCell(2, i).getContents());
					voca.setKorean(data);
					
					array.add(voca);
			}
			
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return array;
	}
}
