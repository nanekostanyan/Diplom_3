package login;

import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.BrowserWebDriver;
import utils.UserManagerAPI;

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
    }

    @After
    @Step("shutDown")
    public void shutDown() {
        driver.quit();
        userManagerAPI.deleteTestUserBySavedToken();
    }

    @Test
    @Step("Вход в профиль через Главную страницу")
    public void LoginFromMainPage() {
        mainPage.enterToAccountButtonClick();

        loginPage.login(userManagerAPI.getLastEmail(), userManagerAPI.getLastPassword());

        Assert.assertTrue(mainPage.isOpen());
        mainPage.linkToAccountClick();

        Assert.assertTrue(profilePage.isOpen());
    }

    @Test
    @Step("Вход в профиль через кнопку в Личном кабинете")
    public void LoginFromLoginPage() {
        mainPage.linkToAccountClick();

        loginPage.login(userManagerAPI.getLastEmail(), userManagerAPI.getLastPassword());

        Assert.assertTrue(mainPage.isOpen());
        mainPage.linkToAccountClick();

        Assert.assertTrue(profilePage.isOpen());
    }

    @Test
    @Step("Вход в профиль через кнопку на форме Регистрации")
    public void LoginFromRegistrationPage() {
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
    @Step("Вход в профиль через форму Восстановления пароля")
    public void LoginFromForgotPasswordPage() {
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
