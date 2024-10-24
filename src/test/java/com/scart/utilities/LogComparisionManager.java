package com.scart.utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class LogComparisionManager {

	
	public static void logComparison(ExtentTest t, String fieldName, String actual, String expected) {
	    boolean isMatch = actual.equals(expected);
	    t.log(
	        isMatch ? Status.PASS : Status.FAIL,
	        MarkupHelper.createLabel(
	            String.format("Actual %s=%s || Expected %s=%s", fieldName, actual, fieldName, expected),
	            isMatch ? ExtentColor.GREEN : ExtentColor.RED
	        )
	    );
	}

	
}
