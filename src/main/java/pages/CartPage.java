package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage<CartPage> {

    private final By TITLE = By.cssSelector("[data-test='title']");
    private final By CART_ITEMS = By.cssSelector("[data-test='inventory-item']");
    private final By CHECKOUT_BUTTON = By.cssSelector("[data-test='checkout']");
    private final By CONTINUE_SHOPPING_BUTTON = By.cssSelector("[data-test='continue-shopping']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void load() {
        driver.get(BASE_URL + "cart.html");
    }

    @Override
    protected void isLoaded() throws Error {
        checkUrlContains("cart.html");
        checkElementIsDisplayed(TITLE, "заголовок Your Cart");
    }

    public CartPage open() {
        return get();
    }

    public String getTitle() {
        checkPageIsLoaded();
        return driver.findElement(TITLE).getText();
    }

    @Step("Получить список товаров в корзине")
    public List<CartItem> getItems() {
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
        checkPageIsLoaded();
        return getItems().size();
    }

    @Step("Получить товар по имени")
    public CartItem getItemByName(String itemName) throws RuntimeException {
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
        checkPageIsLoaded();
        driver.findElement(CHECKOUT_BUTTON).click();
        return new CheckoutPage(driver).checkPageIsLoaded();
    }

    @Step("Нажать кнопку 'Continue Shopping'")
    public ProductsPage clickContinueShopping() {
        checkPageIsLoaded();
        driver.findElement(CONTINUE_SHOPPING_BUTTON).click();
        return new ProductsPage(driver).checkPageIsLoaded();
    }

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
            return root.findElement(name).getText();
        }

        @Step("Получить количество товара")
        public String getQuantity() {
            return root.findElement(quantity).getText();
        }

        @Step("Получить описание товара")
        public String getDescription() {
            return root.findElement(description).getText();
        }

        @Step("Получить цену товара")
        public String getPrice() {
            return root.findElement(price).getText();
        }

        @Step("Удалить товар из корзины")
        public CartPage remove() {
            root.findElement(removeButton).click();
            return cartPage.checkPageIsLoaded();
        }
    }
}
