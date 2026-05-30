package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.ProductsPage;
import tests.base.BaseTest;

public class CartTest extends BaseTest {

    //добавление продукта в корзину
    @Test(description = "Проверка наличия в корзине добавленного продукта",
            testName = "Проверка наличия в корзине добавленного продукта")
    @Description("Проверка наличия в корзине добавленного продукта")
    @Epic("E2E")
    @Feature("C")
    @Story("Negative Login")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://www.saucedemo.com/")
    @TmsLink("ITM-5")
    @Issue("ITM-5")
    @Owner("Anna Krasnokutskaia")
    public void checkAddedProductInCart() {
        productsPage = loginPage
                .open()
                .loginWithCredentials(login, password);
        //выбрали продукт
        ProductsPage.InventoryItem product = productsPage.getItemByName("Sauce Labs Backpack");
        //что искать будем на страничке, записали
        String expectedName = product.getName();
        String expectedDescription = product.getDescription();
        String expectedPrice = product.getPrice();
        //добавили в корзину и перешли в корзину
        cartPage = product
                .addToCart()
                .openCart();
        //нашли товар в корзине
        CartPage.CartItem cartItem = cartPage.getItemByName(expectedName);
        //тут важно, что именно из проверок упало, и да мне лень делить на один метод - один ассерт
        SoftAssert softAssert = new SoftAssert();
        //мы вообще в корзине?
        softAssert.assertEquals(cartPage.getTitle(), "Your Cart");
        //и что добавили
        softAssert.assertEquals(cartPage.getItemsCount(), 1);
        softAssert.assertEquals(cartItem.getName(), expectedName);
        softAssert.assertEquals(cartItem.getDescription(), expectedDescription);
        softAssert.assertEquals(cartItem.getPrice(), expectedPrice);
        softAssert.assertEquals(cartItem.getQuantity(), "1");

        softAssert.assertAll();
    }

    //добавление нескольких товаров
    @Test(description = "Проверка добавления в корзину нескольких продуктов",
            testName = "Проверка добавления в корзину нескольких продуктов")
    public void checkSeveralProductsInCart() {
        productsPage = loginPage
                .open()
                .loginWithCredentials(login, password);

        productsPage.getItemByName("Sauce Labs Backpack").addToCart();
        productsPage.getItemByName("Sauce Labs Bike Light").addToCart();
        productsPage.getItemByName("Sauce Labs Fleece Jacket").addToCart();

        cartPage = productsPage.openCart();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cartPage.getItemsCount(), 3);
        softAssert.assertTrue(cartPage.isItemDisplayed("Sauce Labs Backpack"));
        softAssert.assertTrue(cartPage.isItemDisplayed("Sauce Labs Bike Light"));
        softAssert.assertTrue(cartPage.isItemDisplayed("Sauce Labs Fleece Jacket"));
        softAssert.assertAll();
    }

    //удаление из корзины
    @Test(description = "Проверка удаления продукта из корзины",
            testName = "Проверка удаления продукта из корзины")
    public void checkRemoveProductFromCart() {
        productsPage = loginPage
                .open()
                .loginWithCredentials(login, password);

        cartPage = productsPage
                .getItemByName("Sauce Labs Backpack")
                .addToCart()
                .openCart();

        cartPage.getItemByName("Sauce Labs Backpack").remove();

        Assert.assertEquals(cartPage.getItemsCount(), 0);
    }
}
