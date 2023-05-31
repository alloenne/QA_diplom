package ru.netology.page;


import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class MainPage {

    private SelenideElement heading = $("h2").shouldHave(exactText("Путешествие дня"));
    private SelenideElement buyButton = $$("[class='button__content']").first();
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public MainPage() {
        heading.shouldBe(visible);
    }

    public DebitPage buyWithCard() {
        buyButton.click();
        return new DebitPage();
    }

    public CreditPage buyWithCredit() {
        creditButton.click();
        return new CreditPage();
    }


}
