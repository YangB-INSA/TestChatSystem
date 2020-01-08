package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ChatWindow extends JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea chat;

	/**
	 * Create the application.
	 */
	public ChatWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		chat = new JTextArea();
		chat.setEditable(false);
	}

}
