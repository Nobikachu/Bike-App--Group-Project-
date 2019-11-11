package Main;

import Data.Settings;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * First method called when starting the GUI. Create the stage and sets the default view of the map
 * with raw data points and set that as the scene.
 */
public class Main extends Application {

    public static TabPane tabPane;
    public static Stage copyPrimaryStage;

    /**
     * Method used to close the application as well as triggering the onCloseRequest to save all entries in the list
     * view to CSV files.
     */
    public static void closeApplicationRequest() {
        copyPrimaryStage.fireEvent(new WindowEvent(copyPrimaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialize the GUI.
     *
     * @param primaryStage Primary stage of the GUI
     * @throws Exception Throws exception when the fxml does not exists.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent mainRoot = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("goBike");
        primaryStage.setScene(new Scene(mainRoot, 1025, 650));
        primaryStage.setResizable(false);

        copyPrimaryStage = primaryStage;

        //When user clicks on the close button (red 'X' in the top right) it will show the
        //saving progress in a pop up and commence saving the files. Pop up is closed when the
        //saving is completed.
        primaryStage.setOnCloseRequest(event -> {
            Group savingRoot = new Group();
            Scene savingScene = new Scene(savingRoot, 300, 150);
            Stage savingProgressStage = new Stage();

            savingProgressStage.initStyle(StageStyle.UNDECORATED); //User can't close/minimize/maximize popup
            savingProgressStage.setScene(savingScene);

            Label messageLbl = new Label();
            messageLbl.setText("Saving files. Please wait.");

            ProgressBar progressBar = new ProgressBar();
            progressBar.setProgress(-1); //Progress is undeterminable

            final VBox vb = new VBox();
            vb.setSpacing(5);
            vb.setAlignment(Pos.CENTER);
            vb.getChildren().add(messageLbl);
            vb.getChildren().add(progressBar);
            savingScene.setRoot(vb);

            savingProgressStage.setAlwaysOnTop(true);
            savingProgressStage.show();

            Settings.saveFiles(savingProgressStage);
        });

        ObservableList<Node> nodes = mainRoot.getChildrenUnmodifiable();
        tabPane = (TabPane) nodes.get(1);
    }
}