package Data;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Class which is used to retrieve the settings for the which list of points of interest to be displayed.
 * <p>
 * Created by xjm10 on 21/09/17.
 */
public abstract class Settings {

    public static String retailerFolderDirectory;
    public static String routeFolderDirectory;
    public static String wifiFolderDirectory;
    public static String bikeStationFolderDirectory;
    private static String folderDirectory; //The directory containing the different types of CSV files.
    private static String favoriteFolderDirectory;


    /**
     * Attempts to create the directories to store all the CSV files, a parent directory will hold all the individual
     * CSV's one for each datatype. The parent directory, 'CSVFiles', will be created at in the same directory
     * as the jar. Directories will not be created if they already exist.
     *
     * @throws Exception Exception is thrown if a folder fails to be created.
     */
    public static void setUpDirectories() throws Exception {

        createFolderDirectoryPaths();

        //The directory which will hold all the different folders for each CSV type
        File parentFolder = new File(folderDirectory);

        if (parentFolder.exists()) {
            //Do nothing
            System.out.println("The parent folder already exists.");
        } else {
            //Need to create the parent folder
            boolean isSuccess = parentFolder.mkdir();
            if (isSuccess) {
                System.out.println("Created the parent folder.");
            } else {
                throw new Exception("Failed to create the parent folder.");
            }

        }

        //Attempt to create the folder which will hold all the retailer CSV Files

        File retailerFolder = new File(retailerFolderDirectory);

        if (retailerFolder.exists()) {
            //Do nothing
            System.out.println("The retailer folder already exists.");
        } else {
            //Need to create the retailer folder
            boolean isSuccess = retailerFolder.mkdir();
            if (isSuccess) {
                System.out.println("The retailer folder was created.");
            } else {
                throw new Exception("Failed to create the retailer folder.");
            }
        }

        //Attempt to create the folder which will hold all the route CSV Files
        File routeFolder = new File(routeFolderDirectory);
        if (routeFolder.exists()) {
            //Do nothing
            System.out.println("The route folder already exists.");
        } else {
            //Need to create the route folder
            boolean isSuccess = routeFolder.mkdir();
            if (isSuccess) {
                System.out.println("The route folder was created.");
            } else {
                throw new Exception("Failed to create the route folder.");
            }
        }

        //Attempt to create the folder which will hold all the wifi CSV Files
        File wifiFolder = new File(wifiFolderDirectory);
        if (wifiFolder.exists()) {
            //Do nothing
            System.out.println("The wifi folder already exists.");
        } else {
            //Need to create the WiFi folder
            boolean isSuccess = wifiFolder.mkdir();
            if (isSuccess) {
                System.out.println("The WiFi folder was created.");
            } else {
                throw new Exception("Failed to create the WiFi folder.");
            }
        }

        //Attempt to create the folder which will hold all the bike station CSV files.
        File bikeStationFolder = new File(bikeStationFolderDirectory);
        if (bikeStationFolder.exists()) {
            //Do nothing
            System.out.println("The bike station folder already exists.");
        } else {
            //Need to create the bike station folder
            boolean isSuccess = bikeStationFolder.mkdir();
            if (isSuccess) {
                System.out.println("The bike station folder was created.");
            } else {
                throw new Exception("Failed to create the bike station folder.");
            }
        }

        //Attempt to create the folder which will hold all the favorites CSV for each data type
        File favoriteFolder = new File(favoriteFolderDirectory);
        if (favoriteFolder.exists()) {
            //Do nothing
            System.out.println("The favorite folder already exists.");
        } else {
            //Need to create the Favourites folder
            boolean isSuccess = favoriteFolder.mkdir();
            if (isSuccess) {
                System.out.println("The Favorite folder was created.");
            } else {
                throw new Exception("Failed to create the Favorite folder.");
            }
        }


    }

    /**
     * Create the pop up which will show the loading bar.
     *
     * @param message A loading message.
     * @return Returned task will be used to close the pop up at a later point.
     */
    private static Stage showProgress(String message) {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 325);
        Stage stage = new Stage();

        stage.initStyle(StageStyle.UNDECORATED); //User can't close/minimize/maximize popup
        stage.setScene(scene);

        Text styleMessage = new Text(message);
        styleMessage.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 13));
        styleMessage.setFill(Color.BEIGE);

        Label messageLbl = new Label("", styleMessage);


        Image background = new Image("GUI/background.gif");
        ImageView imageView = new ImageView(background);
        imageView.setFitHeight(325);
        imageView.setFitWidth(600);
        Label backgroundLabel = new Label("", imageView);

        Image loading = new Image("GUI/loading.gif");
        ImageView imageView1 = new ImageView(loading);
        imageView1.setFitHeight(20);
        imageView1.setFitWidth(20);
        Label loadingLabel = new Label("", imageView1);

        VBox first = new VBox();
        first.setAlignment(Pos.BOTTOM_RIGHT);
        first.getChildren().add(loadingLabel);
        first.getChildren().add(messageLbl);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundLabel, first);

        final VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(stackPane);


        scene.setRoot(vb);

        stage.setAlwaysOnTop(true);
        stage.show();
        return stage;
    }

    /**
     * Calls support functions to load stored CSV's. A task is called to load the CSV files
     * on a thread, hence the application will be running with a loading pop up while
     * CSV files are being loaded. Upon completion of loading the CSV the popup will close
     * and the data will be displayed in the application.
     */
    public static void loadFiles() {
        //Create the stage that will be used to show the scene
        Stage stage = showProgress("GoBike is Loading. Please wait");
        LoadCSVTask loadTask = new LoadCSVTask(stage, retailerFolderDirectory, routeFolderDirectory,
                wifiFolderDirectory, bikeStationFolderDirectory, favoriteFolderDirectory);
        Thread loadCSVThread = new Thread(loadTask);
        loadCSVThread.start();
    }


    /**
     * Runs the task concurrently to save the files to the specified directories. At the end of the task
     * (which saves the files) it will also close the stage which is the pop up.
     *
     * @param stage The stage holds the pop up nodes that will be closed when the files are all saved.
     */
    public static void saveFiles(Stage stage) {
        SaveCSVTask savingTask = new SaveCSVTask(stage, retailerFolderDirectory, routeFolderDirectory, wifiFolderDirectory, bikeStationFolderDirectory, favoriteFolderDirectory);
        Thread saveCSVThread = new Thread(savingTask);
        saveCSVThread.start();
    }

    /**
     * Method creates the class path for the parent folder that will hold all the other files.
     */
    private static void createFolderDirectoryPaths() {
        try {
            System.out.println("Getting the directory: " + new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent());
            System.out.println("The file separator: " + File.separatorChar);
            String directoryPath = new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            folderDirectory = directoryPath + File.separatorChar + "CSVFiles" + File.separatorChar;


            retailerFolderDirectory = folderDirectory + "retailerCSVFiles";
            routeFolderDirectory = folderDirectory + "routeCSVFiles";
            wifiFolderDirectory = folderDirectory + "wifiCSVFiles";
            bikeStationFolderDirectory = folderDirectory + "bikeStationCSVFiles";
            favoriteFolderDirectory = folderDirectory + "favoriteCSVFiles";

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
