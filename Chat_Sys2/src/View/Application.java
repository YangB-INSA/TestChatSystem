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

import Controller.Controller_Interface;
import Model.Session;
import Model.User;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
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

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Application {

	private JFrame frame;
	public String username;
	DefaultListModel usermodel;
	DefaultListModel sessionmodel;
	JList userlist;
	JList sessionlist;
	Controller_Interface control;
	JPanel chatpanel;

	/**
	 * Create the application.
	 */
	public Application(Controller_Interface control) {
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
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent event) {
	            exitProcedure(frame);
	        }
	    });
		
		chatpanel = new JPanel();
		chatpanel.setBounds(278, 27, 552, 593);
		frame.getContentPane().add(chatpanel);
		chatpanel.setLayout(new CardLayout(0, 0));
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(Color.LIGHT_GRAY);
		chatpanel.add(textArea, "name_980796593023600");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 27, 218, 230);
		frame.getContentPane().add(scrollPane);
		
		usermodel = new DefaultListModel();
		userlist = new JList();
		for(User user : control.getUserList())
		{
		    usermodel.addElement(user);
		}
		userlist.setModel(usermodel);
		scrollPane.setViewportView(userlist);
		
		JLabel lblNewLabel = new JLabel("Users connected");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(25, 331, 218, 230);
		frame.getContentPane().add(scrollPane_1);
		
		sessionmodel = new DefaultListModel();
		sessionlist = new JList();
		for(Session session : control.getSessionList())
		{
			sessionmodel.addElement(session);
		    ChatCard card = new ChatCard(control, session.getUser().getAddr());
		    card.setName(session.getUser().getAddr());  
		    chatpanel.add(card,session.getUser().getAddr());    
		}
		sessionlist.setModel(sessionmodel);
		
		sessionlist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {				
				showCard();
		        setDefaultButton();		     
			}
		});
		
		scrollPane.setViewportView(userlist);
		scrollPane_1.setViewportView(sessionlist);
		
		JLabel lblNewLabel_1 = new JLabel("Sessions opened");
		lblNewLabel_1.setBackground(Color.BLACK);
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Start new session");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User receiver = (User) userlist.getSelectedValue();
				if (receiver==null) {
					System.out.println("Veuillez sélectionnez un utilisateur");
				}
				else {
					control.getReseau().sendStart_rq(receiver);
					control.addUserInSessionList(receiver);
					UpdateSessionList();
					showLastCard();
					setDefaultButton();
					
				}
			}
		});
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
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Connected as " + control.getUser().getNom());
		mnNewMenu.setIcon(new ImageIcon(Application.class.getResource("/Image/green connected.png")));
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
		System.out.println("Application closed");
		frame.dispose();
		System.exit(0);
	}
	
	public void UpdateUserList() {
		usermodel.clear();
		for(User user : control.getUserList())
		{
		    usermodel.addElement(user);
		}
		userlist.setModel(usermodel);
	}
	
	public void UpdateSessionList() {
		sessionmodel.clear();
		for(Session session : control.getSessionList())
		{
			sessionmodel.addElement(session);
		    ChatCard card = new ChatCard(control, session.getUser().getAddr());
		    card.setName(session.getUser().getNom());  
		    chatpanel.add(card,session.getUser().getAddr());
		  
		}
		sessionlist.setModel(sessionmodel);
	}
	
	public JPanel getChatPanel() {
		return this.chatpanel;
	}
	
	//get the card on top of the cardlayout
	public ChatCard getCurrentCard() {
	    ChatCard card = null;

	    for (Component comp : chatpanel.getComponents() ) {
	        if (comp.isVisible() == true) {
	            card = (ChatCard)comp;
	        }
	    }
	    return card;
	}
	
	//set the default button according to the card currently on top
	public void setDefaultButton() {
		JButton send = getCurrentCard().getDefaultBtn();
		frame.getRootPane().setDefaultButton(send);
	}
	
	//show the corresponding card linked to the selected session in session list
	public void showCard() {
		CardLayout cl = (CardLayout)(chatpanel.getLayout());
        cl.show(chatpanel, ((Session) sessionlist.getSelectedValue()).getUser().getAddr());
	}
	
	//show the newest opened session
	public void showLastCard() {
		CardLayout cl = (CardLayout)(chatpanel.getLayout());
		cl.last(chatpanel);
		
	}
	
}
