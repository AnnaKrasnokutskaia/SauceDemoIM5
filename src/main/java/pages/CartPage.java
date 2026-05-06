package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    private final By TITLE = By.cssSelector("[data-test='title']");
    private final By CART_ITEMS = By.cssSelector("[data-test='inventory-item']");
    private final By CHECKOUT_BUTTON = By.cssSelector("[data-test='checkout']");
    private final By CONTINUE_SHOPPING_BUTTON = By.cssSelector("[data-test='continue-shopping']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return driver.findElement(TITLE).getText();
    }

    public List<CartItem> getItems() {
        List<WebElement> elements = driver.findElements(CART_ITEMS);
        List<CartItem> items = new ArrayList<>();

        for (WebElement element : elements) {
            items.add(new CartItem(element));
        }

        return items;
    }

    public int getItemsCount() {
        return getItems().size();
    }

    public CartItem getItemByName(String itemName) throws RuntimeException {
        for (CartItem item : getItems()) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        throw new RuntimeException("Товар не найден в корзине: " + itemName);
    }

    public boolean isItemDisplayed(String itemName) {
        for (CartItem item : getItems()) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public void clickCheckout() {
        driver.findElement(CHECKOUT_BUTTON).click();
    }

    public void clickContinueShopping() {
        driver.findElement(CONTINUE_SHOPPING_BUTTON).click();
    }

    public static class CartItem {

        private final WebElement root;

        private final By quantity = By.cssSelector("[data-test='item-quantity']");
        private final By name = By.cssSelector("[data-test='inventory-item-name']");
        private final By description = By.cssSelector("[data-test='inventory-item-desc']");
        private final By price = By.cssSelector("[data-test='inventory-item-price']");
        private final By removeButton = By.cssSelector("button");

        public CartItem(WebElement root) {
            this.root = root;
        }

        public String getName() {
            return root.findElement(name).getText();
        }

        public String getQuantity() {
            return root.findElement(quantity).getText();
        }

        public String getDescription() {
            return root.findElement(description).getText();
        }

        public String getPrice() {
            return root.findElement(price).getText();
        }

        public void remove() {
            root.findElement(removeButton).click();
        }
    }
}