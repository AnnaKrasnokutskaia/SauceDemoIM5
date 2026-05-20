package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected WebDriver driver;
    protected final String BASE_URL = "https://www.saucedemo.com/";

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected abstract boolean isLoaded();

    protected void checkPageIsLoaded() {
        if (!isLoaded()) {
            throw new AssertionError("Страница не загрузилась: " + getClass().getSimpleName()
                    + ". Текущий URL: " + driver.getCurrentUrl());
        }
    }

    protected boolean isElementDisplayed(By locator) {
        return !driver.findElements(locator).isEmpty() && driver.findElement(locator).isDisplayed();
    }
}
