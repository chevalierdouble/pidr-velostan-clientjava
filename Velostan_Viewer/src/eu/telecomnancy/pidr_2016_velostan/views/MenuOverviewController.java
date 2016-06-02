package eu.telecomnancy.pidr_2016_velostan.views;


import eu.telecomnancy.pidr_2016_velostan.database.DAOLocation;
import eu.telecomnancy.pidr_2016_velostan.database.DAORoute;
import eu.telecomnancy.pidr_2016_velostan.export.ExportCSV;
import eu.telecomnancy.pidr_2016_velostan.model.LocationModel;
import eu.telecomnancy.pidr_2016_velostan.model.RouteModel;
import eu.telecomnancy.pidr_2016_velostan.utils.RouteCell;
import eu.telecomnancy.pidr_2016_velostan.export.ExportResult;
import eu.telecomnancy.pidr_2016_velostan.webservice.Webservice;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import eu.telecomnancy.pidr_2016_velostan.App;

import java.io.IOException;
import java.util.List;

/**
 * Created by yoann on 15/02/16.
 */
public class MenuOverviewController {

    @FXML
    private Button exportButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<RouteModel> routesListView;

    private App mainApp;

    public MenuOverviewController() {}

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        searchField.setOnMouseClicked(ke -> searchButton.setDefaultButton(true));
    }

    /**
     * Called when the user clicks the export button. Opens a dialog which display export informations.
     */
    @FXML
    private void handleExport() {
        RouteModel route = routesListView.getSelectionModel().getSelectedItem();
        if (route != null) {
            //List<LocationModel> listLocations = DAOLocation.getInstance().getLocationsByRoute(route);
            List<LocationModel> listLocations = Webservice.getLocationsByRoute(route);
            route.setListLocation(listLocations);

            for (LocationModel l : route.getListLocation()) {
                System.out.println(l);
            }

            ExportResult result = ExportCSV.writeCSVFile(route);

            mainApp.showExportDialog(route, result);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getMainStage());
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucun trajet sélectionné");
            alert.setContentText("Veuillez sélectionner un trajet.");

            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks the refresh button. Get in the database the new Routes.
     */
    @FXML
    private void handleRefresh() {
        //this.mainApp.setAllRoutes(DAORoute.getInstance().getAllRoutes());
        this.mainApp.setAllRoutes(Webservice.getAllRoutes());
    }

    public void setApp(App mainApp) {
        // Add observable list data to the table
        this.mainApp = mainApp;

        routesListView.setItems(mainApp.getAllRoutesObservable());
        routesListView.setCellFactory(list -> new RouteCell());
    }

    /**
     * Called when the user clicks on the search button. Filter on device.
     */
    @FXML
    private void handleSearch() {
        String search = searchField.getText();

        mainApp.getAllRoutesObservable().clear();
        if (!search.equals("")) {
            for (RouteModel route : mainApp.getAllRoutes()) {
                if (search.equals(route.getDevice())) {
                    mainApp.getAllRoutesObservable().add(route);
                }
            }
        } else {
            mainApp.getAllRoutesObservable().addAll(mainApp.getAllRoutes());
        }
    }

    public ListView<RouteModel> getRoutesListView() {
        return routesListView;
    }
}
