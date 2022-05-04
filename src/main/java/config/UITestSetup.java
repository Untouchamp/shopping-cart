package config;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeSuite;

import static config.SetupConfig.UISettings.*;

public class UITestSetup{

    @BeforeSuite(alwaysRun = true)
    public void SetupBeforeSuit(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        Configuration.browserSize = "1920x1080";
        //Тут устанавливается тип драйвера
        Configuration.browser = browserType;
        //Дефолтный таймаут для тестов
        Configuration.timeout = 5000;
        //После завершения тестов оставлять браузер открытым, удобно для отладки
        Configuration.holdBrowserOpen = confHoldBrowserOpen;
        Configuration.screenshots = false;
        Configuration.savePageSource = false;

    }
}
