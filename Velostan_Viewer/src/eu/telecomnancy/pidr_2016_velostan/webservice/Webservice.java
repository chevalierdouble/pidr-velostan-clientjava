package eu.telecomnancy.pidr_2016_velostan.webservice;

import eu.telecomnancy.pidr_2016_velostan.model.LocationModel;
import eu.telecomnancy.pidr_2016_velostan.model.RouteModel;
import eu.telecomnancy.pidr_2016_velostan.utils.Utils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yoann on 02/06/16.
 */
public class Webservice {

    private static JSONParser jsonParser = new JSONParser();

    private final static String formatDate = "dd/MM/yyyy HH:mm:ss";
    private final static String url_get_locations_by_route = "http://webservice-velostan.rhcloud.com/webservice/get_locations_by_route.php";
    private final static String url_delete_route = "http://webservice-velostan.rhcloud.com/webservice/delete_route.php";
    private final static String url_get_all_routes = "http://webservice-velostan.rhcloud.com/webservice/get_all_routes.php";
    private final static String TAG_SUCCESS = "success";

    public static List<LocationModel> getLocationsByRoute(RouteModel route) {

        List<LocationModel> listLocations = new ArrayList<>();
        JSONArray locations = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("id_trajet", Integer.toString(route.getId_route())));

        // getting JSON string from URL
        JSONObject json = jsonParser.makeHttpRequest(url_get_locations_by_route, "GET", params);

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // products found
                // Getting Array of Products
                locations = json.getJSONArray("location");

                // looping through All Products
                for (int i = 0; i < locations.length(); i++) {
                    JSONObject c = locations.getJSONObject(i);

                    // Storing each json item in variable
                    int id_location = c.getInt("id_location");
                    String device = c.getString("device");
                    double latitude = c.getDouble("latitude");
                    double longitude = c.getDouble("longitude");
                    double speed = c.getDouble("speed");
                    int id_trajet = c.getInt("id_trajet");

                    String date_location = c.getString("date_location");

                    Date date = null;
                    try {
                        date = Utils.getFormatter().parse(date_location);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    LocationModel l = new LocationModel(id_location, device, latitude, longitude, speed, date, id_trajet);

                    // adding HashList to ArrayList
                    listLocations.add(l);
                }
                System.out.println("get_locations_by_route : Success");
            } else {
                System.out.println("get_locations_by_route : Fail");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listLocations;
    }

    public static void deleteRoute(RouteModel route) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id_trajet", Integer.toString(route.getId_route())));

        // getting JSON Object
        // Note that create product url accepts POST method
        //jsonParser.makeHttpRequest(url_update_trajet, "POST", params);
        JSONObject json = jsonParser.makeHttpRequest(url_delete_route, "POST", params);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully deleted product
                System.out.println("delete_route : Success ("+json.getString("message")+")");
                System.out.println("Route "+json.getString("id_trajet")+" has been succesfully deleted.");
            } else {
                // failed to delete product
                System.out.println("delete_route : Fail ("+json.getString("message")+")");
                System.out.println("Route "+json.getString("id_trajet")+" hasn't been deleted.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<RouteModel> getAllRoutes() {

        List<RouteModel> listRoutes = new ArrayList<>();
        JSONArray routes = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        // getting JSON string from URL
        JSONObject json = jsonParser.makeHttpRequest(url_get_all_routes, "GET", params);

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // products found
                // Getting Array of Products
                routes = json.getJSONArray("trajet");

                // looping through All Products
                for (int i = 0; i < routes.length(); i++) {
                    JSONObject c = routes.getJSONObject(i);

                    // Storing each json item in variable
                    int id_trajet = c.getInt("id_trajet");
                    String device = c.getString("device");
                    double latitude_debut = c.getDouble("latitude_debut");
                    double longitude_debut = c.getDouble("longitude_debut");

                    String date_debut_string = c.getString("date_debut");
                    Date date_debut = null;
                    try {
                        date_debut = Utils.getFormatter().parse(date_debut_string);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                    RouteModel route = new RouteModel();
                    LocationModel locationDebut = new LocationModel();
                    LocationModel locationFin = new LocationModel();

                    locationDebut.setDevice(device);
                    locationDebut.setIdTrajet(id_trajet);
                    locationDebut.setLatitude(latitude_debut);
                    locationDebut.setLongitude(longitude_debut);
                    locationDebut.setDate(date_debut);

                    //
                    // ATTENTION POSSIBLE VALEUR NULL
                    //

                    locationFin.setDevice(device);
                    locationFin.setIdTrajet(id_trajet);

                    Object latitude_fin_obj = c.get("latitude_fin");

                    if (latitude_fin_obj != null) {
                        //String latitude_fin = latitude_fin_obj.getString("latitude_fin");
                        if (latitude_fin_obj instanceof String) {
                            String latitude_fin = (String) latitude_fin_obj;
                            locationFin.setLatitude(Double.parseDouble(latitude_fin));
                        }
                    }

                    Object longitude_fin_obj = c.get("longitude_fin");

                    if (longitude_fin_obj != null) {
                        //String longitude_fin = longitude_fin_obj.getString("longitude_fin");
                        if (longitude_fin_obj instanceof String) {
                            String longitude_fin = (String) longitude_fin_obj;
                            locationFin.setLongitude(Double.parseDouble(longitude_fin));
                        }
                    }

                    Object date_fin_obj = c.get("date_fin");
                    if (date_fin_obj != null) {
                        if (date_fin_obj instanceof String) {
                            String date_fin_string = (String) date_fin_obj;
                            Date date_fin = null;
                            try {
                                date_fin = Utils.getFormatter().parse(date_fin_string);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            locationFin.setDate(date_fin);
                        }
                    }

                    route.setId_route(id_trajet);
                    route.setDevice(device);
                    route.setLocation_begin(locationDebut);
                    route.setLocation_end(locationFin);

                    Object vitesse_moyenne_obj = c.get("vitesse_moyenne");

                    if (vitesse_moyenne_obj != null) {
                        //String vitesse_moyenne = vitesse_moyenne_obj.getString("vitesse_moyenne");
                        if (vitesse_moyenne_obj instanceof String) {
                            String vitesse_moyenne = (String) vitesse_moyenne_obj;
                            route.setAverageSpeed(Double.parseDouble(vitesse_moyenne));
                        }
                    }

                    listRoutes.add(route);
                }
                System.out.println("get_all_routes : Success");
            } else {
                System.out.println("get_all_routes : Fail");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listRoutes;
    }

}
