package pages;

import io.restassured.RestAssured;
import org.aspectj.lang.annotation.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Base {
    protected WebDriver driver;
    protected final WebDriverWait wait;

    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(4);

    public Base(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    @After
    public void shutDown() {
        if (driver != null) {
            driver.quit();
        }
        RestAssured.reset();
    }
}
