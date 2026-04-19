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

        //считаем цену со странички, а то вдруг подорожает
        String originalPrice = driver.findElement(By.xpath("//*[@id='add-to-cart-sauce-labs-fleece-jacket']/preceding-sibling::div[@data-test='inventory-item-price']")).getText();

        //добавим товар в корзину
        driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();

        //перейдём в корзину
        driver.findElement(By.cssSelector("[data-test='shopping-cart-link']")).click();

        //проверим, что там
        String itemName = driver.findElement(By.cssSelector("[data-test='inventory-item-name']")).getText();
        Assert.assertEquals(itemName, "Sauce Labs Fleece Jacket");
        String price = driver.findElement(By.cssSelector("[data-test='inventory-item-price']")).getText();
        Assert.assertEquals(price, originalPrice);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}