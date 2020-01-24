package Application;

import View.*;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import Controller.*;
import Model.*;
import Model.messages.*;


public class ChatSystem {
	
	private Login login;
	private Controller_Interface Interface;
	
	public ChatSystem () throws SocketException, UnknownHostException, InterruptedException {
		
		//create the controller_interface
		Interface = new Controller_Interface(new User("localhost"));
		
		String[] addresses= Interface.getHostAddresses();
		String host = addresses[0];
		
		Interface.getUser().setAddr(host);
		
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