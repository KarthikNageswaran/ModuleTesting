package com.AutomationTesting;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.AutomationTesting.util.constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;




@SuppressWarnings("unused")
public class GenericKeywords {
	public WebDriver driver;
	public Properties prop;
	public ExtentTest test;
	Actions act;
	public GenericKeywords(ExtentTest test) throws IOException{
		this.test=test;
		prop=new Properties();
		FileInputStream fs=new FileInputStream("E:\\Sele Framework\\AutomationTesting\\src\\test\\resources\\Project.properties");
		prop.load(fs);
	}
	
	public void openBrowser(String browserName){
		test.log(LogStatus.INFO, "Opening browser --- "+browserName);
		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\knageswaran\\Downloads\\chromedriver_win32 (2)\\chromedriver.exe");
			driver=new ChromeDriver();
		}else if(browserName.equals("ie")){
			System.setProperty("webdriver.ie.driver", "C:\\Users\\knageswaran\\Downloads\\IEDriverServer_x64_2.53.1\\IEDriverServer.exe");
			driver=new InternetExplorerDriver();
		}else if(browserName.equals("firefox")){
			System.setProperty("webdriver.firefox.marionette", "C:\\Users\\knageswaran\\Downloads\\geckodriver-v0.11.1-win32\\geckodriver.exe");
			driver=new FirefoxDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	public void navigate(String url){
		test.log(LogStatus.INFO, "Navigating to-- "+prop.getProperty(url));
		driver.get(prop.getProperty(url));
	}
	
	public void click(String locator) throws IOException{
		test.log(LogStatus.INFO, "Clicking -- "+locator);
		getElement(locator).click();
	}
	
	public void input(String locator,String value) throws IOException{
		test.log(LogStatus.INFO, "Entering value -- "+locator+"--"+value);
		getElement(locator).sendKeys(value);
	}
	
	public WebElement getElement(String locator) throws IOException{
		WebElement e=null;
		try{
		if(locator.endsWith("_id"))
			e=driver.findElement(By.id(prop.getProperty(locator)));
		else if(locator.endsWith("_name"))
			e=driver.findElement(By.name(prop.getProperty(locator)));
		else if(locator.endsWith("_xpath"))
			e=driver.findElement(By.xpath(prop.getProperty(locator)));
		}catch(Exception e1){
		
			test.log(LogStatus.FAIL, "Extraction of element failed");
			reportFailure("Unable to locate the element");
			Assert.fail("Unable to locate the element");
			
		}
		
	return e;
	}
	
	public static void wait(String seconds) throws InterruptedException{
		int sec=Integer.parseInt(seconds);
		Thread.sleep(6000L);
	}	
	
/********************* Reporting function *******************************/
	public void takescreenshot() throws IOException{
		Date d=new Date();
		String screeshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		String path=constants.reportpath+"screenshots"+screeshotFile;
		
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try{
			FileUtils.copyFile(srcFile, new File(path));
		}catch(IOException e){
			e.printStackTrace();
		}
		//embed screenshot in report
		test.log(LogStatus.INFO, test.addScreenCapture(path));
		}
	
	public void reportFailure(String message) throws IOException{
		takescreenshot();
		test.log(LogStatus.FAIL, message);
	}
	
/************************** Apps Specific Keywords **************************/
	
	public void login(String Data) throws IOException, InterruptedException{
		
		String details[]=Data.split("/");
		String username=details[0];
		String password=details[1];
		getElement("username_textbox_xpath").sendKeys(username);
		Thread.sleep(1000L);
		getElement("password_textbox_xpath").sendKeys(password);
		Thread.sleep(1000L);
		getElement("signin_button_xpath").click();
	}
	
	
	
	public void clickonInfocenter(String testData) throws IOException, InterruptedException{
		act=new Actions(driver);
		Thread.sleep(5000L);
		WebElement ele=driver.findElement(By.id("dropdownOpener"));
				//getElement(prop.getProperty("home_button_id"));
		
		act.moveToElement(ele).build().perform();
		wait("5000");
		driver.findElement(By.linkText(testData)).click();
		
	}
	
	public void clickonsubinfocenter(String testData) throws IOException, InterruptedException, AWTException{
		test.log(LogStatus.INFO, "CLicking on Subinfocenter --- "+testData);
		String xpath1="//*[@id='navbar-nav-header']/li[";
		String xpath2="]";
		List<WebElement> elements=driver.findElements(By.xpath("//*[@id='navbar-nav-header']/li"));
		for(int i=2;i<elements.size()-1;i++){
			String displaytext=driver.findElement(By.xpath(xpath1+i+xpath2)).getText();
			if(displaytext.equals(testData)){
				driver.findElement(By.xpath(xpath1+i+xpath2)).click();
				Thread.sleep(6000L);
				break;
			}
			if(!displaytext.equals(testData)){
				WebElement element=driver.findElement(By.xpath("//*[@class='btn-group more-options']/a"));
				act.moveToElement(element).build().perform(); 
//				Point cord=element.getLocation();
//				int x=cord.getX();
//				int y=cord.getY();
//				//act.moveByOffset(x, y).build().perform();
//				cord.move(x, y);
				Thread.sleep(6000L);
				testData=testData.trim();
				driver.findElement(By.linkText(testData)).click();
				//driver.findElement(By.xpath("//*[@id='100047']")).click();
				WebElement ele=driver.findElement(By.xpath("//*[@id='infocenter-actions']/button"));
				act.moveToElement(ele).build().perform();
				break;
			}
		}
	}
	
	public void clickonInfoportbutton(String testData) throws IOException, InterruptedException{
		Thread.sleep(4000L);
		driver.findElement(By.xpath("//*[@id='btn-FormsLink']")).click();
	}
	
	public void invoke(String testData) throws InterruptedException{
		Thread.sleep(4000L);
		driver.findElement(By.linkText(testData)).click();
		Thread.sleep(6000L);
	}
	
	public void enterValueInTextbox(String Object,String Data) throws IOException{
		test.log(LogStatus.INFO, "Entering value in textbox ---"+Object+"---Data--"+Data);
		getElement(Object).sendKeys(Data);
	}
	
	public void enterValueInRTF(String Object,String Data) throws IOException, InterruptedException{
		if(!Data.equals("")){
			getElement(Object).click();
			wait("1000");
			driver.switchTo().frame(driver.findElement(By.id("mce_0_ifr")));
			//getElement("rtf_body_xpath").sendKeys(Data);
			driver.findElement(By.id("tinymce")).sendKeys(Data);
			driver.switchTo().defaultContent();
			getElement("save_button_xpath").click();
			wait("1000");
			getElement("close_button_xpath").click();
			wait("1000");
		}else{
			test.log(LogStatus.INFO, "Description data is missing");
		}
		
	}
	
	public void selectValueInListBox(String Object,String Data) throws IOException, InterruptedException{
		test.log(LogStatus.INFO, "Select value in displaying list ---"+Object+"---Data--"+Data);
		if(!Data.equals("")){
			getElement(Object).click();
			wait("1000");
			List<WebElement> allnames=driver.findElements(By.xpath(prop.getProperty("dropbox_values_xpath")));
			for(int i=0;i<allnames.size();i++){
				if(Data.equals(allnames.get(i).getText())){
					//driver.findElement(By.linkText(Data)).click();
					allnames.get(i).click();
					break;
				}
			}
		}
	}
		
	public void multiSelectValues(String Object,String Data) throws IOException, InterruptedException{
		
		getElement(Object).click();
		wait("1000");
		if(!Data.equals("")){
			if(Data.contains(",")){
			String values[]=Data.split(",");
				for(int i=0;i<values.length;i++){
					getElement("mlvo_search_textbox_xpath").sendKeys(values[i]);
					getElement("mlvo_search_textbox_xpath").sendKeys(Keys.ENTER);
					wait("1000");
					getElement("mlov_selectvalue_checkbox_xpath").click();
					getElement("mlvo_search_textbox_xpath").clear();
					}
			getElement("mlov_done_button_xpath").click();
			wait("1000");
			}else{
					getElement("mlvo_search_textbox_xpath").sendKeys(Data);
					getElement("mlvo_search_textbox_xpath").sendKeys(Keys.ENTER);
					wait("1000");
					getElement("mlov_selectvalue_checkbox_xpath").click();
					getElement("mlov_done_button_xpath").click();
					wait("1000");
				}
		}else{
			test.log(LogStatus.INFO, "No data found");
		}
				
	}
	
	public void singleSelectValue(String Object,String Data) throws IOException, InterruptedException{
		if(!Data.equals("")){
			getElement(Object).click();
			wait("1000");
			getElement("mlvo_search_textbox_xpath").sendKeys(Data);
			getElement("mlvo_search_textbox_xpath").sendKeys(Keys.ENTER);
			wait("1000");
			getElement("mlov_selectvalue_checkbox_xpath").click();
			getElement("mlov_done_button_xpath").click();
			wait("1000");
		}else
		{
			test.log(LogStatus.INFO, "No data found");
		}
	}
	
	public void attachFile(String Data) throws InterruptedException{
		if(!Data.equals("")){
			driver.findElement(By.xpath("//*[@class='btn btn-default btn-file']/input")).sendKeys(Data);
			wait("1000");
		}else{
			test.log(LogStatus.INFO, "No data found");
		}
		
	}
	
	public void clickMenuOnButton(String Object) throws IOException, InterruptedException{
		getElement(Object).click();
		wait("6000");
	}
	
	public void EntervalueInsubmittextarea(String Object,String Data) throws InterruptedException, IOException{
		//act=new Actions(driver);
		//act.moveToElement(driver.findElement(By.xpath("//*[@id='msi-modal']/div/div"))).build().perform();
		Set<String> winids=driver.getWindowHandles();
		Iterator<String> it=winids.iterator();
		driver.switchTo().window(it.next());
		wait("6000");
		getElement(Object).sendKeys(Data);
		//driver.findElement(By.xpath("//*[@class='modal-dialog']/div/div[1]")).click();
		wait("6000");
	}

	public void clickOnPopupButton(String object) throws IOException, InterruptedException {
		Set<String> winids=driver.getWindowHandles();
		Iterator<String> it=winids.iterator();
		String popupwindow=it.next();
		driver.switchTo().window(popupwindow);
		wait("6000");
		
		getElement(object).click();
		wait("6000");	
	}
	
	public void closebrowser(){
		driver.quit();
	}
	
}
