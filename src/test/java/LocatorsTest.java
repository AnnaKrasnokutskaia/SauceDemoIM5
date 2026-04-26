import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.HashMap;


/*Создать новый Java-класс, в нем для ресурса
https://www.saucedemo.com/ составить список локаторов, можно искать на
ВСЕХ страницах приложения (driver.findElement(<локатор>)) для КАЖДОГО из
примеров локаторов ниже:
• id
• name
• classname
• tagname
• linktext
• partiallinktext
• xpath:
- поиск по атрибуту, например By.xpath("//tag[@attribute='value']");
- поиск по тексту, например By.xpath("//tag[text()='text']");
- поиск по частичному совпадению атрибута, например
By.xpath("//tag[contains(@attribute,'text')]");
- поиск по частичному совпадению текста, например
By.xpath("//tag[contains(text(),'text')]");
- ancestor, например //*[text()='Enterprise Testing']//ancestor::div
- descendant
- following
- parent
- preceding
- Подсказка: XPath Axes
- *поиск элемента с условием AND, например
//input[@class='_2zrpKA_1dBPDZ' and @type='text']
• css:
- .class
- .class1.class2
- .class1 .class2
- #id
- tagname

- tagname.class
- [attribute=value]
- [attribute~=value]
- [attribute|=value]
- [attribute^=value]
- [attribute$=value]
- [attribute*=value]*/

public class LocatorsTest {
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
    public void checkLocator() {
        //открывает страницу по указанному url
        driver.get("https://www.saucedemo.com/");
        //по тэгу найдём тут, тут меньше элементов, хотя всё равно не один
        driver.findElements(By.tagName("input"));
        //залогинимся
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.className("submit-button")).click();
        //продолжаем искать всякое
        driver.findElement(By.linkText("Sauce Labs Backpack"));
        driver.findElement(By.partialLinkText("Bike Light"));
        //поиск по атрибуту, например By.xpath("//tag[@attribute='value']");
        driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']"));
        //поиск по тексту, например By.xpath("//tag[text()='text']");
        driver.findElement(By.xpath("//div[text()='Swag Labs']"));
        //поиск по частичному совпадению атрибута, например By.xpath("//tag[contains(@attribute,'text')]");
        driver.findElement(By.xpath("//div[contains(@class,'secondary_container')]"));
        //поиск по частичному совпадению текста, например By.xpath("//tag[contains(text(),'text')]");
        driver.findElement(By.xpath("//div[contains(text(),'Sauce Labs Bolt')]"));
        //ancestor, например //*[text()='Enterprise Testing']//ancestor::div
        driver.findElement(By.xpath("//*[text()='Sauce Labs Onesie']//ancestor::div"));
        //descendant
        driver.findElement(By.xpath("//a[@id='item_3_title_link']//descendant::div"));
        //following
        driver.findElement(By.xpath("//a[@id='item_3_title_link']//following::div"));
        //parent
        driver.findElement(By.xpath("//*[text()='Sauce Labs Onesie']//parent::div"));
        //preceding
        driver.findElement(By.xpath("//div[contains(text(),'every day that you come across a midweight')]//preceding::a"));
        //поиск элемента с условием AND, например //input[@class='_2zrpKA_1dBPDZ' and @type='text']
        driver.findElement(By.xpath("//div[@data-test='inventory-item-desc' and contains(text(),'Super-soft and comfy ringspun')]"));

        //- .class
        driver.findElement(By.cssSelector(".shopping_cart_link"));
        //- .class1.class2
        //да, такой элемент не один и так писать нехорошо, ну оно возьмёт первый, а тут несколько классов только у этих кнопок
        driver.findElement(By.cssSelector(".btn.btn_primary.btn_small.btn_inventory"));
        //        - .class1 .class2
        driver.findElement(By.cssSelector(".shopping_cart_container .shopping_cart_link"));
        //        - #id
        driver.findElement(By.cssSelector("#menu_button_container"));
        //        - tagname
        driver.findElement(By.cssSelector("footer"));
        //        - tagname.class
        driver.findElement(By.cssSelector("ul.social"));
        //       - [attribute=value]
        driver.findElement(By.cssSelector("[data-test='primary-header']"));
        //- [attribute~=value]
        //это всё еще не лучший селектор для этой кнопки, ибо их много, но эта кнопка всё еще единственная с классами через пробел
        driver.findElement(By.cssSelector("[class~='btn_small']"));
        //- [attribute|=value]
        driver.findElement(By.cssSelector("[data-test|='header']"));
        //- [attribute^=value]
        driver.findElement(By.cssSelector("[id^='item_0_title_']"));
        //- [attribute$=value]
        driver.findElement(By.cssSelector("[id$='0_title_link']"));
        //- [attribute*=value]
        driver.findElement(By.cssSelector("[id*='0_title']"));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}