package ecom.airbnb.steps;

import static com.automation.hooks.ScenarioUtil.embedScreenshot;
import static com.automation.hooks.ScenarioUtil.embedTextInReport;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.automation.hooks.ScenarioContext;

//import com.dagobah.test.springConfig.SpringBeans;

import cucumber.api.Scenario;

public class BaseStep {

	private int screenshotCounter = 0;
	public Logger log = Logger.getLogger(getClass());
	public static final SoftAssert softAssertion = new SoftAssert();

	/**
	 * Get a reference to the current cucumber scenario Supports writing text to
	 * report within test steps
	 *
	 * @param scenario
	 */
	public void before(Scenario scenario) {
		ScenarioContext.scenario = scenario;
	}

	public int getScreenshotCounter() {
		return ++screenshotCounter;
	}

	public void failTestCaseWith(String errorMessage, byte[] screenshot) {
		softAssertion.assertFalse(true, errorMessage);
		embedTextInReport(errorMessage);
		embedScreenshot(screenshot);
	}

	public void failTestCase(String failureMessage) {
		softAssertion.assertFalse(true, failureMessage);
	}

	public String getScenarioName() {
		return ScenarioContext.scenario.getName();
	}

	public void failTestCaseWith(String errorMessage, byte[] screenshot1, byte[] screenshot2) {
		softAssertion.assertFalse(true, errorMessage);
		embedTextInReport(errorMessage);
		embedScreenshot(screenshot1);
		embedScreenshot(screenshot2);
	}

	public void addScreenshotInReport(String message, byte[] screenshot) {
		embedTextInReport(message);
		embedScreenshot(screenshot);
	}
	
	public void addScreenshotInReport( byte[] screenshot) {
		embedScreenshot(screenshot);
	}

	public void stopTestExcutionWith(String errorMessage, byte[] screenshot) {
		embedTextInReport(errorMessage);
		embedScreenshot(screenshot);
		fail(errorMessage);
	}

	public void stopTestExcutionWith(String errorMessage, byte[] screenshot, Exception e) {
		e.printStackTrace();
		embedTextInReport(errorMessage);
		embedScreenshot(screenshot);
		embedTextInReport(e.getLocalizedMessage());
		Assert.fail(errorMessage, e);
		
	}
	
	public void stopTextExecutionWithoutScreenshot(String errorMessage) {
		embedTextInReport(errorMessage);
		fail(errorMessage);
	}

	public void validateCase(boolean condition, String errorMessage, byte[] screenshot) {
		softAssertion.assertTrue(condition, errorMessage);
		if (condition == false) {
			embedTextInReport(errorMessage);
			embedScreenshot(screenshot);
		}
	}
}
