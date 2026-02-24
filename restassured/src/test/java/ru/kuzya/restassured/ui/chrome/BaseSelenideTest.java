package ru.kuzya.restassured.ui.chrome;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

abstract public class BaseSelenideTest {
    /**
     * Инициализация selenide с настройками
     */
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
//        Configuration.driverManagerEnabled = true;
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;  // измени на true для CI

        // === ТАЙМАУТЫ ===
        Configuration.timeout = 30000;              // Общий таймаут ожидания элементов (30 секунд)
        Configuration.pollingInterval = 500;        // Интервал опроса элементов (500ms)
        Configuration.pageLoadTimeout = 60000;      // Таймаут загрузки страницы (60 секунд)

        // Браузер замедляет все действия
        Configuration.fastSetValue = false;
    }

    /**
     * Выполнение метода перед каждым запуском тестов
     */
    @BeforeEach
    public void init(){
        setUp();
    }

    /**
     * Выполнение метода после каждого закрытия тестов
     */
    @AfterEach
    public void tearDown(){
        Selenide.closeWebDriver();
    }

    // Метод для паузы между действиями
    protected void pause(long milliseconds) {
        Selenide.sleep(milliseconds);
    }



}
