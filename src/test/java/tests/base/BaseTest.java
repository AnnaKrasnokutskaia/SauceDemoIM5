package tests.base;

import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;
import utils.AllureUtils;
import utils.TestListener;

import java.time.Duration;
import java.util.HashMap;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected ProductsPage productsPage;
    protected CartPage cartPage;
    protected String login = System.getProperty("login");
    protected String password = System.getProperty("password");

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    @Description("Настройка браузера")
    public void setUp(@Optional("chrome") String browser, ITestContext iTestContext) {
        if (browser.equalsIgnoreCase("chrome")){
            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("credentials_enable_service", false);
            chromePrefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments("--incognito");
            options.addArguments("--headless");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")){
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            options.addArguments("--incognito");
            options.addArguments("--headless");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            driver = new FirefoxDriver(options);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        iTestContext.setAttribute("driver", driver);
    }

    @AfterMethod (alwaysRun = true)
    @Description("Закрытие браузера")
    public void tearDown() {
        driver.quit();
    }
}
