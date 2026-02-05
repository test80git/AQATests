package ru.kuzya.e2eplaywright.pages;

import com.microsoft.playwright.Page;

import static ru.kuzya.e2eplaywright.config.ConfigurationManager.config;

public abstract class BasePage {

    protected Page page;

    public void setAndConfigurePage(final Page page) {
        this.page = page;
        page.setDefaultTimeout(config().timeout()); // Задаем таймаут для взаимодействия со страницей
    }

    public void initComponents() {}

}
