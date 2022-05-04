package com.kuchukhidze.yandex.other;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import config.UITestSetup;
import org.openqa.selenium.Cookie;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.MainPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;
import static config.SetupConfig.UIEndpoints.baseUrl;

public class SandBox extends UITestSetup {
    MainPage mainPage = new MainPage();

    @BeforeMethod
    public void setUp() {
        open(baseUrl);
    }
    @Test
    public void testtt() {
        SelenideElement se = $x("//span[text() = 'Подтвердите, что запросы отправляли вы, а не робот']");
        String searchText = "Пылесос";
        Set<Cookie> cookies = WebDriverRunner.getWebDriver().manage().getCookies();
        List<String> cookie_names = cookies.stream().map(Cookie::getName).collect(Collectors.toList());
        for (Cookie cookie : cookies) {
            WebDriverRunner.getWebDriver().manage().deleteCookie(cookie);
            open(baseUrl);
            se.shouldBe(Condition.visible.because(cookie.getName()));
        }
    }
}
