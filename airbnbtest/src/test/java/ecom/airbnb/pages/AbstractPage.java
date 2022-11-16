package ecom.airbnb.pages;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.polk.test.automation.webdriver.ConfUtil;
import com.polk.test.automation.webdriver.LocalDriver;

/**
 * Reusable methods for all page objects
 */
public abstract class AbstractPage {

	private final LocalDriver driver;
	protected final int waitTimeOutSeconds;
	private final String path;
	private static final Logger LOG = Logger.getLogger(AbstractPage.class.getName());

	public AbstractPage(final String path, LocalDriver driver, int waitTimeOutSeconds) {
		this.path = path;
		this.driver = driver;
		this.waitTimeOutSeconds = waitTimeOutSeconds;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void additonalPageLoadWaits() {
		try {
			do {
				Thread.sleep(2000);
			} while (!(String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
					.equals("complete")));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void waitForPageLoad() {
		try {
			Wait<WebDriver> wait = new WebDriverWait(driver,
					Duration.ofSeconds(ConfUtil.getSeleniumWaitTimeOutSeconds()));
			wait.until(new Function<WebDriver, Boolean>() {
				public Boolean apply(WebDriver driver) {
					return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
							.equals("complete");
				}
			});
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param element
	 */
	public void scrollElementIntoView(WebElement element) {
		try {
			LOG.debug("Trying to scroll element into view-->" + element);
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			LOG.error("Error occurred while scrolling element " + element + " into view");
			throw e;
		}
	}

	/**
	 * @param url
	 */
	public void goToAndWait(String url) {
		getDriver().navigate().to(url);
		waitForLoad(getDriver(), waitTimeOutSeconds);
	}

	/**
	 * @param driver
	 * @param timeOutSeconds
	 */
	public void waitForLoad(WebDriver driver, int timeOutSeconds) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds));
		wait.until(pageLoadCondition);
	}

	/**
	 * wait until condition is true or timeout is reached
	 */
	protected <V> V waitUntilTrueOrTimeout(ExpectedCondition<V> isTrue) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitTimeOutSeconds))
				.ignoring(StaleElementReferenceException.class);
		try {
			return wait.until(isTrue);
		} catch (TimeoutException rte) {
			System.out.println("Expected condition failed ===>" + isTrue.toString());
			LOG.error("Error occured in waitUntilTrueOrTimeout.");
			throw new TimeoutException(rte.getMessage());
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param element
	 * @param text
	 */
	public void setText(WebElement element, String text) {
		try {
			waitForPageLoad();
			if (!isElementVisible(element)) {
				scrollElementIntoView(element);
			}

			element.clear();
			LOG.info("Setting " + text + " in element " + element.toString());
			element.sendKeys(text);
		} catch (Exception e) {
			System.out.println(
					"Error occured while trying to set text==>" + text + " in webelment==>" + element.toString());
			throw e;
		}
	}

	/**
	 * @param element
	 * @param key
	 */
	public void setText(WebElement element, Keys key) {
		try {
			waitForPageLoad();
			scrollElementIntoView(element);
			element.clear();
			LOG.info("Setting " + key + " in element " + element.toString());
			element.sendKeys(key);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param element
	 * @param text
	 */
	public void setText(WebElement element, int text) {
		try {
			waitForPageLoad();
			if (!isElementVisible(element)) {
				scrollElementIntoView(element);
			}
			element.clear();
			LOG.info("Setting " + text + " in element " + element.toString());
			element.sendKeys(String.valueOf(text));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param elementXpath
	 * @param text
	 */
	public void setText(By elementXpath, String text) {
		try {
			WebElement element = find(elementXpath);
			setText(element, text);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param element
	 */
	public void clickOnElement(WebElement element) {
		try {
			LOG.info("Clicking on element::" + element.toString());
			waitForPageLoad();
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitTimeOutSeconds));
			if (!isElementVisible(element)) {
				scrollElementIntoView(element);
			}
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));

			element.click();
		} catch (ElementClickInterceptedException e) {
			try {
				scrollElementIntoView(element);
				element.click();
			} catch (Exception ex) {
				throw ex;
			}
		} catch (Exception e) {
			LOG.error("Unable to click on element::" + element + e.getMessage());
			throw e;
		}
	}

	/**
	 * @param locator
	 */
	public void clickOnElement(By locator) {
		try {
			waitUntilTrueOrTimeout((ExpectedConditions.elementToBeClickable(locator)));
			clickOnElement(find(locator));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param locator
	 * @return
	 */
	public WebElement find(By locator) {
		try {
			waitForPageLoad();
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitTimeOutSeconds));
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return getDriver().findElement(locator);
		} catch (NoSuchElementException ex) {
			LOG.error("Unable to locate element with locator::" + locator + ", error: " + ex.getMessage());
			throw new NoSuchElementException(ex.getMessage());
		}
	}

	/**
	 * @param locator
	 * @return
	 */
	public WebElement find(String locator) {
		try {
			return find(By.xpath(locator));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param locator
	 * @param smallTimeOut
	 * @return
	 */
	protected WebElement find(By locator, int smallTimeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(smallTimeOut));
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return getDriver().findElement(locator);
		} catch (NoSuchElementException ex) {
			LOG.error("Unable to locate element with locator::" + locator + ", error: " + ex.getMessage());
			throw new NoSuchElementException(ex.getMessage());
		}
	}

	/**
	 * @param locator
	 * @param smallTimeOut
	 * @return
	 */
	protected WebElement findWithoutError(By locator, int smallTimeOut) {
		try {
			waitForPageLoad();
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(smallTimeOut));
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return getDriver().findElement(locator);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @param locator
	 * @return
	 */
	public List<WebElement> getElements(By locator) {
		try {
			waitForPageLoad();
			return getDriver().findElements(locator);
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * @param locator
	 * @return
	 */
	protected WebElement getElement(By locator) {
		try {
			waitForPageLoad();
			return getDriver().findElement(locator);
		} catch (NoSuchElementException ex) {
			throw new NoSuchElementException(ex.getMessage());
		}
	}

	public void waitUntilPageLoadingIsDone() {
		new WebDriverWait(getDriver(), Duration.ofSeconds(waitTimeOutSeconds))
				.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
						.equals("complete"));
	}

	/**
	 * @param element
	 * @return
	 */
	public boolean isElementVisible(WebElement element) {
		if (element == null) {
			return false;
		}
		try {
			waitForPageLoad();
			return element.isDisplayed();
		} catch (Exception e) {
			LOG.info("Element " + element + " is not visibible/present yet");
			return false;
		}
	}

	/**
	 * @param locator
	 * @return
	 */
	public boolean isElementEnabled(By locator) {
		try {
			try {
				return getDriver().findElement(locator).isEnabled();
			} catch (Exception e) {
				return false;
			}
		} catch (Exception e) {
			LOG.info("Error occurred while trying to check if element " + locator + " is visibible or not");
			return false;
		}
	}

	/**
	 * @param element
	 * @return
	 */
	public boolean isElementEnabled(WebElement element) {
		try {
			try {
				return element.isEnabled();
			} catch (Exception e) {
				return false;
			}
		} catch (Exception e) {
			LOG.info("Error occurred while trying to check if element " + element + " is enabled or not");
			return false;
		}
	}

	/**
	 * @param locator
	 * @return
	 */
	public boolean isElementVisible(By locator) {
		try {
			waitForPageLoad();
			return getDriver().findElement(locator).isDisplayed();
		} catch (Exception e) {
			LOG.info("Element " + locator + " is not visibible/present yet");
			return false;
		}
	}

	/**
	 * @param element
	 * @return
	 */
	public boolean isElementPresent(WebElement element) {
		try {
			if (element != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LOG.error("Error occurred while trying to check if " + element + " is present on the screen");
			return false;
		}
	}

	/**
	 * @param locator
	 * @return
	 */
	public boolean isElementPresent(By locator) {
		try {
			List<WebElement> element = getDriver().findElements(locator);
			if (element == null || element.size() == 0 || element.isEmpty()) {
				LOG.info("Element not present " + locator);
				return false;
			}
			return true;
		} catch (Exception e) {
			LOG.error("Error occurred while trying to check if " + locator + " is present on the screen");
			return false;
		}
	}

	/**
	 * @param xpath
	 * @return
	 */
	public boolean isElementPresent(String xpath) {
		try {
			List<WebElement> element = getDriver().findElements(By.xpath(xpath));
			if (element == null || element.size() == 0 || element.isEmpty()) {
				LOG.info("Element not present " + xpath);
				return false;
			}
			return true;
		} catch (Exception e) {
			LOG.error("Error occurred while trying to check if " + xpath + " is present on the screen");
			return false;
		}
	}


	public byte[] getScreenshot() {
		try {
			byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
			return screenshot;
		} catch (Exception e) {
			LOG.error("Error occurred while trying to take screenshot. " + e.getMessage());
			throw e;
		}
	}

	/**
	 * @param element
	 * @throws Exception
	 */
	public void javaScriptClick(WebElement element) throws Exception {
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Element is not attached to the page document " + e.getStackTrace());
			throw e;
		} catch (NoSuchElementException e) {
			System.out.println("Element was not found in DOM " + e.getStackTrace());
			throw e;
		} catch (Exception e) {
			System.out.println("Unable to click on element " + e.getStackTrace());
			throw e;
		}
	}

}
