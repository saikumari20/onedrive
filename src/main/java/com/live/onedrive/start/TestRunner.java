package com.live.onedrive.start;

import java.awt.AWTException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.live.onedrive.businesslogic.BusinessLogic;
import com.live.onedrive.pageobjects.PageObject;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestRunner {
	public static WebDriver driver;
	PageObject pageObject;
	BusinessLogic businessLogic;
	public static ExtentReports extent;
	public static ExtentTest logger;

	@Parameters({ "url", "browser" })
	@BeforeTest
	public void startUp(String url, String browser) {
		extent = new ExtentReports(System.getProperty("user.dir") + "/reports/api_execution_report.html", true);
		extent.addSystemInfo("Host Name", "One Drive Test").addSystemInfo("Environment", "Automation Testing");
		extent.loadConfig(new File(System.getProperty("user.dir") + "/reports/extent-config.xml"));
		logger = extent.startTest("Open Browser");
		switch (browser.toLowerCase()) {

		case "ie":
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/driver/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");

			Map<String, Object> chromePreferences = new Hashtable<String, Object>();
			chromePreferences.put("profile.default_content_settings.popups", 0);
			chromePreferences.put("download.prompt_for_download", "false");

			chromePreferences.put("download.default_directory", System.getProperty("user.dir") + "/downloads");

			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("prefs", chromePreferences);

			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			driver = new ChromeDriver(cap);
			logger.log(LogStatus.PASS, "Browser Opened Successfully");
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/driver/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		}
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		cleanUp();
		logger.log(LogStatus.PASS, "Folder Cleaned Successfully");
		extent.endTest(logger);
	}

	public void cleanUp() {
		String downloads = System.getProperty("user.dir") + "/downloads/";
		File file = new File(downloads);
		String[] myFiles;
		if (file.isDirectory()) {
			myFiles = file.list();
			for (int i = 0; i < myFiles.length; i++) {
				File myFile = new File(file, myFiles[i]);
				System.out.println(myFile);
				if (!myFile.isDirectory()) {
					myFile.delete();
				}
			}
		}
	}

	@BeforeClass
	public void init() {
		logger = extent.startTest("Initialize Objects");
		pageObject = new PageObject(driver);
		logger.log(LogStatus.PASS, "Page Factory Initialized Successfully");
		businessLogic = new BusinessLogic(driver, pageObject);
		logger.log(LogStatus.PASS, "Application WorkFlow Initialized");
		extent.endTest(logger);
	}

	@Test(priority = 1)
	public void applicationLogIn() throws Exception {
		logger = extent.startTest("Application Login");
		businessLogic.signIn();
		extent.endTest(logger);
	}

	@Test(priority = 2)
	public void businessLogicValidation() throws Exception {
		logger = extent.startTest("Application Navigation and Validation");
		businessLogic.fileUpload();
		businessLogic.modifyFile();
		businessLogic.validateMofification();
		extent.endTest(logger);
	}

	@Test(priority = 3)
	public void applicationLogOut() {
		logger = extent.startTest("Application Logout");
		businessLogic.deleteUploadedFile();
		logger.log(LogStatus.PASS, "File Deleted Successfully");
		extent.endTest(logger);
	}

	@AfterTest
	public void close() throws IOException, URISyntaxException {
		driver.quit();
		logger = extent.startTest("Close Browser");
		Assert.assertTrue(true);
		logger.log(LogStatus.PASS, "Browser Closed Successfully");
		extent.endTest(logger);
		extent.flush();
		extent.close();
		File htmlFile = new File(System.getProperty("user.dir") +"\\reports\\api_execution_report.html");
		Desktop.getDesktop().browse(htmlFile.toURI());
	}
}
