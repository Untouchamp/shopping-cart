package com.kuchukhidze.yandex;

import config.UITestSetup;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pages.MainPage;
import org.testng.annotations.*;
import pages.SearchResultsPage;

import java.util.ArrayList;
import java.util.List;

import static config.SetupConfig.UIEndpoints.baseUrl;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;

public class MainTest extends UITestSetup {
    MainPage mainPage = new MainPage();

    @BeforeClass
    public static void setUpAll() {
        open(baseUrl);
    }

    @BeforeMethod
    public void setUp() {
    }

    @Test
    public void namesInCartMatch() {
        SoftAssert softAssert = new SoftAssert();

        String searchText = "Пылесос";
        int itemIndex = 1;
        String expItemName =
                mainPage.searchResultsForText(searchText)
                .getNameOfItemByIndex(itemIndex);
        String cartItemName =
                SearchResultsPage.getPage()
                .addItemInCart(itemIndex)
                .goToCartPage()
                .getCartItemsNames().stream().findFirst().orElse("Список наименований пуст!");

        softAssert.assertTrue(expItemName.contains(cartItemName),
                "Названия товаров не совпадают! \nНазвание в каталоге: " + expItemName
                        + ".\nНазвание в корзине: " + cartItemName);

        softAssert.assertAll();


    }

    @Test
    public void assertCartClearAllBtnWorks() {
        SoftAssert softAssert = new SoftAssert();
        SearchResultsPage searchPage = page(SearchResultsPage.class);

        String searchText = "Пылесос";
        int firstItemIndex = 1;
        int secondItemIndex = 2;
        boolean cartIsEmpty = mainPage.searchResultsForText(searchText)
                .addItemInCart(firstItemIndex)
                .addItemInCart(secondItemIndex)
                .goToCartPage()
                .removeAllItemsFromCart()
                .cartIsEmpty();

        softAssert.assertTrue(cartIsEmpty, "Корзина не пуста!");

        softAssert.assertAll();

    }

}
