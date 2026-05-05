package tests;

import elements.CartItem;
import elements.InventoryItem;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CartTest extends BaseTest {

    //добавление продукта в корзину
    @Test
    public void checkAddedProductInCart() {
        login();
        //выбрали продукт
        InventoryItem product = productsPage.getItemByName("Sauce Labs Backpack");
        //что искать будем на страничке, записали
        String expectedName = product.getName();
        String expectedDescription = product.getDescription();
        String expectedPrice = product.getPrice();
        //добавили в корзину
        product.addToCart();
        //перешли в корзину
        productsPage.openCart();
        //нашли товар в корзине
        CartItem cartItem = cartPage.getItemByName(expectedName);
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
    @Test
    public void checkSeveralProductsInCart() {
        login();

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
    @Test
    public void checkRemoveProductFromCart() {
        login();

        productsPage.getItemByName("Sauce Labs Backpack").addToCart();
        productsPage.openCart();

        cartPage.getItemByName("Sauce Labs Backpack").remove();

        Assert.assertEquals(cartPage.getItemsCount(), 0);
    }
}