package canciones.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

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
	private JButton caratulaLabel_1;
	
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
		artistaText_1.setText(c.getArtista());
		albumText_1.setText(c.getAlbum());
		anyoText_1.setText(""+(c.getAnyo()));
		generoText_1.setText(c.getGenero());
		
		//añadir imagen
		{
			byte[] caratula = c.getCaratula();
			try {
				InputStream in = null;
				System.err.println(caratula);
				
				if ( caratula == null ) {
					in = getClass().getResourceAsStream("/canciones/swing/default.png");
				} else {
					in = new ByteArrayInputStream(caratula);
				}
				caratulaLabel_1.setIcon(new ImageIcon( ImageIO.read(in)));
				caratulaLabel_1.setText("");

			} catch (IOException e) {
				// DEJO LA IMAGEN VACIA O COMO ESTUVIERA
				caratulaLabel_1.setIcon(null);
				caratulaLabel_1.setText("Imagen incorrecta");
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
		System.out.println("He tocado en guardar");
		Cancion c = getCancion();
		System.out.println("Antes de cambiar la cancion:" + c);

		c.setTitulo(tituloText.getText());
		c.setArtista(artistaText_1.getText());
		c.setAlbum(albumText_1.getText());
		c.setAnyo(Integer.parseInt(anyoText_1.getText()));
		c.setGenero(generoText_1.getText());
		
		{
			ImageIcon icon = (ImageIcon) caratulaLabel_1.getIcon();
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

		System.out.println("Después de cambiar la cancion:" + c);

	}
	
	private void seleccionImagen() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(this);
		File f = chooser.getSelectedFile();
		if( f != null ){
			try {
				BufferedImage i = ImageIO.read(f);
				caratulaLabel_1.setIcon( new ImageIcon(i) );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void seleccionArchivo() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(this);
		File f = chooser.getSelectedFile();
		if( f != null ){
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

		caratulaLabel= new JButton("caratulaLabel");

		JButton guardarButton = new JButton("Seleccion archivo");
		guardarButton.setToolTipText("Pulsa para seleccionar el archivo de audio");
		guardarButton.addActionListener(new ActionListener() {
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

		caratulaLabel_1 = new JButton("caratulaLabel");
		caratulaLabel_1.setToolTipText("Pulsa para seleccionar la imagen");
		caratulaLabel_1.addActionListener(new ActionListener() {
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
														.addComponent(generoText_1, GroupLayout.PREFERRED_SIZE, 200,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(anyoText_1, GroupLayout.PREFERRED_SIZE, 200,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(albumText_1, GroupLayout.PREFERRED_SIZE, 200,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(artistaText_1, GroupLayout.PREFERRED_SIZE, 200,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(tituloText, GroupLayout.PREFERRED_SIZE, 200,
																GroupLayout.PREFERRED_SIZE)))
										.addGroup(groupLayout.createSequentialGroup().addGap(80).addComponent(
												guardarButton, GroupLayout.PREFERRED_SIZE, 160,
												GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
								.addComponent(caratulaLabel_1, GroupLayout.PREFERRED_SIZE, 260,
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
												.addGap(34).addComponent(guardarButton, GroupLayout.PREFERRED_SIZE, 40,
														GroupLayout.PREFERRED_SIZE))
										.addComponent(caratulaLabel_1, GroupLayout.PREFERRED_SIZE, 260,
												GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		setLayout(groupLayout);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tituloText, artistaText_1, albumText_1, anyoText_1, generoText_1, caratulaLabel_1, guardarButton}));

	}
}
