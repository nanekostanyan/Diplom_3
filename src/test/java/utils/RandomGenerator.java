package utils;

import io.qameta.allure.Step;

import java.util.Random;

public class RandomGenerator {
    private static final String EMAIL_DOMAIN = "@yandex.ru";
    private static final int EMAIL_MIN_SIZE = 5;
    private static final int ADDITIONAL_RANGE_OF_EMAIL = 10;

    private static final int STRING_MIN_SIZE = 7;
    private static final int ADDITIONAL_RANGE_OF_STRING = 7;

    @Step("Генерируем случайную строку")
    public static String generateString() {
        Random rand = new Random();
        int size = rand.nextInt(ADDITIONAL_RANGE_OF_STRING)+STRING_MIN_SIZE;

        return generateString(size);
    }

    @Step("Генерируем случайную строку заданной длины")
    public static String generateString(int length) {
        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterSet.length());
            sb.append(characterSet.charAt(index));
        }
        return sb.toString();
    }

    @Step("Генерируем случайную строку с заданным диапазоном")
    public static String generateString(int minLen, int maxLen) {
        Random rand = new Random();
        if (minLen > maxLen) {
            int buf = minLen;
            minLen = maxLen;
            maxLen = buf;
        } else if (minLen != maxLen) {
            maxLen++;
        }
        int length = rand.nextInt(maxLen-minLen) + minLen;
        return generateString(length);
    }

    @Step("Генерируем случайный email")
    public static String generateEmail() {
        Random rand = new Random();
        int emailNameSize = rand.nextInt(ADDITIONAL_RANGE_OF_EMAIL)+EMAIL_MIN_SIZE;
        return generateString(emailNameSize) + EMAIL_DOMAIN;
    }
}
