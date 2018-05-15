package com.live.onedrive.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageObject {
	WebDriver driver;

	public PageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[text()='Sign in']")
	WebElement signIn;
	@FindBy(xpath = "//iframe[@class='SignIn']")
	WebElement iframeSignIn;
	@FindBy(xpath = "(//input[@type='email'])[1]")
	WebElement email;
	@FindBy(xpath = "//input[@type='submit']")
	WebElement submit;
	@FindBy(xpath = "//input[@name='passwd']")
	WebElement password;
	@FindBy(xpath = "//span[text()='Documents']")
	WebElement documents;
	@FindBy(xpath = "//span[text()='Upload']")
	WebElement upload;
	@FindBy(xpath = "(//input[@type='file'])[1]")
	WebElement fileUpload;
	@FindBy(xpath = "//div[@class='OperationMonitor-itemDescription']")
	WebElement uploadBlankStatus;
	@FindBy(xpath = "//span[contains(@class,'signalFieldValue')]")
	WebElement uploadedFile;
	@FindBy(xpath = "//i[@data-icon-name='Info']")
	WebElement info;
	@FindBy(xpath = "//dt[text()='Size']/following-sibling::dd")
	WebElement sizeInfo;
	@FindBy(xpath = "//dt[text()='Modified']/following-sibling::dd")
	WebElement modifiedInfo;
	@FindBy(xpath = "//span[text()='Open']")
	WebElement openFile;
	@FindBy(xpath = "//span[text()='Open in Text Editor']")
	WebElement textEditor;
	@FindBy(xpath = "(//div[@class='view-line']//span)[2]")
	WebElement editText;
	@FindBy(xpath = "//i[@data-icon-name='Save']")
	WebElement save;
	@FindBy(xpath = "//span[text()='Version history']")
	WebElement versionHistory;
	@FindBy(xpath = "(//div[contains(@class,'ms-DetailsRow-fields')])//a")
	List<WebElement> filesDownloadLink;
	@FindBy(xpath = "//i[@data-icon-name='Delete']")
	WebElement delete;
	@FindBy(xpath = "//div[@class='OperationMonitor-itemName']")
	WebElement uploadStatus;
	@FindBy(xpath = "//i[@data-bind='icon: true']")
	WebElement close;
	@FindBy(xpath = "//span[@class='od-ImageTile-background']")
	List<WebElement> existingFile;
	@FindBy(xpath="//div[@class='o365cs-topnavText']") 
	WebElement profile;
	@FindBy(xpath="//span[text()='Sign out']")
	WebElement signOut;
	@FindBy(xpath="//span[text()='Version history']")
	List<WebElement> versionList;
	
	

	public List<WebElement> getVersionList() {
		return versionList;
	}

	public WebElement getSignOut() {
		return signOut;
	}

	public WebElement getProfile() {
		return profile;
	}

	public WebElement getDelete() {
		return delete;
	}

	public List<WebElement> getFilesDownloadLink() {
		return filesDownloadLink;
	}

	public WebElement getVersionHistory() {
		return versionHistory;
	}

	public WebElement getSave() {
		return save;
	}

	public WebElement getEditText() {
		return editText;
	}

	public WebElement getTextEditor() {
		return textEditor;
	}

	public WebElement getOpenFile() {
		return openFile;
	}

	public WebElement getModifiedInfo() {
		return modifiedInfo;
	}

	public WebElement getSizeInfo() {
		return sizeInfo;
	}

	public WebElement getInfo() {
		return info;
	}

	public WebElement getUploadedFile() {
		return uploadedFile;
	}

	public WebElement getUploadBlankStatus() {
		return uploadBlankStatus;
	}

	public List<WebElement> getExistingFile() {
		return existingFile;
	}

	public WebElement getClose() {
		return close;
	}

	public WebElement getUploadStatus() {
		return uploadStatus;
	}

	public WebElement getFileUpload() {
		return fileUpload;
	}

	public WebElement getUpload() {
		return upload;
	}

	public WebElement getDocuments() {
		return documents;
	}

	public WebElement getSignIn() {
		return signIn;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getIframeSignIn() {
		return iframeSignIn;
	}

	public WebElement getSubmit() {
		return submit;
	}

	public WebElement getPassword() {
		return password;
	}

}
