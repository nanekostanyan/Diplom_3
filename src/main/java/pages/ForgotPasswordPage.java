package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ForgotPasswordPage extends Base {
    private static final String URI_PAGE_PART = "/forgot-password";

    // Заголовок "Восстановить"
    private final By restoreHeaderLocator = By.xpath("//h2[contains(text(),'Восстановление пароля')]");
    // Кнопка "Войти"
    private final By linkToLoginPageLocator = By.xpath(".//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Кликаем по гиперссылке 'Войти'")
    public void linkToLoginPageClick() {
        wait.until(ExpectedConditions.elementToBeClickable(linkToLoginPageLocator)).click();
    }

    @Step("Проверяем, что открыта страница Восстановления пароля")
    public boolean isOpen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(restoreHeaderLocator));
        return driver.getCurrentUrl().contains(URI_PAGE_PART);
    }
}
