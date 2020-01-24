package Controller;

import java.util.*;
import Controller.Controller_reseau;
import java.awt.EventQueue;
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
    private ArrayList<String> historyList ; 
    private List<User> userList;
    private List<User> sessionList;
    
    public Controller_Interface(User utilisateur) throws SocketException, InterruptedException, UnknownHostException {
    	
    	user = utilisateur;
    	history = new History();
    	userList = new ArrayList<User>();
    	sessionList = new ArrayList<User>();
        reseau = new Controller_reseau(this, utilisateur);
           
       /* userList.add(new User("bernard","127.0.0.1"));
        userList.add(new User("albert","127.0.0.1"));
        userList.add(new User("prout","127.0.0.1"));*/
        
        
        /*
        sessionList.add(new Session(new User("prout","127.0.0.1")));
        sessionList.add(new Session(new User("kk","127.0.0.1")));
        sessionList.add(new Session(new User("pipi","127.0.0.1")));
        */
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
    public void setView(Application window) {
    	this.view= window;
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
			if(userList.get(i).getNom().equals(nom)) //si le nom est déjà utilisé
			{
				System.out.println("Username déjà  existant !");
				isIn=true;
			}
		}
		return isIn;
	}
    
    public boolean checkSessionUnicity(String nom ) {
		boolean isIn=false;
		for (int i=0; i < sessionList.size(); i++) {
			if(sessionList.get(i).getNom().equals(nom)) //si le nom est déjà utilisé
			{
				System.out.println("Session deja ouvert!");
				isIn=true;
			}
		}
		return isIn;
	}
    
    //check if the user who notified us is already in our userlist, if not then add it 
    public void addUserInUserList(User utilisateur ) {
    	if (!checkUserUnicity(utilisateur.getNom())) {
    		userList.add(utilisateur);
    	}
	}
    
    public void addUserInSessionList(User utilisateur ) {
    	if (!checkSessionUnicity(utilisateur.getNom())) {
    		sessionList.add(utilisateur);
    		view.AddtoSessionList(utilisateur);
    		view.showLastCard();
    		view.setDefaultButton();
    	}
	}
    
    public void removeUserInSessionList(User utilisateur ) {
    	sessionList.remove(utilisateur);
    	view.RemoveFromSessionList(utilisateur);
	}
    
    public boolean removeInUserList (User utilisateur) {
    	
    	boolean removed = userList.remove(utilisateur);
    	if (view != null) {
    		view.RemoveFromUserList(utilisateur);
    		if (sessionList.remove(utilisateur)) {
    			view.RemoveFromSessionList(utilisateur);
    		}
    	}    
    	
    	return removed;
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
    		view.UpdateUserList();
    	}    
    	
    	return nameChanged;
    	
    }
}
    
  