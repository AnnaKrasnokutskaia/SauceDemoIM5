package pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
    WebDriver driver;
    public final String BASE_URL = "https://www.saucedemo.com/";

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
}
