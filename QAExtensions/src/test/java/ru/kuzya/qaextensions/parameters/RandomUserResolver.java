package ru.kuzya.qaextensions.parameters;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.kuzya.qaextensions.models.User;

import java.util.Random;

public class RandomUserResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext
            , ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(RandomUser.class);
    }

    @Override
    public @Nullable Object resolveParameter(ParameterContext parameterContext
            , ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        if (User.class.equals(type)) {
            Random random = new Random();
            User user = new User();
            user.setName("TestUserName " + random.nextInt(100));
            user.setSurname("TestUserSurname " + random.nextInt(100));
            user.setAge(random.nextInt(100));
            user.setJob("TestJob " + random.nextInt(100));
            return user;
        }
        throw new ParameterResolutionException("No random generator implemented for " + type);
    }
}
