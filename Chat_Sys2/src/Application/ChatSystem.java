package Application;

import View.*;

import java.awt.EventQueue;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import Controller.*;
import Model.*;
import Model.messages.*;


public class ChatSystem {
	Login login;
	Controleur_Processor control;
	
	public ChatSystem () throws UnknownHostException, SocketException, InterruptedException {
		
		String host= InetAddress.getLocalHost().getHostAddress();
		
		//create the controller
		this.control = new Controleur_Processor(new User(host));
		
		//broadcast to get all users currently connected
		control.getReseau().getUserList();
		
		//create the GUI with the controller
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Login(control);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) throws Exception 
	{
		new ChatSystem();
    }
	
}
