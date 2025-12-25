package com.example.demo;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestResultListener.class)
public class VendorComplianceApplicationTests {

    @Test
    public void contextLoads() {
        // empty – required only to trigger TestNG
    }
}
