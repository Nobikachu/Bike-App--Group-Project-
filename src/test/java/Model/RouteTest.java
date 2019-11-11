package Model;

import Data.CSVReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import junit.framework.TestCase;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit testing for the route class
 */
public class RouteTest extends TestCase {

    private Route route1, route2, route3;
    private WiFi wifi1, wifi2, wifi3, wifi4;
    private ObservableList<WiFi> wiFiObservableList = FXCollections.observableArrayList();

    /**
     * Set up some routes to test the functionality of the Retailer class"
     *
     * @throws Exception set up was unsuccessful
     */
    public void setUp() throws Exception {
        super.setUp();
        CSVReader reader = new CSVReader();
        List<CSVRecord> records = reader.readFile((new File("src/test/resources/Data/testRoutes.csv")).getAbsolutePath()).getRecords();
        route1 = new Route(records.get(0));
        route2 = new Route(records.get(0));
        route3 = new Route(records.get(1));
        List<CSVRecord> recordWifi = reader.readFile((new File("src/test/resources/Data/testWifi.csv")).getAbsolutePath()).getRecords();
        wifi1 = new WiFi(recordWifi.get(0));
        wifi2 = new WiFi(recordWifi.get(1));
        wifi3 = new WiFi(recordWifi.get(2));
        wiFiObservableList.addAll(wifi1, wifi2, wifi3);
    }

    /**
     * Checks if toCSV method creates a CSV file with correct format.
     *
     * @throws Exception toCSV method creates a CSV file with incorrect format
     */
    public void testToCSV() throws Exception {
        ArrayList<String> testRecord = route1.toCSV();
        ArrayList<String> test = new ArrayList<>();
        test.add("1015.0");
        test.add("2013-12-01 00:00:03");
        test.add("2013-12-01 00:16:58");
        test.add("401");
        test.add("Allen St & Rivington St");
        test.add("40.72019576");
        test.add("-73.98997825");
        test.add("476");
        test.add("E 31 St & 3 Ave");
        test.add("40.74394314");
        test.add("-73.97966069");
        test.add("14729");
        test.add("Subscriber");
        test.add("1979");
        test.add("2");
        assertEquals(testRecord, test);
    }

    /**
     * Checks if two separate classes with the same value are recognize as same.
     *
     * @throws Exception Two separates classes with same values are treated as different.
     */
    public void testEquals() throws Exception {
        assertTrue(route1.equals(route2));
        assertTrue(route2.equals(route1));
        assertTrue(route1.equals(route1));
        assertFalse(route1.equals(route3));
        assertFalse(route1.equals("String"));
        assertFalse(route1.equals(null));
    }

    /**
     * Checks if two separate classes with the same value, has the same hash code.
     *
     * @throws Exception Two separate classes with the same value, has different hash code.
     */
    public void testHashCode() throws Exception {
        assertTrue(route1.hashCode() == route2.hashCode());
        assertFalse(route1.hashCode() == route3.hashCode());
    }

    /**
     * Checks if it retrieves the correct mid point location.
     *
     * @throws Exception retrieves incorrect mid point location.
     */
    public void testMidPoint() throws Exception {
        Location midPoint = route1.midPoint(route1.getRouteStartStationLatitude(), route1.getRouteEndStationLatitude(), route1.getRouteStartStationLongitude(), route1.getRouteEndStationLongitude());
        Location testPoint = new Location(40.73206956483491, -73.9848203905952);
        assertEquals(testPoint, midPoint);
    }

}