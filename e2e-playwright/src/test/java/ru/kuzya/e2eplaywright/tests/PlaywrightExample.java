package ru.kuzya.e2eplaywright.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;

public class PlaywrightExample {

    @Test
    @SneakyThrows
    void основныеМетоды() {
        try (Playwright playwright = Playwright.create()) {
            // Создание экземпляра браузера
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false) // ← ВАЖНО: браузер будет виден
                            .setDevtools(true) // Открыть DevTools
                            .setArgs(Arrays.asList("--start-maximized")) // Запуск максимизированным
                            .setSlowMo(500) // ← Опционально: замедлить действия на 500ms для наглядности
            );

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setViewportSize(1920, 1080) // ← Опционально: установить размер окна
            );

            Page page = context.newPage();

            // Выполнение действий
            page.navigate("https://example.com");
            System.out.println("Заголовок страницы: " + page.title());
            // Сделать скриншот для проверки
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("screenshot.png")));

            // Можно добавить паузу, чтобы увидеть результат
            page.waitForTimeout(3000); // Пауза 3 секунды
            Assertions.assertEquals("Example Domain", page.title(), "Название страницы должно совпадать");
            // Закрытие ресурсов
            page.close();
            context.close();
            browser.close();
        }
    }

}
