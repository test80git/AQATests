package ru.kuzya.e2eplaywright.pages;


import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import ru.kuzya.e2eplaywright.components.Header;
import ru.kuzya.e2eplaywright.components.SideNavMenu;
import ru.kuzya.e2eplaywright.utils.BasePageFactory;

public final class ProductsPage extends BasePage {

    private Header header;
    private SideNavMenu sideNavMenu;

    @Override
    public void initComponents() {
        header = new Header(page);
        sideNavMenu = new SideNavMenu(page);
    }

    @Step("Get title of the 'Products' page")
    public Locator getTitle() {
        return page.locator(".title");
    }

    @Step("Get all products names")
    public Locator getProductNames() {
        return page.locator("//div[@data-test='inventory-item-name']");
    }

    @Step("Set <sortName> into 'Sort Filter'")
    public ProductsPage setSortFilter(String sortName){
        page.locator("//select[@data-test='product-sort-container']").selectOption(sortName);
        return this;
    }

    @Step("Add to cart item with name <itemName>")
    public ProductsPage addItemToCart(String itemName){
        page.locator(String.format("//div[text()='%s']//following::button[1]", itemName)).click();
        return this;
    }

    @Step("Click on Cart button")
    public CartPage clickOnCart() {
        header.clickOnCart();
        return BasePageFactory.createInstance(page, CartPage.class);
    }

    @Step("Click on 'Logout' button from side navigation menu")
    public LoginPage clickOnLogout() {
        header.clickOnHamburgerIcon();
        sideNavMenu.clickOnLogout();

        return BasePageFactory.createInstance(page, LoginPage.class);
    }

}