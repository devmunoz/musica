package canciones.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class InicioFrame extends JFrame {

	private JPanel contentPane;
	public static JButton listaButton;

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

		JComboBox agrupaCombo = new JComboBox();
		agrupaCombo.setEnabled(false);
		agrupaCombo.setModel(new DefaultComboBoxModel(new String[] { "Artista", "Album", "Anyo", "Genero" }));

		JLabel lblAgruparCanciones = new JLabel("Agrupar canciones");
		lblAgruparCanciones.setEnabled(false);

		listaButton = new JButton("Lista de canciones");
		listaButton.setEnabled(false);
		listaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciaLista();
			}
		});
		listaButton.setToolTipText("Muestra la lista de canciones de la Base de Datos");
		listaButton.setMnemonic('l');

		JButton btnAgrupar = new JButton("Agrupar");
		btnAgrupar.setEnabled(false);
		btnAgrupar.setToolTipText("Agrupa canciones según la opcion elegida");
		btnAgrupar.setMnemonic('g');
		GroupLayout gl_izquierdaPanel = new GroupLayout(izquierdaPanel);
		gl_izquierdaPanel.setHorizontalGroup(gl_izquierdaPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_izquierdaPanel.createSequentialGroup().addGroup(gl_izquierdaPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_izquierdaPanel.createSequentialGroup().addGap(37).addComponent(lblAgruparCanciones))
						.addGroup(gl_izquierdaPanel.createSequentialGroup().addGap(21)
								.addGroup(gl_izquierdaPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(listaButton).addGroup(gl_izquierdaPanel
												.createSequentialGroup()
												.addComponent(agrupaCombo, 0, 89, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnAgrupar,
														GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)))))
						.addContainerGap()));
		gl_izquierdaPanel.setVerticalGroup(gl_izquierdaPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_izquierdaPanel.createSequentialGroup().addGap(75).addComponent(listaButton)
						.addGap(77).addComponent(lblAgruparCanciones).addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_izquierdaPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(agrupaCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAgrupar))
						.addContainerGap(61, Short.MAX_VALUE)));
		izquierdaPanel.setLayout(gl_izquierdaPanel);

		JPanel derechaPanel = new JPanel();
		getContentPane().add(derechaPanel, BorderLayout.EAST);

		JPanel centroPanel = new JPanel();
		getContentPane().add(centroPanel, BorderLayout.CENTER);

		JPanel estadoPanel = new EstadoPanel();
		centroPanel.add(estadoPanel);

	}

	protected void iniciaLista() {
		ListaFrame lf = new ListaFrame();
		lf.setVisible(true);
		// this.setVisible(false);
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
