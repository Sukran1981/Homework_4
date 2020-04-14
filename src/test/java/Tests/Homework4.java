package Tests;

import Utilities.BrowserUtils;
import Utilities.Driver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Homework4 {
  private WebDriver driver= Driver.getDriver();
 // private WebDriver driver;



    @Test

    public void days() {
        driver.get("http://samples.gwtproject.org/samples/Showcase/Showcase.html#!CwCheckBox");
        BrowserUtils.wait(2);
        List<WebElement> checkboxes=driver.findElements(By.cssSelector("input[type='checkbox']"));

        List<WebElement> labels=driver.findElements(By.xpath("//input[@type='checkbox']/following-sibling::label"));

        int counter=0;

        while(counter<3) {

    Random random = new Random();

    int checkboxToSelect = random.nextInt(checkboxes.size() - 1);

    checkboxes.get(checkboxToSelect).click();
    System.out.println(checkboxes.get(checkboxToSelect));
    }
    driver.quit();
    }


    }

