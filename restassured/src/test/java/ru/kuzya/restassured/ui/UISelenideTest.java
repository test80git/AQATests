package ru.kuzya.restassured.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

@Tag("UI")
@Execution(ExecutionMode.CONCURRENT)  // ← ВАЖНО для параллельного выполнения
public class UISelenideTest {

    private static final String value0 = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[4]/span[1]";
    private static final String value1 = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[3]/span[1]";
    private static final String value2 = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[3]/span[2]";
    private static final String value3 = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[3]/span[3]";
    private static final String value4 = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[2]/span[1]";
    private static final String value5 = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[2]/span[2]";
    private static final String value6 = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[2]/span[3]";
    private static final String value7 = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[1]/span[1]";
    private static final String value8 = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[1]/span[2]";
    private static final String value9 = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[1]/span[3]";
    private static final String Plus = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[1]/span[4]";
    private static final String Minus = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[2]/span[4]";
    private static final String Multiplication = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[3]/span[4]";
    private static final String Division = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[4]/span[4]";
    private static final String Equally = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[5]/span[4]";
    private static final String Answer = "//*[@id=\"sciOutPut\"]";
    private static final String AC = "//*[@id=\"sciout\"]/tbody/tr[2]/td[2]/div/div[5]/span[3]";


    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
//        Configuration.browser = "firefox";
        Configuration.headless = false; // измени на true для CI
        Configuration.browserSize = "1920x1080";

        // === ТАЙМАУТЫ ===
        Configuration.timeout = 30000;              // Общий таймаут ожидания элементов (30 секунд)
        Configuration.pollingInterval = 200;        // Интервал опроса элементов (200ms)
        Configuration.pageLoadTimeout = 60000;      // Таймаут загрузки страницы (60 секунд)

        // Браузер замедляет все действия
        Configuration.fastSetValue = false;         // Отключаем быстрый ввод (набирает посимвольно)

        // Дополнительно
        Configuration.savePageSource = true;        // Сохранять HTML при падении
        Configuration.screenshots = true;           // Делать скриншоты при падении

    }

    @BeforeEach
    public void openCalculator() {
        Selenide.open("https://www.calculator.net/");
        Selenide.screenshot("01_initial_page");
    }

    @Test
    public void testVariousCalculations() {
        assertCalculation("1+3", "4");

    }
    @Test
    @DisplayName("Проверяем порядок операций")
    public void testOrderOfOperations() {
        assertCalculation("2+3*4", "14");
    }

    @Test
    public void calcMinusTest() {
        assertCalculation("10-5", "5");
    }

    @Test
    public void calcMultipyTest() {
        assertCalculation("6*7", "42");
    }

    @Test
    public void calcDevideTest() {
        assertCalculation("8/2", "4");
    }

    @Test
    public void calcDevideFaultTest() {
        assertCalculation("8/2", "3");
    }

    private void assertCalculation(String expression, String expected) {
        String actual = calculate(expression);
        Assertions.assertEquals(expected, actual,
                "Calculation failed for: " + expression);
    }

    private String calculate(String expression) {
        // Словарь для маппинга символов на XPath
        Map<Character, String> buttonMap = createButtonMap();
        sleep(100);
        // Очистка
        clickIfExists(AC);

        // Обработка выражения
        for (char c : expression.toCharArray()) {
            String xpath = buttonMap.get(c);
            if (xpath != null) {
                $x(xpath).click();
                sleep(100);
            }
        }

        // Равно
        $x(Equally).click();
        Selenide.screenshot($x(Answer).getText().trim()+"-screenshot");
        sleep(100);
        // Результат
        return $x(Answer).getText().trim();
    }

    private Map<Character, String> createButtonMap() {
        Map<Character, String> map = new HashMap<>();

        // Цифры (адаптируй под реальные XPath)
        map.put('0', value0);
        map.put('1', value1);
        map.put('2', value2);
        map.put('3', value3);
        map.put('4', value4);
        map.put('5', value5);
        map.put('6', value6);
        map.put('7', value7);
        map.put('8', value8);
        map.put('9', value9);

        // Операторы
        map.put('+', Plus);
        map.put('-', Minus);
        map.put('*', Multiplication);
        map.put('/', Division);

        return map;
    }

    private void clickIfExists(String xpath) {
        if ($x(xpath).exists()) {
            $x(xpath).click();
        }
    }

}
