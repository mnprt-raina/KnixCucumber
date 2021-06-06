# Cucumber-TestNG-Sample
![LambdaTest Logo](https://www.lambdatest.com/static/images/logo.svg)
---

### Running Cucumber Scripts With TestNG And Selenium

LambdaTest Selenium Automation Grid is a cloud-based scalable Selenium testing platform which enables you to run your automation scripts on 2000+ different browsers and operating systems. You can now run your Cucumber scripts with TestNG and Selenium for automating your web application over a scalable Selenium infrastructure that is running real browsers and real operating systems.


## Prerequisites for running tests using Cucumber automation framework :

## Environment Setup 

### 1. Java Installation
   
   i.   For Windows :
   
   You can download Java for Windows from [here](http://www.java.com/en/download/manual.jsp)
   
   Run the installer and follow the setup wizard to install Java.
   
   create a new JAVA_HOME environment variable and set variable value as path value to JDK folder.
   
   #### This is Windows environment variable location :
   ```
   Control Panel > All Control Panel Items > System > Advanced system settings > Environment Variables
   ```
   
   ![altext](https://github.com/keshavissar001/images/blob/master/Img1.png)
   
   ii. For Linux :
   
   use this command :
   ```
   sudo apt-get install openjdk-8-jre
   ```
   iii. For Mac
   
   Java should already be present on Mac OS X by default
   
   ### 2. Maven Installation
   
   Install Maven from [here](https://maven.apache.org/install.html)
   
### 3 Setup

   You can download the file. To do this click on “Clone or download” button. You can download zip file.
   
   Right click on this zip file and extract files in your desired location.

   Go to “cucumber-testng-sample-master” folder and copy its path.
   Open command prompt and run :
   
    cd <path> (that you have copied)
    
    (please ignore "<" , ">" symbols)
    

![altext](https://github.com/keshavissar001/images/blob/master/SetPathCucumber.png)


	To clone the file, click on “Clone or download” button and copy the link.

	Then open the command prompt in the folder you want to clone the file. Run the command:

      git clone <paste link here> 
      
### Lambdatest Credentials

    Set LambdaTest username and access key in environment variables. It can be obtained from [LambdaTest dashboard](https://automation.lambdatest.com/)    
    example:
    
    - For linux/mac
    ```
    export LT_USERNAME="YOUR_USERNAME"
    export LT_ACCESS_KEY="YOUR ACCESS KEY"
    ```
    - For Windows
    ```
    set LT_USERNAME="YOUR_USERNAME"
    set LT_ACCESS_KEY="YOUR ACCESS KEY"
    ```
    
   You may also want to run the command below to check for outdated dependencies. Please be sure to verify and review updates before editing your pom.xml file as they may not be compatible with your code.
   
    ```
    $ mvn versions:display-dependency-updates
    ```
    
  ![altext](https://github.com/keshavissar001/images/blob/master/mvnUpdate.png)

### 4. Running Tests

Let’ start with a simple Selenium Remote Webdriver test first. The Cucumber-testng script below tests a simple to-do application with basic functionalities like mark items as done, add items in list, calculate total pending items etc.

Feature: Test to add item Scenario: Test sample-todo-app Given

I go to sample-todo-app to add item 

Then I Click on first checkbox and second checkbox

Then I enter item to add When I click add button 

Then I should verify the added item

Now here is the sample test file which is to be executed for the automation test through LambdaTest

Here is the sample feature file for Cucumber :

```
Feature: Add new item to ToDO list

Scenario: Lambdatest ToDO Scenario

Given user is on home Page
When select First Item
Then select second item
Then add new item
Then verify added item
```

Here is the TestRunner file to automate our feature file through Selenium using TestNG :

```
package MyRunner;

import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(
        features = "src/main/java/Features",
        glue = {"stepDefinitions"},
        tags = {"~@Ignore"},
        format = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        },plugin = "json:target/cucumber-reports/CucumberTestReport.json")

public class TestRunner {
	
    private TestNGCucumberRunner testNGCucumberRunner;
  
    public static RemoteWebDriver connection;
    
    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
    	 testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }
    
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser", "version", "platform" })
    public void setUpClass(String browser, String version, String platform) throws Exception {

    		String username = System.getenv("LT_USERNAME") == null ? "YOUR LT_USERNAME" : System.getenv("LT_USERNAME"); 
    		String accesskey = System.getenv("LT_ACCESS_KEY") == null ? "YOUR LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY"); 

    		DesiredCapabilities capability = new DesiredCapabilities();    		
    		capability.setCapability(CapabilityType.BROWSER_NAME, browser);
    		capability.setCapability(CapabilityType.VERSION,version);
    		capability.setCapability(CapabilityType.PLATFORM, platform);
    		    		
    		capability.setCapability("build", "Your Build Name");
    		
    		capability.setCapability("network", true);
    		capability.setCapability("video", true);
    		capability.setCapability("console", true);
    		capability.setCapability("visual", true);

    		String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";
    		System.out.println(gridURL);
    		connection = new RemoteWebDriver(new URL(gridURL), capability);
    		System.out.println(capability);
    		System.out.println(connection);
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
```

Below are the step definitions :

```
package stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import MyRunner.*;

public class ToDoStepDefinition extends TestRunner {

	public RemoteWebDriver driver = this.connection;

	@Before
	public void updateName(Scenario scenario) {
		driver.executeScript("lambda-name="+scenario.getName());
	}

	@Given("^user is on home Page$")
	public void user_already_on_home_page() {
		System.out.println(driver.getCapabilities());
		driver.get("https://lambdatest.github.io/sample-todo-app/");

	}

	@When("^select First Item$")
	public void select_first_item() {
		driver.findElement(By.name("li1")).click();
	}

	@Then("^select second item$")
	public void select_second_item() {
		driver.findElement(By.name("li2")).click();
	}

	@Then("^add new item$")
	public void add_new_item() {
		driver.findElement(By.id("sampletodotext")).clear();
		driver.findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list");
		driver.findElement(By.id("addbutton")).click();
	}

	@Then("^verify added item$")
	public void verify_added_item() {
		String item = driver.findElement(By.xpath("/html/body/div/div/div/ul/li[6]/span")).getText();
		Assert.assertTrue(item.contains("Yey, Let's add it to list"));
	}

	@After
	public void close_the_browser(Scenario scenario) {
		driver.executeScript("lambda-status=" + (scenario.isFailed() ? "failed" : "passed"));
		driver.quit();
	}

}
```



    To Run Your Test, use following command :
    
    ```
    $ mvn test
    ```
    
 These are the screenshots of the output :
    
![altext](https://github.com/keshavissar001/images/blob/master/CucumberResult1.png)

![altext](https://github.com/keshavissar001/images/blob/master/CucumberResult2.png)

Below we see a screenshot that depicts our Cucumber-testng code is running over different browsers i.e Chrome, IE and Edge on the LambdaTest Selenium Grid Platform. The results of the test script execution along with the logs can be accessed from the LambdaTest Automation dashboard.

![altext](https://github.com/keshavissar001/images/blob/master/AutomationLogCucumber.png)

##  Testing Locally Hosted or Privately Hosted Projects

To help you perform cross browser testing of your locally stored web pages, LambdaTest provides an SSH(Secure Shell) tunnel connection with the name Lambda Tunnel. With Lambda Tunnel, you can test your locally hosted files before you make them live over the internet. You could even perform cross browser testing from different IP addresses belonging to various geographic locations. You can also use LambdaTest Tunnel to test web-apps and websites that are permissible inside your corporate firewall.

* Set tunnel value to True in test capabilities
> OS specific instructions to download and setup tunnel binary can be found at the following links.
>    - [Windows](https://www.lambdatest.com/support/docs/display/TD/Local+Testing+For+Windows)
>    - [Mac](https://www.lambdatest.com/support/docs/display/TD/Local+Testing+For+MacOS)
>    - [Linux](https://www.lambdatest.com/support/docs/display/TD/Local+Testing+For+Linux)

After setting tunnel you can also see the active tunnel in our LambdaTest dashboard:


![tn](https://github.com/Apoorvlt/test/blob/master/tn.PNG)

### Important Note:
Some Safari & IE browsers, doesn't support automatic resolution of the URL string "localhost". Therefore if you test on URLs like "http://localhost/" or "http://localhost:8080" etc, you would get an error in these browsers. A possible solution is to use "localhost.lambdatest.com" or replace the string "localhost" with machine IP address. For example if you wanted to test "http://localhost/dashboard" or, and your machine IP is 192.168.2.6 you can instead test on "http://192.168.2.6/dashboard" or "http://localhost.lambdatest.com/dashboard".

## About LambdaTest

[LambdaTest](https://www.lambdatest.com/) is a cloud based selenium grid infrastructure that can help you run automated cross browser compatibility tests on 2000+ different browser and operating system environments. LambdaTest supports all programming languages and frameworks that are supported with Selenium, and have easy integrations with all popular CI/CD platforms. It's a perfect solution to bring your [selenium automation testing](https://www.lambdatest.com/selenium-automation) to cloud based infrastructure that not only helps you increase your test coverage over multiple desktop and mobile browsers, but also allows you to cut down your test execution time by running tests on parallel.

### Resources

##### [SeleniumHQ Documentation](http://www.seleniumhq.org/docs/)
##### [Cucumber Documentation](https://cucumber.io/docs)


