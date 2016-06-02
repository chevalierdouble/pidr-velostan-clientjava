package eu.telecomnancy.pidr_2016_velostan.views;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * Created by yoann on 11/03/16.
 */
public class AboutDialogController {

    private Stage dialogStage;

    private final static Image closeImage = new Image("file:resources/images/button-cross_red.png");

    public AboutDialogController() {}

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {}

    /**
     * Called when the user clicks back to menu.
     */
    @FXML
    private void handleClose() {
        dialogStage.close();
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage mainStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

}
