package canciones.swing;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
		String rutaDef = "/home/duni/git/Musica/src/canciones/swing/res/";
		addCancionALista("Monster", "Skyllet", "Awake", 2009, "Rock", caratulaByteArray("/canciones/swing/res/1.png"),
				archivoByteArray(rutaDef + "1.mp3"));
		addCancionALista("Vamos al espacio exterior", "Chimo Bayo", "Album desconocido", 1995, "Mákina",
				caratulaByteArray("/canciones/swing/res/2.png"), archivoByteArray(rutaDef + "2.mp3"));
		addCancionALista("Redemption Song", "Bob Marley", "Uprising", 1980, "Reggae",
				caratulaByteArray("/canciones/swing/res/3.png"), archivoByteArray(rutaDef + "3.mp3"));
		addCancionALista("Imagine", "John Lennon", "Imagine", 1971, "Pop",
				caratulaByteArray("/canciones/swing/res/4.png"), archivoByteArray(rutaDef + "4.mp3"));
		addCancionALista("Someone like you", "Adele", "21", 2011, "Soul",
				caratulaByteArray("/canciones/swing/res/5.png"), archivoByteArray(rutaDef + "5.mp3"));
		addCancionALista("Straight Outta Compton", "N.W.A.", "Straight Outta Compton", 1988, "Rap",
				caratulaByteArray("/canciones/swing/res/6.png"), archivoByteArray(rutaDef + "6.mp3"));
		addCancionALista("El niño güei", "SFDK", "2005", 2005, "Rap", caratulaByteArray("/canciones/swing/res/7.png"),
				archivoByteArray(rutaDef + "7.mp3"));
		addCancionALista("Mi carro", "Manolo Escobar", "Y viva España", 1978, "Cancion Española",
				caratulaByteArray("/canciones/swing/res/8.png"), archivoByteArray(rutaDef + "8.mp3"));
		addCancionALista("Mi gran noche", "Raphael", "Digan lo que digan", 1968, "Cancion Española",
				caratulaByteArray("/canciones/swing/res/9.png"), archivoByteArray(rutaDef + "9.mp3"));
		addCancionALista("Blow money fast", "The Zombie Kids", "Album desconocido", 2011, "Electrónica",
				caratulaByteArray("/canciones/swing/res/10.png"), archivoByteArray(rutaDef + "10.mp3"));
		addCancionALista("Warrior", "Andy Caldwell", "Universal Truth", 2006, "Electrónica",
				caratulaByteArray("/canciones/swing/res/11.png"), archivoByteArray(rutaDef + "11.mp3"));
		addCancionALista("Air", "Johann Sebastian Bach", "Album desconocido", 1723, "Clásica",
				caratulaByteArray("/canciones/swing/res/default.png"), archivoByteArray(rutaDef + "12.mp3"));
		addCancionALista("Canon in D major", "Johann Pachelbel", "Album desconocido", 1680, "Clásica",
				caratulaByteArray("/canciones/swing/res/default.png"), archivoByteArray(rutaDef + "13.mp3"));
		addCancionALista("Raining blood", "Slayer", "Reign in blood", 1986, "Metal",
				caratulaByteArray("/canciones/swing/res/14.png"), archivoByteArray(rutaDef + "14.mp3"));
		addCancionALista("Primo victoria", "Sabaton", "Primo victoria", 2005, "Metal",
				caratulaByteArray("/canciones/swing/res/15.png"), archivoByteArray(rutaDef + "15.mp3"));

	}

	private byte[] archivoByteArray(String ruta) throws IOException {

		File f = new File(ruta);
		FileInputStream fis = new FileInputStream(f);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int bytesRead;
		while ((bytesRead = fis.read(buffer)) > 0) {
			baos.write(buffer, 0, bytesRead);
		}
		return baos.toByteArray();
	}

	private byte[] caratulaByteArray(String ruta) throws IOException {
		try {
			InputStream in = null;
			in = getClass().getResourceAsStream(ruta);
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
