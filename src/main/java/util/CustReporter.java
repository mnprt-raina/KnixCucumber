package util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import stepDefinitions.ToDoStepDefinition;

public class CustReporter {
	
	static int iCounter = 0;
	
	public static void info(ExtentTest test,ExtentReports extent, String msg) {
		test.log(Status.INFO, msg);
		extent.flush();
	}
	
	public static void pass(ExtentTest test,ExtentReports extent, String msg) {
		test.log(Status.PASS, msg);
		extent.flush();
	}
	
	public static void fail(ExtentTest test,ExtentReports extent, String msg) {
		test = test.addScreenCaptureFromPath(takeScreenshot(ToDoStepDefinition.driver));
		test.log(Status.FAIL, "<b>"+msg+"</b>");
		extent.flush();
	}
	
	public static String takeScreenshot(WebDriver driver)  {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	    TakesScreenshot ts = (TakesScreenshot)driver;
	    File source = ts.getScreenshotAs(OutputType.FILE);
	    String destination = System.getProperty("user.dir") + "/screenshots/" + dateName+"_"+ iCounter++ + ".png";
	    File finalDestination = new File(destination);
	    try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return destination;
	}
	
}
