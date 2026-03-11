package utils;

import io.qameta.allure.Step;
import net.datafaker.Faker;

import java.util.Locale;

public class RandomGenerator {
    private static final Faker faker = new Faker(Locale.ENGLISH);

    private static final String EMAIL_DOMAIN = "@yandex.ru";
    private static final int EMAIL_MIN_SIZE = 5;
    private static final int EMAIL_MAX_SIZE = 15;

    private static final int STRING_MIN_SIZE = 7;
    private static final int STRING_MAX_SIZE = 15;

    @Step("Генерируем случайную строку")
    public static String generateString() {
        int size = faker.random().nextInt(STRING_MIN_SIZE, STRING_MAX_SIZE);
        return generateString(size);
    }

    @Step("Генерируем случайную строку заданной длины")
    public static String generateString(int length) {
        return faker.regexify(String.format("[a-zA-Z0-9]{%d}", length));
    }

    @Step("Генерируем случайную строку с заданным диапазоном")
    public static String generateString(int minLen, int maxLen) {
        if (minLen > maxLen) {
            int temp = minLen;
            minLen = maxLen;
            maxLen = temp;
        }

        int length = faker.random().nextInt(minLen, maxLen);
        return generateString(length);
    }

    @Step("Генерируем случайный email")
    public static String generateEmail() {
        int emailNameSize = faker.random().nextInt(EMAIL_MIN_SIZE, EMAIL_MAX_SIZE);
        return generateString(emailNameSize) + EMAIL_DOMAIN;
    }
}
