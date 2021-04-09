package com.example.geartrader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)

public class CreateListingActivityTest {


    @Mock
    CreateListingActivity createListingActivity;

    // String lengths are valid for validation
    public String goodTitle = "GoodTitle".trim();
    public String goodSummary = "A good summary".trim();
    public String goodPrice = "600".trim();

    // This title is too small for validation
    public String badTitle = "bad".trim();
    // This summary is too small for validation
    public String badSummary = "bad sum".trim();
    // This summary is too long for validation
    public String badPrice = "999999999.00".trim();

    @Test
    public void good_data_validateCreateListing() {
        boolean result = createListingActivity.validateCreateListing(goodTitle, goodSummary, goodPrice);
        assertTrue(result);
    }

    @Test
    public void bad_title_validateCreateListing() {
        boolean result = createListingActivity.validateCreateListing(badTitle, goodSummary, goodPrice);
        assertFalse(result);
    }
    @Test
    public void bad_summary_validateCreateListing() {
        boolean result = createListingActivity.validateCreateListing(goodTitle, badSummary, goodPrice);
        assertFalse(result);
    }
    @Test
    public void bad_price_validateCreateListing() {
        boolean result = createListingActivity.validateCreateListing(goodTitle, goodSummary, badPrice);
        assertFalse(result);
    }
}