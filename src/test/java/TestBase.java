import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import config.BrowserstackConfig;
import config.EmulationConfig;
import drivers.BrowserstackMobileDriver;
import drivers.EmulationDriver;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.Attach.sessionId;
import static io.qameta.allure.Allure.step;

public class TestBase {

    static BrowserstackConfig browserstackConfig = ConfigFactory.create(BrowserstackConfig.class, System.getProperties());
    static EmulationConfig emulationConfig = ConfigFactory.create(EmulationConfig.class, System.getProperties());

    @BeforeAll
    public static void setup() {

        String deviceHost = System.getProperty("deviceHost", "emulation"); //устанавливаем дефолтное значение

        /*if (browserstackConfig.deviceHost() == "browserstack") {
            Configuration.browser = BrowserstackMobileDriver.class.getName();
        } else {
            Configuration.browser = EmulationDriver.class.getName();
        }*/

        if (Objects.equals("deviceHost", "emulation")) {
            Configuration.browser = EmulationDriver.class.getName();
        } else {
            Configuration.browser = BrowserstackMobileDriver.class.getName();
        }

        Configuration.browserSize = null;
    }


    @BeforeEach
    public void startDriver() {
        addListener("AllureSelenide", new AllureSelenide());

        open();
    }

    @AfterEach
    public void afterEach() {
        String deviceHost = System.getProperty("deviceHost", "emulation");
        String sessionId = sessionId();

        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();

        step("Close driver", Selenide::closeWebDriver);

        if (Objects.equals("deviceHost", "browserstack")) {
            Attach.video(sessionId);
        }

    }
}