package com.polk.test.automation.webdriver;

public class WebDriverFactory {

	private static final LocalDriver driver = new LocalDriver(ConfUtil.getBrowser(), false, null, false);
	
	public static LocalDriver getLocalDriver() {
		return driver;
	}
	

}
