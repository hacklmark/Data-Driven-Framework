package com.w2a.testcases;

import com.w2a.base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

public class BankManangerLoginTest extends TestBase {

    @Test
    public void loginAsBankMananger() throws IOException {

        verifyEquals("abc", "xwz");

        log.debug("Inside Login Test");
        click("bmlBtn_CSS");

        Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login not successful");

        log.debug("Login successfully executed");

        //Reporter.log: Create Report in test-output/html/index.html
        Reporter.log("Login successfully executed");

        Assert.fail("Login not successful");
    }
}
