package com.scart.testcases;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.scart.pageobjects.HomePage;
import com.scart.pageobjects.LoginPage;
import com.scart.testbase.BaseClass;
import com.scart.utilities.ExtractConfigurationProperties;
import com.scart.utilities.LogComparisionManager;
import com.scart.utilities.MyListener;
import com.scart.utilities.TestHelperMethods;
import com.scart.utilities.ThreadPoolUtilityForBrokenLinks;

public class TC03_VerifyLinksInMenuSectionTest extends BaseClass
{
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



	@Test(dependsOnMethods = "verifyLogin")
	public void checkBrokenLinks() {

		HomePage h=  new HomePage(driver);
		ExtentTest t=	MyListener.getTest();
		t.info("Navigated to Home Page ");
		List<WebElement>linksInMenuSection=h.getLinks();
		t.info("Accessed  links present in menu section");
		t.info(MarkupHelper.createLabel("No of Links in Menu Section ="+linksInMenuSection.size(), ExtentColor.BROWN));
		List<String> allHyperLinks=	h.getHrefValueInMenuSections();
		t.info(MarkupHelper.createUnorderedList(allHyperLinks));
		Assert.assertTrue(ThreadPoolUtilityForBrokenLinks.checkLinksValues(allHyperLinks),"One of the link has invalid URL");
		
		
		Assert.assertTrue(ThreadPoolUtilityForBrokenLinks.usingThreads(allHyperLinks),"There is a invalid link in the menu section");
		
	}
	
	
	
	
	
	@Test(dependsOnMethods = "verifyLogin")
	public void verifySelectAllCheckboxFunctionality() {
		HomePage h=  new HomePage(driver);
		ExtentTest t=	MyListener.getTest();
		waitForOrdersList();
		t.info("Navigated to Orders page");
		h.selectHeadCheckBox();
		t.info("selected checkbox which is at top of web table");
		Assert.assertTrue(TestHelperMethods.isCheckBoxSelected(h.getAllCheckBoxes())," Select All CheckBox functionality is Failed");
	}
	


}
