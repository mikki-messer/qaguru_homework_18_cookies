package com.mikkimesser.tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.mikkimesser.helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class DemoWebShopTests extends TestBase{
    /*
    TODO
    1. Скопировать код Стаса +
    2. Подключить зависимости +
    3. Добиться запуска +
    4. Добавить TestBase, вынести в него baseURL +
    5. Добавить файл пропертис для selenoid +
    6. Добавить конфиг для selenoid +
    7. Добавить файл пропертис для demowebshop +
    8. Добавить конфиг для demowebshop +
    9. Подключить selenoid +
    10. Создать джоб на Дженкинсе
    11. Добавить readme
    12. Посмотреть demowebshop, выбрать cookie
    13. Попробовать запилить тест на куки в корзину
     */

    String authCookieName = "NOPCOMMERCE.AUTH";

    @Test
    @Disabled
    @DisplayName("Successful authorization to some demowebshop (API + UI) with allure listener")
    void loginWithApiAndAllureListenerTest() {
        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .filter(new AllureRestAssured())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", demowebshopLogin)
                    .formParam("Password", demowebshopPassword)
                    .log().all()
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(authCookieName);

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));
            step("Set cookie to to browser", () -> {
                Cookie authCookie = new Cookie(authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
            });
        });

        step("Open main page", () ->
                open(""));
        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(demowebshopLogin)));
    }

    @Test
    @DisplayName("Successful authorization to some demowebshop (API + UI) with custom listener")
    void loginWithApiAndCustomListenerTest() {
        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", demowebshopLogin)
                    .formParam("Password", demowebshopPassword)
                    .log().all()
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(authCookieName);

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));
            step("Set cookie to to browser", () -> {
                Cookie authCookie = new Cookie(authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
            });
        });

        step("Open main page", () ->
                open(""));
        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(demowebshopLogin)));
    }

}
