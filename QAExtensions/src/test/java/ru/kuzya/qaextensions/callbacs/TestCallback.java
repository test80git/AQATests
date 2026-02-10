package ru.kuzya.qaextensions.callbacs;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class TestCallback implements BeforeAllCallback, BeforeTestExecutionCallback {
    private static Set<String> nameTests = new HashSet<>();
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.out.println("Before All from Callback");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        String className = context.getRequiredTestClass().getName();
        Method requiredTestMethod = context.getRequiredTestMethod();
        System.out.println("ClassName: " + className);
        System.out.println("requiredTestMethod: " + requiredTestMethod.getName());

        String testToWrite = String.format("%s.%s*", className.trim(), requiredTestMethod.getName());

        nameTests.add(testToWrite);

        String displayName = context.getDisplayName();
        System.out.println("Display name: " + displayName);
        String s = "/src/test/resources/NameTests.txt";
        String s1 = System.getProperty("user.dir") + s;
        Path out = Paths.get(s1);
        String result = String.join("\n", nameTests);
        Files.writeString(out, result);
    }
}
