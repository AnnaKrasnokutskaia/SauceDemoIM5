package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public abstract class BasePage {
    protected WebDriver driver;
    protected final String BASE_URL = "https://www.saucedemo.com/";

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected abstract boolean isLoaded();

    protected void checkPageIsLoaded() {
        log.info("Check page is loaded: {}", getClass().getSimpleName());
        if (!isLoaded()) {
            throw new AssertionError("Страница не загрузилась: " + getClass().getSimpleName()
                    + ". Текущий URL: " + driver.getCurrentUrl());
        }
    }

    protected boolean isElementDisplayed(By locator) {
        log.info("Check element is displayed: {}", locator);
        return !driver.findElements(locator).isEmpty() && driver.findElement(locator).isDisplayed();
    }
}
