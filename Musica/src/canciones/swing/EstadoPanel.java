package canciones.swing;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

import canciones.jdbc.CancionesDAO;
import canciones.jdbc.ConexionBD;

import java.awt.Color;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class EstadoPanel extends JPanel {

	private JLabel numCancionesLabel;
	private JLabel urlLabel;
	private JLabel conexionLabel;
	private JLabel usuarioLabel;
	private JButton vaciaBtn;
	private JButton creaEjemploBtn;

	/**
	 * Create the panel.
	 */
	public EstadoPanel() {
		initComponentes();
		actualizaDatos();
	}

	private void actualizaDatos() {

		if (compruebaConexion()) {
			conexionLabel.setText("Conectada");
			conexionLabel.setForeground(Color.GREEN);
			urlLabel.setText(ConexionBD.getUrlUrl("todo"));
			usuarioLabel.setText(ConexionBD.getUser());
			numCancionesLabel.setText("" + cuentaCanciones());
			vaciaBtn.setEnabled(true);
			creaEjemploBtn.setEnabled(true);
			InicioFrame.listaButton.setEnabled(true);
			
		} else {
			conexionLabel.setText("No conectada");
			conexionLabel.setForeground(Color.RED);
			urlLabel.setText("No conectado");
			usuarioLabel.setText("No conectado");
			numCancionesLabel.setText("0");
			vaciaBtn.setEnabled(false);
			creaEjemploBtn.setEnabled(false);
			InicioFrame.listaButton.setEnabled(false);
		}

	}

	private int cuentaCanciones() {
		Connection c = null;
		try {
			c = ConexionBD.creaConexion();
			List<Cancion> canciones = CancionesDAO.leeCancion(c);
			return canciones.size();
		} catch (SQLException e) {
		} finally {
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
					System.err.println("Error al cerrar la conexion: ");
					e.printStackTrace();
				}
			}
		}

		return 0;
	}

	private boolean compruebaConexion() {
		Connection c = null;
		try {
			c = ConexionBD.creaConexion();
		} catch (SQLException e) {
			System.err.println("Conexion con la Base de Datos fallida");
			System.err.println("Añade los parametros correctos para establecer conexión");
			return false;
		} finally {
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
					System.err.println("Error al cerrar la conexion: ");
					e.printStackTrace();
				}
			}
		}

		return true;

	}

	private void initComponentes() {

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new LineBorder(new Color(128, 128, 128), 1, true));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));

		JPanel panel_2 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 417,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(panel_1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));
		groupLayout
				.setVerticalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap()
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 136,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
										.addGap(5).addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 43,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		JButton actualizarBtn = new JButton("Actualizar");
		actualizarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizaDatos();
			}
		});
		actualizarBtn.setMnemonic('a');
		actualizarBtn.setToolTipText("Actualiza el estado de la BD");

		vaciaBtn = new JButton("Vaciar BD");
		vaciaBtn.setEnabled(false);
		vaciaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					vaciarBD();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		vaciaBtn.setToolTipText("Deja la Base de Datos vacía, sin ningún registro (no borra las tablas)");
		vaciaBtn.setMnemonic('v');

		creaEjemploBtn = new JButton("Crear BD ejemplo");
		creaEjemploBtn.setEnabled(false);
		creaEjemploBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					creaBDEjemplo();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		creaEjemploBtn.setToolTipText("Construye una Base de Datos con canciones a modo de ejemplo");
		creaEjemploBtn.setMnemonic('c');
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addGap(23).addComponent(actualizarBtn)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(vaciaBtn)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(creaEjemploBtn)
						.addContainerGap(32, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE).addComponent(vaciaBtn)
								.addComponent(creaEjemploBtn).addComponent(actualizarBtn))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		JLabel lblNumeroDeCanciones = new JLabel("Numero de canciones en la Base de Datos:");

		numCancionesLabel = new JLabel("000");
		numCancionesLabel.setForeground(Color.BLACK);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup().addContainerGap()
								.addComponent(lblNumeroDeCanciones).addGap(18)
								.addComponent(numCancionesLabel, GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(38, Short.MAX_VALUE)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(lblNumeroDeCanciones)
						.addComponent(numCancionesLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(51, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);

		JLabel lblEstadoDeLa = new JLabel("Estado de la Base de Datos");
		lblEstadoDeLa.setForeground(Color.BLACK);
		lblEstadoDeLa.setHorizontalAlignment(SwingConstants.CENTER);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);

		JLabel lblNombreDeLa = new JLabel("URL de la Base de Datos:");

		urlLabel = new JLabel("nombreBaseDatos");
		urlLabel.setForeground(Color.BLACK);

		JLabel lblEstadoDeLa_1 = new JLabel("Estado de la conexión:");

		conexionLabel = new JLabel("No conectado");
		conexionLabel.setForeground(Color.RED);

		JLabel lblNombreDeUsuario = new JLabel("Nombre de usuario:");

		usuarioLabel = new JLabel("duniel");
		usuarioLabel.setForeground(Color.BLACK);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, GroupLayout.PREFERRED_SIZE, 598, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(lblNombreDeLa)
								.addComponent(lblEstadoDeLa_1).addComponent(lblNombreDeUsuario))
						.addGap(37)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(usuarioLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(urlLabel, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
								.addComponent(conexionLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addGap(164))
				.addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblEstadoDeLa, GroupLayout.PREFERRED_SIZE, 415, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(183, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel.createSequentialGroup().addComponent(lblEstadoDeLa)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2,
												GroupLayout.PREFERRED_SIZE)
										.addGap(15)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblNombreDeLa).addComponent(urlLabel))
										.addGap(18)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblEstadoDeLa_1).addComponent(conexionLabel))
										.addGap(18)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblNombreDeUsuario).addComponent(usuarioLabel))
										.addContainerGap(15, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}

	protected void creaBDEjemplo() throws SQLException, IOException {
		vaciarBD();
		Connection c = null;
		try {
			c = ConexionBD.creaConexion();
			ListaCancionesDefecto lc =new ListaCancionesDefecto();
			lc.addCancionesDefecto();
			for (int i = 0; i < lc.getNumCanciones(); i++) {
				CancionesDAO.addCancion(c, lc.getCanciones().get(i));
				System.out.println("Cancion "+(i+1)+" añadida");
			}
			
			
		} catch (SQLException e) {
			// TODO: handle exception
		}
		actualizaDatos();
	}

	protected void vaciarBD() throws SQLException {
		String sql = "DROP TABLE canciones";

		String sql2 = "CREATE TABLE canciones " + "(id int(5) NOT NULL AUTO_INCREMENT, "
				+ "titulo varchar(50) DEFAULT NULL, " + "artista varchar(50) DEFAULT NULL, "
				+ "album varchar(50) DEFAULT NULL, " + "anyo int(4) DEFAULT NULL, "
				+ "genero varchar(50) DEFAULT NULL, " + "caratula longblob DEFAULT NULL, "
				+ "archivo longblob DEFAULT NULL, " + "PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=latin1";
		Connection connection = null;
		connection = ConexionBD.creaConexion();
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.executeUpdate();

		stmt = connection.prepareStatement(sql2);
		stmt.executeUpdate();

		stmt.close();
		System.out.println("Tabla vaciada");
		actualizaDatos();
	}
}
