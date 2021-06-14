package homework4.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PersonalPageTopBarElement extends AbstractPage {
    private Logger logger = LogManager.getLogger(PersonalPageTopBarElement.class);
    private By personalDataButton = By.xpath("//a[@title='О себе']");

    public PersonalPageTopBarElement(WebDriver driver) {
        super(driver);
    }

    public PersonalDataPage openPersonalData() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(personalDataButton));
        driver.findElement(personalDataButton).click();
        logger.info("Personal Data Page is opened");
        return new PersonalDataPage(driver);
    }
}
