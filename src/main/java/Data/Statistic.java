package Data;

/**
 * Calculates estimated statistics.
 */
public class Statistic {
    /**
     * @param distance distance in kilometer.
     * @return Calculates the amount of petrol saved in litres.
     */
    public double petrolSavedCalculation(double distance) {
        return distance / 10; //Petrol engine in average uses 1 litres per 10 kilometers.
    }

    /**
     * @param distance distance in kilometer.
     * @return Calculates the amount of diesel saved in litres.
     */
    public double dieselSavedCalculation(double distance) {
        return distance / 3; //Diesel engine in average uses 1 litres per 3 kilometers.
    }

    /**
     * @param litres amount of petrol in litres
     * @return Calculates the amount of CO2 produced in a given amount of litres.
     */
    public double calculateCO2Petrol(double litres, double distance) {
        return ((litres / 100.0) * 22.961) * distance;
    }

    /**
     * @param litres amount of diesels in litres
     * @return Calculates the amount of CO2 produced in a given amount of litres.
     */
    public double calculateCO2Diesel(double litres, double distance) {
        return ((litres / 100.0) * 26.050) * distance;
    }

    /**
     * @param distance distance in kilometer.
     * @param weight   The user's weight in kilograms
     * @return Calculates the amount of calories burned.
     */
    public double calculateCaloriesBurned(double distance, double weight) {
        double pounds = weight * 2.20462262; //Converting kilograms to pounds.
        return pounds * (distance * 0.621371192) * 0.256032;
    }

    /**
     * @param distance     between two points in kilometres as a double
     * @param averageSpeed The user's average speed.
     * @return estimated time in minutes as a double.
     */
    public double calculateTime(double distance, double averageSpeed) {
        return (distance / averageSpeed) * 60.0;
    }
}
