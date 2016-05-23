package canciones.swing;

import java.awt.BorderLayout;
//TODO meter campos adicionales? otra imagen?
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.sun.corba.se.pept.transport.ContactInfo;

public class EditaCancionPanel extends JPanel {
	private JTextField tituloText;
	private JTextField artistaText;
	private JTextField albumText;
	private JTextField anyoText;
	private JTextField generoText;
	
	private JButton caratulaLabel;
	
	private Cancion _cancion;
	private boolean _dirty;


	public Cancion getCancion() {
		return _cancion;
	}


	public void setCancion(Cancion _cancion) {
		this._cancion = _cancion;
		deCancionAPanel();
	}

	
	private void deCancionAPanel() {
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
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(20)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTitulo, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblArtista, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAlbum, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAo, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblGenero, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(generoText, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
								.addComponent(anyoText, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
								.addComponent(albumText, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
								.addComponent(artistaText, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
								.addComponent(tituloText, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(80)
							.addComponent(guardarButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
					.addComponent(caratulaLabel, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
					.addGap(20))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTitulo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(tituloText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblArtista, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(artistaText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAlbum, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(albumText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(anyoText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(generoText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblGenero, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addGap(34)
							.addComponent(guardarButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addComponent(caratulaLabel, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

		
	}
}
