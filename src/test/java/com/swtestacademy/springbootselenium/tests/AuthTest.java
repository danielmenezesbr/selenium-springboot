package com.swtestacademy.springbootselenium.tests;

import com.swtestacademy.springbootselenium.annotations.LazyAutowired;
import com.swtestacademy.springbootselenium.pages.AuthPage;
import com.swtestacademy.springbootselenium.steps.LoginSteps;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;

public class AuthTest extends BaseTest {
    @LazyAutowired
    private AuthPage authPage;

    @Test    
    public void invalidUserNameInvalidPassword() throws InterruptedException {
        System.out.println("Driver of invalidUserNameInvalidPassword test: " + applicationContext
            .getBean(WebDriver.class));

        authPage.login("");

        System.out.println("Fim. Aguardando 30s");
        Thread.sleep(30000);

        /*loginSteps
            .givenIAmAtLoginPage()
            .whenILogin("onur@swtestacademy.com", "11223344")
            .thenIVerifyInvalidLoginMessage();*/
    }
}
