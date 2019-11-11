package Model;

import Data.CSVReader;
import junit.framework.TestCase;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;

/**
 * JUnit testing for the BikeStation class
 */
public class BikeStationTest extends TestCase {
    private BikeStation bikeStation1;
    private String testCSV;

    /**
     * Set up some retails to test the functionality of the Retailer class"
     *
     * @throws Exception Set up was unsuccessful
     */
    public void setUp() throws Exception {
        super.setUp();
        bikeStation1 = new BikeStation(new Location(40.76727216, -73.99392888), "W 52 St & 11 Ave");
        testCSV = (new File("src/test/resources/Data/testBikeStation.csv")).getAbsolutePath();
    }

    /**
     * Check if the constructor can accurately create a bike station using a record from a bike station CSV.
     *
     * @throws IOException CSV file does not exist.
     */
    public void testCSVConstructor() throws IOException {
        CSVReader in = new CSVReader();
        CSVParser records = in.readFile(testCSV);
        BikeStation createdBikeStation = new BikeStation(records.getRecords().get(0));
        BikeStation expected = bikeStation1;
        assertEquals(expected, createdBikeStation);
        in.close();
    }

}
