package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class helperMethods extends Amazon_base {

	public void writeToFile(List<WebElement> links, HashMap<WebElement, String> results) throws IOException {
		PrintWriter outFile;
		try {
			outFile = new PrintWriter(new BufferedWriter(new FileWriter("file.txt")));
			for (Map.Entry<WebElement, String> result : results.entrySet()) {
				WebElement product = result.getKey();
				String productName = product.getText();
				String productPrice = result.getValue();
				outFile.println(productName);
				outFile.println(productPrice);
				outFile.println("Product link: " + product.getAttribute("href"));
				outFile.println("******************************************************************************");
				outFile.println();
			}
			System.out.println("Successfully wrote search results to file.");
		} catch (

		IOException e) {
			System.err.println("Error opening output file");
			return;
		}

		outFile.close();
	}

	public void login(String username, String password, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		driver.findElement(By.xpath("//a[@id='nav-link-accountList']")).click();
		wait = new WebDriverWait(driver, Duration.ofSeconds(3));

		driver.findElement(By.id("ap_email")).sendKeys(username);
		driver.findElement(By.id("continue")).click();

		driver.findElement(By.id("ap_password")).sendKeys(password);
		driver.findElement(By.id("signInSubmit")).click();
	}

	/**
	 * This method is currently not in use
	 */
	public void addToCart() {
		driver.findElement(By.id("add-to-cart-button")).click();
	}

}
