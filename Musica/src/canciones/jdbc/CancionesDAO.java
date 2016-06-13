package canciones.jdbc;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import canciones.swing.Cancion;

public class CancionesDAO {

	public static List<Cancion> leeCancion(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select * from canciones");
		List<Cancion> canciones = new ArrayList<>();
		while (rs.next()) {
			int id = rs.getInt("id");
			String titulo = rs.getString("titulo");
			String artista = rs.getString("artista");
			String album = rs.getString("album");
			String anyo = rs.getString("anyo");
			String genero = rs.getString("genero");
			Blob caratula = rs.getBlob("caratula");
			Blob archivo = rs.getBlob("archivo");
			Cancion c = new Cancion();
			c.setId(id);
			c.setTitulo(titulo);
			c.setArtista(artista);
			c.setAlbum(album);
			c.setAnyo(Integer.parseInt(anyo));
			c.setGenero(genero);
			if (caratula != null) {
				byte[] bytes = caratula.getBytes(1, (int) caratula.length());
				c.setCaratula(bytes);
			}
			if (archivo != null) {
				byte[] bytes = archivo.getBytes(1, (int) archivo.length());
				c.setArchivo(bytes);
			}

			canciones.add(c);
		}
		rs.close();
		stmt.close();
		return canciones;
	}

	public static void addCancion(Connection connection, Cancion cancion) throws SQLException {
		String sql = "INSERT INTO canciones(titulo,artista,album,anyo,genero,caratula,archivo) values (?,?,?,?,?,?,?)";
		cancion = cortaCancion(cancion);
		System.out.println("Añadiendo cancion a la BD...");

		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, cancion.getTitulo());
		stmt.setString(2, cancion.getArtista());
		stmt.setString(3, cancion.getAlbum());
		stmt.setInt(4, cancion.getAnyo());
		stmt.setString(5, cancion.getGenero());

		Blob blobCaratula = connection.createBlob();
		blobCaratula.setBytes(1, cancion.getCaratula());
		stmt.setBlob(6, blobCaratula);

		Blob blobArchivo = connection.createBlob();
		blobArchivo.setBytes(1, cancion.getArchivo());
		stmt.setBlob(7, blobArchivo);

		int filas = stmt.executeUpdate();
		ResultSet generatedKeys = stmt.getGeneratedKeys();

		if (!generatedKeys.next()) {
			throw new IllegalStateException("El id es autonumerico y lo debe generar la BD");
		}

		int id = generatedKeys.getInt(1);
		cancion.setId(id);
		stmt.close();

		System.out.println("Número de filas afectadas:" + filas);
	}

	public static boolean editaCancion(Connection connection, Cancion cancion) throws SQLException {
		String sql = "UPDATE canciones SET titulo=?, artista=?, album=?, anyo=?, genero=?, caratula=?, archivo=? where id=?";
		cancion = cortaCancion(cancion);
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, cancion.getTitulo());
		stmt.setString(2, cancion.getArtista());
		stmt.setString(3, cancion.getAlbum());
		stmt.setInt(4, cancion.getAnyo());
		stmt.setString(5, cancion.getGenero());

		Blob blobCaratula = connection.createBlob();
		blobCaratula.setBytes(1, cancion.getCaratula());
		stmt.setBlob(6, blobCaratula);

		Blob blobArchivo = connection.createBlob();
		blobArchivo.setBytes(1, cancion.getArchivo());
		stmt.setBlob(7, blobArchivo);

		stmt.setInt(8, cancion.getId());

		int filas = stmt.executeUpdate();
		stmt.close();

		return filas == 1;

	}

	public static boolean borraCancion(Connection connection, Cancion cancion) throws SQLException {
		String sql = "DELETE FROM canciones WHERE id=?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, cancion.getId());
		int filas = stmt.executeUpdate();
		stmt.close();
		return filas == 1;

	}

	public static Cancion buscarPorID(Connection connection, int id) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"select id,titulo, artista, album, anyo, genero, caratula, archivo from canciones where id=?");
		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();
		List<Cancion> canciones = new ArrayList<>();
		while (rs.next()) {
			int idLeido = rs.getInt("id");
			String titulo = rs.getString("titulo");
			String artista = rs.getString("artista");
			String album = rs.getString("album");
			String anyo = rs.getString("anyo");
			String genero = rs.getString("genero");
			Blob caratula = rs.getBlob("caratula");
			Blob archivo = rs.getBlob("archivo");
			Cancion cancion = new Cancion();

			cancion.setId(idLeido);
			cancion.setTitulo(titulo);
			cancion.setArtista(artista);
			cancion.setAlbum(album);
			cancion.setAnyo(Integer.parseInt(anyo));
			cancion.setGenero(genero);

			if (caratula != null) {
				byte[] bytes = caratula.getBytes(1, (int) caratula.length());
				cancion.setCaratula(bytes);
			}
			if (archivo != null) {
				byte[] bytes = archivo.getBytes(1, (int) archivo.length());
				cancion.setArchivo(bytes);
			}

			canciones.add(cancion);
		}

		rs.close();
		stmt.close();

		if (canciones.size() > 1) {
			throw new IllegalStateException();
		}
		if (canciones.size() == 0) {
			return null;
		}

		return canciones.get(0);
	}

	/**
	 * Ajusta los valores de la cancion para que no falle al insertarlos en la
	 * Base de Datos.
	 * 
	 * @param cancion
	 * @return cancion con campos ajustados
	 */
	private static Cancion cortaCancion(Cancion cancion) {
		if (cancion.getTitulo().length() > 50) {
			cancion.setTitulo(cancion.getTitulo().substring(0, 50));
		}
		if (cancion.getArtista().length() > 50) {
			cancion.setArtista(cancion.getArtista().substring(0, 50));
		}
		if (cancion.getAlbum().length() > 50) {
			cancion.setAlbum(cancion.getAlbum().substring(0, 50));
		}
		if (cancion.getAnyo() > 9999) {
			cancion.setAnyo(9999);
		}
		if (cancion.getGenero().length() > 50) {
			cancion.setGenero(cancion.getGenero().substring(0, 50));
		}

		return cancion;
	}
}
