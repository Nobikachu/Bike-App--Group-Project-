package Model;

/**
 * Abstracts the idea of a PointOfInterest to be stored and displayed by the app.
 */
public abstract class PointOfInterest implements Exportable {
    private Location location;
    private String name;

    /**
     * @param location Point of interest's location
     * @param name     Name of the location
     */
    PointOfInterest(Location location, String name) {
        this.location = location;
        this.name = name;
    }

    /**
     * @return the location of the point of interest
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return the name of the location
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Name to change the name of the location.
     */
    public void setName(String name) {
        this.name = name;
    }


}
