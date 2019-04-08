import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

import constants.ApplicationConstants;
import helpers.SqliteWrapper;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Hangman {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JTextField textFieldCategoryName;
	private JTextField textFieldNewWord;
	private JTextField textFieldHint;
	private JPanel panelUpdateCategories;
	private JTabbedPane tabbedPane;
	private JPanel panelLogin;
	private JComboBox comboBoxSelectedCategory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hangman window = new Hangman();
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
	public Hangman() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.WHITE);
		
		frame.setBounds(100, 100, 700, 400);
		frame.setMinimumSize(new Dimension(700, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panelPlay = new JPanel();
		tabbedPane.addTab("Play", null, panelPlay, null);
		panelPlay.setLayout(null);

		panelLogin = new JPanel();
		tabbedPane.addTab("Login", null, panelLogin, null);
		panelLogin.setLayout(null);

		JMenuItem mntmLogin = new JMenuItem("Login");
		mntmLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.addTab("Login", null, panelLogin, null);
			}
		});
		mnFile.add(mntmLogin);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 11, 70, 14);
		panelLogin.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 36, 70, 14);
		panelLogin.add(lblPassword);

		txtUsername = new JTextField();
		txtUsername.setText("username");
		txtUsername.setBounds(90, 8, 86, 20);
		panelLogin.add(txtUsername);
		txtUsername.setColumns(10);

		pwdPassword = new JPasswordField();
		pwdPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				pwdPassword.setText("");
			}
			
		});
		pwdPassword.setText("password");
		pwdPassword.setBounds(90, 33, 86, 20);
		panelLogin.add(pwdPassword);

		panelUpdateCategories = new JPanel();
		
		
		tabbedPane.addTab("Update categories", null, panelUpdateCategories, null);
		panelUpdateCategories.setLayout(null);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(btnLogin.getText().equals(ApplicationConstants.LOGIN_STRING)) {
					if(txtUsername.getText().equals(ApplicationConstants.USER) &&
							new String(pwdPassword.getPassword()).equals(ApplicationConstants.PWD)) {
						tabbedPane.addTab("Update categories", null, panelUpdateCategories, null);
						btnLogin.setText(ApplicationConstants.LOGOUT_STRING);
					}
				}
				else {
					btnLogin.setText(ApplicationConstants.LOGIN_STRING);
					txtUsername.setText("");
					pwdPassword.setText("");
					tabbedPane.remove(panelUpdateCategories); 
					tabbedPane.remove(panelLogin);
				}

			}
		});
		btnLogin.setBounds(90, 64, 86, 23);
		panelLogin.add(btnLogin);


		JLabel lblCategoryName = new JLabel("Category name");
		lblCategoryName.setBounds(10, 11, 135, 14);
		panelUpdateCategories.add(lblCategoryName);

		textFieldCategoryName = new JTextField();
		textFieldCategoryName.setBounds(244, 8, 106, 20);
		panelUpdateCategories.add(textFieldCategoryName);
		textFieldCategoryName.setColumns(10);

		JButton btnAddCategory = new JButton("Add category");
		btnAddCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SqliteWrapper sqliteWrapper = new SqliteWrapper();
				try {
					sqliteWrapper.insertCategory(textFieldCategoryName.getText());
					lblCategoryName.setForeground(Color.BLACK);
					comboBoxSelectedCategory.removeAllItems();
					HashMap<Integer, String> categoriesHashMap = sqliteWrapper.selectAllCategories();
					for (String category : categoriesHashMap.values()) {
						comboBoxSelectedCategory.addItem(category);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					lblCategoryName.setForeground(Color.RED);
				}
				
			}
		});
		btnAddCategory.setBounds(360, 7, 135, 23);
		panelUpdateCategories.add(btnAddCategory);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 659, 2);
		panelUpdateCategories.add(separator);

		JLabel lblSelectedCategoryName = new JLabel("Selected category name");
		lblSelectedCategoryName.setBounds(10, 49, 224, 14);
		panelUpdateCategories.add(lblSelectedCategoryName);

		comboBoxSelectedCategory = new JComboBox();
		comboBoxSelectedCategory.setBounds(244, 46, 251, 20);
		panelUpdateCategories.add(comboBoxSelectedCategory);

		JLabel lblWord = new JLabel("Word ");
		lblWord.setBounds(10, 74, 224, 14);
		panelUpdateCategories.add(lblWord);

		textFieldNewWord = new JTextField();
		textFieldNewWord.setBounds(244, 71, 251, 20);
		panelUpdateCategories.add(textFieldNewWord);
		textFieldNewWord.setColumns(10);

		JLabel lblHint = new JLabel("Hint");
		lblHint.setBounds(10, 99, 224, 14);
		panelUpdateCategories.add(lblHint);

		textFieldHint = new JTextField();
		textFieldHint.setBounds(244, 99, 251, 20);
		panelUpdateCategories.add(textFieldHint);
		textFieldHint.setColumns(10);

		JButton btnAddWordTo = new JButton("Add word to database");
		btnAddWordTo.setBounds(244, 127, 251, 23);
		panelUpdateCategories.add(btnAddWordTo);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				SqliteWrapper sqliteWrapper = new SqliteWrapper();
				sqliteWrapper.createTables();
				tabbedPane.remove(panelLogin);
				tabbedPane.remove(panelUpdateCategories);
			}
		});
		
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(tabbedPane.getSelectedComponent().equals(panelUpdateCategories)) {
					comboBoxSelectedCategory.removeAllItems();
					SqliteWrapper sqliteWrapper = new SqliteWrapper();
					HashMap<Integer, String> categoriesHashMap = sqliteWrapper.selectAllCategories();
					for (String category : categoriesHashMap.values()) {
						comboBoxSelectedCategory.addItem(category);
					}
					
				}
			}
		});
		
	}
}
