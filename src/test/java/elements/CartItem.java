package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CartItem {

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