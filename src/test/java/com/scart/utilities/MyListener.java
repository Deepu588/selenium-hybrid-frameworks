package com.scart.utilities;
//package utils;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
//import base.BaseClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.scart.testbase.BaseClass;

public class MyListener implements ITestListener,ISuiteListener {
	
	private ExtentReports extent;
	private String reportPath;
	ExtentSparkReporter sparkReporter;
	public Set<String> browserNames=new HashSet<String>();
	public Set<String> browserVersions=new HashSet<String>();
	private static ThreadLocal<ExtentTest> test=new ThreadLocal<>();
	private static ThreadLocal<BaseClass> baseClass = new ThreadLocal<>();
    private Map<String, List<Long>> testDurations = new LinkedHashMap<>();
    private Map<String, List<Integer>> testStatuses = new LinkedHashMap<>();
	@Override
	public void onStart(ISuite suite) {
		
		
		 if (extent == null) {
	            synchronized (MyListener.class) {
	                if (extent == null) {
		
		
		try {
			ScreenRecorderUtil.startRecord("S-CART Recording");
		} catch (Exception e) {
			e.printStackTrace();
		}

		reportPath=ExtentReportManager.getReportPath();
		extent = new ExtentReports();
	
		sparkReporter=ExtentReportManager.createSparkReporter(  reportPath);
		extent.attachReporter(sparkReporter);
		ExtentReportManager.setSystemInformation(extent);
	}

	            }}}
	                
	@Override
	public void onTestStart(ITestResult result) {
		
		ExtentTest   test1 = extent.createTest(result.getMethod().getMethodName());
		test1.log(Status.INFO, result.getMethod().getMethodName()+" is started just now ");
		test.set(test1);
	        String testName = result.getMethod().getMethodName();
	        TestDetailsAssigner.assignDetailsToTest(test1, testName);


		baseClass.set((BaseClass) result.getInstance());      
		if (baseClass.get() != null) {
			
			String browserName = baseClass.get().getBrowserName();
			test1.assignDevice(System.getProperty("os.name")+" -"+browserName);
			String browserVersion = baseClass.get().getBrowserVersion();

			if (browserName != null && browserVersion != null) {
				browserNames.add(browserName);		
				browserVersions.add(browserVersion);
			}
		}
	}

	
	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().log(Status.PASS, "Test case: " + result.getMethod().getMethodName() + " Passed");
        recordTestResult(result, 1);  // Status 1 for pass
        //count++;
	}





	@Override
	public void onTestFailure(ITestResult result) {
		test.get().log(Status.FAIL, "Test case: " + result.getMethod().getMethodName() + "  Failed");
		test.get().log(Status.FAIL, result.getThrowable());
        recordTestResult(result, 2);  // Status 1 for pass
       // count++;
		try {
			BaseClass baseClass = (BaseClass) result.getInstance();
		
	        String base64Screenshot = baseClass.captureScreenBase64(result.getMethod().getMethodName());

            test.get().log(Status.FAIL, "Screenshot of failure",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			
		} catch (Exception e) {
			test.get().log(Status.FAIL, "Failed to capture screenshot: " + e.getMessage());
		}
	}





	@Override
	public void onTestSkipped(ITestResult result) {
		test.get().log(Status.SKIP, "Test case: " + result.getMethod().getMethodName() + " Skipped");
        System.out.println(result.getThrowable());
		recordTestResult(result, 3);  // Status 1 for pass
		//count++;
	}

	@Override
	public void onFinish(ISuite suite) {

	
				try {
					ScreenRecorderUtil.stopRecord();
				//this is the line for generating audio	
				TestOverviewCaluclation.generateTestOverview(suite);	
					File f=new File("GraphImages");
					if(!f.exists()) {
						f.mkdir();
					}
					// I am adding this newly 
					 String outputPath =System.getProperty("user.dir")+File.separator	+ "GraphImages/TestExecutionResultGraph"+new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss").format(new Date()) +".png"; // Customize the path
					 Map<String, Long> flatDurations = new LinkedHashMap<>();
			            Map<String, Integer> flatStatuses = new LinkedHashMap<>();
			            
			            for (Map.Entry<String, List<Long>> entry : testDurations.entrySet()) {
			                String testName = entry.getKey();
			                List<Long> durations = entry.getValue();
			                List<Integer> statuses = testStatuses.get(testName);
			                
			                for (int i = 0; i < durations.size(); i++) {
			                    String key = testName + " (Run " + (i + 1) + ")";
			                    flatDurations.put(key, durations.get(i));
			                    flatStatuses.put(key, statuses.get(i));
			                }
			            }
			            
			            ChartGenerator.generateBarChart(flatDurations, flatStatuses, outputPath);				} catch (Exception e) {
					e.printStackTrace();
				}
				sparkReporter.config().setJs(ExtentReportManager.getJs());
				extent.setSystemInfo("Browser Name ", browserNames.toString());
				extent.setSystemInfo("Browser Version ", browserVersions.toString());
				extent.setSystemInfo("Execution Time ", new SimpleDateFormat("dd.MM.yyyy.hh-mm-ss").format(new Date()));
			  
				
				 
				
				extent.flush();
				File reportFile = new File(reportPath);
					try {
						Desktop.getDesktop().browse(reportFile.toURI());
						//if you need to implement automatic mail also then do some changes for variable called from,to and password in Mail Sender class
						//MailSender.sendEmailWithAttachment(ExtentReportManager.getLatestHtmlFile());  //   this line is responsible for sending mail
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
		
	
	
	}
	

	private void recordTestResult(ITestResult result, int status) {
	    String testName = result.getMethod().getMethodName();
	    long duration = result.getEndMillis() - result.getStartMillis();
	    
	    // Add duration to the list instead of trying to set at a specific index
	    testDurations.computeIfAbsent(testName, k -> new ArrayList<>()).add(duration);
	    testStatuses.computeIfAbsent(testName, k -> new ArrayList<>()).add(status);
	}
	
	
	public static ExtentTest getTest() {
		return test.get();
	}
}