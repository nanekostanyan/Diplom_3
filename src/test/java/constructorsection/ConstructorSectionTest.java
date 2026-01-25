package constructorsection;

import io.qameta.allure.Step;
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
    @Step("Проверяем переход на раздел `Булки`")
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
    @Step("Проверяем переход на раздел `Соусы`")
    public void switchToSaucesTab() {
        mainPage.clickOnBunsTab(); // Сначала активируем не тестируемый раздел, чтобы гарантировать, что начали тест не с тестируемого

        mainPage.clickOnSaucesTab();

        mainPage.checkToppingSauce();
        assertTrue("Таб соусов не активен", mainPage.isSaucesSectionActive());
        assertTrue("Раздел соусов не отображается", mainPage.isSaucesSectionTitleDisplayed());
        assertTrue("Нет соусов", mainPage.getSaucesCount() > 0);
    }

    @Test
    @Step("Проверяем переход на раздел `Начинки`")
    public void switchToFillingTab() {
        mainPage.clickOnBunsTab(); // Сначала активируем не тестируемый раздел, чтобы гарантировать, что начали тест не с тестируемого

        mainPage.clickOnFillingsTab();

        mainPage.checkToppingFilling();
        assertTrue("Таб начинок не активен", mainPage.isFillingsSectionActive());
        assertTrue("Раздел начинок не отображается", mainPage.isFillingsSectionTitleDisplayed());
        assertTrue("Нет начинок", mainPage.getFillingsCount() > 0);
    }
}
