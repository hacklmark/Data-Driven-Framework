package com.w2a.base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    /**
     *  It will be initialized:
     *  WebDriver
     *  Properties
     *  Logs - log4j jar, .log, log4j.properties, Logger
     *  ExtentReports
     *  DB
     *  Excel
     *  Mail
     *  ReportNG, ExtentReports
     *  Jenkins
     *
     */

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger("devpinoyLogger");
    public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata.xlsx");
    public static WebDriverWait wait;
    public ExtentReports rep = ExtentManager.getInstance();
    public static ExtentTest test;

    @BeforeSuite
    public void setUp(){

        if (driver==null){

            //either with PropertyConfigurator or in pom.xml configure log4j
            //PropertyConfigurator.configure(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\log4j.properties");
            try {
                fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                config.load(fis);
                log.debug("Config file loaded !!!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                OR.load(fis);
                log.debug("OR file loaded !!!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(config.getProperty("browser").equals("firefox")){
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\geckodriver.exe");
                driver = new FirefoxDriver();
                log.debug("Firefox Launched !!!");
            }else if(config.getProperty("browser").equals("chrome")){
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
                driver = new ChromeDriver();
                log.debug("Chrome Launched !!!");
            }else if(config.getProperty("browser").equals("ie")){
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                log.debug("Internet Explorer Launched !!!");
            }

            driver.get(config.getProperty("testsiteurl"));
            log.debug("Navigated to : " + config.getProperty("testsiteurl"));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 5);
        }
    }

    public void click(String locator){

        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        }else if (locator.endsWith("_XPATH")){
            driver.findElement(By.xpath(OR.getProperty(locator))).click();
        }else if (locator.endsWith("_ID")){
            driver.findElement(By.id(OR.getProperty(locator))).click();
        }
        test.log(LogStatus.INFO, "Clicking on : " + locator);
    }

    public void type(String locator, String value){

        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        }else if (locator.endsWith("_XPATH")){
            driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        }else if (locator.endsWith("_ID")){
            driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        }
        test.log(LogStatus.INFO, "Typing on : "+locator+" entered value as "+value);
    }

    static WebElement dropdown;

    public void select(String locator, String value){
        if (locator.endsWith("_CSS")) {
            dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        }else if (locator.endsWith("_XPATH")){
            dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
        }else if (locator.endsWith("_ID")){
            dropdown = driver.findElement(By.id(OR.getProperty(locator)));
        }

        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
        test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);
    }

    public boolean isElementPresent(By by){

        try {
            driver.findElement(by);
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public static void verifyEquals(String expected, String actual) throws IOException {
        try {
            Assert.assertEquals(actual, expected);
        }catch (Throwable t){
            TestUtil.captureScreenshot();
            //ReportNG
            System.setProperty("org.uncommons.reportng.escape-output", "false");
            Reporter.log("<br>"+"Verification failure: "+t.getMessage()+"<br>");
            Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" widht=200 height=200></a>");
            Reporter.log("<br>");
            Reporter.log("<br>");
            //Extent Report
            test.log(LogStatus.FAIL, "Verification failed with exception : "+t.getMessage());
            test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
        }
    }


    @AfterSuite
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }

        log.debug("Test execution completed !!!");
    }
}