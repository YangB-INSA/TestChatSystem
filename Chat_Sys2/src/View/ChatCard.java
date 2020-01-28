package View;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import Controller.Controller_Interface;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Desktop;
import java.awt.event.ActionEvent;

public class ChatCard extends JPanel {
	private Controller_Interface inter;
	private String receiverAddr;
	private JTextField textField;
	private JTextArea textArea;
	private JButton btnSend;

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
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
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
					textArea.append("                                                                       " + Date + "\n"+ "  Moi : " + message + "\n");
					inter.getReseau().sendMsgNormal(receiverAddr, message, Date);
					textField.setText("");
				}
			}
		});
		
		btnSend.setBounds(463, 539, 79, 43);
		add(btnSend);
		
		JButton btnFile = new JButton("File");
		String Date = getDate();
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					JFileChooser choix = new JFileChooser();
					 int returnVal = choix.showOpenDialog(getParent());
					    if(returnVal == JFileChooser.APPROVE_OPTION) {
					       System.out.println("You chose to open this file: " + choix.getSelectedFile().getName());
					       inter.getReseau().sendFileRequest(receiverAddr, choix.getSelectedFile().getName(), Date, choix.getSelectedFile());
					       inter.getReseau().sendFile(choix.getSelectedFile(), receiverAddr);
					       textArea.append("                                                                       " + Date + "\n"+ "  Moi : fichier " + choix.getSelectedFile().getName() + " envoyé " + "\n");
					       textField.setText("");
					    }
				    
			}
		});
		btnFile.setBounds(361, 539, 79, 43);
		add(btnFile);

	}
	
	public JButton getDefaultBtn () {
		return btnSend;
		
	}
	
	public void setNameChanged(String oldname, String newname) {
		textArea.append("\n" + "  Notification : " + oldname + " a changé son nom en " + newname + "\n" + "\n");
	}
	//méthode pour set le message
	public void setMessage(String sender, String message, String Date) {
		textArea.append("                                                                       " + Date + "\n" + "  " +sender + " : " + message + "\n");
	}
	public void setMessageFile (String sender, String FileName, String Date) {
		textArea.append("                                                                       " + Date + "\n" +
	"  " +sender + ": a envoyé le fichier suivant : " + FileName + "\n" + "  Vous le trouverez dans le chemin principale de l'application"  + "\n");
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
