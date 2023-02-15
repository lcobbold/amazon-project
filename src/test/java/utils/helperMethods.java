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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVWriter;


public class HelperMethods extends Amazon_base {

	public void writeToFile(List<WebElement> links, HashMap<WebElement, String> results, String fileName)
			throws IOException {
		PrintWriter outFile;
		CSVWriter csvWriter;
		if (fileName.substring(fileName.length() - 3).equalsIgnoreCase("txt")) {
			try {
				outFile = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
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
				System.out.println("Successfully wrote search results to txt file.");
			} catch (IOException e) {
				System.err.println("Error opening output file");
				return;
			}
			outFile.close();
		} else if (fileName.substring(fileName.length() - 3).equalsIgnoreCase("csv")) {
			try {
				csvWriter = new CSVWriter(new FileWriter(fileName));
				String[] header = {"Product Name", "Product Link", "Price"};
				csvWriter.writeNext(header);
				for (Map.Entry<WebElement, String> result : results.entrySet()) {
					WebElement product = result.getKey();
					String productLink = product.getAttribute("href");
					String productName = product.getText();
					String productPrice = result.getValue();
					String[] data = {productName, productLink, productPrice};
					csvWriter.writeNext(data);
				}
				System.out.println("Successfully wrote search results to csv file.");
			} catch (IOException e) {
				System.err.println("Error opening output file");
				return;
			}
			csvWriter.close();
		}
		else {
			System.out.println("Invalid file format provided");
		}
		
		
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
