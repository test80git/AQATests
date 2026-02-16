package ru.kuzya.restassured.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Tag("UI")
public class ScreenTests {
    Logger log = LoggerFactory.getLogger(ScreenTests.class);

    @Test
    public void testScreenIphone12Pro(TestInfo testInfo) throws IOException {
        Configuration.browserSize = "390x844";
//        Selenide.open("https://threadqa.ru");
        Selenide.open("https://lkfl.vodokanalrnd.ru/login");
        assertScreen(testInfo);
    }

    private void assertScreen(TestInfo testInfo) throws IOException {
        String expectedFileName = testInfo.getTestMethod().get().getName() + ".png";
        // Относительные пути (работают на любой ОС)
        String expectedScreenDir = "src/test/resources/screens/";
        String diffDir = "build/diffs/";

        // Создаем директорию для скриншотов, если её нет
        createDirectoryIfNotExists(expectedScreenDir);
        createDirectoryIfNotExists(diffDir);

        File actualScreenshot = Selenide.screenshot(OutputType.FILE);
        File expectedScreenshot = new File(expectedScreenDir + expectedFileName);

        if (!expectedScreenshot.exists()) {
            addImgToAllure("actual", actualScreenshot);
            Files.copy(Path.of(actualScreenshot.getAbsolutePath()), Paths.get(expectedScreenDir + expectedFileName));
            throw new IllegalArgumentException("Не могу добавить изображение, потому что нет ссылки. Фактический экран может " +
                                               "можно загрузить с сайта allure");
        }

        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedScreenDir + expectedFileName);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualScreenshot.toPath().toString());

        String pathname = "build/diffs/diff_" + expectedFileName;
        File resultDestination = new File(pathname);

        ImageComparison imageComparison = new ImageComparison(expectedImage, actualImage, resultDestination);
        ImageComparisonResult result = imageComparison.compareImages();

        if (!result.getImageComparisonState().equals(ImageComparisonState.MATCH)) {
            addImgToAllure("actual", actualScreenshot);
            addImgToAllure("expected", expectedScreenshot);
            addImgToAllure("diff", resultDestination);
            // Сохраняем diff если его еще нет
            if (!resultDestination.exists()) {
                try {
                    ImageComparisonUtil.saveImage(resultDestination, result.getResult());
                } catch (Exception e) {
                    log.error("Не удалось сохранить diff", e);
                }
            }
        }
        Assertions.assertEquals(ImageComparisonState.MATCH, result.getImageComparisonState());
    }


    private void addImgToAllure(String name, File file) {
        try {
            byte[] image = Files.readAllBytes(file.toPath());
            saveScreenshot(name, image);
        } catch (IOException e) {
            throw new RuntimeException("Не могу прочитать байты");
        }
    }


    @Attachment(value = "{name}", type = "image/png")
    private static byte[] saveScreenshot(String name, byte[] image) {
        return image;
    }

    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

}
