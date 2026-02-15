package ru.kuzya.restassured.ui.helpDesc;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.kuzya.restassured.ui.core.BaseSeleniumPage;

import java.time.Duration;


public class MainPage extends BaseSeleniumPage {
    private final By qu = By.id("requestId");

    private final String expression = "//button[@class='Button DistributionButton DistributionButtonClose DistributionButtonClose_view_button Button_size_m']";
    private final By queue = By.xpath(expression);


    @FindBy(xpath = "//button[@class='search3__button search3__button_icon_yes mini-suggest__button']")
    private WebElement searchButton;

    @FindBy(id = "text")
    private WebElement searchInput;

    @FindBy(xpath = "//*[@id=\"search-result\"]/li[3]")
    private WebElement searchResult;

    // Для клика по аватарке (если нужен)
    @FindBy(xpath = "//span[@class='avatar__image-wrapper']")
    private WebElement avatar;


    public MainPage() {
        driver.get("https://ya.ru/?npr=1");
        PageFactory.initElements(driver, this);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("text")));
    }

    public void closeBanner() {
        driver.findElement(queue).click();
    }

    public void searchText(String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(searchInput));

        searchInput.clear();
        searchInput.sendKeys(text, Keys.ENTER);

//        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void clickAvatar() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(avatar));
        avatar.click();
    }

    public String getSearchResult() {
        System.out.println("searchResult: " + searchResult.getText());
        System.out.println("getAccessibleName: " + searchResult.getAccessibleName());
        return searchResult.getText();
    }
}
