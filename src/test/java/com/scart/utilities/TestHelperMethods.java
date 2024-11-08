package com.scart.utilities;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

public class TestHelperMethods {




	public static boolean	checkText(List<WebElement> mailsList,String text) {
		return mailsList.stream().map(mailCell->mailCell.getText()).allMatch((cellText)->cellText.contains(text));

	}


	public static List<Date> convertStringInToDate(List<WebElement> dateCells){
		List<Date> allDates=	 dateCells.stream().map(cell->cell.getText().trim()).map(cellText->{
			try {
				return convertToDateFormat(cellText);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());

		return allDates;

	}


	public static Date convertToDateFormat(String text) throws Exception {
		SimpleDateFormat  sdf=  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d= sdf.parse(text);
		return d;
	}

	public static boolean checkOrder(List<Date> allDates) {
		for (int i = 0; i < allDates.size()-1; i++) {
			if((allDates.get(i)).before(allDates.get(i+1)))
			{
				continue;
			}
			else {
				return false;
			}
		}
		return true;
	}




	public static boolean isCheckBoxSelected(List<WebElement> allCheckBoxes) {

		return  allCheckBoxes.stream().allMatch(element->element.isSelected());

	}




	public static int[]  getDimensionsOfElement(WebElement element) {
		//System.out.println("Height= "+element.getSize().getHeight()+" Width= "+element.getSize().getWidth());

		return new int[] {  element.getSize().getHeight(),element.getSize().getWidth()};



	}



	public static boolean check(List<WebElement> gridBox) {

		for(int i=0;i<gridBox.size()-1;i++) {


			if(Arrays.equals(getDimensionsOfElement(gridBox.get(i)), getDimensionsOfElement(gridBox.get(i+1)))) {

				continue;
			}
			else {
				return false;
			}
		}
		return true;
	}


	
	
	
	
	
	

	public static HashMap<Integer, String[]> getAllDimensionsAsMap(List<WebElement> gridBox) {
	    HashMap<Integer, String[]> dimensionsMap = new HashMap<>();

	    for (int i = 0; i < gridBox.size(); i++) {
	        WebElement element = gridBox.get(i);
	        String[] dimensions = new String[2]; 
	        dimensions[0] ="Height= "+ element.getSize().getHeight(); 
	        dimensions[1] = "Width= "+element.getSize().getWidth();  

	        dimensionsMap.put(i, dimensions); 
	    }

	    return dimensionsMap; 
	}

	
	
	
	
	
	
	
	
	


}
