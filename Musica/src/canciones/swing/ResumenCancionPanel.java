package canciones.swing;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class ResumenCancionPanel extends JPanel {

	private Cancion cancion;
	private JLabel caratula;
	private JLabel titulo;
	private JLabel artista;
	private JLabel album;
	private JLabel genero;
	private JLabel anyo;

	public ResumenCancionPanel() {
		initComponents();
	}

	public void setCancion(Cancion c) {
		cancion = c;

		titulo.setText(c.getTitulo());
		artista.setText(c.getArtista());
		album.setText(c.getAlbum());
		if (c.getAnyo() == 0) {
			anyo.setText("Año desconocido");
		} else {
			anyo.setText("" + (c.getAnyo()));
		}
		genero.setText(c.getGenero());

		caratula.setIcon(null);
		{
			byte[] imagen = c.getCaratula();
			try {
				InputStream in = null;
				if (imagen == null) {
					in = getClass().getResourceAsStream("/canciones/swing/res/default.png");
				} else {
					in = new ByteArrayInputStream(imagen);
				}
				BufferedImage image = ImageIO.read(in);
				if (image != null) {
					ImageIcon ii = new ImageIcon(image);

					// escalar la imagen a 100
					Image img = ii.getImage();
					Image newimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
					ii = new ImageIcon(newimg);

					caratula.setIcon(ii);
				}
				caratula.setText("");

			} catch (IOException e) {
				// DEJO LA IMAGEN VACIA O COMO ESTUVIERA
				caratula.setIcon(null);
				caratula.setText("Imagen incorrecta");
				e.printStackTrace();
			}
		}
	}

	private void initComponents() {
		setMinimumSize(new Dimension(500, 150));

		caratula = new JLabel("caratula");
		caratula.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));

		JLabel lblTituloDeLa = new JLabel("Titulo:");

		JLabel lblArtista = new JLabel("Artista:");

		JLabel lblAlbum = new JLabel("Album:");

		JLabel lblAo = new JLabel("Año:");

		JLabel lblGenero = new JLabel("Genero:");

		titulo = new JLabel("Titulo de la cancion");

		artista = new JLabel("Titulo de la cancion");

		album = new JLabel("Titulo de la cancion");

		anyo = new JLabel("Titulo de la cancion");

		genero = new JLabel("Titulo de la cancion");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				groupLayout.createSequentialGroup().addGap(20)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTituloDeLa, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblArtista, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAlbum, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAo, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblGenero, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(genero, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
								.addComponent(titulo, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
								.addComponent(artista, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
								.addComponent(album, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
								.addComponent(anyo, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(caratula, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addGap(25)));
		groupLayout
				.setVerticalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(16)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblTituloDeLa, GroupLayout.PREFERRED_SIZE, 20,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(titulo))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblArtista, GroupLayout.PREFERRED_SIZE, 20,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(artista))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblAlbum, GroupLayout.PREFERRED_SIZE, 20,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(album))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblAo, GroupLayout.PREFERRED_SIZE, 20,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(anyo))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblGenero, GroupLayout.PREFERRED_SIZE, 20,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(genero))
										.addContainerGap())
								.addGroup(groupLayout
										.createSequentialGroup().addGap(25).addComponent(caratula,
												GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(25, Short.MAX_VALUE)));
		setLayout(groupLayout);
	}
}
