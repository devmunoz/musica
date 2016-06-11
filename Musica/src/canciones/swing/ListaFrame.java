package canciones.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import canciones.jdbc.CancionesDAO;
import canciones.jdbc.ConexionBD;

import javax.swing.JList;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class ListaFrame extends JFrame {

	private JPanel contentPane;
	private JList<Cancion> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaFrame frame = new ListaFrame();
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
	public ListaFrame() {
		setMinimumSize(new Dimension(600, 650));

		initComponents();
		inicializaDatosDeLista();

	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 651);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel surPanel = new JPanel();
		contentPane.add(surPanel, BorderLayout.SOUTH);

		JButton addBtn = new JButton("Añadir cancion");
		addBtn.setMnemonic('a');
		addBtn.setToolTipText("Pulsa para añadir una nueva cancion a la base de datos");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCancion();
			}
		});
		surPanel.add(addBtn);

		JButton editBtn = new JButton("Editar Cancion");
		editBtn.setMnemonic('e');
		editBtn.setToolTipText("Pulsa para editar los datos de la cancion seleccionada");
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarCancion();
			}
		});
		surPanel.add(editBtn);

		JButton delBtn = new JButton("Borrar Cancion");
		delBtn.setMnemonic('b');
		delBtn.setToolTipText("Pulsa para borrar la cancion seleccionada");
		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borraCancion();
			}
		});
		surPanel.add(delBtn);

		JPanel nortePanel = new JPanel();
		nortePanel.setBackground(Color.GRAY);
		contentPane.add(nortePanel, BorderLayout.NORTH);

		JLabel ordenLabel = new JLabel("Selecciona cancion para editar o borrar, o añade una nueva");
		ordenLabel.setForeground(Color.WHITE);
		nortePanel.add(ordenLabel);
		ordenLabel.setFont(new Font("DejaVu Sans Light", Font.BOLD | Font.ITALIC, 14));
		ordenLabel.setBackground(Color.WHITE);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		list = new JList<Cancion>();
		scrollPane.setViewportView(list);
	}

	protected void editarCancion() {
		int i = list.getSelectedIndex();
		if (i == -1) {
			System.out.println("No hay nada seleccionado");
			return;
		}

		Cancion c = list.getSelectedValue();
		try {
			Cancion cancionEditada = CancionDialog.editaCancionConID(c.getId());
			if (cancionEditada == null) {
				return;
			}
			System.out.println("han cambiado la cancion, actualizo la lista");

			DefaultListModel<Cancion> model = (DefaultListModel<Cancion>) list.getModel();
			model.set(i, cancionEditada);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addCancion() {
		Cancion cancion = new Cancion();
		CancionDialog dialog = new CancionDialog();
		dialog.setCancion(cancion);
		dialog.setModal(true);
		dialog.setVisible(true);
		if (!dialog.isAceptado()) {
			System.out.println("No quiero crearlo");
			return;
		}

		Connection connection = null;

		try {

			connection = ConexionBD.creaConexion();
			CancionesDAO.addCancion(connection, cancion);

			DefaultListModel<Cancion> model = (DefaultListModel<Cancion>) list.getModel();
			model.addElement(cancion);

		} catch (Exception e) {
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

	private void borraCancion() {
		int i = list.getSelectedIndex();
		if (i == -1) {
			System.out.println("No hay nada seleccionado");
			return;
		}

		Cancion cancion = list.getSelectedValue();
		Connection connection = null;

		try {

			connection = ConexionBD.creaConexion();
			CancionesDAO.borraCancion(connection, cancion);

			System.out.println("han cambiado la cancion, actualizo la lista");

			DefaultListModel<Cancion> model = (DefaultListModel<Cancion>) list.getModel();
			model.remove(i);
		} catch (SQLException e) {
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

	private void inicializaDatosDeLista() {
		list.setCellRenderer(new RendererDeListaDeCanciones());
		Connection conexion = null;
		try {
			conexion = ConexionBD.creaConexion();
			List<Cancion> canciones = CancionesDAO.leeCancion(conexion);

			DefaultListModel<Cancion> model = new DefaultListModel<>();
			for (Cancion c : canciones) {
				model.addElement(c);
			}

			list.setModel(model);
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
}
