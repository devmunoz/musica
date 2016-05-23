package canciones.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class EditaCancionPanel extends JPanel {
	private JTextField tituloText;
	private JTextField artistaText;
	private JTextField albumText;
	private JTextField anyoText;
	private JTextField generoText;
	
	
	private JButton caratulaLabel;
	
	private Cancion _cancion;

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
		artistaText.setText(c.getArtista());
		albumText.setText(c.getAlbum());
		anyoText.setText(""+(c.getAnyo()));
		generoText.setText(c.getGenero());
		
		//añadir imagen
		{
			byte[] caratula = c.getCaratula();
			try {
				InputStream in = null;
				System.err.println(caratula);
				if (caratula == null) {
					in = getClass().getResourceAsStream("/canciones/swing/default.png");
				} else {
					in = new ByteArrayInputStream(caratula);
				}
				caratulaLabel.setIcon(new ImageIcon( ImageIO.read(in)));
				caratulaLabel.setText("");

			} catch (IOException e) {
				// DEJO LA IMAGEN VACIA O COMO ESTUVIERA
				caratulaLabel.setIcon(null);
				caratulaLabel.setText("Imagen incorrecta");
				e.printStackTrace();
			}
		}
	}


	public static void main(String[] args) {
		JFrame f = new JFrame();
		EditaCancionPanel p = new EditaCancionPanel();
		Cancion c = new Cancion();
		c.setTitulo("prueba");
		c.setArtista("artista");
		c.setAlbum("Album de prueba");
		p.setCancion(c);
		f.getContentPane().add(p, BorderLayout.CENTER);
		
		f.pack();
		f.setVisible(true);
	}
	
	public void guardarCancion() {
		System.out.println("He entrado a guardar");
		Cancion c = getCancion();
		System.err.println("Antes de cambiar la cancion:" + c);

		c.setTitulo(tituloText.getText());
		c.setArtista(artistaText.getText());
		c.setAlbum(albumText.getText());
		c.setAnyo(Integer.parseInt(anyoText.getText()));
		c.setGenero(generoText.getText());
		
		{
			ImageIcon icon = (ImageIcon) caratulaLabel.getIcon();
			BufferedImage i = (BufferedImage) icon.getImage();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				ImageIO.write(i, "png", out);
				out.flush();
				c.setCaratula( out.toByteArray() );
			} catch (IOException e) {
				// NO DEBERIA FALLAR PORQUE VA DE MEMORIA A MEMORIA,
				// DEJO LA TRAZA POR SI ACASO
				e.printStackTrace();
			}
			
		}

		//TODO controlar que no toque el archivo de audio
		

		System.err.println("Después de cambiar la cancion:" + c);

	}
	
	private void seleccionImagen() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(this);
		File f = chooser.getSelectedFile();
		if( f != null ){
			try {
				BufferedImage i = ImageIO.read(f);
				caratulaLabel.setIcon( new ImageIcon(i) );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * Create the panel.
	 */
	public EditaCancionPanel() {

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

		caratulaLabel= new JButton("caratulaLabel");

		JButton guardarButton = new JButton("Guardar cambios");
		guardarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("tocaste guardar");
				guardarCancion();
			}
		});

		artistaText = new JTextField();
		artistaText.setColumns(10);

		albumText = new JTextField();
		albumText.setColumns(10);

		anyoText = new JTextField();
		anyoText.setColumns(10);

		generoText = new JTextField();
		generoText.setColumns(10);

		caratulaLabel = new JButton("caratulaLabel");
		caratulaLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("tocaste cambiar imagen");
				seleccionImagen();
			}
		});

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
						groupLayout
								.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout
												.createSequentialGroup().addGap(20).addGroup(groupLayout
														.createParallelGroup(Alignment.LEADING)
														.addComponent(lblTitulo, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblArtista, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblAlbum, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblAo, GroupLayout.PREFERRED_SIZE,
																60, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblGenero, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE))
												.addGap(10)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(generoText, GroupLayout.PREFERRED_SIZE, 200,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(anyoText, GroupLayout.PREFERRED_SIZE, 200,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(albumText, GroupLayout.PREFERRED_SIZE, 200,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(artistaText, GroupLayout.PREFERRED_SIZE, 200,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(tituloText, GroupLayout.PREFERRED_SIZE, 200,
																GroupLayout.PREFERRED_SIZE)))
										.addGroup(groupLayout.createSequentialGroup().addGap(80).addComponent(
												guardarButton, GroupLayout.PREFERRED_SIZE, 160,
												GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
								.addComponent(caratulaLabel, GroupLayout.PREFERRED_SIZE, 260,
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
														.addComponent(artistaText, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblAlbum, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(albumText, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblAo, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(anyoText, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(generoText, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblGenero, GroupLayout.PREFERRED_SIZE, 30,
																GroupLayout.PREFERRED_SIZE))
												.addGap(34).addComponent(guardarButton, GroupLayout.PREFERRED_SIZE, 40,
														GroupLayout.PREFERRED_SIZE))
										.addComponent(caratulaLabel, GroupLayout.PREFERRED_SIZE, 260,
												GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		setLayout(groupLayout);

	}
}
