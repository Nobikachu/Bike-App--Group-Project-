package Model;

import Data.CSVReader;
import junit.framework.TestCase;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * JUnit testing for the retailer class
 */
public class RetailerTest extends TestCase {

    private Retailer retail1, retail2, retail3, retail4;
    private String testCSV;

    /**
     * Set up some retails to test the functionality of the Retailer class"
     *
     * @throws Exception Set up was unsuccessful
     */
    public void setUp() throws Exception {
        super.setUp();
        retail1 = new Retailer(new Location(32.53454, 54.2342423), "Bob games", "3 Sponge Street", "", "New York", "NY", "St Mary", "Sushi", "4532");
        retail2 = new Retailer(new Location(32.53454, 54.2342423), "Bob games", "3 Sponge Street", "", "New York", "NY", "St Mary", "Sushi", "4532");
        retail3 = new Retailer(new Location(32.53374, 54.2342732), "The shop of shops", "6 Church Rd", "2nd floor", "New York", "NY", "Cafe", "", "4532");
        testCSV = (new File("src/test/resources/Data/testRetailers.csv")).getAbsolutePath();

    }

    /**
     * Checks if the CSV constructor is the same as primary constructor.
     *
     * @throws IOException CSV constructor is not the same as primary constructor
     */
    public void testCSVConstructor() throws IOException {
        CSVReader in = new CSVReader();
        CSVParser records = in.readFile(testCSV);
        Retailer retailer = new Retailer(records.getRecords().get(0));
        Retailer expected = new Retailer(new Location(40.7028416, -74.0126922), "Starbucks Coffee", "3 New York Plaza", "", "New York", "NY", "Casual Eating & Takeout", "F-Coffeehouse", "10004");
        assertEquals(expected, retailer);
        in.close();
    }

    /**
     * Checks if the getStreetName method returns the correct street name.
     * Making sure street number and flat number are not included in the street name.\
     *
     * @throws Exception Does not retrieve the street name properly.
     */
    public void testGetStreetName() throws Exception {
        assertEquals("Sponge Street", retail1.getStreetName());
    }

    /**
     * Checks if two separate classes with the same value are recognize as same.
     *
     * @throws Exception Two separate classes with the same values are treated as different.
     */
    public void testEquals() throws Exception {
        assertTrue(retail1.equals(retail2));
        assertTrue(retail2.equals(retail1));
        assertTrue(retail1.equals(retail1));
        assertFalse(retail1.equals(retail3));
        assertFalse(retail1.equals("String"));
        assertFalse(retail1.equals(null));
    }

    /**
     * Checks if two separate classes with the same value, has the same hash code.
     *
     * @throws Exception Two separated classes with same value, does not have the same hash code.
     */
    public void testHashCode() throws Exception {
        assertTrue(retail1.hashCode() == retail2.hashCode());
        assertFalse(retail1.hashCode() == retail3.hashCode());
    }

    /**
     * Checks if toCSV method creates a CSV file with correct format.
     *
     * @throws Exception toCSV method creates a CSV file with incorrect format
     */
    public void testToCSV() throws Exception {
        List<String> retailerAttributes = retail1.toCSV();
        Retailer retailer = new Retailer(
                new Location(Double.parseDouble(retailerAttributes.get(8)), Double.parseDouble(retailerAttributes.get(9))),
                retailerAttributes.get(0), //Name
                retailerAttributes.get(1), //Address line 1
                retailerAttributes.get(2), //Address line 2
                retailerAttributes.get(3), //City
                retailerAttributes.get(4), //State
                retailerAttributes.get(6), //Primary
                retailerAttributes.get(7), //Secondary
                retailerAttributes.get(5)); //Zip

        assertEquals(retail1, retailer);
    }

}