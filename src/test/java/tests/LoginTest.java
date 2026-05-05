package tests;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
public class LoginTest extends BaseTest{

    @Test
    public void checkLoginWithPositiveCred(){
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");
        assertEquals(productsPage.getTitle(), "Products");
    }

    @Test
    public void checkLoginWithWrongLogin(){
        loginPage.open();
        loginPage.loginWithCredentials("standard_user_test", "secret_sauce");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do not match any user" +
                " in this service");
    }

    @Test
    public void checkLoginWithWrongPassword(){
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce_test");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do not match any user" +
                " in this service");
    }

    @Test
    public void checkLoginWithEmptyPassword(){
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Password is required");
    }

    @Test
    public void checkLoginWithEmptyLogin(){
        loginPage.open();
        loginPage.loginWithCredentials("", "secret_sauce");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username is required");
    }
}
