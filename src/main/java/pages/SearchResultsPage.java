package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;

public class SearchResultsPage implements IUpperMenu{
    private final ElementsCollection SEARCH_RESULTS_BUY_BTN = $$x(".//div[@data-id='product']")
            .as("Результаты поиска");

    private SelenideElement getBuyBtnFromElement(SelenideElement elementWithBuyBtn) {
        return elementWithBuyBtn.$x("./descendant::button[text()='Купить']")
                .as("Кнопка 'Купить' у переданного товара");
    }

    private SelenideElement getNameFromItemAsElement(SelenideElement elementWithBuyBtn) {
        return elementWithBuyBtn.$x("./descendant::a[contains(concat(' ', @class, ' '), ' catalog-product__name ')]/span")
                .as("Наименование у переданного товара");
    }

    private static class SingletonHelper{
        private static final SearchResultsPage INSTANCE = new SearchResultsPage();
    }

    public static SearchResultsPage getPage(){
        return SearchResultsPage.SingletonHelper.INSTANCE;
    }

    @Step("Добавить товар в корзину по индексу")
    public SearchResultsPage addItemInCart(int indexNumberOfItem) {
        if (indexNumberOfItem > SEARCH_RESULTS_BUY_BTN.size() || indexNumberOfItem !=0) {
            getBuyBtnFromElement(
                    SEARCH_RESULTS_BUY_BTN.get(indexNumberOfItem - 1)
                    .as("Товар в результатах поиска с индексом: " + indexNumberOfItem)
                    ).click();
        }
        else
            throw new IllegalStateException("The number of item is greater that result list size or equals zero!");

        return this;
    }

    @Step("Получить имена товаров по индексу")
    public List<String> getNamesOfItemsByIndex(List<Integer> indexNumbersOfItem) {
        List<String> names = new ArrayList<>();
        indexNumbersOfItem.forEach(indexNumberOfItem -> {
            if (indexNumberOfItem > SEARCH_RESULTS_BUY_BTN.size() || indexNumberOfItem !=0) {
                names.add(getNameFromItemAsElement(
                        SEARCH_RESULTS_BUY_BTN.get(indexNumberOfItem - 1)
                                .as("Товар в результатах поиска с индексом: " + indexNumberOfItem)
                ).text());
            }
            else
                throw new IllegalStateException("The number of item is greater that result list size or equals zero!");

        }
        );

        return names;
    }

    public String getNameOfItemByIndex(int indexNumberOfItem) {
        return getNamesOfItemsByIndex(List.of(indexNumberOfItem)).stream().findFirst().orElse("Список наименований пуст!");
    }
}
