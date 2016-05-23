package canciones.swing;

import java.util.Arrays;

public class Cancion {
	private String titulo = "<Sin titulo>";
	private String artista = "<Sin artista>";
	private String album = "<Sin album>";
	private int anyo = 0;
	private String genero = "<Sin genero>";
	private byte[] caratula;
	private byte[] archivo;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public byte[] getCaratula() {
		return caratula;
	}

	public void setCaratula(byte[] caratula) {
		this.caratula = caratula;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	@Override
	public String toString() {

		return "Cancion [titulo: " + getTitulo() + "  artista: " + getArtista() + "  album: " + getAlbum() + "  año: "
				+ getAnyo() + "  genero: " + getGenero() + "  imagen: " + Arrays.toString(caratula) + "  archivo: "
				+ Arrays.toString(archivo) + "]";
	}

}
