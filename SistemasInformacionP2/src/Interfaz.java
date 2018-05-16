import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.Year;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;

public class Interfaz {

	private JFrame frame;
	private JTextField JTextFieldDonde;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz window = new Interfaz();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interfaz() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 938, 601);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btnNewButton = new JButton("Seleccionar Archivo");
		btnNewButton.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		btnNewButton.setBounds(55, 273, 223, 64);

		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnNewButton);

		JTextField JTextFieldCargo = new JTextField("");
		JTextFieldCargo.setFont(new Font("Courier", Font.ITALIC, 12));
		JTextFieldCargo.setBounds(303, 290, 536, 30);
		frame.getContentPane().add(JTextFieldCargo);

		JSpinner mes = new JSpinner(new SpinnerNumberModel(00, 0, 12, 1));
		mes.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 13));
		mes.setBounds(420, 166, 139, 36);
		frame.getContentPane().add(mes);

		JSpinner ano = new JSpinner(new SpinnerNumberModel(1970, 1970, 2099, 1));
		ano.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 13));
		ano.setBounds(598, 166, 139, 36);
		frame.getContentPane().add(ano);

		JLabel lblNewLabel = new JLabel("Seleccione una Fecha");
		lblNewLabel.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		lblNewLabel.setBounds(149, 164, 209, 36);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Generador De  Nominas");
		lblNewLabel_1.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 47));
		lblNewLabel_1.setBounds(149, 28, 664, 125);
		frame.getContentPane().add(lblNewLabel_1);

		JTextFieldDonde = new JTextField();
		JTextFieldDonde.setBounds(303, 421, 536, 30);
		frame.getContentPane().add(JTextFieldDonde);
		JTextFieldDonde.setColumns(10);

		JButton btnDonde = new JButton();
		btnDonde.setLayout(new BorderLayout());
		JLabel label1 = new JLabel("Donde generar", SwingConstants.CENTER);
		label1.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		JLabel label2 = new JLabel("los archivos", SwingConstants.CENTER);
		label2.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 17));
		btnDonde.add(BorderLayout.NORTH,label1);
		btnDonde.add(BorderLayout.SOUTH,label2);
		btnDonde.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 15));
		btnDonde.setBounds(55, 410, 223, 54);
		btnDonde.setHorizontalAlignment(SwingConstants.LEFT);
		frame.getContentPane().add(btnDonde);
		
		JButton btnGenerar = new JButton("Generar");
		btnGenerar.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 36));
		btnGenerar.setBounds(392, 487, 234, 64);
		frame.getContentPane().add(btnGenerar);


		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("directorio","xlsx");
				chooser.setFileFilter(filter);

				int returnVal = chooser.showOpenDialog(chooser);
				if(returnVal==JFileChooser.APPROVE_OPTION) {
					JTextFieldCargo.setText(chooser.getSelectedFile().getPath());
				}
			}
		});
		
		btnDonde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int returnVal = chooser.showOpenDialog(chooser);
				if(returnVal==JFileChooser.APPROVE_OPTION) {
					JTextFieldDonde.setText(chooser.getSelectedFile().getPath());
				}
			}
		});
		
		btnGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("deprecation")
				Date fecha = new Date((int)ano.getValue(), (int)mes.getValue()-1, 1);
				Main m = new Main(JTextFieldCargo.getText(), JTextFieldDonde.getText(), fecha);
				m.calcular();
				
			}
		});
	}
}
