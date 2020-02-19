package com.w2a.rough;

import com.w2a.utilities.MonitoringMail;
import com.w2a.utilities.TestConfig;

import javax.mail.MessagingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestHostAddress {

    public static void main(String[] args) throws UnknownHostException, MessagingException {
        MonitoringMail mail = new MonitoringMail();
        String messageBody = InetAddress.getLocalHost().getHostAddress()+":8080/job/DataDrivenLiveProject/Extent_20Reports/";
        System.out.println(messageBody);

        mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
    }
}
