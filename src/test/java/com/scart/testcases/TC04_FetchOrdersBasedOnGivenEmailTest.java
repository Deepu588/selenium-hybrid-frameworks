package com.scart.testcases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Parameters;
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
import com.scart.utilities.PaginationHelperMethods;
import com.scart.utilities.TestHelperMethods;

public class TC04_FetchOrdersBasedOnGivenEmailTest extends BaseClass {

	HomePage hp;
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


	@Parameters("emailText")
	@Test(dependsOnMethods = "verifyLogin")
	public void filterOrdersListBasedOnEnteredEmail(String emailText) {
		ExtentTest t=	  MyListener.getTest();
		hp=  new HomePage(driver);
		waitForOrdersList();
		t.info("Just Navigated to orders page");
		hp.enterAndClick(emailText);	
		t.info(MarkupHelper.createLabel("Email entered in search bar ="+emailText, ExtentColor.BROWN));
		waitForSpinnerToDisappear();
		t.info("After filtering the resultant mails list given below");
		t.info(MarkupHelper.createOrderedList(hp.getMailIdsList()));
		assertTrue(	TestHelperMethods.checkText(hp.getMailsList(),emailText),"Filtration Failed");

	}


	@Test(dependsOnMethods = "verifyLogin")
	public void sortOrdersListBasedonAscendingOrderOfDate() {
		ExtentTest t=MyListener.getTest();
		hp=new HomePage(driver);
		waitForOrdersList();
		t.info("Just clicked order link in menu section");
		hp.selectByValueInDropdown(new ExtractConfigurationProperties().getDropDownItemValue());
		t.info("selected dropdown item which arranges the table based on the ascending order ");
		hp.clickSearchBtn();
		waitForSpinnerToDisappear();
		t.info(MarkupHelper.createUnorderedList(hp.getDateCellsInString()));
		List<Date> allDates=TestHelperMethods.convertStringInToDate(hp.getDateCells());
		t.info("checking above dates are in ascending order or not");
		assertTrue(TestHelperMethods.checkOrder(allDates),"Table Rows are not sorted based on ascending order of dates");
	}




	@Test(dependsOnMethods = "verifyLogin")
	public void getOrdersTableDataAndWriteIntoExcel() throws IOException {
		ExtentTest t=MyListener.getTest();
		hp=new HomePage(driver);
		waitForOrdersList();
		t.info("Just Navigated to Orders Section");
		PaginationHelperMethods helper=	new PaginationHelperMethods();
		helper.writeHeaders();
		t.info("Wrote Table Headers in to Excel File");
		helper.writeTableData(hp.getRows());	
		for(int i=3;i<=4;i++) {
			hp.getPageNo(i);
			t.info("Navigated to "+(i-1)+" Page in Orders Table");
			waitForSpinnerToDisappear();
			helper.writeTableData(hp.getRows());	
		}
		helper.saveWorkbook();
		t.info(MarkupHelper.createLabel("Wrote "+(helper.getLastRowNum())+"  Orders (rows) in Excel file", ExtentColor.CYAN));
		assertTrue(helper.getLastRowNum()==hp.getTotalRowsCount(),"Unable to write Orders data in to Excel file");

	}


















	

}
