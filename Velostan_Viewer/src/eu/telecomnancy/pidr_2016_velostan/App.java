package eu.telecomnancy.pidr_2016_velostan;

import eu.telecomnancy.pidr_2016_velostan.database.ConnectionJDBC;
import eu.telecomnancy.pidr_2016_velostan.database.DAORoute;
import eu.telecomnancy.pidr_2016_velostan.model.RouteModel;
import eu.telecomnancy.pidr_2016_velostan.export.ExportResult;
import eu.telecomnancy.pidr_2016_velostan.views.ExportDialogController;
import eu.telecomnancy.pidr_2016_velostan.views.MenuOverviewController;

import eu.telecomnancy.pidr_2016_velostan.views.RootOverviewController;
import eu.telecomnancy.pidr_2016_velostan.webservice.Webservice;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by thomasgauthey on 18/05/2016.
 */
public class App extends Application {

    private Stage mainStage;
    private BorderPane rootLayout;
    private ObservableList<RouteModel> allRoutesObservable = null;
    private List<RouteModel> allRoutes = null;
    private MenuOverviewController menuController;
    //private List<LocationModel> allLocations = null;

    public App() {}

    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //this.allRoutes = DAORoute.getInstance().getAllRoutes();
        this.allRoutes = Webservice.getAllRoutes();
        for (RouteModel r : this.allRoutes)
            System.out.println(r);

        this.allRoutesObservable = FXCollections.observableArrayList();
        this.allRoutesObservable.setAll(allRoutes);
            /*for (RouteModel r : this.allRoutesObservable)
                System.out.println(r);*/

            /*this.allLocations = DAOLocation.getInstance().getAllLocations();
            for (LocationModel l : this.allLocations)
                System.out.println(l);*/

        this.mainStage = primaryStage;

        mainStage.setOnCloseRequest(we -> {
            try {
                ConnectionJDBC.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        initRootLayout();
        showMenuOverview();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/RootLayout.fxml"));
            rootLayout = loader.load();

            // Give the controller access to the main app.
            RootOverviewController controller = loader.getController();
            controller.setMainApp(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMenuOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/MenuOverview.fxml"));
            AnchorPane menuOverview = loader.load();

            // Set menu overview into the center of root layout.
            rootLayout.setCenter(menuOverview);

            menuController = loader.getController();
            menuController.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to create a new lesson. If the user clicks OK,
     * the details are saved into a new lesson object and true
     * is returned.
     *
     * @param route the route object which will be export
     */
    public void showExportDialog(RouteModel route, ExportResult result) {
        String files = "Les fichiers exportés sont les suivants :\n";
        for (String filename : result.getList()) {
            files += filename+"\n";
        }

        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/ExportDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Export informations");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ExportDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRoute(route);
            controller.getFilesLabel().setText(files);
            controller.getDirectoryField().setText(result.getDirectory());

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public ObservableList<RouteModel> getAllRoutesObservable() {
        return allRoutesObservable;
    }

    public List<RouteModel> getAllRoutes() {
        return allRoutes;
    }

    public MenuOverviewController getMenuController() {
        return menuController;
    }

    public void setAllRoutes(List<RouteModel> allRoutes) {
        this.allRoutes = allRoutes;
        this.allRoutesObservable.clear();
        this.allRoutesObservable.setAll(allRoutes);
        for (RouteModel r : this.allRoutesObservable)
            System.out.println(r);
    }

    /*public List<LocationModel> getAllLocations() {
        return allLocations;
    }

    public void setAllLocations(List<LocationModel> allLocations) {
        this.allLocations = allLocations;
    }*/
}
