package com.polk.test.automation.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class WaitConditions {
	
	public static ExpectedCondition<Boolean> urlContains(final String text) {
		return new ExpectedCondition<Boolean>() {
			private String currentUrl = "";

			@Override
			public Boolean apply(WebDriver driver) {
				currentUrl = driver.getCurrentUrl();
				return currentUrl.contains(text);
			}

			@Override
			public String toString() {
				return String.format("URL to contain \"%s\". Current URL: \"%s\"", text, currentUrl);
			}
		};
	}

	/**
	 * @param text
	 * @return
	 */
	public static ExpectedCondition<Boolean> pageContainsText(final String text) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return driver.getPageSource().contains(text);
			}

			@Override
			public String toString() {
				return String.format("Page to contain \"%s\"", text);
			}
		};
	}

}
