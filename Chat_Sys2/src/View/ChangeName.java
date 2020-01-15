package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class ChangeName extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

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
	public ChangeName() {
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
		
		JButton btnChange = new JButton("Change");
		btnChange.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnChange.setBounds(92, 133, 87, 36);
		contentPanel.add(btnChange);
		
		JButton btnCancel = new JButton("Cancel");
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
}
