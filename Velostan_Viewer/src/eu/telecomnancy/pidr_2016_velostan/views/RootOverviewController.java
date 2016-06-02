package eu.telecomnancy.pidr_2016_velostan.views;

import eu.telecomnancy.pidr_2016_velostan.App;
import eu.telecomnancy.pidr_2016_velostan.database.ConnectionJDBC;
import eu.telecomnancy.pidr_2016_velostan.database.DAORoute;
import eu.telecomnancy.pidr_2016_velostan.model.RouteModel;
import eu.telecomnancy.pidr_2016_velostan.webservice.Webservice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by yoann on 15/02/16.
 */
public class RootOverviewController {

    private App mainApp;

    @FXML
    private MenuItem closeItem;
    @FXML
    private MenuItem deleteItem;
    @FXML
    private MenuItem aboutItem;


    public RootOverviewController() {
    }

    /**
     * Called when the user clicks the close menu item.
     */
    @FXML
    private void handleCloseItem() {
        try {
            ConnectionJDBC.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            new ExceptionAlert(e, "Erreur à la fermeture de connexion à la base de données.");
        }
        mainApp.getMainStage().close();
    }

    /**
     * Called when the user clicks the delete menu item.
     */
    @FXML
    private void handleDeleteItem() {
        RouteModel route = mainApp.getMenuController().getRoutesListView().getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainApp.getMainStage());
        alert.setTitle("Suppression de données");
        alert.setHeaderText("Vous avez choisi de supprimer le trajet n°"+route.getId_route());
        alert.setContentText("La suppresssion de données est irréversible et définitive. \nÊtes-vous sûr de ce choix ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //DAORoute.getInstance().deleteRoute(route);
            Webservice.deleteRoute(route);
            mainApp.getAllRoutesObservable().remove(route);
            mainApp.getAllRoutes().remove(route);
        }
    }

    /**
     * Called when the user clicks the about menu item.
     */
    @FXML
    private void handleAboutItem() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/AboutDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("About");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getMainStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AboutDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public App getMainApp() {
        return mainApp;
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }
}
