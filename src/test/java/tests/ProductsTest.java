package tests;

import elements.InventoryItem;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsTest extends BaseTest{

    //Проверка списка товаров, наличие одного известного товара и корректности заполнения его карточки
    //Тут нужен софтассерт, чтоб понять, что именно упало
    @Test
    public void checkProducts(){
        login();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(productsPage.getItems().size(), 6);
        InventoryItem item = productsPage.getItemByName("Sauce Labs Backpack");
        softAssert.assertEquals(item.getName(), "Sauce Labs Backpack");
        softAssert.assertEquals(item.getDescription(), "carry.allTheThings() with the sleek, streamlined " +
                "Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        softAssert.assertEquals(item.getPrice(), "$29.99");
        softAssert.assertTrue(item.isImageDisplayed());
        softAssert.assertAll();
    }

    //Добавление товара в корзину и удаление из неё
    //А тут не нужен софтассерт, потому что если товар в корзину не добавился, то его не удалить
    @Test
    public void checkAddRemoveChart(){
        login();
        InventoryItem item = productsPage.getItemByName("Sauce Labs Backpack");
        item.addToCart();
        Assert.assertTrue(item.isInCart());
        Assert.assertEquals(productsPage.getCartBadgeText(), "1");
        item.removeFromCart();
        Assert.assertFalse(item.isInCart());
        Assert.assertFalse(productsPage.isCartBadgeDisplayed());
    }

    //сортировка по имени A -> Z
    @Test
    public void sortByNameAZTest() {
        login();
        productsPage.sortBy("az");

        List<String> actual = productsPage.getItemNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(null); //почему не работает Collections.naturalOrder()?

        Assert.assertEquals(actual, expected);
    }

    //сортировка по имени Z -> A
    @Test
    public void sortByNameZATest() {
        login();
        productsPage.sortBy("za");

        List<String> actual = productsPage.getItemNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());

        Assert.assertEquals(actual, expected);
    }

    //сортировка по цене по возрастанию
    @Test
    public void sortByPriceAscTest() {
        login();
        productsPage.sortBy("lohi");

        List<Double> actual = productsPage.getItemPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(null);

        Assert.assertEquals(actual, expected);
    }

    //сортировка по цене по убыванию
    @Test
    public void sortByPriceDescTest() {
        login();
        productsPage.sortBy("hilo");

        List<Double> actual = productsPage.getItemPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());

        Assert.assertEquals(actual, expected);
    }
}
