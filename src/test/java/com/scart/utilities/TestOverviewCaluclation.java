package com.scart.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;



public class TestOverviewCaluclation {

	

    public static void generateTestOverview(ISuite suite) {
        StringBuilder overview = new StringBuilder();
        Map<String, ISuiteResult> suiteResults = suite.getResults();
        
        // Execution Statistics
        int totalTests = 0;
        int passedTests = 0;
        int failedTests = 0;
        int skippedTests = 0;
        long totalDuration = 0;
        Set<ITestResult> allFailedTests = null;
        
        // Collect statistics
        for (ISuiteResult result : suiteResults.values()) {
            ITestContext context = result.getTestContext();
            totalTests += context.getPassedTests().size()+context.getFailedTests().size()+context.getSkippedTests().size();
            passedTests += context.getPassedTests().size();
            failedTests += context.getFailedTests().size();
            skippedTests += context.getSkippedTests().size();
            totalDuration += (context.getEndDate().getTime() - context.getStartDate().getTime());
            
            if (failedTests > 0) {
                allFailedTests = context.getFailedTests().getAllResults();
            }
        }

        // Format execution time
        long minutes = totalDuration / (1000 * 60);
        long seconds = (totalDuration / 1000) % 60;

        // Build overview paragraph
        overview.append("Hello ");
        overview.append(System.getProperty("user.name"));
  
        overview.append(" Now I am going to give Test Execution Overview for ").append(suite.getName()).append(". ");
        overview.append("The test suite was executed on ")
                .append(new SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a").format(new Date()))
                .append("in "+System.getProperty("os.name")+"operating system. ");

        overview.append("Total execution time was ")
                .append(minutes).append(" minutes and ")
                .append(seconds).append(" seconds. ");

        // Add test statistics
        overview.append("Out of ").append(totalTests).append(" total tests, ");
        overview.append(passedTests).append(" tests passed successfully, ");
        overview.append(failedTests).append(" tests failed, and ");
        overview.append(skippedTests).append(" tests were skipped. ");

        // Calculate success rate
        double successRate = (double) passedTests / totalTests * 100;
        overview.append("The overall success rate is ")
                .append(String.format("%.1f", successRate))
                .append(" percent. ");
        	//System.out.println(successRate);
        // Add failed test details if any
        if (failedTests > 0) {
            overview.append("The following tests failed: ");
            for (ITestResult failedTest : allFailedTests) {
                overview.append(failedTest.getName())
                        .append(" - ")
                        .append(failedTest.getThrowable().getMessage())
                        .append(". ");
            }
        }

        // Add test categories or groups if used
        List<String> groups = suite.getXmlSuite().getTests().get(0).getIncludedGroups();
        if (groups != null && groups.size() > 0) {
            overview.append("The test suite included the following groups: ");
            for (String group : groups) {
                overview.append(group).append(", ");
            }
        }
        	overview.append("This is the basic overview of results of recent Test Execution").append(".");
        	overview.append("Entire screen recording of Test Execution  is available here ").append(".");
        	overview.append("Thank You").append(".").append("Have a Nice day");
        
        
        generateEnhancedAudioReport(overview.toString(), suite.getName());
    }

    
    
    
    private static void generateEnhancedAudioReport(String reportText, String suiteName) {
       
    		
    	
    	AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "AKIA23WHUJD7M4NI3BVW", 
                "WZskS8GuKldsJp30MYYXEs2J85cGBYZ0F0LEjF0K"
            );

    	
    	
    	PollyClient polly = PollyClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.AP_SOUTH_1)  
                .build();
         
    
    
    	  SynthesizeSpeechRequest speechRequest = SynthesizeSpeechRequest.builder()
    	            .text(reportText)
    	            .voiceId("Aditi")  
    	            .outputFormat("mp3")
    	            .build();
    
    
    	  
    	  
    	  
    	  
    	  try (software.amazon.awssdk.core.ResponseInputStream<SynthesizeSpeechResponse> speechResponse = polly.synthesizeSpeech(speechRequest)) {
              InputStream audioStream = speechResponse;
   
              // Save the output to a file
              String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
      		String fileName = "S_CART_Test_Report_Overview" + timeStamp ;
   
      		File reportsDir = new File("AudioFiles");
      		if (!reportsDir.exists()) {
      			reportsDir.mkdir();
      		}
   
      		String fileName1 = "AudioFiles" + File.separator + fileName+".mp3" ;
              
              
              
              try (FileOutputStream outputStream = new FileOutputStream(fileName1)) {
                  byte[] buffer = new byte[2 * 1024];
                  int bytesRead;
                  while ((bytesRead = audioStream.read(buffer)) != -1) {
                      outputStream.write(buffer, 0, bytesRead);
                  }
                  //System.out.println("Audio file created successfully. LN-165");
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
    	  
    	  
    	  
    	  
    	  
    
    }
    

	
	
	
}
