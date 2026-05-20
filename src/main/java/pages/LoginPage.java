package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage<LoginPage> {

    //Описываем элементы
    private final By USERNAME_FIELD = By.id("user-name");
    private final By PASSWORD_FIELD = By.id("password");
    private final By LOGIN_BUTTON = By.id("login-button");
    private final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void load() {
        driver.get(BASE_URL);
    }

    @Override
    protected void isLoaded() throws Error {
        if (!driver.getCurrentUrl().equals(BASE_URL)) {
            throw new AssertionError("Ожидалась страница логина, текущий URL: " + driver.getCurrentUrl());
        }
        checkElementIsDisplayed(LOGIN_BUTTON, "кнопка Login");
    }

    //Описываем методы взаимодействия
    @Step("Открытие страницы логина")
    public LoginPage open() {
        return get();
    }

    @Step("Вход в магазин с логином: '{login}' и паролем: '{password}'")
    public ProductsPage loginWithCredentials(String login, String password) {
        checkPageIsLoaded();
        driver.findElement(USERNAME_FIELD).sendKeys(login);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
        return new ProductsPage(driver);
    }

    @Step("Получение сообщения об ошибке")
    public String getErrorMessage() {
        checkPageIsLoaded();
        return driver.findElement(ERROR_MESSAGE).getText();
    }
}
