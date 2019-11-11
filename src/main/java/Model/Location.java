package Model;

/**
 * Stores a coordinate (latitude, longitude) and provides simple functionality using the coordinates.
 */
public class Location {
    private double longitude;
    private double latitude;

    /**
     * Create a Location object
     *
     * @param latitude  latitude of coordinate
     * @param longitude longitude of coordinate
     */
    public Location(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Setter for the latitude.
     *
     * @param newLatitude The new latitude.
     */
    public void setLatitude(Double newLatitude) {
        this.latitude = newLatitude;
    }

    /**
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Setter for the longitude.
     *
     * @param newLongitude The new longitude.
     */
    public void setLongitude(Double newLongitude) {
        this.longitude = newLongitude;
    }


    /**
     * @param otherObject otherObject is a different location
     * @return boolean Return true if they have the same location. Otherwise return false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || getClass() != otherObject.getClass()) return false;

        Location location = (Location) otherObject;

        return Double.compare(location.longitude, longitude) == 0 && Double.compare(location.latitude, latitude) == 0;
    }

    /**
     * Convert the location class to hash code.
     *
     * @return integer consists of hash code for this class.
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(longitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Takes two locations and returns the distance between the two points.
     *
     * @param location point to calculate distance from
     * @return distance between two points in kilometres as a double
     */
    public double calculateDistance(Location location) {

        double lat1 = getLatitude();
        double lat2 = location.getLatitude();
        double long1 = getLongitude();
        double long2 = location.getLongitude();

        double radLat1 = Math.PI * (lat1 / 180);
        double radLat2 = Math.PI * (lat2 / 180);
        double theta = long1 - long2;
        double radTheta = Math.PI * (theta / 180);

        double distance = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);
        distance = Math.acos(distance);
        distance *= (180 / Math.PI);
        distance *= 60 * 1.1515;
        distance *= 1.609344;

        return distance;
    }

    /**
     * Custom string represenation of the Location object. Returns the latitude, longtitude pair of coordinates.
     *
     * @return String representation of location object as (latitude, longitude)
     */
    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", this.latitude, this.longitude);
    }
}
