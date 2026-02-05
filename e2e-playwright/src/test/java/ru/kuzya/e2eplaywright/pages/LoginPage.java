package ru.kuzya.e2eplaywright.pages;


import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import ru.kuzya.e2eplaywright.utils.BasePageFactory;

import static ru.kuzya.e2eplaywright.config.ConfigurationManager.config;

public final class LoginPage extends BasePage {

    @Step("Открытие страницы входа")
    public LoginPage open() {
        page.navigate(config().baseUrl());
        return this;
    }

    @Step("Type <username> into 'Username' textbox")
    public LoginPage typeUsername(final String username) {
        page.fill("id=user-name", username); // XPath //*[@id="user-name"]
        return this;
    }

    @Step("Type <password> into 'Password' textbox")
    public LoginPage typePassword(final String password) {
        page.fill("id=password", password); // XPath //*[@id="password"]
        return this;
    }

    @Step("Get error message")
    public Locator getErrorMessage() {
        return page.locator(".error-message-container h3");
    }

    @Step("Click on the 'Login' button")
    public ProductsPage submitLogin() {
        page.click("id=login-button");
        return BasePageFactory.createInstance(page, ProductsPage.class);
    }

    @Step("Попытка входа в Swag Labs")
    public ProductsPage loginAs(final String username, final String password) {
        open();
        typeUsername(username);
        typePassword(password);
        return submitLogin();
    }

}

