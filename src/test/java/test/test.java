package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utils.Amazon_base;
import utils.HelperMethods;

/**
 * A Selenium based java project to test and automate the functionality of a web
 * application (Amazon.com) with a data driven approach.
 * 
 * @author Leslie Cobbold
 *
 */
public class test {
	HelperMethods method = new HelperMethods();
	Amazon_base baseMethods;
	WebDriver driver;
	WebDriverWait wait;

	/**
	 * Sets up the Selenium environment with either the webdriver approach or grid
	 * approach.
	 * 
	 * @param url     the url of the web app to test/automate
	 * @param browser the browser to test with i.e(Chrome, Firefox or Edge)
	 * @param grid    decision to use Selenium grid setup(true) or not(false)
	 */
	@Parameters({ "url", "browser", "setupType" })
	@BeforeTest
	public void setup(String url, String browser, String setupType) {
		baseMethods = new Amazon_base();
		baseMethods.gridSetupOrWebdriver(browser, url, setupType);
		driver = baseMethods.getDriver();
	}

	/**
	 * Verify if clicking the home button/logo will take you to landing page
	 */
	@Test
	public void testHomeButton() {
		String urlOfHomeButton = "https://www.amazon.com/ref=nav_logo";
		driver.findElement(By.id("nav-logo-sprites")).click();
		String urlAfterHomeButtonClick = driver.getCurrentUrl();
		assertTrue(urlOfHomeButton.equals(urlAfterHomeButtonClick), "Url of page not valid!");

	}

	/**
	 * Tests if the page title is valid
	 */
	@Test
	public void testTitle() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		String expectedTitle = "Amazon.com. Spend less. Smile more.";
		String actualTitle = driver.getTitle();

		assertEquals(actualTitle, expectedTitle, "Title of page not valid!");
	}

	/**
	 * Tests the search bar by searching a product
	 * 
	 * @param productName the product being searched for on Amazon.com
	 */
	@Parameters({ "productName" })
	@Test
	public void testSearchBar(String productName) {
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(productName);
		wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		String actualSearchBarValue = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"))
				.getAttribute("value");
		assertEquals(actualSearchBarValue, productName, "Item searched is not valid!");
	}

	/**
	 * Tests the dropdown menu of the different departments on Amazon.com
	 */
	@Test
	public void getDepartments() {
		Select departments = new Select(driver.findElement(By.id("searchDropdownBox")));
		List<WebElement> types = departments.getOptions();
		String expectedDepartment = "Alexa Skills";
		String actualDepartment = types.get(1).getText();
		assertEquals(actualDepartment, expectedDepartment, "Department does not match");
	}

	/**
	 * Tests the login form to validate if provided username and password.
	 * 
	 * @param username the username given to test
	 * @param password the password given to test
	 */
	@Parameters({ "testUsername", "testPassword" })
	@Test(priority = 1)
	public void testSignIn(String username, String password) {
		method.login(username, password, driver);
	}

	// get product link, product name, product price, possibly image, possibly
	// ratings, rank based on request
	// -> output data to basic html website or text file.
	// use data driven xml file to get product request name
	// possible improvements incorporate cucumber

	/**
	 * Scrapes the website for the search results if provided a product name and
	 * returns a text file with the search results.
	 * 
	 * @param productName the product being searched for on Amazon.com
	 */
	@Parameters({ "productName", "fileName"})
	@Test
	public void getSearchResults(String productName, String outputFileName) {
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(productName);
		driver.findElement(By.id("nav-search-submit-button")).click();
		wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		List<WebElement> searchLinks = driver.findElements(By.xpath(
				"//div[@data-component-type='s-search-result']//a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal']"));
		List<WebElement> prices = driver.findElements(By.xpath("//span[@class='a-price']/span[1]"));
		HashMap<WebElement, String> searchResult = new HashMap<WebElement, String>();
		for (int i = 0; i < searchLinks.size(); i++) {
			searchResult.put(searchLinks.get(i), prices.get(i).getAttribute("innerText"));
		}

		try {
			method.writeToFile(searchLinks, searchResult, outputFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Quits the browser after the test is completed
	 */
	@AfterTest
	public void tearDown() {
		baseMethods.quitBrowser();
	}
}