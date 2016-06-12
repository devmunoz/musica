package canciones.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import canciones.jdbc.CancionesDAO;
import canciones.jdbc.ConexionBD;
import canciones.swing.Cancion;
import canciones.swing.EditaCancionPanel;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Dimension;

public class CancionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private EditaCancionPanel cancionPanel;
	private boolean aceptado = false;

	/**
	 * Launch the application.
	 * 
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		editaCancionConID(1);
	}

	public Cancion getCancion() {
		return cancionPanel.getCancion();
	}

	public void setCancion(Cancion cancion) {
		cancionPanel.setCancion(cancion);
	}

	/**
	 * Create the dialog.
	 */
	public CancionDialog() {
		initComponents();
	}

	private void initComponents() {

		setMinimumSize(new Dimension(610, 370));
		getContentPane().setMinimumSize(new Dimension(600, 300));
		setBounds(100, 100, 610, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			cancionPanel = new EditaCancionPanel();
			contentPanel.add(cancionPanel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Guardar");
				okButton.setMnemonic('g');
				okButton.setToolTipText("Pulsa para guardar los cambios");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						guardar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setMnemonic('c');
				cancelButton.setToolTipText("Pulsa para cancelar los cambios");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelar();
					}

				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}

	protected void cancelar() {
		System.out.println("Cancelado");
		aceptado = false;
		setVisible(false);
	}

	protected void guardar() {
		System.out.println("Guardado");
		cancionPanel.guardarCancion();
		aceptado = true;
		setVisible(false);
	}

	public static Cancion editaCancionConID(int id) throws SQLException {

		Connection connection = null;
		try {
			connection = ConexionBD.creaConexion();
			Cancion c = CancionesDAO.buscarPorID(connection, id);
			CancionDialog dialog = new CancionDialog();
			System.out.println("dialogo sin cancion");
			dialog.setCancion(c);
			dialog.setModal(true);
			dialog.setVisible(true);
			System.out.println("dialogo con cancion, aceptado aun no captado");
			boolean aceptado = dialog.isAceptado();
			System.out.println("El dialogo es aceptado: " + aceptado);
			if (aceptado) {
				System.out.println("Modificando");
				CancionesDAO.editaCancion(connection, c);
				System.out.println("Guardada en BD");
				return c;

			}
		} finally {
			connection.close();
		}
		return null;
	}

	public boolean isAceptado() {
		return aceptado;
	}

}
