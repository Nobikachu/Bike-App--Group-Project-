package Model;

import Data.CSVReader;
import junit.framework.TestCase;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.util.List;

/**
 * JUnit testing for the WiFi class
 */
public class WiFiTest extends TestCase {

    private WiFi wifi1, wifi2, wifi3;

    /**
     * Set up some retails to test the functionality of the Retailer class"
     *
     * @throws Exception Set up was unsuccessful
     */
    public void setUp() throws Exception {
        super.setUp();
        CSVReader reader = new CSVReader();
        List<CSVRecord> records = reader.readFile((new File("src/test/resources/Data/testWifi.csv")).getAbsolutePath()).getRecords();
        wifi1 = new WiFi(records.get(0));
        wifi2 = new WiFi(records.get(0));
        wifi3 = new WiFi(records.get(1));
        reader.close();
    }

    /**
     * Checks if two separate classes with the same value are recognize as same.
     *
     * @throws Exception Two separates classes with same values are treated as different.
     */
    public void testEquals() throws Exception {
        assertTrue(wifi1.equals(wifi2));
        assertTrue(wifi2.equals(wifi1));
        assertTrue(wifi1.equals(wifi1));
        assertFalse(wifi1.equals(wifi3));
        assertFalse(wifi1.equals("String"));
        assertFalse(wifi1.equals(null));
    }

    /**
     * Checks if two separate classes with the same value, has the same hash code.
     *
     * @throws Exception Two separate classes with the same value, has different hash code.
     */
    public void testHashCode() throws Exception {
        assertTrue(wifi1.hashCode() == wifi2.hashCode());
        assertFalse(wifi1.hashCode() == wifi3.hashCode());
    }

}