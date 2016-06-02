package eu.telecomnancy.pidr_2016_velostan.database;

import eu.telecomnancy.pidr_2016_velostan.views.ExceptionAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class ConnectionJDBC {

	private static Connection connection = null;
	private static ConnectionJDBC connectionJDBC = null;

	private static String DRIVER = "";
	private static String URL = "";
	private static String USER = "";
	private static String PASSWORD = "";

	public static ConnectionJDBC getInstance() throws IOException {
		if (connectionJDBC == null)
			connectionJDBC = new ConnectionJDBC();
		return connectionJDBC;
	}
	
	public static void close() throws IOException, SQLException {
        if (connectionJDBC != null)
			connectionJDBC.getConnection().close();
	}

	private ConnectionJDBC() throws IOException {
		DRIVER="com.mysql.jdbc.Driver";
		URL="jdbc:mysql://127.10.7.130:3306/webservice";
		USER="adminL2r5wiS";
		PASSWORD=" 9uY_EEznfpWN";

		try {	
			Class.forName(DRIVER).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			new ExceptionAlert(e, "Erreur : Driver non trouvée").showAndWait();
		}

		try {
			connection = DriverManager.getConnection( URL, USER, PASSWORD );
			
		} catch ( SQLException e ) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
			new ExceptionAlert(e, "Erreur : Connection à la base de données impossible").showAndWait();
		}
	}

	public Connection getConnection() {
		return ConnectionJDBC.connection;
	}
}
