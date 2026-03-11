package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserWebDriver {
    private final static String START_PAGE_URL = "https://stellarburgers.education-services.ru";

    private static WebDriver driver;

    @Step("Запускаем главную страницу Stellar Burger")
    public static WebDriver createDriverWMainPage() {
        driver = createDriver();
        driver.manage().window().maximize();
        driver.get(START_PAGE_URL);
        return driver;
    }

    @Step("Создаём WebDriver")
    private static WebDriver createDriver() {
        String browser = System.getProperty("browser", "chrome");

        switch (browser.toLowerCase()) {
            case "yandex":
                return yandex();
            case "chrome":
            default:
                return chrome();
        }
    }

    @Step("Запуск Google Chrome")
    private static WebDriver chrome() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
        return new ChromeDriver(chromeOptions);
    }

    @Step("Запуск Yandex Browser")
    private static WebDriver yandex() {
        WebDriverManager.chromedriver()
                .driverVersion("142.0.7241.94")
                .setup();

        ChromeOptions yandexOptions = new ChromeOptions();

        String localAppData = System.getenv("LOCALAPPDATA");
        if (localAppData == null) {
            localAppData = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local";
        }

        String yandexPath = localAppData + "\\Yandex\\YandexBrowser\\Application\\browser.exe";

        yandexOptions.setBinary(yandexPath);
        yandexOptions.addArguments("--incognito");
        yandexOptions.addArguments("--remote-allow-origins=*");

        try {
            return new ChromeDriver(yandexOptions);
        } catch (SessionNotCreatedException e) {
            yandexOptions = new ChromeOptions();
            yandexOptions.addArguments("--incognito");
            yandexOptions.addArguments("--remote-allow-origins=*");
            return new ChromeDriver(yandexOptions);
        }
    }
}
