package com.AutomationTesting.testcases;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.AutomationTesting.Keywords;
import com.AutomationTesting.util.DataUtil;
import com.AutomationTesting.util.ExtentManager;
import com.AutomationTesting.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GmailTest {
	Xls_Reader reader=new Xls_Reader("E:\\Sele Framework\\AutomationTesting\\TestData\\SuiteA.xlsx");
	String testName="GmailTest";
	ExtentReports ex;
	ExtentTest test;

	@Test(dataProvider="getdata")
	public void testing(Hashtable<String,String> data) throws IOException, InterruptedException, AWTException{
		
		ex=ExtentManager.getInstance();
		test=ex.startTest(testName);
		test.log(LogStatus.INFO, "Starting test"+testName);
		
		if(DataUtil.isSkip(reader, testName) || data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test because runmode N");
			throw new SkipException("Skipping the test");
		}
		
		Keywords app=new Keywords();
		app.test1(reader,testName,test,data);
	}	
		
	@AfterTest
	public void runeverytest(){
		if(ex!=null){
		ex.endTest(test);
		ex.flush();
		}
	}
		
	
	@DataProvider
	public Object[][] getdata(){
		return DataUtil.getdata(reader, testName);
	}
		
	
}
