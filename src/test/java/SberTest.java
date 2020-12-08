import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static io.qameta.allure.Allure.step;

public class SberTest {

    @BeforeAll
    static void setup (){
        Configuration.startMaximized = true;
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

       // Configuration.remote = "https://user1:1234@" + System.getProperty("remote.browser.url") + ":4444/wd/hub/";
    }

    @Test
    void searchSber() {
        step("Открываем сайт сбербанка", (step) -> {
            open("https://www.sberbank.ru");
        });
        step("Кликаем на 'Вклады'", (step) -> {
            $x("//*[contains(@aria-label, 'Меню  Вклады')]").click();
        });
        step("В меню выбираем 'Счета'", (step) -> {
            $x("//*[@href= '/ru/person/contributions/special_accounts']").click();
        });
        step("Кликаем на сберегательный счет'", (step) -> {
            $x("//a[@href='/ru/person/contributions/deposits/sb_schet']").click();
        });
        step("Проверяем что мы на странице 'Сберегательный счёт'", (step) -> {
            $x("//*[contains(@class, 'page-teaser-dict__header')]").shouldHave(Condition.exactText("Сберегательный счет"));
        });
    }
    @AfterEach
    @Step("Attachments")
    public void afterEach(){
        Helper.attachScreenshot("Last screenshot");
        Helper.attachPageSource();
        Helper.attachAsText("Browser console logs", Helper.getConsoleLogs());
       // Helper.attachVideo();

        closeWebDriver();
    }
}
