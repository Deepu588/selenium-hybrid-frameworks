package com.scart.testcases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.scart.pageobjects.HomePage;
import com.scart.pageobjects.LoginPage;
import com.scart.testbase.BaseClass;
import com.scart.utilities.ExtractConfigurationProperties;
import com.scart.utilities.LogComparisionManager;
import com.scart.utilities.LoginDataProvider;
import com.scart.utilities.MyListener;

public class TC01_LoginAndLogOutTest extends BaseClass {
	@Test(dataProvider = "loginData", dataProviderClass = LoginDataProvider.class)
	public void loginAndLogOutTest(String u, String p) {

		LoginPage l = new LoginPage(driver);
		ExtentTest t=	MyListener.getTest();
		ExtractConfigurationProperties p1=	new ExtractConfigurationProperties();


		t.info(	MarkupHelper.createLabel("Current  URL= "+driver.getCurrentUrl(), ExtentColor.BROWN));


		l.resetUserName();
		l.resetPassword();
		t.info("cleared Data in Fields");

		l.setUserName(u);
		l.setPassword(p);
		l.clickSubmit();
		
		LogComparisionManager.logComparison(t,"username", u, p1.getUserName());
		LogComparisionManager.logComparison(t,"password", p, p1.getPassword());


		
		assertTrue(waitForHomePage(), "Invalid Credentials");
		t.pass("Logged In successfully ");

		HomePage hp= new HomePage(driver);

		performClick(hp.getAvatar());
		performClick(hp.getLogoutLink());

		t.info("Just clicked Logout button");
		waitForLoginPage();
		assertTrue(getUrl().equals(p1.getUrl()),"Logout Failed");

	}










}
