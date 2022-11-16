package ecom.airbnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.automation.hooks.ScenarioContext;
import com.automation.hooks.ScenarioUtil;
import com.polk.test.automation.webdriver.ConfUtil;
import com.polk.test.automation.webdriver.LocalDriver;

public class WishlistPage extends AbstractPage {

	public WishlistPage(String path, LocalDriver driver, int waitTimeOutSeconds) {
		super(path, driver, waitTimeOutSeconds);
	}

	public WishlistPage() {
		super(ConfUtil.getAppBaseUrl(), ScenarioContext.driver, ConfUtil.getSeleniumWaitTimeOutSeconds());
	}

	String defaultWindowHandle;

	private By firstListing = By.xpath("(//*[@id='FMP-target'])[1]");
	private By secondListing = By.xpath("(//*[@id='FMP-target'])[2]");
	private By thirdListing = By.xpath("(//*[@id='FMP-target'])[3]");
	private By addToWishList = By.xpath("//button[@aria-label=\"Add listing to a list\"]");
	private By loginPopup = By.xpath("//button[@data-testid=\"cypress-headernav-profile\"]/div");
	private By nameOfWishList = By.id("name-list-input-save-to-list-modal");
	private By viewWishList = By.linkText("Wishlists");
	private By createWishList = By.xpath("//button[text()='Create']");
	private By firstWishListLink = By.xpath("//div[text()='FirstWishList']");
	private By listOfwishes = By.xpath("//div[@aria-describedby=\"carousel-description\"]");
	private By settingPage = By.xpath("//button[@aria-label=\"Settings\"]");
	private By deleteWishListButton = By.xpath("//button[text()='Delete']");
	private By confirmDelete = By.xpath("//button[text()='Yes, delete']");
	private By viewPicture =By.xpath("//div[text()='Show all photos']");
	private By viewCancellationPolicy = By.xpath("//h3[text()='Cancellation policy']/parent::div/following-sibling::div/button");
	private By closeCancellationPolicy = By.xpath("//button[@aria-label=\"Close\"]");
	
	
	public void viewPictures() {
		additonalPageLoadWaits();
		additonalPageLoadWaits();
		clickOnElement(viewPicture);
		additonalPageLoadWaits();
		additonalPageLoadWaits();
		ScenarioUtil.embedScreenshot(getScreenshot());
		getDriver().navigate().back();
	}
	
	public void viewCancellationPolicy() {
		additonalPageLoadWaits();
		additonalPageLoadWaits();
		clickOnElement(viewCancellationPolicy);
		additonalPageLoadWaits();
		additonalPageLoadWaits();
		ScenarioUtil.embedScreenshot(getScreenshot());
		clickOnElement(closeCancellationPolicy);
		additonalPageLoadWaits();
	}
	
	public void addFirstListingToWishlist() {
		defaultWindowHandle = getDriver().getWindowHandle();
		additonalPageLoadWaits();
		additonalPageLoadWaits();
		clickOnElement(firstListing);
		for (String e : getDriver().getWindowHandles()) {
			if (!e.equalsIgnoreCase(defaultWindowHandle)) {
				getDriver().switchTo().window(e);
			}
		}
		viewPictures();
		viewCancellationPolicy();
		clickOnElement(addToWishList);
		setText(nameOfWishList, "FirstWishList");
		clickOnElement(createWishList);
		getDriver().switchTo().window(defaultWindowHandle);
	}

	public void addSecondListingToWishlist() {
		clickOnElement(secondListing);
		for (String e : getDriver().getWindowHandles()) {
			if (!e.equalsIgnoreCase(defaultWindowHandle)) {
				getDriver().switchTo().window(e);
			}
		}
		viewPictures();
		viewCancellationPolicy();
		clickOnElement(addToWishList);
		clickOnElement(firstWishListLink);
		getDriver().switchTo().window(defaultWindowHandle);
	}

	public void addThirdListingToWishlist() {
		clickOnElement(thirdListing);
		for (String e : getDriver().getWindowHandles()) {
			if (!e.equalsIgnoreCase(defaultWindowHandle)) {
				getDriver().switchTo().window(e);
			}
		}
		viewPictures();
		viewCancellationPolicy();
		clickOnElement(addToWishList);
		clickOnElement(firstWishListLink);
		getDriver().switchTo().window(defaultWindowHandle);
	}

	/**
	 * @param expectedNumberOfListingInWishList
	 * @throws Exception
	 */
	public void validateItemsInWishList(int expectedNumberOfListingInWishList) throws Exception {
		clickOnElement(loginPopup);
		clickOnElement(viewWishList);
		clickOnElement(firstWishListLink);
		waitUntilTrueOrTimeout(ExpectedConditions.visibilityOfAllElements(find(listOfwishes)));
		int numberOfListsing = getElements(listOfwishes).size();
		if (expectedNumberOfListingInWishList != numberOfListsing) {
			throw new Exception(String.format("Expected %s listings in wishlist found %s",
					expectedNumberOfListingInWishList, numberOfListsing));
		}
		ScenarioUtil.embedScreenshot(getScreenshot());
	}

	public void deleteWishList() {
		try {
			javaScriptClick(find(settingPage));
		} catch (Exception e) {
			e.printStackTrace();
		}
		clickOnElement(deleteWishListButton);
		clickOnElement(confirmDelete);
	}

}
