package ru.kuzya.restassured.ui.helpDesc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.kuzya.restassured.ui.core.BaseSeleniumTest;

@Tag("UI")
public class HelpDescTest extends BaseSeleniumTest {

    @Test
    public void searchInYandexText() {
        MainPage mainPage = new MainPage();
        mainPage.closeBanner();
        mainPage.searchText("Iphone");
        String searchResult = mainPage.getSearchResult();
        Assertions.assertTrue(searchResult.contains("iPhone"));
    }
}
