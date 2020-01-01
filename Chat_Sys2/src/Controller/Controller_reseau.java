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
    
    public void showList(List<User> userList){
    	for (int i=0; i < userList.size(); i++) {
    		System.out.println(userList.get(i));
    	}
    }
    
    /**
     * check if the sender isn't us(cause broadcast we send are also sent to the localhost)
     * @param remoteAddr
     * @return
     */
    public boolean checkSender(String remoteAddr) {
    	
    	return (inter.getUser().getAddr().equals(remoteAddr)); 
    	
    }
    
    /* Method to manage received messages */

    public void messageHandle(Message m, String remoteAddr) throws IOException
    {
    	/**
    	 un broadcast envoie a tous les gens sur le r�seau, y compris nous meme
    	 * donc il faut verifier si la hostAddr du sender  n'est pas la notre
    	 */
    	
        if(!checkSender(remoteAddr)) {

        	if (m instanceof GetUserList) {
        		System.out.println("GetUserList re�u from " + m.getSender() +"\n");
        		if (inter.getUser().getNom()==null) {
        			System.out.println("not connected yet sry bru");
        		}
        		else {
        			sendOK(m.getSender());
        		}
        	}
        	
        	else if (m instanceof Connected) {
            	System.out.println("Connected re�u from " + m.getSender() +"\n");
            	inter.addUserInUserList(m.getSender());
            	
            }
            
            else if (m instanceof OK) {
            	System.out.println("OK re�u from " + m.getSender() +"\n");
            	inter.addUserInUserList(m.getSender());
            }
            
            else if (m instanceof Disconnected) {
            	System.out.println("Diconnected re�u from " + m.getSender() +"\n");
            	
            	if (!inter.removeInUserList(m.getSender())) {
            		System.out.println("cet utilisateur n'existe pas");
            	}
            	
            }
            
            /*si on recoit une telle notif, on cherche l'utilisateur dans notre liste
              qui porte cette ancien nom, et on le change par le nouveau */
            else if(m instanceof NameChanged) {
            	System.out.println("NameChanged re�u from " + m.getSender()+", " + m.getSender().getNom() + " > " + ((NameChanged)m).getOldname() +"\n");
                
            	if (!inter.changeNameInUserList(m.getSender(),((NameChanged)m).getOldname())) {
            		System.out.println("cet utilisateur n'existe pas");
            	}
            	
            }
            /*
            else if(m instanceof Start_rq) {
            	processor.processStar_rq((Start_rq) m);
            }
            */
        }
        
        /*
        //check if it's the right session (and it exist)
        if (inter.getUserList().getUser().isActif()) {
        	 
        	if(m instanceof MsgNormal) {
             	System.out.println("msgnormal re�u");
            }
    
        	
        }
        */
  
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
        System.out.println("Connected envoy� : Username = " + inter.getUser().getNom()+"\n");
    }
    
    /* Send the local User info to another user 
     * who requested it through notifications (GetUserlist and Connected)
     */
    public void sendOK(User receiver) {
        Message m = new OK(inter.getUser());
        client.sendTo(m,receiver.getAddr(),port);
        System.out.println("Ok envoy� � " + receiver.getNom()+"\n");
    }

    /* Send a broadcast msg to notify
     * other users that you are disconnected
     */
    public void sendDisconnected() {
        Message m = new Disconnected(inter.getUser());
        client.sendTo(m,broadcast.getHostAddress(),port);    
        System.out.println("Disconnected envoy� : Username = " + inter.getUser().getNom()+"\n");
    }

    /* Send a broadcast msg to notify
     * other users that you change your username
     */
    public void sendNameChanged(String oldname) {
    	Message m = new NameChanged(inter.getUser(),oldname);
        client.sendTo(m,broadcast.getHostAddress(),port); 
        System.out.println("NameChanged envoy� : Username = " + inter.getUser().getNom()+"\n");
    }
    
    public void sendStart_rq(User receiver) {
        Message m = new Start_rq(inter.getUser());
        client.sendTo(m,receiver.getAddr(),port); 
    }
    
    public void sendMsgNormal(String text, int id, String hostname) {
        Message m = new MsgNormal(inter.getUser(),text, id);
        client.sendTo(m,hostname,port); 
        System.out.println("Message envoy� : " + text + ", � " + hostname + " sur le port " + port+"\n");
    }
    
}
