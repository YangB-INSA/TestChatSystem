package View;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import Controller.Controller_Interface;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Login {
	private Controller_Interface Interface;
	private JFrame frame;
	private String username;
	private JTextField textField;
	private int coord_x,coord_y;
	
	/**
	 * Create the application.
	 */
	
	public Login(Controller_Interface Interface) {
		this.Interface = Interface;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.control);
		frame.setBounds(100, 100, 709, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Log in");
		frame.setResizable(true);
		frame.setUndecorated(true);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 369, 410);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				 coord_x = e.getX();
			     coord_y = e.getY();
			}
		});
		
		lblNewLabel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				
				int x = arg0.getXOnScreen();
	            int y = arg0.getYOnScreen();
	            frame.setLocation(x - coord_x, y - coord_y);  
			}
		});	
		
		lblNewLabel.setBounds(-51, -25, 420, 297);
		panel.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/Image/login image.jpg")));
		
		JLabel lblNewLabel_3 = new JLabel("Keep up the teamwork");
		lblNewLabel_3.setForeground(UIManager.getColor("Button.highlight"));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(85, 302, 264, 38);
		panel.add(lblNewLabel_3);
		
		JLabel lblLinkUsTogether = new JLabel("Link us together");
		lblLinkUsTogether.setForeground(Color.WHITE);
		lblLinkUsTogether.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLinkUsTogether.setBounds(107, 347, 199, 38);
		panel.add(lblLinkUsTogether);
		
		JLabel lblNewLabel_1 = new JLabel("Welcome on the chat !");
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 17));
		lblNewLabel_1.setBounds(428, 78, 223, 29);
		frame.getContentPane().add(lblNewLabel_1);
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar()==' ') {
					e.consume();
				}
				if (textField.getText().length() >= 15 ) { // limit textfield to 10 characters
		            e.consume(); 
				}
			}
		});
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(417, 198, 251, 44);
		
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Log in ");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username=textField.getText();
				if (username.isEmpty()) {
				    textField.setText("");
				}
				else if (username.length() < 4 ){
					JOptionPane.showMessageDialog(frame,"Your username must have at least 4 characters. \n "
				    		+ "Please enter another username.",
				    "Error",JOptionPane.ERROR_MESSAGE);
				    textField.setText("");
				}
				else if (Interface.checkUserUnicity(username)) {
				    JOptionPane.showMessageDialog(frame,"Username already used. \n "
				    		+ "Please enter another username.",
				    "Error",JOptionPane.ERROR_MESSAGE);
				    textField.setText("");
			    }
			    else
			    {
			    	Interface.getUser().setNom(username);
					System.out.println("Local User = " + Interface.getUser().getNom() + "/" + Interface.getUser().getAddr());
					Interface.getReseau().sendConnected();
			    	frame.dispose();
			    	Interface.setView(new Application(Interface));
		        }    
			}
		});
		
		btnNewButton.setFont(new Font("Verdana", Font.BOLD, 14));
		btnNewButton.setBounds(479, 292, 123, 44);
		frame.getContentPane().add(btnNewButton);
		frame.getRootPane().setDefaultButton(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Username");
		lblNewLabel_2.setFont(new Font("Verdana", Font.BOLD, 15));
		lblNewLabel_2.setBounds(495, 155, 104, 22);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("X");
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel_4.setBounds(687, 0, 22, 29);
		frame.getContentPane().add(lblNewLabel_4);
		frame.setVisible(true);
	}
}
