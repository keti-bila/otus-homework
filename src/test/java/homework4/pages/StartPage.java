package homework4.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StartPage extends AbstractPage {
    private static Logger logger = LogManager.getLogger(StartPage.class);
    private By goToLoginButton = By.xpath("//div[@class='header2__auth-container']/button");
    private By nameButton = By.className("header2-menu__item-text__username");
    private By personalPageButton = By.xpath("//a[@title='Личный кабинет']");


    public StartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open start page")
    public static StartPage open(String url, WebDriver driver) {
        driver.get(url);
        logger.info("Start page is opened");
        return new StartPage(driver);
    }

    @Step("Go to login page")
    public LoginPage goToLogin() {
        driver.findElement(goToLoginButton).click();
        logger.info("Login page is opened");
        return new LoginPage(driver);
    }

    @Step("Go to personal page")
    public PersonalPageTopBarElement goToPersonalPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameButton));
        actions.moveToElement(driver.findElement(nameButton)).perform();
        driver.findElement(personalPageButton).click();
        logger.info("Personal page is opened");
        return new PersonalPageTopBarElement(driver);
    }
}
