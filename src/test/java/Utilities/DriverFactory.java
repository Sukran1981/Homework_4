package Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    /**
     * you homework will be improve this method
     * This method return webdriver object based on browser type
     * if you want to use chrome browser,just provide chrome  as sparameter
     * @param browserName
     * @return webdriver object
     */
    public static WebDriver createDriver(String browserName){

        if(browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().version("79.0").setup();
            return new ChromeDriver();
        }else{
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver();
        }
    }
}
