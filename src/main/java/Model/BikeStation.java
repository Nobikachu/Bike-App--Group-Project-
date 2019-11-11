package Model;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;

/**
 * Class representing a bike station, will have attributes and functionality inherited form points of interest.
 */
public class BikeStation extends PointOfInterest {

    private String name;

    /**
     * Create a bike station location instance.
     *
     * @param location The location of the bike station.
     * @param name     The name of the bike station, typically given as its location in the style of its address.
     */
    public BikeStation(Location location, String name) {
        super(location, name);
        this.name = name;
    }

    /**
     * Overloaded constructor for when the BikeStation instance is created from values retrieved
     * from a CSV record.
     *
     * @param record Record entry from the CSV file that contains the values for the bike station entry.
     */
    public BikeStation(CSVRecord record) {
        this(new Location(Double.parseDouble(record.get("bikestation_latitude")),
                        Double.parseDouble(record.get("bikestation_longitude"))),
                record.get("bikestation_name"));
    }


    /**
     * Create a record of the bike station instance that will be saved to a CSV file.
     *
     * @return Array list consists of data from each category.
     */
    public ArrayList<String> toCSV() {
        ArrayList<String> record = new ArrayList<>();
        record.add(Double.toString(getLocation().getLatitude()));
        record.add(Double.toString(getLocation().getLongitude()));
        record.add(getName());
        return record;
    }

    /**
     * Customized comparison method to check if each attribute is the same.
     *
     * @param otherObject Object to compare with
     * @return If the objects are the same or not (boolean).
     */
    @Override
    public boolean equals(Object otherObject) {
        //Instantly return false if the other object does not exist or if the class is not a bike station
        if (otherObject == null || this.getClass() != otherObject.getClass()) {
            return false;
        }

        BikeStation otherBikeStation = (BikeStation) otherObject;

        return this.getLocation().equals(otherBikeStation.getLocation())
                && this.getName().equals(otherBikeStation.getName());
    }

    /**
     * Custom method to calculate the hash code
     *
     * @return Calculated hash code for the bike station object.
     */
    @Override
    public int hashCode() {
        int result = 31 * this.name.hashCode();
        result += 31 * this.getLocation().getLatitude();
        result += 31 * this.getLocation().getLongitude();
        return result;
    }

}