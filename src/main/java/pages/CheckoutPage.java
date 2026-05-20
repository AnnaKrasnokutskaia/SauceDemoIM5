package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private final By TITLE = By.cssSelector("[data-test='title']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isLoaded() {
        return driver.getCurrentUrl().contains("checkout-step-one.html") && isElementDisplayed(TITLE);
    }

    public CheckoutPage open() {
        driver.get(BASE_URL + "checkout-step-one.html");
        checkPageIsLoaded();
        return this;
    }

    public String getTitle() {
        checkPageIsLoaded();
        return driver.findElement(TITLE).getText();
    }
}
