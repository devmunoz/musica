package canciones.swing;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ListaCancionesDefecto {
	private ArrayList<Cancion> canciones;

	public void addCancionALista(String titulo, String artista, String album, int anyo, String genero, byte[] caratula,
			byte[] archivo) {
		Cancion c = new Cancion();
		c.setTitulo(titulo);
		c.setArtista(artista);
		c.setAlbum(album);
		c.setAnyo(anyo);
		c.setGenero(genero);
		c.setCaratula(caratula);
		c.setArchivo(archivo);

		canciones.add(c);
	}

	public List<Cancion> getCanciones() {
		return canciones;
	}

	public int getNumCanciones() {
		return canciones.size();
	}

	public void addCancionesDefecto() throws IOException {
		canciones = new ArrayList<Cancion>();
		String rutaDef = "/canciones/swing/res/";
		addCancionALista("Ejemplo 1", "devmunoz & Suno AI", "IA music 2024", 2009, "Reggae", caratulaByteArray(rutaDef + "1.png"), archivoByteArray(rutaDef + "1.mp3"));

		addCancionALista("Ejemplo 2", "devmunoz & Suno AI", "IA music 2024", 2024, "Reggae",
		caratulaByteArray(rutaDef + "2.png"), archivoByteArray(rutaDef + "2.mp3"));
	}

	private byte[] archivoByteArray(String ruta) throws IOException {
		InputStream is = getClass().getResourceAsStream(ruta);
		if (is == null) {
			throw new FileNotFoundException("Recurso no encontrado: " + ruta);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int bytesRead;
		while ((bytesRead = is.read(buffer)) > 0) {
			baos.write(buffer, 0, bytesRead);
		}
		is.close();
		return baos.toByteArray();
	}

	private byte[] caratulaByteArray(String ruta) throws IOException {
		try {
			InputStream in = null;
			in = getClass().getResourceAsStream(ruta);
			if (in == null) {
				throw new FileNotFoundException("Recurso no encontrado: " + ruta);
			}
			ImageIcon ii = new ImageIcon(ImageIO.read(in));
			BufferedImage i = (BufferedImage) ii.getImage();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				ImageIO.write(i, "png", out);
				out.flush();
				return out.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
