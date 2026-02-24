package ru.kuzya.restassured.ui.chrome;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

@Tag("UI_Collection")
public class WikiTest extends BaseSelenideTest {

    private final static String URL = "https://ru.wikipedia.org/wiki/Java";

    @Test
    public void openAllHrefs() throws UnsupportedEncodingException {
        //открываем нужную страницу
        Selenide.open(URL);
        //обозначаем необходимые нам элементы в которых содержится атрибут href
        ElementsCollection hrefs = $$x("//div[@id='toc']//a[@href]");
        //создаем список, в который поместим ссылки
        List<String> links = new ArrayList<>();

//        //1 способ заполнения списка значениями через цикл for
//        for (int i = 0; i < hrefs.size(); i++) {
//            links.add(hrefs.get(i).getAttribute("href"));
//        }
        //2 способ заполнения списка через цикл for each
        for (SelenideElement href : hrefs) {
            links.add(href.getAttribute("href"));
        }
//        //3 способ заполнения через stream api
//        hrefs.forEach(x -> links.add(x.getAttribute("href")));

//        //1 способ открытия ссылок через for each
//        for (int i = 0; i < links.size(); i++) {
//            Selenide.open(links.get(i));
//        }
        //2 способ открытия всех ссылок через for
        int i = 0;
        for (String link : links) {
            String decodedUrl = URLDecoder.decode(links.get(i++), StandardCharsets.UTF_8);
            System.out.println("=== Открываю ссылку " + (i + 1) + " ===" + decodedUrl);

            Selenide.open(link);
            // ждем загрузки страницы
            $("body").shouldBe(visible, Duration.ofSeconds(10));
            // ПАУЗА 3 СЕКУНДЫ - чтобы вы увидели страницу
            Selenide.sleep(500);
        }
//        //3 способ открытия всех ссылок через stream api
//        links.forEach(Selenide::open);

//        //как получить случайную ссылку
//        //1 способ
//        Selenide.open(links.get((int) (Math.random() * links.size() + 1)));

//        //2 способ
//        //создаем рандом
//        Random random = new Random();
//        for (int i = 0; i < links.size(); i++) {
//            //получаем случайное число на основе размера списка
//            int index = random.nextInt(links.size());
//            //открываем случайную ссылку по индексу из списка
//            Selenide.open(links.get(index));
//        }
//        //3 способ если нужно убирать ссылку после ее доставания из списка
//        while (!links.isEmpty()) {
//            int index = random.nextInt(links.size());
//            Selenide.open(links.get(index));
//            links.remove(WebDriverRunner.getWebDriver().getCurrentUrl());
//        }

//        //пример работы стримапи
//        List<Integer> hrefSizeEach = hrefs.stream()
//                .map(x -> x.getAttribute("href").length())
//                .toList();
    }

}
