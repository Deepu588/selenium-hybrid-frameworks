package com.scart.utilities;

import com.aventstack.extentreports.ExtentTest;

public class TestDetailsAssigner {


	public static void assignDetailsToTest(ExtentTest test ,String testmethodName) {
		test.assignAuthor(System.getProperty("user.name"));
	//	test.assignDevice(System.getProperty("os.name")+" Laptop");
		switch (testmethodName) {
		
		case "verifyLogin":{
			test.assignCategory("Functionality");
			test.getModel().setDescription("This test verifies the login functionality by entering valid user credentials and submitting the login form. It checks whether the user is successfully redirected to the dashboard or homepage. If the login is successful, the test passes; otherwise, it fails.");
			break;
		}
		
		case "loginAndLogOutTest":{
			test.assignCategory("Functionality");

			test.getModel().setDescription("This test validates both login and logout functionalities. It first verifies successful login by providing valid credentials, then proceeds to click the logout button to ensure the user is logged out and redirected to the login page. A successful logout indicates a pass.");
			break;
		}
		case "selectThemeOption":{
			test.assignCategory("UI");
			test.getModel().setDescription("This test checks the theme selection functionality. It clicks on the theme dropdown and selects the 'Pink' option. The background color of the page should change to pink. The test passes if the background color matches the expected pink color; otherwise, it fails.");
			break;
		}
		case "menuButtonTest":{
			test.assignCategory("UI");
			test.getModel().setDescription("This test validates the functionality of the hamburger menu button. When clicked, the side menu bar should collapse, reducing its width. The test passes if the side menu bar collapses as expected, indicating the functionality works correctly.");
			break;
		}
		case "filterOrdersListBasedOnEnteredEmail":{
			test.assignCategory("Functionality");

			test.getModel().setDescription("This test checks the order filtering functionality based on the entered email address. After entering an email and clicking search, only the orders related to that email should be displayed in the orders list table. The test passes if the displayed table contains only orders matching the entered email.");
			break;
		}
		case "sortOrdersListBasedonAscendingOrderOfDate":{
			test.assignCategory("Functionality");

			test.getModel().setDescription("This test verifies the sorting functionality of the orders list table. It selects the option to sort the orders by the ascending order of the date. Each row in the table is then examined to ensure the dates are in the correct sequence, with the earliest date appearing first. The test passes if all table cells follow the expected ascending order; otherwise, it fails.");
			break;
		}
		
		case "checkBrokenLinks":{
			test.assignCategory("Functionality");
			test.getModel().setDescription("This test checks for broken links within the menu section of the webpage. It collects the href attribute values of all anchor tags and converts them into URLs. For each URL, an HTTP connection is established, and the response code is checked. If any response code is 400 or greater, the link is considered broken. The test fails if at least one invalid link is found, indicating that there are broken links on the page.");
			break;
		}
		
		case "verifySelectAllCheckboxFunctionality":{
			test.assignCategory("UI");
			test.getModel().setDescription("This test validates the 'Select All' checkbox functionality within a web table. When the main checkbox at the top of the table is selected, all individual row checkboxes should also be selected. The test passes if selecting the 'Select All' checkbox correctly selects all row checkboxes; otherwise, the test fails.");
			break;
		}
		case "verifyGridItemDimensionsConsistency":{
			test.assignCategory("UI");
			test.getModel().setDescription("This test checks for consistency in the dimensions of items within a grid layout. It calculates the width and height of each item in the grid. The test passes if all items have the same dimensions, ensuring uniformity in layout presentation; otherwise, the test fails.");
			break;
		}
		
		case "getOrdersTableDataAndWriteIntoExcel":{
			test.assignCategory("Functionality");
			test.getModel().setDescription(" test method captures all rows from the 'Orders List' table across multiple pages, writes them into an Excel file, and dynamically handles pagination. The test passes if the last row number in the Excel file matches the total element count from the web page; otherwise, the test fails.");
			break;
		}
		

		default:
			break;
		}


	}





}
