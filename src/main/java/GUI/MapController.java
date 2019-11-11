package GUI;


import Data.GeoCoder;
import Main.Main;
import Model.*;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


/**
 * Initialize the map and markers.
 * Handles route directions and adding markers in a correct location.
 */
@SuppressWarnings("FieldCanBeLocal")
public class MapController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    public static GoogleMap map;
    //Variable that will be accessed throughout the application, to implement map functionality (such as showing points
    //of interest on the map and need to pass arguments to the MapController).
    //Is instantiated in the MapInitialization method in the MapController.
    public static MapController controller;
    private static InfoWindow currentInfoWindow = null;
    private static Boolean nearbyWiFiFlag = false;
    private static Boolean nearbyRetailerFlag = false;
    private static Boolean nearbyBikeStationFlag = false;
    private static Boolean nearbyRetailerWiFiFlag = false;
    private static Marker nearbyRetailerWiFiMarker = null;
    private static Boolean clickEnabled = false;
    @FXML
    public CheckBox routeWifiNearby;
    @FXML
    public CheckBox retailerWifiNearby;
    @FXML
    public CheckBox routeBikeStationNearby;
    @FXML
    public Button statButton;
    @FXML
    public Button clearRouteButton;
    @FXML
    public Button clearRetailerButton;
    @FXML
    public Button clearWifiButton;
    @FXML
    public CheckBox routeRetailerNearby;
    @FXML
    public Button clearBikeStationButton;
    @FXML
    public Button createRouteButton;
    private ArrayList<Marker> shownNearbyWiFiArray = new ArrayList<>();
    private ArrayList<Marker> shownNearbyRetailerArray = new ArrayList<>();
    private ArrayList<Marker> shownNearbyBikeStationArray = new ArrayList<>();
    private DirectionsService directionsService;
    private DirectionsPane directionsPane;
    private StringProperty from = new SimpleStringProperty();
    private StringProperty to = new SimpleStringProperty();
    @FXML
    private GoogleMapView mapView;
    @FXML
    private TextField fromTextField;
    @FXML
    private TextField toTextField;
    //The markers currently shown
    private Marker wifiMarker = null;
    private Marker retailerMarker = null;
    private Marker bikeStationMarker = null;
    private DirectionsRenderer directionsDisplay;
    //Used to keep track if the points of interest are shown on the map.
    private boolean isRetailersShown = false;
    private boolean isWiFiShown = false;
    private boolean isBikeStationShown = false;
    //Arrays which contain the markers to be displayed
    private ArrayList<Marker> shownRetailerArray = new ArrayList<>();
    private ArrayList<Marker> shownWifiArray = new ArrayList<>();
    private ArrayList<Marker> shownBikeStationArray = new ArrayList<>();
    private ArrayList<Marker> markerList = new ArrayList<>();


    //https://imgur.com/a/ufJCT

    //The URL to Google Map icons used to display the locations for each data type on the map.
    //Default icon theme is the dark theme.
    private final String ALL_BIKESTATION_ICON_URL = "https://i.imgur.com/2Ln07t2.png";
    private final String SELECTED_BIKESTATION_ICON_URL = "https://i.imgur.com/Ckx7PN7.png";
    private final String NEARBY_BIKESTATION_TO_ROUTE_ICON_URL = "https://i.imgur.com/rUwh6MV.png";

    private final String ALL_WIFI_ICON_URL = "https://i.imgur.com/KKSNi5w.png";
    private final String SELECTED_WIFI_ICON_URL = "https://i.imgur.com/Mv1UdMz.png";
    private final String NEARBY_WIFI_TO_RETAILER_ICON_URL = "https://i.imgur.com/9u3zuRS.png";
    private final String NEARBY_WIFI_TO_ROUTE_ICON_URL = "https://i.imgur.com/Cs6tiyz.png";

    private final String ALL_RETAILER_ICON_URL = "https://i.imgur.com/5QV1Nz5.png";
    private final String SELECTED_RETAILER_ICON_URL = "https://i.imgur.com/ZxPV9fV.png";
    private final String NEARBY_RETAILER_TO_ROUTE_ICON_URL = "https://i.imgur.com/hd7qT60.png";


    //The URL to Google Map icons used to display the locations for each data type on the map.
    //Icons for the light theme.
    //https://imgur.com/a/iRbwg
    private String ALL_BIKESTATION_ICON_URL_LIGHT = "https://i.imgur.com/YQ6tJa1.png";
    private String SELECTED_BIKESTATION_ICON_URL_LIGHT = "https://i.imgur.com/86X4RcY.png";
    private String NEARBY_BIKESTATION_TO_ROUTE_ICON_URL_LIGHT = "https://i.imgur.com/8LWTttD.png";

    private String ALL_WIFI_ICON_URL_LIGHT = "https://i.imgur.com/RC46HvW.png";
    private String SELECTED_WIFI_ICON_URL_LIGHT = "https://i.imgur.com/eAp7gMj.png";
    private String NEARBY_WIFI_TO_RETAILER_ICON_URL_LIGHT = "https://i.imgur.com/p23Luyd.png";
    private String NEARBY_WIFI_TO_ROUTE_ICON_URL_LIGHT = "https://i.imgur.com/35Fz43f.png";

    private String ALL_RETAILER_ICON_URL_LIGHT = "https://i.imgur.com/DNWrvpw.png";
    private String SELECTED_RETAILER_ICON_URL_LIGHT = "https://i.imgur.com/MNFm4Iz.png";
    private String NEARBY_RETAILER_TO_ROUTE_ICON_URL_LIGHT = "https://i.imgur.com/LG5Pycy.png";


    //The URL to Google Map icons used to display the locations for each data type on the map.
    //Icons for the dark theme.
    //https://imgur.com/a/ufJCT
    private String ALL_BIKESTATION_ICON_URL_DARK = "https://i.imgur.com/2Ln07t2.png";
    private String SELECTED_BIKESTATION_ICON_URL_DARK = "https://i.imgur.com/Ckx7PN7.png";
    private String NEARBY_BIKESTATION_TO_ROUTE_ICON_URL_DARK = "https://i.imgur.com/rUwh6MV.png";

    private String ALL_WIFI_ICON_URL_DARK = "https://i.imgur.com/KKSNi5w.png";
    private String SELECTED_WIFI_ICON_URL_DARK = "https://i.imgur.com/Mv1UdMz.png";
    private String NEARBY_WIFI_TO_RETAILER_ICON_URL_DARK = "https://i.imgur.com/9u3zuRS.png";
    private String NEARBY_WIFI_TO_ROUTE_ICON_URL_DARK = "https://i.imgur.com/Cs6tiyz.png";

    private String ALL_RETAILER_ICON_URL_DARK = "https://i.imgur.com/5QV1Nz5.png";
    private String SELECTED_RETAILER_ICON_URL_DARK = "https://i.imgur.com/ZxPV9fV.png";
    private String NEARBY_RETAILER_TO_ROUTE_ICON_URL_DARK = "https://i.imgur.com/hd7qT60.png";

    /**
     * Method used to create a visible WiFi marker.
     *
     * @param wifi WiFi object that will be made into a marker.
     * @return The created marker showing the WiFi's location and associated information window with extra info.
     */
    private static Marker makeWiFiMarker(WiFi wifi) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLong(wifi.getLocation().getLatitude(), wifi.getLocation().getLongitude()))
                .visible(Boolean.TRUE)
                .title("Wifi");


        Marker marker = new Marker(markerOptions);

        map.addUIEventHandler(marker, UIEventType.click, (netscape.javascript.JSObject obj) -> {

            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content("<b>WiFi</b>" + "<br>" + "Name: " + wifi.getName() + "<br>" + "Type: " + wifi.getType() + "<br>" + "City: " + wifi.getCity());

            if (currentInfoWindow != null) {
                currentInfoWindow.close();
            }
            currentInfoWindow = new InfoWindow(infoWindowOptions);
            currentInfoWindow.open(map, marker);

        });


        return marker;
    }

    /**
     * Method used to create a visible bike station marker.
     *
     * @param bikeStation The bike station whose location will be represented by the marker. Additional information will be put inside an information window.
     * @return The created marker showing the bike station's location and associated information window with extra info.
     */
    private static Marker makeBikeStationMarker(BikeStation bikeStation) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLong(bikeStation.getLocation().getLatitude(),
                bikeStation.getLocation().getLongitude())).visible(Boolean.TRUE).title("Bike Station");

        Marker marker = new Marker(markerOptions);

        map.addUIEventHandler(marker, UIEventType.click, (netscape.javascript.JSObject obj) -> {

            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content("<b>Bike Station</b>" + "<br>" + "Name: " + bikeStation.getName());

            if (currentInfoWindow != null) {
                currentInfoWindow.close();
            }
            currentInfoWindow = new InfoWindow(infoWindowOptions);
            currentInfoWindow.open(map, marker);

        });


        return marker;
    }

    /**
     * Initializes the map.
     */
    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();
        options.center(new LatLong(40.7830603, -73.9712488))
                .zoomControl(true)
                .zoom(12)
                .overviewMapControl(false)
                .streetViewControl(false)
                .overviewMapControl(false)
                .mapTypeControl(false)
                .mapType(MapTypeIdEnum.ROADMAP)
                .styleString("[\n" +
                        "  {\n" +
                        "    \"featureType\": \"administrative\",\n" +
                        "    \"elementType\": \"geometry\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"poi\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road\",\n" +
                        "    \"elementType\": \"labels.icon\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"transit\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "]");
        map = mapView.createMap(options);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();
        directionsDisplay = new DirectionsRenderer(true, mapView.getMap(), directionsPane);


        //The static variable holding the controller is instantiated
        controller = this;

        clickRoute();
    }

    /**
     * At the start all buttons related to clearing markers and displayed routes should be disabled
     * since there is nothing to clear. Also bind the the text fields to the components for calculating
     * a route based on the specified start and end destination.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
        to.bindBidirectional(toTextField.textProperty());
        from.bindBidirectional(fromTextField.textProperty());

        routeRetailerNearby.setDisable(true);
        routeWifiNearby.setDisable(true);
        routeBikeStationNearby.setDisable(true);
        retailerWifiNearby.setDisable(true);
        statButton.setDisable(true);
        clearBikeStationButton.setDisable(true);
        clearRouteButton.setDisable(true);
        clearRetailerButton.setDisable(true);
        clearWifiButton.setDisable(true);
        clearBikeStationButton.setDisable(true);


    }

    /**
     * Called when the directions are received.
     *
     * @param results result of the direction.
     * @param status  The status of the direction.
     */
    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
    }

    /**
     * Changes the map theme to normal.
     */
    public void changeMapNormalTheme() {

        MapOptions options = new MapOptions();
        options.center(new LatLong(40.7830603, -73.9712488))
                .zoomControl(true)
                .zoom(12)
                .overviewMapControl(false)
                .streetViewControl(false)
                .overviewMapControl(false)
                .mapTypeControl(false)
                .mapType(MapTypeIdEnum.ROADMAP)
                .styleString("[\n" +
                        "  {\n" +
                        "    \"featureType\": \"administrative\",\n" +
                        "    \"elementType\": \"geometry\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"poi\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road\",\n" +
                        "    \"elementType\": \"labels.icon\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"transit\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "]");
        map = mapView.createMap(options);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();
        directionsDisplay = new DirectionsRenderer(true, mapView.getMap(), directionsPane);

        if (isRetailersShown) {
            showMarkers(shownRetailerArray);
        }

        if (isWiFiShown) {
            showMarkers(shownWifiArray);
        }

        if (isBikeStationShown) {
            showMarkers(shownBikeStationArray);
        }

        if (nearbyRetailerFlag) {
            showMarkers(shownNearbyRetailerArray);
        }

        if (nearbyRetailerWiFiFlag) {
            map.addMarker(nearbyRetailerWiFiMarker);
        }

        if (nearbyWiFiFlag) {
            showMarkers(shownNearbyWiFiArray);
        }

        if (nearbyBikeStationFlag) {
            showMarkers(shownNearbyBikeStationArray);
        }

        if (wifiMarker != null) {
            map.addMarker(wifiMarker);
        }

        if (retailerMarker != null) {
            map.addMarker(retailerMarker);
        }

        if (bikeStationMarker != null) {
            map.addMarker(bikeStationMarker);
        }

        if (RawDataViewerController.currentlySelectedRoute != null) {
            displayRoute(RawDataViewerController.currentlySelectedRoute.getRouteStartStationName(), RawDataViewerController.currentlySelectedRoute.getRouteEndStationName(), new Location(RawDataViewerController.currentlySelectedRoute.getRouteStartStationLatitude(), RawDataViewerController.currentlySelectedRoute.getRouteStartStationLongitude()), new Location(RawDataViewerController.currentlySelectedRoute.getRouteEndStationLatitude(), RawDataViewerController.currentlySelectedRoute.getRouteEndStationLongitude()));
        }

        clickRoute();

    }

    /**
     * Changes the map theme to dark.
     */
    public void changeMapDarkTheme() {

        MapOptions options = new MapOptions();
        options.center(new LatLong(40.7830603, -73.9712488))
                .zoomControl(true)
                .zoom(12)
                .overviewMapControl(false)
                .streetViewControl(false)
                .overviewMapControl(false)
                .mapTypeControl(false)
                .mapType(MapTypeIdEnum.ROADMAP)
                .styleString("[\n" +
                        "  {\n" +
                        "    \"elementType\": \"geometry\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#242f3e\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"elementType\": \"labels.text.fill\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#746855\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"elementType\": \"labels.text.stroke\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#242f3e\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"administrative\",\n" +
                        "    \"elementType\": \"geometry\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"administrative.locality\",\n" +
                        "    \"elementType\": \"labels.text.fill\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#d59563\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"poi\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"poi\",\n" +
                        "    \"elementType\": \"labels.text.fill\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#d59563\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"poi.park\",\n" +
                        "    \"elementType\": \"geometry\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#263c3f\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"poi.park\",\n" +
                        "    \"elementType\": \"labels.text.fill\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#6b9a76\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road\",\n" +
                        "    \"elementType\": \"geometry\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#38414e\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road\",\n" +
                        "    \"elementType\": \"geometry.stroke\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#212a37\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road\",\n" +
                        "    \"elementType\": \"labels.icon\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road\",\n" +
                        "    \"elementType\": \"labels.text.fill\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#9ca5b3\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road.highway\",\n" +
                        "    \"elementType\": \"geometry\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#746855\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road.highway\",\n" +
                        "    \"elementType\": \"geometry.stroke\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#1f2835\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road.highway\",\n" +
                        "    \"elementType\": \"labels.text.fill\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#f3d19c\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"transit\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"visibility\": \"off\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"transit\",\n" +
                        "    \"elementType\": \"geometry\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#2f3948\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"transit.station\",\n" +
                        "    \"elementType\": \"labels.text.fill\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#d59563\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"water\",\n" +
                        "    \"elementType\": \"geometry\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#17263c\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"water\",\n" +
                        "    \"elementType\": \"labels.text.fill\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#515c6d\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"water\",\n" +
                        "    \"elementType\": \"labels.text.stroke\",\n" +
                        "    \"stylers\": [\n" +
                        "      {\n" +
                        "        \"color\": \"#17263c\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "]");
        map = mapView.createMap(options);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();
        directionsDisplay = new DirectionsRenderer(true, mapView.getMap(), directionsPane);

        if (isRetailersShown) {
            showMarkers(shownRetailerArray);
        }

        if (isWiFiShown) {
            showMarkers(shownWifiArray);
        }

        if (isBikeStationShown) {
            showMarkers(shownBikeStationArray);
        }

        if (nearbyRetailerFlag) {
            showMarkers(shownNearbyRetailerArray);
        }

        if (nearbyRetailerWiFiFlag) {
            map.addMarker(nearbyRetailerWiFiMarker);
        }

        if (nearbyWiFiFlag) {
            showMarkers(shownNearbyWiFiArray);
        }

        if (nearbyBikeStationFlag) {
            showMarkers(shownNearbyBikeStationArray);
        }

        if (wifiMarker != null) {
            map.addMarker(wifiMarker);
        }

        if (retailerMarker != null) {
            map.addMarker(retailerMarker);
        }

        if (bikeStationMarker != null) {
            map.addMarker(bikeStationMarker);
        }

        if (RawDataViewerController.currentlySelectedRoute != null) {
            displayRoute(RawDataViewerController.currentlySelectedRoute.getRouteStartStationName(), RawDataViewerController.currentlySelectedRoute.getRouteEndStationName(), new Location(RawDataViewerController.currentlySelectedRoute.getRouteStartStationLatitude(), RawDataViewerController.currentlySelectedRoute.getRouteStartStationLongitude()), new Location(RawDataViewerController.currentlySelectedRoute.getRouteEndStationLatitude(), RawDataViewerController.currentlySelectedRoute.getRouteEndStationLongitude()));
        }


        clickRoute();


    }

    /**
     * Creates a bike route from two points that were selected on the map with the mouse
     */
    private void clickRoute() {
        MarkerOptions markerOptionsFirst = new MarkerOptions();
        final LatLong[] firstLatLong = new LatLong[1];
        final Marker[] firstMarker = new Marker[1];

        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
            if (clickEnabled) {

                if (markerList.size() % 2 != 1) {
                    firstLatLong[0] = event.getLatLong();
                    markerOptionsFirst.position(firstLatLong[0])
                            .visible(Boolean.TRUE)
                            .title("First Marker");
                    firstMarker[0] = new Marker(markerOptionsFirst);
                    map.addMarker(firstMarker[0]);
                    markerList.add(firstMarker[0]);
                } else {
                    LatLong latLong = event.getLatLong();
                    Location origin = new Location(firstLatLong[0].getLatitude(), firstLatLong[0].getLongitude());
                    Location destination = new Location(latLong.getLatitude(), latLong.getLongitude());
                    Route clickRoute = new Route(0, new Date(0), new Date(0), "", "", origin.getLatitude(), origin.getLongitude(), "", "", destination.getLatitude(), destination.getLongitude(), "", "", "", "");
                    //Enable check boxes
                    routeWifiNearby.setDisable(false);
                    clearRouteButton.setDisable(false);
                    routeRetailerNearby.setDisable(false);
                    routeBikeStationNearby.setDisable(false);
                    //Display route
                    RawDataViewerController.currentlySelectedRoute = clickRoute;
                    displayRoute("Point A", "Point B", origin, destination);
                    //Delete created marker and arraylist
                    for (Marker marker : markerList) {
                        map.removeMarker(marker);
                    }
                    markerList.remove(0);
                    clickEnabled = false;

                    createRouteButton.setText("Create Route");
                }
            }
        });
    }

    /**
     * Method the shortest distance between the two selected points.
     * Statistic button is no longer disabled.
     * Statistic class has being updated with the new route.
     */
    @FXML
    private void routeAction() {
        if(from.get().length() > 0) {

            JSONObject originJSON = GeoCoder.getLocationFormGoogle(from.get());
            LatLong origin = GeoCoder.getLatLng(originJSON);

            JSONObject destinationJSON = GeoCoder.getLocationFormGoogle(to.get());
            LatLong destination = GeoCoder.getLatLng(destinationJSON);


            StatisticController.controller.initData(from.get(), to.get(), new Location(origin.getLatitude(), origin.getLongitude()), new Location(destination.getLatitude(), destination.getLongitude()));
            StatisticController.controller.setTextField();

            Route selectedRoute = new Route(0, new Date(0), new Date(0), "", "", origin.getLatitude(), origin.getLongitude(), "", "", destination.getLatitude(), destination.getLongitude(), "", "", "", "");

            MapController.controller.clearRoute();
            MapController.controller.routeRetailerNearby.setDisable(false);
            MapController.controller.routeWifiNearby.setDisable(false);
            MapController.controller.routeBikeStationNearby.setDisable(false);
            MapController.controller.clearRouteButton.setDisable(false);
            statButton.setDisable(false);

            RawDataViewerController.currentlySelectedRoute = selectedRoute;

            MapController.controller.displayRoute(from.get(), to.get(), new Location(selectedRoute.getRouteStartStationLatitude(), selectedRoute.getRouteStartStationLongitude()), new Location(selectedRoute.getRouteEndStationLatitude(), selectedRoute.getRouteEndStationLongitude()));
        }
    }

    /**
     * Creates a route by clicking for two points on the map.
     */
    @FXML
    private void createRouteAction() {
        if (!createRouteButton.getText().equals("Create Route")) {
            for (Marker marker : markerList) {
                map.removeMarker(marker);
            }
            if (markerList.size() > 0) {
                markerList.remove(0);
            }
            clickEnabled = false;
            createRouteButton.setText("Create Route");
        } else {
            clickEnabled = true;
            clearRoute();
            createRouteButton.setText("Cancel");
        }
    }

    /**
     * Method to change the pane to statistic pane and display the route statistic
     */
    @FXML
    private void showStat() {
        //Take you to the statistic tab.
        SingleSelectionModel<Tab> selectionModel = Main.tabPane.getSelectionModel();
        selectionModel.select(2);
    }

    /**
     * Displays a route on the map between the two locations.
     *
     * @param startName  name of the start location
     * @param endName    name of the end location
     * @param startPoint Location object of the start station
     * @param endPoint   Location object of the end station
     */
    @FXML
    void displayRoute(String startName, String endName, Location startPoint, Location endPoint) {
        if (createRouteButton.getText().equals("Cancel")) {
            //Stop current click routing selection
            for (Marker marker : markerList) {
                map.removeMarker(marker);
            }
            if (markerList.size() > 0) {
                markerList.remove(0);
            }
            clickEnabled = false;
            createRouteButton.setText("Create Route");
        }

        statButton.setDisable(false);
        StatisticController.controller.initData(startName, endName, startPoint, endPoint);
        StatisticController.controller.setTextField();

        String origin = String.format("%s,%s", startPoint.getLatitude(), startPoint.getLongitude());
        String destination = String.format("%s,%s", endPoint.getLatitude(), endPoint.getLongitude());


        DirectionsRequest request = new DirectionsRequest(origin, destination, TravelModes.BICYCLING);
        directionsService.getRoute(request, this, directionsDisplay);
    }

    /**
     * Displays a retailer on the map as a marker.
     *
     * @param retailer Retailer object to be displayed on the map.
     */
    @FXML
    void displayRetailer(Retailer retailer) {
        try {
            map.removeMarker(retailerMarker);
        } catch (NullPointerException e) {
            //Do nothing, there's no marker to remove
        }

        Marker newRetailerMarker = makeRetailerMarker(retailer);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(SELECTED_RETAILER_ICON_URL);
        newRetailerMarker.setOptions(markerOptions);

        retailerMarker = newRetailerMarker;
        map.addMarker(newRetailerMarker);

    }

    /**
     * Display a bike station on the map as a marker.
     *
     * @param bikeStation The bike station to be displayed on the map.
     */
    public void displayBikeStation(BikeStation bikeStation) {
        try {
            map.removeMarker(bikeStationMarker);
        } catch (Exception e) {
            //No nothing, there were no previously bike station marker on the map
            //e.printStackTrace();
        }

        Marker newBikeStationMarker = makeBikeStationMarker(bikeStation);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(SELECTED_BIKESTATION_ICON_URL);
        newBikeStationMarker.setOptions(markerOptions);

        bikeStationMarker = newBikeStationMarker;
        map.addMarker(newBikeStationMarker);
    }

    /**
     * Displays a wifi location on the map as a marker.
     *
     * @param currentWifi WiFi object to be displayed on the map.
     */
    @FXML
    public void displayWiFi(WiFi currentWifi) {

        try {
            map.removeMarker(wifiMarker);
        } catch (Exception e) {

        }

        Marker newWiFiMarker = makeWiFiMarker(currentWifi);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(SELECTED_WIFI_ICON_URL);
        newWiFiMarker.setOptions(markerOptions);

        wifiMarker = newWiFiMarker;
        map.addMarker(newWiFiMarker);

    }

    /**
     * Method used to set the visibility of the markers provided to be true (hence showing them on the map.
     *
     * @param markers The ArrayList containing the markers to be shown.
     */
    private void showMarkers(ArrayList<Marker> markers) {
        for (Marker marker : markers) {
            map.addMarker(marker);
        }
    }

    /**
     * Method used to set the visiblity of the markers provided to be false (hence hiding them from the map.
     *
     * @param markers The ArrayList containg the markers to be hidden
     */
    private void hideMarkers(ArrayList<Marker> markers) {
        for (Marker marker : markers) {
            map.removeMarker(marker);
        }
    }

    /**
     * If the checkbox for Retailer is checked, then all the retailers will be
     * shown. If the checkbox for Retailer is unchecked, then all the retailers are hidden.
     */
    @FXML
    public void toggleRetailerDisplay() {
        if (isRetailersShown) {
            hideMarkers(shownRetailerArray);
            shownRetailerArray.clear();
            isRetailersShown = false;
        } else {

            for (Retailer retailer : Data.DataManager.getAllRetailers()) {

                Marker retailerMarker = makeRetailerMarker(retailer);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(ALL_RETAILER_ICON_URL);
                retailerMarker.setOptions(markerOptions);

                shownRetailerArray.add(retailerMarker);
            }

            showMarkers(shownRetailerArray);
            isRetailersShown = true;
        }
    }

    /**
     * If the checkbox for WiFi is checked, then all the wifi locations will be
     * shown. If the checkbox for Retailer is unchecked, then all the wifi locations are hidden.
     */
    @FXML
    public void toggleWiFiDisplay() {
        if (isWiFiShown) {
            hideMarkers(shownWifiArray);
            shownWifiArray.clear();
            isWiFiShown = false;
        } else {

            for (WiFi wifi : Data.DataManager.getAllWifi()) {

                Marker wifiMarker = makeWiFiMarker(wifi);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(ALL_WIFI_ICON_URL);


                wifiMarker.setOptions(markerOptions);

                shownWifiArray.add(wifiMarker);
            }

            showMarkers(shownWifiArray);
            isWiFiShown = true;
        }
    }

    /**
     * If the checkbox for bike stations is checked, then all the bike station locations
     * will be shown. If the checkbox for bike staion is unchecked, then all the bike stations are
     * hidden.
     */
    @FXML
    public void toggleBikeStations() {
        if (isBikeStationShown) {
            //Hide the previously created markers
            hideMarkers(shownBikeStationArray);
            shownBikeStationArray.clear();
            isBikeStationShown = false;
        } else {

            for (BikeStation bikeStation : Data.DataManager.getAllBikeStations()) {
                Marker bikeStationMarker = makeBikeStationMarker(bikeStation);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(ALL_BIKESTATION_ICON_URL);
                bikeStationMarker.setOptions(markerOptions);

                shownBikeStationArray.add(bikeStationMarker);
            }

            showMarkers(shownBikeStationArray);
            isBikeStationShown = true;
        }

    }

    /**
     * Method used to create a visible Retailer marker.
     *
     * @param retailer Retailer object that will be made into a marker.
     * @return The created marker showing the retail's location and associated information window with extra info.
     */
    private Marker makeRetailerMarker(Retailer retailer) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLong(retailer.getLocation().getLatitude(), retailer.getLocation().getLongitude()))
                .visible(Boolean.TRUE)
                .title("Retailer");


        Marker marker = new Marker(markerOptions);

        map.addUIEventHandler(marker, UIEventType.click, (netscape.javascript.JSObject obj) -> {

            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content("<b>Retailer</b>" + "<br>" + "Name: " + retailer.getName() + "<br>" + "Address: " + retailer.getStreetName() + "<br>" + "City: " + retailer.getCity());

            if (currentInfoWindow != null) {
                currentInfoWindow.close();
            }
            currentInfoWindow = new InfoWindow(infoWindowOptions);
            currentInfoWindow.open(map, marker);

        });

        return marker;
    }


    /**
     * Method to toggle the display of the closest wiFi location to a retailer.
     */
    public void toggleRetailerWiFi() {
        if (!nearbyRetailerWiFiFlag) {

            nearbyRetailerWiFiFlag = true;


            Retailer currentRetailer = RawDataViewerController.currentlySelectedRetailer;

            WiFi closestWifi = currentRetailer.getClosestRetailerWiFi(Data.DataManager.getAllWifi());

            Marker wifiMarker = makeWiFiMarker(closestWifi);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(NEARBY_WIFI_TO_RETAILER_ICON_URL);
            wifiMarker.setOptions(markerOptions);


            nearbyRetailerWiFiMarker = wifiMarker;

            map.addMarker(nearbyRetailerWiFiMarker);


        } else {
            nearbyRetailerWiFiFlag = false;

            map.removeMarker(nearbyRetailerWiFiMarker);
            nearbyRetailerWiFiMarker = null;
        }
    }

    /**
     * Method to toggle the display of the closest WiFi location for a bike route.
     */
    public void toggleNearbyWiFi() {
        if (!nearbyWiFiFlag) {

            nearbyWiFiFlag = true;
            Route currentRoute = RawDataViewerController.currentlySelectedRoute;
            ArrayList<WiFi> closestWiFiList = currentRoute.getClosestWifi(Data.DataManager.getAllWifi());

            for (WiFi wifi : closestWiFiList) {

                Marker wifiMarker = makeWiFiMarker(wifi);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(NEARBY_WIFI_TO_ROUTE_ICON_URL);
                wifiMarker.setOptions(markerOptions);

                shownNearbyWiFiArray.add(wifiMarker);
            }

            showMarkers(shownNearbyWiFiArray);


        } else {
            nearbyWiFiFlag = false;

            hideMarkers(shownNearbyWiFiArray);
            shownNearbyWiFiArray.clear();

        }
    }


    /**
     * Method to toggle the display of the closest Retailer location for a bike route.
     */
    public void toggleNearbyRetailer() {
        if (!nearbyRetailerFlag) {

            nearbyRetailerFlag = true;
            Route currentRoute = RawDataViewerController.currentlySelectedRoute;
            ArrayList<Retailer> closestRetailerList = currentRoute.getClosestRetailer(Data.DataManager.getAllRetailers());

            for (Retailer retailer : closestRetailerList) {

                Marker retailerMarker = makeRetailerMarker(retailer);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(NEARBY_RETAILER_TO_ROUTE_ICON_URL);
                retailerMarker.setOptions(markerOptions);

                shownNearbyRetailerArray.add(retailerMarker);
            }

            showMarkers(shownNearbyRetailerArray);


        } else {
            nearbyRetailerFlag = false;

            hideMarkers(shownNearbyRetailerArray);
            shownNearbyRetailerArray.clear();

        }
    }

    /**
     * Toggle closeset bike stations to the start and end of the currently selected route.
     */
    public void toggleNearbyBikeStation() {
        if (!nearbyBikeStationFlag) {

            nearbyBikeStationFlag = true;
            Route currentRoute = RawDataViewerController.currentlySelectedRoute;
            ArrayList<BikeStation> closestBikeStationList = currentRoute.getClosestBikeStation(Data.DataManager.getAllBikeStations());
            for (BikeStation bikeStation : closestBikeStationList) {
                Marker bikeStationMarker = makeBikeStationMarker(bikeStation);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(NEARBY_BIKESTATION_TO_ROUTE_ICON_URL);
                bikeStationMarker.setOptions(markerOptions);
                shownNearbyBikeStationArray.add(bikeStationMarker);
            }

            showMarkers(shownNearbyBikeStationArray);


        } else {
            nearbyBikeStationFlag = false;
            hideMarkers(shownNearbyBikeStationArray);
            shownNearbyBikeStationArray.clear();
        }
    }

    /**
     * Method to clear a route and related displays from the map.
     */
    public void clearRoute() {
        statButton.setDisable(true);
        if (nearbyWiFiFlag) {
            toggleNearbyWiFi();
        }

        if (nearbyRetailerFlag) {
            toggleNearbyRetailer();
        }

        if (nearbyBikeStationFlag) {
            toggleNearbyBikeStation();
        }

        MapController.controller.routeRetailerNearby.setSelected(false);
        MapController.controller.routeRetailerNearby.setDisable(true);

        MapController.controller.routeWifiNearby.setSelected(false);
        MapController.controller.routeWifiNearby.setDisable(true);

        MapController.controller.routeBikeStationNearby.setSelected(false);
        MapController.controller.routeBikeStationNearby.setDisable(true);

        MapController.controller.clearRouteButton.setDisable(true);

        RawDataViewerController.currentlySelectedRoute = null;

        if (directionsDisplay != null) {
            directionsDisplay.setMap(null);
            directionsDisplay = null;
        }

        directionsDisplay = new DirectionsRenderer(true, mapView.getMap(), directionsPane);

    }


    /**
     * Method to clear a retailer and related displays from the map.
     */
    public void clearRetailer() {

        if (nearbyRetailerWiFiFlag) {
            toggleRetailerWiFi();
        }

        MapController.controller.retailerWifiNearby.setSelected(false);
        MapController.controller.retailerWifiNearby.setDisable(true);
        MapController.controller.clearRetailerButton.setDisable(true);

        RawDataViewerController.currentlySelectedRetailer = null;
        try {
            map.removeMarker(retailerMarker);
        } catch (NullPointerException e) {
            //Do nothing, no marker to remove
        }

    }


    /**
     * Method to clear the previously selected and shown wifi location on the map.
     */
    public void clearWifi() {

        MapController.controller.clearWifiButton.setDisable(true);

        RawDataViewerController.currentlySelectedWiFi = null;

        try {
            map.removeMarker(wifiMarker);
        } catch (Exception e) {

        }
    }

    /**
     * Method to clear the previously selected and display bike station location on the map.
     */
    public void clearBikeStation() {
        clearBikeStationButton.setDisable(true);
        RawDataViewerController.currentlySelectedBikeStation = null;

        try {
            //Remove the previously selected bike station to be displayed as a marker
            map.removeMarker(bikeStationMarker);
        } catch (Exception e) {
            //There were no previously selected marker.
            //e.printStackTrace();
        }
    }

}