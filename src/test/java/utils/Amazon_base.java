package utils;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Amazon_base {
	WebDriver driver;

	public void gridSetupOrWebdriver(String browser, String url, String setupType) {
		if (setupType.equalsIgnoreCase("grid")) {
			gridSetup(url);
		} else if(setupType.equalsIgnoreCase("web driver")) {
			amazonSetup(browser, url);
		}
		else {
			System.out.println("Invalid setup provided!");
		}
	}

	public void gridSetup(String url) {
		ChromeOptions option = new ChromeOptions();
		driver = null;

		try {
			driver = new RemoteWebDriver(new URL("http://localhost:4444"), option);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		driver.get(url);
		System.out.println("Website title - " + driver.getTitle());

	}

	public void amazonSetup(String browser, String url) {
		String userDir = System.getProperty("user.dir");
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", userDir + "\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.firefox.driver", userDir + "\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", userDir + "\\drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
		if (url.equals("")) {
			driver.get("about:blank");
		} else {
			driver.get(url);
		}

	}

	//encapsulation
	public WebDriver getDriver() {
		return this.driver;
	}

	public void quitBrowser() {
		driver.quit();
	}
}
