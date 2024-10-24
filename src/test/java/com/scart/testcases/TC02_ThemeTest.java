package com.scart.testcases;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.scart.pageobjects.HomePage;
import com.scart.pageobjects.LoginPage;
import com.scart.testbase.BaseClass;
import com.scart.utilities.ExtractConfigurationProperties;
import com.scart.utilities.LogComparisionManager;
import com.scart.utilities.MyListener;
import com.scart.utilities.TestHelperMethods;

public class TC02_ThemeTest extends BaseClass {
	HomePage h;

	@Test
	public void verifyLogin() {
		ExtractConfigurationProperties e=	new ExtractConfigurationProperties();
		LoginPage login = new LoginPage(driver);
		ExtentTest t=	MyListener.getTest();
		login.resetPassword();
		login.resetUserName();
		login.setUserName(e.getUserName());
		login.setPassword(e.getPassword());
		LogComparisionManager.logComparison(t,"username", e.getUserName(), e.getUserName());
		LogComparisionManager.logComparison(t,"password", e.getPassword(), e.getPassword());
		login.clickSubmit();
		t.info("Just clicked submit button");
		wait.until(ExpectedConditions.urlToBe(e.gethomePageURL()));
		assertTrue(getUrl().equals(e.gethomePageURL()), "Invalid credentials");

	}


	@Test (dependsOnMethods = "verifyLogin")
	public void selectThemeOption() {
		ExtentTest t=	MyListener.getTest();
		h = new HomePage(driver);
		h.clickThemeOption();
		t.info("clicked on Theme Option to select bg color");
		h.selectDropDownItem();
		t.info("just clicked on dropdown item");
		WaitForPage();
		t.info("checking the theme color");
		t.info(MarkupHelper.createLabel("Obtained Background color is "+h.getBgColorOfMainHeader(),ExtentColor.TEAL));
		t.info(MarkupHelper.createLabel("Expected Background colors are "+"rgb(232, 62, 140)   OR  rgba(232, 62, 140, 1) ", ExtentColor.GREEN));
		assertTrue(h.getBgColorOfMainHeader().equals("rgb(232, 62, 140)") || h.getBgColorOfMainHeader().equals("rgba(232, 62, 140, 1)"));
	}



	@Test (dependsOnMethods = "verifyLogin")
	public void menuButtonTest() {
		HomePage hp= new HomePage(driver);
		ExtentTest t=	MyListener.getTest();
		t.info("Menu Test Started just now ");
		t.log(Status.INFO,MarkupHelper.createLabel("Before clicking hamberger menu ", ExtentColor.BROWN),MediaEntityBuilder.createScreenCaptureFromBase64String(captureWebElementBase64(hp.getSideBar()), "Side Bar").build());
		hp.clickMenu();
		t.info("clicked menu button");
		t.log(Status.INFO,MarkupHelper.createLabel("After clicking hamberger menu ", ExtentColor.BROWN),MediaEntityBuilder.createScreenCaptureFromBase64String(captureWebElementBase64(hp.getSideBar()), "Side Bar").build());
		assertTrue(	hp.getClassAttributeForBodyTag().contains("sidebar-collapse"));
		t.pass("menu button is working properly");
	}


	@Test(dependsOnMethods = "verifyLogin")
	public void verifyGridItemDimensionsConsistency() {
		HomePage hp= new HomePage(driver);
		ExtentTest t=	MyListener.getTest();
		hp.clickMenu();
		t.info("Expanded Menu Section ");
		t.info("Caluclating the dimensions of Items in Grid box");
		t.info(MarkupHelper.createJsonCodeBlock(TestHelperMethods.getAllDimensionsAsMap(hp.getGridItems())));
		assertTrue(TestHelperMethods.check(hp.getGridItems()),"Grid Items Dimensions are Not Consistent");
	}





	public boolean WaitForPage() {
		try {

			wait.until(driver -> isPageLoaded());
			return true;
		} catch (Exception e) {
			return false;
		}
	}


}
