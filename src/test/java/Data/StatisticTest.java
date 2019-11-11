package Data;

import junit.framework.TestCase;

/**
 * JUnoit testing for the statistic class
 */
public class StatisticTest extends TestCase {
    Statistic stat = new Statistic();

    /**
     * Checks if it calculates correct amount of petrol saved.
     *
     * @throws Exception Returns the wrong amount of petrol.
     */
    public void testPetrolSavedCalculation() throws Exception {
        assertEquals(20.0, stat.petrolSavedCalculation(200));
    }

    /**
     * Checks if it calculates correct amount of diesel saved.
     *
     * @throws Exception Returns the wrong amount of diesel.
     */
    public void testDieselSavedCalculation() throws Exception {
        assertEquals(18.0, stat.dieselSavedCalculation(54));
    }

    /**
     * Checks if it calculates correct amount of CO2 gas.
     *
     * @throws Exception Returns the wrong amount of CO2 gas.
     */
    public void testCalculateCO2Petrol() throws Exception {
        assertEquals(17.037062, stat.calculateCO2Petrol(3.5, 21.2));
    }

    /**
     * Checks if it calculates correct amount of CO2 gas.
     *
     * @throws Exception Returns the wrong amount of CO2 gas.
     */
    public void testCalculateCO2Diesel() throws Exception {
        assertEquals(19.3291, stat.calculateCO2Diesel(3.5, 21.2));
    }

    /**
     * Checks that the calculated estimate time is the expected time.
     *
     * @throws Exception returns estimate time is outside of the expected time.
     */
    public void testCalculateTime() throws Exception {
        assertEquals(450.0, stat.calculateTime(120.0, 16.0));
    }

    /**
     * Checks that the calculated calories burned is calculated accurately.
     *
     * @throws Exception returns inaccurate amount calories burned.
     */
    public void testCaloriesBurned() throws Exception {
        assertEquals(336.706000016849, stat.calculateCaloriesBurned(12.0, 80.0));
    }
}
