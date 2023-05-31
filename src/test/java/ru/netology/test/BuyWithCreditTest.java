package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;


import static com.codeborne.selenide.Selenide.open;

public class BuyWithCreditTest {
    CreditPage creditPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        DBHelper.clearDB();
        open("http://localhost:8080");
        Configuration.holdBrowserOpen = true;
        MainPage mainPage = new MainPage();
        creditPage = mainPage.buyWithCredit();
    }

    @Test
    void shouldBuyWithActivatedCard() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.successNotificationForm();
        var status = DBHelper.getTransactionCreditRequestStatus();
        var bankId = DBHelper.getBankId();
        var creditId = DBHelper.getCreditId();
        Assertions.assertEquals("APPROVED", status);
        Assertions.assertEquals(bankId, creditId);
    }

    @Test
    void shouldNotBuyWithDeclinedCardNotification() {
        var cardNumber = DataHelper.getDeclinedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.errorNotificationForm();
        var status = DBHelper.getTransactionCreditRequestStatus();
        var bankId = DBHelper.getBankId();
        var creditId = DBHelper.getCreditId();
        Assertions.assertEquals("DECLINED", status);
        Assertions.assertEquals(bankId, creditId);
    }

    @Test
    void shouldNotBuyWithDeclinedCardBD() {
        var cardNumber = DataHelper.getDeclinedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.anyNotificationForm();
        var status = DBHelper.getTransactionCreditRequestStatus();
        var bankId = DBHelper.getBankId();
        var creditId = DBHelper.getCreditId();
        Assertions.assertEquals("DECLINED", status);
        Assertions.assertEquals(bankId, creditId);
    }

    @Test
    void errorEmptyCard() {
        var cardNumber = DataHelper.getEmpty();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.cardNumberFieldEmpty();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCard1Number() {
        var cardNumber = DataHelper.get1Numbers();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.cardNumberFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCard15Number() {
        var cardNumber = DataHelper.getCard15Numbers();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.cardNumberFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCard17Number() {
        var cardNumber = DataHelper.getCard17Numbers();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.errorNotificationForm();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorRandomCard() {
        var cardNumber = DataHelper.getRandomCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.errorNotificationForm();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCardSpace() {
        var cardNumber = DataHelper.getSpace();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.cardNumberFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCardLetters() {
        var cardNumber = DataHelper.getLetters();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.cardNumberFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCardSymbols() {
        var cardNumber = DataHelper.getSymbols();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.cardNumberFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorMonthEmpty() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getEmpty();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.monthFieldEmpty();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorMonthSpace() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getSpace();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.monthFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorMonthLetters() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getLetters();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.monthFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorMonthSymbols() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getSymbols();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.monthFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorMonth1Numbers() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.get1Numbers();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.monthFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void error13Month() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.get13Month();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.monthFieldIncorrect();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void error00Month() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.get00Month();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.monthFieldIncorrect();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorLastMonth() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getLastMonth();
        var year = DataHelper.getCurrentYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.monthFieldIncorrect();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorMonth3Numbers() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getNumbers();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.monthFieldIncorrect();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorYearEmpty() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getEmpty();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.yearFieldEmpty();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorYearSpace() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getSpace();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.yearFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorYearLetters() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getLetters();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.yearFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorYearSymbols() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getSymbols();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.yearFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorYear1Numbers() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.get1Numbers();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.yearFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorYearLast() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getLastYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.yearFieldIncorrect();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorYearFuture() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getYearAfter6();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.yearFieldExpired();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorYear3Numbers() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getNumbers();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.yearFieldExpired();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorOwnerEmpty() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getEmpty();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.ownerFieldEmpty();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorOwnerSymbols() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getSymbols();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.ownerFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorOwnerSpace() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getSpace();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.ownerFieldEmpty();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorOwnerNumbers() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getNumbers();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.ownerFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorOwnerNotLatinLetter() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getLetters();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.ownerFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorOwnerOneLatinLetter() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getOneLetter();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.ownerFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCVVEmpty() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getEmpty();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.cvvFieldEmpty();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCVVSpace() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getSpace();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.cvvFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCVVLetters() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getLetters();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.cvvFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCVVSymbols() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getSymbols();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.cvvFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCVV1Number() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.get1Numbers();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.cvvFieldInvalid();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void errorCVV4Number() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.get4NumbForCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.errorNotificationForm();
        var bankId = DBHelper.getBankId();
        Assertions.assertNull(bankId);
    }

    @Test
    void closeNotification() {
        var cardNumber = DataHelper.getRandomCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvv);
        creditPage.errorNotificationForm();
        creditPage.closeNotification();
        creditPage.noNotificationForm();

    }

}
