package GUI;

import Data.Statistic;
import Model.Location;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller to display statistic about the selected route.
 */
public class StatisticController implements Initializable {
    //Static controller class for other classes to call it to update the statistic.
    public static StatisticController controller;
    @FXML
    private TextField currentLocationTextField;
    @FXML
    private TextField destinationTextField;
    @FXML
    private TextField distanceTextField;
    @FXML
    private TextField timeTextField;
    @FXML
    private TextField petrolTextField;
    @FXML
    private TextField dieselTextField;
    @FXML
    private TextField carbonPetrolTextField;
    @FXML
    private TextField carbonDieselTextField;
    @FXML
    private TextField weightLossTextField;
    @FXML
    private TextField cycleSpeedTextField;
    @FXML
    private TextField weightTextField;
    @FXML
    private Button recalculateButton;
    @FXML
    private Label errorLabel;
    //Used to calculate the statistics.
    private Statistic stat = new Statistic();
    //Strings to update the text fields.
    private String origin;
    private String destination;
    private String distance;
    private String time;
    private String petrolSaved;
    private String dieselSaved;
    private String carbonPetrol;
    private String carbonDiesel;
    private String calories;
    //Default weight and speed.
    private double weight = 60;
    private double averageSpeed = 16;
    //Keeps track of the total distance between two points.
    private double totalDistance;

    /**
     * @param origin      String containing the name of the origin
     * @param destination String containing the name of the destination.
     * @param originCoord Coordinate of the origin.
     * @param destCoord   Coordinate of the destination.
     */
    public void initData(String origin, String destination, Location originCoord, Location destCoord) {
        this.origin = origin;
        this.destination = destination;
        totalDistance = originCoord.calculateDistance(destCoord);
        distance = String.format("%.02f Kilometers", totalDistance);
        double time = stat.calculateTime(totalDistance, averageSpeed);
        this.time = String.format("%.02f Minutes", time);
        double petrol = stat.petrolSavedCalculation(totalDistance);
        double diesel = stat.dieselSavedCalculation(totalDistance);
        petrolSaved = String.format("%.02f Litres", petrol);
        dieselSaved = String.format("%.02f Litres", diesel);
        double carbonP = stat.calculateCO2Petrol(petrol, totalDistance);
        double carbonD = stat.calculateCO2Diesel(petrol, totalDistance);
        carbonPetrol = String.format("%.05f CO2 grams", carbonP);
        carbonDiesel = String.format("%.05f CO2 grams", carbonD);
        double caloriesBurned = stat.calculateCaloriesBurned(totalDistance, weight);
        calories = String.format("%.02f Calories burned", caloriesBurned);
    }

    /**
     * Updates the text field.
     */
    public void setTextField() {
        currentLocationTextField.setText(origin);
        destinationTextField.setText(destination);
        distanceTextField.setText(distance);
        timeTextField.setText(time);
        petrolTextField.setText(petrolSaved);
        dieselTextField.setText(dieselSaved);
        carbonPetrolTextField.setText(carbonPetrol);
        carbonDieselTextField.setText(carbonDiesel);
        weightLossTextField.setText(calories);
        cycleSpeedTextField.setText(Double.toString(averageSpeed));
        weightTextField.setText(Double.toString(weight));
        recalculateButton.setDisable(false);
    }

    /**
     * Recalculates statistics.
     */
    @FXML
    void recalculateStat() {
        try {
            averageSpeed = Double.parseDouble(cycleSpeedTextField.getText());
            weight = Double.parseDouble(weightTextField.getText());
            //Checks if the user's input is appropriate values.
            if (averageSpeed < 5.0) {
                averageSpeed = 5.0;
            } else if (averageSpeed > 40.0) {
                averageSpeed = 40.0;
            } else if (weight < 30.0) {
                weight = 30.0;
            } else if (weight > 1000.0) {
                weight = 1000.0;
            }
            errorLabel.setVisible(false);
            cycleSpeedTextField.setText(Double.toString(averageSpeed));
            weightTextField.setText(Double.toString(weight));
            double time = stat.calculateTime(totalDistance, averageSpeed);
            this.time = String.format("%.02f Minutes", time);
            timeTextField.setText(this.time);
            double caloriesBurned = stat.calculateCaloriesBurned(totalDistance, weight);
            calories = String.format("%.02f Calories burned", caloriesBurned);
            weightLossTextField.setText(calories);
        } catch (NumberFormatException e) {
            errorLabel.setVisible(true);
        }
    }

    /**
     * Initialize the Statistic controller class.
     */
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
    }


}
