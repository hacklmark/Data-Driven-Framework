package com.w2a.testcases;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.IOException;

public class BankManangerLoginTest extends TestBase {

    @Test
    public void bankManangerLoginTest() throws IOException, InterruptedException {

        if (!TestUtil.isTestRunnable("bankManangerLoginTest", excel)){
            throw new SkipException("Skipping the test " + "bankManangerLoginTest".toUpperCase() +" as the Run mode is NO");
        }

        verifyEquals("abc", "xwz");
        Thread.sleep(2000);
        log.debug("Inside Login Test");
        click("bmlBtn_CSS");

        Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login not successful");

        log.debug("Login successfully executed");

        //Reporter.log: Create Report in test-output/html/index.html
        Reporter.log("Login successfully executed");

        //Assert.fail("Login not successful");
    }
}
