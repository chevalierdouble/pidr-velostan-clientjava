package eu.telecomnancy.pidr_2016_velostan.database;

/**
 * Created by thomasgauthey on 18/05/2016.
 */

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

public class DAOLocation {

    private static DAOLocation dao = null;

    private DAOLocation() {}

    public static DAOLocation getInstance() {
        if (dao == null)
            dao = new DAOLocation();
        return dao;
    }

    public LocationModel getLocationByID(int id) throws IOException {
        ConnectionJDBC jdbc = ConnectionJDBC.getInstance();
        LocationModel location = new LocationModel();

        String query = "SELECT * FROM location WHERE id_location = ?";
        Statement statement = null;
        ResultSet result = null;

        try {
            PreparedStatement preparedStmt = jdbc.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, id);
            preparedStmt.execute();

            statement = jdbc.getConnection().createStatement();

            if (statement.execute(query)) {
                result = statement.getResultSet();
            }

            while (result != null && result.next()) {
                location.setId(result.getInt("id_location"));
                location.setDevice(result.getString("device"));
                location.setLatitude(result.getDouble("latitude"));
                location.setLongitude(result.getDouble("longitude"));
                location.setSpeed(result.getDouble("speed"));
                location.setIdTrajet(result.getInt("id_trajet"));

                String dateString = result.getString("date_location");
                Date date = null;
                try {
                    date = Utils.getFormatter().parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                location.setDate(date);
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            new ExceptionAlert(ex, "Erreur : Récupération de la Location n°"+location.getId()+" a échoué.").showAndWait();
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

        return location;
    }

    public List<LocationModel> getAllLocations() throws IOException {
        ConnectionJDBC jdbc = ConnectionJDBC.getInstance();
        List<LocationModel> listLocations = new ArrayList<>();

        String query = "SELECT * FROM location";
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
                LocationModel location = new LocationModel();
                location.setId(result.getInt("id_location"));
                location.setDevice(result.getString("device"));
                location.setLatitude(result.getDouble("latitude"));
                location.setLongitude(result.getDouble("longitude"));
                location.setSpeed(result.getDouble("speed"));
                location.setIdTrajet(result.getInt("id_trajet"));

                String dateString = result.getString("date_location");
                Date date = null;
                try {
                    date = Utils.getFormatter().parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                location.setDate(date);

                listLocations.add(location);
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            new ExceptionAlert(ex, "Erreur : Récupération de toutes les Locations a échoué.").showAndWait();
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

        return listLocations;

    }

    public List<LocationModel> getLocationsByRoute(RouteModel route) throws IOException {
        ConnectionJDBC jdbc = ConnectionJDBC.getInstance();
        List<LocationModel> listLocations = new ArrayList<>();

        String query = "SELECT * FROM location WHERE id_trajet = "+route.getId_route();
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
                LocationModel location = new LocationModel();
                location.setId(result.getInt("id_location"));
                location.setDevice(result.getString("device"));
                location.setLatitude(result.getDouble("latitude"));
                location.setLongitude(result.getDouble("longitude"));
                location.setSpeed(result.getDouble("speed"));
                location.setIdTrajet(result.getInt("id_trajet"));

                String dateString = result.getString("date_location");
                Date date = null;
                try {
                    date = Utils.getFormatter().parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                location.setDate(date);

                listLocations.add(location);
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            new ExceptionAlert(ex, "Erreur : Récupération de toutes les Locations appartenant au trajet n°"+route.getId_route()+" a échoué.").showAndWait();
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

        return listLocations;

    }

}

