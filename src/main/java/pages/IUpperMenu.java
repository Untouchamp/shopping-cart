package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public interface IUpperMenu {
    SelenideElement seCartModal = $x(".//div[@id='app-cart-modal']").as("Всплывающее окно корзины");
    SelenideElement seCartIcon = $x(".//a[@data-commerce-target='CART']").as("Иконка корзины в верхнем меню");

    default CartPage goToCartPage(){
        seCartIcon.shouldBe(visible).click();
        return CartPage.getPage();
    }

    default <T extends IUpperMenu> T modalCartShouldClose(T currentPage) {
        seCartModal.should(disappear);
        return currentPage;
    }

}
