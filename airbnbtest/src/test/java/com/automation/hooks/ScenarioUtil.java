package com.automation.hooks;

import java.text.SimpleDateFormat;
import java.util.Date;

import cucumber.api.Scenario;

public class ScenarioUtil {

	public static Scenario getScenario() {
		return ScenarioContext.scenario;
	}

	/**
	 * @param text
	 */
	public static void embedTextInReport(String text) {
		ScenarioContext.scenario.write(text);
	}

	public static String getScenarioName() {
		return ScenarioContext.scenario.getName();
	}

	public static void setScenario(Scenario scenario) {
		ScenarioContext.scenario = scenario;
	}

	/**
	 * @param screenshot
	 */
	public static void embedScreenshot(byte[] screenshot) {
		embedTextInReport("TimeStamp==>" + (new SimpleDateFormat("hh:mm:ss")).format(new Date()));
		ScenarioContext.scenario.embed(screenshot, "image/png");
	}

}
