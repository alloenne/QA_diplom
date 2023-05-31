package ru.netology.data;

import com.github.javafaker.Faker;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("ru"));

    private DataHelper() {
    }

    //валидные значения
    public static String getAppruvedCard() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCard() {
        return "4444 4444 4444 4442";
    }

    public static String getValidName() {
        return "IVANOV IVAN";
    }

    public static String getValidMonth() {
        return "12";
    }

    public static String getValidYear() {
        return "24";
    }

    public static String getValidCVC() {
        return "999";
    }

    //невалидные значения
    public static String getRandomCard() {
        return new String(faker.business().creditCardNumber());
    }

    public static String getCard15Numbers() {
        return new String(faker.business().creditCardNumber()).substring(0, 15);
    }

    public static String getCard17Numbers() {
        return new String(faker.business().creditCardNumber()) + "1";
    }

    public static String get13Month() {
        return "13";
    }

    public static String get00Month() {
        return "00";
    }

    public static String getLastMonth() {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getLastYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getCurrentYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getYearAfter6() {
        return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getOneLetter() {
        return "I";
    }

    public static String get4NumbForCVC() {
        return "0000";
    }

    public static String getEmpty() {
        return "";
    }

    public static String getSpace() {
        return " ";
    }

    public static String getLetters() {
        return new String(faker.name().fullName());
    }

    public static String getSymbols() {
        return "/*/#^&";
    }

    public static String get1Numbers() {
        return "0";
    }

    public static String getNumbers() {
        return "333";
    }


}
