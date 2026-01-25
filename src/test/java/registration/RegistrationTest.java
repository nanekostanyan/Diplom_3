package registration;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilePage;
import pages.RegisterPage;
import utils.BrowserWebDriver;
import utils.RandomGenerator;
import utils.UserManagerAPI;

import static org.apache.http.HttpStatus.SC_OK;

public class RegistrationTest {
    private WebDriver driver = BrowserWebDriver.createDriverWMainPage();
    private UserManagerAPI userManagerAPI = new UserManagerAPI();
    private MainPage mainPage = new MainPage(driver);
    private LoginPage loginPage = new LoginPage(driver);
    private ProfilePage profilePage = new ProfilePage(driver);
    private RegisterPage registerPage = new RegisterPage(driver);

    private String userName;
    private String userEmail;
    private String userPassword;

    @Before
    @Step("setUp")
    public void setUp() {
        this.userName = RandomGenerator.generateString();
        this.userEmail = RandomGenerator.generateEmail();
        this.userPassword = RandomGenerator.generateString();
    }

    @After
    @Step("shutDown")
    public void shutDown() {
        driver.quit();
        userManagerAPI.deleteTestUserBySavedTokenIfCan(this.userEmail, this.userPassword);
    }

    @Test
    @Step("Тест: Успешная регистрации пользователя")
    public void SuccessfulRegistration() {
        mainPage.linkToAccountClick();

        Assert.assertTrue(loginPage.isOpen());
        loginPage.registrationLinkClick();

        Assert.assertTrue(registerPage.isOpen());
        registerPage.nameFieldInput(this.userName);
        registerPage.emailFieldInput(this.userEmail);
        registerPage.passwordFieldInput(this.userPassword);
        registerPage.registerButtonClick();

        Assert.assertTrue(loginPage.isOpen());

        // Проверяем что можем авторизоваться под созданным пользователем через запрос в API
        userManagerAPI.loginUser(this.userEmail, this.userPassword);
        userManagerAPI.checkResponseSC(SC_OK);
    }

    @Test
    @Step("Тест: Неудачная попытка регистрации пользователя с коротким (<6 символов) паролем")
    public void RegistrationWUncorrectedPassword() {
        this.userPassword = RandomGenerator.generateString(1, 5); // Пароль невалиден, если его размер меньше 6

        mainPage.linkToAccountClick();

        Assert.assertTrue(loginPage.isOpen());
        loginPage.registrationLinkClick();

        Assert.assertTrue(registerPage.isOpen());
        registerPage.nameFieldInput(this.userName);
        registerPage.emailFieldInput(this.userEmail);
        registerPage.passwordFieldInput(this.userPassword);
        registerPage.registerButtonClick();

        registerPage.passwordErrorVisibilityCheck();
        registerPage.passwordErrorGetMessageText();

        // Если тест упадёт, на всякий случай попробуем удалить пользователя через его учётные данные, если он вдруг был создан
    }
}
