package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProductsPage extends BasePage {

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    private final By TITLE = By.cssSelector("[data-test='title']");
    private final By INVENTORY_ITEMS = By.cssSelector("[data-test='inventory-item']");
    private final By SORTER = By.cssSelector("[data-test='product-sort-container']");
    private final By CART_LINK = By.cssSelector("[data-test='shopping-cart-link']");
    private final By CART_BADGE = By.cssSelector("[data-test='shopping-cart-badge']");

    @Override
    protected boolean isLoaded() {
        log.info("Check products page is loaded");
        return driver.getCurrentUrl().contains("inventory.html") && isElementDisplayed(TITLE);
    }

    public String getTitle() {
        log.info("Get products page title");
        checkPageIsLoaded();
        return driver.findElement(TITLE).getText();
    }

    public ProductsPage open() {
        log.info("Open products page");
        driver.get(BASE_URL + "inventory.html");
        checkPageIsLoaded();
        return this;
    }

    //Сделали отдельный класс карточек товара, теперь получаем их списком
    @Step("Получение списка товаров")
    public List<InventoryItem> getItems() {
        log.info("Get inventory items");
        checkPageIsLoaded();
        List<WebElement> elements = driver.findElements(INVENTORY_ITEMS);
        List<InventoryItem> items = new ArrayList<>();

        for (WebElement element : elements) {
            items.add(new InventoryItem(element, this));
        }

        return items;
    }

    //получаем конкретную карточку товара по имени
    @Step("Получение карточки товара по имени")
    public InventoryItem getItemByName(String itemName) throws RuntimeException {
        log.info("Get inventory item by name '{}'", itemName);
        checkPageIsLoaded();
        for (InventoryItem item : getItems()) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        throw new RuntimeException("Товар не найден: " + itemName);
    }

    //получение списка имён товаров
    @Step("Получение списка имён товаров")
    public List<String> getItemNames() {
        log.info("Get inventory item names");
        checkPageIsLoaded();
        List<String> names = new ArrayList<>();

        for (InventoryItem item : getItems()) {
            names.add(item.getName());
        }

        return names;
    }

    //получение списка цен
    @Step("Получение списка цен")
    public List<Double> getItemPrices() {
        log.info("Get inventory item prices");
        checkPageIsLoaded();
        List<Double> prices = new ArrayList<>();

        for (InventoryItem item : getItems()) {
            String priceText = item.getPrice().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }

        return prices;
    }

    //сортировка
    @Step("Сортировка '{value}'")
    public ProductsPage sortBy(String value) {
        log.info("Sort products by '{}'", value);
        checkPageIsLoaded();
        Select select = new Select(driver.findElement(SORTER));
        select.selectByValue(value);
        return this;
    }

    //перейти в корзину по кнопочке
    @Step("Перейти в корзину по кнопочке")
    public CartPage openCart() {
        log.info("Open cart from products page");
        checkPageIsLoaded();
        driver.findElement(CART_LINK).click();
        CartPage cartPage = new CartPage(driver);
        cartPage.checkPageIsLoaded();
        return cartPage;
    }

    //получить счётчик товаров
    @Step("Получить счётчик товаров")
    public String getCartBadgeText() {
        log.info("Get cart badge text");
        checkPageIsLoaded();
        return driver.findElement(CART_BADGE).getText();
    }

    //счётчик товаров отображается?
    @Step("Проверить отображение счетчика товаров")
    public boolean isCartBadgeDisplayed() {
        log.info("Check cart badge is displayed");
        checkPageIsLoaded();
        return !driver.findElements(CART_BADGE).isEmpty();
    }

    @Log4j2
    public static class InventoryItem {

        // Корневой элемент конкретного товара (div.inventory_item)
        private final WebElement root;
        private final ProductsPage productsPage;

        // Локаторы внутренних элементов карточки товара
        private final By name = By.cssSelector("[data-test='inventory-item-name']");
        private final By description = By.cssSelector("[data-test='inventory-item-desc']");
        private final By price = By.cssSelector("[data-test='inventory-item-price']");
        private final By button = By.cssSelector("button"); // одна кнопка: Add или Remove
        private final By image = By.cssSelector("img");

        //Конструктор принимает WebElement конкретного товара(driver.findElements(...))
        public InventoryItem(WebElement root, ProductsPage productsPage) {
            this.root = root;
            this.productsPage = productsPage;
        }

        //Получить название товара
        @Step("Получить название товара")
        public String getName() {
            log.info("Get inventory item name");
            return root.findElement(name).getText();
        }

        //Получить описание товара
        @Step("Получить описание товара")
        public String getDescription() {
            log.info("Get inventory item description");
            return root.findElement(description).getText();
        }

        //Получить цену товара
        @Step("Получить цену товара")
        public String getPrice() {
            log.info("Get inventory item price");
            return root.findElement(price).getText();
        }

        //Получить текст кнопки (Add to cart / Remove)
        @Step("Получить текст кнопки (Add to cart / Remove)")
        public String getButtonText() {
            log.info("Get inventory item button text");
            return root.findElement(button).getText();
        }

        //Проверка: товар уже добавлен в корзину или нет. Если кнопка = "Remove" → товар уже в корзине
        @Step("Проверка: товар уже добавлен в корзину или нет")
        public boolean isInCart() {
            log.info("Check inventory item is in cart");
            return getButtonText().equals("Remove");
        }

        //Добавить товар в корзину
        @Step("Добавить товар в корзину")
        public ProductsPage addToCart() {
            log.info("Add inventory item to cart");
            if (!isInCart()) {
                root.findElement(button).click();
            }
            productsPage.checkPageIsLoaded();
            return productsPage;
        }

        //Удалить товар из корзины
        @Step("Удалить товар из корзины")
        public ProductsPage removeFromCart() {
            log.info("Remove inventory item from cart");
            if (isInCart()) {
                root.findElement(button).click();
            }
            productsPage.checkPageIsLoaded();
            return productsPage;
        }

        @Step("Проверить отображение изображения")
        public boolean isImageDisplayed() {
            log.info("Check inventory item image is displayed");
            return root.findElement(image).isDisplayed();
        }
    }
}
