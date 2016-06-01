package canciones.jdbc;

import java.sql.Connection;
import java.sql.SQLException;



import canciones.swing.Cancion;;
public class EditaCancion {
	public static void main(String[] args) {
		ConexionBD.cargaDriver();
		
		Connection connection = null;
		try{

			connection = ConexionBD.creaConexion();
			
			Cancion cancion = contactoAActualizar();
			
			boolean ok = CancionesDAO.editaCancion(connection, cancion);
			System.out.println(ok);
			
			
			connection.close();
		}
		catch( SQLException e ){
			e.printStackTrace();
		}
		finally {
			try {
				if (connection != null && !connection.isClosed() ) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	private static Cancion contactoAActualizar() {
		//TODO controlar cuantos campos edito para ver cuales deben cambiar

		Cancion c = new Cancion();
		c.setId(3);
		c.setTitulo("Titulo");
		c.setArtista("Díaz Pérez");
		return c;
	}

}
