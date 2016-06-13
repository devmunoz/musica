package canciones.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import canciones.jdbc.CancionesDAO;
import canciones.jdbc.ConexionBD;

public class InicioFrame extends JFrame {

	private JPanel contentPane;
	public static JButton listaButton;
	public static JComboBox agrupaCombo;
	public static JButton agrupaBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioFrame frame = new InicioFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InicioFrame() {
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 699, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		// JPanel nortePanel = new JPanel();
		// getContentPane().add(nortePanel, BorderLayout.NORTH);

		JPanel surPanel = new JPanel();
		getContentPane().add(surPanel, BorderLayout.SOUTH);

		JButton salirButon = new JButton("Salir de la aplicación");
		salirButon.setMnemonic('S');
		salirButon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		JButton btnCambiarConexinA = new JButton("Cambiar conexión a BD");
		btnCambiarConexinA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarBD();
			}
		});
		surPanel.add(btnCambiarConexinA);
		surPanel.add(salirButon);

		JPanel izquierdaPanel = new JPanel();
		izquierdaPanel.setDoubleBuffered(false);
		getContentPane().add(izquierdaPanel, BorderLayout.WEST);

		agrupaCombo = new JComboBox();
		agrupaCombo.setModel(new DefaultComboBoxModel(new String[] { "Artista", "Album", "Año", "Genero" }));

		JLabel lblAgruparCanciones = new JLabel("Agrupar canciones");

		listaButton = new JButton("Lista de canciones");
		listaButton.setEnabled(false);
		listaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciaLista();
			}
		});
		listaButton.setToolTipText("Muestra la lista de canciones de la Base de Datos");
		listaButton.setMnemonic('l');

		agrupaBtn = new JButton("Agrupar");
		agrupaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				agrupaCanciones(agrupaCombo.getSelectedIndex());
			}
		});
		agrupaBtn.setEnabled(false);
		agrupaBtn.setToolTipText("Agrupa canciones según la opcion elegida");
		agrupaBtn.setMnemonic('g');
		GroupLayout gl_izquierdaPanel = new GroupLayout(izquierdaPanel);
		gl_izquierdaPanel.setHorizontalGroup(gl_izquierdaPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_izquierdaPanel.createSequentialGroup().addGroup(gl_izquierdaPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_izquierdaPanel.createSequentialGroup().addGap(37).addComponent(lblAgruparCanciones))
						.addGroup(gl_izquierdaPanel.createSequentialGroup()
								.addGap(21).addGroup(gl_izquierdaPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(listaButton).addGroup(gl_izquierdaPanel.createSequentialGroup()
												.addComponent(agrupaCombo, 0, 89, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(agrupaBtn,
														GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)))))
						.addContainerGap()));
		gl_izquierdaPanel.setVerticalGroup(gl_izquierdaPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_izquierdaPanel.createSequentialGroup().addGap(75).addComponent(listaButton).addGap(77)
						.addComponent(lblAgruparCanciones).addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_izquierdaPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(agrupaCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(agrupaBtn))
						.addContainerGap(61, Short.MAX_VALUE)));
		izquierdaPanel.setLayout(gl_izquierdaPanel);

		JPanel derechaPanel = new JPanel();
		getContentPane().add(derechaPanel, BorderLayout.EAST);

		JPanel centroPanel = new JPanel();
		getContentPane().add(centroPanel, BorderLayout.CENTER);

		JPanel estadoPanel = new EstadoPanel();
		centroPanel.add(estadoPanel);

	}

	protected void agrupaCanciones(int i) {

		ArrayList<String> elementosParaComboBox = new ArrayList<String>();
		List<Cancion> canciones;
		Connection conexion = null;
		try {
			conexion = ConexionBD.creaConexion();
			canciones = CancionesDAO.leeCancion(conexion);

			for (int b = 0; b < canciones.size(); b++) {

				String s = queQuieroDeCancion(canciones.get(b), queEs(i));

				// para añadir primera cancion
				if (elementosParaComboBox.size() == 0) {
					elementosParaComboBox.add(s);
				}

				// evitar añadir exponencialmente
				int elementos = elementosParaComboBox.size();
				boolean igualdad = false;

				for (int d = 0; d < elementos; d++) {
					if (s.equals(elementosParaComboBox.get(d))) {
						// si es igual a los que ya hay no lo añado
						igualdad = true;
						break;
					}
				}
				// si no es igual lo añado
				if (!igualdad) {
					elementosParaComboBox.add(queQuieroDeCancion(canciones.get(b), queEs(i)));
				}
			}

			String[] elementosCombo = arrayListToArrayString(elementosParaComboBox);
			// lo mando al combobox
			CancionesAgrupadas ca = new CancionesAgrupadas();
			ca.setCampo(queEs(i));
			ca.setVisible(true);
			ca.setOpciones(elementosCombo);
			;
			ca.comboBox.setModel(new DefaultComboBoxModel(elementosCombo));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String[] arrayListToArrayString(ArrayList<String> compList) {
		String[] s = new String[compList.size()];
		for (int i = 0; i < s.length; i++) {
			s[i] = compList.get(i);
		}
		return s;
	}

	public static String queQuieroDeCancion(Cancion c, String s) {
		switch (s) {
		case "artista":
			s = c.getArtista();
			break;
		case "album":
			s = c.getAlbum();
			break;
		case "anyo":
			s = "" + c.getAnyo();
			break;
		case "genero":
			s = c.getGenero();
			break;
		}

		return s;
	}

	private String queEs(int i) {
		String devuelve = "";
		switch (i) {
		case 0:
			devuelve = "artista";
			break;
		case 1:
			devuelve = "album";
			break;
		case 2:
			devuelve = "anyo";
			break;
		case 3:
			devuelve = "genero";
			break;
		}
		return devuelve;
	}

	protected void iniciaLista() {
		ListaFrame lf = new ListaFrame();
		lf.setVisible(true);
	}

	protected void cambiarBD() {
		SeleccionBDDialog dialog = new SeleccionBDDialog();
		SeleccionBD sb = new SeleccionBD();

		dialog.getContentPane().add(sb);
		dialog.setModal(true);
		dialog.setVisible(true);
		if (!dialog.isFinalizado()) {
			return;
		}
	}
}
