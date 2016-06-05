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

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, cancion.getTitulo());
		stmt.setString(2, cancion.getArtista());
		stmt.setString(3, cancion.getAlbum());
		int filas = stmt.executeUpdate();
		stmt.close();

		System.out.println("Número de filas afectadas:" + filas);
	}
	

	public static boolean editaCancion(Connection connection, Cancion cancion) throws SQLException {
		String sql = "UPDATE canciones SET titulo=?, artista=?, album=?, anyo=?, genero=?, caratula=?, archivo=? where id=?";
		
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
		PreparedStatement stmt = connection.prepareStatement("select id,titulo,artista from canciones where id=?");
		stmt.setInt(1,id);

		ResultSet rs = stmt.executeQuery();
		List<Cancion> canciones = new ArrayList<>();
		while (rs.next()) {
			int idLeido = rs.getInt("id");
			String titulo = rs.getString("titulo");
			String artista = rs.getString("artista");
			Cancion cancion = new Cancion();
			cancion.setTitulo(titulo);
			cancion.setArtista(artista);
			cancion.setId(idLeido);
			canciones.add(cancion);
		}
		
		rs.close();
		stmt.close();
		
		if( canciones.size() > 1 ){
			throw new IllegalStateException();
		}
		if( canciones.size() == 0 ){
			return null;
		}
		
		return canciones.get(0);
	}


}
