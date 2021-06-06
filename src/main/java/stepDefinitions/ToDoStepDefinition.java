package stepDefinitions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import MyRunner.RunnerSupport;
import MyRunner.TestRunner;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import helper.Constants;

public class ToDoStepDefinition extends TestRunner {

	static WebDriver driver;
	static Properties OR;

	static {
		final String ORFile = System.getProperty("user.dir")+ Constants.OBJECTREPOSITORY;
		OR = RunnerSupport.loadOR(ORFile);
	}

	@Given("^User launches KNIX application$") 
	public void user_launches_app() throws Exception {

		if(System.getProperty("browser").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//src//test//resources//drivers//chromedriver.exe");
			driver = new ChromeDriver();
		}
		driver.get(System.getProperty(Constants.APPURL));
		driver.manage().window().maximize();

	}

	@Then("^User validates size from the (.*) dropdown with (.*)$")
	public void select_the_size(String dropdownList, String captionUnderActiveImage){
		List<WebElement> allSizes;
		String imageCaptionText;
		allSizes = getWebElements(dropdownList);
		imageCaptionText = getWebElement(captionUnderActiveImage).getText();

		for(int i = 0; i<=allSizes.size(); i++) {
			String toSelectSize = allSizes.get(i).getText();

			allSizes.get(i).click(); 
			imageCaptionText = imageCaptionText.substring(0, imageCaptionText.indexOf(","));
			imageCaptionText = imageCaptionText.trim();
			String nameTerm[] = imageCaptionText.split(" ");

			if(toSelectSize.contains(nameTerm[3])) {
				System.out.println("size found");
			}
		} 
	}


	@Then("^User validates if (.*) in the (.*) is in a sorted order$")
	public void list_sort(String listValues) {
		List<String> listExpected = Arrays.asList("1", "2", "3", "4", "5", "6", "6+", "7","7+", "8", "8+");
		List<WebElement> listActual;

		listActual = getWebElements(listValues);

		for(int i = 0; i<=listActual.size(); i++) {
			if (listActual.get(i).equals(listExpected.get(i))) {
				System.out.println("The list is in sorted order");
			};
		}
	}

	@Then("^User clicks (.*)$")
	public void user_clicks_element(String locator) {
		WebElement ele;
		ele = getWebElement(locator);
		ele.click();
	}

	@Then("^Validate user is able to see the selected size in the (.*)$")
	public void validate_user_sees_selected_size()
	{
		WebElement ele;
		Map<String, String> map = new HashMap<>();
		map.put("size 1", "Fits 32A, 34A, 32B");
		map.put("size 2", "Fits 36A, 34B, 32C");
		map.put("size 3", "Fits 36B, 38B, 36C");
		map.put("size 4", "Fits 34C, 32D, 34D");
		map.put("size 5", "Fits 38C, 40C, 36D, 38D");
		map.put("size 6", "Fits 32DD, 34DD, 32E, 34E");
		map.put("size 6+", "Fits 32F, 34F, 32G, 34G");
		map.put("size 7", "Fits 36DD, 38DD, 36E, 38E");
		map.put("size 7+", "Fits 36F, 38F, 36G, 38G");
		map.put("size 8", "Fits 40D, 42D, 40DD, 42DD, 40E, 42E");
		map.put("size 8+", "Got the data from Requirements");
		
		
		
		
	}

	public WebElement getWebElement(String locator){
		WebElement ele;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		By by = By.xpath(OR.getProperty(locator));
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		ele = driver.findElement(by);
		return ele;
	}

	public List<WebElement> getWebElements(String locator){
		List<WebElement> elements;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		By by = By.xpath(OR.getProperty(locator));
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		elements = driver.findElements(by);
		return elements;
	}

	public void getWebElementAndSendKeys(String value, String locator){
		WebElement ele;
		ele = getWebElement(locator);
		ele.sendKeys(value);
		ele.sendKeys(Keys.ENTER);
	}



}
