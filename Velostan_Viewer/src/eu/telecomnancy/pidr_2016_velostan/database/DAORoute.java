package eu.telecomnancy.pidr_2016_velostan.database;



import eu.telecomnancy.pidr_2016_velostan.model.LocationModel;
import eu.telecomnancy.pidr_2016_velostan.model.RouteModel;
import eu.telecomnancy.pidr_2016_velostan.utils.Utils;
import eu.telecomnancy.pidr_2016_velostan.views.ExceptionAlert;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yoann on 20/05/16.
 */
public class DAORoute {

    private static DAORoute dao = null;

    private DAORoute() {}

    public static DAORoute getInstance() {
        if (dao == null)
            dao = new DAORoute();
        return dao;
    }

    public List<RouteModel> getAllRoutes() throws IOException {
        ConnectionJDBC jdbc = ConnectionJDBC.getInstance();
        List<RouteModel> listRoutes = new ArrayList<>();

        String query = "SELECT * FROM trajet ORDER BY id_trajet DESC";
        Statement statement = null;
        ResultSet result = null;

        try {
            PreparedStatement preparedStmt = jdbc.getConnection().prepareStatement(query);
            preparedStmt.execute();

            statement = jdbc.getConnection().createStatement();

            if (statement.execute(query)) {
                result = statement.getResultSet();
            }

            while (result != null && result.next()) {
                RouteModel route = new RouteModel();
                LocationModel locationDebut = new LocationModel();
                LocationModel locationFin = new LocationModel();

                locationDebut.setDevice(result.getString("device"));
                locationDebut.setIdTrajet(result.getInt("id_trajet"));
                locationDebut.setLatitude(result.getDouble("latitude_debut"));
                locationDebut.setLongitude(result.getDouble("longitude_debut"));

                String dateDebutString = result.getString("date_debut");
                Date dateDebut = null;
                try {
                    dateDebut = Utils.getFormatter().parse(dateDebutString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                locationDebut.setDate(dateDebut);

                //
                // ATTENTION POSSIBLE VALEUR NULL
                //

                locationFin.setDevice(result.getString("device"));
                locationFin.setIdTrajet(result.getInt("id_trajet"));
                locationFin.setLatitude(result.getDouble("latitude_fin"));
                locationFin.setLongitude(result.getDouble("longitude_fin"));

                String dateFinString = result.getString("date_fin");
                Date dateFin = null;

                if (dateFinString != null) {
                    try {
                        dateFin = Utils.getFormatter().parse(dateFinString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    locationFin.setDate(dateFin);
                }

                route.setId_route(result.getInt("id_trajet"));
                route.setDevice(result.getString("device"));
                route.setAverageSpeed(result.getDouble("vitesse_moyenne"));
                route.setLocation_begin(locationDebut);
                route.setLocation_end(locationFin);

                listRoutes.add(route);
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            new ExceptionAlert(ex, "Erreur : Récupération de tous les trajets a échoué.").showAndWait();
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return listRoutes;

    }

    public void deleteRoute(RouteModel route) throws IOException  {
        ConnectionJDBC jdbc = ConnectionJDBC.getInstance();

        try {
            String query = "DELETE FROM trajet WHERE id_trajet = ?";
            PreparedStatement preparedStmt = jdbc.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, route.getId_route());

            preparedStmt.execute();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            new ExceptionAlert(e, "Erreur : Suppression du trajet n°"+route.getId_route()+" a échoué.").showAndWait();
        }

        System.out.println("The route n°"+route.getId_route()+" has been removed from the database.");
    }
}
