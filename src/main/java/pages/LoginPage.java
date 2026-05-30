package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class LoginPage extends BasePage {

    //Описываем элементы
    private final By USERNAME_FIELD = By.id("user-name");
    private final By PASSWORD_FIELD = By.id("password");
    private final By LOGIN_BUTTON = By.id("login-button");
    private final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isLoaded() {
        log.info("Check login page is loaded");
        return driver.getCurrentUrl().equals(BASE_URL) && isElementDisplayed(LOGIN_BUTTON);
    }

    //Описываем методы взаимодействия
    @Step("Открытие страницы логина")
    public LoginPage open() {
        log.info("Open login page");
        driver.get(BASE_URL);
        checkPageIsLoaded();
        return this;
    }

    @Step("Вход в магазин с логином: '{user}' и паролем: '{password}'")
    public ProductsPage loginWithCredentials(String login, String password) {
        log.info("Login with credentials '{}', '{}'", login, password);
        checkPageIsLoaded();
        driver.findElement(USERNAME_FIELD).sendKeys(login);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
        return new ProductsPage(driver);
    }

    @Step("Получение сообщения об ошибке")
    public String getErrorMessage() {
        log.info("Get login error message");
        checkPageIsLoaded();
        return driver.findElement(ERROR_MESSAGE).getText();
    }
}
