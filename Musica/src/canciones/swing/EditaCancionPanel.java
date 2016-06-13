package canciones.swing;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class EditaCancionPanel extends JPanel {
	private JTextField tituloText;
	private JTextField artistaText;
	private JTextField artistaText_1;
	private JTextField albumText;
	private JTextField albumText_1;
	private JTextField anyoText;
	private JTextField anyoText_1;
	private JTextField generoText;
	private JTextField generoText_1;

	private JButton caratulaLabel;

	private Cancion _cancion;

	private ImageIcon _ii;

	public Cancion getCancion() {
		return _cancion;
	}

	public void setCancion(Cancion _cancion) {
		this._cancion = _cancion;
		deCancionAPanel();
	}

	public void deCancionAPanel() {
		Cancion c = getCancion();
		tituloText.setText(c.getTitulo());
		artistaText_1.setText(c.getArtista());
		albumText_1.setText(c.getAlbum());
		if (c.getAnyo() == 0) {
			anyoText_1.setText("Año desconocido");
		} else {
			anyoText_1.setText("" + (c.getAnyo()));
		}

		generoText_1.setText(c.getGenero());

		// añadir imagen
		{
			byte[] caratula = c.getCaratula();
			try {
				InputStream in = null;
				System.err.println(caratula);

				if (caratula == null) {
					in = getClass().getResourceAsStream("/canciones/swing/res/default.png");
				} else {
					in = new ByteArrayInputStream(caratula);
				}
				ImageIcon ii = new ImageIcon(ImageIO.read(in));

				set_ii(ii);

				caratulaLabel.setIcon(redimensionaImg(ii));
				caratulaLabel.setText("");

			} catch (IOException e) {
				// DEJO LA IMAGEN VACIA O COMO ESTUVIERA
				caratulaLabel.setIcon(null);
				caratulaLabel.setText("Imagen incorrecta");
				e.printStackTrace();
			}
		}
	}

	public ImageIcon get_ii() {
		return _ii;
	}

	public void set_ii(ImageIcon _ii) {
		this._ii = _ii;
	}

	private ImageIcon redimensionaImg(ImageIcon ii) {
		Image img = ii.getImage();
		Image newimg = img.getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}

	public void guardarCancion() {
		System.out.println("Guardando cancion...");
		Cancion c = getCancion();

		c = arreglaCancion(c);
		System.out.println("Antes de cambiar la cancion:" + c);


		{
			ImageIcon icon = get_ii();

			BufferedImage i = (BufferedImage) icon.getImage();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				ImageIO.write(i, "png", out);
				out.flush();
				c.setCaratula(out.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		System.out.println("Después de cambiar la cancion:" + c);
		System.out.println("Cancion guardada.");

	}

	/**
	 * Quita blancos y arregla el string del año.
	 * 
	 * @param c
	 * @return
	 */
	private Cancion arreglaCancion(Cancion c) {

		if (tituloText.getText().equals("")) {
			c.setTitulo("Título desconocido");
		} else {
			c.setTitulo(tituloText.getText());
		}

		if (artistaText_1.getText().equals("")) {
			c.setArtista("Artista desconocido");
		} else {
			c.setArtista(artistaText_1.getText());
		}

		if (albumText_1.getText().equals("")) {
			c.setAlbum("Album desconocido");
		} else {
			c.setAlbum(albumText_1.getText());
		}

		if (anyoText_1.getText().equals("") || anyoText_1.getText().equals("Año desconocido")) {
			c.setAnyo(0);
		} else {
			c.setAnyo(Integer.parseInt(anyoText_1.getText()));
		}

		if (generoText_1.getText().equals("")) {
			c.setGenero("Género desconocido");
		} else {
			c.setGenero(generoText_1.getText());
		}

		return c;
	}

	private void seleccionImagen() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filtroImagen=new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
	    chooser.setFileFilter(filtroImagen);
		chooser.showOpenDialog(this);
		File f = chooser.getSelectedFile();
		if (f != null) {
			try {
				BufferedImage i = ImageIO.read(f);
				ImageIcon ii = new ImageIcon(i);
				set_ii(ii);
				caratulaLabel.setIcon(redimensionaImg(ii));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void seleccionArchivo() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filtroImagen=new FileNameExtensionFilter("MP3 & WAV","mp3","wav");
	    chooser.setFileFilter(filtroImagen);
		chooser.showOpenDialog(this);
		File f = chooser.getSelectedFile();
		if (f != null) {
			try {
				FileInputStream fis = new FileInputStream(f);
				_cancion.setArchivo(archivoToByteArray(fis));
				System.out.println(_cancion);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private byte[] archivoToByteArray(FileInputStream bis) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int bytesRead;
		while ((bytesRead = bis.read(buffer)) > 0) {
			baos.write(buffer, 0, bytesRead);
		}
		return baos.toByteArray();
	}

	/**
	 * Create the panel.
	 */
	public EditaCancionPanel() {
		setFocusTraversalPolicyProvider(true);

		initComponents();

	}

	private void initComponents() {

		JLabel lblTitulo = new JLabel("Titulo");

		JLabel lblArtista = new JLabel("Artista");

		JLabel lblAlbum = new JLabel("Album");

		JLabel lblAo = new JLabel("Año");

		JLabel lblGenero = new JLabel("Genero");

		tituloText = new JTextField();
		tituloText.setColumns(10);

		artistaText = new JTextField();
		artistaText.setColumns(10);

		albumText = new JTextField();
		albumText.setColumns(10);

		anyoText = new JTextField();
		anyoText.setColumns(10);

		generoText = new JTextField();
		generoText.setColumns(10);

		new JButton("caratulaLabel");

		JButton seleccionarButton = new JButton("Seleccion archivo");
		seleccionarButton.setMnemonic('a');
		seleccionarButton.setToolTipText("Pulsa para seleccionar el archivo de audio");
		seleccionarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("tocaste editar archivo");
				seleccionArchivo();
			}
		});

		artistaText_1 = new JTextField();
		artistaText_1.setColumns(10);

		albumText_1 = new JTextField();
		albumText_1.setColumns(10);

		anyoText_1 = new JTextField();
		anyoText_1.setToolTipText("Introduce sólo números");
		anyoText_1.setColumns(10);

		generoText_1 = new JTextField();
		generoText_1.setColumns(10);

		caratulaLabel = new JButton("caratulaLabel");
		caratulaLabel.setMnemonic('i');
		caratulaLabel.setToolTipText("Pulsa para seleccionar la imagen");
		caratulaLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("tocaste cambiar imagen");
				seleccionImagen();
			}
		});

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(
						groupLayout
								.createParallelGroup(
										Alignment.LEADING)
								.addGroup(groupLayout
										.createSequentialGroup().addGroup(groupLayout.createParallelGroup(
												Alignment.LEADING)
												.addGroup(groupLayout
														.createSequentialGroup().addGap(20).addGroup(groupLayout
																.createParallelGroup(Alignment.LEADING)
																.addComponent(lblTitulo, GroupLayout.PREFERRED_SIZE, 60,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(lblArtista, GroupLayout.PREFERRED_SIZE,
																		60, GroupLayout.PREFERRED_SIZE)
																.addComponent(lblAlbum, GroupLayout.PREFERRED_SIZE, 60,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(lblAo, GroupLayout.PREFERRED_SIZE, 60,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(lblGenero, GroupLayout.PREFERRED_SIZE, 60,
																		GroupLayout.PREFERRED_SIZE))
														.addGap(10)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(generoText_1, GroupLayout.DEFAULT_SIZE,
																		200, Short.MAX_VALUE)
																.addComponent(anyoText_1, GroupLayout.DEFAULT_SIZE, 200,
																		Short.MAX_VALUE)
																.addComponent(albumText_1, GroupLayout.DEFAULT_SIZE,
																		200, Short.MAX_VALUE)
																.addComponent(artistaText_1, GroupLayout.DEFAULT_SIZE,
																		200, Short.MAX_VALUE)
																.addComponent(tituloText, GroupLayout.DEFAULT_SIZE, 200,
																		Short.MAX_VALUE)))
												.addGroup(groupLayout.createSequentialGroup().addGap(80).addComponent(
														seleccionarButton, GroupLayout.PREFERRED_SIZE, 160,
														GroupLayout.PREFERRED_SIZE)))
										.addGap(30).addComponent(caratulaLabel, GroupLayout.PREFERRED_SIZE, 260,
												GroupLayout.PREFERRED_SIZE)
										.addGap(20)));
		groupLayout
				.setVerticalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(20).addGroup(groupLayout
										.createParallelGroup(
												Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblTitulo, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(tituloText, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblArtista, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(artistaText_1, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblAlbum, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(albumText_1, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblAo, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(anyoText_1, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(generoText_1, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblGenero, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE))
												.addGap(34).addComponent(seleccionarButton, GroupLayout.PREFERRED_SIZE,
														40, GroupLayout.PREFERRED_SIZE))
										.addComponent(caratulaLabel, GroupLayout.PREFERRED_SIZE, 260,
												GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		setLayout(groupLayout);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { tituloText, artistaText_1, albumText_1,
				anyoText_1, generoText_1, caratulaLabel, seleccionarButton }));

	}
}
