package com.scart.utilities;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class ExtractConfigurationProperties {

	private Properties p;
	
	public ExtractConfigurationProperties()  {

        p = new Properties();
        FileReader f = null;
        String path=System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config.properties";
		try {
			//f = new FileReader("./src//test//resources//config.properties");
			f = new FileReader(path);
			// implemented absolute path in flexible manner instead of relative path
	        p.load(f);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       // p.load(f);
	}
	
	
	
	
	
	public String getUrl() {
		return p.getProperty("applicationURL");
	}
	
	
	
	public String getUserName() {
		return p.getProperty("userName");
	}
	
	
	public String getDropDownItemValue() {
		return p.getProperty("dropdownItemValue");
	}
	
	
	public String getPassword() {
		return p.getProperty("password");
	}
	
	
	public String getOrdersURL() {
		return p.getProperty("ordersURL");
	}
	
	
	
	public String gethomePageURL() {
		return p.getProperty("homePageURL");
	}
	
	
	public String getAccessKey() {
		return p.getProperty("accessKey");
	}
	
	public String getSecretAccess() {
		return p.getProperty("secretAccessKey");
	}
	
	
}
