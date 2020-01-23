package Controller;


import Model.reseau.UDPReceiver;
import Model.reseau.UDPSender;
import View.ChatCard;

import java.awt.Component;
import java.awt.Desktop;

import TCPFile.TCPSend;
import TCPFile.TCPReceive;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JPanel;

import java.net.DatagramPacket;
import java.net.InetAddress;
import Model.messages.*;
import Model.Session;
import Model.User;

/**
 * Manage sending messages & processing messages received
 */
public class Controller_reseau {
	
	public Controller_Interface inter;
	public User user;
    private UDPSender client;
    private UDPReceiver server;
    private InetAddress broadcast;
    private int port;
    final int portTCP=3300;
    
    public Controller_reseau(Controller_Interface p, User utilisateur) throws SocketException, InterruptedException, UnknownHostException {
        this.inter = p;
        this.user= utilisateur;
        this.port = user.getPort();
        broadcast = InetAddress.getByName("255.255.255.255"); //broadcast
        server = new UDPReceiver(this);
        server.setName("UDP Receiver");
        server.start(); //start le thread receiver
        client = new UDPSender(this);  
    }
   
    public UDPSender getClient() {
    	return this.client;
    }
    public int getPort() {
    	return this.port;
    }
    
    public void showUserList(List<User> list){
    	for (int i=0; i < list.size(); i++) {
    		System.out.println(list.get(i));
    	}	
    }
    
    public void showSessionList(List<User> list){
    	for (int i=0; i < list.size(); i++) {
    		System.out.println(list.get(i));
    	}	
    }
    
    /**
     * check if the sender isn't us (cause broadcast we send are also sent to the localhost)
     * @param remoteAddr
     * @return
     */
    public boolean checkSender(String remoteAddr) {
    	return (inter.getUser().getAddr().equals(remoteAddr)); 
    }
    
    public boolean checkSession(String addr) {
    	boolean isOpened=false;
    	List<User> sessionlist=inter.getSessionList();
    	for (int i=0;i<sessionlist.size();i++) {
    		if (sessionlist.get(i).getAddr().equals(addr)) {
    			System.out.println("Session opened with " + sessionlist.get(i).getAddr());
    		    isOpened = true;
    		}
    	}
    	return isOpened;
    }
    
    /* Method to manage received messages */

    public void messageHandle(Message m, String remoteAddr) throws IOException
    {
    	/**
    	 un broadcast envoie a tous les gens sur le rï¿½seau, y compris nous meme
    	 * donc il faut verifier si la hostAddr du sender  n'est pas la notre
    	 */
    	
        if(!checkSender(remoteAddr)) {

        	if (m instanceof GetUserList) {
        		System.out.println("GetUserList reï¿½u from " + m.getSender() +"\n");
        		if (inter.getUser().getNom().equals("noname")) {
        			System.out.println("not connected yet sry bru");
        		}
        		else {
        			sendOK(m.getSender());
        		}
        	}
        	
        	else if (m instanceof Connected) {
            	System.out.println("Connected reï¿½u from " + m.getSender() +"\n");
            	inter.addUserInUserList(m.getSender());
            	
            	if (inter.getView() != null) {
            		inter.getView().UpdateUserList();
            	}     	
            }
            
            else if (m instanceof OK) {
            	System.out.println("OK reï¿½u from " + m.getSender() +"\n");
            	inter.addUserInUserList(m.getSender());
            }
            
            else if (m instanceof Disconnected) {
            	System.out.println("Disconnected reï¿½u from " + m.getSender() +"\n");
            	// on le supprime de la user list + si on a ouvert une session on la ferme 
            	if (!inter.removeInUserList(m.getSender())) {
            		System.out.println("cet utilisateur n'existe pas");
            	}      
            }
            
            /*si on recoit une telle notif, on cherche l'utilisateur dans notre liste
              qui porte cette ancien nom, et on le change par le nouveau */
            else if (m instanceof NameChanged) {
            	System.out.println("NameChanged reçu from " + m.getSender());
                
            	if (!inter.changeNameInUserList(m.getSender())) {
            		System.out.println("cet utilisateur n'existe pas");
            	}
            }
            
            else if (m instanceof Start_rq) {
            	System.out.println("Start_rq reçu from " + m.getSender() +"\n");
            	inter.addUserInSessionList(m.getSender());
            }
        	
            else if (m instanceof Stop_rq) {
            	System.out.println("Stop_rq reçu from " + m.getSender() +"\n");
            	inter.removeUserInSessionList(m.getSender());
            }
        	
            else if (m instanceof FileRequest) {
            	System.out.println("FileRequest reï¿½u from " + m.getSender() +"\n");
            	System.out.println("Dï¿½marrage de l'ï¿½coute pour recevoir le fichier"+"\n");
            	this.receiveFile(((FileRequest) m).getFileName());
            	User sender = ((FileRequest)m).getSender();
            	JPanel chatPanel = inter.getView().getChatPanel();
            	
            	for (Component comp : chatPanel.getComponents()) {
        
            		//System.out.println(comp);
            	    if (comp instanceof ChatCard && ((ChatCard)comp).getReceiver().equals(sender.getAddr())) {
            	    	((ChatCard)comp).setMessageFile(sender.getNom(),((FileRequest) m).getFileName(), ((FileRequest)m).getDate());
            	    	try {
							Thread.sleep(2 * 1000); // Pause de 2 secs sinon ça ouvre direct le fichier
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
            	    	Desktop.getDesktop().open( ((FileRequest) m).getFile() );;
            	    }
            	}
            }
        	
            else if(m instanceof MsgNormal) {
            	System.out.println("msgnormal reï¿½u par "+ ((MsgNormal)m).getSender() + " : " + ((MsgNormal)m).getMessage());
            	User sender = ((MsgNormal)m).getSender();
            	JPanel chatPanel = inter.getView().getChatPanel();
            	
            	for (Component comp : chatPanel.getComponents()) {
        
            		//System.out.println(comp);
            	    if (comp instanceof ChatCard && ((ChatCard)comp).getReceiver().equals(sender.getAddr())) {
            	    	System.out.println(((ChatCard)comp).getReceiver());
            	    	System.out.println(sender.getNom() + ((MsgNormal)m).getMessage() + ((MsgNormal)m).getDate());
            	        ((ChatCard)comp).setMessage(sender.getNom(),((MsgNormal)m).getMessage(), ((MsgNormal)m).getDate());
            	    
            	    }
            	}
            	
            }
            
        }
        
        
        //check if there's a session opened with the sender of this message
        //if (checkSession(m.getSender().getAddr())) 
        	 
        
    
     }
        
  
    
    
    
    /* Methods to send every message types*/
    
    
   /* Send a broadcast msg to get 
    * the userlist before logging in 
    */
    public void getUserList() {
    	Message m = new GetUserList(inter.getUser());
    	client.sendTo(m,broadcast.getHostAddress(), port);
    	System.out.println("broadcast sent to get users connected"+"\n");
    }
    
    /* Send a broadcast msg to notify
     * other users that you are connected
     */
    public void sendConnected() {
        Message m = new Connected(inter.getUser());
        client.sendTo(m,broadcast.getHostAddress(),port);
        System.out.println("Connected envoyï¿½ : Username = " + inter.getUser().getNom()+"\n");
    }
    
    /* Send the local User info to another user 
     * who requested it through notifications (GetUserlist and Connected)
     */
    public void sendOK(User receiver) {
        Message m = new OK(inter.getUser());
        client.sendTo(m,receiver.getAddr(),port);
        System.out.println("Ok envoyï¿½ ï¿½ " + receiver.getNom()+"\n");
    }
    
    public void sendFileRequest (String receiver, String FileName, String date, File myFile) {
    	Message m = new FileRequest(inter.getUser(), FileName, date, myFile);
    	client.sendTo(m,receiver,port);
        System.out.println("Notification d'envois de fichier envoyï¿½ ï¿½ " + inter.getUser().getNom()+"\n");
    }

    /* Send a broadcast msg to notify
     * other users that you are disconnected
     */
    public void sendDisconnected() {
        Message m = new Disconnected(inter.getUser());
        client.sendTo(m,broadcast.getHostAddress(),port);    
        System.out.println("Disconnected envoyï¿½ : Username = " + inter.getUser().getNom()+"\n");
    }

    /* Send a broadcast msg to notify
     * other users that you change your username
     */
    public void sendNameChanged() {
    	Message m = new NameChanged(inter.getUser());
        client.sendTo(m,broadcast.getHostAddress(),port); 
        System.out.println("NameChanged envoyï¿½ : Username = " + inter.getUser().getNom()+"\n");
    }
    
    public void sendStart_rq(User receiver) {
        Message m = new Start_rq(inter.getUser());
        client.sendTo(m,receiver.getAddr(),port); 
        System.out.println("Start_rq envoyï¿½ vers " + receiver.getNom()+"\n");
    }
    
    public void sendStop_rq(User receiver) {
        Message m = new Stop_rq(inter.getUser());
        client.sendTo(m,receiver.getAddr(),port); 
        System.out.println("Stop_rq envoyï¿½ vers " + receiver.getNom()+"\n");
    }
    
    public void sendMsgNormal(String receiver, String msg, String date) {
        Message m = new MsgNormal(inter.getUser(),msg,date);
        client.sendTo(m,receiver,port); 
        System.out.println("Message envoyï¿½ par " + inter.getUser().getNom() + " : " + msg + ", ï¿½ " + receiver + " sur le port " + port+"\n");
    }
    public void sendFile (File file, String addr) {
    	 System.out.println("----Thread TCP Send created");
         TCPSend send=new TCPSend(file, portTCP,addr);
         send.start();
         System.out.println("file sended : "+file.getName());
    }
    void receiveFile(String file_name) throws IOException {
        System.out.println("----Thread TCP Receive created");
            
            TCPReceive receive=new TCPReceive(portTCP,file_name);
            
            receive.start();
    }
}