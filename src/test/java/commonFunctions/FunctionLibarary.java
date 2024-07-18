package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibarary {
	public static Properties conpro;
	public static WebDriver driver;
	//method for launching browser
	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		//load properties file
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("Chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("Firefox"))
		{
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		}
		else
		{
			Reporter.log("Browser values is not matching", true);
		}
		return driver;
	}
	// method for launching URL
	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));
	}
	//method for waiting
	public static void waitForElement(String LocatorType, String LocatorValue, String testData )
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(testData)));
		if(LocatorType.equalsIgnoreCase("name"))
		{
			// wait until element is visible
			mywait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("xpath")) 
		{
			mywait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(LocatorValue)));	
		}
		if(LocatorType.equalsIgnoreCase("Id"))
		{
			mywait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(LocatorValue)));			
		}
	}
	public static void typeAction(String LocatorType,String LocatorValue,String testData)
	{
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorType)).sendKeys(testData);
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(testData);
		}
		if(LocatorType.equalsIgnoreCase("Id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(testData);			
		}
	}
	//method for clickaction
	public static void clickAction(String LocatorType, String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("Id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}
	public static void validdateTitle(String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try {
			Assert.assertEquals(Actual_Title, Expected_Title, "Tital Not Match");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	public static void closeBrowser()
	{
		driver.close();
	}
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat dt = new SimpleDateFormat("YYYY_MM_dd mm_hh");
		return dt.format(date);
	}
	//method for dropdown action'
	public static void dropDownAction(String LocatorType,String LocatorValue,String testData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(testData);
			Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
			element.selectByIndex(value);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			int value = Integer.parseInt(testData);
			Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
			element.selectByIndex(value);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(testData);
			Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
			element.selectByIndex(value);
		}
	}

	public static void scrolltoelement()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)", "");
	}
	//method for capture stock number into notepad'
	public static void capStock(String LocatorType, String LocatorValue) throws Throwable
	{
		String stockNumber ="";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			stockNumber = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		//Crteate notepad under CaptureData folder
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stockNumber);
		bw.flush();
		bw.close();

	}
	//methods stock tables
	public static void stockTable() throws Throwable
	{
		// read stoc number from note pad
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		br.close();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("serach-button"))).click();
		Thread.sleep(3000);
		WebElement Act_Data = driver.findElement(By.xpath("//table[@class ='table ewTable']/tbody/tr[1]/td[8]/div/span/span"));
		Reporter.log(Act_Data+" "+Exp_Data,true);
		try {
			Assert.assertEquals(Act_Data,  Exp_Data,"Stock Number Not found in table");
		}catch (Throwable t) 
		{
			System.out.println(t.getMessage());
		}		
	}
	// Method for capture supplier number into notepad
	public static void capSup(String LocatorType, String LocatorValue) throws Throwable
	{
		String capSup=" ";
		if(LocatorType.equalsIgnoreCase("name"))
		{
			capSup = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			capSup = driver.findElement(By.xpath(LocatorType)).getAttribute("value");
		}


		//Creat notepad under CaptureData folder
		FileWriter fw = new FileWriter("./CaptureData/supplierNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(capSup);
		bw.flush();
		bw.close();
	}
	// method for supplier table
	public static void supplierTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		br.close();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(50000);
		WebElement Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[6]/div/span/span"));
		Reporter.log(Act_Data+ "  "+Exp_Data);
		try {
			Assert.assertEquals(Act_Data, Exp_Data, "Supplier number was not found in the table");
		} catch (Throwable t) {
			System.out.println(t.getMessage());		
		}		

	}
	public static void capCusno(String LocatorType, String LocatorValue) throws Throwable
	{
		String capCusno=" ";
		if(LocatorType.equalsIgnoreCase("name"))
		{
			capCusno = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			capCusno = driver.findElement(By.xpath(LocatorType)).getAttribute("value");
		}

		//Creat notepad under CaptureData folder
		FileWriter fw = new FileWriter("./CaptureData/customernumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(capCusno);
		bw.flush();
		bw.close();
	}
	public static void customerTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/customernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		br.close();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(50000);
		WebElement Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[5]/div/span/span"));
		Reporter.log(Act_Data+ "  "+Exp_Data);
		try {
			Assert.assertEquals(Act_Data, Exp_Data, "customer number was not found in the table");
		} catch (Throwable t) {
			System.out.println(t.getMessage());		
		}
	}



}
