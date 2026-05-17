package tests;

import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.base.BaseTest;

import static org.testng.Assert.*;
public class LoginTest extends BaseTest {

    @Test (description = "Проверка логина с корректными кредами",
            testName = "Проверка логина")
    @Description("Проверка логина с корректными кредами")
    @Epic("E2E")
    @Feature("Login in to Sausedemo")
    @Story("Positive Login")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.saucedemo.com/")
    @TmsLink("ITM-5")
    @Issue("ITM-5")
    @Owner("Anna Krasnokutskaia")
    public void checkLoginWithPositiveCred(){
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");
        assertEquals(productsPage.getTitle(), "Products");
    }

    @DataProvider(name = "Тестовые данные для негативного логина")
    public Object[][] loginData(){
        return new Object[][]{
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"},
                {"standard_user_test", "secret_sauce", "Epic sadface: Username and password do not match any user in this service"},
                {"standard_user", "secret_sauce_test", "Epic sadface: Username and password do not match any user in this service"},
                {"test", "test", "Epic sadface: Username and password do not match any user in this service"}
        };
    }

    @Test(description = "Проверка логина с некорректными кредами",
            testName = "Проверка логина",
            dataProvider = "Тестовые данные для негативного логина")
    @Description("Проверка логина с не корректными кредами")
    @Epic("E2E")
    @Feature("Login in to Sausedemo")
    @Story("Negative Login")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.saucedemo.com/")
    @TmsLink("ITM-5")
    @Issue("ITM-5")
    @Owner("Anna Krasnokutskaia")
    public void negativeLogin(String user, String password, String message){
        loginPage.open();
        loginPage.loginWithCredentials(user, password);
        assertEquals(loginPage.getErrorMessage(), message);
    }
}
