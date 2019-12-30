package Controller;

import Controller.Controleur_Processor;


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

public class Controller_reseau {
	User user;
	Controleur_Processor processor;
    
    private UDPSender client;
    private UDPReceiver server;
    private InetAddress broadcast;
    private boolean actif;
    private int port;
    
    public Controller_reseau(Controleur_Processor p, User utilisateur) throws SocketException, InterruptedException, UnknownHostException {
        this.processor = p;
        this.user= utilisateur;
        this.port = user.getPort();
        broadcast = InetAddress.getByName("255.255.255.255"); //broadcast
        actif = true;
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
    public boolean isActiv(){
        return this.actif;
    }
    
    public void setActiv(boolean b){
        this.actif=b;
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
    	
    	return (processor.getUser().getAddr().equals(remoteAddr)); 
    	
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
        		if (processor.getUser().getNom()==null) {
        			System.out.println("not connected yet sry bru");
        		}
        		else {
        			sendOK(m.getSender());
        		}
        	}
        	
        	else if (m instanceof Connected) {
            	System.out.println("Connected reçu from " + m.getSender() +"\n");
            	processor.addUserInUserList(m.getSender());
            	showList(processor.getUserList());
            	if (processor.getUser().getNom()==null) {
        			System.out.println("not connected yet sry bru");
        		}
        		else {
        			sendOK(m.getSender());
        		}
            }
            
            else if (m instanceof OK) {
            	System.out.println("OK reçu from " + m.getSender() +"\n");
            	processor.addUserInUserList(m.getSender());
            }
            
            else if (m instanceof Disconnected) {
            	System.out.println("Diconnected reçu from " + m.getSender() +"\n");
            	
            	if (!processor.removeInUserList(m.getSender())) {
            		System.out.println("cet utilisateur n'existe pas");
            	}
            	showList(processor.getUserList());
            }
            
            /*si on recoit une telle notif, on cherche l'utilisateur dans notre liste
              qui porte cette ancien nom, et on le change par le nouveau */
            else if(m instanceof NameChanged) {
            	System.out.println("NameChanged reçu from " + m.getSender() +"\n");
                
            	if (!processor.changeNameInUserList(m.getSender(),((NameChanged)m).getOldname())) {
            		System.out.println("cet utilisateur n'existe pas");
            	}
            	showList(processor.getUserList());
            }
            
            else if(m instanceof MsgNormal) {
            	System.out.println("msgnormal reçu");
            }
   
            /*
            else if(m instanceof Start_rq) {
            	processor.processStar_rq((Start_rq) m);
            }
            */
        }
  
    }
    
    /* Methods to send every message types*/
    
    
   /* Send a broadcast msg to get 
    * the userlist before logging in 
    */
    public void getUserList() {
    	Message m = new GetUserList(processor.getUser());
    	client.sendTo(m,broadcast.getHostAddress(), port);
    	System.out.println("broadcast sent to get users connected"+"\n");
    }
    
    /* Send a broadcast msg to notify
     * other users that you are connected
     */
    public void sendConnected() {
        Message m = new Connected(processor.getUser());
        client.sendTo(m,broadcast.getHostAddress(),port);
        System.out.println("Connected envoyé : Username = " + processor.getUser().getNom()+"\n");
    }
    
    /* Send the local User info to another user 
     * who requested it through notifications (GetUserlist and Connected)
     */
    public void sendOK(User receiver) {
        Message m = new OK(processor.getUser());
        client.sendTo(m,receiver.getAddr(),port);
        System.out.println("Ok envoyé à " + receiver.getNom()+"\n");
    }

    /* Send a broadcast msg to notify
     * other users that you are disconnected
     */
    public void sendDisconnected() {
        Message m = new Disconnected(processor.getUser());
        client.sendTo(m,broadcast.getHostAddress(),port);    
    }

    /* Send a broadcast msg to notify
     * other users that you change your username
     */
    public void sendNameChanged(String oldname) {
    	Message m = new NameChanged(processor.getUser(),oldname);
        client.sendTo(m,broadcast.getHostAddress(),port); 
    }
    
    public void sendStart_rq(String nickname,String hostname) {
        Message m = new Start_rq(nickname);
        client.sendTo(m,hostname,port); 
    }
    
    public void sendMsgNormal(String text, int id, String hostname) {
        Message m = new MsgNormal(processor.getUser(),text, id);
        client.sendTo(m,hostname,port); 
        System.out.println("Message envoyé : " + text + ", à " + hostname + " sur le port " + port+"\n");
    }
    
}
