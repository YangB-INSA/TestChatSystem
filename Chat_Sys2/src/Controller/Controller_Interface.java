package Controller;

import java.util.*;
import Controller.Controller_reseau;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import View.Application;
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
    
    public Controller_Interface() throws SocketException, UnknownHostException, InterruptedException {
    	
    	this.address = getHostAddress();
    	this.user = new User(address);
    	this.history = new History();
    	this.userList = new ArrayList<User>();
    	this.sessionList = new ArrayList<User>();
    	this.reseau = new Controller_reseau(this);
        
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
    
    public static String getAddress() {
    	return address;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
    
    public Controller_reseau getReseau( ){
    	return this.reseau;
    }
    
    public List<User> getUserList(){
    	return this.userList;
    }
    
    public List<User> getSessionList(){
    	return this.sessionList;
    }
    
    public void addtoSessionList(User session) {
    	this.sessionList.add(session);
    }
   
    public void setView(Application window) {
    	this.view= window;
    }
    
    public void addtoUserList(User user) {
    	this.userList.add(user);
    }
    
    /**
     * Check if username is already used
     * @param nom
     * @return true if it's already used, else false
     */
    public boolean checkUserUnicity(String nom ) {
		boolean isIn=false;
		for (int i=0; i < userList.size(); i++) {
			if(userList.get(i).getNom().equals(nom)) 
			{
				System.out.println("Username d�j� existant !");
				isIn=true;
			}
		}
		return isIn;
	}
    
    /**
     * check if a session is already opened with this user
     * @param user
     * @return true if opened, else false
     */
    public boolean checkSessionUnicity(User user ) {
		boolean isIn=false;
		for (int i=0; i < sessionList.size(); i++) {
			if(sessionList.get(i).getAddr().equals(user.getAddr())) 
			{
				isIn=true;
			}
		}
		return isIn;
	}
    
    
    /**
     * Add user to the userlist and the user JList, then update the GUI
     * @param utilisateur
     */
    public void addUserInUserList(User utilisateur ) {
    	if (!checkUserUnicity(utilisateur.getNom())) {
    		userList.add(utilisateur);
    		if (view != null && view instanceof Application) {
    			view.AddtoUserList(utilisateur);
    			view.UpdateListUI();
    		}
    	}
	}
    
    /**
     * Add user to the sessionlist and the session JList, update the GUI 
     * and show the card corresponding to this session
     * @param utilisateur
     */
    public void addUserInSessionList(User utilisateur ) {
    	if (!checkSessionUnicity(utilisateur)) {
    		sessionList.add(utilisateur);
    		view.AddtoSessionList(utilisateur);
    		view.UpdateListUI();
    		view.showLastCard();
    		view.setDefaultButton();
    	}
	}
    
    /**
     * remove user from the session list and session JList, then update the GUI
     * @param utilisateur
     */
    public void removeUserInSessionList(User utilisateur ) {
    	sessionList.remove(utilisateur);
    	view.RemoveFromSessionList(utilisateur);
    	view.UpdateListUI();
	}
    
    /**
     * remove user from the all lists, then update the GUI
     * @param utilisateur
     * @return true if it contained the element, else false
     */
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
    
    /**
     * search a particular user in the userlist
     * @param user
     * @return the username of the user 
     */
    public String searchInUserList(User user) {
    	String nickname="";
    	for (int i=0; i < userList.size(); i++) {
			if(userList.get(i).getAddr().equals(user.getAddr()))
			{
				nickname = userList.get(i).getNom();
			}
		}
    	return nickname;
    }
    
    /**
     * change the name of a particular user in both the userlist and sessionlist 
     * @param utilisateur
     * @return true if the name has been changed, else false
     */
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
    
    /**
     * find the hostaddress which is not loopback (not 127.0.0.1)
     * @return the localhost address but not 127.0.0.1
     */
    public static String getHostAddress() {
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
		  String[] addresses = HostAddresses.toArray(new String[1]); 
		  String host = addresses[0];
		  return host;
    }
}
    
  