package com.polk.test.automation.webdriver;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Creates a browser session which can be used to interact with a web UI. This
 * class allows for a single session to be re-used across multiple test cases
 * for increased speed.
 */

public class SharedDriver extends EventFiringWebDriver {

	public static WebDriver DRIVER;

	private static final Thread CLOSE_THREAD = new Thread(SharedDriver::quitGlobalInstance);

	public SharedDriver(WebDriver driver, boolean isExtRequired) {
		super(driver);
		Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
	}

	public static void quitGlobalInstance() {
		WebDriver driver = DRIVER;
		String closeBrowserAtEnd = ConfUtil.getProperty("closeBrowser");
		if (!(closeBrowserAtEnd != null && closeBrowserAtEnd.equalsIgnoreCase("no"))) {
			System.out.println("Closing Driver");
			DRIVER = null;
			if (driver != null) {
				driver.quit();
			}
		}
	}

	protected static WebDriver getFirefoxDriver(DesiredCapabilities capabilities) {
		WebDriverManager.firefoxdriver().setup();
		return WebDriverManager.firefoxdriver().create();
	}

	protected static WebDriver getChromeDriver(boolean isExtRequired) {
		WebDriver driver = null;
		boolean state = true;
		int count = 0;
		while (state) {
			try {
				driver = getChromeDriver_function(isExtRequired);
				state = false;
			} catch (Exception ExceptionInInitializerError) {
				ExceptionInInitializerError.printStackTrace();
				count++;
				if (count <= 20) {
					state = true;
				} else {
					state = false;
				}
			}
		}
		return driver;
	}

	private static WebDriver getChromeDriver_function(boolean isExtRequired) {
		//WebDriverManager.chromedriver().version("95.0.4638.69").setup();
		WebDriverManager.chromedriver().setup();
		String downloadFilepath = FileUtil.USER_DIR + ConfUtil.getProperty("download.directory");
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
//		DesiredCapabilities cap = new DesiredCapabilities();
		ChromeOptions options = new ChromeOptions();
		String needModHeader = ConfUtil.getProperty("needModHeader");
		if (needModHeader != null && needModHeader.equalsIgnoreCase("yes")) {
			byte[] fileContent = null;
			try {
				fileContent = FileUtils.readFileToByteArray(new File(
						FileUtil.USER_DIR + "\\src\\main\\resources\\browser extension\\extension_2_2_5_0.crx"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			String encodedString = Base64.getEncoder().encodeToString(fileContent);
			options.addEncodedExtensions(encodedString);
		}
		options.addArguments("--start-maximized");
		options.addArguments("--no-sandbox");
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		options.setExperimentalOption("prefs", chromePrefs);
//		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return WebDriverManager.chromedriver().capabilities(options).create();
	}

	protected static WebDriver getIeDriver() {
		WebDriverManager.iedriver().setup();
		System.out.println("Initiating IE driver");
		return new InternetExplorerDriver();
	}

	protected static WebDriver getEdgeDriver() {
		WebDriverManager.edgedriver().setup();
		return new EdgeDriver();
	}

	protected static ChromeDriver getHeadlessDriver() {
		WebDriverManager.chromedriver().setup();
		String downloadFilepath = FileUtil.USER_DIR + ConfUtil.getProperty("download.directory");
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		DesiredCapabilities cap = new DesiredCapabilities();
		ChromeOptions options = new ChromeOptions();
		String needModHeader = ConfUtil.getProperty("needModHeader");
		if (needModHeader != null && needModHeader.equalsIgnoreCase("yes")) {
			byte[] fileContent = null;
			try {
				fileContent = FileUtils.readFileToByteArray(new File(
						FileUtil.USER_DIR + "\\src\\main\\resources\\browser extension\\extension_2_2_5_0.crx"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			String encodedString = Base64.getEncoder().encodeToString(fileContent);
			options.addEncodedExtensions(encodedString);
		}
		options.addArguments("--headless", "--window-size=1920,1200");
		// options.addArguments("window-size=1040, 784");
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		options.setExperimentalOption("prefs", chromePrefs);
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		// cap.setCapability("resolution", "1920x1200");
		return new ChromeDriver(options);
	}

	@Override
	public void close() {
		if (Thread.currentThread() != CLOSE_THREAD) {
			throw new UnsupportedOperationException(
					"You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
		}
		try {
			super.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
