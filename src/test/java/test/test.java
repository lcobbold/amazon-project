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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utils.Amazon_base;
import utils.helperMethods;

public class test {
	helperMethods method = new helperMethods();
	Amazon_base baseMethods;
	WebDriver driver;

	@Parameters({"url", "browser", "grid"})
	@BeforeTest
	public void setup(String url, String browser, boolean grid) {
		baseMethods = new Amazon_base();
		baseMethods.gridSetupOrWebdriver(browser, url, grid);
		driver = baseMethods.getDriver();
	}

//	@Test
//	public void testHomeButton() {
//		String urlBeforeHomeButton = "https://www.amazon.com/ref=nav_logo";
//		driver.findElement(By.id("nav-logo-sprites")).click();
//		String urlAfterHomeButton = driver.getCurrentUrl();
//		assertTrue(urlBeforeHomeButton.equals(urlAfterHomeButton), "Url of page not valid!");
//	}
//
//	@Test
//	public void testTitle() {
//		String expectedTitle = "Amazon.com. Spend less. Smile more.";
//		String actualTitle = driver.getTitle();
//
//		assertEquals(actualTitle, expectedTitle, "Title of page not valid!");
//	}
//	
//	@Parameters({"productName"})
//	@Test
//	public void testSearchBar(String productName) {
//		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(productName);
//		String actualSearchBarValue = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).getAttribute("value");
//		assertEquals(actualSearchBarValue, productName, "Item searched is not valid!");
//	}
	
//	
//	@Test
//	public void testDropDownMenu() {
//		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Iphone 14");
//	}
	
//	@Test
//	@Parameters({"testUsername", "testPassword"})
//	public void testSignIn(String username, String password) {
//		method.login(username, password, driver);
//	}

	// get product link, product name, product price, possibly image, possibly
	// image, rank based on request
	// -> output data to basic html website.
	// use data driven xml file to get product request name
	// possible improvements incorporate cucumber

	@Test
	public void getSearchResults() {
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Iphone 14");
		driver.findElement(By.id("nav-search-submit-button")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
		List<WebElement> searchLinks = driver.findElements(By.xpath(
				"//a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal']"));
		List<WebElement> prices = driver.findElements(By.xpath("//span[@class='a-price']/span[1]"));
		HashMap<String, String> searchResult = new HashMap<String, String>();
		for (WebElement link : searchLinks) {
			for (WebElement price : prices) {
				searchResult.put(link.getText(), price.getAttribute("innerText"));
			}
		}
		try {
			method.writeToFile(searchLinks, searchResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void tearDown() {
		baseMethods.quitBrowser();
	}
}
