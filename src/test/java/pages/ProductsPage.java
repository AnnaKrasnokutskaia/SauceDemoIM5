package pages;

import elements.InventoryItem;
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
        driver.get(BASE_URL + "/inventory.html");
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
}
