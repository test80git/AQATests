package ru.kuzya.e2eplaywright.tests;


import org.junit.jupiter.api.Test;
import ru.kuzya.e2eplaywright.models.ShipInfo;
import ru.kuzya.e2eplaywright.pages.CartPage;
import ru.kuzya.e2eplaywright.pages.ProductsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProductsTest extends BaseTest {

    @Test
    public void testSuccessfulLogout() {
        loginPage.loginAs("standard_user", "secret_sauce")
                .clickOnLogout();
        assertThat(page).hasURL("https://www.saucedemo.com/");
    }

    @Test
    public void testSortItems() {
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");

        assertThat(productsPage.getProductNames().first()).hasText("Sauce Labs Backpack");

        productsPage.setSortFilter("Name (Z to A)");

        assertThat(productsPage.getProductNames().first()).hasText("Test.allTheThings() T-Shirt (Red)");
    }

    @Test
    public void testAddItemToCartAndBuy() {
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        String firstItemName = productsPage.getProductNames().first().textContent();

        CartPage cartPage = productsPage.addItemToCart(firstItemName).clickOnCart();

        assertThat(cartPage.getItems()).containsText(firstItemName);

        ShipInfo shipInfo = ShipInfo.builder()
                .firstName("Oleg")
                .lastName("ThreadQA")
                .zip("123456")
                .build();

        cartPage.clickOnCheckout().fillInfo(shipInfo).clickOnContinue().clickOnFinish();

        assertThat(cartPage.getCompleteHeader()).hasText("Thank you for your order!");
    }
}