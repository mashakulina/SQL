package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.dataHelper.DataHelper;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.*;

public class EntranceTest {
    @BeforeAll
    static void setUP() {
        DataHelper.cleanData();
        DataHelper.setDemoData();
        DataHelper.setUser();
    }

    @AfterAll
    static void cleanDB() {
        DataHelper.cleanData();
    }

    @BeforeEach
    void openWebService() {
        open("http://localhost:9999/");
    }

    @AfterEach
    void closeWebService() {
        closeWebDriver();
    }

    @Test
    @DisplayName("Успешный вход")
    void shouldLoginSuccess() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Не верно введен SMS-код")
    void shouldThrowAnInvalidCodeError() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var invalidVerificationCode = DataHelper.getInvalidVerificationCode();
        verificationPage.invalidVerify(invalidVerificationCode);
    }

    @Test
    @DisplayName("Ошибка входа")
    void shouldErrorEntrance() {
        var loginPage = new LoginPage();
        var invalidAuthInfo = DataHelper.getInvalidAuthInfo();
        loginPage.invalidLogin(invalidAuthInfo);
    }

    @Test
    @DisplayName("Блокировка входа после 3 неверных попыток")
    void shouldBlockEntrance() {
        var loginPage = new LoginPage();
        var invalidAuthInfo = DataHelper.getInvalidAuthInfo();
        loginPage.invalidLogin(invalidAuthInfo);
        loginPage.invalidLoginRepeat(invalidAuthInfo);
        loginPage.invalidLoginRepeat(invalidAuthInfo);
        loginPage.blockEntrance();
    }
}
