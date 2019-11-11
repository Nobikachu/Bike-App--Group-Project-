package Data;

import junit.framework.TestCase;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.util.List;

/**
 * JUnit Testing for the CSVReader Class
 */
public class CSVReaderTest extends TestCase {
    CSVReader reader = new CSVReader();

    /**
     * Checks if theCSV reader reads the header in a correct format.
     *
     * @throws Exception CSV reader reads the header in a incorrect format.
     */
    public void testReadFile() throws Exception {
        List<CSVRecord> records = reader.readFile((new File("src/test/resources/Data/testWifi.csv")).getAbsolutePath()).getRecords();
        CSVRecord test = records.get(0);
        String check = "CSVRecord [comment=null, mapping={OBJECTID=0, the_geom=1, BORO=2, TYPE=3, PROVIDER=4, NAME=5, LOCATION=6, LAT=7, LON=8, X=9, Y=10, LOCATION_T=11, REMARKS=12, CITY=13, SSID=14, SOURCEID=15, ACTIVATED=16, BOROCODE=17, BORONAME=18, NTACODE=19, NTANAME=20, COUNDIST=21, POSTCODE=22, BOROCD=23, CT2010=24, BOROCT2010=25, BIN=26, BBL=27, DOITT_ID=28}, recordNumber=1, values=[998, POINT (-73.99403913047428 40.745968480330795), MN, Free, LinkNYC - Citybridge, mn-05-123662, 179 WEST 26 STREET, 40.745968, -73.994039, 985901.695307, 211053.130644, Outdoor Kiosk, Tablet Internet -phone , Free 1 GB Wi-FI Service, New York, LinkNYC Free Wi-Fi, LINK-008695, 01/18/2017 12:00:00 AM +0000, 1, Manhattan, MN17, Midtown-Midtown South, 3, 10001, 105, 95, 1009500, 0, 0, 1425]]";
        assertEquals(check, test.toString());
    }

}