package MyRunner;

import java.util.Properties;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import helper.Constants;

@CucumberOptions(
		features = "src/main/java/Features/OutOfStock.feature",
		glue = {"stepDefinitions"},
		monochrome = true)
		
public class TestRunner { 

	private TestNGCucumberRunner testNGCucumberRunner;
	public static RemoteWebDriver connection;
	protected static Properties propOR;
	protected static Properties propConfig;
	public static ExtentReports extent;
	
	@BeforeSuite
	public void create(){
		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("Spark.html");
		extent.attachReporter(spark);
		
	}
	
	@BeforeClass(alwaysRun = true)
	public void setUpCucumber() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		
		final String configFile = System.getProperty("user.dir")+ Constants.RUNCONFIG_FILE;
		RunnerSupport.prepareTestEnv(configFile);
		
	}
	
	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
        
		testNGCucumberRunner.finish();
	}
}