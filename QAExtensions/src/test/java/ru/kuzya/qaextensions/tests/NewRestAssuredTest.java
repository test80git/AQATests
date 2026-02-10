package ru.kuzya.qaextensions.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class NewRestAssuredTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
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
}
