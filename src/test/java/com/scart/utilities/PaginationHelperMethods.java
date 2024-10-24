
package com.scart.utilities;

import org.apache.poi.xssf.usermodel.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PaginationHelperMethods {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private int rowNum;

	public PaginationHelperMethods() {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("OrdersData");
		rowNum = 0;
	}

	public void writeHeaders() {
		
		String[] headers = {"Email", "SubTotal", "Shipping", "Discount", "Tax", 
                "Total", "Payment Method", "Status", "StoreList", "Created At"};
		XSSFRow headerRow = sheet.createRow(rowNum++);
		int headerColNum = 0;
		for (String header : headers) {
			XSSFCell cell = headerRow.createCell(headerColNum++);
			cell.setCellValue(header);
		}
	}

	public void writeTableData(List<WebElement> rows) {
		//int rowIndex = startRow;
		for (WebElement row : rows) {
			XSSFRow excelRow = sheet.createRow(rowNum++);
			List<WebElement> cells = row.findElements(By.xpath("td"));
			int cellIndex = 0;

			for (WebElement cell : cells) {
				if (cell.getText() != null && !cell.getText().isEmpty()) {
					XSSFCell excelCell = excelRow.createCell(cellIndex++);
					excelCell.setCellValue(cell.getText());
				}
			}
		}
	}

	public void saveWorkbook() throws IOException {
		String directory=System.getProperty("user.dir")+File.separator+"OrdersList";

		String fileName= "OrdersData".concat(	new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date())).concat(".xlsx");
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File excelFile = new File(dir, fileName);
		try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
			workbook.write(fileOut);
		}
		workbook.close();
	}

	public int getLastRowNum() {
		return sheet.getLastRowNum();
	}
	
	
	
	
	public int getCurrentRowNum() {
        return rowNum;
    }
}
