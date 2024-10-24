package com.scart.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadMailContent {
    private String path;
    
    public ReadMailContent() {
        this.path = System.getProperty("user.dir") + File.separator + "TestData" + File.separator + "MailData.xlsx";
    }

    public String[] getMailContent() throws IOException {
        String header = "";
        StringBuilder bodyBuilder = new StringBuilder();
        StringBuilder footerBuilder = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            
            XSSFSheet sheet = workbook.getSheet("MailContent");
            if (sheet == null) {
                throw new IOException("Sheet 'MailContent' not found in Excel file");
            }

            // Read header (safely)
            XSSFRow headerRow = sheet.getRow(0);
            if (headerRow != null) {
                XSSFCell headerCell = headerRow.getCell(1);
                if (headerCell != null) {
                    header = getCellValueSafely(headerCell);
                }
            }

            // Read body (rows 1-9)
            for (int i = 1; i <= 9; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    XSSFCell cell = row.getCell(0);
                    if (cell != null) {
                        String cellValue = getCellValueSafely(cell);
                        if (!cellValue.isEmpty()) {
                        	//System.out.println(cellValue+" ------------------------------");
                            bodyBuilder.append(cellValue).append("\n");
                        }
                    }
                }
            }

            // Read footer (rows 10-12)
            for (int i = 10; i <= 12; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    XSSFCell cell = row.getCell(0);
                    if (cell != null) {
                        String cellValue = getCellValueSafely(cell);
                        if (!cellValue.isEmpty()) {
                            footerBuilder.append(cellValue).append("\n");
                        }
                    }
                }
            }
        }

        return new String[]{
            header.isEmpty() ? getDefaultHeader() : header,
            bodyBuilder.length() == 0 ? getDefaultBody() : bodyBuilder.toString(),
            footerBuilder.length() == 0 ? getDefaultFooter() : footerBuilder.toString()
        };
    }

    private String getCellValueSafely(XSSFCell cell) {
        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    return String.valueOf(cell.getNumericCellValue()).trim();
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue()).trim();
                case FORMULA:
                    return cell.getCellFormula().trim();
                default:
                    return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String getEmailGHeader() {
        try {
            return new ReadMailContent().getMailContent()[0];
        } catch (Exception e) {
            return getDefaultHeader();
        }
    }

    public static String getEmailBody() {
        try {
            return new ReadMailContent().getMailContent()[1];
        } catch (Exception e) {
            return getDefaultBody();
        }
    }

    public static String getEmailFooter() {
        try {
            return new ReadMailContent().getMailContent()[2];
        } catch (Exception e) {
            return getDefaultFooter();
        }
    }

    private static String getDefaultHeader() {
        return "Test Report for S-Cart Admin Automation Suite";
    }

    private static String getDefaultBody() {
        return "Hello,\n\n" +
               "Please find attached the results of the automated test execution for the S-Cart " +
               "Admin platform, performed using the Selenium Hybrid Framework.\n\n" +
               "The attached HTML report includes:\n" +
               "- Entire Screen recordings of all tests.\n" +
               "- Graphical representation of test pass/fail statistics.\n" +
               "- An audio overview of the test results.\n\n" +
               "This email serves to ensure that the application has been validated based on key " +
               "functional flows including login, order management, UI interaction, and more.\n\n";
    }

    private static String getDefaultFooter() {
        return "Best regards,\nAnnam Deepak,\nQA Team";
    }
}