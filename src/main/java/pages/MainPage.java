package pages;

import dev.failsafe.internal.util.Assert;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MainPage extends Base {
    // Заголовок "Соберите Бургер"
    private final By mainHeaderLocator = By.xpath("//h1[contains(text(),'Соберите бургер')]");
    // Гиперссылка на "Личный кабинет"
    private final By linkToAccountPageLocator = By.cssSelector("a[href='/account']");
    // Кнопка "Войти в аккаунт"
    private final By enterToAccountButtonLocator = By.xpath(".//button[text()='Войти в аккаунт']");

    private final By activeTab = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]");
    private final By tabHeader = (By.xpath("//h2[@class='text text_type_main-medium mb-6 mt-10']"));
    private final By tabContainer = (By.xpath("//div[contains(@style, 'display: flex')]"));
    private final String sectionTitleLocatorFormatString = "//h2[text()='%s']";

    private final By ingredientsList = By.className("BurgerIngredients_ingredients__list__2A-mT");
    private final By ingredientItem = By.className("BurgerIngredient_ingredient__1TVf6");
    private final String ingredientTabsLocatorFormatString = "//div[contains(@class, 'tab_tab__1SPyG')]//span[text()='%s']/..";

    // Скрипты для скролла
    private final String scrollToTabContainerScript = "arguments[0].scrollIntoView(true);";
    private final String scrollToSectionTabScript = "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});";

    private final static String BUN_TAG_NAME = "Булки";
    private final static String SAUCES_TAG_NAME = "Соусы";
    private final static String FILLINGS_TAG_NAME = "Начинки";

    public MainPage(WebDriver driver) {
        super(driver);
    }

    // Клики

    @Step("Переход на страницу личного кабинета")
    public void linkToAccountClick() {
        wait.until(ExpectedConditions.elementToBeClickable(linkToAccountPageLocator)).click();
    }

    @Step("Кликаем по кнопке `Войти в аккаунт`")
    public void enterToAccountButtonClick() {
        wait.until(ExpectedConditions.elementToBeClickable(enterToAccountButtonLocator)).click();
    }

    @Step("Переходим на таб 'Булочки'")
    public void clickOnBunsTab() {
        clickTab(BUN_TAG_NAME);
    }

    @Step("Переходим на таб 'Соусы'")
    public void clickOnSaucesTab() {
        clickTab(SAUCES_TAG_NAME);
    }

    @Step("Переходим на таб 'Начинки'")
    public void clickOnFillingsTab() {
        clickTab(FILLINGS_TAG_NAME);
    }

    @Step("Кликаем по табу секции")
    private void clickTab(String tabName) {
        scrollToTabs();

        By tabsLocator = By.xpath(String.format(ingredientTabsLocatorFormatString, tabName));

        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(tabsLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);

        wait.until(ExpectedConditions.textToBePresentInElementLocated(activeTab, tabName));
    }

    // Скролл

    @Step("Находим контейнер с вкладками и скроллим к нему")
    private void scrollToTabs() {
        WebElement tabsContainer = driver.findElement(tabContainer);
        wait.until(ExpectedConditions.presenceOfElementLocated(tabContainer));
        ((JavascriptExecutor) driver).executeScript(scrollToTabContainerScript, tabsContainer);
    }

    @Step("Скроллим к заголовку секции")
    private void scrollToSection(String sectionType) {
        By sectionTitleLocator = By.xpath(String.format(sectionTitleLocatorFormatString, sectionType));
        WebElement sectionTitle = wait.until(ExpectedConditions.presenceOfElementLocated(sectionTitleLocator));
        ((JavascriptExecutor) driver).executeScript(scrollToSectionTabScript, sectionTitle);
    }

    // Проверки

    @Step("Проверяем активность таб `Булки`")
    public void checkToppingBun() {
        checkTopping(BUN_TAG_NAME);
    }

    @Step("Проверяем активность таб `Соусы`")
    public void checkToppingSauce() {
        checkTopping(SAUCES_TAG_NAME);
    }

    @Step("Проверяем активность таб `Начинки`")
    public void checkToppingFilling() {
        checkTopping(FILLINGS_TAG_NAME);
    }

    @Step("Проверяем активность таба, заголовок секции и наличие элементов")
    private void checkTopping(String topping) {
        // Проверяем активность таба
        waitForTabActivation(topping);
        // Проверяем заголовок секции
        checkSectionTitle(topping);
        // Проверяем наличие нужных ингредиентов
        checkIngredientsPresence(topping, 2);
        // После скролла возвращаемся к табам, чтобы восстанавливаем активный таб
        scrollToTabs();

        // Убеждаемся, что нужный таб всё ещё активен после скролла
        if (!isSectionActive(topping)) {
            switch (topping) {
                case BUN_TAG_NAME:
                    clickOnBunsTab();
                    break;
                case SAUCES_TAG_NAME:
                    clickOnSaucesTab();
                    break;
                case FILLINGS_TAG_NAME:
                    clickOnFillingsTab();
            }
        }
    }

    @Step("Ждём активации нужного таба")
    private void waitForTabActivation(String expectedTabName) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(activeTab, expectedTabName));

        String actualText = driver.findElement(activeTab).getText();
        Assert.isTrue(actualText.equals(expectedTabName), String.format("Ожидалось что активен будет таб `%s`, но активен `%s`", actualText, expectedTabName));
    }

    @Step("Ищем среди всех заголовков тот, который отображается")
    private void checkSectionTitle(String expectedTitle) {
        List<WebElement> allTitles = driver.findElements(tabHeader);
        for (WebElement title : allTitles) {
            if (title.isDisplayed() && title.getText().equals(expectedTitle)) {
                return;
            }
        }
        Assert.isTrue(false, "Не нашли заголовок секции");
    }

    @Step("Проверяем наличие ингредиентов")
    private void checkIngredientsPresence(String sectionType, int minIngredientsCount) {
        scrollToSection(sectionType);

        List<WebElement> ingredients = wait.until(ExpectedConditions.visibilityOfElementLocated(ingredientsList)).findElements(ingredientItem);

        Assert.isTrue(ingredients.size() >= minIngredientsCount,
                String.format("В секции `%s` ожидали найти хотя бы `%d` ингридиентов, но нашли всего `%d`",
                    sectionType, ingredients.size(), minIngredientsCount));
    }

    @Step("Получаем количество ингредиентов в секции")
    private int getIngredientsCountBySection(String sectionType) {
        scrollToSection(sectionType);
        List<WebElement> ingredients = wait.until(ExpectedConditions.visibilityOfElementLocated(ingredientsList)).findElements(ingredientItem);
        return ingredients.size();
    }

    @Step("Проверяем, что секция `Булки` активен")
    public boolean isBunsSectionActive() {
        return isSectionActive(BUN_TAG_NAME );
    }

    @Step("Проверяем, что секция `Соусы` активен")
    public boolean isSaucesSectionActive() {
        return isSectionActive(SAUCES_TAG_NAME);
    }

    @Step("Проверяем, что секция `Начинки` активен")
    public boolean isFillingsSectionActive() {
        return isSectionActive(FILLINGS_TAG_NAME);
    }

    @Step("Проверяем, что заголовок секции `Булки` отображается")
    public boolean isBunsSectionTitleDisplayed() {
        return isSectionTitleDisplayed(BUN_TAG_NAME);
    }

    @Step("Проверяем, что заголовок секции `Соусы` отображается")
    public boolean isSaucesSectionTitleDisplayed() {
        return isSectionTitleDisplayed(SAUCES_TAG_NAME);
    }
    @Step("Проверяем, что заголовок секции `Начинки` отображается")
    public boolean isFillingsSectionTitleDisplayed() {
        return isSectionTitleDisplayed(FILLINGS_TAG_NAME);
    }

    @Step("Получаем количество булок")
    public int getBunsCount() {
        return getIngredientsCountBySection(BUN_TAG_NAME);
    }

    @Step("Получаем количество соусов")
    public int getSaucesCount() {
        return getIngredientsCountBySection(SAUCES_TAG_NAME);
    }

    @Step("Получаем количество начинок")
    public int getFillingsCount() {
        return getIngredientsCountBySection(FILLINGS_TAG_NAME);
    }

    @Step("Проверяем, что секция активна")
    private boolean isSectionActive(String sectionName) {
        scrollToTabs();
        String currentActiveTab = driver.findElement(activeTab).getText();
        return currentActiveTab.equals(sectionName);
    }

    @Step("Проверяем, что заголовок секция отображается")
    private boolean isSectionTitleDisplayed(String sectionName) {
        By sectionLocator = By.xpath(String.format(sectionTitleLocatorFormatString, sectionName));
        return driver.findElement(sectionLocator).isDisplayed();
    }

    @Step("Проверяем, что открыта Главная страница")
    public boolean isOpen() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(mainHeaderLocator)).isDisplayed();
    }
}
