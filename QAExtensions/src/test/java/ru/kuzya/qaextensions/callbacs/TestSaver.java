package ru.kuzya.qaextensions.callbacs;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class TestSaver implements AfterAllCallback, AfterTestExecutionCallback {

    private static Set<String> failedTests = new HashSet<>();

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        String s = "/src/test/resources/FailedTests.txt";
        String s1 = System.getProperty("user.dir") + s;
        Path out = Paths.get(s1);
        String result = String.join(" ", failedTests);
        Files.writeString(out, result);

    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        String className = context.getRequiredTestClass().getName();
        Method requiredTestMethod = context.getRequiredTestMethod();
        System.out.println("ClassName: " + className);
        System.out.println("requiredTestMethod: " + requiredTestMethod.getName());

        String testToWrite = String.format("--tests %s.%s*", className.trim(), requiredTestMethod.getName());

        context.getExecutionException().ifPresent(x -> failedTests.add(testToWrite));
    }
}
