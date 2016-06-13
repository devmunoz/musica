package canciones.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import canciones.jdbc.CancionesDAO;
import canciones.jdbc.ConexionBD;

public class CancionesAgrupadas extends JDialog {
	private JButton salirButton;
	private List<Cancion> canciones;
	private JPanel topPanel;
	private JLabel eligeLabel;
	private JList list;
	public static JComboBox comboBox;
	public static String[] opciones;
	public static String campo;

	public static void setCampo(String campo) {
		CancionesAgrupadas.campo = campo;
	}

	public static String getCampo() {
		return campo;
	}

	public static String[] getOpciones() {
		return opciones;
	}

	public static void setOpciones(String[] opciones) {
		CancionesAgrupadas.opciones = opciones;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CancionesAgrupadas dialog = new CancionesAgrupadas();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CancionesAgrupadas() {
		initComponents();
	}

	private void initComponents() {
		setBounds(100, 100, 500, 600);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				salirButton = new JButton("Salir");
				salirButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				salirButton.setMnemonic('s');
				salirButton.setActionCommand("Cancel");
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane
					.setHorizontalGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_buttonPane
									.createSequentialGroup().addGap(174).addComponent(salirButton,
											GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
									.addContainerGap(174, Short.MAX_VALUE)));
			gl_buttonPane
					.setVerticalGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
							gl_buttonPane.createSequentialGroup()
									.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(salirButton)));
			buttonPane.setLayout(gl_buttonPane);
		}

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		list = new JList();
		scrollPane.setViewportView(list);
		{
			topPanel = new JPanel();
			topPanel.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
			getContentPane().add(topPanel, BorderLayout.NORTH);
			eligeLabel = new JLabel("Elige el elemento para agrupar:");

			comboBox = new JComboBox();

			JButton btnAgrupar = new JButton("Agrupar!");
			btnAgrupar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					agrupa();
				}
			});
			GroupLayout gl_topPanel = new GroupLayout(topPanel);
			gl_topPanel.setHorizontalGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_topPanel.createSequentialGroup().addGap(30).addComponent(eligeLabel).addGap(18)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addContainerGap(191, Short.MAX_VALUE))
					.addGroup(Alignment.TRAILING, gl_topPanel.createSequentialGroup()
							.addContainerGap(171, Short.MAX_VALUE).addComponent(btnAgrupar).addGap(229)));
			gl_topPanel.setVerticalGroup(gl_topPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_topPanel
					.createSequentialGroup().addGap(17)
					.addGroup(gl_topPanel.createParallelGroup(Alignment.BASELINE).addComponent(eligeLabel).addComponent(
							comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnAgrupar)
					.addContainerGap(48, Short.MAX_VALUE)));
			topPanel.setLayout(gl_topPanel);
		}
	}

	protected void agrupa() {
		List<Cancion> cancionesSinFiltrar;
		String eleccion = (String) comboBox.getSelectedItem();
		ArrayList<Cancion> cancionesFiltradas = new ArrayList<Cancion>();

		Connection connection = null;
		try {
			connection=ConexionBD.creaConexion();
			cancionesSinFiltrar = CancionesDAO.leeCancion(connection);

			for (Cancion cancion : cancionesSinFiltrar) {
				if (queCojoDeCancion(cancion).equals(eleccion)) {
					cancionesFiltradas.add(cancion);
				}
			}


			agregaLista(cancionesFiltradas);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void agregaLista(ArrayList<Cancion> canciones) {
		list.setCellRenderer(new RendererDeListaDeCanciones());
		Connection conexion = null;
		
			DefaultListModel<Cancion> model = new DefaultListModel<>();
			for (Cancion c : canciones) {
				model.addElement(c);
			}
			list.setModel(model);
			
	}

	private String queCojoDeCancion(Cancion cancion) {
		String campo = getCampo();
		return InicioFrame.queQuieroDeCancion(cancion, campo);
	}

	private class RendererDeListaDeCanciones implements ListCellRenderer<Cancion> {

		private ResumenCancionPanel cp = new ResumenCancionPanel();

		@Override
		public Component getListCellRendererComponent(JList<? extends Cancion> list, Cancion value, int index,
				boolean isSelected, boolean cellHasFocus) {
			cp.setCancion(value);
			if (isSelected) {
				cp.setBackground(Color.LIGHT_GRAY);
			}
			cp.setOpaque(isSelected);
			if (cellHasFocus) {
				cp.setBorder(BorderFactory.createEtchedBorder());
			} else {
				cp.setBorder(BorderFactory.createEmptyBorder());
			}
			return cp;
		}
	}
}
