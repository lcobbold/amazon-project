package utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Amazon_base { // main class
	WebDriver driver;

//	public Amazon_base() { // this is a Constructor class
//		int x = 5;
//		int y = 5;
//		System.out.println(x);
//		System.out.println(y);
//		System.out.println(x + y);
//
//	}

	public void gridSetupOrWebdriver(String browser, String url, boolean grid) {
		if (grid) {
			gridSetup(url);
		}
		else {
			amazonSetup(browser, url);
		}
	}

	public void gridSetup(String url) {
		ChromeOptions option = new ChromeOptions();
		driver = null;
		
		
		try {
			driver = new RemoteWebDriver(new URL("http://localhost:4444"), option);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			driver.get(url);
			System.out.println("Website title - " + driver.getTitle());
			
		}

	public void amazonSetup(String browser, String url) { // this is your main method

		String userDir = System.getProperty("user.dir");
//		Scanner scr = new Scanner(System.in);
//		System.out.println("Enter browserName: "); // Asks user for browser
//		String browser = scr.nextLine();
//		System.out.println("Enter a valid url: ");
//		String url = scr.nextLine();

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", userDir + "\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.firefox.driver", userDir + "\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		if (url.equals("")) {
			driver.get("about:blank");
		} else {
			driver.get(url);
		}

	}

	public void quitBrowser() {
		driver.quit();
	}

	public static void main(String[] args) {
		Amazon_base base = new Amazon_base();
		base.gridSetupOrWebdriver("chrome", "https://www.amazon.com/",true);
	}

}
