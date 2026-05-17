package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProductsPage;
import tests.base.BaseTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsTest extends BaseTest {

    //Проверка списка товаров, наличие одного известного товара и корректности заполнения его карточки
    //Тут нужен софтассерт, чтоб понять, что именно упало
    @Test(description = "Проверка наличия на странице корректного списка продуктов",
            testName = "Проверка наличия на странице корректного списка продуктов")
    public void checkProducts(){
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(productsPage.getItems().size(), 6);
        ProductsPage.InventoryItem item = productsPage.getItemByName("Sauce Labs Backpack");
        softAssert.assertEquals(item.getName(), "Sauce Labs Backpack");
        softAssert.assertEquals(item.getDescription(), "carry.allTheThings() with the sleek, streamlined " +
                "Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        softAssert.assertEquals(item.getPrice(), "$29.99");
        softAssert.assertTrue(item.isImageDisplayed());
        softAssert.assertAll();
    }

    //Добавление товара в корзину и удаление из неё
    //А тут не нужен софтассерт, потому что если товар в корзину не добавился, то его не удалить
    @Test(description = "Проверка добавления и удаления товаров из корзины",
            testName = "Проверка добавления и удаления товаров из корзины")
    public void checkAddRemoveChart(){
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");
        ProductsPage.InventoryItem item = productsPage.getItemByName("Sauce Labs Backpack");
        item.addToCart();
        Assert.assertTrue(item.isInCart());
        Assert.assertEquals(productsPage.getCartBadgeText(), "2");
        item.removeFromCart();
        Assert.assertFalse(item.isInCart());
        Assert.assertFalse(productsPage.isCartBadgeDisplayed());
    }

    //сортировка по имени A -> Z
    @Test(description = "Проверка сортировки в алфавитном порядке",
            testName = "Проверка сортировки в алфавитном порядке",
            groups = {"sort"})
    public void sortByNameAZTest() {
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");
        productsPage.sortBy("az");

        List<String> actual = productsPage.getItemNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(null); //почему не работает Collections.naturalOrder()?

        Assert.assertEquals(actual, expected);
    }

    //сортировка по имени Z -> A
    @Test(description = "Проверка сортировки в обратном алфавитном порядке",
            testName = "Проверка сортировки в обратном алфавитном порядке",
            groups = {"sort"})
    public void sortByNameZATest() {
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");
        productsPage.sortBy("za");

        List<String> actual = productsPage.getItemNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());

        Assert.assertEquals(actual, expected);
    }

    //сортировка по цене по возрастанию
    @Test(description = "Проверка сортировки по возрастанию",
            testName = "Проверка сортировки по возрастанию",
            groups = {"sort"})
    public void sortByPriceAscTest() {
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");
        productsPage.sortBy("lohi");

        List<Double> actual = productsPage.getItemPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(null);

        Assert.assertEquals(actual, expected);
    }

    //сортировка по цене по убыванию
    @Test(description = "Проверка сортировки по убыванию",
            testName = "Проверка сортировки по убыванию",
            groups = {"sort"})
    public void sortByPriceDescTest() {
        loginPage.open();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");
        productsPage.sortBy("hilo");

        List<Double> actual = productsPage.getItemPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());

        Assert.assertEquals(actual, expected);
    }
}
