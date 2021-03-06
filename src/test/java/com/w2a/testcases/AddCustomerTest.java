package com.w2a.testcases;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class AddCustomerTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class,dataProvider = "dp")
    public void addCustomerTest(String firstName, String lastName, String postCode, String alerttext, String runmode) throws InterruptedException {

        if (!TestUtil.isTestRunnable("addCustomerTest", excel)){
            throw new SkipException("Skipping the test " + "addCustomerTest".toUpperCase() +" as the Run mode is NO");
        }

        if (!runmode.equals("Y")){
            throw new SkipException("Skipping the test as the Run mode is NO");
        }

        click("addCustBtn_CSS");
        type("firstname_CSS",firstName);
        type("lastname_XPATH",lastName);
        type("postcode_CSS",postCode);
        click("addbtn_CSS");
        Thread.sleep(2000);

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(alerttext));
        alert.accept();

        Thread.sleep(2000);
    }
}

 /*   @Test(dataProviderClass = TestUtil.class,dataProvider = "dp")
    public void addCustomerTest(Hashtable<String,String> data) throws InterruptedException {

        if (!data.get("runmode").equals("Y")){
            throw new SkipException("Skipping the test case as the Run mode for data set is NO");
        }

        click("addCustBtn_CSS");
        type("firstname_CSS",data.get("firstName"));
        type("lastname_XPATH",data.get("lastName"));
        type("postcode_CSS",data.get("postCode"));
        click("addbtn_CSS");
        Thread.sleep(2000);

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
        alert.accept();

        Thread.sleep(2000);
    }*/

