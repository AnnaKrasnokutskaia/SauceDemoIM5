package tests;

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
    public void checkAddedProductInCart() {
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");
        //выбрали продукт
        ProductsPage.InventoryItem product = productsPage.getItemByName("Sauce Labs Backpack");
        //что искать будем на страничке, записали
        String expectedName = product.getName();
        String expectedDescription = product.getDescription();
        String expectedPrice = product.getPrice();
        //добавили в корзину
        product.addToCart();
        //перешли в корзину
        productsPage.openCart();
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
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");

        productsPage.getItemByName("Sauce Labs Backpack").addToCart();
        productsPage.getItemByName("Sauce Labs Bike Light").addToCart();
        productsPage.getItemByName("Sauce Labs Fleece Jacket").addToCart();

        productsPage.openCart();

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
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");

        productsPage.getItemByName("Sauce Labs Backpack").addToCart();
        productsPage.openCart();

        cartPage.getItemByName("Sauce Labs Backpack").remove();

        Assert.assertEquals(cartPage.getItemsCount(), 0);
    }
}