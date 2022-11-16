package ecom.airbnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.automation.hooks.ScenarioContext;
import com.automation.hooks.ScenarioUtil;
import com.polk.test.automation.webdriver.ConfUtil;
import com.polk.test.automation.webdriver.LocalDriver;

import io.netty.handler.ssl.OpenSslCachingX509KeyManagerFactory;

public class SearchPage extends AbstractPage {

	public SearchPage(String path, LocalDriver driver, int waitTimeOutSeconds) {
		super(path, driver, waitTimeOutSeconds);
	}

	public SearchPage() {
		super(ConfUtil.getAppBaseUrl(), ScenarioContext.driver, ConfUtil.getSeleniumWaitTimeOutSeconds());
	}

	private By locationHeader = By.xpath("//div[text()='Anywhere']/ancestor::div[@role='search']");
	private By locationTextBox = By.id("bigsearch-query-location-input");
	private By checkInDateHeader = By.xpath("//div[text()='Check in']");
	private By flexibleDateHeaderOption = By.xpath("//button[contains(text(),'flexible')]");
	private By flexibleMonthSelection = By.id("flexible_trip_lengths-one_month");
	private By nextMonthButton = By.xpath("(//button[@aria-label=\"Next\"])[1]");
	private By whoButtonInHeader = By.xpath("//div[text()='Who']");
	private By increaseAdultCount = By.xpath("//*[@id='stepper-adults']/button[@aria-label='increase value']");
	private By increaseChildrenCount = By.xpath("//*[@id='stepper-children']/button[@aria-label='increase value']");
	private By increasePetCount = By.xpath("//*[@id='stepper-pets']/button[@aria-label='increase value']");
	private By searchButton = By.xpath("//button[@data-testid='structured-search-input-search-button']");
	private By filterButton = By.xpath("//span[text()='Filters']/parent::*/parent::*");
	private By priceMaxFilter = By.id("price_filter_max");
	private By showAllHomes = By.xpath("//a[contains(text(),'Show')]");
	

	/**
	 * @param type
	 * @param place
	 * @param month
	 * @param numberOfAdults
	 * @param numberOfKids
	 * @param numberOfPets
	 * @param maxAmount
	 */
	public void searchForAPlace(String type, String place, String month, String numberOfAdults, String numberOfKids,
			String numberOfPets, String maxAmount) {
		additonalPageLoadWaits();
		clickOnElement(locationHeader);
		setText(locationTextBox, place);
		ScenarioUtil.embedScreenshot(getScreenshot());
		clickOnElement(checkInDateHeader);
		clickOnElement(flexibleDateHeaderOption);
		clickOnElement(flexibleMonthSelection);
		clickOnElement(nextMonthButton);
		clickOnElement(nextMonthButton);
		clickOnElement(By.xpath("//div[text()='" + month + "']"));
		ScenarioUtil.embedScreenshot(getScreenshot());
		clickOnElement(whoButtonInHeader);
		int localNumberOfAdults = Integer.valueOf(numberOfAdults);
		while (localNumberOfAdults != 0) {
			clickOnElement(increaseAdultCount);
			localNumberOfAdults--;
		}
		ScenarioUtil.embedScreenshot(getScreenshot());
		int localNumberOfChilder = Integer.valueOf(numberOfKids);
		while (localNumberOfChilder != 0) {
			clickOnElement(increaseChildrenCount);
			localNumberOfChilder--;
		}
		ScenarioUtil.embedScreenshot(getScreenshot());
		int localNumberOfPets = Integer.valueOf(numberOfPets);
		while (localNumberOfPets != 0) {
			clickOnElement(increasePetCount);
			localNumberOfPets--;
		}
		ScenarioUtil.embedScreenshot(getScreenshot());
		clickOnElement(searchButton);
		additonalPageLoadWaits();
		clickOnElement(filterButton);
		additonalPageLoadWaits();
		clickOnElement(priceMaxFilter);
		while (find(priceMaxFilter).getAttribute("value").length() > 0) {
			setText(find(priceMaxFilter), Keys.BACK_SPACE);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		setText(find(priceMaxFilter), Keys.BACK_SPACE);
		setText(find(priceMaxFilter),maxAmount);
		clickOnElement(By.xpath("//div[text()='" + type + "']"));
		ScenarioUtil.embedScreenshot(getScreenshot());
		clickOnElement(showAllHomes);
		System.out.println("");
	}

	
	
}
