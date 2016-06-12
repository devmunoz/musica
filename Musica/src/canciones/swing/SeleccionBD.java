package canciones.swing;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;

import canciones.jdbc.ConexionBD;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.Font;
import java.awt.Dimension;

public class SeleccionBD extends JPanel {

	private static JTextField urlText;
	private static JTextField tablaText;
	private static JTextField usuarioText;
	private static JPasswordField passText;
	private static String _url;
	private static String _pass;
	private static String _usuario;
	
	/**
	 * Create the panel.
	 */
	public SeleccionBD() {
		initcomponents();
	}

	private void initcomponents() {

		setMinimumSize(new Dimension(514, 211));

		urlText = new JTextField();
		urlText.setToolTipText("Introduce direccion y puerto si es necesario, por ejemplo \"192.168.1.25:8080\"");
		urlText.setText(ConexionBD.getUrlUrl("url"));
		urlText.setColumns(10);

		tablaText = new JTextField();
		tablaText.setText(ConexionBD.getUrlUrl("tabla"));
		tablaText.setColumns(10);

		JLabel urlLabel = new JLabel("URL de la Base de Datos");

		JLabel tablaLabel = new JLabel("Nombre de la base de datos");

		JLabel usuarioLabel = new JLabel("Nombre de usuario");

		JLabel passLabel = new JLabel("Contraseña");

		usuarioText = new JTextField();
		usuarioText.setText(ConexionBD.getUser());
		usuarioText.setColumns(10);

		passText = new JPasswordField();
		passText.setText(ConexionBD.getPassword());

		JLabel lblNewLabel = new JLabel("Introduce los datos de la Base de datos para establecer conexión.");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 13));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(usuarioText, Alignment.LEADING)
									.addComponent(urlText, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
								.addComponent(usuarioLabel)
								.addComponent(urlLabel, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tablaLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(tablaText)
								.addComponent(passLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(passText))
							.addGap(12))
						.addComponent(lblNewLabel))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(urlLabel)
						.addComponent(tablaLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(urlText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tablaText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(usuarioLabel)
						.addComponent(passLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(usuarioText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(passText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}

	
	protected static boolean pruebaConexion() {

		ConexionBD.setUrl("jdbc:mysql://" + urlText.getText() + "/" + tablaText.getText());
		ConexionBD.setUser(usuarioText.getText());
		char[] contrasenya = passText.getPassword();
		String pass = "";
		for (char c : contrasenya) {
			pass = pass + c;
		}
		ConexionBD.setPassword(pass);

		Connection conection = null;
		try {
			conection = ConexionBD.creaConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Algo ha fallado en la conexion: " + urlText.getText() + "/" + tablaText.getText()
					+ " con el usuario: " + usuarioText.getText());
			e.printStackTrace();
			ConexionBD.conexionDefecto();

			return false;
		} finally {
			if (conection != null) {
				try {
					conection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		_url = ConexionBD.getUrl();
		_usuario = ConexionBD.getUser();
		_pass = ConexionBD.getPassword();
		
		return true;
	}

	
	protected void cancelar() {
		if (!pruebaConexion()) {
			ConexionBD.conexionDefecto();
		}
		
	}

	protected void eligeConexion() {
		ConexionBD.setUrl(_url);
		ConexionBD.setUser(_usuario);
		ConexionBD.setPassword(_pass);
	}

	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		SeleccionBD s = new SeleccionBD();
		f.getContentPane().add(s);
		f.setVisible(true);
		f.pack();
	}
}
