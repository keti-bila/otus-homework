package homework4;

import homework4.pages.PersonalDataPage;
import homework4.pages.StartPage;
import homework4.util.BaseHooks;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.nio.charset.Charset;

@Epic("Personal page data testing")
@Feature("Login personal page and entering personal data")
public class Homework4Test extends BaseHooks {
    private static final Logger LOGGER = LogManager.getLogger(Homework4Test.class);

    @Test(description = "Login https://otus.ru/, enter personal data, verify data is saved correctly")
    @Story("Login personal page with valid data and entering personal data")
    @Description("Login https://otus.ru/, enter personal data, verify data is saved correctly")
    public void personalDataPageTest() {
        LOGGER.debug("The default logging level {}", Charset.defaultCharset().displayName());
        PersonalDataPage personalDataPage = StartPage.open(getCfg().homepage(), getWebDriverHooks().getDriver())
                .goToLogin()
                .authorise(getCfg().email(), getCfg().password())
                .goToPersonalPage()
                .openPersonalData();

        personalDataPage.setFirstName("Катя").setLastName("Смит")
                .setFirstNameLatin("Kate").setLastNameLatin("Smith")
                .setBlogName("KateKate").setDateOfBirth("05.02.1990")
                .setCountry("Украина").setCity("Киев")
                .setEnglishLevel("Средний (Intermediate)")
                .setFirstContact("viber", "Kate")
                .setSecondContact("telegram", "Kate")
                .clickSave();

        getWebDriverHooks().restartDriver();

        PersonalDataPage personalDataPageReload = StartPage.open(getCfg().homepage(), getWebDriverHooks().getDriver())
                .goToLogin()
                .authorise(getCfg().email(), getCfg().password())
                .goToPersonalPage()
                .openPersonalData();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(personalDataPageReload.getFirstName(), "Катя", "First name is not as expected");
        softAssert.assertEquals(personalDataPageReload.getLastName(), "Смит", "Last name is not as expected");
        softAssert.assertEquals(personalDataPageReload.getFirstNameLatin(), "Kate", "First name latin is not as expected");
        softAssert.assertEquals(personalDataPageReload.getLastNameLatin(), "Smith", "Last name latin is not as expected");
        softAssert.assertEquals(personalDataPageReload.getBlogName(), "KateKate", "Blog name is not as expected");
        softAssert.assertEquals(personalDataPageReload.getDateOfBirth(), "05.02.1990", "date of birth is not as expected");
        softAssert.assertEquals(personalDataPageReload.getCountry(), "Украина", "Country is not as expected");
        softAssert.assertEquals(personalDataPageReload.getCity(), "Киев", "City is not as expected");
        softAssert.assertEquals(personalDataPageReload.getEnglishLevel(), "Средний (Intermediate)", "English level is not as expected");
        softAssert.assertEquals(personalDataPageReload.getFirstContact(), "Kate", "First contact is not as expected");
        softAssert.assertAll();
        LOGGER.info("Test1");
    }
}
