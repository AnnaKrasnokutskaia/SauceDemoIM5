package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class CheckoutPage extends BasePage {

    private final By TITLE = By.cssSelector("[data-test='title']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isLoaded() {
        log.info("Check checkout page is loaded");
        return driver.getCurrentUrl().contains("checkout-step-one.html") && isElementDisplayed(TITLE);
    }

    public CheckoutPage open() {
        log.info("Open checkout page");
        driver.get(BASE_URL + "checkout-step-one.html");
        checkPageIsLoaded();
        return this;
    }

    public String getTitle() {
        log.info("Get checkout page title");
        checkPageIsLoaded();
        return driver.findElement(TITLE).getText();
    }
}
