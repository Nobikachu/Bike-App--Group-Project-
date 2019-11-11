package Data;

import Model.BikeStation;
import Model.Retailer;
import Model.Route;
import Model.WiFi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides methods to filter Points of Interest, find unique attributes of a list of Points and search through Points
 */
public class Filter {

    /**
     * @param wifiList List of WiFi points
     * @param borough  Borough to filter with
     * @return List with all WiFi with same borough as an ObservableArrayList
     */
    public ObservableList<WiFi> filterWiFiBorough(ObservableList<WiFi> wifiList, String borough) {
        ArrayList<WiFi> filteredList = new ArrayList<>();
        for (WiFi wifi : wifiList) {
            if (wifi.getBorough().equals(borough)) {
                filteredList.add(wifi);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }


    /**
     * @param wifiList List of WiFi points
     * @param type     Type of WiFi to filter from
     * @return List with all WiFi points with the same type as an ObservableArrayList
     */
    public ObservableList<WiFi> filterWiFiType(ObservableList<WiFi> wifiList, String type) {
        ArrayList<WiFi> filteredList = new ArrayList<>();
        for (WiFi wifi : wifiList) {
            if (wifi.getType().equals(type)) {
                filteredList.add(wifi);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param wifiList List of WiFi points
     * @param provider Provider to filter from
     * @return List with all WiFi points with the same provider as an ObservableList
     */
    public ObservableList<WiFi> filterWiFiProvider(ObservableList<WiFi> wifiList, String provider) {
        ArrayList<WiFi> filteredList = new ArrayList<>();
        for (WiFi wifi : wifiList) {
            if (wifi.getProvider().equals(provider)) {
                filteredList.add(wifi);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param routeList          List of Routes
     * @param start_station_name Start Station Name to filter from
     * @return List with all Routes with the same start location as an ObservableList
     */
    public ObservableList<Route> filterRouteStartLocation(ObservableList<Route> routeList, String start_station_name) {
        ArrayList<Route> filteredList = new ArrayList<>();
        for (Route route : routeList) {
            if (route.getRouteStartStationName().equals(start_station_name)) {
                filteredList.add(route);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param routeList        List of Routes
     * @param end_station_name End Station Name to filter from
     * @return List with all Routes woth the same end location as an ObservableList
     */
    public ObservableList<Route> filterRouteEndLocation(ObservableList<Route> routeList, String end_station_name) {
        ArrayList<Route> filteredList = new ArrayList<>();
        for (Route route : routeList) {
            if (route.getRouteEndStationName().equals(end_station_name)) {
                filteredList.add(route);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param routeList List of routes
     * @param bike_id   Bike Id to filter from
     * @return List with all Routes with the same bike id as an ObservableList
     */
    public ObservableList<Route> filterRouteBikeID(ObservableList<Route> routeList, String bike_id) {
        ArrayList<Route> filteredList = new ArrayList<>();
        for (Route route : routeList) {
            if (route.getRouteBikeID().equals(bike_id)) {
                filteredList.add(route);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param routeList List of routes
     * @param gender    Gender to filter from
     * @return List with all routes with the same gender as an ObservableList
     */
    public ObservableList<Route> filterRouteGender(ObservableList<Route> routeList, String gender) {
        ArrayList<Route> filteredList = new ArrayList<>();
        for (Route route : routeList) {
            if (route.getRouteGender().equals(gender)) {
                filteredList.add(route);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param retailerList List of retailers
     * @param street       Street to filter from
     * @return List with all retailers on the same stree as an ObservableList
     */
    public ObservableList<Retailer> filterRetailerStreet(ObservableList<Retailer> retailerList, String street) {
        ArrayList<Retailer> filteredList = new ArrayList<>();
        for (Retailer retail : retailerList) {
            if (retail.getStreetName().equals(street)) {
                filteredList.add(retail);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param retailerList List of retailers
     * @param ZIP          ZIP to filter from
     * @return List with all retailers with the same zip as an Observablelist
     */
    public ObservableList<Retailer> filterRetailerZIP(ObservableList<Retailer> retailerList, String ZIP) {
        ArrayList<Retailer> filteredList = new ArrayList<>();
        for (Retailer retail : retailerList) {
            if (retail.getZip().equals(ZIP)) {
                filteredList.add(retail);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param retailerList List of retailers
     * @param primary      Primary to filter from
     * @return List with all retailers with the same primary as an ObservableList
     */
    public ObservableList<Retailer> filterRetailerPrimary(ObservableList<Retailer> retailerList, String primary) {
        ArrayList<Retailer> filteredList = new ArrayList<>();
        for (Retailer retail : retailerList) {
            if (retail.getPrimary().equals(primary)) {
                filteredList.add(retail);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }


    /**
     * @param bikeStationList List of bike stations
     * @param name            Name to filter from
     * @return List with all bike stations with the same name as an ObservableList
     */
    public ObservableList<BikeStation> filterBikeStationName(ObservableList<BikeStation> bikeStationList, String name) {
        ArrayList<BikeStation> filteredList = new ArrayList<>();
        for (BikeStation bikeStation : bikeStationList) {
            if (bikeStation.getName().equals(name)) {
                filteredList.add(bikeStation);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * Returns all unique Borough Names in WiFi
     *
     * @param wifiList Array of all wifi points
     * @return ArrayList of Strings with unique borough names
     */
    public ArrayList<String> getUniqueBoroughs(ObservableList<WiFi> wifiList) {
        Set<String> boroughsAsSet = new HashSet<>();
        for (WiFi p : wifiList) {
            boroughsAsSet.add(p.getBorough());

        }
        ArrayList<String> boroughsAsList = new ArrayList<>(boroughsAsSet);
        Collections.sort(boroughsAsList);
        return boroughsAsList;
    }

    /**
     * Returns all unique Types in WiFi
     *
     * @param wifiList Array of all wifi points
     * @return ArrayList of Strings with unique types
     */
    public ArrayList<String> getUniqueTypes(ObservableList<WiFi> wifiList) {
        Set<String> typesAsSet = new HashSet<>();
        for (WiFi p : wifiList) {
            typesAsSet.add(p.getType());
        }
        ArrayList<String> typesAsList = new ArrayList<>(typesAsSet);
        Collections.sort(typesAsList);
        return typesAsList;
    }

    /**
     * Returns all unique Provider Names in WiFi
     *
     * @param wifiList Array of all wifi points
     * @return ArrayList of Strings with unique provider names
     */
    public ArrayList<String> getUniqueProvider(ObservableList<WiFi> wifiList) {
        Set<String> providerAsSet = new HashSet<>();
        for (WiFi p : wifiList) {
            providerAsSet.add(p.getProvider());
        }
        ArrayList<String> providerAsList = new ArrayList<>(providerAsSet);
        Collections.sort(providerAsList);
        return providerAsList;
    }

    /**
     * @param retailerList List of retailers
     * @return All unique ZIPs of retailers as an ArrayList
     */
    public ArrayList<String> getUniqueZip(ObservableList<Retailer> retailerList) {
        Set<String> zipAsSet = new HashSet<>();
        for (Retailer p : retailerList) {
            if (!p.getZip().equals("")) { //to make sure empty strings are not included.
                zipAsSet.add(p.getZip());
            }
        }
        ArrayList<String> zipAsList = new ArrayList<>(zipAsSet);
        Collections.sort(zipAsList);
        return zipAsList;
    }

    /**
     * @param retailerList List of retailers
     * @return All unique Primarys used in retailers
     */
    public ArrayList<String> getUniquePrimary(ObservableList<Retailer> retailerList) {
        Set<String> primaryAsSet = new HashSet<>();
        for (Retailer p : retailerList) {
            if (!p.getPrimary().equals("")) { //to make sure empty strings are not included.
                primaryAsSet.add(p.getPrimary());
            }
        }
        ArrayList<String> primaryAsList = new ArrayList<>(primaryAsSet);
        Collections.sort(primaryAsList);
        return primaryAsList;
    }

    /**
     * @param routeList List of routes
     * @return All unique Start Station Names in routes
     */
    public ArrayList<String> getUniqueStart(ObservableList<Route> routeList) {
        Set<String> routeAsSet = new HashSet<>();
        for (Route p : routeList) {
            routeAsSet.add(p.getRouteStartStationName());
        }
        ArrayList<String> routeAsList = new ArrayList<>(routeAsSet);
        Collections.sort(routeAsList);
        return routeAsList;
    }

    /**
     * @param routeList List if routes
     * @return All unique Genders in routes
     */
    public ArrayList<String> getUniqueGender(ObservableList<Route> routeList) {
        Set<String> routeAsSet = new HashSet<>();
        for (Route p : routeList) {
            routeAsSet.add(p.getRouteGender());
        }
        ArrayList<String> routeAsList = new ArrayList<>(routeAsSet);
        Collections.sort(routeAsList);
        return routeAsList;
    }

    /**
     * @param routeList List of routes
     * @return All unique End Station Names in routes
     */
    public ArrayList<String> getUniqueEnd(ObservableList<Route> routeList) {
        Set<String> routeAsSet = new HashSet<>();
        for (Route p : routeList) {
            routeAsSet.add(p.getRouteEndStationName());
        }
        ArrayList<String> routeAsList = new ArrayList<>(routeAsSet);
        Collections.sort(routeAsList);
        return routeAsList;
    }

    /**
     * @param routeList List of routes
     * @return All unique Bike Ids in routes
     */
    public ArrayList<String> getUniqueBikeId(ObservableList<Route> routeList) {
        Set<String> routeAsSet = new HashSet<>();
        for (Route p : routeList) {
            routeAsSet.add(p.getRouteBikeID());
        }
        ArrayList<String> routeAsList = new ArrayList<>(routeAsSet);
        Collections.sort(routeAsList);
        return routeAsList;
    }

    /**
     * @param retailerList List of retailers
     * @return All unique Street Names of retailers
     */
    public ArrayList<String> getUniqueStreet(ObservableList<Retailer> retailerList) {
        Set<String> retailerAsSet = new HashSet<>();
        for (Retailer p : retailerList) {
            if (!p.getStreetName().equals("")) { //to make sure empty strings are not included.
                retailerAsSet.add(p.getStreetName());
            }
        }
        ArrayList<String> retailerAsList = new ArrayList<>(retailerAsSet);
        Collections.sort(retailerAsList);
        return retailerAsList;
    }


    /**
     * @param bikeStationList List of bike stations as an observablelist
     * @return A list of unique bike station names as an arraylist
     */
    public ArrayList<String> getUniqueName(ObservableList<BikeStation> bikeStationList) {
        Set<String> bikeStationAsSet = new HashSet<>();
        for (BikeStation p : bikeStationList) {
            if (!p.getName().equals("")) { //to make sure empty strings are not included.
                bikeStationAsSet.add(p.getName());
            }
        }
        ArrayList<String> bikeStationAsList = new ArrayList<>(bikeStationAsSet);
        Collections.sort(bikeStationAsList);
        return bikeStationAsList;
    }

//-----------------------------------Searching--------------------------------------------


    /**
     * @param wifiList List of WiFis
     * @param borough  Borough to search from
     * @return All WiFi which contain the contents of borough as an ObservableList
     */
    public ObservableList<WiFi> searchWiFiBorough(ObservableList<WiFi> wifiList, String borough) {
        ArrayList<WiFi> filteredList = new ArrayList<>();
        for (WiFi wifi : wifiList) {
            if (wifi.getBorough().toLowerCase().contains(borough.toLowerCase())) {
                filteredList.add(wifi);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param wifiList List of WiFis
     * @param type     Type to search from
     * @return All WiFi which contain the contents of type as an ObservableList
     */
    public ObservableList<WiFi> searchWiFiType(ObservableList<WiFi> wifiList, String type) {
        ArrayList<WiFi> filteredList = new ArrayList<>();
        for (WiFi wifi : wifiList) {
            if (wifi.getType().toLowerCase().contains(type.toLowerCase())) {
                filteredList.add(wifi);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param wifiList List of WiFis
     * @param provider Provider to search from
     * @return All WiFi which contain the contents of provider as an ObservableList
     */
    public ObservableList<WiFi> searchWiFiProvider(ObservableList<WiFi> wifiList, String provider) {
        ArrayList<WiFi> filteredList = new ArrayList<>();
        for (WiFi wifi : wifiList) {
            if (wifi.getProvider().toLowerCase().contains(provider.toLowerCase())) {
                filteredList.add(wifi);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param routeList          List of routes
     * @param start_station_name Start station Name to search from
     * @return Routes which contain the contents of Start Station Name as an ObservableList
     */
    public ObservableList<Route> searchRouteStartLocation(ObservableList<Route> routeList, String start_station_name) {
        ArrayList<Route> filteredList = new ArrayList<>();
        for (Route route : routeList) {
            if (route.getRouteStartStationName().toLowerCase().contains(start_station_name.toLowerCase())) {
                filteredList.add(route);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param routeList        List of routes
     * @param end_station_name End Station Name to search from
     * @return Routes which contain the contents of End Station name as an ObservableList
     */
    public ObservableList<Route> searchRouteEndLocation(ObservableList<Route> routeList, String end_station_name) {
        ArrayList<Route> filteredList = new ArrayList<>();
        for (Route route : routeList) {
            if (route.getRouteEndStationName().toLowerCase().contains(end_station_name.toLowerCase())) {
                filteredList.add(route);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param routeList List of routes
     * @param bike_id   Bike Id to search from
     * @return Routes which contain the content of Bike Id as an ObservableList
     */
    public ObservableList<Route> searchRouteBikeID(ObservableList<Route> routeList, String bike_id) {
        ArrayList<Route> filteredList = new ArrayList<>();
        for (Route route : routeList) {
            if (route.getRouteBikeID().toLowerCase().contains(bike_id.toLowerCase())) {
                filteredList.add(route);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param routeList List of routes
     * @param gender    Gender to search from
     * @return Routes which contain the content of gender as an ObservableList
     */
    public ObservableList<Route> searchRouteGender(ObservableList<Route> routeList, String gender) {
        ArrayList<Route> filteredList = new ArrayList<>();
        for (Route route : routeList) {
            if (route.getRouteGender().toLowerCase().contains(gender.toLowerCase())) {
                filteredList.add(route);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param retailerList List of retailers
     * @param street       Street to search from
     * @return Retailers which contain the content of street as an ObservableList
     */
    public ObservableList<Retailer> searchRetailerStreet(ObservableList<Retailer> retailerList, String street) {
        ArrayList<Retailer> filteredList = new ArrayList<>();
        for (Retailer retail : retailerList) {
            if (retail.getStreetName().toLowerCase().contains(street.toLowerCase())) {
                filteredList.add(retail);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param retailerList List of retailers
     * @param ZIP          Zip to search from
     * @return Retailers which contain the content of Zip as an ObservableList
     */
    public ObservableList<Retailer> searchRetailerZIP(ObservableList<Retailer> retailerList, String ZIP) {
        ArrayList<Retailer> filteredList = new ArrayList<>();
        for (Retailer retail : retailerList) {
            if (retail.getZip().toLowerCase().contains(ZIP.toLowerCase())) {
                filteredList.add(retail);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    /**
     * @param retailerList List of retailers
     * @param primary      Primary to search from
     * @return Retailers which contain the content of primary as an ObservableList
     */
    public ObservableList<Retailer> searchRetailerPrimary(ObservableList<Retailer> retailerList, String primary) {
        ArrayList<Retailer> filteredList = new ArrayList<>();
        for (Retailer retail : retailerList) {
            if (retail.getPrimary().toLowerCase().contains(primary.toLowerCase())) {
                filteredList.add(retail);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }


    /**
     * @param bikeStationList List of bike stations
     * @param name            Name to search from
     * @return Bike stations which contain the content of name as an ObservableList
     */
    public ObservableList<BikeStation> searchBikeStationName(ObservableList<BikeStation> bikeStationList, String name) {
        ArrayList<BikeStation> filteredList = new ArrayList<>();
        for (BikeStation bikeStation : bikeStationList) {
            if (bikeStation.getName().toLowerCase().contains(name.toLowerCase())) {
                filteredList.add(bikeStation);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }
}
