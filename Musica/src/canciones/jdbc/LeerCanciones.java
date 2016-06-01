package canciones.jdbc;

import static canciones.jdbc.ConexionBD.*;
import static canciones.jdbc.CancionesDAO.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import canciones.swing.Cancion;

public class LeerCanciones {
	public static void main(String[] args) {

		cargaDriver();

		Connection connection = null;
		try {
			connection = creaConexion();
			System.out.println("Conexión establecida");

			List<Cancion> canciones = leeCancion(connection);
			
			for( Cancion c : canciones ){
				System.out.println(c);
			}

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null && !connection.isClosed() ) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
