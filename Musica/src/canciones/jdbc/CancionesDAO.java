package canciones.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import canciones.swing.Cancion;

public class CancionesDAO {
	public static List<Cancion> leeCanciones(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet use = stmt.executeQuery("use musica");
		ResultSet rs = stmt.executeQuery("select * from canciones");
		List<Cancion> canciones = new ArrayList<>();
		while (rs.next()) {
			int id = rs.getInt("id");
			String titulo = rs.getString("titulo");
			String artista = rs.getString("artista");
			String album = rs.getString("album");
			String anyo = rs.getString("anyo");
			String genero = rs.getString("genero");
			Cancion c = new Cancion();
			c.setId(id);
			c.setTitulo(titulo);
			c.setArtista(artista);
			c.setAlbum(album);
			c.setAnyo(Integer.parseInt(anyo));
			c.setGenero(genero);
			canciones.add(c);
		}
		rs.close();
		stmt.close();
		return canciones;
	}
}
