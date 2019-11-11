package Data;

import junit.framework.TestCase;

/**
 * JUnit testing for the SyntaxChecker class
 */
public class SyntaxCheckerTest extends TestCase {
    /**
     * Checks if the string only contains alpha
     *
     * @throws Exception String contains characters other than alpha
     */
    public void testIsAlpha() throws Exception {
        assertTrue(SyntaxChecker.isAlpha("Test"));
    }

    /**
     * Checks if the method can correctly identify invalid input, a number."
     *
     * @throws Exception String contains characters other than alpha.
     */
    public void testIsAlpha2() throws Exception {
        assertFalse(SyntaxChecker.isAlpha("123"));
    }

    /**
     * Checks if the string only contains numbers
     *
     * @throws Exception String contains characters other than number
     */
    public void testIsNum() throws Exception {
        assertTrue(SyntaxChecker.isNum("120.5"));
    }

    /**
     * Checks if the it can correctly identify invalid input, non-digits."
     *
     * @throws Exception String isn't a number.
     */
    public void testIsNum2() throws Exception {
        assertFalse(SyntaxChecker.isNum("abc"));
    }

    /**
     * Checks if it can correctly identify invalid input, single deciaml point.
     *
     * @throws Exception String isn't a number.
     */
    public void testIsNum3() throws Exception {
        assertFalse(SyntaxChecker.isNum("."));
    }


    /**
     * Checks if the string is a valid value for latitude
     *
     * @throws Exception String contains invalid value for latitude.
     */
    public void testLatValid() throws Exception {
        assertTrue(SyntaxChecker.latValid("-43.516479"));
        assertFalse(SyntaxChecker.latValid("-180000000"));
    }

    /**
     * Checks if the string is a valid value for longitude
     *
     * @throws Exception String contains invalid value for longitude.
     */
    public void testLongValid() throws Exception {
        assertTrue(SyntaxChecker.longValid("172.564958"));
        assertFalse(SyntaxChecker.longValid("180.00001"));
    }

    /**
     * Checks if the string is either male, female or unknown
     *
     * @throws Exception Contains string other than male, female or unknown.
     */
    public void testCheckGender() throws Exception {
        assertTrue(SyntaxChecker.checkGender("Male"));
        assertTrue(SyntaxChecker.checkGender("Female"));
        assertTrue(SyntaxChecker.checkGender("Unknown"));
        assertFalse(SyntaxChecker.checkGender("Mail"));
    }

}