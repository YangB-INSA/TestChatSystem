package View;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatCard extends JPanel {
	private JTextField textField;
	public JButton btnSend;

	/**
	 * Create the panel.
	 */
	public ChatCard() {
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 539, 327, 43);
		add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 532, 517);
		add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message=textField.getText();
				textArea.append(message + "\n");
				textField.setText("");
			}
		});
		
		btnSend.setBounds(463, 539, 79, 43);
		add(btnSend);
		
		JButton btnFile = new JButton("File");
		btnFile.setBounds(361, 539, 79, 43);
		add(btnFile);

	}
	
	public JButton getDefaultBtn () {
		return btnSend;
		
	}
	
	//méthode pour set le message
	public void setMessage( ) {
		
	}
}
