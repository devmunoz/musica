package canciones.jdbc;
import static canciones.jdbc.CancionesDAO.*;
import static canciones.jdbc.ConexionBD.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import canciones.swing.Cancion;

public class AddCancion {
	public static void main(String[] args) {
		cargaDriver();
		
		Connection connection = null;
		try{

			connection = creaConexion();
			
			Cancion cancion = cancionAInsertar();
			
			addCancion(connection, cancion);
			System.out.println( leeCanciones(connection) );
			
			
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
	
	private static Cancion cancionAInsertar() {
		Scanner in = new Scanner(System.in);
		System.out.print("Titulo de la nueva cancion: ");
		String titulo = in.nextLine();
		System.out.print("Artista de la nueva cancion: ");
		String artista = in.nextLine();
		System.out.print("Album de la nueva cancion: ");
		String album = in.nextLine();
		Cancion cancion = new Cancion();
		cancion.setTitulo(titulo);
		cancion.setArtista(artista);
		cancion.setAlbum(album);
		return cancion;
	}

}
