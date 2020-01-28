package Controller;


import Model.reseau.UDPReceiver;
import Model.reseau.UDPSender;
import View.Application;
import View.ChatCard;

import java.awt.Component;
import java.awt.Desktop;

import Model.reseau.TCPClient;
import Model.reseau.TCPServer;
import TCPFile.TCPReceive;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.FileSystems;
import javax.swing.JPanel;

import java.net.DatagramPacket;
import java.net.InetAddress;
import Model.messages.*;
import Model.History;
import Model.User;

/**
 * Manage sending messages & processing messages received
 */
public class Controller_reseau {
	
	private Controller_Interface inter;
	private User user;
    private UDPSender client;
    private UDPReceiver server;
    private InetAddress broadcast;
    private int port;
    private final int portTCP=3300;
    
    public Controller_reseau(Controller_Interface p) throws SocketException, InterruptedException, UnknownHostException {
        this.inter = p;
        this.user = p.getUser();
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
    
    public Controller_Interface getInterface() {
    	return this.inter;
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
    	 un broadcast envoie a tous les gens sur le r�seau, y compris nous meme
    	 * donc il faut verifier si la hostAddr du sender  n'est pas la notre
    	 */
    	
        if(!checkSender(remoteAddr)) {

        	if (m instanceof GetUserList) {
        		System.out.println("GetUserList re�u from " + m.getSender() +"\n");
        		if (inter.getUser().getNom().equals("noname")) {
        			System.out.println("not connected yet sry bru");
        		}
        		else {
        			sendOK(m.getSender());
        		}
        	}
        	
        	else if (m instanceof Connected) {
            	System.out.println("Connected re�u from " + m.getSender() +"\n");
            	inter.addUserInUserList(m.getSender());
            	
            	if (inter.getView() != null) {
            		inter.getView().UpdateListUI();
            	}     	
            }
            
            else if (m instanceof OK) {
            	System.out.println("OK re�u from " + m.getSender() +"\n");
            	inter.addUserInUserList(m.getSender());
            }
            
            else if (m instanceof Disconnected) {
            	System.out.println("Disconnected re�u from " + m.getSender() +"\n");
            	// on le supprime de la user list + si on a ouvert une session on la ferme 
            	if (!inter.removeInUserList(m.getSender())) {
            		System.out.println("cet utilisateur n'existe pas");
            	}      
            }
                     
            else if (m instanceof NameChanged) {
            	System.out.println("NameChanged re�u from " + m.getSender());
                String oldname = inter.searchInUserList(m.getSender());
            	if (!inter.changeNameInUserList(m.getSender())) {
            		System.out.println("cet utilisateur n'existe pas");
            	}
            	
            	if (inter.getView() != null && inter.getView() instanceof Application && inter.checkSessionUnicity(m.getSender())) {
            		getAccordingCard(m.getSender()).setNameChanged(oldname,m.getSender().getNom());
            	}
          
            }
            
            else if (m instanceof Start_rq) {
            	System.out.println("Start_rq re�u from " + m.getSender() +"\n");
            	inter.addUserInSessionList(m.getSender());
            	
            	//affiche l'historique dans le chat
            	ArrayList<String> messageList = inter.getHistory().generateHistory(m.getSender().getAddr());
            	ChatCard card = getAccordingCard(m.getSender());
            	for (int i=0 ; i< messageList.size() ; i++) {
            		card.setMessage(messageList.get(i), messageList.get(i+1), messageList.get(i+2));
            		i=i+2;
            	}
            }
        	
            else if (m instanceof Stop_rq) {
            	System.out.println("Stop_rq re�u from " + m.getSender() +"\n");
            	inter.removeUserInSessionList(m.getSender());
            }
        	
            else if (m instanceof FileRequest) {
            	System.out.println("FileRequest re�u from " + m.getSender() +"\n");
            	System.out.println("D�marrage de l'�coute pour recevoir le fichier"+"\n");
            	this.receiveFile(((FileRequest) m).getFileName());
            	User sender = ((FileRequest)m).getSender();
            	JPanel chatPanel = inter.getView().getChatPanel();
            	
            	for (Component comp : chatPanel.getComponents()) {
        
            	    if (comp instanceof ChatCard && ((ChatCard)comp).getReceiver().equals(sender.getAddr())) {
            	    	((ChatCard)comp).setMessageFile(sender.getNom(),((FileRequest) m).getFileName(), ((FileRequest)m).getDate());
            	    	try {
							Thread.sleep(2 * 1000); // Pause de 2 secs sinon �a ouvre direct le fichier
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
            	    
            	    	Path FilePath = FileSystems.getDefault().getPath("");
            	    	File myFile = FilePath.resolve(((FileRequest) m).getFileName()).toFile();
            	    	System.out.println("fichier resolve : " +myFile.getName());
            	    	if (myFile.exists()) {
            	    		System.out.println("file" + ((FileRequest) m).getFileName() + "existe !" + "\n");
            	    		Desktop.getDesktop().open(myFile);
            	    	}
            	    }
            	}
            }
        	
            else if(m instanceof MsgNormal) {
        
            	System.out.println("msgnormal re�u par "+ ((MsgNormal)m).getSender() + " : " + ((MsgNormal)m).getMessage());
            	MsgNormal message = (MsgNormal)m;
            	User sender = message.getSender();
            	History.addToHistory(m.getSender().getAddr(), message);
            	getAccordingCard(sender).setMessage(sender.getNom(),message.getMessage(), message.getDate());
            	
            }
            
        }

        
     }
        
    //get the card corresponding to a certain address
    public ChatCard getAccordingCard(User sender) {
    	ChatCard card = null;
    	JPanel chatPanel = inter.getView().getChatPanel();
    	for (Component comp : chatPanel.getComponents()) {            	
    	    if (comp instanceof ChatCard && ((ChatCard)comp).getReceiver().equals(sender.getAddr())) {
    	    	card = ((ChatCard)comp);
    	    }
    	}
    	return card;
    	
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
    
    public void sendFileRequest (String receiver, String FileName, String date, File myFile) {
    	Message m = new FileRequest(inter.getUser(), FileName, date, myFile);
    	client.sendTo(m,receiver,port);
        System.out.println("Notification d'envois de fichier envoy� � " + inter.getUser().getNom()+"\n");
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
    public void sendNameChanged() {
    	Message m = new NameChanged(inter.getUser());
        client.sendTo(m,broadcast.getHostAddress(),port); 
        System.out.println("NameChanged envoy� : Username = " + inter.getUser().getNom()+"\n");
    }
    
    public void sendStart_rq(User receiver) {
        Message m = new Start_rq(inter.getUser());
        client.sendTo(m,receiver.getAddr(),port); 
        System.out.println("Start_rq envoy� vers " + receiver.getNom()+"\n");
        
        ArrayList<String> messageList = inter.getHistory().generateHistory(receiver.getAddr());
    	ChatCard card = getAccordingCard(receiver);
    	System.out.println(card);
    	System.out.println(messageList);
    	for (int i=0 ; i< messageList.size() ; i++) {	
    		card.setMessage(messageList.get(i), messageList.get(i+1), messageList.get(i+2)); 		
    		i=i+2;
    	}
    }
    
    public void sendStop_rq(User receiver) {
        Message m = new Stop_rq(inter.getUser());
        client.sendTo(m,receiver.getAddr(),port); 
        System.out.println("Stop_rq envoy� vers " + receiver.getNom()+"\n");
    }
    
    public void sendMsgNormal(String receiver, String msg, String date) {   	
        Message m = new MsgNormal(inter.getUser(),msg,date);     
        client.sendTo(m,receiver,port);         
        History.addToHistory(receiver,(MsgNormal)m);      
        System.out.println("Message envoy� � " + inter.getUser().getNom() + " : " + msg + ", � " + receiver + " sur le port " + port+"\n");
    }
    
    public void sendFile (File file, String addr) {
    	 System.out.println("----Thread TCP Send created");
         TCPClient send=new TCPClient(file, portTCP,addr);
         send.start();
         System.out.println("file sended : "+file.getName());
    }
    
    public void receiveFile(String file_name) throws IOException {
        System.out.println("----Thread TCP Receive created");        
        TCPServer receive=new TCPServer(portTCP,file_name);       
        receive.start();
    }
}