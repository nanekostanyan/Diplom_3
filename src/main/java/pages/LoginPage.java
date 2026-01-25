package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends Base {
    private static final String URI_PAGE_PART = "/login";

    // Заголовок "Вход"
    private final By loginHeaderLocator = By.xpath("//h2[contains(text(),'Вход')]");
    // Поле "Email"
    private final By emailFieldLocator = By.xpath(".//label[text()='Email']/parent::div/input");
    // Поле "Пароль"
    private final By passwordFieldLocator = By.xpath(".//label[text()='Пароль']/parent::div/input");
    // Кнопка "Войти"
    private final By loginButtonLocator = By.xpath(".//button[text()='Войти']");
    // Гиперссылка "Зарегистрироваться"
    private final By registerLinkLocator = By.cssSelector("a[href='/register']");
    // Гиперссылка "Восстановить пароль"
    private final By forgotPasswordLinkLocator = By.cssSelector("a[href='/forgot-password']");


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Авторизуемся по полученным email и password")
    public void login(String email, String password) {
        isOpen();
        emailFieldInput(email);
        passwordFieldInput(password);
        loginButtonClick();
    }

    @Step("Заполняем поле 'Email'")
    public void emailFieldInput(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(emailFieldLocator)).sendKeys(email);
    }

    @Step("Заполняем поле 'Пароль'")
    public void passwordFieldInput(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordFieldLocator)).sendKeys(password);
    }

    @Step("Кликаем по кнопке 'Войти'")
    public void loginButtonClick() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator)).click();
    }

    @Step("Клик на ссылку перехода на страницу Регистрации со страницы Логина")
    public void registrationLinkClick() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLinkLocator)).click();
    }

    @Step("Клик на ссылку перехода на страницу Восстановления пароля со страницы Логина")
    public void forgotPasswordLinkClick() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLinkLocator)).click();
    }

    @Step("Проверяем, что открыта страницы Логина")
    public boolean isOpen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginHeaderLocator));
        return driver.getCurrentUrl().contains(URI_PAGE_PART);
    }
}
