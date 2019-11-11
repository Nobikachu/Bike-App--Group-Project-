package Model;

import javafx.collections.ObservableList;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;

/**
 * A Retailer object, Encapsulates the data provided by Data.gov into an object
 */
public class Retailer extends PointOfInterest {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String primary;
    private String secondary;
    private String zip;


    /**
     * Default Retailer constructor
     *
     * @param location     the location of the retailer
     * @param name         the name of the retailer
     * @param addressLine1 the address line 1 of the retailer
     * @param addressLine2 the address line 2 of the retailer
     * @param city         the city in which the retailer is located
     * @param state        the state in which the retailer is located
     * @param primary      the primary service the retailer offers
     * @param secondary    the secondary service the retailer offers
     * @param zip          the zipcode of the retailer
     */
    public Retailer(Location location, String name, String addressLine1, String addressLine2, String city, String state, String primary, String secondary, String zip) {
        super(location, name);
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.primary = primary;
        this.secondary = secondary;
        this.zip = zip;
    }

    /**
     * A retailer constructor which takes an entry from a CSV file and creates a retailer object
     *
     * @param record the CSVRecord containing the Retailer data.
     */
    public Retailer(CSVRecord record) {
        this(new Location(Double.parseDouble(record.get("Lat")), Double.parseDouble(record.get("Long"))),
                record.get("CnBio_Org_Name"),
                record.get("CnAdrPrf_Addrline1"),
                record.get("CnAdrPrf_Addrline2"),
                record.get("CnAdrPrf_City"),
                record.get("CnAdrPrf_State"),
                record.get("Primary"),
                record.get("Secondary"),
                record.get("CnAdrPrf_ZIP"));
    }

    /**
     * @return String containing address line 1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * @param addressLine1 new address line 1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * @return String containing address line 2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * @param addressLine2 new address line 2
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * @return String containing city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return returns a string containing state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state new state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return String containing primary
     */
    public String getPrimary() {
        return primary;
    }

    /**
     * @param primary new primary
     */
    public void setPrimary(String primary) {
        this.primary = primary;
    }

    /**
     * @return String containing secondary
     */
    public String getSecondary() {
        return secondary;
    }

    /**
     * @param secondary new secondary
     */
    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    /**
     * @param otherObject otherObject is a different retailer
     * @return boolean Return true if they have the same retailer. Otherwise return false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || getClass() != otherObject.getClass()) return false;

        Retailer retailer = (Retailer) otherObject;

        if (!addressLine1.equals(retailer.addressLine1)) return false;
        if (addressLine2 != null ? !addressLine2.equals(retailer.addressLine2) : retailer.addressLine2 != null)
            return false;
        if (!city.equals(retailer.city)) return false;
        if (!state.equals(retailer.state)) return false;
        if (!primary.equals(retailer.primary)) return false;
        if (secondary != null ? !secondary.equals(retailer.secondary) : retailer.secondary != null) return false;
        return zip != null ? zip.equals(retailer.zip) : retailer.zip == null;
    }

    /**
     * Convert the retailer class to hash code.
     *
     * @return integer consists of hash code for this class.
     */
    @Override
    public int hashCode() {
        int result = addressLine1.hashCode();
        result = 31 * result + (addressLine2 != null ? addressLine2.hashCode() : 0);
        result = 31 * result + city.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + primary.hashCode();
        result = 31 * result + (secondary != null ? secondary.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }

    /**
     * Gets the street name of a retailer from its address
     *
     * @return Street name as a string
     */
    public String getStreetName() {
        String address = this.getAddressLine1();
        if (address.length() > 0) { //Checks if it's a valid address.
            String[] street = address.split(" ");
            StringBuilder result = new StringBuilder();
            if (Character.isDigit(street[1].charAt(0))) { //Case when the address has two number before the street name.
                for (int i = 2; i < street.length; i++) {
                    result.append(street[i]).append(" ");
                }
            } else {
                for (int i = 1; i < street.length; i++) {
                    result.append(street[i]).append(" ");
                }
            }
            return result.deleteCharAt(result.length() - 1).toString();
        }
        return "";
    }

    /**
     * @return Array list consists of data from each category.
     */
    public ArrayList<String> toCSV() {
        ArrayList<String> record = new ArrayList<>();
        record.add(getName());
        record.add(addressLine1);
        record.add(addressLine2);
        record.add(city);
        record.add(state);
        record.add(zip);
        record.add(primary);
        record.add(secondary);
        record.add(Double.toString(getLocation().getLatitude()));
        record.add(Double.toString(getLocation().getLongitude()));

        return record;
    }

    /**
     * @return String containing ZIP
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip new zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return Returns the name of the retailer.
     */
    @Override
    public String toString() {
        return getName();
    }


    /**
     * Returns the closest WiFi hotspot to the retailer
     *
     * @param wifiArray ArrayList of all WiFi points
     * @return The WiFi object that is closest to the route
     */
    public WiFi getClosestRetailerWiFi(ObservableList<WiFi> wifiArray) {

        WiFi closestWifi = null;

        double smallestDistance = Double.POSITIVE_INFINITY;
        for (WiFi p : wifiArray) {
            if (this.getLocation().calculateDistance(p.getLocation()) < smallestDistance) {
                smallestDistance = this.getLocation().calculateDistance(p.getLocation());
                closestWifi = p;
            }
        }

        return closestWifi;
    }


}