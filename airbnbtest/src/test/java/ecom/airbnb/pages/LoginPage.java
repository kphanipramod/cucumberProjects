package ecom.airbnb.pages;

import org.openqa.selenium.By;

import com.automation.hooks.ScenarioContext;
import com.polk.test.automation.webdriver.ConfUtil;
import com.polk.test.automation.webdriver.LocalDriver;

public class LoginPage extends AbstractPage {

	public LoginPage(String path, LocalDriver driver, int waitTimeOutSeconds) {
		super(path, driver, waitTimeOutSeconds);
	}

	public LoginPage() {
		super(ConfUtil.getAppBaseUrl(), ScenarioContext.driver, ConfUtil.getSeleniumWaitTimeOutSeconds());
	}

	private By continueWithEmail = By.xpath("//div[text()='Continue with email']");
	private By emailField = By.name("user[email]");
	private By continueButton = By.xpath("//button[@data-testid=\"signup-login-submit-btn\"]");
	private By passwordField = By.name("user[password]");
	

	/**
	 * @param userName
	 * @param password
	 */
	public void login(String userName, String password) {
		clickOnElement(continueWithEmail);
		setText(emailField, userName);
		clickOnElement(continueButton);
		setText(passwordField, password);
		clickOnElement(continueButton);
	}

}
