package eu.telecomnancy.pidr_2016_velostan.views;

import eu.telecomnancy.pidr_2016_velostan.model.RouteModel;

import eu.telecomnancy.pidr_2016_velostan.utils.OpenInBrowser;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by yoann on 16/02/16.
 */
public class ExportDialogController {

    private Stage dialogStage;
    private RouteModel route;

    @FXML
    private TextField directoryField;
    @FXML
    private Label filesLabel;
    @FXML
    private Hyperlink mapLink;

    public ExportDialogController() {}

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {}

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        dialogStage.close();
    }

    @FXML
    private void handleMap() {
        new Thread(() -> {
            OpenInBrowser op = new OpenInBrowser();
            op.open("https://www.google.com/maps/d/");
        }).start();
    }

    public RouteModel getRoute() {
        return route;
    }

    public void setRoute(RouteModel route) {
        this.route = route;
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Label getFilesLabel() {
        return filesLabel;
    }

    public TextField getDirectoryField() {
        return directoryField;
    }
}
