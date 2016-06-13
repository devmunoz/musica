package canciones.swing;

public class Cancion {
	private int id;

	private String titulo = "Título desconocido";
	private String artista = "Artista desconocido";
	private String album = "Álbum desconocido";
	private int anyo = 0;
	private String genero = "Género desconocido";
	private byte[] caratula;
	private byte[] archivo = {};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

		boolean karatula = false;
		boolean harchivo = false;
		if (getCaratula() != null) {
			karatula = true;
		}
		if (getArchivo() != null) {
			harchivo = true;
		}
		return "Cancion [id: " + getId() + "  titulo: " + getTitulo() + "  artista: " + getArtista() + "  album: "
				+ getAlbum() + "  año: " + getAnyo() + "  genero: " + getGenero() + "  imagen: " + karatula
				+ "  archivo: " + harchivo + "]";
	}

}
