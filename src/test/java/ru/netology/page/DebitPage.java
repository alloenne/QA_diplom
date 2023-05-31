package ru.netology.page;


import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DebitPage {


    private SelenideElement heading = $$("h3").last().shouldHave(exactText("Оплата по карте"));
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement buttonContinue = $(byText("Продолжить"));
    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement failNotification = $(".notification_status_error");
    private SelenideElement anyNotification = $(".notification");
    private SelenideElement verificationErrorNumber = $$(".input__inner").findBy(text("Номер карты")).$(".input__sub");
    private SelenideElement verificationErrorMonth = $$(".input__inner").findBy(text("Месяц")).$(".input__sub");
    private SelenideElement verificationErrorYear = $$(".input__inner").findBy(text("Год")).$(".input__sub");
    private SelenideElement verificationErrorOwner = $$(".input__inner").findBy(text("Владелец")).$(".input__sub");
    private SelenideElement verificationErrorCVV = $$(".input__inner").findBy(text("CVC/CVV")).$(".input__sub");

    public DebitPage() {
        heading.shouldBe(visible);
    }

    public void fillOutFields(String cardNumber, String month, String year, String owner, String cvc) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        cvcField.setValue(cvc);
        buttonContinue.click();
    }

    public void successNotificationForm() {
        successNotification.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Операция одобрена Банком."));
    }

    public void errorNotificationForm() {
        failNotification.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }

    public void anyNotificationForm() {
        anyNotification.shouldBe(visible, Duration.ofSeconds(15));
    }


    public void cardNumberFieldEmpty() {
        verificationErrorNumber.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    public void cardNumberFieldInvalid() {
        verificationErrorNumber.shouldBe(visible).shouldHave(text("Неверный формат"));
    }

    public void monthFieldInvalid() {
        verificationErrorMonth.shouldBe(visible).shouldHave(text("Неверный формат"));
    }

    public void monthFieldEmpty() {
        verificationErrorMonth.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    public void monthFieldIncorrect() {
        verificationErrorMonth.shouldBe(visible).shouldHave(text("Неверно указан срок действия карты"));
    }

    public void yearFieldEmpty() {
        verificationErrorYear.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    public void yearFieldExpired() {
        verificationErrorYear.shouldBe(visible).shouldHave(text("Неверно указан срок действия карты"));
    }

    public void yearFieldIncorrect() {
        verificationErrorYear.shouldBe(visible).shouldHave(text("Истёк срок действия карты"));
    }

    public void yearFieldInvalid() {
        verificationErrorYear.shouldBe(visible).shouldHave(text("Неверный формат"));
    }

    public void ownerFieldEmpty() {
        verificationErrorOwner.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    public void ownerFieldInvalid() {
        verificationErrorOwner.shouldBe(visible).shouldHave(text("Неверный формат"));
    }

    public void cvvFieldInvalid() {
        verificationErrorCVV.shouldBe(visible).shouldHave(text("Неверный формат"));
    }

    public void cvvFieldEmpty() {
        verificationErrorCVV.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }


}
