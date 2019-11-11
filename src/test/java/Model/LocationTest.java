package Model;

import junit.framework.TestCase;

/**
 * Junit testing for the Location class
 */
public class LocationTest extends TestCase {

    private Location location1;
    private Location location2;
    private Location location3;

    /**
     * Set up some test location to check the functionality of the class "Location"
     *
     * @throws Exception Set up was unsuccessful
     */
    public void setUp() throws Exception {
        super.setUp();
        location1 = new Location(-43.516479, 172.564958);
        location2 = new Location(-43.516479, 172.564958);
        location3 = new Location(-43.543607, 172.679650);
    }

    /**
     * Checks if it gets the correct latitude
     *
     * @throws Exception Returns incorrect latitude
     */
    public void testGetLatitude() throws Exception {
        assertEquals(-43.516479, location1.getLatitude());
    }

    /**
     * Checks if it gets the correct longitude
     *
     * @throws Exception returns incorrect longitude
     */
    public void testGetLongitude() throws Exception {
        assertEquals(172.564958, location1.getLongitude());
    }

    /**
     * Checks if the location has the correct coordinates.
     *
     * @throws Exception returns incorrect coordinates
     */
    public void testEquals() throws Exception {
        assertTrue(location1.equals(location2));
        assertTrue(location2.equals(location1));
        assertTrue(location1.equals(location1));
        assertFalse(location1.equals(location3));
        assertFalse(location1.equals(null));
        assertFalse(location1.equals("-43.516479, 172.564958"));
    }

    /**
     * Checks if the location has the correct hash code
     *
     * @throws Exception returns incorrect hash code.
     */
    public void testHashCode() throws Exception {
        assertTrue(location1.hashCode() == location2.hashCode());
        assertFalse(location1.hashCode() == location3.hashCode());
    }

    /**
     * Checks that the calculated distance is within 0.1% of the expected distance. Values are fairly arbitrary right now
     *
     * @throws Exception returns calculated distance outside of expected 0.1%.
     */
    public void testCalculateDistance() throws Exception {
        assertTrue(Math.abs((location1.calculateDistance(location3) / 9.725829598215203) - 1) < 0.001);
    }
}