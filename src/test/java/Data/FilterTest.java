package Data;

import Model.Location;
import Model.Retailer;
import Model.Route;
import Model.WiFi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;

/**
 * JUnit testing for the Filter Clas
 */
public class FilterTest extends TestCase {

    WiFi testWifi1;
    WiFi testWifi2;
    WiFi testWifi3;
    WiFi testWifi4;
    WiFi testWifi5;
    WiFi testWifi6;
    Retailer testRetailer1;
    Retailer testRetailer2;
    Retailer testRetailer3;
    Retailer testRetailer4;
    Retailer testRetailer5;
    Retailer testRetailer6;
    Route testRoute1;
    Route testRoute2;
    Route testRoute3;
    Route testRoute4;
    Route testRoute5;
    private Filter testFilter = new Filter();
    private ObservableList<WiFi> wiFiObservableList = FXCollections.observableArrayList();
    private ObservableList<Retailer> retailerObservableList = FXCollections.observableArrayList();
    private ObservableList<Route> routeObservableList = FXCollections.observableArrayList();

    /**
     * Created several new Retailer, WiFi and Route classes to test Filter class.
     *
     * @throws Exception Checks if set it up without producing an error
     */
    public void setUp() throws Exception {
        super.setUp();
        testWifi1 = new WiFi(new Location(33.63336, 37.89713), "Test Wifi 1", 1, "Borough 1", "Type 1", "Provider 1", "Remark", "Syria", "Id 1");
        testWifi2 = new WiFi(new Location(17.23267, 9.20732), "Test Wifi 2", 2, "Borough 2", "Type 3", "Provider 2", "Remark", "Niger", "Id 2");
        testWifi3 = new WiFi(new Location(-5.16332, 15.66038), "Test Wifi 3", 3, "Borough 2", "Type 2", "Provider 3", "Remark", "Congo", "Id 3");
        testWifi4 = new WiFi(new Location(44.21541, 63.57427), "Test Wifi 4", 4, "Borough 3", "Type 2", "Provider 3", "Remark", "Kazakhstan", "Id 4");
        testWifi5 = new WiFi(new Location(71.62692, -98.84529), "Test Wifi 5", 5, "Borough 4", "Type 1", "Provider 4", "Remark", "Wales Island", "Id 5");
        //Case when there is no wifi name. Should be ignored when finding the unique wifi name.
        testWifi6 = new WiFi(new Location(43.543453, 54.865675), "", 6, "Borough 3", "Type 1", "Provider 2", "Remark", "Denmark", "Id 6");

        wiFiObservableList.addAll(testWifi1, testWifi2, testWifi3, testWifi4, testWifi5, testWifi6);

        testRetailer1 = new Retailer(new Location(33.63336, 37.89713), "Test Retailer 1", "Address Other 1", "Address Line 2", "Syria", "State 1", "Primary 1", "Secondary 1", "2234");
        testRetailer2 = new Retailer(new Location(17.23267, 9.20732), "Test Retailer 2", "Address Line 1", "Address Line 2", "Niger", "State 2", "Primary 2", "Secondary 1", "2234");
        testRetailer3 = new Retailer(new Location(-5.16332, 15.66038), "Test Retailer 3", "Address Line 1", "Address Line 2", "Congo", "State 2", "Primary 2", "Secondary 2", "3234");
        testRetailer4 = new Retailer(new Location(44.21541, 63.57427), "Test Retailer 4", "Address Other 1", "Address Line 2 (Other)", "Kazakhstan", "State 3", "Primary 3", "Secondary 3", "2234");
        //Case when no address has being recorded. Should be ignored when finding the unique street name.
        testRetailer5 = new Retailer(new Location(71.62692, -98.84529), "Test Retailer 5", "", "Address Line 2 (Other)", "Wales Island", "State 3", "Primary 4", "Secondary 4", "5234");
        //Case when no zip/primary has not being recorded. Should be ignored when finding the unique value of zip/primary value.
        testRetailer6 = new Retailer(new Location(76.34545, -97.345345), "Test Retailer 6", "48 1/2 Address Street", "Address Line 3", "North Korea", "State 3", "", "Secondary 5", "");

        retailerObservableList.addAll(testRetailer1, testRetailer2, testRetailer3, testRetailer4, testRetailer5, testRetailer6);

        testRoute1 = new Route(10.01, "2015-08-23 10:30:00", "2015-08-23 10:54:12", "Id 1", "Station 1", 33.63336, 37.89713, "Id 2", "Station 2", 33.63336, 37.89723, "Bike Id 1", "User 1", "1998", "M");
        testRoute2 = new Route(19.8, "2013-05-31 2:31:09", "2013-05-31 2:55:47", "Id 3", "Station 3", 17.23267, 9.20732, "Id 4", "Station 4", 17.23267, 9.20742, "Bike Id 2", "User 2", "1986", "F");
        testRoute3 = new Route(15, "2017-09-24 17:12:33", "2017-09-24 17:44:23", "Id 5", "Station 5", -5.16332, 15.66038, "Id 6", "Station 6", -5.16332, 15.66048, "Bike Id 3", "User 3", "1995", "F");
        testRoute4 = new Route(18, "2008-04-08 23:58:01", "2018-04-09 00:30:36", "Id 7", "Station 7", 44.21541, 63.57427, "Id 8", "Station 8", 44.21541, 63.57527, "Bike Id 4", "User 3", "1995", "F");
        testRoute5 = new Route(9, "1970-01-01 00:00:00", "1970-01-01 00:10:42", "Id 9", "Station 9", 71.62692, -98.84529, "Id 10", "Station 10", 71.62692, -98.84529, "Bike Id 5", "User 4", "1956", "M");

        routeObservableList.addAll(testRoute1, testRoute2, testRoute3, testRoute4, testRoute5);
    }

    /**
     * Checks if filterWiFiBorough method filtered the right Borough.
     *
     * @throws Exception filters incorrect borough
     */
    public void testFilterWiFiBorough() throws Exception {
        ObservableList<WiFi> checkTest = testFilter.filterWiFiBorough(wiFiObservableList, "Borough 2");
        ObservableList<WiFi> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testWifi2, testWifi3);
        assertEquals(checkTest, checkResult);
    }

    /**
     * Checks if filterWiFiProvider method filtered the right provider.
     *
     * @throws Exception filters incorrect provider
     */
    public void testFilterWiFiProvider() throws Exception {
        ObservableList<WiFi> checkTest = testFilter.filterWiFiProvider(wiFiObservableList, "Provider 1");
        ObservableList<WiFi> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testWifi1);
        assertEquals(checkTest, checkResult);
    }

    /**
     * Checks if filterWiFiType method filtered the right type.
     *
     * @throws Exception filters incorrect wifi type.
     */
    public void testFilterWiFiType() throws Exception {
        ObservableList<WiFi> checkTest = testFilter.filterWiFiType(wiFiObservableList, "Type 1");
        ObservableList<WiFi> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testWifi1, testWifi5, testWifi6);
        assertEquals(checkTest, checkResult);
    }

    /**
     * Checks if filterRouteEndLocation method filtered the right end location.
     *
     * @throws Exception filters incorrect end location
     */
    public void testFilterRouteStartLocation() throws Exception {
        ObservableList<Route> checkTest = testFilter.filterRouteStartLocation(routeObservableList, "Station 1");
        ObservableList<Route> checkResult = FXCollections.observableArrayList();
        checkResult.add(testRoute1);
        assertEquals(checkTest, checkTest);

    }

    /**
     * Checks if filterRouteStartLocation method filtered the right Start location.
     *
     * @throws Exception filters incorrect start location.
     */
    public void testFilterRouteEndLocation() throws Exception {
        ObservableList<Route> checkTest = testFilter.filterRouteEndLocation(routeObservableList, "Station 8");
        ObservableList<Route> checkResult = FXCollections.observableArrayList();
        checkResult.add(testRoute4);
        assertEquals(checkTest, checkResult);

    }

    /**
     * Checks if filterRouteBikeID method filtered the right Bike ID's.
     *
     * @throws Exception filters incorrect Bike ID's.
     */
    public void testFilterRouteGender() throws Exception {
        ObservableList<Route> checkTest = testFilter.filterRouteGender(routeObservableList, "F");
        ObservableList<Route> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testRoute2, testRoute3, testRoute4);
        assertEquals(checkTest, checkResult);

    }

    /**
     * Checks if filterRouteGender method filtered the right gender.
     *
     * @throws Exception Filters incorrect gender.
     */
    public void testFilterRouteBikeID() throws Exception {
        ObservableList<Route> checkTest = testFilter.filterRouteBikeID(routeObservableList, "Bike Id 1");
        ObservableList<Route> checkResult = FXCollections.observableArrayList();
        checkResult.add(testRoute1);
        assertEquals(checkTest, checkResult);

    }

    /**
     * Checks if filterRetailerStreet method filtered the right street name.
     *
     * @throws Exception filters incorrect street name
     */
    public void testFilterRetailerStreet() throws Exception {
        ObservableList<Retailer> checkTest = testFilter.searchRetailerStreet(retailerObservableList, "Other 1");
        ObservableList<Retailer> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testRetailer1, testRetailer4);
        assertEquals(checkResult, checkTest);
    }

    /**
     * Checks if filterRetailerPrimary method filtered the right primary.
     *
     * @throws Exception filters incorrect primary
     */
    public void testFilterRetailerPrimary() throws Exception {
        ObservableList<Retailer> checkTest = testFilter.searchRetailerPrimary(retailerObservableList, "Primary 2");
        ObservableList<Retailer> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testRetailer2, testRetailer3);
        assertEquals(checkResult, checkTest);
    }

    /**
     * Checks if filterRetailerZIP method filtered the right zip.
     *
     * @throws Exception filters incorrect ZIP
     */
    public void testFilterRetailerZip() throws Exception {
        ObservableList<Retailer> checkTest = testFilter.searchRetailerZIP(retailerObservableList, "2234");
        ObservableList<Retailer> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testRetailer1, testRetailer2, testRetailer4);
        assertEquals(checkResult, checkTest);
    }


    /**
     * Checks if GetUniqueBoroughs method return observable list of unique strings.
     *
     * @throws Exception return incorrect observable list.
     */
    public void testGetUniqueBoroughs() throws Exception {
        ArrayList<String> checkTest = testFilter.getUniqueBoroughs(wiFiObservableList);
        ArrayList<String> checkResult = new ArrayList<>();
        checkResult.add("Borough 1");
        checkResult.add("Borough 2");
        checkResult.add("Borough 3");
        checkResult.add("Borough 4");
        assertEquals(checkTest, checkResult);

    }

    /**
     * Checks if GetUniqueType method return observable list of unique strings.
     *
     * @throws Exception return incorrect observable list.
     */
    public void testGetUniqueType() throws Exception {
        ArrayList<String> checkTest = testFilter.getUniqueTypes(wiFiObservableList);
        ArrayList<String> checkResult = new ArrayList<>();
        checkResult.add("Type 1");
        checkResult.add("Type 2");
        checkResult.add("Type 3");
        assertEquals(checkTest, checkResult);

    }

    /**
     * Checks if GetUniqueBoroughs method return observable list of unique strings.
     * Empty string should not be included in the observable list.
     *
     * @throws Exception return incorrect observable list or empty string is included.
     */
    public void testGetUniqueProvider() throws Exception {
        ArrayList<String> checkTest = testFilter.getUniqueProvider(wiFiObservableList);
        ArrayList<String> checkResult = new ArrayList<>();
        checkResult.add("Provider 1");
        checkResult.add("Provider 2");
        checkResult.add("Provider 3");
        checkResult.add("Provider 4");
        assertEquals(checkTest, checkResult);

    }

    /**
     * Checks if GetUniqueZips method return observable list of unique strings.
     * Empty string should not be included in the observable list.
     *
     * @throws Exception return incorrect observable list or empty string is included.
     */
    public void testGetUniqueZip() throws Exception {
        ArrayList<String> checkTest = testFilter.getUniqueZip(retailerObservableList);
        ArrayList<String> checkResult = new ArrayList<>();
        checkResult.add("2234");
        checkResult.add("3234");
        checkResult.add("5234");
        Collections.sort(checkTest);
        Collections.sort(checkResult);
        assertEquals(checkResult, checkTest);
    }

    /**
     * Checks if GetUniquePrimary method return observable list of unique strings.
     * Empty string should not be included in the observable list.
     *
     * @throws Exception return incorrect observable list or empty string is included.
     */
    public void testGetUniquePrimary() throws Exception {
        ArrayList<String> checkTest = testFilter.getUniquePrimary(retailerObservableList);
        ArrayList<String> checkResult = new ArrayList<>();
        checkResult.add("Primary 1");
        checkResult.add("Primary 2");
        checkResult.add("Primary 3");
        checkResult.add("Primary 4");
        Collections.sort(checkTest);
        Collections.sort(checkResult);
        assertEquals(checkResult, checkTest);
    }

    /**
     * Checks if GetUniqueStreet method return observable list of unique strings.
     * Empty string should not be included in the observable list.
     *
     * @throws Exception return incorrect observable list or empty string is included.
     */
    public void testGetUniqueStreet() throws Exception {
        ArrayList<String> checkTest = testFilter.getUniqueStreet(retailerObservableList);
        ArrayList<String> checkResult = new ArrayList<>();
        checkResult.add("Line 1");
        checkResult.add("Other 1");
        checkResult.add("Address Street"); //Should remove the flat number if it's included in the address.
        Collections.sort(checkResult);
        assertEquals(checkTest, checkResult);
    }

    /**
     * Checks if GetUniqueStart method return observable list of unique strings.
     *
     * @throws Exception return incorrect observable list.
     */
    public void testGetUniqueStart() throws Exception {
        ArrayList<String> checkTest = testFilter.getUniqueStart(routeObservableList);
        ArrayList<String> checkResult = new ArrayList<>();
        checkResult.add("Station 1");
        checkResult.add("Station 3");
        checkResult.add("Station 5");
        checkResult.add("Station 7");
        checkResult.add("Station 9");
        Collections.sort(checkTest);
        Collections.sort(checkResult);
        assertEquals(checkTest, checkResult);
    }

    /**
     * Checks if GetUniqueEnd method return observable list of unique strings.
     *
     * @throws Exception return incorrect observable list.
     */
    public void testGetUniqueEnd() throws Exception {
        ArrayList<String> checkTest = testFilter.getUniqueEnd(routeObservableList);
        ArrayList<String> checkResult = new ArrayList<>();
        checkResult.add("Station 2");
        checkResult.add("Station 4");
        checkResult.add("Station 6");
        checkResult.add("Station 8");
        checkResult.add("Station 10");
        Collections.sort(checkTest);
        Collections.sort(checkResult);
        assertEquals(checkTest, checkResult);
    }

    /**
     * Checks if GetUniqueBikeID method return observable list of unique strings.
     *
     * @throws Exception return incorrect observable list.
     */
    public void testGetUniqueBikeId() throws Exception {
        ArrayList<String> checkTest = testFilter.getUniqueBikeId(routeObservableList);
        ArrayList<String> checkResult = new ArrayList<>();
        checkResult.add("Bike Id 1");
        checkResult.add("Bike Id 2");
        checkResult.add("Bike Id 3");
        checkResult.add("Bike Id 4");
        checkResult.add("Bike Id 5");
        Collections.sort(checkTest);
        Collections.sort(checkResult);
        assertEquals(checkTest, checkResult);
    }

    /**
     * Checks if GetUniqueGender method return observable list of unique strings.
     *
     * @throws Exception return incorrect observable list.
     */
    public void testGetUniqueGender() throws Exception {
        ArrayList<String> checkTest = testFilter.getUniqueGender(routeObservableList);
        ArrayList<String> checkResult = new ArrayList<>();
        checkResult.add("M");
        checkResult.add("F");
        Collections.sort(checkTest);
        Collections.sort(checkResult);
        assertEquals(checkTest, checkResult);
    }

    /**
     * Checks if searchWiFiBorough method return all the objects with borough as "Borough 1"
     * Also checks if it's case sensitive
     *
     * @throws Exception return objects, where borough is not "Borough 1" or searching is not case sensitive.
     */
    public void testSearchWiFiBorough() throws Exception {
        ObservableList<WiFi> checkTest = testFilter.searchWiFiBorough(wiFiObservableList, "Borough 1");
        ObservableList<WiFi> checkTest2 = testFilter.searchWiFiBorough(wiFiObservableList, "bORough 1");
        ObservableList<WiFi> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testWifi1);
        assertEquals(checkTest, checkResult);
        assertEquals(checkTest2, checkResult);
    }

    /**
     * Checks if searchWiFiType method return all the objects with borough as "Type 3"
     * Also checks if it's case sensitive
     *
     * @throws Exception return objects, where Type is not "Type 3" or searching is not case sensitive.
     */
    public void testSearchWiFiType() throws Exception {
        ObservableList<WiFi> checkTest = testFilter.searchWiFiType(wiFiObservableList, "Type 3");
        ObservableList<WiFi> checkTest2 = testFilter.searchWiFiType(wiFiObservableList, "TYPE 3");
        ObservableList<WiFi> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testWifi2);
        assertEquals(checkTest, checkResult);
        assertEquals(checkTest2, checkResult);
    }

    /**
     * Checks if searchWiFi method return all the objects with borough as "Provider 3"
     * Also checks if it's case sensitive
     *
     * @throws Exception return objects, where provider is not "Provider 3" or searching is not case sensitive.
     */
    public void testSearchWiFiProvider() throws Exception {
        ObservableList<WiFi> checkTest = testFilter.searchWiFiProvider(wiFiObservableList, "Provider 3");
        ObservableList<WiFi> checkTest2 = testFilter.searchWiFiProvider(wiFiObservableList, "pRoViDER 3");
        ObservableList<WiFi> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testWifi3, testWifi4);
        assertEquals(checkTest, checkResult);
        assertEquals(checkTest2, checkResult);
    }

    /**
     * Checks if searchRouteStartLocation method return all the objects with start station as "Station 3"
     * Also checks if it's case sensitive
     *
     * @throws Exception return objects, where start station is not "Station 3" or searching is not case sensitive.
     */
    public void testSearchRouteStartLocation() throws Exception {
        ObservableList<Route> checkTest = testFilter.searchRouteStartLocation(routeObservableList, "Station 3");
        ObservableList<Route> checkTest2 = testFilter.searchRouteStartLocation(routeObservableList, "sTATION 3");
        ObservableList<Route> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testRoute2);
        assertEquals(checkTest, checkResult);
        assertEquals(checkTest2, checkResult);

    }

    /**
     * Checks if searchRouteEndLocation method return all the objects with end station as "Station 10"
     * Also checks if it's case sensitive
     *
     * @throws Exception return objects, where end station is not "Station 10" or searching is not case sensitive.
     */
    public void testSearchRouteEndLocation() throws Exception {
        ObservableList<Route> checkTest = testFilter.searchRouteEndLocation(routeObservableList, "Station 10");
        ObservableList<Route> checkTest2 = testFilter.searchRouteEndLocation(routeObservableList, "StAtIOn 10");
        ObservableList<Route> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testRoute5);
        assertEquals(checkTest, checkResult);
        assertEquals(checkTest2, checkResult);

    }

    /**
     * Checks if searchRouteBikeID method return all the objects with bike id as "Bike Id 3"
     * Also checks if it's case sensitive
     *
     * @throws Exception return objects, where bike ID is not "Bike Id 3" or searching is not case sensitive.
     */
    public void testSearchRouteBikeID() throws Exception {
        ObservableList<Route> checkTest = testFilter.searchRouteBikeID(routeObservableList, "Bike Id 3");
        ObservableList<Route> checkTest2 = testFilter.searchRouteBikeID(routeObservableList, "BIKe id 3");
        ObservableList<Route> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testRoute3);
        assertEquals(checkTest, checkResult);
        assertEquals(checkTest2, checkResult);
    }

    /**
     * Checks if searchRouteGender method return all the objects with gender as "F"
     * Also checks if it's case sensitive
     *
     * @throws Exception return objects, where gender is not "F" or searching is not case sensitive.
     */
    public void testSearchRouteGender() throws Exception {
        ObservableList<Route> checkTest = testFilter.searchRouteGender(routeObservableList, "F");
        ObservableList<Route> checkTest2 = testFilter.searchRouteGender(routeObservableList, "f");
        ObservableList<Route> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testRoute2, testRoute3, testRoute4);
        assertEquals(checkTest, checkResult);
        assertEquals(checkTest2, checkResult);
    }


    /**
     * Checks if searchRetailerStreet method return all the objects with street name as "Line 1"
     * Also checks if it's case sensitive
     *
     * @throws Exception return objects, where Street name is not "Line 1" or searching is not case sensitive.
     */
    public void testSearchRetailerStreet() throws Exception {
        ObservableList<Retailer> checkTest = testFilter.searchRetailerStreet(retailerObservableList, "Line 1");
        ObservableList<Retailer> checkTest2 = testFilter.searchRetailerStreet(retailerObservableList, "linE 1");
        ObservableList<Retailer> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testRetailer2);
        checkResult.addAll(testRetailer3);
        assertEquals(checkTest, checkResult);
        assertEquals(checkTest2, checkResult);
    }

    /**
     * Checks if searchRetailerZIP method return all the objects with ZIP as "5234"
     *
     * @throws Exception return objects, where ZIP is not "5234"
     */
    public void testSearchRetailerZIP() throws Exception {
        ObservableList<Retailer> checkTest = testFilter.searchRetailerZIP(retailerObservableList, "5234");
        ObservableList<Retailer> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testRetailer5);
        assertEquals(checkTest, checkResult);
    }

    /**
     * Checks if searchRetailerStreet method return all the objects with primary as "Primary 2"
     * Also checks if it's case sensitive
     *
     * @throws Exception return objects, where Primary is not "Primary 2" or searching is not case sensitive.
     */
    public void testSearchRetailerPrimary() throws Exception {
        ObservableList<Retailer> checkTest = testFilter.searchRetailerPrimary(retailerObservableList, "Primary 2");
        ObservableList<Retailer> checkTest2 = testFilter.searchRetailerPrimary(retailerObservableList, "priMARy 2");
        ObservableList<Retailer> checkResult = FXCollections.observableArrayList();
        checkResult.addAll(testRetailer2);
        checkResult.addAll(testRetailer3);
        assertEquals(checkTest, checkResult);
        assertEquals(checkTest2, checkResult);
    }


}