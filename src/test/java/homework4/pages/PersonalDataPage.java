package homework4.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PersonalDataPage extends AbstractPage {
    private Logger logger = LogManager.getLogger(PersonalDataPage.class);
    private static final String VALUE_ATTRIBUTE = "value";

    private By firstName = By.name("fname");
    private By lastName = By.name("lname");
    private By firstNameLatin = By.name("fname_latin");
    private By lastNameLatin = By.name("lname_latin");
    private By blogName = By.name("blog_name");
    private By dateOfBirth = By.name("date_of_birth");
    private By country = By.cssSelector(".js-lk-cv-dependent-master>label>div");
    private By city = By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)");
    private By englishLevel = By.xpath("//input[@name='english_level']/following-sibling::div");
    private By firstContact = By.xpath("//input[@name='contact-0-service']/following-sibling::div");
    private By setFirstContactValue = By.name("contact-0-value");
    private By addSecondContact = By.xpath("//button[contains(text(), 'Добавить')]");
    private By secondContact = By.xpath("//input[@name='contact-1-service']/following-sibling::div");
    private By setSecondContactValue = By.name("contact-1-value");
    private By saveButton = By.xpath("//button[@title='Сохранить и продолжить']");

    public PersonalDataPage(WebDriver driver) {
        super(driver);
    }

    public String getFirstName() {
        return driver.findElement(this.firstName).getAttribute(VALUE_ATTRIBUTE);
    }

    public String getLastName() {
        return driver.findElement(this.lastName).getAttribute(VALUE_ATTRIBUTE);
    }

    public String getFirstNameLatin() {
        return driver.findElement(firstNameLatin).getAttribute(VALUE_ATTRIBUTE);
    }

    public String getLastNameLatin() {
        return driver.findElement(lastNameLatin).getAttribute(VALUE_ATTRIBUTE);
    }

    public String getBlogName() {
        return driver.findElement(blogName).getAttribute(VALUE_ATTRIBUTE);
    }

    public String getDateOfBirth() {
        return driver.findElement(dateOfBirth).getAttribute(VALUE_ATTRIBUTE);
    }

    public String getCountry() {
        return driver.findElement(country).getText();
    }

    public String getCity() {
        return driver.findElement(city).getText();
    }

    public String getEnglishLevel() {
        return driver.findElement(englishLevel).getText();
    }

    public String getFirstContact() {
        return driver.findElement(setFirstContactValue).getAttribute(VALUE_ATTRIBUTE);
    }

    public String getSecondContact() {
        return driver.findElement(setSecondContactValue).getAttribute(VALUE_ATTRIBUTE);
    }

    public PersonalDataPage setFirstName(String firstName) {
        clearAndSendKeysOfInputByLocator(this.firstName, firstName);
        logger.info("First name was set");
        return this;
    }

    public PersonalDataPage setLastName(String lastName) {
        clearAndSendKeysOfInputByLocator(this.lastName, lastName);
        logger.info("Last name was set");
        return this;
    }

    public PersonalDataPage setFirstNameLatin(String firstNameLatin) {
        clearAndSendKeysOfInputByLocator(this.firstNameLatin, firstNameLatin);
        logger.info("First name latin was set");
        return this;
    }

    public PersonalDataPage setLastNameLatin(String lastNameLatin) {
        clearAndSendKeysOfInputByLocator(this.lastNameLatin, lastNameLatin);
        logger.info("Last name latin was set");
        return this;
    }

    public PersonalDataPage setBlogName(String blogName) {
        clearAndSendKeysOfInputByLocator(this.blogName, blogName);
        logger.info("Blog name was set");
        return this;
    }

    public PersonalDataPage setDateOfBirth(String dateOfBirth) {
        clearAndSendKeysOfInputByLocator(this.dateOfBirth, dateOfBirth);
        logger.info("Date of birth was set");
        return this;
    }

    public PersonalDataPage setCountry(String country) {
        driver.findElement(this.country).click();
        By chooseCountry = By.xpath("//button[@title='" + country + "']");
        driver.findElement(chooseCountry).click();
        logger.info("Country was set");
        return this;
    }

    public PersonalDataPage setCity(String city) {
        actions.moveToElement(driver.findElement(this.city)).perform();
        driver.findElement(this.city).click();
        By chooseCity = By.xpath("//button[@title='" + city + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(chooseCity));
        driver.findElement(chooseCity).click();
        logger.info("City was set");
        return this;
    }

    public PersonalDataPage setEnglishLevel(String englishLevel) {
        driver.findElement(this.englishLevel).click();
        By chooseEnglishLevel = By.xpath("//button[@title='" + englishLevel + "']");
        driver.findElement(chooseEnglishLevel).click();
        logger.info("English level was set");
        return this;
    }

    public PersonalDataPage setFirstContact(String typeOfFirstContact, String firstContact) {
        actions.moveToElement(driver.findElement(this.firstContact)).build().perform();
        driver.findElement(this.firstContact).click();
        By chooseFirstContact = By.xpath("(//button[@data-value='" + typeOfFirstContact + "'])[1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(chooseFirstContact));
        actions.moveToElement(driver.findElement(chooseFirstContact)).build().perform();
        driver.findElement(chooseFirstContact).click();
        clearAndSendKeysOfInputByLocator(setFirstContactValue, firstContact);
        logger.info("First contact was set");
        return this;
    }

    public PersonalDataPage setSecondContact(String typeOfSecondContact, String secondContact) {
        driver.findElement(addSecondContact).click();
        driver.findElement(this.secondContact).click();
        By chooseSecondContact = By.xpath("(//button[@data-value='" + typeOfSecondContact + "'])[2]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(chooseSecondContact));
        driver.findElement(chooseSecondContact).click();
        clearAndSendKeysOfInputByLocator(setSecondContactValue, secondContact);
        logger.info("Second contact was set");
        return this;
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
        logger.info("Personal data was saved");
    }
}
