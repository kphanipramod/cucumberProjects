package com.polk.test.automation.webdriver;

import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Creates a browser session which can be used to interact with a web UI. This
 * class allows for a single session to be re-used across multiple test cases
 * for increased speed.
 */

public class LocalDriver extends SharedDriver {

	private static final Logger LOG = Logger.getLogger(LocalDriver.class.getName());
	
	public static final Thread CLOSE_THREAD = new Thread(LocalDriver::quitGlobalInstance);
	
	public LocalDriver(String browser, boolean isProxyEnabled, Proxy proxy, boolean isExtRequired) {
		super(getDriver(browser, isProxyEnabled, proxy, isExtRequired), isExtRequired);
		Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
	}

	/**
	 * @param browser
	 * @param isProxyEnabled
	 * @param proxy
	 * @param isExtRequired
	 * @return
	 */
	private static WebDriver getDriver(String browser, boolean isProxyEnabled, Proxy proxy, boolean isExtRequired) {
		if (DRIVER != null) {
			LOG.info("DRIVER already exists = " + DRIVER);
			return DRIVER;
		}
		DRIVER = getBrowserDriver(browser, null, isExtRequired);

		return DRIVER;
	}

	/**
	 * @param browser
	 * @param capabilities
	 * @param isExtRequired
	 * @return
	 */
	private static WebDriver getBrowserDriver(String browser, DesiredCapabilities capabilities, boolean isExtRequired) {
		switch (browser) {
		case BrowserType.FIREFOX:
			return getFirefoxDriver(capabilities);
		case "googlechrome":
			return getChromeDriver(isExtRequired);
		case "CHROMELINUX":
			// return getChromeLinuxDriver(capabilities);
		case BrowserType.IE:
			return getIeDriver();
		case BrowserType.EDGE:
			return getEdgeDriver();
		case "phantomjs":
			return getHeadlessDriver();
		default:
			throw new RuntimeException("Framework does not support browser \"" + browser + "\"");
		}
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
