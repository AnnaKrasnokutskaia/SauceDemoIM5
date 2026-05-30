package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CartPage extends BasePage {

    private final By TITLE = By.cssSelector("[data-test='title']");
    private final By CART_ITEMS = By.cssSelector("[data-test='inventory-item']");
    private final By CHECKOUT_BUTTON = By.cssSelector("[data-test='checkout']");
    private final By CONTINUE_SHOPPING_BUTTON = By.cssSelector("[data-test='continue-shopping']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isLoaded() {
        log.info("Check cart page is loaded");
        return driver.getCurrentUrl().contains("cart.html") && isElementDisplayed(TITLE);
    }

    public CartPage open() {
        log.info("Open cart page");
        driver.get(BASE_URL + "cart.html");
        checkPageIsLoaded();
        return this;
    }

    public String getTitle() {
        log.info("Get cart page title");
        checkPageIsLoaded();
        return driver.findElement(TITLE).getText();
    }

    @Step("Получить список товаров в корзине")
    public List<CartItem> getItems() {
        log.info("Get items from cart");
        checkPageIsLoaded();
        List<WebElement> elements = driver.findElements(CART_ITEMS);
        List<CartItem> items = new ArrayList<>();

        for (WebElement element : elements) {
            items.add(new CartItem(element, this));
        }

        return items;
    }

    @Step("Получить количество различных товаров в корзине")
    public int getItemsCount() {
        log.info("Get cart items count");
        checkPageIsLoaded();
        return getItems().size();
    }

    @Step("Получить товар по имени")
    public CartItem getItemByName(String itemName) throws RuntimeException {
        log.info("Get cart item by name '{}'", itemName);
        checkPageIsLoaded();
        for (CartItem item : getItems()) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        throw new RuntimeException("Товар не найден в корзине: " + itemName);
    }

    @Step("Проверить отображение товара")
    public boolean isItemDisplayed(String itemName) {
        log.info("Check cart item is displayed: '{}'", itemName);
        checkPageIsLoaded();
        for (CartItem item : getItems()) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    @Step("Нажать кнопку 'Checkout'")
    public CheckoutPage clickCheckout() {
        log.info("Click checkout button");
        checkPageIsLoaded();
        driver.findElement(CHECKOUT_BUTTON).click();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.checkPageIsLoaded();
        return checkoutPage;
    }

    @Step("Нажать кнопку 'Continue Shopping'")
    public ProductsPage clickContinueShopping() {
        log.info("Click continue shopping button");
        checkPageIsLoaded();
        driver.findElement(CONTINUE_SHOPPING_BUTTON).click();
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.checkPageIsLoaded();
        return productsPage;
    }

    @Log4j2
    public static class CartItem {

        private final WebElement root;
        private final CartPage cartPage;

        private final By quantity = By.cssSelector("[data-test='item-quantity']");
        private final By name = By.cssSelector("[data-test='inventory-item-name']");
        private final By description = By.cssSelector("[data-test='inventory-item-desc']");
        private final By price = By.cssSelector("[data-test='inventory-item-price']");
        private final By removeButton = By.cssSelector("button");

        public CartItem(WebElement root, CartPage cartPage) {
            this.root = root;
            this.cartPage = cartPage;
        }

        @Step("Получить имя товара")
        public String getName() {
            log.info("Get cart item name");
            return root.findElement(name).getText();
        }

        @Step("Получить количество товара")
        public String getQuantity() {
            log.info("Get cart item quantity");
            return root.findElement(quantity).getText();
        }

        @Step("Получить описание товара")
        public String getDescription() {
            log.info("Get cart item description");
            return root.findElement(description).getText();
        }

        @Step("Получить цену товара")
        public String getPrice() {
            log.info("Get cart item price");
            return root.findElement(price).getText();
        }

        @Step("Удалить товар из корзины")
        public CartPage remove() {
            log.info("Remove item from cart");
            root.findElement(removeButton).click();
            cartPage.checkPageIsLoaded();
            return cartPage;
        }
    }
}
