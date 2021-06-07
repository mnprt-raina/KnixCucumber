package stepDefinitions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import MyRunner.RunnerSupport;
import MyRunner.TestRunner;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import helper.Constants;
import sun.nio.fs.DefaultFileSystemProvider;

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
					System.out.println("report failure. Size must be present for loop : "+i);
				}else {

					allSizes.get(i).click(); 
					imageCaptionText = getWebElement(captionUnderActiveImage).getText();

					boolean sizeMatched = false;

					if(imageCaptionText.isEmpty()) {
						System.out.println("report failure. caption must be present for size : "+selectedSizeText);
					}else {
						String individualSize[] = sizeArr[0].split(",");

						for(int j=0;j<individualSize.length;j++) {
							if(imageCaptionText.contains(individualSize[j])) {
								sizeMatched = true;
								break;
							}
						}

						if(sizeMatched) {
							System.out.println("size found . selectedSizeText :"+selectedSizeText+" and caption image : "+imageCaptionText);
						}else {
							System.out.println("size mismatch . selectedSizeText :"+selectedSizeText+" and caption image : "+imageCaptionText);
						}

					}

				}
				//open the drodown again
				getWebElement(dropdown).click();
				System.out.println();

			} 
		}catch(Exception e) {
			System.out.println(e.getMessage());
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
			actualSizes = actualSizes + ","+a;
		}

		actualSizes = actualSizes.substring(1,actualSizes.length());

		System.out.println(actualSizes);

		if(expectedSizes.equals(actualSizes)) {
			System.out.println("pass");
		}else {
			System.out.println("failed");
		}

	}

	@Then("^User clicks (.*)$")
	public void user_clicks_element(String locator) {
		WebElement ele;
		ele = getWebElement(locator);
		ele.click();
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
				System.out.println("shows selected item");
			}else {
				System.out.println("report failure. Selected item is not shown");
			}
			
		}
		
		
		

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
