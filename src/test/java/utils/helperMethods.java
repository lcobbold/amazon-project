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

public class helperMethods extends Amazon_base{
	WebDriver driver;
	
	/**public void filePrint() {
		Scanner in = new Scanner(System.in);
        //Ask user for output text name
        System.out.print("Name of an output file: ");
        String outFileName = in.nextLine();

        FileWriter outFile;
        try {
            outFile = new FileWriter(outFileName);
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.err.println("Error opening output file");
            return;
        }
        /*
         * Close input and output streams
         
        in.close();
        try {
            outFile.close();
        } catch (IOException e) {
            System.err.println("Error closing file");
        }
    }
**/
	public void writeToFile(List<WebElement> links, HashMap<String, String> results) throws IOException {
		PrintWriter outFile;
        try {
            outFile = new PrintWriter(new BufferedWriter(new FileWriter("file.txt")));
            for (Map.Entry<String, String> result : results.entrySet()) {
            	String productName = result.getKey();
            	String productPrice = result.getValue();
            	outFile.println(productName);
            	outFile.println(productPrice);
            	for(WebElement link:links) {
                    outFile.println("Product link: " + link.getAttribute("href"));
                    outFile.println("******************************************************************************");
                }
            }
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
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

}
