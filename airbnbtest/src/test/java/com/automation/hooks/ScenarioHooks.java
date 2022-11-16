package com.automation.hooks;

import org.apache.log4j.Logger;

import com.polk.test.automation.webdriver.WebDriverFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import ecom.airbnb.steps.BaseStep;

public class ScenarioHooks {

	private static final Logger LOG = Logger.getLogger(ScenarioHooks.class.getName());

	/**
	 * @param scenario
	 * @throws InterruptedException
	 */
	@Before()
	public void before(final Scenario scenario) throws InterruptedException {
		ScenarioContext.scenario = scenario;
		ScenarioContext.driver = WebDriverFactory.getLocalDriver();
	}


	/**
	 * @param scenario
	 */
	@After()
	public void afterScenario(final Scenario scenario) {
		BaseStep.softAssertion.assertAll();
		ScenarioContext.scenario = null;
	}

}
