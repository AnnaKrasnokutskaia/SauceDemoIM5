package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage extends BasePage{

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    private final By TITLE = By.cssSelector("[data-test='title']");
    private final By INVENTORY_ITEMS = By.cssSelector("[data-test='inventory-item']");
    private final By SORTER = By.cssSelector("[data-test='product-sort-container']");
    private final By CART_LINK = By.cssSelector("[data-test='shopping-cart-link']");
    private final By CART_BADGE = By.cssSelector("[data-test='shopping-cart-badge']");


    public String getTitle(){
        return driver.findElement(TITLE).getText();
    }

    public void open() {
        driver.get(BASE_URL + "inventory.html");
    }

    //Сделали отдельный класс карточек товара, теперь получаем их списком
    public List<InventoryItem> getItems() {
        List<WebElement> elements = driver.findElements(INVENTORY_ITEMS);
        List<InventoryItem> items = new ArrayList<>();

        for (WebElement element : elements) {
            items.add(new InventoryItem(element));
        }

        return items;
    }

    //получаем конкретную карточку товара по имени
    public InventoryItem getItemByName(String itemName) throws RuntimeException {
        for (InventoryItem item : getItems()) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        throw new RuntimeException("Товар не найден: " + itemName);
    }

    //получение списка имён товаров
    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();

        for (InventoryItem item : getItems()) {
            names.add(item.getName());
        }

        return names;
    }

    //получение списка цен
    public List<Double> getItemPrices() {
        List<Double> prices = new ArrayList<>();

        for (InventoryItem item : getItems()) {
            String priceText = item.getPrice().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }

        return prices;
    }

    //сортировка
    public void sortBy(String value) {
        Select select = new Select(driver.findElement(SORTER));
        select.selectByValue(value);
    }

    //перейти в корзину по кнопочке
    public void openCart() {
        driver.findElement(CART_LINK).click();
    }

    //получить счётчик товаров
    public String getCartBadgeText() {
        return driver.findElement(CART_BADGE).getText();
    }

    //счётчик товаров отображается?
    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(CART_BADGE).isEmpty();
    }

    public static class InventoryItem {

        // Корневой элемент конкретного товара (div.inventory_item)
        private final WebElement root;

        // Локаторы внутренних элементов карточки товара
        private final By name = By.cssSelector("[data-test='inventory-item-name']");
        private final By description = By.cssSelector("[data-test='inventory-item-desc']");
        private final By price = By.cssSelector("[data-test='inventory-item-price']");
        private final By button = By.cssSelector("button"); // одна кнопка: Add или Remove
        private final By image = By.cssSelector("img");

        //Конструктор принимает WebElement конкретного товара(driver.findElements(...))
        public InventoryItem(WebElement root) {
            this.root = root;
        }

        //Получить название товара
        public String getName() {
            return root.findElement(name).getText();
        }

        //Получить описание товара
        public String getDescription() {
            return root.findElement(description).getText();
        }

        //Получить цену товара
        public String getPrice() {
            return root.findElement(price).getText();
        }

        //Получить текст кнопки (Add to cart / Remove)
        public String getButtonText() {
            return root.findElement(button).getText();
        }

        //Проверка: товар уже добавлен в корзину или нет. Если кнопка = "Remove" → товар уже в корзине
        public boolean isInCart() {
            return getButtonText().equals("Remove");
        }

        //Добавить товар в корзину
        public void addToCart() {
            if (!isInCart()) {
                root.findElement(button).click();
            }
        }

        //Удалить товар из корзины
        public void removeFromCart() {
            if (isInCart()) {
                root.findElement(button).click();
            }
        }

        public boolean isImageDisplayed() {
            return root.findElement(image).isDisplayed();
        }
    }
}
