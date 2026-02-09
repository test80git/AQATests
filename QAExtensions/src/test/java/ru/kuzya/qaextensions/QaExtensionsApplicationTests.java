package ru.kuzya.qaextensions;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kuzya.qaextensions.callbacs.TestSaver;
import ru.kuzya.qaextensions.models.User;
import ru.kuzya.qaextensions.parameters.RandomUser;
import ru.kuzya.qaextensions.parameters.RandomUserResolver;

@SpringBootTest
@ExtendWith({RandomUserResolver.class})
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
        Assertions.assertEquals("1", "2");
    }


    @Test
    void contextLoads2(@RandomUser User testUser) {
        System.out.println(testUser.getName());
        System.out.println(testUser.getSurname());
        System.out.println(testUser.getAge());
        System.out.println(testUser.getJob());
        Assertions.assertEquals("1", "2");
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
