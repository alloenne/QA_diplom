package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;
import ru.netology.page.DebitPage;


import static com.codeborne.selenide.Selenide.open;

public class BuyWithCardTest {
    DebitPage debitPage;

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
        debitPage = mainPage.buyWithCard();
    }

    @Test
    void shouldBuyWithActivatedCard() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.successNotificationForm();
        var status = DBHelper.getTransactionPaymentStatus();
        var transactionId = DBHelper.getTransactionId();
        var paymentId = DBHelper.getPaymentId();
        Assertions.assertEquals("APPROVED", status);
        Assertions.assertEquals(transactionId, paymentId);
    }

    @Test
    void shouldNotBuyWithDeclinedCardNotification() {
        var cardNumber = DataHelper.getDeclinedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.errorNotificationForm();
        var status = DBHelper.getTransactionPaymentStatus();
        var transactionId = DBHelper.getTransactionId();
        var paymentId = DBHelper.getPaymentId();
        Assertions.assertEquals("DECLINED", status);
        Assertions.assertEquals(transactionId, paymentId);
    }

    @Test
    void shouldNotBuyWithDeclinedCardBD() {
        var cardNumber = DataHelper.getDeclinedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.anyNotificationForm();
        var status = DBHelper.getTransactionPaymentStatus();
        var transactionId = DBHelper.getTransactionId();
        var paymentId = DBHelper.getPaymentId();
        Assertions.assertEquals("DECLINED", status);
        Assertions.assertEquals(transactionId, paymentId);
    }

    @Test
    void errorEmptyCard() {
        var cardNumber = DataHelper.getEmpty();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.cardNumberFieldEmpty();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCard1Number() {
        var cardNumber = DataHelper.get1Numbers();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.cardNumberFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCard15Number() {
        var cardNumber = DataHelper.getCard15Numbers();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.cardNumberFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCard17Number() {
        var cardNumber = DataHelper.getCard17Numbers();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.errorNotificationForm();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorRandomCard() {
        var cardNumber = DataHelper.getRandomCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.errorNotificationForm();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCardSpace() {
        var cardNumber = DataHelper.getSpace();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.cardNumberFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCardLetters() {
        var cardNumber = DataHelper.getLetters();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.cardNumberFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCardSymbols() {
        var cardNumber = DataHelper.getSymbols();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.cardNumberFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorMonthEmpty() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getEmpty();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.monthFieldEmpty();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorMonthSpace() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getSpace();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.monthFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorMonthLetters() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getLetters();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.monthFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorMonthSymbols() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getSymbols();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.monthFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorMonth1Numbers() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.get1Numbers();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.monthFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void error13Month() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.get13Month();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.monthFieldIncorrect();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void error00Month() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.get00Month();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.monthFieldIncorrect();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorLastMonth() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getLastMonth();
        var year = DataHelper.getCurrentYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.monthFieldIncorrect();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);

    }

    @Test
    void errorMonth3Numbers() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getNumbers();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.monthFieldIncorrect();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorYearEmpty() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getEmpty();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.yearFieldEmpty();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorYearSpace() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getSpace();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.yearFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorYearLetters() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getLetters();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.yearFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorYearSymbols() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getSymbols();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.yearFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorYear1Numbers() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.get1Numbers();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.yearFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorYearLast() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getLastYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.yearFieldIncorrect();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorYearFuture() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getYearAfter6();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.yearFieldExpired();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorYear3Numbers() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getNumbers();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.yearFieldExpired();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorOwnerEmpty() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getEmpty();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.ownerFieldEmpty();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorOwnerSymbols() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getSymbols();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.ownerFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorOwnerSpace() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getSpace();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.ownerFieldEmpty();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorOwnerNumbers() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getNumbers();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.ownerFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorOwnerNotLatinLetter() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getLetters();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.ownerFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorOwnerOneLatinLetter() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getOneLetter();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.ownerFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCVVEmpty() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getEmpty();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.cvvFieldEmpty();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCVVSpace() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getSpace();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.cvvFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCVVLetters() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getLetters();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.cvvFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCVVSymbols() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getSymbols();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.cvvFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCVV1Number() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.get1Numbers();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.cvvFieldInvalid();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void errorCVV4Number() {
        var cardNumber = DataHelper.getAppruvedCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.get4NumbForCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.errorNotificationForm();
        var transactionId = DBHelper.getTransactionId();
        Assertions.assertNull(transactionId);
    }

    @Test
    void closeNotification() {
        var cardNumber = DataHelper.getRandomCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidName();
        var cvv = DataHelper.getValidCVC();
        debitPage.fillOutFields(cardNumber, month, year, owner, cvv);
        debitPage.errorNotificationForm();
        debitPage.closeNotification();
        debitPage.noNotificationForm();

    }

}
