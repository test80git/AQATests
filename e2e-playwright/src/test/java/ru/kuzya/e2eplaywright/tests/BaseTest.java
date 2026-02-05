package ru.kuzya.e2eplaywright.tests;

import com.google.common.collect.ImmutableMap;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.qameta.allure.Attachment;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;
import ru.kuzya.e2eplaywright.pages.BasePage;
import ru.kuzya.e2eplaywright.pages.LoginPage;
import ru.kuzya.e2eplaywright.utils.BasePageFactory;
import ru.kuzya.e2eplaywright.utils.BrowserManager;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static ru.kuzya.e2eplaywright.config.ConfigurationManager.config;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected LoginPage loginPage;
    private boolean needVideo;

    @RegisterExtension
    AfterTestExecutionCallback callback =
            context -> {
                Optional<Throwable> exception = context.getExecutionException();
                if (exception.isPresent()) {
                    needVideo = true;
                    captureScreenshotOnFailure();
                }
            };

    @AfterEach
    public void attach() {
        browserContext.close();
        if (config().video() && needVideo) {
            captureVideo();
        }
        needVideo = false;
    }

    @BeforeEach
    public void createContext() {
        if (config().video()) {
            browserContext = browser.newContext(new Browser.NewContextOptions()
                    .setRecordVideoDir(Paths.get(config().baseTestVideoPath())));
        } else {
            browserContext = browser.newContext();
        }
        page = browserContext.newPage();
        loginPage = createInstance(LoginPage.class);
    }


    @Attachment(value = "Test Video", type = "video/webm")
    @SneakyThrows
    private byte[] captureVideo() {
        return Files.readAllBytes(page.video().path());
    }

    @Attachment(value = "Failed Test Case Screenshot", type = "image/png")
    private byte[] captureScreenshotOnFailure(){
        return page.screenshot();
    }

    protected <T extends BasePage> T createInstance(Class<T> basePage) {
        return BasePageFactory.createInstance(page, basePage);
    }

    @BeforeAll
    public void initBrowser() {
        playwright = Playwright.create();
        browser = BrowserManager.getBrowser(playwright);

        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Platform", System.getProperty("os.name"))
                        .put("Version", System.getProperty("os.version"))
                        .put("Browser", config().browser().toUpperCase())
                        .put("Context URL", config().baseUrl())
                        .build(),
                config().allureResultsDir() + "/");
    }

    @AfterAll
    public void close() {
        browser.close();
        playwright.close();
    }
}