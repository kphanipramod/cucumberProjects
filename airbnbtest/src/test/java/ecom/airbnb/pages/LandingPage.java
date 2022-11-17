package ecom.airbnb.pages;

import org.openqa.selenium.By;

import com.automation.hooks.ScenarioContext;
import com.polk.test.automation.webdriver.ConfUtil;
import com.polk.test.automation.webdriver.LocalDriver;

public class LandingPage extends AbstractPage {

	public LandingPage(String path, LocalDriver driver, int waitTimeOutSeconds) {
		super(path, driver, waitTimeOutSeconds);
	}

	public LandingPage() {
		super(ConfUtil.getAppBaseUrl(), ScenarioContext.driver, ConfUtil.getSeleniumWaitTimeOutSeconds());
	}

	private By changeCurrencyButton = By.xpath("//button[@aria-label=\"Choose a language and currency\"]");
	private By currencyButton = By.xpath("//button[text()='Currency']");
	private By euroCurrencyButton = By.xpath("//button/div[text()='EUR – €']/parent::button");
	private By loginPopup = By.xpath("//button[@data-testid=\"cypress-headernav-profile\"]/div");
	private By loginLink = By.linkText("Log in");
	private By logoutLink = By.xpath("//form/button/div");
	private By acceptCookies = By.xpath("//button[text()='OK']");
	private By popupWindowClose = By.xpath("//button[@aria-label='Close']");

	public void clickOnChangeCurrenyButton() {
		additonalPageLoadWaits();
		additonalPageLoadWaits();
		clickOnElement(changeCurrencyButton);
		clickOnElement(currencyButton);
		clickOnElement(euroCurrencyButton);
		additonalPageLoadWaits();
		additonalPageLoadWaits();
		if (isElementPresent(acceptCookies)) {
			clickOnElement(find(acceptCookies));
		}
	}

	public void openAirBnbWebSite() {
		goToAndWait("https://www.airbnb.com/");
		additonalPageLoadWaits();
		additonalPageLoadWaits();
		additonalPageLoadWaits();
		if (isElementPresent(acceptCookies)) {
			clickOnElement(find(acceptCookies));
		}
		if (isElementPresent(popupWindowClose)) {
			clickOnElement(find(popupWindowClose));
		}
	}

	public void openLoginWindow() {
		clickOnElement(find(loginPopup));
		clickOnElement(find(loginLink));
	}

	public void logout() {
		clickOnElement(find(loginPopup));
		clickOnElement(find(logoutLink));
	}
}
