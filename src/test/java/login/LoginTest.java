package login;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.BrowserWebDriver;
import utils.UserManagerAPI;

import static org.apache.http.HttpStatus.SC_OK;

public class LoginTest {
    private WebDriver driver = BrowserWebDriver.createDriverWMainPage();
    private UserManagerAPI userManagerAPI = new UserManagerAPI();
    private MainPage mainPage = new MainPage(driver);
    private LoginPage loginPage = new LoginPage(driver);
    private RegisterPage registerPage = new RegisterPage(driver);
    private ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
    private ProfilePage profilePage = new ProfilePage(driver);

    @Before
    @Step("setUp")
    public void setUp() {
        userManagerAPI.createTestUser();
        userManagerAPI.checkResponseSC(SC_OK);
    }

    @After
    @Step("shutDown")
    public void shutDown() {
        driver.quit();
        userManagerAPI.deleteTestUserBySavedToken();
    }

    @Test
    @DisplayName("Вход в профиль через кнопку `Войти в аккаунт`")
    @Description("Через Главную страницу, переходим на страницу Авторизации через кнопку `Войти в аккаунт`. " +
            "Вводим валидные данные ранее созданного тестового пользователя и нажимаем на кнопку `Войти`. " +
            "Проверяем, что попали на страницу Профиля авторизованного пользователя.")
    public void loginFromMainPage() {
        mainPage.enterToAccountButtonClick();

        loginPage.login(userManagerAPI.getLastEmail(), userManagerAPI.getLastPassword());

        Assert.assertTrue(mainPage.isOpen());
        mainPage.linkToAccountClick();

        Assert.assertTrue(profilePage.isOpen());
    }

    @Test
    @Step("Вход в профиль через кнопку в Личном кабинете")
    @DisplayName("Вход в профиль через Главную страницу")
    @Description("Через Главную страницу, переходим на страницу Авторизации через кнопку `Личный кабинет` в заголовке страницы. " +
            "Вводим валидные данные ранее созданного тестового пользователя и нажимаем на кнопку `Войти`. " +
            "Проверяем, что попали на страницу Профиля авторизованного пользователя.")
    public void loginFromLoginPage() {
        mainPage.linkToAccountClick();

        loginPage.login(userManagerAPI.getLastEmail(), userManagerAPI.getLastPassword());

        Assert.assertTrue(mainPage.isOpen());
        mainPage.linkToAccountClick();

        Assert.assertTrue(profilePage.isOpen());
    }

    @Test
    @DisplayName("Вход в профиль через форму Регистрации")
    @Description("Через Главную страницу, переходим на страницу Авторизации, а оттуда на страницу Регистрации. " +
            "Со страницы Регистрации, переходим на страницу Авторизации." +
            "Вводим валидные данные ранее созданного тестового пользователя и нажимаем на кнопку `Войти`. " +
            "Проверяем, что попали на страницу Профиля авторизованного пользователя.")
    public void loginFromRegistrationPage() {
        mainPage.linkToAccountClick();

        Assert.assertTrue(loginPage.isOpen());
        loginPage.registrationLinkClick();

        Assert.assertTrue(registerPage.isOpen());
        registerPage.linkToLoginPageClick();

        loginPage.login(userManagerAPI.getLastEmail(), userManagerAPI.getLastPassword());

        Assert.assertTrue(mainPage.isOpen());
        mainPage.linkToAccountClick();

        Assert.assertTrue(profilePage.isOpen());
    }

    @Test
    @DisplayName("Вход в профиль через форму Восстановления пароля")
    @Description("Через Главную страницу, переходим на страницу Авторизации, а оттуда на страницу Восстановления пароля. " +
            "Со страницы Восстановления пароля, переходим на страницу Авторизации." +
            "Вводим валидные данные ранее созданного тестового пользователя и нажимаем на кнопку `Войти`. " +
            "Проверяем, что попали на страницу Профиля авторизованного пользователя.")
    public void loginFromForgotPasswordPage() {
        mainPage.linkToAccountClick();

        Assert.assertTrue(loginPage.isOpen());
        loginPage.forgotPasswordLinkClick();

        Assert.assertTrue(forgotPasswordPage.isOpen());
        forgotPasswordPage.linkToLoginPageClick();

        loginPage.login(userManagerAPI.getLastEmail(), userManagerAPI.getLastPassword());

        Assert.assertTrue(mainPage.isOpen());
        mainPage.linkToAccountClick();

        Assert.assertTrue(profilePage.isOpen());
    }
}
