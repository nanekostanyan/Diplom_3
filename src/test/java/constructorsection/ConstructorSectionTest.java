package constructorsection;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.BrowserWebDriver;

import static org.junit.Assert.assertTrue;

public class ConstructorSectionTest {
    private WebDriver driver = BrowserWebDriver.createDriverWMainPage();
    private MainPage mainPage = new MainPage(driver);

    @After
    @Step("shutDown")
    public void shutDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверяем переход на раздел `Булки`")
    @Description("На Главной странице скроллим до табов ингредиентов. " +
            "Выбираем таб с ингредиентами, отличный от `Булки`, чтобы сбросить стартовое состояние." +
            "Выбираем таб `Булки`. Проверяем, что действительно переключились на нужный таб. " +
            "Проверяем, что секция `Булок` активна и в ней есть хотя бы несколько активных ингредиентов.")
    public void switchToBunsTab() {
        mainPage.clickOnBunsTab();
        mainPage.clickOnSaucesTab(); // "Булки" выбраны по умолчанию, поэтому сначала поменяем их на что-то другое
        mainPage.clickOnBunsTab();

        mainPage.checkToppingBun();
        assertTrue("Таб булочек не активен", mainPage.isBunsSectionActive());
        assertTrue("Раздел булочек не отображается", mainPage.isBunsSectionTitleDisplayed());
        assertTrue("Нету булочек", mainPage.getBunsCount() > 0);
    }

    @Test
    @DisplayName("Проверяем переход на раздел `Соусы`")
    @Description("На Главной странице скроллим до табов ингредиентов. " +
            "Выбираем таб с ингредиентами, отличный от `Соусы`, чтобы сбросить стартовое состояние." +
            "Выбираем таб `Соусы`. Проверяем, что действительно переключились на нужный таб. " +
            "Проверяем, что секция `Булок` активна и в ней есть хотя бы несколько активных ингредиентов.")
    public void switchToSaucesTab() {
        mainPage.clickOnBunsTab(); // Сначала активируем не тестируемый раздел, чтобы гарантировать, что начали тест не с тестируемого

        mainPage.clickOnSaucesTab();

        mainPage.checkToppingSauce();
        assertTrue("Таб соусов не активен", mainPage.isSaucesSectionActive());
        assertTrue("Раздел соусов не отображается", mainPage.isSaucesSectionTitleDisplayed());
        assertTrue("Нет соусов", mainPage.getSaucesCount() > 0);
    }

    @Test
    @DisplayName("Проверяем переход на раздел `Начинки`")
    @Description("На Главной странице скроллим до табов ингредиентов. " +
            "Выбираем таб с ингредиентами, отличный от `Начинки`, чтобы сбросить стартовое состояние." +
            "Выбираем таб `Начинки`. Проверяем, что действительно переключились на нужный таб. " +
            "Проверяем, что секция `Булок` активна и в ней есть хотя бы несколько активных ингредиентов.")

    public void switchToFillingTab() {
        mainPage.clickOnBunsTab(); // Сначала активируем не тестируемый раздел, чтобы гарантировать, что начали тест не с тестируемого

        mainPage.clickOnFillingsTab();

        mainPage.checkToppingFilling();
        assertTrue("Таб начинок не активен", mainPage.isFillingsSectionActive());
        assertTrue("Раздел начинок не отображается", mainPage.isFillingsSectionTitleDisplayed());
        assertTrue("Нет начинок", mainPage.getFillingsCount() > 0);
    }
}
