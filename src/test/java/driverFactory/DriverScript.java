package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibarary;
import utilities.ExcelFileUtil;

public class DriverScript 
{
	public static WebDriver driver;
	String Inputpath = "./FileInput/Controller.xlsx";
	String Outoutpath = "./FileOutput/HybridResults.xlsx";
	String TCSheet = "MasterTestCases";
	String TCModule = "";
	ExtentReports reports;
	ExtentTest logger;
	public void startTest() throws Throwable
	{
		String Module_status = "";
		String Module_new = "";
		// create object for excelfileutil
		ExcelFileUtil xl = new ExcelFileUtil(Inputpath);
		//itterate all rows in TCSheet
		for(int i=1; i<=xl.rowCount(TCSheet); i++)
		{     
			if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
				reports =new ExtentReports("./target/ExtendRepoprts/"+TCModule+FunctionLibarary.generateDate()+".html");
				logger = reports.startTest(TCModule);
				logger.assignAuthor("Niranjan");
				// we stored corresponding sheets or testcases into TCModule
				TCModule = xl.getCellData(TCSheet, i, 1);
				
				// itterate corresponding sheet
				for(int j= 1; j<=xl.rowCount(TCModule); j++)
				{
					//read each cell from TCModule
					String Description = xl.getCellData(TCModule, j, 0);
					String Object_Type = xl.getCellData(TCModule, j, 1);
					String LType = xl.getCellData(TCModule, j, 2);
					String LValue = xl.getCellData(TCModule, j, 3);
					String TData	= xl.getCellData(TCModule, j, 4);
					try
					{
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibarary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("openUrl"))
						{
							FunctionLibarary.openUrl();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibarary.waitForElement(LType, LValue, TData);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibarary.typeAction(LType, LValue, TData);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibarary.clickAction(LType, LValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("scroll"))
						{
							FunctionLibarary.scrolltoelement();
							logger.log(LogStatus.INFO, Description);
						}						
						if(Object_Type.equalsIgnoreCase("validdateTitle"))
						{
							FunctionLibarary.validdateTitle(TData);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibarary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibarary.dropDownAction(LType, LValue, TData);
							logger.log(LogStatus.INFO, Description);
							
						}
						if(Object_Type.equalsIgnoreCase("capStock"))
						{
							FunctionLibarary.capStock(LType, LValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("stockTable"))
						{
							FunctionLibarary.stockTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("capSup"))
						{
							FunctionLibarary.capStock(LType, LValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase(" "))
						{
							FunctionLibarary.supplierTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("capCusno"))
						{
							FunctionLibarary.capCusno(LType, LValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase(" "))
						{
							FunctionLibarary.customerTable();
							logger.log(LogStatus.INFO, Description);
						}
						
						//write as pass into status cell TCModule
						xl.setCellData(TCModule, j, 5, "Pass", Outoutpath);
						logger.log(LogStatus.PASS, Description);
						Module_status = "True";
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
						
						// write as Fail into status cell TCModle
						xl.setCellData(TCModule, j, 5, "Fail", Outoutpath);
						logger.log(LogStatus.FAIL, Description);
						Module_new = "False";
						File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(screen, new File("./target/screenshot/"+TCModule+Description+FunctionLibarary.generateDate()+".png"));
					}
					reports.endTest(logger);
					reports.flush();
					if(Module_status.equalsIgnoreCase("True"))
					{
						// write as pass into TCSheet
						xl.setCellData(TCSheet, i, 3, "Pass", Outoutpath);
					}
					if(Module_new.equalsIgnoreCase("False"))
					{
						xl.setCellData(TCSheet, i, 3, "Fail", Outoutpath);
					}
				}
			}
			else
			{
				//write as blocked in to TCSheet
				xl.setCellData(TCSheet, i, 3, "Blocked", Outoutpath);
			}
		}
	}
	
	

}
