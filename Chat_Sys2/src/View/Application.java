package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Controller.Controleur_Processor;

import java.awt.Font;
import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Application {

	private JFrame frame;
	private JTextField textField;
	public String username;
	Controleur_Processor control;
/*
	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 */
	public Application(Controleur_Processor control) {
		this.control=control;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 876, 697);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent event) {
	            exitProcedure(frame);
	        }
	    });
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 27, 218, 230);
		frame.getContentPane().add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JLabel lblNewLabel = new JLabel("Users connected");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(25, 331, 218, 230);
		frame.getContentPane().add(scrollPane_1);
		
		JList list_1 = new JList();
		scrollPane_1.setViewportView(list_1);
		
		JLabel lblNewLabel_1 = new JLabel("Sessions opened");
		lblNewLabel_1.setBackground(Color.BLACK);
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Start new session");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(25, 267, 218, 33);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnStopThisSession = new JButton("Stop this session");
		btnStopThisSession.setBackground(Color.DARK_GRAY);
		btnStopThisSession.setForeground(Color.WHITE);
		btnStopThisSession.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnStopThisSession.setBounds(25, 571, 218, 33);
		frame.getContentPane().add(btnStopThisSession);
		
		JPanel panel = new JPanel();
		panel.setBounds(278, 27, 552, 491);
		frame.getContentPane().add(panel);
		panel.setLayout(new CardLayout(0, 0));
		
		JTextArea textArea = new JTextArea();
		panel.add(textArea, "name_13997320947600");
		
		textField = new JTextField();
		textField.setBounds(278, 536, 392, 68);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Send");
		btnNewButton_1.setBackground(Color.DARK_GRAY);
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_1.setBounds(768, 551, 62, 53);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnFile = new JButton("File");
		btnFile.setForeground(Color.WHITE);
		btnFile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnFile.setBackground(Color.DARK_GRAY);
		btnFile.setBounds(693, 551, 62, 53);
		frame.getContentPane().add(btnFile);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Connected as " + control.getUser().getNom());
		mnNewMenu.setIcon(new ImageIcon("D:\\Projet INSA\\Webp.net-resizeimage.png"));
		mnNewMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Change Username");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String newUsername = JOptionPane.showInputDialog(frame, "Entrez un nouveau username","Want to change ?",JOptionPane.QUESTION_MESSAGE);
				String oldUsername = control.getUser().getNom();
				control.getUser().setNom(newUsername);
				System.out.println(" New Local User = " + control.getUser().getNom() + "/" + control.getUser().getAddr());
				control.getReseau().sendNameChanged(oldUsername);
				mnNewMenu.setText("Connected as " + control.getUser().getNom());
				
			}
		});
		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Disconnect");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitProcedure(frame);
			}
		});
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		mnNewMenu.add(mntmNewMenuItem);
	}
	
	public void exitProcedure(JFrame frame) {
		control.getReseau().sendDisconnected();
		frame.dispose();
		System.exit(0);
	}
}
