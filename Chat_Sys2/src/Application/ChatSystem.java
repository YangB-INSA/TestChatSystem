package Application;
import View.*;

import java.awt.EventQueue;
import java.net.SocketException;
import java.net.UnknownHostException;


import Controller.*;
import Model.*;


public class ChatSystem {
	private Controller_Interface Interface;
	
	public ChatSystem () throws SocketException, UnknownHostException, InterruptedException {
		
		String host= Controller_Interface.getHostAddresses();
		
		//create the controller_interface
		Interface = new Controller_Interface(new User(host));
	
		//broadcast to get all users currently connected
		Interface.getReseau().getUserList();	
		
		//create the GUI with the controller
  		EventQueue.invokeLater(new Runnable() {
  			public void run() {
  				try {
  					new Login(Interface);
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