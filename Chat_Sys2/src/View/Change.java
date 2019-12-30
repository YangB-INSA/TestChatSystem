package View;

import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Change {

	private JDialog dialog;
	public String newUsername;
	JFrame owner;
	private JLabel lblNewLabel;
	private JTextField textField;
/*
	/**
	 * Launch the application.
	 /
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Change window = new Change();
					window.dialog.setVisible(true);
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
	public Change(JFrame owner) {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dialog = new JDialog(owner, "Change your username",true);
		dialog.setLocationRelativeTo(owner);
		dialog.getContentPane().setLayout(null);
		dialog.setSize(800, 200);//On lui donne une taille
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		
	}
	
	public String getusername() {
		return this.newUsername;
	}

}
