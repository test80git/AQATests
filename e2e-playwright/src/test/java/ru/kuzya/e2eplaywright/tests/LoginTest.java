package ru.kuzya.e2eplaywright.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.kuzya.e2eplaywright.pages.ProductsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {

    @Test
    public void testCorrectLoginCredentials() {
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        assertThat(productsPage.getTitle()).hasText("Products");
    }

    @Test
    @DisplayName("Тест валидный видео не отображается в алюротчёте")
    public void testLockedOutUser() {
        loginPage.loginAs("wrong", "fake");
        assertThat(loginPage.getErrorMessage())
                .hasText("Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    @DisplayName("Тест не валидный для отображения видео в алюротчёте")
    public void testLockedOutUserWithNoValidText() {
        loginPage.loginAs("wrong", "fake");
        assertThat(loginPage.getErrorMessage())
                .hasText("Epic 1 sadface: Username and password do not match any user in this service");
    }
}