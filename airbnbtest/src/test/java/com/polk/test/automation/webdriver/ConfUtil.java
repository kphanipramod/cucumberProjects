package com.polk.test.automation.webdriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class ConfUtil {

	private static Properties properties;
	private static final String DEFAULT_FILE_NAME = "conf/test.properties";

	private ConfUtil() {
	}

	/*
	 * To load properties with custom file, if given file name is blank then it
	 * loads default properties
	 */
	public static Properties load(final String fileName) {
		if (properties != null) {
			return properties;
		}
		try (InputStream inStream = ConfUtil.class.getClassLoader()
				.getResourceAsStream(fileName == null ? DEFAULT_FILE_NAME : fileName)) {
			properties = new Properties();
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// overwrite with system properties
		loadSystemProperties(properties);

		return properties;
	}

	/**
	 * @param localProps
	 * @return
	 */
	private static Properties loadSystemProperties(Properties localProps) {
		final Properties systemProperties = System.getProperties();
		final Enumeration<?> e = systemProperties.propertyNames();
		String key;
		String value;
		while (e.hasMoreElements()) {
			key = (String) e.nextElement();
			value = systemProperties.getProperty(key);
			localProps.setProperty(key, value);
		}
		return localProps;
	}

	/*
	 * Loads default test.properties from classpath
	 */
	public static Properties load() {
		return load(null);
	}

	/**
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		if (properties == null) {
			load();
		}

		return properties.getProperty(key);
	}

	public static String getAppBaseUrl() {
		return getProperty("acceptance.test.application.base.url");
	}
	

	public static String getBrowser() {
		return getProperty("acceptance.test.application.browser");
	}

	public static int getSeleniumWaitTimeOutSeconds() {
		return Integer.parseInt(getProperty("acceptance.test.selenium.wait.timeout.seconds"));
	}


}
