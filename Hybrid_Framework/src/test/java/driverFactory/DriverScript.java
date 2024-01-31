package driverFactory;

import org.openqa.selenium.WebDriver;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commanFunction.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
public static WebDriver driver;
String Msheet ="MasterTestCases";
String inputpath ="./FileInput/Controller.xlsx";
String Outputpath ="./FileOutput/HybridResults.xlsx";
ExtentReports report;
ExtentTest logger;
public void startTest() throws Throwable
{
	String Module_Status="";
	//create object for Excelfileutil class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all rows in Msheet
	for(int i=1;i<=xl.rowCount(Msheet);i++)
	{
		
		
		if(xl.getCellData(Msheet, i, 2).equalsIgnoreCase("Y"))
		{
			//store all sheet into One variable
			String TCModule =xl.getCellData(Msheet, i, 1);
			report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+"--"+".html");
			logger =report.startTest(TCModule);
			//iterate all rows in TCModule
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//read each cell from TCModule sheet
				String Description = xl.getCellData(TCModule, j, 0);
				String Object_Type =xl.getCellData(TCModule, j, 1);
				String Locator_Name =xl.getCellData(TCModule, j, 2);
				String Locator_Value =xl.getCellData(TCModule, j, 3);
				String Test_Data = xl.getCellData(TCModule, j, 4);
				try {
					if(Object_Type.equalsIgnoreCase("startBrowser"))
					{
						driver =FunctionLibrary.startBrowser();
						logger.log(LogStatus.INFO, Description);
						
					}
					if(Object_Type.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(Locator_Name, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(Locator_Name, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(Locator_Name, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("dropDownAction")) 
					{
						FunctionLibrary.dropDownAction(Locator_Name, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("stockCapture")) 
					{
						FunctionLibrary.stockCapture(Locator_Name, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("stockTable")) 
					{
						FunctionLibrary.stockTable();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("captureSupplier")) 
					{
						FunctionLibrary.captureSupplier(Locator_Name, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("suppliertable")) 
					{
						FunctionLibrary.suppliertable();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("captureCustomer")) 
					{
						FunctionLibrary.captureCustomer(Locator_Name, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("customerTable")) 
					{
						FunctionLibrary.customerTable();
						logger.log(LogStatus.INFO, Description);
					}
					//write as pass into TCModule sheet
					xl.setCellData(TCModule, j, 5, "Pass", Outputpath);
					logger.log(LogStatus.PASS, Description);
					Module_Status ="True";
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
					//write as Fail into TCModule sheet
					xl.setCellData(TCModule, j, 5, "Fail", Outputpath);
					logger.log(LogStatus.FAIL, Description);
					Module_Status ="False";
				}
				if(Module_Status.equalsIgnoreCase("True"))
				{
					//write ass pass into MSheet
					xl.setCellData(Msheet, i, 3, "Pass", Outputpath);
				}
				else
				{
					//write ass Fail into MSheet
					xl.setCellData(Msheet, i, 3, "Fail", Outputpath);
				}
				report.endTest(logger);
				report.flush();
				
			}
		}
		else
		{
			//write as blocked into Msheet for flag N
			xl.setCellData(Msheet, i, 3, "Blocked", Outputpath);
		}
	}
	
}
}