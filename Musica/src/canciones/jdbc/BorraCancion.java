package canciones.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import canciones.swing.Cancion;;

public class BorraCancion {

	public static void main(String[] args) {
		ConexionBD.cargaDriver();

		Connection connection = null;
		try {

			connection = ConexionBD.creaConexion();

			Cancion cancion = cancionABorrar();

			boolean ok = CancionesDAO.borraCancion(connection, cancion);
			System.out.println(ok);

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	private static Cancion cancionABorrar() {
		Cancion c = new Cancion();
		c.setId(3);
		return c;
	}

}
