package stepDefinitions;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import MyRunner.RunnerSupport;
import MyRunner.TestRunner;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import helper.Constants;
import util.CustReporter;

public class ToDoStepDefinition extends TestRunner{

	public static WebDriver driver;
	static Properties OR;

	ExtentTest test;

	static {
		final String ORFile = System.getProperty("user.dir")+ Constants.OBJECTREPOSITORY;
		OR = RunnerSupport.loadOR(ORFile);
	}

	@Before
	public void getName(Scenario s) {
		test = extent.createTest(s.getName());
	}

	@After
	public void destroy() {
		driver.quit();
	}

	@Given("^User launches KNIX application on (.*)$") 
	public void user_launches_app(String browser) throws Exception {

		switch(browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//src//test//resources//drivers//chromedriver.exe");
			driver = new ChromeDriver();
			test.assignCategory("chrome");
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"//src//test//resources//drivers//geckodriver.exe");
			driver = new FirefoxDriver();
			test.assignCategory("firefox");
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver",System.getProperty("user.dir")+"//src//test//resources//drivers//msedgedriver.exe");
			driver = new EdgeDriver();
			test.assignCategory("edge");
			break;
		}

		driver.get(System.getProperty(Constants.APPURL));
		driver.manage().window().maximize();

	}

	@Then("^User validates (.*) from the (.*) with (.*)$")
	public void select_the_size(String fits,String dropdown, String captionUnderActiveImage){
		List<WebElement> allSizes;
		String imageCaptionText;

		try{
			allSizes = getWebElements(fits);

			for(int i = 0; i<=allSizes.size()-1; i++) {
				String selectedSizeText = allSizes.get(i).getText();

				selectedSizeText = selectedSizeText.replaceAll("Fits", "");
				String sizeArr[] = selectedSizeText.split("\n");

				if(selectedSizeText.isEmpty()) {
					CustReporter.fail(test, extent,"Size must be present for loop : "+i);
				}
				else{

					allSizes.get(i).click(); 
					imageCaptionText = getWebElement(captionUnderActiveImage).getText();

					boolean sizeMatched = false;

					if(imageCaptionText.isEmpty()) {
						test.log(Status.INFO,"Caption must be present for size : "+selectedSizeText);
					}else {
						String individualSize[] = sizeArr[0].split(",");

						for(int j=0;j<individualSize.length;j++) {
							if(imageCaptionText.contains(individualSize[j])) {
								sizeMatched = true;
								break;
							}
						}

						if(sizeMatched) {
							CustReporter.pass(test, extent,"size found . selectedSizeText :"+selectedSizeText+" and caption image : "+imageCaptionText);
						}else {
							CustReporter.fail(test, extent,"size mismatch . selectedSizeText :"+selectedSizeText+" and caption image : "+imageCaptionText);
						}
					}
				}
				//open the drodown again
				getWebElement(dropdown).click();
			} 
		}catch(Exception e) {
			test.log(Status.INFO,e.getMessage());
		}

	}


	@Then("^User validates if the (.*) is sorted$")
	public void list_sort(String dropdownList) {

		String expectedSizes = "1,2,3,4,5,6,6+,7,7+,8,8+";

		List<WebElement> allSizes;
		allSizes = getWebElements(dropdownList);

		String actualSizes = "";

		for(WebElement ele: allSizes) { 
			String a = ele.getText();
			actualSizes = actualSizes + ","+ a;
		}

		actualSizes = actualSizes.substring(1,actualSizes.length());

		test.log(Status.INFO,"Actual size order list :"+actualSizes);

		if(expectedSizes.equals(actualSizes)) {
			CustReporter.pass(test, extent, "The sizes are in sorted order.");	
		}
		else{
			CustReporter.fail(test, extent, "The sizes are not in sorted order. Expected size order : "+expectedSizes);	
		}
	}

	@Then("^User clicks (.*)$")
	public void user_clicks_element(String locator) {
		try {
			WebElement ele;
			ele = getWebElement(locator);
			clickElement(ele);
		}catch(Exception e) {
			CustReporter.fail(test, extent, "Exception occurred : "+e.getMessage());
		}
		
	}

	@Then("^Validate user is able to see the selected (.*) in the (.*)$")
	public void validate_user_sees_selected_size(String fittingsize, String dropdown)
	{
		List<WebElement> allSizes;
		WebElement dropButton;

		allSizes = getWebElements(fittingsize);
		dropButton = getWebElement(dropdown);

		for(int i = 0; i<=allSizes.size()-1; i++) {
			dropButton.click();
			String toSelect = allSizes.get(i).getText();
			toSelect = "Size " + toSelect;

			allSizes.get(i).click();

			String actual = getWebElement(dropdown).getText();

			if(toSelect.equals(actual)) {	
				CustReporter.pass(test, extent,"Selected size is being shown. Expected : "+toSelect +" . Actual : "+actual);
			}else {
				CustReporter.fail(test, extent,"Selected size is not being shown. Expected : "+toSelect +" . Actual : "+actual);
			}

		}	

	}

	/*
	 * @Given("^Validate if User is shown Email me when Available on (.*)$") public
	 * void validate_main_add_text(String mainButton) { WebElement ele; ele =
	 * getWebElement(mainButton);
	 * 
	 * if (ele.getText().equals(Constants.EMAILWHENAVAILABLE)) {
	 * CustReporter.pass(test, extent,"The main button contains the required text");
	 * }else { CustReporter.fail(test,
	 * extent,"report Failure. The main button does not contain the required text");
	 * }
	 * 
	 * }
	 */

	@Then("^Validate (.*) for all (.*)$")
	public void validateLimitedColours(String limcolour,String fittingSize) {

		List<WebElement> colour = getWebElements(limcolour);

		for(WebElement e: colour) {

			String colourName = e.getAttribute("data-text");

			List<WebElement> allSizes = getWebElements(fittingSize);

			clickElement(e);

			CustReporter.info(test, extent,"the colour is : "+ colourName);

			for(int i=0;i<allSizes.size();i++) {
				getWebElement("sizedropdown").click();

				String text = allSizes.get(i).getText();

				allSizes.get(i).click();

				String textArr[] = text.split("Fits");

				CustReporter.info(test, extent,"the size is : "+ text);

				String textOfAddToBag = getText("additemtobag");

				String stockInfoText = getText("stockinfo","4");
				
				if(!textOfAddToBag.equals("Add to Bag") || stockInfoText.contains("Sorry")){
					if(e.getAttribute("class").contains("disabled")) {
						CustReporter.pass(test, extent,"the "+ colourName +" button is crossed out for an out of stock item.");
					}else {
						CustReporter.fail(test, extent,"the "+ colourName +" button is not crossed out for an out of stock item.");
					}
				}

				if(stockInfoText.contains("Sorry")) {
					if(textOfAddToBag.equals("Add to Bag")) {
						CustReporter.fail(test, extent,"Add to Bag is available for an out of stock item.");
					}else {
						CustReporter.pass(test, extent,"Add to bag is unavailable of an out of stock item.");
					}
				}
			}
		}
	}



	/*
	 * @Then("^Validate that the (.*) is disabled$") public void
	 * validate_button_disabled(String mainButton) { WebElement ele; ele =
	 * getWebElement(mainButton);
	 * 
	 * if (ele.getAttribute("class").contains("disabled")) { CustReporter.pass(test,
	 * extent,"the button is disabled"); }else { CustReporter.fail(test,
	 * extent,"the button is enabled"); }
	 * 
	 * }
	 */

	@Then("^Validate user is unable to add item to the bag$")
	public void unable_to_add_to_cart(){
		WebElement e = getWebElement("additemtobag");
		e.click();
		if(e.getText().equals("Select Size")){
			CustReporter.pass(test, extent,"User is unable to add item to cart without selecting size.");
		}else {
			CustReporter.fail(test, extent,"User is able to add item to cart without selecting size.");
		}
	}

	@Then("^Validate user is able to add (.*) to the bag$")
	public void able_to_add_to_cart(String bagItem) { 
		List<WebElement> allSizes;
		WebElement dropButton, cartButton, bag;

		allSizes = getWebElements("fittingsize");
		dropButton = getWebElement("sizedropdown");

		dropButton.click();
		WebElement e = allSizes.get(1);
		clickElement(e);

		cartButton= getWebElement("additemtobag");
		cartButton.click();

		bag = getWebElement(bagItem);

		if (bag.getAttribute("class").contains("yourBag")) {
			CustReporter.pass(test, extent,"The item has been added to the cart.");
		}else {
			CustReporter.fail(test, extent,"The item has not been added to the cart.");
		}

	}

	public WebElement getWebElement(String locator){
		WebElement ele = null;
		try {
			Long time = Long.valueOf(System.getProperty(Constants.WAITTIME));
			WebDriverWait wait = new WebDriverWait(driver, time);
			By by = By.xpath(OR.getProperty(locator));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			ele = driver.findElement(by);
		}catch(TimeoutException e) {
			//do nothing
		}catch(Exception e){
			CustReporter.fail(test, extent, "Exception occurred : "+e.getMessage());
		}

		highlighter(ele);
		return ele;
	}

	public WebElement getWebElement(String locator,String override){
		WebElement ele = null;
		try {
			Long time = Long.valueOf(override);
			WebDriverWait wait = new WebDriverWait(driver, time);
			By by = By.xpath(OR.getProperty(locator));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			ele = driver.findElement(by);
		}catch(TimeoutException e) {
			//do nothing
		}catch(Exception e){
			CustReporter.fail(test, extent, "Exception occurred : "+e.getMessage());
		}

		highlighter(ele);
		return ele;
	}

	public List<WebElement> getWebElements(String locator){
		List<WebElement> elements=null;
		try{
			Long time = Long.valueOf(System.getProperty(Constants.WAITTIME));
			WebDriverWait wait = new WebDriverWait(driver,time);
			By by = By.xpath(OR.getProperty(locator));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			elements = driver.findElements(by);
		}catch(TimeoutException e) {
			//do nothing
		}catch(Exception e){
			CustReporter.fail(test, extent, "Exception occurred : "+e.getMessage());
		}
		return elements;
	}

	public String getText(String locator){
		WebElement ele = null;
		String res="";
		try {
			ele = getWebElement(locator);
			if(ele!=null)
				res = ele.getText();
		}catch(TimeoutException e) {
			//do nothing
		}catch(Exception e){
			CustReporter.fail(test, extent, "Exception occurred : "+e.getMessage());
		}

		return res;
	}
	
	public String getText(String locator,String time){
		WebElement ele = null;
		String res="";
		try {
			ele = getWebElement(locator,time);
			if(ele!=null)
				res = ele.getText();
		}catch(TimeoutException e) {
			//do nothing
		}catch(Exception e){
			CustReporter.fail(test, extent, "Exception occurred : "+e.getMessage());
		}

		return res;
	}

	public void getWebElementAndSendKeys(String value, String locator){
		try {
			WebElement ele;
			ele = getWebElement(locator);
			ele.sendKeys(value);
			ele.sendKeys(Keys.ENTER);
		}catch(TimeoutException e) {
			//do nothing
		}catch(Exception e){
			CustReporter.fail(test, extent, "Exception occurred : "+e.getMessage());
		}
	}
	
	public void clickElement(WebElement e) {
		try {
			e.click();
		}catch(ElementClickInterceptedException ex) {
			CustReporter.info(test, extent, "ElementClickInterceptedException has occurred. Trying with JS click.");
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", e);
		}catch(Exception ex){
			CustReporter.fail(test, extent, "Exception occurred : "+ex.getMessage());
		}
	}
	
	static void highlighter(WebElement element) {
		try{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String var = (String) js.executeScript("return arguments[0].getAttribute('style', arguments[1]);", element); 
			js.executeScript("return arguments[0].setAttribute('style', arguments[1]);", element, "border: 4px solid red; ");
			Thread.sleep(170);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, var);
		}catch(Throwable t) {
		}
	}
}
