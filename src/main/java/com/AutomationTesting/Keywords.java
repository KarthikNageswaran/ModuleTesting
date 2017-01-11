package com.AutomationTesting;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Hashtable;

import com.AutomationTesting.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;

public class Keywords {
	
	public void test1(
			Xls_Reader reader,
			String testName,
			ExtentTest test, Hashtable<String, String> table) throws IOException, InterruptedException, AWTException{
		
		GenericKeywords app=new GenericKeywords(test);	
		int rowCount=reader.getRowCount("Keywords");
		
		for(int rNum=2;rNum<=rowCount;rNum++){
			
			String TCID=reader.getCellData("Keywords", "TCID", rNum);
			if(TCID.equals(testName)){
				String Keyword=reader.getCellData("Keywords", "Keyword", rNum);
				String Object=reader.getCellData("Keywords", "Object", rNum);
				String testData=reader.getCellData("Keywords", "Data", rNum);
				String Data=table.get(testData);
				
				if(Keyword.equals("openBrowser"))
					app.openBrowser(Data);
				if(Keyword.equals("navigate"))
					app.navigate(Object);
				if(Keyword.equals("click"))
					app.click(Object);
				if(Keyword.equals("input"))
					app.input(Object,Data);
				if(Keyword.equals("clickonInfocenter"))
					app.clickonInfocenter(testData);
				if(Keyword.equals("clickonsubinfocenter"))
					app.clickonsubinfocenter(testData);
				if(Keyword.equals("clickonInfoportbutton"))
					app.clickonInfoportbutton(testData);				
				if(Keyword.equals("invoke"))
					app.invoke(testData);
				if(Keyword.equals("enterValueInTextbox"))
					app.enterValueInTextbox(Object,Data);
				if(Keyword.equals("enterValueInRTF"))
					app.enterValueInRTF(Object,Data);
				if(Keyword.equals("selectValueInListBox"))
					app.selectValueInListBox(Object,Data);
				if(Keyword.equals("multiSelectValues"))
					app.multiSelectValues(Object,Data);
				if(Keyword.equals("login"))
					app.login(Data);
				if(Keyword.equals("attachFile"))
					app.attachFile(testData);		
				if(Keyword.equals("singleSelectValue"))
					app.singleSelectValue(Object,Data);
				if(Keyword.equals("clickMenuOnButton"))
					app.clickMenuOnButton(Object);
				if(Keyword.equals("EntervalueInsubmittextarea"))
					app.EntervalueInsubmittextarea(Object,Data);
				if(Keyword.equals("clickOnPopupButton"))
					app.clickOnPopupButton(Object);
				if(Keyword.equals("closebrowser"))
					app.closebrowser();
				
				
				
				
			}
			
		}
		
		

//		app.openBrowser("firefox");
//		app.navigate("http://google.com");
//		app.click("gmail_link_xpath");
//		
//		app.input("username_textbox_id", "karthick.information@gmail.com");
//		app.click("next_button_id");
//		
		
		
		
	}
	
}
