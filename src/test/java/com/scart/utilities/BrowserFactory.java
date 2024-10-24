package com.scart.utilities;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {

	public WebDriver driver;

	public  WebDriver getBroswer(String browser)
	{



		switch (browser.toLowerCase()) {
		case "chrome":

			driver = new ChromeDriver(BrowserCustomization.customizeChrome());
			break;

		case "edge":
			driver = new EdgeDriver(BrowserCustomization.customizeEdge());
			break;
		case "firefox":
			driver = new FirefoxDriver(BrowserCustomization.customizeFirefox());
			break;

		default:
			System.out.println("Browser Not Supported...");

			return null;
		}

		
		 	driver.get(new ExtractConfigurationProperties().getUrl());
	        driver.manage().deleteAllCookies();
	        driver.navigate().refresh();
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
	        return driver;

	}



}
