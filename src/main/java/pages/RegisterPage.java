package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends Base {
    private static final String URI_PAGE_PART = "/register";

    private boolean isPasswordErrorEnabled;
    private String passwordErrorMessage;

    //Заголовок "Регистрация"
    private final By registerHeaderLocator = By.xpath("//h2[contains(text(),'Регистрация')]");
    // Поле "Имя"
    private final By nameFieldLocator = By.xpath(".//label[text()='Имя']/parent::div/input");
    // Поле "Email"
    private final By emailFieldLocator = By.xpath(".//label[text()='Email']/parent::div/input");
    // Поле "Пароль"
    private final By passwordFieldLocator = By.xpath(".//label[text()='Пароль']/parent::div/input");
    // Кнопка "Зарегистрироваться"
    private final By registerButtonLocator = By.xpath(".//button[text()='Зарегистрироваться']");
    // Сообщение об ошибке для полем "Пароль"
    private final By passwordErrorLocator = By.xpath("//div[@class='input__container' and .//input[@name='Пароль']]//p[contains(@class, 'input__error')]");
    // Гиперссылка "Войти"
    private final By linkToLoginPageLocator = By.cssSelector("a[href='/login']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполняем поле 'Имя'")
    public void nameFieldInput(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(nameFieldLocator)).sendKeys(name);
    }

    @Step("Заполняем поле 'Email'")
    public void emailFieldInput(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(emailFieldLocator)).sendKeys(email);
    }

    @Step("Заполняем поле 'Пароль'")
    public void passwordFieldInput(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordFieldLocator)).sendKeys(password);
    }

    @Step("Кликаем по кнопке 'Зарегистрироваться'")
    public void registerButtonClick() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButtonLocator)).click();
    }

    @Step("Кликаем по гиперссылке 'Войти'")
    public void linkToLoginPageClick() {
        wait.until(ExpectedConditions.elementToBeClickable(linkToLoginPageLocator)).click();
    }

    @Step("Проверяем, что открыта страница Регистрации")
    public boolean isOpen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerHeaderLocator));
        return driver.getCurrentUrl().contains(URI_PAGE_PART);
    }

    @Step("Проверяем наличие ошибки под полем 'Пароль'")
    public boolean passwordErrorVisibilityCheck() {
        isPasswordErrorEnabled = wait.until(ExpectedConditions.elementToBeClickable(passwordErrorLocator)).isEnabled();
        return isPasswordErrorEnabled;
    }

    @Step("Получаем текст ошибки под полем 'Пароль'")
    public String passwordErrorGetMessageText() {
        passwordErrorMessage = wait.until(ExpectedConditions.elementToBeClickable(passwordErrorLocator)).getText();
        return passwordErrorMessage;
    }
}
