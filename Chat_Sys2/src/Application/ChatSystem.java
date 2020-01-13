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
	Controller_Interface control;
	
	public ChatSystem () throws UnknownHostException, SocketException, InterruptedException {
		
		String host= InetAddress.getLocalHost().getHostAddress();
		
		//create the controller_interface
		this.control = new Controller_Interface(new User(host));
		
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