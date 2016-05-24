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
	
	public static void addCancion(Connection connection, Cancion cancion) throws SQLException {
		String sql = "INSERT INTO canciones(titulo,artista,album) values (?,?,?)";

		System.out.println("Voy a ejecutar:" + sql);

		Statement stmtUse = connection.createStatement();
		ResultSet use = stmtUse.executeQuery("use musica");

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, cancion.getTitulo());
		stmt.setString(2, cancion.getArtista());
		stmt.setString(3, cancion.getAlbum());
		int filas = stmt.executeUpdate();
		stmt.close();

		System.out.println("Número de filas afectadas:" + filas);
	}

}
