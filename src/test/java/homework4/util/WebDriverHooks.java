package homework4.util;

import homework4.ServerConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverHooks {
    private WebDriver driver;
    private Logger logger = LogManager.getLogger(WebDriverHooks.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class, System.getProperties(), System.getenv());

    public void setDriver() {
        this.driver = WebDriverFactory.createDriver(/*Browsers.getBrowserByString(cfg.browser())*/);
        logger.info("Driver is initialised");
        if (this.driver !=null) {
            this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            this.driver.manage().window().maximize();
        }
    }

    public void shutDownDriver() {
        if (this.driver != null) {
            this.driver.quit();
            logger.info("Browser is closed");
        }
    }

    public void restartDriver() {
        this.shutDownDriver();
        this.setDriver();
    }

    public WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver should be initialised");
        }
        return driver;
    }

    public void clearCookies() {
        if(driver == null) {
            throw new IllegalStateException("Driver should be initialised");
        }
        driver.manage().deleteAllCookies();
        logger.info("All cookies are deleted");
    }
}
