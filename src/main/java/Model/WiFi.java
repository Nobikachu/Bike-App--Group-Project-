package Model;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * A WiFi hotspot object, Encapsulates the data provided by Data.gov into an object
 */
public class WiFi extends PointOfInterest {
    private int objectId;
    private String borough;
    private String type;
    private String provider;
    private String remarks;
    private String city;
    private String ssid;

    /**
     * Default constructor for the WiFi object
     *
     * @param location the location of the hotspot
     * @param name     the name of the hotspot
     * @param objectId the ID of the hotspot
     * @param borough  the borough in which the hotspot is located
     * @param type     the type of the hotspot
     * @param provider the provider of the hotspot
     * @param remarks  any pertinent remarks about the hotspot
     * @param city     the city in which the hotspot is located
     * @param ssid     the SSID of the hotspot
     */
    public WiFi(Location location, String name, int objectId, String borough, String type, String provider, String remarks, String city, String ssid) {
        super(location, name);
        this.objectId = objectId;
        this.borough = borough;
        this.type = type;
        this.provider = provider;
        this.remarks = remarks;
        this.city = city;
        this.ssid = ssid;
    }

    /**
     * Takes a CSVRecord and creates a WiFi object out of it
     *
     * @param record the record containing the WiFi data
     */
    public WiFi(CSVRecord record) {
        this(new Location(Double.parseDouble(record.get("LAT")), Double.parseDouble(record.get("LON"))),
                record.get("NAME"),
                Integer.parseInt(record.get("OBJECTID")),
                record.get("BORO"),
                record.get("TYPE"),
                record.get("PROVIDER"),
                record.get("REMARKS"),
                record.get("CITY"),
                record.get("SSID"));
    }

    /**
     * @param otherObject otherObject is a different WiFi
     * @return boolean Return true if they have the same WiFi. Otherwise return false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || getClass() != otherObject.getClass()) return false;

        WiFi wiFi = (WiFi) otherObject;

        if (objectId != wiFi.objectId) return false;
        if (!borough.equals(wiFi.borough)) return false;
        if (!type.equals(wiFi.type)) return false;
        if (!provider.equals(wiFi.provider)) return false;
        if (remarks != null ? !remarks.equals(wiFi.remarks) : wiFi.remarks != null) return false;
        if (!city.equals(wiFi.city)) return false;
        return ssid.equals(wiFi.ssid);
    }

    /**
     * Convert the retailer class to hash code.
     *
     * @return integer consists of hash code for this class.
     */
    @Override
    public int hashCode() {
        int result = objectId;
        result = 31 * result + borough.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + provider.hashCode();
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + city.hashCode();
        result = 31 * result + ssid.hashCode();
        return result;
    }

    /**
     * @return Array list consists of data from each category.
     */
    public List toCSV() {
        ArrayList<String> record = new ArrayList<>();
        record.add(Integer.toString(objectId));
        record.add(borough);
        record.add(type);
        record.add(provider);
        record.add(getName());
        record.add(Double.toString(getLocation().getLatitude()));
        record.add(Double.toString(getLocation().getLongitude()));
        record.add(remarks);
        record.add(city);
        record.add(ssid);
        return record;
    }

    /**
     * @return integer contains the object ID.
     */
    public int getObjectId() {
        return objectId;
    }

    /**
     * @param objectId new object ID
     */
    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    /**
     * @return String containing borough
     */
    public String getBorough() {
        return borough;
    }

    /**
     * @param borough new borough
     */
    public void setBorough(String borough) {
        this.borough = borough;
    }

    /**
     * @return String containing wifi type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type new wifi type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return String containing wifi provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * @param provider new provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * @return String containing remarks.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks new remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
     * @return String containing ssid
     */
    public String getSsid() {
        return ssid;
    }

    /**
     * @param ssid new ssid
     */
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }


}
