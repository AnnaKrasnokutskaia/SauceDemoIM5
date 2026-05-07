package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
public class LoginTest extends BaseTest{

    @Test (description = "Проверка логина с корректными кредами",
            testName = "Проверка логина")
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
    public void negativeLogin(String user, String password, String message){
        loginPage.open();
        loginPage.loginWithCredentials(user, password);
        assertEquals(loginPage.getErrorMessage(), message);
    }
}
