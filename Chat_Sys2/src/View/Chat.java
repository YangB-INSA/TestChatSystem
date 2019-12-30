package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.SystemColor;
import java.awt.TrayIcon.MessageType;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JList;
import java.awt.Color;
import javax.swing.UIManager;

import Controller.Controleur_Processor;
import Model.User;

import javax.swing.JLabel;

public class Chat {

	Controleur_Processor control;
	public String username;
	private JFrame frame;
	private JTextField textField;
	
	/**
	 * Create the application.
	 */
	public Chat(Controleur_Processor control) {
		this.control = control;
		this.username = control.getUser().getNom();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(244, 10, 497, 541);
		frame.getContentPane().add(panel);
		panel.setLayout(new CardLayout(0, 0));
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		panel.add(textArea, "name_886610462468200");
		
		textField = new JTextField();
		textField.setBounds(245, 588, 416, 69);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = textField.getText();
				if (message.isEmpty()) {
					textField.setText("");
				}
				else {
					textArea.append(control.getUser().getNom() + " : " + message + "\n");
					textField.setText("");
				}
			}
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(669, 602, 72, 38);
		frame.getContentPane().add(btnNewButton);
		frame.getRootPane().setDefaultButton(btnNewButton);
		
		//JList
		DefaultListModel dlm = new DefaultListModel();
		JList list = new JList(dlm);
		JScrollPane scrollPane = new JScrollPane(list);
		
		scrollPane.setBounds(20, 147, 207, 510);
		for(User user : control.getUserList())
		{
		    dlm.addElement(user);
		}
		control.getUserList().toArray();
		frame.getContentPane().add(scrollPane);
		
		JButton btnNewButton_1 = new JButton("Start a new session");
		btnNewButton_1.setBackground(Color.BLACK);
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Verdana", Font.BOLD, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(20, 91, 207, 38);
		frame.getContentPane().add(btnNewButton_1);
		frame.setBounds(100, 100, 1012, 746);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent event) {
	            exitProcedure(frame);
	        }
	    });
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(3, 3, 16, 2));
		menuBar.setForeground(SystemColor.textInactiveText);
		menuBar.setBackground(UIManager.getColor("Button.background"));
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Connected as " + control.getUser().getNom());
		mnNewMenu.setForeground(SystemColor.desktop);
		mnNewMenu.setIcon(new ImageIcon("D:\\Projet INSA\\Webp.net-resizeimage.png"));
		mnNewMenu.setBackground(SystemColor.textHighlight);
		menuBar.add(mnNewMenu);
		
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Disconnect");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitProcedure(frame);
			}
		});
		mntmNewMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Change username");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Change change = new Change(frame);
				String newUsername = JOptionPane.showInputDialog(frame, "Entrez un nouveau username","Want to change ?",JOptionPane.QUESTION_MESSAGE);
				control.getUser().setNom(newUsername);
				System.out.println("Local User = " + control.getUser().getNom() + "/" + control.getUser().getAddr());
		
				mnNewMenu.setText("Connected as " + control.getUser().getNom());
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		
		
	}
	
	public void exitProcedure(JFrame frame) {
		control.getReseau().sendDisconnected();
		frame.dispose();
		System.exit(0);
	}
}
/*	
	
	  Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat window = new Chat();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
*/