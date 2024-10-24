package com.scart.testbase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.model.Media;
import com.scart.utilities.BrowserFactory;
import com.scart.utilities.ExtractConfigurationProperties;


public class BaseClass {

	public WebDriver driver;
	public WebDriverWait wait;
	public Logger logger;
	private String browserName;
	private String browserVersion;

	@Parameters("browser")
	@BeforeClass
	public void setUp(String browser) throws Exception {
		driver=	new BrowserFactory().getBroswer(browser);
		wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		logger = LogManager.getLogger(this.getClass());
		browserName= ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
		browserVersion=((RemoteWebDriver) driver).getCapabilities().getBrowserVersion();
	}


	@AfterClass
	public void tearDown() {
		driver.quit();
	}


	
	
	public String getBrowserName() {
		return browserName;
	}


	public String getBrowserVersion() {
		return browserVersion;
	}


	

	public String getUrl() {
		return driver.getCurrentUrl();
	}

	public boolean isPageLoaded() {
		return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
	}




	public String captureScreen(String tName) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String destinationFilePath = System.getProperty("user.dir") + File.separator+ "FailedTestScreenshots" + File.separator+ tName + "_" + timeStamp + ".png";
		File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(destinationFilePath);

		try {
			destinationFile.getParentFile().mkdirs();
			Files.copy(sourceFile.toPath(), destinationFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("Screenshot saved at: " + destinationFilePath);

		return destinationFilePath;
	}


	
	
	
	
	
	public String captureScreenBase64(String tName) {
		
		
	return	((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		
	}

	
	public String captureWebElementBase64(WebElement element) {
		return  element.getScreenshotAs(OutputType.BASE64);
	}
	
	
	
	
	public void performClick(WebElement element) {

		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		
	}



	
	
	
 public void waitForTableToSortByDate(String intialFirstRowText,WebElement element) {
		 
//		 wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(
//	               element, intialFirstRowText)));

	 wait.until(ExpectedConditions.elementToBeClickable(element));
	 }
	
	
 
 
 
 public boolean waitForHomePage() {
		try {
			 ExtractConfigurationProperties p=	new ExtractConfigurationProperties();

			wait.until(ExpectedConditions.urlToBe(p.gethomePageURL()));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

 
 public boolean waitForLoginPage() {
		try {
			 ExtractConfigurationProperties p=	new ExtractConfigurationProperties();

			wait.until(ExpectedConditions.urlToBe(p.getUrl()));
			return true;
		} catch (Exception e) {
			return false;
		}
	}


 
 
 
 
 public void waitForOrdersList() {
	 ExtractConfigurationProperties p=	new ExtractConfigurationProperties();
	
	 driver.navigate().to(p.getOrdersURL());
		wait.until(ExpectedConditions.urlToBe(p.getOrdersURL()));
	
	 
	 

 }
 
 
 
 

 
 public void waitForSpinnerToDisappear() {
     wait.until(ExpectedConditions.invisibilityOfElementLocated(
         By.xpath("//div[@id='loading' and not(contains(@style,'none'))]")));
 }
 
 

}
