package com.automation.hooks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.polk.test.automation.webdriver.LocalDriver;

import cucumber.api.Scenario;

public class ScenarioContext {
	
	public static LocalDriver driver;
	public static Scenario scenario;

	// To share data between steps
	// So for now we are considering the values will be saved using key1 = scenario name, key2 = type of data
	public static Map<String, Map<String, List<String>>> currentData = new HashMap<>();
	
	public static Map<String, Map<String, Object>> currentRawData = new HashMap<>();
	
}
