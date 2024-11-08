package com.scart.utilities;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {



	public static String getReportPath() {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportName = "S-CART_Test_Report-" + timeStamp + ".html";

		File reportsDir = new File("reports");
		if (!reportsDir.exists()) {
			reportsDir.mkdir();
		}

		return       System.getProperty("user.dir")+ File.separator+ "reports" + File.separator + reportName;
	}


	
	
	 public static String getLatestMp4File() {
	        File dir = new File(System.getProperty("user.dir"), "Test-Recordings");
	        
	        File[] mp4Files = dir.listFiles((d, name) -> name.endsWith(".mp4"));
	        
	        if (mp4Files != null && mp4Files.length > 0) {
	            Arrays.sort(mp4Files, Comparator.comparingLong(File::lastModified).reversed());
	            return mp4Files[0].getAbsolutePath();
	        }
	        return null;
	    }
	
	
//	
	public static String getLatestAudioFile() {
		File dir = new File("AudioFiles/");

		File[] audioFiles = dir.listFiles((d, name) -> name.endsWith(".mp3"));

		Arrays.sort(audioFiles, Comparator.comparingLong(File::lastModified).reversed());

		if (audioFiles.length > 0) {
		//	System.out.println(  "Getting path to check 59---"  +"GraphImages/"+pngFiles[0].getName());
			return "AudioFiles/" + audioFiles[0].getName();  
		}
		return null;  
	}
	
	
	 public static String getLatestPngFile() {
	        File dir = new File(System.getProperty("user.dir"), "GraphImages");
	        
	        File[] pngFiles = dir.listFiles((d, name) -> name.endsWith(".png"));
	        
	        if (pngFiles != null && pngFiles.length > 0) {
	            Arrays.sort(pngFiles, Comparator.comparingLong(File::lastModified).reversed());
	            return pngFiles[0].getAbsolutePath();
	        }
	        return null;
	    }
	
	public static String getLatestHtmlFile() {
		File dir = new File("reports/");

		File[] htmlFiles = dir.listFiles((d, name) -> name.endsWith(".html"));

		Arrays.sort(htmlFiles, Comparator.comparingLong(File::lastModified).reversed());

		if (htmlFiles.length > 0) {
			//System.out.println(  "Getting path to check 70---"  +"reports/"+htmlFiles[0].getName());
			return "reports/" + htmlFiles[0].getName();  
		}
		return null;  
	}
	
	
	
	 public static String getLatestExcelFile() {
	        File dir = new File(System.getProperty("user.dir"), "OrdersList");
	        
	        File[] excelFiles = dir.listFiles((d, name) -> name.endsWith(".xlsx"));
	        
	        if (excelFiles != null && excelFiles.length > 0) {
	            Arrays.sort(excelFiles, Comparator.comparingLong(File::lastModified).reversed());
	            return excelFiles[0].getAbsolutePath();
	        }
	        return null;
	    }
	
	
	
	
	
	


	public static ExtentSparkReporter createSparkReporter(String path) {


		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(path);
		sparkReporter.config().setDocumentTitle(" S_CART Admin Automation Test Report");
		sparkReporter.config().setReportName("S_CART Test Results");
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setCss(getCss());
		
		return sparkReporter;
	}


	public static  ExtentReports   setSystemInformation(ExtentReports report) {
		report.setSystemInfo("Website Name", "S-Cart Admin");
		report.setSystemInfo("Tester Name", System.getProperty("user.name"));
		report.setSystemInfo("Reporting Manager", "Rajesh Buddha");
		report.setSystemInfo("Environment", "QA");
		report.setSystemInfo("OS Name", System.getProperty("os.name"));
		//report.setSystemInfo("OS Version", System.getProperty("os.version"));
		report.setSystemInfo("Java Version", System.getProperty("java.version"));
		report.setSystemInfo("Maven Version","3.9.7");
	
		return report;
	}






	public static String getJs() 
	{
//		String videoFilePath = getLatestMp4File();  
//		String pngFilePath=getLatestPngFile();
//		String audioFilePath=getLatestAudioFile();
//		String excelFilePath=getLatestExcelFile();
		
		
		
		
		String videoUrl = S3Uploader.uploadFileToS3(getLatestMp4File(), "video");
	    String pngUrl = S3Uploader.uploadFileToS3(getLatestPngFile(), "image");
	    String audioUrl = S3Uploader.uploadFileToS3(getLatestAudioFile(), "audio");
	    String excelUrl = S3Uploader.uploadFileToS3(getLatestExcelFile(), "excel");
		
	
	    FileUrlManager.storeUrl(FileUrlManager.VIDEO_FILE, videoUrl);
        FileUrlManager.storeUrl(FileUrlManager.IMAGE_FILE, pngUrl);
        FileUrlManager.storeUrl(FileUrlManager.AUDIO_FILE, audioUrl);
        FileUrlManager.storeUrl(FileUrlManager.EXCEL_FILE, excelUrl);
        //FileUrlManager.storeUrl(FileUrlManager.HTML_FILE, htmlUrl);
	    
	    
	    
		String js=   
	
"	var ul = document.querySelector('.nav-right');"+
"	var newLi = document.createElement('li');"+
"	newLi.className = 'm-r-10';" +
"var newLink = document.createElement('a');"+
"	newLink.href = '#';"+
"newLink.title='click here to view the entire screen recording';"+
"var newSpan = document.createElement('span');"+
"newSpan.className = 'badge badge-primary';"+
"	newSpan.innerHTML = '&#x1F3A5; Play Recording';  "+   //added icon just now
"newSpan.style.color='white';"+
"newSpan.style.borderRadius='100px';"+
"newSpan.style.border = '2px solid white'; "+
"newSpan.style.boxShadow = '0 4px 6px rgba(0, 0, 0, 0.1)'; "
+ "newSpan.style.padding = '10px 10px'; "+// Padding to make it look like a button

"newLink.addEventListener('click', function() {" +
"    var modalDiv = document.createElement('div');" +
"    modalDiv.style.position = 'fixed';" +
"    modalDiv.style.top = '50%';" +
"    modalDiv.style.left = '50%';" +
"    modalDiv.style.transform = 'translate(-50%, -50%)';" +
"    modalDiv.style.zIndex = '1000';" +
"    modalDiv.style.backgroundColor = 'rgba(0, 0, 0, 0.6)';" +
"    modalDiv.style.padding = '5px';" +
"    modalDiv.style.borderRadius = '10px';" +
"    modalDiv.style.width = '800px';" +
"    modalDiv.style.height = '500px';" +
"    modalDiv.style.display = 'flex';" +
"    modalDiv.style.justifyContent = 'center';" +
"    modalDiv.style.alignItems = 'center';" +

// Close button for the modal
"    var closeButton = document.createElement('button');" +
"    closeButton.textContent = 'X';" +
"    closeButton.style.position = 'absolute';" +
"    closeButton.style.top = '8px';" +
"    closeButton.style.right = '10px';" +
"    closeButton.style.backgroundColor = '#f44336';" +
"    closeButton.style.color = 'white';" +
"    closeButton.style.border = 'none';" +
"    closeButton.style.padding = '5px 5px';" +
//"closeButton.style.borderRadius='50%';"+
"    closeButton.style.cursor = 'pointer';" +
"    closeButton.style.fontSize='20px';"+
"    closeButton.addEventListener('click', function() {" +
"        document.body.removeChild(modalDiv);" +
"    });" +

"    var videoElement = document.createElement('video');" +
//"    videoElement.src = '" + videoFilePath + "';" +  
//"    videoElement.src = '" + new File(videoFilePath).toURI().toString() + "';" +
"    videoElement.src = '" + videoUrl + "';" +  // Using S3 URL instead of local file

"    videoElement.width = 650;" +
//"     videoElement.height=700;"+
"    videoElement.controls = true;" +
"    videoElement.autoplay = true;" +
"videoElement.muted=false;"+

"    modalDiv.appendChild(videoElement);" +
"    modalDiv.appendChild(closeButton);" +

"    document.body.appendChild(modalDiv);" +
"});" +

"newLink.appendChild(newSpan);" +
"newLi.appendChild(newLink);" +
"ul.appendChild(newLi);"+

"	var ul1 = document.querySelector('.nav-right');"+
"	var newLis = document.createElement('li');"+
"	newLis.className = 'm-r-10';" +
"var newLink1 = document.createElement('a');"+
//"	newLink1.href = '"+pngFilePath+"';"+
//"newLink1.href = '" + new File(pngFilePath).toURI().toString() + "';" +
"newLink1.href = '" + pngUrl + "';" +  // Using S3 URL for graph

"newLink1.target = '_blank'; "+ 
"newLink1.title='click here to view Test Execution Results';"+
//
"var newSpan1 = document.createElement('span');"+
"newSpan1.className = 'badge badge-primary';"+
"	newSpan1.innerHTML = '&#x1F4C8; View Graph';  "+ 
"newSpan1.style.color='white';"+
"newSpan1.style.borderRadius='100px';"+
"newSpan1.style.border = '2px solid white'; "+
"newSpan1.style.boxShadow = '0 4px 6px rgba(0, 0, 0, 0.1)'; "+
"newSpan1.style.padding = '10px 10px'; "+
//
"newLink1.appendChild(newSpan1);" +
"newLis.appendChild(newLink1);" +
"ul1.appendChild(newLis);"+







"var ul2 = document.querySelector('.nav-right');" +
"var newList = document.createElement('li');" +
"newList.className = 'm-r-10';" +

"var newLink2 = document.createElement('a');" +
"newLink2.href = '#';" +  // Prevent navigation
"newLink2.title = 'Click here to listen Test Run Summary';" +

// Create an audio element
//"var audio = new Audio('" + new File(audioFilePath).toURI().toString() + "');" +
"var audio = new Audio('" + audioUrl + "');" +  // Using S3 URL for audio

"audio.preload = 'auto';" +  // Preload the audio file

// Create the span for the button
"var newSpan2 = document.createElement('span');" +
"newSpan2.className = 'badge badge-primary';" +
"newSpan2.innerHTML = '&#x1F3A7; Listen to Report';" +  
"newSpan2.style.color = 'white';" +
"newSpan2.style.borderRadius='100px';"+
"newSpan2.style.border = '2px solid white'; "+
"newSpan2.style.boxShadow = '0 4px 6px rgba(0, 0, 0, 0.1)'; "+
"newSpan2.style.padding = '10px 10px'; "+
// Append the span to the link, and the link to the list item
"newLink2.appendChild(newSpan2);" +
"newList.appendChild(newLink2);" +
"ul2.appendChild(newList);" +

// Variable to track if audio is playing
"var isPlaying = false;" +

// Add click event listener for play/pause functionality
"newLink2.addEventListener('click', function(event) {" +
"    event.preventDefault();" +  // Prevent default link behavior
"    if (isPlaying) {" +
"        audio.pause();" +
"        newSpan2.innerHTML = '&#x1F3A7; Play Audio';" +  // Change text to 'Listen' when paused
"    } else {" +
"        audio.play();" +
"        newSpan2.innerHTML = '&#x1F3A7; Pause Audio';" +   // Change text to 'Pause' when playing
"    }" +
"    isPlaying = !isPlaying;" +  // Toggle play state
"});"+







"	var ul3 = document.querySelector('.nav-right');"+
"	var newList1 = document.createElement('li');"+
"	newList1.className = 'm-r-10';" +
"var newLink3 = document.createElement('a');"+
//"	newLink1.href = '"+pngFilePath+"';"+
//"newLink3.href = '" + new File(excelFilePath).toURI().toString() + "';" +
"newLink3.href = '" + excelUrl + "';" +  // Using S3 URL for excel

"newLink3.target = '_blank'; "+ 
"newLink3.title='click here to Download Orders List';"+
//
"var newSpan3 = document.createElement('span');"+
"newSpan3.className = 'badge badge-primary';"+
"	newSpan3.innerHTML = '&#x2B73; Download Orders List';  "+ 
"newSpan3.style.color='white';"+
"newSpan3.style.borderRadius='100px';"+
"newSpan3.style.border = '2px solid white'; "+
"newSpan3.style.boxShadow = '0 4px 6px rgba(0, 0, 0, 0.1)'; "+
"newSpan3.style.padding = '10px 10px'; "+
//
"newLink3.appendChild(newSpan3);" +
"newList1.appendChild(newLink3);" +
"ul3.appendChild(newList1);"










;




		return  js;
	}




	public static String getCss() 
	{
		String css=     //".header .vheader .nav-logo>a .logo{width:200%;}"+
				".dark .header, body.dark {background-color:rgba(36,49,64,0.8);}"+
				".header .vheader .nav-left, .header .vheader .nav-right {padding-left:revert-layer;}"+
				".dark .test-content{border-style:solid;border-color:black;border-width:2px;}"+
				".badge.log{font-size:100%;color:white;padding:5px;}"+
				".pass-bg {background-color:darkgreen;}"+
				".fail-bg{background-color:darkred;}"+
				".badge{border-radius:5px;font-size:105%;}"+
				//".header .vheader .search-input input {width:90%;text-align:left;}"+
				".badge-primary{background-color:rgba(101, 105, 223, 0.7);}"+
				".dark .badge-default{border:1px solid #007bff !important;box-shadow: 2px 2px #6c757d;}"
				;
		return css;
	}

}
