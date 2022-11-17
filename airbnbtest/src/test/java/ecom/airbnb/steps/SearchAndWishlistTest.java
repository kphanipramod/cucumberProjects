package ecom.airbnb.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import ecom.airbnb.pages.LandingPage;
import ecom.airbnb.pages.LoginPage;
import ecom.airbnb.pages.SearchPage;
import ecom.airbnb.pages.WishlistPage;

public class SearchAndWishlistTest extends BaseStep {

	private LandingPage landingPage = new LandingPage();
	private LoginPage login = new LoginPage();
	private SearchPage search = new SearchPage();
	private WishlistPage wishList = new WishlistPage();

	@Given("Goto airbnb website")
	public void goto_airbnb_website() {
		landingPage.openAirBnbWebSite();
	}

	/**
	 * @param username
	 * @param password
	 */
	@Then("login on the website with {string} and {string} and change currency to euro")
	public void loginOnWebSiteWith(String username, String password) {
		landingPage.openLoginWindow();
		login.login(username, password);
		landingPage.clickOnChangeCurrenyButton();
	}

	/**
	 * @param type
	 * @param place
	 * @param month
	 * @param numberOfAdults
	 * @param numberOfKids
	 * @param numberOfPets
	 * @param amount
	 */
	@Then("Search for an {string} in {string} in the month of {string} with {string} adults {string} kids and {string} pet costs uptos {string} euro")
	public void search_for_an_in_in_the_month_of_with_adults_kids_and_pet(String type, String place, String month,
			String numberOfAdults, String numberOfKids, String numberOfPets, String amount) {
		search.searchForAPlace(type, place, month, numberOfAdults, numberOfKids, numberOfPets, amount);
	}

	@Then("look at first listing view pictures and cancellation policy and add to wishlist")
	public void look_at_first_listing_and_add_to_wishlist() {
		wishList.addFirstListingToWishlist();
	}

	@Then("look at second listing view pictures and cancellation policy and add that to wishlist")
	public void look_at_second_listing_and_add_that_to_wishlist() {
		wishList.addSecondListingToWishlist();
	}

	@Then("look at thrid listing view pictures and cancellation policy and add that to wishlist")
	public void look_at_thrid_listing_and_add_that_too_to_wishlist() {
		wishList.addThirdListingToWishlist();
	}

	@Then("validate that {int} listings are available in wishlist")
	public void validate_that_listings_are_available_in_wishlist(Integer int1) throws Exception {
		wishList.validateItemsInWishList(int1);
	}

	//Additonal Step to remve wishlist items to re-run the test cases multiple times with no wishlist items saved before
	@Then("remove all items from wishlist")
	public void remove_all_items_from_wishlist() {
		wishList.deleteWishList();
	}

	@Then("logout from the application")
	public void logout_from_the_application() {
		landingPage.logout();
	}
	

}
