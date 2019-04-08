import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class AdventureGame {

	private JFrame frame;
	private JTextField textFieldAction;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdventureGame window = new AdventureGame();
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
	public AdventureGame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 766, 413);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnFile.add(mntmSettings);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblAction = new JLabel("Action");
		lblAction.setBounds(10, 11, 46, 14);
		panel.add(lblAction);
		
		textFieldAction = new JTextField();
		textFieldAction.setBounds(10, 30, 740, 23);
		panel.add(textFieldAction);
		textFieldAction.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//textAreaResult.setText(textFieldAction.getText());
			}
		});
		btnSend.setBounds(10, 64, 89, 23);
		panel.add(btnSend);
		
		JTextPane txtpnResult = new JTextPane();
		txtpnResult.setText("result");
		txtpnResult.setBounds(10, 99, 740, 253);
		panel.add(txtpnResult);
		
		
	}
}
