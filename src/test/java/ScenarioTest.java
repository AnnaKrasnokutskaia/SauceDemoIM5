import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.HashMap;


/*Создать отдельный Java-класс с тестом, сценарий:
a. Залогиниться
b. Добавить товар в корзину
c. Перейти в корзину
d. Проверить (assertEquals) стоимость товара и его имя в корзине*/

public class ScenarioTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        driver = new ChromeDriver(options);
    }
    @Test
    public void checkScenario() {
        //открывает страницу по указанному url
        driver.get("https://www.saucedemo.com/");

        //залогинимся
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("submit-button")).click();

        //найти блок, в котором поля с нужным именем
        String name = "Sauce Labs Fleece Jacket";
        String inventoryItemXpath = "//div[@class='inventory_item'][descendant-or-self::a[contains(@id, 'title_link')][contains(., '%s')]]";
        var inventoryItem = driver.findElement(By.xpath(inventoryItemXpath.formatted(name)));

        //цена элемента
        String originalPrice = inventoryItem.findElement(By.xpath("descendant::div[@data-test='inventory-item-price']")).getText();

        //добавим товар в корзину, для этого ткнём кнопку в нужном блоке
        inventoryItem.findElement(By.xpath("descendant::button")).click();

        //перейдём в корзину
        driver.findElement(By.cssSelector("[data-test='shopping-cart-link']")).click();

        //проверим, что там
        String itemName = driver.findElement(By.cssSelector("[data-test='inventory-item-name']")).getText();
        Assert.assertEquals(itemName, name);
        String price = driver.findElement(By.cssSelector("[data-test='inventory-item-price']")).getText();
        Assert.assertEquals(price, originalPrice);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}