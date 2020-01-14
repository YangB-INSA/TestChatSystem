package Controller;


import Model.reseau.UDPReceiver;
import Model.reseau.UDPSender;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
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
    
    public void showSessionList(List<Session> list){
    	for (int i=0; i < list.size(); i++) {
    		System.out.println(list.get(i).getUser());
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
    	List<Session> sessionlist=inter.getSessionList();
    	for (int i=0;i<sessionlist.size();i++) {
    		if (sessionlist.get(i).getUser().getAddr().equals(addr)) {
    			System.out.println("Session opened with " + sessionlist.get(i).getUser().getAddr());
    		    isOpened = true;
    		}
    	}
    	return isOpened;
    }
    
    /* Method to manage received messages */

    public void messageHandle(Message m, String remoteAddr) throws IOException
    {
    	/**
    	 un broadcast envoie a tous les gens sur le réseau, y compris nous meme
    	 * donc il faut verifier si la hostAddr du sender  n'est pas la notre
    	 */
    	
        if(!checkSender(remoteAddr)) {

        	if (m instanceof GetUserList) {
        		System.out.println("GetUserList reçu from " + m.getSender() +"\n");
        		if (inter.getUser().getNom().equals("noname")) {
        			System.out.println("not connected yet sry bru");
        		}
        		else {
        			sendOK(m.getSender());
        		}
        	}
        	
        	else if (m instanceof Connected) {
            	System.out.println("Connected reçu from " + m.getSender() +"\n");
            	inter.addUserInUserList(m.getSender());
            	
            	inter.getView().UpdateUserList();
            	
            }
            
            else if (m instanceof OK) {
            	System.out.println("OK reçu from " + m.getSender() +"\n");
            	inter.addUserInUserList(m.getSender());
            }
            
            else if (m instanceof Disconnected) {
            	System.out.println("Disconnected reçu from " + m.getSender() +"\n");
            	
            	if (!inter.removeInUserList(m.getSender())) {
            		System.out.println("cet utilisateur n'existe pas");
            	}          
            	/* 
            	if (inter.getView() =! null ) { //check if on a deja affiché la GUI
            
            		inter.getView().UpdateUserList();
            	}
            	
            	*/
            }
            
            /*si on recoit une telle notif, on cherche l'utilisateur dans notre liste
              qui porte cette ancien nom, et on le change par le nouveau */
            else if (m instanceof NameChanged) {
            	System.out.println("NameChanged reçu from " + m.getSender()+", " + m.getSender().getNom() + " > " + ((NameChanged)m).getOldname() +"\n");
                
            	if (!inter.changeNameInUserList(m.getSender(),((NameChanged)m).getOldname())) {
            		System.out.println("cet utilisateur n'existe pas");
            	}
            	//check si l'application est lancée
            	inter.getView().UpdateUserList();
            }
            
            else if (m instanceof Start_rq) {
            	System.out.println("Start_rq reçu from " + m.getSender() +"\n");
            	inter.addUserInSessionList(m.getSender());
            	inter.getView().UpdateSessionList();
            }
            
        }
        
        
        //check if there's a session opened with the sender of this message
        if (checkSession(m.getSender().getAddr())) {
        	 
        	if(m instanceof MsgNormal) {
             	System.out.println("msgnormal reçu par "+ m.getSender() + " : " + m.getText());
            }
    
        }
        
  
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
        System.out.println("Connected envoyé : Username = " + inter.getUser().getNom()+"\n");
    }
    
    /* Send the local User info to another user 
     * who requested it through notifications (GetUserlist and Connected)
     */
    public void sendOK(User receiver) {
        Message m = new OK(inter.getUser());
        client.sendTo(m,receiver.getAddr(),port);
        System.out.println("Ok envoyé à " + receiver.getNom()+"\n");
    }

    /* Send a broadcast msg to notify
     * other users that you are disconnected
     */
    public void sendDisconnected() {
        Message m = new Disconnected(inter.getUser());
        client.sendTo(m,broadcast.getHostAddress(),port);    
        System.out.println("Disconnected envoyé : Username = " + inter.getUser().getNom()+"\n");
    }

    /* Send a broadcast msg to notify
     * other users that you change your username
     */
    public void sendNameChanged(String oldname) {
    	Message m = new NameChanged(inter.getUser(),oldname);
        client.sendTo(m,broadcast.getHostAddress(),port); 
        System.out.println("NameChanged envoyé : Username = " + inter.getUser().getNom()+"\n");
    }
    
    public void sendStart_rq(User receiver) {
        Message m = new Start_rq(inter.getUser());
        client.sendTo(m,receiver.getAddr(),port); 
        System.out.println("Start_rq envoyé vers " + receiver.getNom()+"\n");
    }
    
    public void sendMsgNormal(String text, int id, String hostname) {
        Message m = new MsgNormal(inter.getUser(),text, id);
        client.sendTo(m,hostname,port); 
        System.out.println("Message envoyé : " + text + ", à " + hostname + " sur le port " + port+"\n");
    }
    
}
