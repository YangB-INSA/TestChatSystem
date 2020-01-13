package View;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.CardLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import Model.User;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class test {

	private JFrame frame;
	int i;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 685, 554);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		String JANE = "Jane Doe";
		String BOBBY = "bobby";
		String KATTY = "Katty";
		
		
		DefaultListModel listModel = new DefaultListModel();
        listModel.addElement(JANE);
        listModel.addElement(BOBBY);
        listModel.addElement(KATTY);
		JList list = new JList(listModel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(26, 73, 158, 374);
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(207, 28, 440, 476);
		frame.getContentPane().add(panel_1);
		
		panel_1.setLayout(new CardLayout(0, 0));
		
		panel_1.add(new ChatCard(), JANE);
		panel_1.add(new ChatCard(),BOBBY);
		panel_1.add(new ChatCard(), KATTY);
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cl = (CardLayout)(panel_1.getLayout());
		        cl.show(panel_1,(String)list.getSelectedValue());
				System.out.println(list.getSelectedValue());
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		i=0;
		
		panel.add(list);
		
		JLabel lblList = new JLabel("List");
		lblList.setBounds(95, 48, 46, 14);
		frame.getContentPane().add(lblList);
		
	}
}
