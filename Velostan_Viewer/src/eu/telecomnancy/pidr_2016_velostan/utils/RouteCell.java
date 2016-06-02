package eu.telecomnancy.pidr_2016_velostan.utils;

import eu.telecomnancy.pidr_2016_velostan.App;
import eu.telecomnancy.pidr_2016_velostan.model.RouteModel;

import eu.telecomnancy.pidr_2016_velostan.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * Created by yoann on 15/02/16.
 */
public class RouteCell extends ListCell<RouteModel> {

    private BorderPane borderPane = new BorderPane();

    private AnchorPane topPane = new AnchorPane();
    private AnchorPane centerPane = new AnchorPane();
    private GridPane gridPane = new GridPane();
    private AnchorPane bottomPane = new AnchorPane();

    private Label idRouteLabel = new Label();
    private Label deviceLabel = new Label();
    private Label beginLabel = new Label();
    private Label endLabel = new Label();
    private Label averageSpeedLabel = new Label();
    private ContextMenu contextMenu = new ContextMenu();

    /**
     * Called when the user clicks the close menu item.
     */
    private EventHandler<ActionEvent> handleCopyItem(RouteModel route) {
        return event -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(route == null ? "blabla" : route.getDevice());
            clipboard.setContent(content);
        };
    }

    @Override
    public void updateItem(RouteModel item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {

            idRouteLabel.setText("Trajet n°"+String.valueOf(item.getId_route()));
            idRouteLabel.setFont(new Font("utkal", 18));
            AnchorPane.setTopAnchor(idRouteLabel, 5.0);
            AnchorPane.setLeftAnchor(idRouteLabel, 5.0);
            AnchorPane.setBottomAnchor(idRouteLabel, 5.0);

            deviceLabel.setText("Vélo n°"+item.getLocation_begin().getDevice());
            deviceLabel.setFont(new Font("utkal", 18));
            AnchorPane.setTopAnchor(deviceLabel, 5.0);
            AnchorPane.setRightAnchor(deviceLabel, 5.0);
            AnchorPane.setBottomAnchor(deviceLabel, 5.0);

            try {
                topPane.getChildren().addAll(idRouteLabel, deviceLabel);
            } catch (IllegalArgumentException e) {}

            String beginString = "Début : "+Utils.getFormatter().format(item.getLocation_begin().getDate())
                    +" (latitude = "+item.getLocation_begin().getLatitude()
                    +" ; longitude = "+item.getLocation_begin().getLongitude()
                    +")";

            beginLabel.setText(beginString);
            beginLabel.setFont(new Font("utkal", 15));
            GridPane.setConstraints(beginLabel, 0, 0);

            String endString;
            if (item.getLocation_end().getDate() != null) {
                endString = "Fin : " + Utils.getFormatter().format(item.getLocation_end().getDate())
                        + " (latitude = " + item.getLocation_end().getLatitude()
                        + " ; longitude = " + item.getLocation_end().getLongitude()
                        + ")";
            } else {
                endString = "Trajet en cours...";
            }

            endLabel.setText(endString);
            endLabel.setFont(new Font("utkal", 15));
            GridPane.setConstraints(endLabel, 0, 1);

            AnchorPane.setTopAnchor(gridPane, 5.0);
            AnchorPane.setLeftAnchor(gridPane, 5.0);
            AnchorPane.setRightAnchor(gridPane, 5.0);
            AnchorPane.setBottomAnchor(gridPane, 5.0);

            try {
                gridPane.getChildren().addAll(beginLabel, endLabel);
                centerPane.getChildren().addAll(gridPane);
            } catch (IllegalArgumentException e) {}

            averageSpeedLabel.setText("Vitesse moyenne : "+String.valueOf(item.getAverageSpeed())+" km/h");
            averageSpeedLabel.setFont(new Font("utkal", 15));
            AnchorPane.setTopAnchor(averageSpeedLabel, 5.0);
            AnchorPane.setLeftAnchor(averageSpeedLabel, 5.0);
            AnchorPane.setRightAnchor(averageSpeedLabel, 5.0);
            AnchorPane.setBottomAnchor(averageSpeedLabel, 5.0);

            try {
                bottomPane.getChildren().addAll(averageSpeedLabel);
            } catch (IllegalArgumentException e) {}

            borderPane.setTop(topPane);
            borderPane.setCenter(centerPane);
            borderPane.setBottom(bottomPane);

            borderPane.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    MenuItem copyItem = new MenuItem("Copier le numéro de vélo");
                    copyItem.setOnAction(handleCopyItem(item));
                    if (contextMenu.getItems().isEmpty()) {
                        contextMenu.getItems().add(copyItem);
                        this.setContextMenu(contextMenu);
                    }
                }
            });

            setGraphic(borderPane);
        } else {
            setGraphic(null);   // <== clear the now empty cell.
        }
    }
}
