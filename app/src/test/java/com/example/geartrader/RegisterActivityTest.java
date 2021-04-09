package com.example.geartrader;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class RegisterActivityTest {

    private RegisterActivity registerActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        registerActivity = new RegisterActivity();
        Context context;
    }

    // String lengths are valid for validation
    public String goodUsername = "TestUser".trim();
    public String goodPass = "TestPassword".trim();
    public String goodEmail = "TestUser@mail.com".trim();

    // This title is too small for validation
    public String badUsername = "TestUsernameIsTooLongToBeValid".trim();
    // This summary is too small for validation
    public String badPass = "123".trim();
    // This email does not contain the required @ symbol
    public String badEmail = "ThisEmailHasNoAtSymbol".trim();
    // This email is to short to be valid
    public String badEmailTwo = "@mail".trim();

    @Test
    public void good_data_validateCreateUser() {
        boolean result = registerActivity.validateCreateUser(goodUsername, goodPass, goodEmail);
        assertTrue(result);
    }

    @Test
    public void bad_username_validateCreateListing() {
        boolean result = registerActivity.validateCreateUser(badUsername, goodPass, goodEmail);
        assertFalse(result);
    }

    @Test
    public void bad_pass_validateCreateListing() {
        boolean result = registerActivity.validateCreateUser(goodUsername, badPass, goodEmail);
        assertFalse(result);
    }

    @Test
    public void bad_email_validateCreateListing() {
        boolean result = registerActivity.validateCreateUser(goodUsername, goodPass, badEmail);
        assertFalse(result);
    }

    @Test
    public void bad_emailtwo_validateCreateListing() {
        boolean result = registerActivity.validateCreateUser(goodUsername, goodPass, badEmailTwo);
        assertFalse(result);
    }
}

