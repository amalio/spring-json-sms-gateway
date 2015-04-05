package com.opteral.springsms;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestHelperTest {

    @Test
    public void tenCharString()
    {
        String testString = TestHelper.genString(10);

        assertEquals(10, testString.length());
    }

    @Test
    public void twoCharString()
    {
        String testString = TestHelper.genString(2);

        assertEquals(2, testString.length());
    }

    @Test
    public void emptyString()
    {
        String testString = TestHelper.genString(0);

        assertTrue(testString.isEmpty());
    }
}
