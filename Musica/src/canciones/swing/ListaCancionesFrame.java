package canciones.swing;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import canciones.jdbc.ConexionBD;
import canciones.jdbc.CancionDialog;
import canciones.jdbc.CancionesDAO;

public class ListaCancionesFrame extends JFrame {

	private JPanel contentPane;
	private JList<Cancion> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaCancionesFrame frame = new ListaCancionesFrame();
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
	public ListaCancionesFrame() {
		initComponents();
		inicializaDatosDeLista();
	}
	
	private void initComponents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		list = new JList<Cancion>();
		contentPane.add( new JScrollPane(list), BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[]{0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		JButton addContactoButton = new JButton("Añadir cancion");
		addContactoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCancion();
			}

		});
		GridBagConstraints gbc_addContactoButton = new GridBagConstraints();
		gbc_addContactoButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_addContactoButton.anchor = GridBagConstraints.NORTH;
		gbc_addContactoButton.insets = new Insets(0, 0, 5, 5);
		gbc_addContactoButton.gridx = 0;
		gbc_addContactoButton.gridy = 0;
		panel.add(addContactoButton, gbc_addContactoButton);
		
		JButton deleteContactoButton = new JButton("Borrar cancion");
		deleteContactoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borraCancion();
			}
		});
		GridBagConstraints gbc_deleteContactoButton = new GridBagConstraints();
		gbc_deleteContactoButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_deleteContactoButton.anchor = GridBagConstraints.NORTH;
		gbc_deleteContactoButton.insets = new Insets(0, 0, 5, 5);
		gbc_deleteContactoButton.gridx = 0;
		gbc_deleteContactoButton.gridy = 1;
		panel.add(deleteContactoButton, gbc_deleteContactoButton);
		
		JButton editContactoButton = new JButton("Editar cancion");
		editContactoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarContactoSeleccionado();
			}
		});
		GridBagConstraints gbc_editContactoButton = new GridBagConstraints();
		gbc_editContactoButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_editContactoButton.anchor = GridBagConstraints.NORTH;
		gbc_editContactoButton.insets = new Insets(0, 0, 5, 5);
		gbc_editContactoButton.gridx = 0;
		gbc_editContactoButton.gridy = 2;
		panel.add(editContactoButton, gbc_editContactoButton);
	}

	protected void editarContactoSeleccionado() {
		int i = list.getSelectedIndex();
		if( i == -1 ){
			System.out.println("No hay nada seleccionado");
			return;
		}
		
		Cancion c = list.getSelectedValue();
		try {
			Cancion contactoCambiado = CancionDialog.editaCancionConID(c.getId());
			if( contactoCambiado == null ){
				return;
			}
			System.out.println("han cambiado el contacto, actualizo la lista");
			
			DefaultListModel<Cancion> model = (DefaultListModel<Cancion>) list.getModel();
			model.set(i, contactoCambiado);
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
			if( !dialog.isAceptado() ){
				System.out.println("No quiero crearlo");
				return;
			}

			Connection connection = null;

			try {

				connection = ConexionBD.creaConexion();
				CancionesDAO.addCancion(connection, cancion);

				DefaultListModel<Cancion> model = (DefaultListModel<Cancion>) list.getModel();
				model.addElement(cancion);

			}
			catch( Exception e ){
				e.printStackTrace();
			}
			finally{
				if( connection != null ){
					try{
						connection.close();
					}
					catch( SQLException e ){
						e.printStackTrace();
					}
				}
			}
		

	}
	

	private void borraCancion() {
		int i = list.getSelectedIndex();
		if( i == -1 ){
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
		}	
		finally{
			if( connection != null ){
				try{
					connection.close();
				}
				catch( SQLException e ){
					e.printStackTrace();
				}
			}
		}
	}

	
	private class RendererDeListaDeContactos extends DefaultListCellRenderer{
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,boolean cellHasFocus) {
			JLabel ret = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Cancion c = (Cancion) value;
			ret.setText( c.getId()+" - " + c.getTitulo() + " " + c.getArtista() );
			return ret;
		}
		
	}
	
	private void inicializaDatosDeLista(){
		list.setCellRenderer( new RendererDeListaDeContactos() );
		Connection conexion = null;
		try {
			conexion = ConexionBD.creaConexion();
			List<Cancion> canciones = CancionesDAO.leeCancion(conexion);
			
			DefaultListModel<Cancion> model = new DefaultListModel<>();
			for( Cancion c: canciones ){
				model.addElement(c);
			}
			
			list.setModel(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if( conexion != null ){
				try {
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}