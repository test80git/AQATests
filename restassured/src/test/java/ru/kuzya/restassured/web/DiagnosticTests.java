package ru.kuzya.restassured.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Диагностический класс для проверки работы Allure Attachment
 */
@Tag("UI")
public class DiagnosticTests {
    private static final Logger log = LoggerFactory.getLogger(DiagnosticTests.class);

    @Test
    public void diagnosticTest() {
        log.info("=== ДИАГНОСТИКА ALLURE ATTACHMENT ===");

        // 1. Текстовый аттачмент
        attachText("Текстовое сообщение, которое отображается в Test body Allure");

        // 2. Изображение (сгенерированное)
        attachImage(createDummyImage());

        // 3. Скриншот через Selenide (если доступен)
        attachScreenshot();
    }

    /**
     * Прикрепляет текстовое сообщение к отчету Allure
     */
    @Attachment(value = "Текстовый аттачмент", type = "text/plain")
    private String attachText(String message) {
        return message;
    }

    /**
     * Прикрепляет изображение к отчету Allure
     */
    @Attachment(value = "Изображение", type = "image/png")
    private byte[] attachImage(byte[] image) {
        return image;
    }

    /**
     * Делает скриншот через Selenide и прикрепляет к отчету
     */
    private void attachScreenshot() {
        Configuration.browserSize = "390x844";
        Selenide.open("https://google.com");
        try {
            File screenshot = Selenide.screenshot(OutputType.FILE);
            if (screenshot != null && screenshot.exists()) {
                log.info("Screenshot :{}", screenshot.toPath());
                byte[] imageBytes = Files.readAllBytes(screenshot.toPath());
                attachScreenshotBytes("Скриншот", imageBytes);
                log.info("Скриншот прикреплен: {} ({} байт)", screenshot.getName(), imageBytes.length);
            }
        } catch (Exception e) {
            log.debug("Не удалось сделать скриншот: {}", e.getMessage());
        }
    }

    /**
     * Прикрепляет скриншот с именем к отчету Allure
     */
    @Attachment(value = "{name}", type = "image/png")
    private byte[] attachScreenshotBytes(String name, byte[] image) {
        return image;
    }

    /**
     * Создает тестовое изображение 1x1 пиксель
     */
    private byte[] createDummyImage() {
        try {
            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            log.error("Ошибка создания изображения", e);
            return new byte[0];
        }
    }
}