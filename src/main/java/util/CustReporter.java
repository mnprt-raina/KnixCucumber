package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class CustReporter {
	
	public void info() {
		
	}
	
	public static void pass(ExtentTest test,ExtentReports extent, String msg) {
		test.log(Status.PASS, msg);
		extent.flush();
	}
	
	public static void fail(ExtentTest test,ExtentReports extent, String msg) {
		test.log(Status.FAIL, msg);
		extent.flush();
	}
	
}
