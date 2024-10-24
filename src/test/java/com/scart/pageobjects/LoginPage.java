package com.scart.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.scart.testbase.BasePage;

public class LoginPage extends BasePage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(name = "username")
    private WebElement txtUsername;


    @FindBy(how = How.NAME, using = "password")
    private WebElement txtPassword;


    @FindBy(xpath = "//button[@type='submit']")
    private WebElement btnSubmit;


    @FindBy(xpath = "//i[@for='inputError']")
    private WebElement errorMsg;


    public void resetUserName() {
        txtUsername.clear();
    }

    public void resetPassword() {
        txtPassword.clear();
    }


    public void setUserName(String userName) {

        txtUsername.sendKeys(userName);
    }


    public void setPassword(String password) {
        txtPassword.sendKeys(password);
    }


    public void clickSubmit() {
        btnSubmit.click();
    }


    public String getErrorMessage() {
        return errorMsg.getText().trim();
    }


}
