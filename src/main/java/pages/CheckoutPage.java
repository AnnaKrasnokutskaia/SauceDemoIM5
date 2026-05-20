package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage<CheckoutPage> {

    private final By TITLE = By.cssSelector("[data-test='title']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void load() {
        driver.get(BASE_URL + "checkout-step-one.html");
    }

    @Override
    protected void isLoaded() throws Error {
        checkUrlContains("checkout-step-one.html");
        checkElementIsDisplayed(TITLE, "заголовок Checkout: Your Information");
    }

    public CheckoutPage open() {
        return get();
    }

    public String getTitle() {
        checkPageIsLoaded();
        return driver.findElement(TITLE).getText();
    }
}
