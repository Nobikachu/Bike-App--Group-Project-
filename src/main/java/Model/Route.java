package Model;


import javafx.collections.ObservableList;
import org.apache.commons.csv.CSVRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * A Route object, Encapsulates the route data provided by CitiBike into an object
 */
public class Route implements Exportable {

    private static final List<String> genderList = Arrays.asList("unknown", "male", "female");
    public static SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private double route_duration;
    private Date start_time;
    private Date stop_time;

    private String start_station_id;
    private String start_station_name;
    private double start_station_latitude;
    private double start_station_longitude;

    private String end_station_id;
    private String end_station_name;
    private double end_station_latitude;
    private double end_station_longitude;


    private String bike_id;
    private String user_type;
    private String birth_year;
    private String gender;

    private double routeDistance;

    /**
     * Default constructor for a Route
     *
     * @param route_duration          the duration of the rouute (in seconds)
     * @param start_time              the date/time the trip started as a string
     * @param stop_time               the date/time the trip ended as a string
     * @param start_station_id        the station id the trip started from
     * @param start_station_name      the name of the station the trip started from
     * @param start_station_latitude  the latitude of the start station
     * @param start_station_longitude the longitude of the start station
     * @param end_station_id          the station id the trip ended at
     * @param end_station_name        the name of the station the trip ended at
     * @param end_station_latitude    the latitude of the end station
     * @param end_station_longitude   the longitude of the end station
     * @param bike_id                 the id of the bike used
     * @param user_type               the type of CitiBike user
     * @param birth_year              the birth year of the cyclist
     * @param gender                  the gender of the cyclist
     * @throws ParseException throws an eception if either of the given dates are improperly formed
     */
    public Route(double route_duration, String start_time, String stop_time, String start_station_id, String start_station_name, double start_station_latitude, double start_station_longitude, String end_station_id, String end_station_name, double end_station_latitude, double end_station_longitude, String bike_id, String user_type, String birth_year, String gender) throws ParseException {
        this.route_duration = route_duration;
        this.start_time = dateParser.parse(start_time);
        this.stop_time = dateParser.parse(stop_time);
        this.start_station_id = start_station_id;
        this.start_station_name = start_station_name;
        this.start_station_latitude = start_station_latitude;
        this.start_station_longitude = start_station_longitude;
        this.end_station_id = end_station_id;
        this.end_station_name = end_station_name;
        this.end_station_latitude = end_station_latitude;
        this.end_station_longitude = end_station_longitude;
        this.bike_id = bike_id;
        this.user_type = user_type;
        this.birth_year = birth_year;
        this.gender = gender;
        this.routeDistance = new Location(start_station_latitude, start_station_longitude).calculateDistance(new Location(end_station_latitude, end_station_longitude));
    }

    /**
     * Default constructor for a Route
     *
     * @param route_duration          the duration of the rouute (in seconds)
     * @param start_time              the date/time the trip started as a Date object
     * @param stop_time               the date/time the trip ended as a Date object
     * @param start_station_id        the station id the trip started from
     * @param start_station_name      the name of the station the trip started from
     * @param start_station_latitude  the latitude of the start station
     * @param start_station_longitude the longitude of the start station
     * @param end_station_id          the station id the trip ended at
     * @param end_station_name        the name of the station the trip ended at
     * @param end_station_latitude    the latitude of the end station
     * @param end_station_longitude   the longitude of the end station
     * @param bike_id                 the id of the bike used
     * @param user_type               the type of CitiBike user
     * @param birth_year              the birth year of the cyclist
     * @param gender                  the gender of the cyclist
     */
    public Route(double route_duration, Date start_time, Date stop_time, String start_station_id, String start_station_name, double start_station_latitude, double start_station_longitude, String end_station_id, String end_station_name, double end_station_latitude, double end_station_longitude, String bike_id, String user_type, String birth_year, String gender) {
        this.route_duration = route_duration;
        this.start_time = start_time;
        this.stop_time = stop_time;
        this.start_station_id = start_station_id;
        this.start_station_name = start_station_name;
        this.start_station_latitude = start_station_latitude;
        this.start_station_longitude = start_station_longitude;
        this.end_station_id = end_station_id;
        this.end_station_name = end_station_name;
        this.end_station_latitude = end_station_latitude;
        this.end_station_longitude = end_station_longitude;
        this.bike_id = bike_id;
        this.user_type = user_type;
        this.birth_year = birth_year;
        this.gender = gender.toLowerCase();
        this.routeDistance = new Location(start_station_latitude, start_station_longitude).calculateDistance(new Location(end_station_latitude, end_station_longitude));
    }

    /**
     * Creates a Route from a CSV entry
     *
     * @param record the CSVRecord containing the data
     * @throws ParseException Throws an exception if either date is malformed
     */
    public Route(CSVRecord record) throws ParseException {
        this(Double.parseDouble(record.get("tripduration")),
                record.get("starttime"),
                record.get("stoptime"),
                record.get("start station id"),
                record.get("start station name"),
                Double.parseDouble(record.get("start station latitude")),
                Double.parseDouble(record.get("start station longitude")),
                record.get("end station id"),
                record.get("end station name"),
                Double.parseDouble(record.get("end station latitude")),
                Double.parseDouble(record.get("end station longitude")),
                record.get("bikeid"),
                record.get("usertype"),
                record.get("birth year"),
                genderList.get(Integer.parseInt(record.get("gender"))));
    }

    /**
     * @return Array list consists of data from each category.
     */
    public ArrayList<String> toCSV() {
        ArrayList<String> record = new ArrayList<>();
        record.add(Double.toString(route_duration));
        record.add(dateParser.format(start_time));
        record.add(dateParser.format(stop_time));
        record.add(start_station_id);
        record.add(start_station_name);
        record.add(Double.toString(start_station_latitude));
        record.add(Double.toString(start_station_longitude));
        record.add(end_station_id);
        record.add(end_station_name);
        record.add(Double.toString(end_station_latitude));
        record.add(Double.toString(end_station_longitude));
        record.add(bike_id);
        record.add(user_type);
        record.add(birth_year);
        record.add(Integer.toString(genderList.indexOf(gender)));
        return record;
    }

    /**
     * @param otherObject otherObject is a different route
     * @return boolean Return true if they have the same route. Otherwise return false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || getClass() != otherObject.getClass()) return false;

        Route route = (Route) otherObject;

        if (Double.compare(route.route_duration, route_duration) != 0) return false;
        if (Double.compare(route.start_station_latitude, start_station_latitude) != 0) return false;
        if (Double.compare(route.start_station_longitude, start_station_longitude) != 0) return false;
        if (Double.compare(route.end_station_latitude, end_station_latitude) != 0) return false;
        if (Double.compare(route.end_station_longitude, end_station_longitude) != 0) return false;
        if (Double.compare(route.routeDistance, routeDistance) != 0) return false;
        if (!start_time.equals(route.start_time)) return false;
        if (!stop_time.equals(route.stop_time)) return false;
        if (!start_station_id.equals(route.start_station_id)) return false;
        if (!start_station_name.equals(route.start_station_name)) return false;
        if (!end_station_id.equals(route.end_station_id)) return false;
        if (!end_station_name.equals(route.end_station_name)) return false;
        if (!bike_id.equals(route.bike_id)) return false;
        if (!user_type.equals(route.user_type)) return false;
        if (birth_year != null ? !birth_year.equals(route.birth_year) : route.birth_year != null) return false;
        return gender != null ? gender.equals(route.gender) : route.gender == null;
    }

    /**
     * Convert the retailer class to hash code.
     *
     * @return integer consists of hash code for this class.
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(route_duration);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + start_time.hashCode();
        result = 31 * result + stop_time.hashCode();
        result = 31 * result + start_station_id.hashCode();
        result = 31 * result + start_station_name.hashCode();
        temp = Double.doubleToLongBits(start_station_latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(start_station_longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + end_station_id.hashCode();
        result = 31 * result + end_station_name.hashCode();
        temp = Double.doubleToLongBits(end_station_latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(end_station_longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + bike_id.hashCode();
        result = 31 * result + user_type.hashCode();
        result = 31 * result + (birth_year != null ? birth_year.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        temp = Double.doubleToLongBits(routeDistance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * @return double which is a value of a route duration
     */
    public double getRouteDuration() {

        return route_duration;
    }

    /**
     * @param distance new duration.
     */
    public void setRouteDuration(Double distance) {

        this.route_duration = distance;
    }

    /**
     * @return String containing route starting time
     */
    public String getRouteStartTime() {

        return dateParser.format(start_time);
    }

    /**
     * @param time new starting time.
     * @throws ParseException When the string does not contain a valid time.
     */
    public void setRouteStartTime(String time) throws ParseException {

        this.start_time = dateParser.parse(time);
    }

    /**
     * @return String containing rotue end time
     */
    public String getRouteEndTime() {

        return dateParser.format(stop_time);
    }

    /**
     * @param time new ending time.
     * @throws ParseException When the string does not contain a valid time.
     */

    public void setRouteEndTime(String time) throws ParseException {

        this.stop_time = dateParser.parse(time);
    }

    /**
     * Returns the date/time the route was taken
     *
     * @return The date/time as a Date object.
     */
    public Date getDate() {
        return start_time;
    }

    /**
     * @return String containing route start station ID
     */
    public String getRouteStartStationID() {

        return start_station_id;
    }

    /**
     * @param id new start staion ID
     */
    public void setRouteStartStationID(String id) {

        this.start_station_id = id;
    }

    /**
     * @return String containing route start station name.
     */
    public String getRouteStartStationName() {

        return start_station_name;
    }

    /**
     * @param name new start station name.
     */
    public void setRouteStartStationName(String name) {

        this.start_station_name = name;
    }

    /**
     * @return Double which is a latitude of the start station.
     */
    public double getRouteStartStationLatitude() {

        return start_station_latitude;
    }

    /**
     * @param coordinate new start station latitude
     */
    public void setRouteStartStationLatitude(Double coordinate) {

        this.start_station_latitude = coordinate;
    }

    /**
     * @return Double which is a longitude of the start station.
     */
    public double getRouteStartStationLongitude() {

        return start_station_longitude;
    }

    /**
     * @param coordinate new start station longitude
     */
    public void setRouteStartStationLongitude(Double coordinate) {

        this.start_station_longitude = coordinate;
    }

    /**
     * @return String containing route end station ID
     */
    public String getRouteEndStationID() {

        return end_station_id;
    }

    /**
     * @param id new end station ID.
     */
    public void setRouteEndStationID(String id) {

        this.end_station_id = id;
    }

    /**
     * @return String containing route end station name.
     */
    public String getRouteEndStationName() {

        return end_station_name;
    }

    /**
     * @param name new end station name
     */
    public void setRouteEndStationName(String name) {

        this.end_station_name = name;
    }

    /**
     * @return Double which is a latitude of the end station.
     */
    public double getRouteEndStationLatitude() {

        return end_station_latitude;
    }

    /**
     * @param coordinate new end station latitude
     */
    public void setRouteEndStationLatitude(Double coordinate) {

        this.end_station_latitude = coordinate;
    }

    /**
     * @return Double which is a longitude of the end station.
     */
    public double getRouteEndStationLongitude() {

        return end_station_longitude;
    }

    /**
     * @param coordinate new end station longitude
     */
    public void setRouteEndStationLongitude(Double coordinate) {

        this.end_station_longitude = coordinate;
    }

    /**
     * @return String containing the bike ID
     */
    public String getRouteBikeID() {

        return bike_id;
    }

    /**
     * @param id new Bike ID.
     */
    public void setRouteBikeID(String id) {

        this.bike_id = id;
    }

    /**
     * @return String containing user type
     */
    public String getRouteUserType() {

        return user_type;
    }

    /**
     * @param type new user type
     */
    public void setRouteUserType(String type) {

        this.user_type = type;
    }

    /**
     * @return String containing date of birth
     */
    public String getRouteBirthYear() {

        return birth_year;
    }

    /**
     * @param year new date of birth
     */
    public void setRouteBirthYear(String year) {

        this.birth_year = year;
    }

    /**
     * @return String containing gender
     */
    public String getRouteGender() {

        return gender;
    }

    /**
     * @param gender new gender
     */
    public void setRouteGender(String gender) {

        this.gender = gender.toLowerCase();
    }

    /**
     * @return double containing route distance.
     */
    public double getRouteDistance() {
        return routeDistance;
    }

    /**
     * Calculates the midpoint of the route
     *
     * @param lat1 latitude of first point
     * @param lat2 latitude of second point
     * @param lon1 longitude of first point
     * @param lon2 longitude of second point
     * @return a Location object of the midpoint of the route
     */
    public Location midPoint(double lat1, double lat2, double lon1, double lon2) {

        double dLon = Math.toRadians(lon2 - lon1);

        //convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

        double latdeg = Math.toDegrees(lat3);
        double londeg = Math.toDegrees(lon3);

        return new Location(latdeg, londeg);
    }


    /**
     * Takes an ObservableList of WiFi objects and returns the closest WiFi objects in a radius
     *
     * @param wifiArray ArrayList of all wifi points
     * @return The WiFi object that is closest to the route
     */
    public ArrayList<WiFi> getClosestWifi(ObservableList<WiFi> wifiArray) {


        double startLat = this.getRouteStartStationLatitude();
        double endLat = this.getRouteEndStationLatitude();
        double startLon = this.getRouteStartStationLongitude();
        double endLon = this.getRouteEndStationLongitude();

        Location midPoint = this.midPoint(startLat, endLat, startLon, endLon);
        double radius = midPoint.calculateDistance(new Location(startLat, startLon));


        ArrayList<WiFi> closestWiFiList = new ArrayList<>();

        for (WiFi wifi : wifiArray) {

            if (wifi.getLocation().calculateDistance(midPoint) <= radius) {
                closestWiFiList.add(wifi);
            }

        }

        return closestWiFiList;
    }

    /**
     * Takes an ObservableList of BikeStation objects and returns the closest BikeStation objects to the start and end of route
     *
     * @param bikeStationArray List of BikeStations
     * @return List with closest bike station at the start and end of route
     */
    public ArrayList<BikeStation> getClosestBikeStation(ObservableList<BikeStation> bikeStationArray) {
        double startLat = this.getRouteStartStationLatitude();
        double endLat = this.getRouteEndStationLatitude();
        double startLon = this.getRouteStartStationLongitude();
        double endLon = this.getRouteEndStationLongitude();

        double startDistance = Double.MAX_VALUE;
        double endDistance = Double.MAX_VALUE;
        ArrayList<BikeStation> closestBikeStation = new ArrayList<>();

        for (BikeStation bikeStation : bikeStationArray) {
            if (bikeStation.getLocation().calculateDistance(new Location(startLat, startLon)) < startDistance) {
                startDistance = bikeStation.getLocation().calculateDistance(new Location(startLat, startLon));
                if (closestBikeStation.size() != 0) {
                    closestBikeStation.remove(0);
                }
                closestBikeStation.add(bikeStation);
            }
        }

        for (BikeStation bikeStation : bikeStationArray) {
            if (bikeStation.getLocation().calculateDistance(new Location(endLat, endLon)) < endDistance) {
                endDistance = bikeStation.getLocation().calculateDistance(new Location(endLat, endLon));
                if (closestBikeStation.size() != 1) {
                    closestBikeStation.remove(1);
                }
                closestBikeStation.add(bikeStation);
            }
        }

        return closestBikeStation;
    }


    /**
     * Takes an ObservableList of Retailer objects and returns the closest Retailer objects in a radius
     *
     * @param retailerArray ArrayList of all wifi points
     * @return The Retailer object that is closest to the route
     */
    public ArrayList<Retailer> getClosestRetailer(ObservableList<Retailer> retailerArray) {


        double startLat = this.getRouteStartStationLatitude();
        double endLat = this.getRouteEndStationLatitude();
        double startLon = this.getRouteStartStationLongitude();
        double endLon = this.getRouteEndStationLongitude();

        Location midPoint = this.midPoint(startLat, endLat, startLon, endLon);
        double radius = midPoint.calculateDistance(new Location(startLat, startLon));


        ArrayList<Retailer> closestRetailerList = new ArrayList<>();

        for (Retailer retailer : retailerArray) {

            if (retailer.getLocation().calculateDistance(midPoint) <= radius) {
                closestRetailerList.add(retailer);
            }

        }

        return closestRetailerList;
    }
}