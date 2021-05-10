import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class HomeWorkTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Logger logger = LogManager.getLogger(HomeWorkTest.class);
    ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Driver started");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @AfterTest
    public void end() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Driver quited");
    }

    @Test
    public void contactsCheck() {
        driver.get(cfg.homepage());
        WebElement contactButton = driver.findElement(By.cssSelector(".header2_subheader-container__right>div>a:last-child"));
        contactButton.click();
        String actualAdress = driver.findElement(By.xpath("//div[contains(text(),'Адрес')]/following-sibling::div")).getText();
        String expectedAdress = "125167, г. Москва, Нарышкинская аллея., д. 5, стр. 2, тел. +7 499 938-92-02";
        Assert.assertEquals(actualAdress, expectedAdress, "Adress is not as expected");
        logger.info("Address was checked");
        String title = driver.getTitle();
        String expTitle = "Контакты | OTUS";
        Assert.assertEquals(title, expTitle, "Tittle is not as expected");
        logger.info("Title was checked");
    }

    @Test
    public void phoneNumberTest() {
        driver.get("https://msk.tele2.ru/shop/number");
        WebElement searchNumberInput = driver.findElement(By.id("searchNumber"));
        searchNumberInput.sendKeys("97");
        searchNumberInput.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='preloader-icon']")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='preloader-icon']")));
        logger.info("Number was searched");
    }

    @Test
    public void faqTest() {
        driver.get(cfg.homepage());
        WebElement faq = driver.findElement(By.cssSelector(".header2_subheader-container__right>div>a:nth-child(3)"));
        faq.click();
        WebElement question = driver.findElement(By.xpath("//*[.='Где посмотреть программу интересующего курса?']"));
        question.click();
        WebElement answer = driver.findElement(By.xpath("//*[.='Где посмотреть программу интересующего курса?']/following-sibling::div"));
        String answerText = answer.getText();
        String expectedText = "Программу курса в сжатом виде можно увидеть на странице курса после блока с преподавателями. Подробную программу курса можно скачать кликнув на “Скачать подробную программу курса”";
        Assert.assertEquals(answerText, expectedText, "Text in answer is not as expected");
        logger.info("Answer was checked");
    }

    @Test
    public void subscribeTest() {
        driver.get(cfg.homepage());
        WebElement emailInput = driver.findElement(By.className("footer2__subscribe-input"));
        emailInput.sendKeys("dimomer109@laraskey.com");
        WebElement subscribe = driver.findElement(By.className("footer2__subscribe-button"));
        subscribe.click();
        WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("subscribe-modal__success")));
        String expectedText = "Вы успешно подписались";
        Assert.assertEquals(success.getText(), expectedText, "Text is not as expected");
        logger.info("Subscribed successfully");
    }
}
