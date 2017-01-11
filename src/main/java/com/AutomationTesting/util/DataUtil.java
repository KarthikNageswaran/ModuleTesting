package com.AutomationTesting.util;

import java.util.Hashtable;

public class DataUtil {

	public static Object[][] getdata(Xls_Reader reader,String testName) {

		String sheetName="Data";
		
		int row=1;
		while(!reader.getCellData(sheetName, 0, row).equals(testName)){
			row++;
		}
		System.out.println(row);
		
		int colstarts=row+1;
		int rowDatastarts=row+2;
		
		int rnum=0;
		while(!reader.getCellData(sheetName, 0, rowDatastarts+rnum).equals("")){
			rnum++;
		}System.out.println("Data rows"+rnum);
		
		int cnum=0;
		while(!reader.getCellData(sheetName, cnum, colstarts).equals("")){
			cnum++;
		}System.out.println("columns"+cnum);
		
		Object[][] data=new Object[rnum][1];
		int rowinc=0;
		Hashtable<String,String> table=null; 
		System.out.println("Printing total data:");
		for(int i=rowDatastarts;i<rowDatastarts+rnum;i++){
			table=new Hashtable<String,String>();
			for(int j=0;j<cnum;j++){
				String Key=reader.getCellData(sheetName, j, colstarts);
				String Value=reader.getCellData(sheetName, j, i);
				//data[rowinc][j]=reader.getCellData(sheetName, j, i);
				table.put(Key, Value);
			}
			data[rowinc][0]=table;
			rowinc++;
		}
		return data;	
	}
	
	public static boolean isSkip(Xls_Reader reader,String TestunderExecution){
		int rows=reader.getRowCount("Testcases");
		
		for(int rnum=2;rnum<rows;rnum++){
			if(reader.getCellData("Testcases", "TCID", rnum).equals(TestunderExecution)){
				if(reader.getCellData("Testcases", "Runmode", rnum).equals("Y")){
					return false;
				}else{
					return true;
				}
				
			}
		}
		return true;
		
	}


}
