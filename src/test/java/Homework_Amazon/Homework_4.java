package Homework_Amazon;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Homework_4 {

    protected static WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void close() {
        driver.quit();
    }

    @Test
    public void days() {
        driver.get("http://samples.gwtproject.org/samples/Showcase/Showcase.html#!CwCheckBox");
        List<WebElement> list = driver.findElements(By.cssSelector(".gwt-CheckBox>label"));
        List<WebElement> checkboxes = driver.findElements(By.cssSelector(".gwt-CheckBox>input"));
        Random random = new Random();

        int count = 0;
        while (count < 3) {
            //this method will return any value between 0 and 7
            int index = random.nextInt(list.size());

            if (checkboxes.get(index).isEnabled()) {
                list.get(index).click();
                if (list.get(index).getText().equals("Friday")) {
                    count++;
                }
                System.out.println(list.get(index).getText());
                list.get(index).click();
            }
        }
    }

    @Test
    public void todaysDate() {
        driver.get("http://practice.cybertekschool.com/dropdown");
        WebElement year = driver.findElement(By.id("year"));
        WebElement month = driver.findElement(By.id("month"));
        WebElement day = driver.findElement(By.id("day"));

        Select y = new Select(year);
        Select m = new Select(month);
        Select d = new Select(day);

        String year_value = y.getFirstSelectedOption().getText();
        String month_value = m.getFirstSelectedOption().getText();
        String day_value = d.getFirstSelectedOption().getText();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMMMdd");
        Assert.assertEquals(year_value + month_value + day_value, sf.format(new Date()));
    }


    @Test
    public void years_months_days() {

        driver.get("http://practice.cybertekschool.com/dropdown");
        WebElement year = driver.findElement(By.id("year"));
        WebElement month = driver.findElement(By.id("month"));
        WebElement day = driver.findElement(By.id("day"));

        Select y = new Select(year);
        Select m = new Select(month);
        Select d = new Select(day);

        Random random = new Random();
        int index = random.nextInt(y.getOptions().size());
        y.selectByIndex(index);

        List<String> months31 = new ArrayList<>(Arrays.asList(new String[]{"January", "March", "May", "July", "August", "October", "December"}));

        int febDays;

        int yearValue = Integer.parseInt(y.getFirstSelectedOption().getText());

        if (yearValue % 400 == 0 || (yearValue % 4 == 0 && yearValue % 100 != 0)) {
            febDays = 29;
        } else {
            febDays = 28;

            for (int i = 0; i < 12; i++) {

                m.selectByIndex(i);

                if (months31.contains(m.getFirstSelectedOption().getText())) {
                    Assert.assertEquals(d.getOptions().size(), 31);

                } else if (m.getFirstSelectedOption().getText().equals("February")) {
                    Assert.assertEquals(d.getOptions().size(), febDays);

                } else {
                    Assert.assertEquals(d.getOptions().size(), 30);
                }
            }
        }
    }

    @Test
    public void department() {

        driver.get("https://www.amazon.com/");
        Assert.assertEquals(driver.findElement(By.className("nav-search-label")).getText(), "All");

        List<WebElement> list = new Select(driver.findElement(By.id("searchDropdownBox"))).getOptions();
        boolean notAlphabeticalOrder = false;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getText().compareTo(list.get(i + 1).getText()) > 0) {
                notAlphabeticalOrder = true;
                break;
            }

        }
        Assert.assertTrue(notAlphabeticalOrder);
    }


    @Test
    public void main_departments() {

        driver.get("https://www.amazon.com/gp/site-directory");
        List<WebElement> mainDepartments = driver.findElements(By.tagName("h2"));

        List<WebElement> allDepartment = new Select(driver.findElement(By.id("searchDropdownBox"))).getOptions();

        Set<String> mainDepart = new HashSet<>();
        Set<String> allDeparts = new HashSet<>();

        for (WebElement each : mainDepartments) {
            mainDepart.add(each.getText());

        }
        for (WebElement each : allDepartment) {
            allDeparts.add(each.getText());
        }
        for (String each : mainDepart) {
            if (!allDeparts.contains(each)) {
                System.out.println(each);
                System.out.println("This main department is not in all department this");
            }
        }
        Assert.assertTrue(allDepartment.containsAll(mainDepart));
    }

    private List<WebElement> getWebElements(WebElement searchDropdownBox) {
        return (List<WebElement>) new Select(searchDropdownBox);
    }

    @Test
    public void links() {

        driver.get("https://www.w3schools.com/");
        List<WebElement> list1 = driver.findElements(By.tagName("a"));

        for (WebElement each : list1) {
            if (each.isDisplayed()) {
                System.out.println(each.getText());
                System.out.println(each.getAttribute("href"));
            }
        }
    }

    @Test

    public void valid_links() {
        driver.get("https://www.selenium.dev/documentation/en/");
        List<WebElement> list1 = driver.findElements(By.tagName("a"));


        for (int i = 0; i < list1.size(); i++) {
            String href = list1.get(i).getAttribute("href");
            try {
                URL url = new URL(href);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.connect();
                Assert.assertEquals(httpURLConnection.getResponseCode(), 200);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void cart() {
        driver.get("https://amazon.com");

        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("wooden spoon");
        driver.findElement(By.xpath("//span[@id='nav-search-submit-text']/following-sibling::input")).click();
        List<WebElement> price = driver.findElements(By.xpath("//span[@class='a-price']/span[@class='a-offscreen']"));

        int x = new Random().nextInt(price.size());
        x = x == 0 ? 1 : x;

        String originName = driver.findElement(By.xpath("//span[@class='a-size-base-plus a-color-base a-text-normal'])[" + x + "]")).getText();

        String originPrice = "$" +
                driver.findElement(By.xpath("//span[@classs='a-price']/span[2]/span[2])[" + x + "]")).getText() + "." +
                driver.findElement(By.xpath("//span[@classs='a-price']/span[2]/span[3])[" + x + "]")).getText();

        driver.findElement(By.xpath("(//span[@class='a-price-fraction'])[" + x + "]")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Qty:']/following-sibling::span")).getText(), "1");

        Assert.assertEquals(driver.findElement(By.id("productTitle")).getText(), originName);
        Assert.assertEquals(driver.findElement(By.id("price_inside_buybox")).getText(), originPrice);

        Assert.assertTrue(driver.findElement(By.id("add-to-cart-button")).isDisplayed());
    }


    @Test

    public void prime() {

        driver.get("https://amazon.com");

        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("wooden spoon");
        driver.findElement(By.xpath("//span[@id='nav-search-submit-text']/following-sibling::input")).click();

        WebElement firstPrimeName = driver.findElement(By.xpath("(//i[@aria-label='Amazon Prime']/../../../../../..//h2)[1]"));

        String name1 = firstPrimeName.getText();
        driver.findElement(By.xpath("//i[@class='a-icon a-icon-prime a-icon-medium']/../div/label/i")).click();
        String name2 = driver.findElement(By.xpath("(//i[@aria-label='Amazon Prime']/../../../../../..//h2)[1]")).getText();

        Assert.assertEquals(name2, name1);
        driver.findElement(By.xpath("//div[@id='brandsRefinements']//ul/li[last()]//i")).click();
        String name3 = driver.findElement(By.xpath("(//i[@aria-label='Amazon Prime']/../../../../../..//h2)[1]")).getText();


        System.out.println(name1);
        System.out.println(name2);
        System.out.println(name3);
        Assert.assertNotEquals(name1, name3);
    }

    @Test
    public void more_spoons() {

        driver.get("https://amazon.com");

        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("wooden spoon");
        driver.findElement(By.xpath("//span[@id='nav-search-submit-text']/following-sibling::input")).click();
        List<WebElement> l1 = driver.findElements(By.xpath("//div[@id='brandsRefinements']//ul/li/span/a/span"));

        List<String> s1 = new ArrayList<>();
        for (WebElement each : l1) {
            s1.add(each.getText());
        }
        driver.findElement(By.xpath("//i[@class='a-icon a-icon-prime a-icon-medium']/../div/label/i")).click();
        List<WebElement> l2 = driver.findElements(By.xpath("//div[@id='brandsRefinements']//ul/li/span/a/span"));
        List<String> s2 = new ArrayList<>();
        for (WebElement each : l2) {
            s2.add(each.getText());
        }
        Assert.assertEquals(s1, s2);

    }
}



