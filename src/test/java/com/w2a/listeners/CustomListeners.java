package com.w2a.listeners;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;
import com.w2a.utilities.MonitoringMail;
import com.w2a.utilities.TestConfig;
import com.w2a.utilities.TestUtil;
import org.testng.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

//Add this class to listeners in pom.xml
public class CustomListeners extends TestBase implements ITestListener, ISuiteListener {

    public String messageBody;
    public void onTestStart(ITestResult iTestResult) {
        //First Testcase needs to Extent report
        test = rep.startTest(iTestResult.getName().toUpperCase());
        //runmodes - Y
        System.out.println(TestUtil.isTestRunnable(iTestResult.getName(), excel));

    }

    public void onTestSuccess(ITestResult iTestResult) {

        //extent report
        test.log(LogStatus.PASS, iTestResult.getName().toUpperCase()+" PASS");
        rep.endTest(test);
        rep.flush();
    }

    public void onTestFailure(ITestResult iTestResult) {

        //Create a screenshot link in test-output/html/index.html or in target/surefire-reports/html/index.html
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        try {
            TestUtil.captureScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Extent report
        test.log(LogStatus.FAIL, iTestResult.getName().toUpperCase()+" Failed with exception : "+iTestResult.getThrowable());
        test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
        //ReportNG
        Reporter.log("Click to see Screenshot");
        Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
        Reporter.log("<br>");
        Reporter.log("<br>");
        Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" widht=200 height=200></a>");
        rep.endTest(test);
        rep.flush();
    }

    public void onTestSkipped(ITestResult iTestResult) {

        test.log(LogStatus.SKIP, iTestResult.getName().toUpperCase() + " Skipped the test as the Run mode is NO");
        rep.endTest(test);
        rep.flush();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {

    }

    @Override
    public void onStart(ISuite iSuite) {

    }

    @Override
    public void onFinish(ISuite iSuite) {

        MonitoringMail mail = new MonitoringMail();
        try {
            messageBody = InetAddress.getLocalHost().getHostAddress()+":8080/job/DataDrivenLiveProject/Extent_20Reports/";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(messageBody);

        try {
            mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
