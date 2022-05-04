package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;

public class MainPage implements IUpperMenu{
    private final SelenideElement SEARCH_FIELD = $x(".//input[@type='search' and not(@id)]").as("Главная страница - поле поиска");
    private final SelenideElement SEARCH_BTN = SEARCH_FIELD.$x("./following-sibling::div//span[contains(concat(' ', @class, ' '), ' ui-input-search__icon_search ')]")
            .as("Главная страница - кнопка поиска (Лупа)");

    @Step("Передаём значение {text} в поле поиска")
    public MainPage setTextToSearchField(String text) {
        SEARCH_FIELD.shouldBe(Condition.visible).sendKeys(text);
        return this;
    }

    @Step("Клик по кнопке 'Лупа'")
    public MainPage clickObSearchBtn() {
        SEARCH_BTN.shouldBe(Condition.enabled).click();
        return this;
    }

    @Step("Поиск по слову {text}")
    public SearchResultsPage searchResultsForText(String text) {
        refresh();
        setTextToSearchField(text)
                .clickObSearchBtn();
        return SearchResultsPage.getPage();
    }




}
