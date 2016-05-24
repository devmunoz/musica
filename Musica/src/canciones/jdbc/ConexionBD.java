package canciones.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	public static Connection creaConexion() throws SQLException {
		Connection connection;
		String url = "jdbc:mysql://localhost";
		String user = "padmin";
		String password = "duni";
		connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

	public static void cargaDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}