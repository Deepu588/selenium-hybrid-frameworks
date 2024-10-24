package com.scart.utilities;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class ThreadPoolUtilityForBrokenLinks {

	
	
	public static boolean checkLinksValues(List<String> allHyperLinks) {
		
		 return allHyperLinks.stream().allMatch(link->(!link.isBlank() && !link.equals("#")));
	}
	
	
	
	public static boolean  usingThreads(List<String> allHyperLinks)
	
	{
		
		
		
		ExecutorService executor = Executors.newFixedThreadPool(60); 
	    List<Future<Boolean>> results = allHyperLinks.stream()
	            .map(link -> executor.submit(() -> checkValidOrNot(link)))
	            .collect(Collectors.toList());
	    
	    boolean allValid = results.stream().allMatch(future -> {
	        try {
	            return future.get(); 
	        } catch (Exception e) {
	            return false;
	        }
	    });
//
	    executor.shutdown();
	    
	    
	    return allValid;
	}
	
	
	
	
	public static boolean checkValidOrNot(String s)  {
		try {
			URL  url=  new URL(s);
			URLConnection conn;
 
			conn = url.openConnection();
 
			HttpsURLConnection httpUrl=	(HttpsURLConnection) conn;
			httpUrl.setConnectTimeout(5000);
			 httpUrl.setRequestMethod("HEAD");
			httpUrl.connect();
			boolean b=(httpUrl.getResponseCode()>=400)?false:true;
			httpUrl.disconnect();
			return b;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
	}
	
	
}
