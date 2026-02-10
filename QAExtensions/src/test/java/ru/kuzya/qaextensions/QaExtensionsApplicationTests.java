package ru.kuzya.qaextensions;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kuzya.qaextensions.callbacs.RestExtension;
import ru.kuzya.qaextensions.callbacs.TestCallback;
import ru.kuzya.qaextensions.callbacs.TestSaver;
import ru.kuzya.qaextensions.models.Booking;
import ru.kuzya.qaextensions.models.User;
import ru.kuzya.qaextensions.parameters.RandomUser;
import ru.kuzya.qaextensions.parameters.RandomUserResolver;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


//@SpringBootTest
@ExtendWith({RandomUserResolver.class, TestSaver.class, TestCallback.class, RestExtension.class})
class QaExtensionsApplicationTests {

    @BeforeEach
    void setUp() {
        System.out.println("     Before each test");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("    Before all tests");
    }

    @Test
    void contextLoads(@RandomUser User testUser) {
        System.out.println(testUser.getName());
        System.out.println(testUser.getSurname());
        System.out.println(testUser.getAge());
        System.out.println(testUser.getJob());

    }

    @Test
    void contextLoads1(@RandomUser User testUser) {
        System.out.println(testUser.getName());
        System.out.println(testUser.getSurname());
        System.out.println(testUser.getAge());
        System.out.println(testUser.getJob());
        assertEquals("1", "2");
    }


    @Test
    void contextLoads2(@RandomUser User testUser) {
        System.out.println(testUser.getName());
        System.out.println(testUser.getSurname());
        System.out.println(testUser.getAge());
        System.out.println(testUser.getJob());
        assertEquals("1", "2");
    }

    @Test
    void restAssuredConfigTest(){
        Booking booking = Booking.Builder().build();
        int actualStatusCode = step("Создание бронирования с валидными параметрами", ()->
                given()
                        .contentType(ContentType.JSON)
                        .body(booking)
                        .post("/booking")
                        .then()
                        .extract().statusCode()
                );
        step("statusCode ответа эквивалентен 200", () ->
                assertEquals(200, actualStatusCode, "createBooking вернул неверный statusCode")
        );
    }

    @Test
    void simpleGetTest() {
        int statusCode = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/booking")
                .then()
                .extract()
                .statusCode();

        System.out.println("Status Code: " + statusCode);
        assertEquals(200, statusCode);
    }

    @AfterEach
    void tearDown() {
        System.out.println("        After each test");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("    After all test");
    }
}
