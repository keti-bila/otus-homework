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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeWork3Test {
    private WebDriver driver;
    private WebDriverWait wait;
    private final Logger logger = LogManager.getLogger(HomeWork3Test.class);
    private static final String URL = "https://market.yandex.ru/";
    private Actions actions;

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
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Driver quited");
    }

    @Test
    public void yandexTest() {
        String samsungBrand = "Samsung";
        String xiaomiBrand = "Xiaomi";
        logger.info("Test started");
        openMarketGoToSmartphones();
        filterByBrandSortByPriceAscending(Arrays.asList(samsungBrand, xiaomiBrand));
        String samsungName = addFirstPhoneOfBrandToCompareList(samsungBrand);
        String xiaomiName = addFirstPhoneOfBrandToCompareList(xiaomiBrand);
        driver.get(URL + "compare");
        verifyCompareListHasAddedPositions(Arrays.asList(samsungName, xiaomiName));
    }

    private void openMarketGoToSmartphones() {
        driver.get(URL);
        WebElement buttonElektronika = driver.findElement(By.xpath("//span[contains(text(), 'Электроника')]"));
        buttonElektronika.click();
        WebElement buttonSmartfony = driver.findElement(By.xpath("//a[contains(@href, '/catalog--smartfony/')]"));
        buttonSmartfony.click();
        logger.info("Url is opened");
    }

    private void filterByBrandSortByPriceAscending(List<String> brandNames) {
        for (String brandName : brandNames) {
            WebElement brandCheckbox = driver.findElement(By.xpath("//input[@name='Производитель " + brandName + "']/following-sibling::div"));
            actions.moveToElement(brandCheckbox).perform();
            brandCheckbox.click();
        }
        By loaderLocator = By.xpath("//div[@data-tid='8bc8e36b']");
        waitForElementToBeGone(loaderLocator);
        WebElement sortPrice = driver.findElement(By.xpath("//button[@data-autotest-id='dprice']"));
        sortPrice.click();
        waitForElementToBeGone(loaderLocator);
        logger.info("Phones are sorted by brand and price");
    }

    private String addFirstPhoneOfBrandToCompareList(String brandName) {
        WebElement firstPhone = driver.findElement(By.xpath("(//a[contains(@title, '" + brandName + "')] )[1]"));
        actions.moveToElement(firstPhone).perform();
        String phoneName = driver.findElement(By.xpath("(//a[contains(@title, '" + brandName + "')] )[1]")).getAttribute("title");
        WebElement addPhone = driver.findElement(By.xpath("(//a[contains(@title, '" + brandName + "')] )[1]/parent::h3/parent::div/parent::div/preceding-sibling::div[2]/div[2]/div"));
        addPhone.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Товар " + phoneName + " добавлен к сравнению')]")));
        logger.info("First {} is added", brandName);
        return phoneName;
    }

    private void verifyCompareListHasAddedPositions(List<String> phoneNames) {
        String compareListXpath = "//div[@data-tid='412661c']";
        List<WebElement> compareList = driver.findElements(By.xpath(compareListXpath));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(compareList.size(), 2, "Number of options in compare list is not as expected");
        logger.info("Number of phones in compare list is " + compareList.size());
        ArrayList<String> phoneList = new ArrayList<>();
        for (int i = 1; i <= compareList.size(); i++) {
            String phone = driver.findElement(By.xpath(compareListXpath + "[" + i + "]" + "/div/a")).getText();
            phoneList.add(phone);
        }
        for (String phoneName : phoneNames) {
            softAssert.assertTrue(isInList(phoneList, phoneName), "Required " + phoneName + " is not in the list");
        }
        softAssert.assertAll();
        logger.info("All phones are in compare list");
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

    private boolean isInList(ArrayList<String> list, String name) {
        for (String phoneName : list) {
            if (name.equals(phoneName)) {
                return true;
            }
        }
        return false;
    }
}

