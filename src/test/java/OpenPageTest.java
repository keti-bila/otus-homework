import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenPageTest {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(OpenPageTest.class);

    @Before
    public void start() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @After
    public void end() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void openPageTest() {
        driver.get("https://otus.ru/");
        String title = driver.getTitle();
        Assert.assertEquals("Title is not as expected", "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям", title);
        logger.info("Test starts");
    }
}
