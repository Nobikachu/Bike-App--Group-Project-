package Data;

import GUI.RawDataViewerController;
import Model.BikeStation;
import Model.Retailer;
import Model.Route;
import Model.WiFi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.text.ParseException;
import java.util.*;

import static GUI.UserAlerts.error;

/**
 * A program which handles all aspects of data manipulation related to the application.
 */
public class DataManager {
    //Contains the headers required in a properly formed CSV of WiFi objects
    public static final String[] wifiHeaders = {"OBJECTID", "BORO", "TYPE", "PROVIDER", "NAME", "LAT", "LON", "REMARKS", "CITY", "SSID"};
    //Contains the headers required in a properly formed CSV of Route objects
    public static final String[] routeHeaders = {"tripduration", "starttime", "stoptime", "start station id", "start station name", "start station latitude", "start station longitude", "end station id", "end station name", "end station latitude", "end station longitude", "bikeid", "usertype", "birth year", "gender"};
    //Contains the headers required in a properly formed CSV of Route objects
    public static final String[] retailerHeaders = {"CnBio_Org_Name", "CnAdrPrf_Addrline1", "CnAdrPrf_Addrline2", "CnAdrPrf_City", "CnAdrPrf_State", "CnAdrPrf_ZIP", "Primary", "Secondary", "Lat", "Long"};
    //Contains the headers required in a properly formed CSV of BikeStation objects
    public static final String[] bikeStationHeaders = {"bikestation_latitude", "bikestation_longitude", "bikestation_name"};
    //The HashMaps have keys which are the name of the (user created/imported) list the user gave them.
    // The values of the HashMap are a collection of the associated values from the (user created/imported) list.
    private static HashMap<Object, ObservableSetList<Route>> routeArrays = new HashMap<>();
    private static HashMap<Object, ObservableSetList<WiFi>> wifiArrays = new HashMap<>();
    private static HashMap<Object, ObservableSetList<Retailer>> retailersArrays = new HashMap<>();
    private static HashMap<Object, ObservableSetList<BikeStation>> bikeStationArrays = new HashMap<>();

    /**
     * Get the Map of Route lists. Key is the name of the list the user has named it, value of the HashMap
     * is the associated collection of values.
     *
     * @return the ObervableSetLists of routes as a HashMap
     */
    public static HashMap<Object, ObservableSetList<Route>> getRouteArrays() {
        return routeArrays;
    }

    /**
     * Get the Map of WiFi lists. Key is the name of the list the user has named it, value of the HashMap
     * is the associated collection of values.
     *
     * @return the ObervableSetLists of WiFis as a HashMap
     */
    public static HashMap<Object, ObservableSetList<WiFi>> getWifiArrays() {
        return wifiArrays;
    }

    /**
     * Get the Map of Retailer lists. Key is the name of the list the user has named it, value of the HashMap
     * is the associated collection of values.
     *
     * @return the ObervableSetLists of Retailers as a HashMap
     */
    public static HashMap<Object, ObservableSetList<Retailer>> getRetailersArrays() {
        return retailersArrays;
    }


    /**
     * Get the Map of BikeStation lists. Key is the name of the list the user has named it, value of the HashMap
     * is the associated collection of values.
     *
     * @return The ObservableSetList of BikeStation as a HashMap.
     */
    public static HashMap<Object, ObservableSetList<BikeStation>> getBikeStationArrays() {
        return bikeStationArrays;
    }


    /**
     * Imports a csv file of bike station data and creates a list of BikeStation objects from it and prompts the user to
     * add it to an existing set of stations, or create a new one.
     * <p>
     * Need to check if the correct header is applied
     *
     * @param filename The CSV file
     */
    public static void importBikeStation(String filename) {
        // Initialize the CSVReader
        CSVReader reader = new CSVReader();
        CSVParser records = reader.readFile(filename);
        if (records == null) {
            return;
        }
        // Check that the correct headers are present and if not, display an error dialog
        if (!checkHeaders(bikeStationHeaders, records.getHeaderMap())) {
            csvError(bikeStationHeaders, records.getHeaderMap().keySet().toArray());
            return;
        }
        // Else make a new list and append it to the static ArrayList
        Set<BikeStation> bikeStations = new HashSet<>();
        for (CSVRecord record : records) {
            bikeStations.add(new BikeStation(record));
        }

        reader.close();
        Object result = listDialog(bikeStationArrays.keySet());
        if (result == null) {
            return;
        }
        if (result.equals("Create a new list")) {
            Object name = nameDialog(bikeStationArrays.keySet());
            if (name == null) {
                return;
            }
            bikeStationArrays.put(name, new ObservableSetList<>(bikeStations));
            //Now has new lists to be displayed, update the choice box to display this
            RawDataViewerController.controller.changeListChoiceBox();
        } else {
            bikeStationArrays.get(result).addAll(bikeStations);
        }
    }


    /**
     * Imports a csv file of WiFi hotspot data and creates a list of WiFi objects from it and prompts the user to
     * add it to an existing set of hotspots, or create a new one.
     * <p>
     * Need to check if the correct header is applied
     *
     * @param filename The CSV file
     */
    public static void importWifi(String filename) {
        // Initialize the CSVReader
        CSVReader reader = new CSVReader();
        CSVParser records = reader.readFile(filename);
        if (records == null) {
            return;
        }
        // Check that the correct headers are present and if not, display an error dialog
        if (!checkHeaders(wifiHeaders, records.getHeaderMap())) {
            csvError(wifiHeaders, records.getHeaderMap().keySet().toArray());
            return;
        }
        // Else make a new list and append it to the static ArrayList
        Set<WiFi> hotspots = new HashSet<>();
        for (CSVRecord record : records) {
            hotspots.add(new WiFi(record));
        }

        reader.close();
        Object result = listDialog(wifiArrays.keySet());
        if (result == null) {
            return;
        }
        if (result.equals("Create a new list")) {
            Object name = nameDialog(wifiArrays.keySet());
            if (name == null) {
                return;
            }
            wifiArrays.put(name, new ObservableSetList<>(hotspots));
            //Now has new lists to be displayed, update the choice box to display this
            RawDataViewerController.controller.changeListChoiceBox();
        } else {
            wifiArrays.get(result).addAll(hotspots);
        }
    }

    /**
     * Imports a csv file of retailer data and creates a list of Retailer objects from it and prompts the user to
     * add it to an existing set of retailers, or create a new one.
     *
     * @param classPath The class path for the CSV file
     */
    public static void importRetailers(String classPath) {
        CSVReader reader = new CSVReader();
        CSVParser records = reader.readFile(classPath);
        if (records == null) {
            return;
        }
        if (!checkHeaders(retailerHeaders, records.getHeaderMap())) {
            csvError(retailerHeaders, records.getHeaderMap().keySet().toArray());
            return;
        }
        Set<Retailer> retailers = new HashSet<>();
        for (CSVRecord record : records) {
            retailers.add(new Retailer(record));
        }
        reader.close();
        Object result = listDialog(retailersArrays.keySet());
        if (result == null) {
            return;
        }
        if (result.equals("Create a new list")) {
            Object name = nameDialog(retailersArrays.keySet());
            if (name == null) {
                return;
            }
            System.out.println("Adding retailer list to the settings: " + name.toString() + " " + classPath);
            retailersArrays.put(name, new ObservableSetList<>(retailers));
            //Now has new lists to be displayed, update the choice box to display this
            RawDataViewerController.controller.changeListChoiceBox();
        } else {
            retailersArrays.get(result).addAll(retailers);
        }
    }

    /**
     * Adds a route to the given set.
     *
     * @param set   The set into which to put the route.
     * @param route The route to be inserted.
     */
    public static void addRoute(Object set, Route route) {
        routeArrays.get(set).getSet().add(route);
    }

    /**
     * Adds a retailer to the given set.
     *
     * @param set      The set into which to put the retailer.
     * @param retailer The retailer to be inserted.
     */
    public static void addRetailer(Object set, Retailer retailer) {
        retailersArrays.get(set).getSet().add(retailer);
    }

    /**
     * Adds a wifi to the given set.
     *
     * @param set  The set into which to put the wifi.
     * @param wifi The wifi to be inserted.
     */
    public static void addWifi(Object set, WiFi wifi) {
        wifiArrays.get(set).getSet().add(wifi);
    }

    /**
     * Adds a bike station to the given set containing bike stations.
     *
     * @param set         The name of the set the bike station will be added to.
     * @param bikeStation The bike station that will be added to the specified set.
     */
    public static void addBikeStation(Object set, BikeStation bikeStation) {
        bikeStationArrays.get(set).getSet().add(bikeStation);
    }

    /**
     * Removes a route from the given set.
     *
     * @param set   The set from which to remove the route.
     * @param route The route to be removed.
     */
    public static void removeRoute(Object set, Route route) {
        routeArrays.get(set).getSet().remove(route);
    }

    /**
     * Removes a retailer from the given set.
     *
     * @param set      The set from which to remove the retailer.
     * @param retailer The retailer to be removed.
     */
    public static void removeretailer(Object set, Retailer retailer) {
        retailersArrays.get(set).getSet().remove(retailer);
    }

    /**
     * Removes a hotspot from the given set.
     *
     * @param set  The set from which to remove the hotspot.
     * @param wifi The hotspot to be removed.
     */
    public static void removewifi(Object set, WiFi wifi) {
        wifiArrays.get(set).getSet().remove(wifi);
    }

    /**
     * Removes a bike station from the specified set/list.
     *
     * @param setName     The name of the set the bike station is to be removed from.
     * @param bikeStation The bike station that will be removed.
     */
    public static void removeBikeStation(Object setName, BikeStation bikeStation) {
        bikeStationArrays.get(setName).getSet().remove(bikeStation);
    }

    /**
     * Method used to retrieve retailers from the given CSVFile as an ObservableSetList type.
     * <p>
     * Used on application startup, hence does not need to check if a file of the same name
     * already exists or if the user wanted to create a separate list or add to an existing one.
     * Just needs to load from the CSV file and populate routeArrays.
     *
     * @param retailerListPath The path of the CSV file to load data from.
     * @return An ObservableSetList of retailers from the provided CSV as an ObservableSetList
     * @throws NullPointerException Exception is thrown if the path to the CSV is invalid.
     */
    static ObservableSetList<Retailer> loadRetailersFromCSV(String retailerListPath) {
        System.out.println("Class Path " + retailerListPath);
        CSVReader reader = new CSVReader();
        CSVParser records = reader.readFile(retailerListPath);
        Set<Retailer> retailers = new HashSet<>();
        for (CSVRecord record : records) {
            retailers.add(new Retailer(record));
        }

        return new ObservableSetList<>(retailers);
    }

    /**
     * Method used to retrieve routes from the given CSVFile as an ObservableSetList type.
     * Used on application startup, hence does not need to check if a file of the same name
     * already exists or if the user wanted to create a separate list or add to an existing one.
     * Just needs to load from the CSV file and populate routeArrays.
     *
     * @param routeListPath The path of the CSV file to load data from.
     * @return An ObservableSetList of route entries fromm the CSV file.
     * @throws NullPointerException Exception is thrown if the path to the CSV is invalid.
     */
    static ObservableSetList<Route> loadRoutesFromCSV(String routeListPath) {
        System.out.println("Class Path " + routeListPath);
        CSVReader reader = new CSVReader();
        try {
            CSVParser records = reader.readFile(routeListPath);
            System.out.println("Can create the records");
            Set<Route> routes = new HashSet<>();
            for (CSVRecord record : records) {
                routes.add(new Route(record));
            }

            return new ObservableSetList<>(routes);
        } catch (NullPointerException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method used to load WiFi locations from the given CSVFile as an ObservableSetList type, will be used to add all
     * the records and the name of the list ot the DataManager.wifiArrays HashMap.
     * <p>
     * Used on application startup, hence does not need to check if a file of the same name
     * already exists or if the user wanted to create a separate list or add to an existing one.
     * Just needs to load from the CSV file and populate wifiArrays.
     *
     * @param wifiListClasspath The path of the CSV file to load data from.
     * @return An ObservableSetList of WiFi entries from the specified CSV file.
     * @throws NullPointerException Exception is thrown if the path to the CSV is invalid.
     */
    static ObservableSetList<WiFi> loadWifiFromCSV(String wifiListClasspath) {
        System.out.println("Class Path " + wifiListClasspath);
        CSVReader reader = new CSVReader();
        CSVParser records = reader.readFile(wifiListClasspath);
        System.out.println("Can create the records");
        Set<WiFi> wifis = new HashSet<>();
        for (CSVRecord record : records) {
            wifis.add(new WiFi(record));
        }

        return new ObservableSetList<>(wifis);
    }

    /**
     * Method used to load bike  stations from the given CSVFile as an ObservableSetList type, will be used to add all
     * the records and the name of the list ot the DataManager.wifiArrays HashMap.
     * <p>
     * Used on application startup, hence does not need to check if a file of the same name
     * already exists or if the user wanted to create a separate list or add to an existing one.
     * Just needs to load from the CSV file and populate wifiArrays.
     *
     * @param bikeStationListClasspath The class path to the bike station CSV to load from.
     * @return An ObservableSetList of the bike stations from the specified CSV file.
     * @throws NullPointerException Exception is thrown if the path to the CSV is invalid.
     */
    public static ObservableSetList<BikeStation> loadBikeStationFromCSV(String bikeStationListClasspath) {
        CSVReader reader = new CSVReader();
        CSVParser records = reader.readFile(bikeStationListClasspath);
        Set<BikeStation> bikeStations = new HashSet<>();
        for (CSVRecord record : records) {
            bikeStations.add(new BikeStation(record));
        }

        return new ObservableSetList<>(bikeStations);
    }

    /**
     * Imports a csv file of route data and creates a list of Route objects from it and prompts the user to
     * add it to an existing set of routes, or create a new one.
     *
     * @param filename The CSV file
     */
    public static void importRoutes(String filename) {
        CSVReader reader = new CSVReader();
        CSVParser records = reader.readFile(filename);
        if (records == null) {
            return;
        }
        if (!checkHeaders(routeHeaders, records.getHeaderMap())) {
            csvError(routeHeaders, records.getHeaderMap().keySet().toArray());
            return;
        }
        Set<Route> routes = new HashSet<>();
        for (CSVRecord record : records) {
            try {
                routes.add(new Route(record));
            } catch (ParseException e) {
                error("Malformed date in CSV. No routes were imported");
            }
        }
        Object result = listDialog(routeArrays.keySet());
        if (result == null) {
            return;
        }
        if (result.equals("Create a new list")) {
            Object name = nameDialog(routeArrays.keySet());
            if (name == null) {
                return;
            }
            routeArrays.put(name, new ObservableSetList<>(routes));

            //Now has new lists to be displayed, update the choice box to display this
            RawDataViewerController.controller.changeListChoiceBox();
        } else {
            routeArrays.get(result).addAll(routes);
        }
        reader.close();
    }

    /**
     * Returns true if and only if each key in got is also in expected
     *
     * @param expected the headers expected to be present
     * @param got      the headers actually present
     * @return True if expected headers are present, otherwise false
     */
    private static boolean checkHeaders(String[] expected, Map<String, ?> got) {
        for (String header : expected) {
            if (!got.containsKey(header)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a list of routes in order of distance, from shortest to longest.
     *
     * @return a list of routes sorted by distance.
     */
    public static ArrayList<Route> sortRoutesByDistance() {

        ArrayList<Route> sortedRouteArray = new ArrayList<>();

        sortedRouteArray.sort((o1, o2) -> {
            if (o1.getRouteDistance() < o2.getRouteDistance())
                return -1;
            else if (o1.getRouteDistance() > o2.getRouteDistance())
                return 1;
            return 0;
        });

        return sortedRouteArray;
    }

    /**
     * Presents the user with a ComboBox dialog to choose which set to import the data into, or to create a new set.
     *
     * @param options Names of sets currently in use.
     * @return The user's selection as a string.
     **/
    private static Object listDialog(Set<Object> options) {
        Dialog<Object> listDialog = new ChoiceDialog<>("Create a new list", options);
        listDialog.setTitle("Which list");
        listDialog.setHeaderText("Into which list would you like to import the data?");
        listDialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<Object> result = listDialog.showAndWait();
        return result.orElse(null);
    }


    /**
     * Presents the user with a dialog to enter the name of a new set into which to import the data, checking that
     * the chosen name isn't already used and is not the empty string
     *
     * @param exclude A list of set names already used, and therefore excluded from use
     * @return The chosen name as a string
     */
    private static Object nameDialog(Set<Object> exclude) {
        Dialog<String> nameDialog = new TextInputDialog();
        nameDialog.setTitle("New list");
        nameDialog.setHeaderText("What would you like to call the new list?");
        nameDialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<String> input = nameDialog.showAndWait();
        if (!input.isPresent()) {
            return null;
        }
        while (input.get().equals("") || exclude.contains(input.get())) {
            nameDialog.setContentText("The name cannot be blank and cannot be already used by another set");
            input = nameDialog.showAndWait();
            if (!input.isPresent()) {
                return null;
            }
        }
        return input.get();
    }

    /**
     * Returns all WiFi objects in all sets.
     *
     * @return an ObservableArrayList of WiFi objects.
     */
    public static ObservableList<WiFi> getAllWifi() {
        ObservableList<WiFi> result = FXCollections.observableArrayList();
        for (ObservableSetList<WiFi> list : wifiArrays.values()) {
            result.addAll(list.getList());
        }
        return result;
    }

    /**
     * Returns all Retailer objects in all sets.
     *
     * @return an ObservableArrayList of Retailer objects.
     */
    public static ObservableList<Retailer> getAllRetailers() {
        ObservableList<Retailer> result = FXCollections.observableArrayList();
        for (ObservableSetList<Retailer> list : retailersArrays.values()) {
            result.addAll(list.getList());
        }
        return result;
    }

    /**
     * Returns all Route objects in all sets.
     *
     * @return an ObservableArrayList of Route objects.
     */
    public static ObservableList<Route> getAllRoutes() {
        ObservableList<Route> result = FXCollections.observableArrayList();
        for (ObservableSetList<Route> list : routeArrays.values()) {
            result.addAll(list.getList());
        }
        return result;
    }


    /**
     * Returns all the bike station objects in all the sets.
     *
     * @return An ObservableArrayList of bike station objects from all the sets.
     */
    public static ObservableList<BikeStation> getAllBikeStations() {
        ObservableList<BikeStation> result = FXCollections.observableArrayList();
        for (ObservableSetList<BikeStation> list : bikeStationArrays.values()) {
            result.addAll(list.getList());
        }
        return result;
    }


    /**
     * Presents the user with an error if the CSV file they chose does not match the expected model.
     *
     * @param expected an array of headers expected in the CSV file.
     * @param got      an array of headers that were present in the CSV file.
     */
    private static void csvError(String[] expected, Object[] got) {
        StringBuilder message = new StringBuilder("Malformed CSV. Expected headings:\n");
        for (String heading : expected) {
            message.append(heading).append(", ");
        }
        message.append("\nReceived headings:\n");
        for (Object heading : got) {
            message.append(heading).append(", ");
        }

        Alert alert = new Alert(Alert.AlertType.ERROR, message.toString(), ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }


}