package com.polk.test.automation.webdriver;

import static com.automation.hooks.ScenarioContext.scenario;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import com.automation.hooks.ScenarioContext;

import cucumber.api.Scenario;

public class CucumberUtil {

	private static final Logger LOG = Logger.getLogger(CucumberUtil.class.getName());
	
	public static void takeScreenShot() {
		if (scenario == null) {
			LOG.error("You should not see this message. Something is really wrong. Scenario object not found.");
			return;
		}
		try {
			if (ScenarioContext.driver.getWrappedDriver() instanceof TakesScreenshot) {
				byte[] screenshot = ScenarioContext.driver.getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");
			}
		} catch (Throwable somePlatformsDontSupportScreenshotsOrBrowserHasDied) {
			somePlatformsDontSupportScreenshotsOrBrowserHasDied.printStackTrace(System.err);
		}
	}

	/**
	 * To take screen shots in between the steps.
	 * 
	 * @param isTakeScreenShots
	 */
	public static void takeScreenShot(boolean isTakeScreenShots) {
		if (!isTakeScreenShots) {
			LOG.info("Screen shots are disabled.");
			return;
		}
		Scenario scenario = ScenarioContext.scenario;
		if (scenario == null) {
			LOG.error("You should not see this message. Something is really wrong. Scenario object not found.");
			return;
		}
		takeScreenShot();
	}

	public static void markFail(String message) {
		Assert.assertTrue(false, message);
	}

	public static void embedTextInReport(String text) {
		scenario.write(text);
	}

}
