package Application;
import View.*;

import java.awt.EventQueue;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import Controller.*;
import Model.*;


public class ChatSystem {
	private Controller_Interface Interface;
	
	public ChatSystem () throws SocketException, UnknownHostException, InterruptedException {
		//create the controller_interface
		Interface = new Controller_Interface();
	
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