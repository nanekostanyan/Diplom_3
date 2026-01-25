package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends Base {
    private static final String URI_PAGE_PART = "/profile";

    //Заголовок "Профиль"
    private final By profileHeaderLocator = By.xpath("//a[text()='Профиль']");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверяем, что открыта страница Профиля")
    public boolean isOpen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileHeaderLocator));
        return driver.getCurrentUrl().contains(URI_PAGE_PART);
    }
}
