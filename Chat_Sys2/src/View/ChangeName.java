package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.Controller_Interface;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangeName extends JDialog {

	private Controller_Interface inter;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private String newName;
	private String oldName;
	private JDialog window;
	/*
	 
	public static void main(String[] args) {
		try {
			ChangeName dialog = new ChangeName();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	/**
	 * Create the dialog.
	 */
	public ChangeName(Controller_Interface inter) {
		
		this.window = this;
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 412, 231);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		
		textField = new JTextField();
		textField.setBounds(49, 82, 285, 36);
		contentPanel.add(textField);
		textField.setColumns(10);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar()==' ') {
					e.consume();
				}
				if (textField.getText().length() >= 10 ) { // limit textfield to 10 characters
		            e.consume(); 
				}
			}
		});
		
		JButton btnChange = new JButton("Change");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newName = textField.getText();
				oldName = inter.getUser().getNom();
				if (newName.isEmpty()) {
				    textField.setText("");
				}
				else if (newName.length() < 4 ){
					JOptionPane.showMessageDialog(window,"Your username must have at least 4 characters. \n "
				    		+ "Please enter another username.",
				    "Error",JOptionPane.ERROR_MESSAGE);
				    textField.setText("");
				}
				else if (newName.equals(oldName) ){
					JOptionPane.showMessageDialog(window,"Your username is the same as your current one \n "
				    		+ "Please enter another username.",
				    "Error",JOptionPane.ERROR_MESSAGE);
				    textField.setText("");
				}
				else if (inter.checkUserUnicity(newName)) {
				    JOptionPane.showMessageDialog(window,"Username already used. \n "
				    		+ "Please enter another username.",
				    "Error",JOptionPane.ERROR_MESSAGE);
				    textField.setText("");
			    }
				else {
					inter.getUser().setNom(newName);
					System.out.println(" New Local User = " + inter.getUser().getNom() + "/" + inter.getUser().getAddr());
					inter.getReseau().sendNameChanged(oldName);
					window.dispose();
				}
				
			}
		});
		btnChange.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnChange.setBounds(92, 133, 87, 36);
		contentPanel.add(btnChange);
		window.getRootPane().setDefaultButton(btnChange);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.dispose();
			}
		});
		btnCancel.setBounds(204, 133, 87, 36);
		contentPanel.add(btnCancel);
		
		JLabel lblEnterANew = new JLabel("Enter a new username ");
		lblEnterANew.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEnterANew.setBounds(125, 41, 178, 30);
		contentPanel.add(lblEnterANew);
		
		JLabel lblWantToChange = new JLabel("Want to change ?");
		lblWantToChange.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblWantToChange.setBounds(136, 11, 178, 30);
		contentPanel.add(lblWantToChange);
	}
	
	public String getNewName() {
		if (newName != null) {
			return this.newName;
		}
		else {
			return this.oldName;
		}
	}
	
}
