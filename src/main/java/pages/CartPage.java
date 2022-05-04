package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class CartPage implements IUpperMenu {

    private final ElementsCollection CART_ITEMS_NAMES = $$x(".//div[@class='cart-items__product-name']/a")
            .as("Страница корзины - наименования товаров в корзине");

    private final SelenideElement SELECT_ALL_CHECKBOX_ACTIVE = $x(".//span[contains(concat(' ', @class, ' '), ' select-all-checkbox__icon_checked ')]")
            .as("Страница корзины - Чекбокс 'Выбрать все' активен");

    private final SelenideElement SELECT_ALL_BTN = $x(".//span[contains(concat(' ', @class, ' '), ' mass-selection__choose-all-title ')]")
            .as("Страница корзины - Кнопка 'Выбрать все'");

    private final SelenideElement REMOVE_CHOSEN_BTN = $x(".//div[contains(concat(' ', @class, ' '), ' mass-selection__delete-btn ')]")
            .as("Страница корзины - Кнопка 'Удалить выбранные'");

    private SelenideElement getEmptyCartText() {
        return $x(".//div[@class='empty-message__title-empty-cart']").as("Страница корзины - Текст 'Корзина пуста'");
    }

    private static class SingletonHelper{
        private static final CartPage INSTANCE = new CartPage();
    }

    public static CartPage getPage(){
        return SingletonHelper.INSTANCE;
    }

    public List<String> getCartItemsNames() {
        return CART_ITEMS_NAMES.texts();
    }

    public Boolean cartIsEmpty() {
        return getEmptyCartText().should(Condition.exist).is(Condition.visible);
    }

    public CartPage removeAllItemsFromCart() {
        if (!SELECT_ALL_CHECKBOX_ACTIVE.exists())
            SELECT_ALL_BTN.click();

        REMOVE_CHOSEN_BTN.shouldBe(Condition.enabled).click();
        return this;
    }

}
