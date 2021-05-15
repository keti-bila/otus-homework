import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeWork3Test {
    private WebDriver driver;
    private WebDriverWait wait;
    private Logger logger = LogManager.getLogger(HomeWork3Test.class);
    private final String URL = "https://market.yandex.ru/";
    private Actions actions;
    String samsungName = null;
    String xiaomiName = null;
    private int sizeOfCompareList = 0;
    List<WebElement> compareList = null;
    String compareListXpath = "//div[@data-tid='412661c']";
    boolean samsungIsPresent = false;
    boolean xiaomiIsPresent = false;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Driver started");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        actions = new Actions(driver);
    }

    @AfterTest
    public void end() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Driver quited");
    }

    @Test
    public void yandexTest() {
//        Открыть в Chrome сайт Яндекс.Маркет - "Электроника"-> "Смартфоны"
        logger.info("Test started");
        driver.get(URL);
        WebElement buttonElektronika = driver.findElement(By.xpath("//span[contains(text(), 'Электроника')]"));
        buttonElektronika.click();
        WebElement buttonSmartfony = driver.findElement(By.xpath("//a[contains(@href, '/catalog--smartfony/')]"));
        buttonSmartfony.click();
        logger.info("Url is opened");

//        Отфильтровать список товаров: Samsung и Xiaomi
        WebElement checkboxSamsung = driver.findElement(By.xpath("//input[@name='Производитель Samsung']/following-sibling::div"));
        WebElement checkboxXiaomi = driver.findElement(By.xpath("//input[@name='Производитель Xiaomi']/following-sibling::div"));
        actions.moveToElement(checkboxSamsung).perform();
        checkboxSamsung.click();
        checkboxXiaomi.click();
        By loaderLocator = By.xpath("//div[@data-tid='8bc8e36b']");
        waitForElementToBeGone(loaderLocator);

//        Отсортировать список товаров по цене (от меньшей к большей)
        WebElement sortPrice = driver.findElement(By.xpath("//button[@data-autotest-id='dprice']"));
        sortPrice.click();
        waitForElementToBeGone(loaderLocator);
        logger.info("Phones are sorted by brand and price");

//        Добавить первый в списке Samsung
        WebElement firstSamsung = driver.findElement(By.xpath("(//a[contains(@title, 'Samsung')] )[1]"));
        actions.moveToElement(firstSamsung).perform();
        samsungName = driver.findElement(By.xpath("(//a[contains(@title, 'Samsung')] )[1]")).getAttribute("title");
        WebElement addSamsung = driver.findElement(By.xpath("(//a[contains(@title, 'Samsung')] )[1]/parent::h3/parent::div/parent::div/preceding-sibling::div[2]/div[2]/div"));
        addSamsung.click();
        sizeOfCompareList++;

//        Проверить, что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        WebElement addedSamsung = driver.findElement(By.xpath("//*[contains(text(), 'Товар " + samsungName + " добавлен к сравнению')]"));
        wait.until(ExpectedConditions.visibilityOf(addedSamsung));
        logger.info("First Samsung is added");

//        Добавить первый в списке Xiaomi
        WebElement firstXiaomi = driver.findElement(By.xpath("(//a[contains(@title, 'Xiaomi')] )[1]"));
        actions.moveToElement(firstXiaomi).perform();
        xiaomiName = driver.findElement(By.xpath("(//a[contains(@title, 'Xiaomi')] )[1]")).getAttribute("title");
        WebElement addXiaomi = driver.findElement(By.xpath("(//a[contains(@title, 'Xiaomi')] )[1]/parent::h3/parent::div/parent::div/preceding-sibling::div[2]/div[2]/div"));
        addXiaomi.click();
        sizeOfCompareList++;

//        Проверить, что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        WebElement addedXiaomi = driver.findElement(By.xpath("//*[contains(text(), 'Товар " + xiaomiName + " добавлен к сравнению')]"));
        wait.until(ExpectedConditions.visibilityOf(addedXiaomi));
        logger.info("First Xiaomi is added");

//        Перейти в раздел Сравнение
        driver.get("https://market.yandex.ru/compare");

//        Проверить, что в списке товаров 2 позиции
        compareList = driver.findElements(By.xpath(compareListXpath));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(compareList.size(), sizeOfCompareList, "Number of options in compare list is not as expected");
        logger.info("Number of phones in compare list is " + compareList.size());
        containString();
        softAssert.assertTrue(samsungIsPresent, "Required Samsung is not in the list");
        softAssert.assertTrue(xiaomiIsPresent, "Required Xiaomi is not in the list");
        softAssert.assertAll();
        logger.info("All phones are in compare list");
    }

    private void containString() {
        ArrayList<String> phoneList = new ArrayList<>();
        for (int i = 1; i <= compareList.size(); i++) {
            String phone = driver.findElement(By.xpath(compareListXpath + "[" + i + "]" + "/div/a")).getText();
            phoneList.add(phone);
        }
        for (String phoneName : phoneList) {
            if (samsungName.equals(phoneName)) {
                samsungIsPresent = true;
            }
        }
        for (String phoneName : phoneList) {
            if (xiaomiName.equals(phoneName)) {
                xiaomiIsPresent = true;
            }
        }
    }

    private boolean isElementDisplayed(By elementSelector) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, 1);
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(elementSelector));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void waitForElementToBeGone(By elementSelector) {
        if (isElementDisplayed(elementSelector)) {
           wait.until(ExpectedConditions.invisibilityOfElementLocated(elementSelector));
        }
    }
}

