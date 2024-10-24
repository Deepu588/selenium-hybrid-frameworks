package com.scart.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.FindAll;

import com.scart.testbase.BasePage;
import java.util.List;
import java.util.stream.Collectors;
public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}


	@FindBy(xpath = "//ul[@class='navbar-nav']/li[3]")
	private WebElement themeOption;


	@FindBy(xpath = "//div[contains(@class,'dropdown-menu-left p-0 show')]/a[5]")
	private WebElement getDropdownItem;

	@FindBy(xpath = "//nav[contains(@class,'main-header')]")
	private WebElement mainHeader;


	@FindBy(xpath="//img[@alt='User Image']") 
	private WebElement avatarImage;

	@FindBy(xpath="//a[text()='Logout']")
	private WebElement logout;

	@FindBy(xpath="//a[@class='nav-link' and @role='button']")
	private WebElement menuBtn;

	@FindBy(tagName = "body")
	private WebElement bodyTag;
	
	
	@FindBy(xpath="//aside/div")
	private WebElement sideBarDiv;

	@FindAll({

		@FindBy(xpath="//ul[contains(@class,'nav-sidebar')]/li[@class='nav-item ']/a"),

		@FindBy(xpath="//li[contains(@class,'nav-item has-treeview')]/ul/li/a")	
	})
	private List<WebElement> allLinks;


	@FindBy(xpath="//ul/li[@class='nav-item has-treeview'][1]")
	private WebElement orderManagerMenuList;

	@FindBy(xpath="//ul[@class='nav nav-treeview']/li/a[1]/p[text()='Order']")
	// //ul[@class='nav nav-treeview']/li/a[1]/p[text()='Order']
	private WebElement ordersButton;

	@FindBy(xpath="//table/tbody/tr")
	private List<WebElement> allRows;


	@FindBy(xpath="//div/input[@name='email']")
	private WebElement emailField;


	@FindBy(xpath="//table/tbody/tr/td[2]")
	private List<WebElement> mailsList;

	@FindBy(id="sort_order")
	private WebElement selectTag;

	@FindBy(xpath="//input[@name='email']/following::button[1]")
	private WebElement searchBtn;


	@FindBy(xpath="//table/tbody/tr/td[11]")
	private List<WebElement> dateCells;


	@FindBy(xpath="//table/tbody/tr/td[1]/input")
	private List<WebElement> allCheckBoxes;


	@FindBy(xpath ="//button[contains(@class,'grid-select-all')]")
	private WebElement headCheckBox;


	@FindBy(xpath="//div[@class='row']/child::div[contains(@class,'col-md-3')]")
	private List<WebElement> gridBoxes;

	//
	//	@FindBy(xpath="//ul[contains(@class,'pull-right')]/li[\" + i + \"]")
	//	private WebElement pageNo;
	//	
	//	
	@FindBy(xpath="//div[@id='loading' and not(contains('@style','none'))]")
	private WebElement spinner;


	@FindBy(xpath = "//div[@class='ml-3 float-left']/b[2]")
	private WebElement totalRowsCount;



	@FindBy(xpath = "//ul[contains(@class,'pull-right')]")
	private WebElement paginationContainer;

	
	
	@FindBy(id="select2-sort_order-result-orw7-created_at__asc")
	private WebElement dropdownOption;
	
	
	
	public WebElement getDropdownItemForAsc() {
		return dropdownOption;
	}
	
	
	public void getPageNo(int i) {
		paginationContainer.findElement(By.xpath("./li[" + i + "]")).click();
	}

	public WebElement getSideBar() {
		return sideBarDiv;
	}

	public WebElement getSpinner() {
		return spinner;
	}

	public List<WebElement> getGridItems(){
		return gridBoxes;
	}

	public int getTotalRowsCount() {
		return Integer.parseInt(totalRowsCount.getText().trim());
	}

	public void selectByValueInDropdown(String value) {
		Select s=	new Select(selectTag);
		s.selectByVisibleText(value);
		searchBtn.click();
	}

	public void selectHeadCheckBox() {
		headCheckBox.click();
	}

	public List<WebElement> getAllCheckBoxes(){
		return allCheckBoxes;
	}

	public void selectAllCheckBoxes() {
		allCheckBoxes.forEach(checkBox->checkBox.click());
	}


	public void resetMailField() {
		emailField.clear();
	}

	public List<WebElement> getDateCells(){
		return dateCells;
	}
	
	public List<String> getDateCellsInString(){
		
		return dateCells.stream().map(d->d.getText()).collect(Collectors.toList());
	}

	public void clickSearchBtn() {
		searchBtn.click();
	}

	public void enterAndClick(String text) {
		emailField.sendKeys(text,Keys.ENTER);
	}


	public List<WebElement> getMailsList(){
		return mailsList;
	}

	
	
	public List<String> getMailIdsList(){
		
	
		return mailsList.stream().map(mail->mail.getText()).collect(Collectors.toList());
	}
	
	
	public void clickOrderSubMenuBtn() {

		orderManagerMenuList.click();
		
		//ordersButton.click();
	}

	public List<WebElement> getRows(){
		return allRows;
	}






	public List<String> getHrefValueInMenuSections() {
		//System.out.println("Size of Links ="+allLinks.size());
		return	allLinks.stream().map(element->element.getAttribute("href")).collect(Collectors.toList());

	}

	public List<WebElement> getLinks(){
		return allLinks;
	}



	public void clickThemeOption() {

		themeOption.click();

	}

	public void selectDropDownItem() {
		getDropdownItem.click();
	}


	public String getBgColorOfMainHeader() {
		return mainHeader.getCssValue("background-color");
	}

	public WebElement getAvatar() {
		return avatarImage;
	}

	public void clickMenu() {
		menuBtn.click();
	}

	public WebElement getLogoutLink() {
		return logout;
	}


	public String getClassAttributeForBodyTag() {
		return bodyTag.getAttribute("class");
	}





}
