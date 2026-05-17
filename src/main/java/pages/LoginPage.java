package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{

    //Описываем элементы
    private final By USERNAME_FIELD = By.id("user-name");
    private final By PASSWORD_FIELD = By.id("password");
    private final By LOGIN_BUTTON = By.id("login-button");
    private final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    //Описываем методы взаимодействия
    @Step("Открытие страницы логина")
    public void open(){
        driver.get(BASE_URL);
    }

    @Step("Вход в магазин с логином: '{user}' и паролем: '{password}'")
    public void loginWithCredentials (String login, String password){
        driver.findElement(USERNAME_FIELD).sendKeys(login);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
    }

    @Step("Получение сообщения об ошибке")
    public String getErrorMessage(){
        return driver.findElement(ERROR_MESSAGE).getText();
    }
}
