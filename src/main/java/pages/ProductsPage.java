package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

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
        return driver.getCurrentUrl().contains("inventory.html") && isElementDisplayed(TITLE);
    }

    public String getTitle() {
        checkPageIsLoaded();
        return driver.findElement(TITLE).getText();
    }

    public ProductsPage open() {
        driver.get(BASE_URL + "inventory.html");
        checkPageIsLoaded();
        return this;
    }

    //Сделали отдельный класс карточек товара, теперь получаем их списком
    @Step("Получение списка товаров")
    public List<InventoryItem> getItems() {
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
        checkPageIsLoaded();
        Select select = new Select(driver.findElement(SORTER));
        select.selectByValue(value);
        return this;
    }

    //перейти в корзину по кнопочке
    @Step("Перейти в корзину по кнопочке")
    public CartPage openCart() {
        checkPageIsLoaded();
        driver.findElement(CART_LINK).click();
        CartPage cartPage = new CartPage(driver);
        cartPage.checkPageIsLoaded();
        return cartPage;
    }

    //получить счётчик товаров
    @Step("Получить счётчик товаров")
    public String getCartBadgeText() {
        checkPageIsLoaded();
        return driver.findElement(CART_BADGE).getText();
    }

    //счётчик товаров отображается?
    @Step("Проверить отображение счетчика товаров")
    public boolean isCartBadgeDisplayed() {
        checkPageIsLoaded();
        return !driver.findElements(CART_BADGE).isEmpty();
    }

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
            return root.findElement(name).getText();
        }

        //Получить описание товара
        @Step("Получить описание товара")
        public String getDescription() {
            return root.findElement(description).getText();
        }

        //Получить цену товара
        @Step("Получить цену товара")
        public String getPrice() {
            return root.findElement(price).getText();
        }

        //Получить текст кнопки (Add to cart / Remove)
        @Step("Получить текст кнопки (Add to cart / Remove)")
        public String getButtonText() {
            return root.findElement(button).getText();
        }

        //Проверка: товар уже добавлен в корзину или нет. Если кнопка = "Remove" → товар уже в корзине
        @Step("Проверка: товар уже добавлен в корзину или нет")
        public boolean isInCart() {
            return getButtonText().equals("Remove");
        }

        //Добавить товар в корзину
        @Step("Добавить товар в корзину")
        public ProductsPage addToCart() {
            if (!isInCart()) {
                root.findElement(button).click();
            }
            productsPage.checkPageIsLoaded();
            return productsPage;
        }

        //Удалить товар из корзины
        @Step("Удалить товар из корзины")
        public ProductsPage removeFromCart() {
            if (isInCart()) {
                root.findElement(button).click();
            }
            productsPage.checkPageIsLoaded();
            return productsPage;
        }

        @Step("Проверить отображение изображения")
        public boolean isImageDisplayed() {
            return root.findElement(image).isDisplayed();
        }
    }
}
