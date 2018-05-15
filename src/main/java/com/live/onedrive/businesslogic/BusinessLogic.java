package com.live.onedrive.businesslogic;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.live.onedrive.pageobjects.PageObject;
import com.live.onedrive.start.TestRunner;
import com.relevantcodes.extentreports.LogStatus;

public class BusinessLogic {

	WebDriver driver;
	PageObject pageObject;

	public BusinessLogic(WebDriver driver, PageObject pageObject) {
		this.driver = driver;
		this.pageObject = pageObject;

	}

	public void signIn() throws Exception {
		pageObject.getSignIn().click();
		driver.switchTo().frame(pageObject.getIframeSignIn());
		pageObject.getEmail().sendKeys("drivetest007@outlook.com");
		pageObject.getSubmit().click();
		driver.switchTo().defaultContent();
		pageObject.getPassword().sendKeys("drive@Test007");
		TestRunner.logger.log(LogStatus.PASS, "Application Login Successfull : " + TestRunner.logger.addScreenCapture(takeSnapShot("Login")));
		pageObject.getSubmit().click();
	}

	public void fileUpload() throws Exception {
		wait(2000);
		pageObject.getDocuments().click();
		List<WebElement> existingFile = pageObject.getExistingFile();
		if (existingFile.size() > 0) {
			existingFile.get(0).click();
			wait(3000);
			pageObject.getDelete().click();
			wait(6000);
		}

		pageObject.getUpload().click();
		String uploads = System.getProperty("user.dir") + "/uploads/";
		wait(2000);
		pageObject.getFileUpload().sendKeys(uploads + "Test.txt");
		String blankTextMessage = pageObject.getUploadBlankStatus().getText();
		try {
			Assert.assertEquals(blankTextMessage,
					"Sorry, OneDrive can't upload empty folders or empty files. Please try again.");
			TestRunner.logger.log(LogStatus.PASS, "Empty File Validation Successfull : "+ TestRunner.logger.addScreenCapture(takeSnapShot("emptyFile")));
		} catch (Exception e) {
			TestRunner.logger.log(LogStatus.FAIL, "Failed to Validate the Empty File : "+ TestRunner.logger.addScreenCapture(takeSnapShot("emptyFile")));
		}
		wait(2000);
		pageObject.getUpload().click();
		wait(2000);
		pageObject.getFileUpload().sendKeys(uploads + "TestData.txt");
		try {
			Assert.assertEquals(pageObject.getUploadedFile().getText().trim(), "TestData.txt");
			TestRunner.logger.log(LogStatus.PASS,
					"File Uploaded Successfully : " + TestRunner.logger.addScreenCapture(takeSnapShot("fileUpload")));
		} catch (Exception e) {
			TestRunner.logger.log(LogStatus.FAIL, "Failed to Upload File : "+ TestRunner.logger.addScreenCapture(takeSnapShot("fileUpload")));

		}
		wait(2000);
		pageObject.getInfo().click();
		try {
			Assert.assertEquals(pageObject.getSizeInfo().getText(),
					getFileSizeBytes(new File(uploads + "TestData.txt")));
			TestRunner.logger.log(LogStatus.PASS, "Validated the Uploaded File MetaData : "+ TestRunner.logger.addScreenCapture(takeSnapShot("fileMetaData")));
		} catch (Exception e) {
			TestRunner.logger.log(LogStatus.FAIL, "Failed to Validate the Uploaded File MetaData : "+ TestRunner.logger.addScreenCapture(takeSnapShot("fileMetaData")));
		}
		pageObject.getUploadedFile().click();
	}

	public void modifyFile() throws Exception {
		pageObject.getOpenFile().click();
		int versionSize = pageObject.getVersionList().size();
		while (versionSize == 0) {
			wait(2000);
			versionSize = pageObject.getVersionList().size();
		}
		wait(3000);
		pageObject.getTextEditor().click();
		wait(2000);
		Set<String> windowsSet = driver.getWindowHandles();
		String firstWindow = driver.getWindowHandle();

		for (String window : windowsSet) {
			if (!window.equals(firstWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}
		wait(8000);
		pageObject.getEditText().click();
		TestRunner.logger.log(LogStatus.PASS, "Editor Opened Successfully : "+ TestRunner.logger.addScreenCapture(takeSnapShot("Editor")));
		wait(3000);
		StringSelection selection = new StringSelection(" Data Modified");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		robot.setAutoDelay(1000);
		robot.keyPress(KeyEvent.VK_END);
		robot.keyRelease(KeyEvent.VK_END);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		robot.setAutoDelay(2000);

		TestRunner.logger.log(LogStatus.PASS, "File Modified Successfully : "+ TestRunner.logger.addScreenCapture(takeSnapShot("modifiedFileUpload")));
		pageObject.getSave().click();
		wait(3000);
	}

	public void validateMofification() {
		pageObject.getVersionHistory().click();

		for (WebElement link : pageObject.getFilesDownloadLink()) {
			link.click();
		}
		wait(2000);
		fileDownloadValidator();
	}

	public void profileLogOut() {
		wait(3000);
		pageObject.getProfile().click();
		pageObject.getSignOut().click();
		wait(3000);

	}

	public void deleteUploadedFile() {
		pageObject.getClose().click();
		wait(2000);
		pageObject.getDelete().click();

	}

	private String getFileSizeBytes(File file) {
		return file.length() + " bytes";
	}

	private String getFileLastModified(File file) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(file.lastModified());
	}

	public void fileDownloadValidator() {
		File folder = new File(System.getProperty("user.dir") + "/downloads/");
		File[] listOfFiles = folder.listFiles();
		List<String> fileData = new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				try {
					fileData.add(readFile(listOfFiles[i].getAbsolutePath()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}

		try {
			Assert.assertFalse(fileData.get(0).equals(fileData.get(1)));

			TestRunner.logger.log(LogStatus.PASS, "Both Files are different and Validated Successfully");
		} catch (Exception e) {

			TestRunner.logger.log(LogStatus.FAIL, "Both Files are Same");
		}
	}

	public String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	public void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String takeSnapShot(String name) throws Exception {
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(System.getProperty("user.dir") + "//screenshots//" + name + ".png");
		FileUtils.copyFile(SrcFile, DestFile);
		return DestFile.toString();
	}
}
