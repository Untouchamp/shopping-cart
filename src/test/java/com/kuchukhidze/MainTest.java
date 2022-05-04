package com.kuchukhidze;

import config.UITestSetup;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pages.MainPage;
import org.testng.annotations.*;
import pages.SearchResultsPage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static config.SetupConfig.UIEndpoints.baseUrl;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;

public class MainTest extends UITestSetup {
    private static MainPage mainPage;
    private static SearchResultsPage searchResultsPage;

    @BeforeClass
    public static void setUpAll() {
        mainPage = new MainPage();
        searchResultsPage = new SearchResultsPage();
    }

    @BeforeMethod
    public void setUp() {
        open(baseUrl);
    }

    @Test
    public void checkNamesInCartAndCleanCartAbility() {
        SoftAssert softAssert = new SoftAssert();

        String searchText = "Пылесос";
        int firstItemIndex = 1;
        int secondItemIndex = 2;
        //Получаем названия выбранных из поисковой выдачи товаров
        List<String> expItemName =
                mainPage.searchResultsForText(searchText)
                        .getNamesOfItemsByIndex(List.of(firstItemIndex,secondItemIndex));

        //Получаем названия товаров в корзине
        List<String> cartItemsNames =
                searchResultsPage
                        .addItemsInCart(List.of(firstItemIndex,secondItemIndex))
                        .goToCartPage()
                        .getCartItemsNames();

        rightListElemsContainsLeftListElems(softAssert, expItemName, cartItemsNames);

        boolean cartIsEmpty = searchResultsPage.goToCartPage()
                .removeAllItemsFromCart()
                .cartIsEmpty();

        softAssert.assertTrue(cartIsEmpty, "Корзина не пуста!");

        softAssert.assertAll();
    }

    //Атомарные тесты лучше, чем один длинный сценарий
    @Test
    public void namesInCartMatch() {
        SoftAssert softAssert = new SoftAssert();

        String searchText = "Пылесос";
        int firstItemIndex = 1;
        int secondItemIndex = 2;
        //Получаем названия выбранных из поисковой выдачи товаров
        List<String> expItemName =
                mainPage.searchResultsForText(searchText)
                .getNamesOfItemsByIndex(List.of(firstItemIndex,secondItemIndex));

        //Получаем названия товаров в корзине
        List<String> cartItemsNames =
                searchResultsPage
                .addItemsInCart(List.of(firstItemIndex,secondItemIndex))
                .goToCartPage()
                .getCartItemsNames();

        rightListElemsContainsLeftListElems(softAssert, expItemName, cartItemsNames);

        softAssert.assertAll();

    }

    @Test
    public void assertCartClearAllBtnWorks() {
        SoftAssert softAssert = new SoftAssert();

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

    // по "ТЗ" предполагаем что сортировка в порядке добавления,
    // может возникнуть проблема, если названия в корзине не соответствуют порядку добавления
    // во избежании этого можно отсортировать списки перед сравнением
    private void rightListElemsContainsLeftListElems(SoftAssert soft, List<String> rightList, List<String> leftList){
        if (rightList.size() != leftList.size())
            Assert.fail("Lists sizes are not equal!");

        for (int i = 0; i < rightList.size(); i++) {
            soft.assertTrue(rightList.get(i).contains(leftList.get(i)),
                    "Названия товаров не совпадают! \nНазвание в каталоге: " + rightList.get(i)
                            + ".\nНазвание в корзине: " + leftList.get(i));
        }

    }
}
