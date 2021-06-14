package homework4.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends AbstractPage {
    private Logger logger = LogManager.getLogger(LoginPage.class);
    private By emailInput = By.xpath("//*[@action='/login/']/div/input[@name='email']");
    private By passwordInput = By.xpath("//*[@action='/login/']/div/input[@name='password']");
    private By enterButton = By.xpath("//div[@class='new-log-reg__body']/form/div/button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public StartPage authorise(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        clearAndSendKeysOfInputByLocator(emailInput, email);
        clearAndSendKeysOfInputByLocator(passwordInput, password);
        driver.findElement(enterButton).click();
        logger.info("You've been logged in");
        return new StartPage(driver);
    }


}
