package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage<T extends BasePage<T>> extends LoadableComponent<T> {
    protected final WebDriver driver;
    protected final String BASE_URL = "https://www.saucedemo.com/";

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    @SuppressWarnings("unchecked")
    protected T checkPageIsLoaded() {
        isLoaded();
        return (T) this;
    }

    protected void checkUrlContains(String urlPart) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlContains(urlPart));
        } catch (TimeoutException e) {
            throw new AssertionError("Ожидался URL с '" + urlPart + "', текущий URL: " + driver.getCurrentUrl(), e);
        }
    }

    protected void checkElementIsDisplayed(By locator, String elementName) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new AssertionError("Страница не загрузилась: не отображается элемент " + elementName, e);
        }
    }
}
