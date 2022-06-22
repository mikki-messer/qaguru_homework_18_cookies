package com.mikkimesser.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.mikkimesser.configuration.ApiConfig;
import com.mikkimesser.configuration.WebConfig;
import com.mikkimesser.helpers.Attach;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.qameta.allure.Allure.step;

public class TestBase {
    static String demowebshopLogin;
    static String demowebshopPassword;

    @BeforeAll
    @Step("Preliminary setup")
    static void configure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.baseUrl = "http://demowebshop.tricentis.com";
        RestAssured.baseURI = "http://demowebshop.tricentis.com";

        WebConfig webConfig = ConfigFactory.create(WebConfig.class, System.getProperties());

        String remoteWebDriver = webConfig.remoteWebDriver();

        Configuration.browser = webConfig.browser();
        Configuration.browserVersion = webConfig.browserVersion();

        ApiConfig apiConfig = ConfigFactory.create(ApiConfig.class);
        demowebshopLogin = apiConfig.demowebshopLogin();
        demowebshopPassword = apiConfig.demowebshopPassword();

        if (remoteWebDriver != null) {
            step("Remote web driver setup", () -> {
                Configuration.remote = String.format("https://%s:%s@%s/wd/hub",
                        webConfig.selenoidLogin(),
                        webConfig.selenoidPassword(),
                        remoteWebDriver);

                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("enableVNC", true);
                capabilities.setCapability("enableVideo", true);
                Configuration.browserCapabilities = capabilities;
            });
        }
    }

    @AfterEach
    @Step("Saving screenshot, video on remote web driver, page source, and console logs (except of FireFox)")
    void afterEach() {
        String screenshotName;                                // Returns a `String`.
        screenshotName = String.format("Screenshot %s", ZonedDateTime                    // Represent a moment as perceived in the wall-clock time used by the people of a particular region ( a time zone).
                .now(                            // Capture the current moment.
                        ZoneId.of("Europe/Moscow")  // Specify the time zone using proper Continent/Region name. Never use 3-4 character pseudo-zones such as PDT, EST, IST.
                )                                // Returns a `ZonedDateTime` object.
                .format(                         // Generate a `String` object containing text representing the value of our date-time object.
                        DateTimeFormatter.ofPattern("uuuu.MM.dd.HH.mm.ss")
                ));
        Attach.screenshotAs(screenshotName);
        Attach.pageSource();
        Attach.browserConsoleLogs();

        Attach.addVideo(Configuration.remote);

        closeWebDriver();
    }
}
