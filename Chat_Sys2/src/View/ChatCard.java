package View;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controller.Controller_Interface;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class ChatCard extends JPanel {
	Controller_Interface inter;
	public String receiverAddr;
	private JTextField textField;
	private JTextArea textArea;
	public JButton btnSend;

	/**
	 * Create the panel.
	 */
	public ChatCard(Controller_Interface inter,String Addr) {
		this.inter = inter;
		this.receiverAddr=Addr;
		
		setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 539, 327, 43);
		add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 532, 517);
		add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().isBlank()) {
					textField.setText("");
				}
				else {
					String message=textField.getText();
					String Date = getDate();
					textArea.append(Date + "  Moi : " + message + "\n");
					inter.getReseau().sendMsgNormal(receiverAddr, message, Date);
					textField.setText("");
				}
			}
		});
		
		btnSend.setBounds(463, 539, 79, 43);
		add(btnSend);
		
		JButton btnFile = new JButton("File");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser choix = new JFileChooser();
				 int returnVal = choix.showOpenDialog(getParent());
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       System.out.println("You chose to open this file: " + choix.getSelectedFile().getName());
				       inter.getReseau().sendFileRequest(receiverAddr, choix.getSelectedFile().getName());
				       inter.getReseau().sendFile(choix.getSelectedFile(), receiverAddr);
				    }
			}
		});
		btnFile.setBounds(361, 539, 79, 43);
		add(btnFile);

	}
	
	public JButton getDefaultBtn () {
		return btnSend;
		
	}
	
	//méthode pour set le message
	public void setMessage(String sender, String message, String Date) {
		textArea.append(Date + " " + sender + " : " + message + "\n");
	}
	
	public String getDate() {
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd | HH:mm:ss");
		Date date = new Date();
		return format.format(date);
	}
	
	public String getReceiver() {
		return this.receiverAddr;
	}
}
