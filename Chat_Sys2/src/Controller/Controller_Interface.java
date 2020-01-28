package Controller;

import java.util.*;
import Controller.Controller_reseau;
import java.awt.EventQueue;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import Model.messages.*;
import View.Application;
import View.Login;
import Model.Session;
import Model.History;
import Model.User;

public class Controller_Interface {
	
    private Controller_reseau reseau;
    private User user;
    private History history;
    private Application view; 
    private List<User> userList;
    private List<User> sessionList;
    private static String address;
    
    public Controller_Interface() throws SocketException, InterruptedException, UnknownHostException {
    	
    	address = getHostAddress();
    	user = new User(address);
    	history = new History();
    	userList = new ArrayList<User>();
    	sessionList = new ArrayList<User>();
        reseau = new Controller_reseau(this);
           
        userList.add(new User("bernard","192.10.2.5"));
        userList.add(new User("albert","195.12.5.54"));
        userList.add(new User("prout","241.0.2.1"));
        
  
    }
    

    /* Method */ 
    
    public User getUser() {
    	return this.user;
    }
    
    
    public Application getView( ) {
    	return this.view;
    }
    
    public History getHistory() {
    	return this.history;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
    public void setView(Application window) {
    	this.view= window;
    }
    
    public static String getAddress() {
    	return address;
    }
    
    public Controller_reseau getReseau( ){
    	return this.reseau;
    }
    
    public void addtoUserList(User user) {
    	this.userList.add(user);
    }
    
    public void addtoSessionList(User session) {
    	this.sessionList.add(session);
    }
    
    public List<User> getUserList(){
    	return this.userList;
    }
    
    public List<User> getSessionList(){
    	return this.sessionList;
    }
    
    //check if username is already used
    public boolean checkUserUnicity(String nom ) {
		boolean isIn=false;
		for (int i=0; i < userList.size(); i++) {
			if(userList.get(i).getNom().equals(nom)) //si le nom est d�j� utilis�
			{
				System.out.println("Username d�j� existant !");
				isIn=true;
			}
		}
		return isIn;
	}
    
    public boolean checkSessionUnicity(User user ) {
		boolean isIn=false;
		for (int i=0; i < sessionList.size(); i++) {
			if(sessionList.get(i).getAddr().equals(user.getAddr())) //si le nom est d�j� utilis�
			{
				isIn=true;
			}
		}
		return isIn;
	}
    
    //check if the user who notified us is already in our userlist, if not then add it 
    public void addUserInUserList(User utilisateur ) {
    	if (!checkUserUnicity(utilisateur.getNom())) {
    		userList.add(utilisateur);
    		if (view != null && view instanceof Application) {
    			view.AddtoUserList(utilisateur);
    			view.UpdateListUI();
    		}
    	}
	}
    
    public void addUserInSessionList(User utilisateur ) {
    	if (!checkSessionUnicity(utilisateur)) {
    		sessionList.add(utilisateur);
    		view.AddtoSessionList(utilisateur);
    		view.UpdateListUI();
    		view.showLastCard();
    		view.setDefaultButton();
    	}
	}
    
    public void removeUserInSessionList(User utilisateur ) {
    	sessionList.remove(utilisateur);
    	view.RemoveFromSessionList(utilisateur);
    	view.UpdateListUI();
	}
    
    public boolean removeInUserList (User utilisateur) {
    	
    	boolean removed = userList.remove(utilisateur);
    	if (view != null) {
    		view.RemoveFromUserList(utilisateur);
    		if (sessionList.remove(utilisateur)) {
    			view.RemoveFromSessionList(utilisateur);
    			view.UpdateListUI();
    		}
    	}     	
    	return removed;
    }
    
    public String searchInUserList(User user) {
    	String nickname="";
    	for (int i=0; i < userList.size(); i++) {
			if(userList.get(i).getAddr().equals(user.getAddr())) //si le nom est d�j� utilis�
			{
				nickname = userList.get(i).getNom();
			}
		}
    	return nickname;
    }
    
    public boolean changeNameInUserList(User utilisateur) {
    	boolean nameChanged = false;
    	for (int i=0; i < userList.size(); i++) {
    		User user = userList.get(i);
			if (user.getAddr().equals(utilisateur.getAddr())) {
				user.setNom(utilisateur.getNom());
				nameChanged = true;
			}
		}
    	for (int i=0; i < sessionList.size(); i++) {
    		User user = sessionList.get(i);
			if (user.getAddr().equals(utilisateur.getAddr())) {
				user.setNom(utilisateur.getNom());
				nameChanged = true;
			}
		}
   	
    	if (view != null) {
    		view.UpdateListUI();
    	}    
    	
    	return nameChanged;   
    }
    
    public static String getHostAddress() {
    	System.out.println("debut");
		  Set<String> HostAddresses = new HashSet<>();
		  try {
		    for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
		      if (!ni.isLoopback() && ni.isUp() && ni.getHardwareAddress() != null) {
		        for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
		          if (ia.getBroadcast() != null) {  //If limited to IPV4
		            HostAddresses.add(ia.getAddress().getHostAddress());
		          }
		        }
		      }
		    }
		  } catch (SocketException e) { }
		  System.out.println("fin");
		  String[] addresses = HostAddresses.toArray(new String[0]); 
		  String host = addresses[0];
		  return host;
    }
}
    
  