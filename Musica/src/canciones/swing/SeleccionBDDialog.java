package canciones.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SeleccionBDDialog extends JDialog {

	private JPanel contentPanel = new JPanel();
	private SeleccionBD seleccionPanel;
	private boolean finalizado = false;
	private JButton eligeBtn;

	/**
	 * Create the dialog.
	 */
	public SeleccionBDDialog() {
		initComponents();
	}

	private void initComponents() {
		setMinimumSize(new Dimension(520, 240));
		setMaximumSize(new Dimension(520, 240));
		setBounds(100, 100, 520, 240);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			seleccionPanel = new SeleccionBD();
			contentPanel.add(seleccionPanel);

		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton pruebaBtn = new JButton("Probar conexión");
				pruebaBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						probar();
					}
				});
				pruebaBtn.setMnemonic('P');
				pruebaBtn.setActionCommand("OK");
				buttonPane.add(pruebaBtn);
				getRootPane().setDefaultButton(pruebaBtn);
			}
			{
				eligeBtn = new JButton("Elegir conexión");
				eligeBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						elegir();
					}
				});
				eligeBtn.setEnabled(false);
				eligeBtn.setMnemonic('e');
				eligeBtn.setActionCommand("Cancel");
				buttonPane.add(eligeBtn);
			}
			{
				JButton btnCancelarYSalir = new JButton("Cancelar y salir");
				btnCancelarYSalir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						salir();
					}
				});
				btnCancelarYSalir.setMnemonic('c');
				buttonPane.add(btnCancelarYSalir);
			}
		}
	}

	protected void setFinalizado(boolean b) {
		this.finalizado = b;
	}

	protected boolean isFinalizado() {
		return this.finalizado;
	}

	protected void salir() {
		seleccionPanel.cancelar();
		finalizado = false;
		setVisible(false);
	}

	protected void elegir() {
		seleccionPanel.eligeConexion();
		finalizado = false;
		setVisible(false);
	}

	protected void probar() {
		eligeBtn.setEnabled(SeleccionBD.pruebaConexion());
	}
}
