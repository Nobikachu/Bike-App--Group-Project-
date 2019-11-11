package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.util.Optional;

/**
 * Class which is used to show alerts to the user to inform or warn them of invalid actions.
 */
public abstract class UserAlerts {
    /**
     * Alters the user on invalid inputs they have entered.
     *
     * @param message The message displayed to the user using the dialogue.
     */
    public static void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    /**
     * Alters the user with an warning message
     *
     * @param message The message displayed to the user using the dialogue.
     * @return Returns a string containing the user's decision.
     */
    public static String warning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> input = alert.showAndWait();
        return input.map(ButtonType::getText).orElse(null);
    }
}
