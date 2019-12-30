package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class ChangeUsername {

	JFrame owner;
	public String newUsername;
	private JDialog frame;
	private JFrame fra;
	private JTextField textField;

	/*
// Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeUsername window = new ChangeUsername();
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
	public ChangeUsername(JFrame owner) {
		this.owner=owner;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog(owner,"Change your username");
		frame.setVisible(true);
		frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		frame.setBounds(100, 100, 443, 253);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("You want to change username ? ");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 15));
		lblNewLabel.setBounds(90, 10, 280, 45);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter your new username : ");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(124, 50, 436, 45);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar()==' ') {
					e.consume();
				}
				if (textField.getText().length() >= 15 ) {// limit textfield to 15 characters
		            e.consume(); 
				}
			}
		});
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(66, 92, 310, 45);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Change");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newUsername=textField.getText();
			    if (newUsername.equals("abdel")) {
			    	frame.dispose();
			    }
			    else
			    {
				    System.out.println("Erreur");
				    JOptionPane.showMessageDialog(frame,"Username already used. \n "
				    		+ "Please enter another username.",
				    "Error",JOptionPane.ERROR_MESSAGE);
				    textField.setText("");
		        }    
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setFont(new Font("Verdana", Font.BOLD, 14));
		btnNewButton.setBounds(163, 147, 118, 41);
		frame.getContentPane().add(btnNewButton);
		frame.getRootPane().setDefaultButton(btnNewButton);
	}
	
	public String getusername() {
		return this.newUsername;
	}
}
