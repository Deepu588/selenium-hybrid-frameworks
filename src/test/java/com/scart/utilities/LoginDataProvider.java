package com.scart.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {

	
	
	 	private FileInputStream fis;
	    private FileOutputStream fos;
	    private XSSFWorkbook workbook;
	    private XSSFSheet sheet;
	    private XSSFRow row;
	    private XSSFCell cell;
	    private String path;

	    		@DataProvider(name="loginData")
		  public Object[][] getDataAsArray() throws IOException {
	    			//path="C:\\Users\\Deepak-Annam\\eclipse-workspace\\seleniumproject\\S-Cart_Admin\\TestData\\LoginData.xlsx";
	    		path=	System.getProperty("user.dir")+"\\TestData\\LoginData.xlsx";
		        fis = new FileInputStream(path);
		        workbook = new XSSFWorkbook(fis);
		        sheet = workbook.getSheet("Sheet1");
		        int rowCount = sheet.getLastRowNum();
		        
		      //  System.out.println("No of Rows= "+rowCount);
		        int colCount = sheet.getRow(0).getLastCellNum();

		        Object[][] data = new Object[rowCount][colCount];
		        DataFormatter formatter = new DataFormatter();
		       // System.out.println("Data array dimensions: " + data.length + " x " + data[0].length);
		        for (int i = 0; i < rowCount; i++) {
		            for (int j = 0; j < colCount; j++) {
		            	
		                data[i][j] = formatter.formatCellValue(sheet.getRow(i).getCell(j));
		               // System.out.println(data[i][j]);
		            }
		        }

		        workbook.close();
		        fis.close();
		        return data;
		    }

		
		
		
		
		
		
	

}
