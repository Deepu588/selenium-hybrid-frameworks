package com.scart.utilities;

import java.util.Map;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserCustomization {


	public static ChromeOptions customizeChrome() {
		
		ChromeOptions options=	new ChromeOptions();
		
		options.setAcceptInsecureCerts(true);
		
		options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
		
		options.addArguments("--disable-popup-blocking");
		
		options.addArguments("--disable-notifications");
		
		options.addArguments("--disable-gpu");
		
		options.addArguments("--start-maximized");
		options.setExperimentalOption("prefs", Map.of(
			        "credentials_enable_service", false,
			        "profile.password_manager_enabled", false
			    ));
		
		return options;

	}




	public static EdgeOptions customizeEdge() {

		EdgeOptions options = new EdgeOptions();

		options.setAcceptInsecureCerts(true);

		options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});

		options.addArguments("start-maximized");

		options.addArguments("--disable-popup-blocking");

		options.addArguments("--disable-notifications");


		options.addArguments("--disable-gpu");
		
		 options.setExperimentalOption("prefs", Map.of(
			        "credentials_enable_service", false,
			        "profile.password_manager_enabled", false
			    ));


		return options;
	}






	public static FirefoxOptions customizeFirefox() {
		
	    FirefoxOptions options = new FirefoxOptions();

	    options.setAcceptInsecureCerts(true);

	    options.addArguments("--start-maximized");

	    options.addPreference("dom.popup_maximum", 0);

	    options.addPreference("dom.webnotifications.enabled", false);

	 
	    options.addPreference("layers.acceleration.disabled", true);

	    options.addPreference("signon.rememberSignons", false);

	    return options;

	}







}





