package View;


import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import Controller.Controller_Interface;
import Model.User;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.CardLayout;
import javax.swing.JTextArea;
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
import javax.swing.UIManager;

public class Application {

	private JFrame frame;
	private String username;
	private DefaultListModel usermodel;
	private DefaultListModel sessionmodel;
	private JList userlist;
	private JList sessionlist;
	private Controller_Interface inter;
	private JPanel chatpanel;
	private CardLayout cl;

	/**
	 * Create the application.
	 */
	public Application(Controller_Interface inter) {
		this.inter=inter;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 872, 705);
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
		
		JLabel chatTitleLabel = new JLabel("Start chatting now ! ");
		chatTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		chatTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		chatTitleLabel.setBounds(278, 0, 552, 25);
		frame.getContentPane().add(chatTitleLabel);
		
		usermodel = new DefaultListModel();
		userlist = new JList();
		for(User user : inter.getUserList())
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
		for(User session : inter.getSessionList())
		{
			sessionmodel.addElement(session);
		    ChatCard card = new ChatCard(inter, session.getAddr());
		    card.setName(session.getAddr());  
		    chatpanel.add(card,session.getAddr());    
		}
		sessionlist.setModel(sessionmodel);
		
		sessionlist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {	
				showCard();
				if (sessionlist.getSelectedValue() != null) {
					chatTitleLabel.setText(((User)sessionlist.getSelectedValue()).getNom());
					setDefaultButton();	
				}
				else {
					chatTitleLabel.setText("Start chatting now !");
				}
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
				else if(inter.getSessionList().contains(receiver)) {
					System.out.println("Session déjà démarrée");
				}
				else {
					inter.addUserInSessionList(receiver);		
					sessionlist.setSelectedValue(receiver, true);
					showLastCard();
					setDefaultButton();
					inter.getReseau().sendStart_rq(receiver);
					
				}
			}
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(25, 267, 218, 33);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnStopThisSession = new JButton("Stop this session");
		btnStopThisSession.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				User receiver = (User) sessionlist.getSelectedValue();
				if (receiver==null) {
					System.out.println("Veuillez sélectionnez une session");
				}
				else {
					inter.getReseau().sendStop_rq(receiver);
					inter.removeUserInSessionList(receiver);	
				}
			}
		});
		
		btnStopThisSession.setBackground(Color.DARK_GRAY);
		btnStopThisSession.setForeground(Color.WHITE);
		btnStopThisSession.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnStopThisSession.setBounds(25, 571, 218, 33);
		frame.getContentPane().add(btnStopThisSession);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(UIManager.getColor("Button.light"));
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Connected as " + inter.getUser().getNom());
		mnNewMenu.setHorizontalAlignment(SwingConstants.CENTER);
		mnNewMenu.setIcon(new ImageIcon(Application.class.getResource("/Image/green connected.png")));
		mnNewMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Change Username");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ChangeName dialog = new ChangeName(inter);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				mnNewMenu.setText("Connected as " + inter.getUser().getNom());
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
		frame.setVisible(true);
	}
	
	
	/**  Methods  */
	
	public void exitProcedure(JFrame frame) {
		inter.getReseau().sendDisconnected();
		System.out.println("Application closed");
		frame.dispose();
		System.exit(0);
	}
	
	public void AddtoUserList(User sender) {
		usermodel.addElement(sender);
	}
	
	public void RemoveFromUserList(User sender) {
		usermodel.removeElement(sender);
	}
	
	public void AddtoSessionList(User sender) {
		sessionmodel.addElement(sender);
		ChatCard card = new ChatCard(inter, sender.getAddr());
	    card.setName(sender.getAddr());  
	    chatpanel.add(card,sender.getAddr());
	}
	
	public void RemoveFromSessionList(User sender) {
		
		Component[] components = chatpanel.getComponents();
		
		for(int i = 0; i < components.length; i++) {
			System.out.println(components[i].getName());
		    if(components[i] instanceof ChatCard && ((ChatCard)components[i]).getName().equals(sender.getAddr())) {
		        chatpanel.remove(components[i]);
		    }
		} 
		sessionmodel.removeElement(sender);
		showLastCard();		
		sessionlist.setSelectedIndex(sessionlist.getLastVisibleIndex());
	}
	
	public void UpdateListUI() {
		userlist.repaint();
		sessionlist.repaint();
	}
	
	public JPanel getChatPanel() {
		return this.chatpanel;
	}
	
	//get the card on top of the cardlayout
	public ChatCard getCurrentCard() {
	    ChatCard card = null;

	    for (Component comp : chatpanel.getComponents() ) {
	        if (comp instanceof ChatCard && comp.isVisible() == true) {
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
		//test if there is more than just the JtextArea in the Jpanel
		//just faire des cas particuliers avec if sessionlist = null ou getselected value = null
		
		if (sessionlist.getSelectedValue() == null) {
			cl = (CardLayout)(chatpanel.getLayout());
			cl.first(chatpanel);
		}
		else {
			cl = (CardLayout)(chatpanel.getLayout());
			cl.show(chatpanel, ((User)sessionlist.getSelectedValue()).getAddr());
		}
	}
	
	public void showFirstCard() {
		cl = (CardLayout)(chatpanel.getLayout());
		cl.first(chatpanel);
	}
	
	public void showLastCard() {
		cl = (CardLayout)(chatpanel.getLayout());
		cl.last(chatpanel);
	}
	
	public boolean checkSessionOpened(User user) {
		boolean isIn = false;
		for(User session : inter.getSessionList()) {
			if (session.getAddr().equals(user.getAddr())) {
				isIn=true;
			}
		}
		return isIn;	
	}
}
