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

import Model.Historique;
import Model.User;
public class Controller_Interface {
    private Controller_reseau reseau;
    private User user;
    private Historique history;
    private Application view;
    private ArrayList<String> historyList ; 
    private List<User> userList;
    private List<Session> sessionList;
    
    public Controller_Interface(User utilisateur) throws SocketException, InterruptedException, UnknownHostException {
    	
    	user = utilisateur;
    	userList = new ArrayList<User>();
    	sessionList = new ArrayList<Session>();
        reseau = new Controller_reseau(this, utilisateur);
        
  		/*
        userList.add(new User("bran","127.0.0.1"));
        userList.add(new User("bernard","127.0.0.1"));
        userList.add(new User("albert","127.0.0.1"));
        userList.add(new User("prout","127.0.0.1"));
        */
        
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
    
    public void setView(Application window) {
    	this.view= window;
    }
    
    public Controller_reseau getReseau( ){
    	return this.reseau;
    }
    
    public void addtoUserList(User user) {
    	this.userList.add(user);
    }
    
    public void addtoSessionList(Session session) {
    	this.sessionList.add(session);
    }
    
    public List<User> getUserList(){
    	return this.userList;
    }
    
    public List<Session> getSessionList(){
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
			if(sessionList.get(i).getUser().getNom().equals(nom)) //si le nom est déjà utilisé
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
    		sessionList.add(new Session(utilisateur));
    	}
	}
    
    public boolean removeInUserList (User utilisateur) {
    	boolean removed = false;
    	for (int i=0; i < userList.size(); i++) {
    		User user = userList.get(i);
			if (user.getNom().equals(utilisateur.getNom()) && user.getAddr().equals(utilisateur.getAddr())) {
				userList.remove(i);
				removed = true;
			}
		}
    	return removed;
    }
    
    public boolean changeNameInUserList(User utilisateur , String oldname) {
    	boolean nameChanged = false;
    	for (int i=0; i < userList.size(); i++) {
    		User user = userList.get(i);
			if (user.getNom().equals(oldname)) {
				user.setNom(utilisateur.getNom());
				nameChanged = true;
			}
		}
    	for (int i=0; i < sessionList.size(); i++) {
    		User user = sessionList.get(i).getUser();
			if (user.getNom().equals(oldname)) {
				user.setNom(utilisateur.getNom());
				nameChanged = true;
			}
		}
    	return nameChanged;
    	
    }
    
    /* Methods to process different message types */
    
    public void processConnected(Message m) {
    	addUserInUserList(m.getSender());
    	//sendOKmessage
    }
    
    public void processDisconnected(Message m) {
    	if (!removeInUserList(m.getSender())) {
    		System.out.println("cet utilisateur n'existe pas");
    	}
    }
    
    public void processNameChanged(Message m) {
    	if (!changeNameInUserList(m.getSender(),((NameChanged)m).getOldname())) {
    		System.out.println("cet utilisateur n'existe pas");
    	}
    }
    
    public void processMsgNormal(MsgNormal m) {
    	System.out.println("msgnormal reçu");
    }
    
    
    public void processStar_rq(Message m) {
    	/* checker les sessions precedentes : si dÃ©jÃ  dÃ©marrÃ© une session avec cette user, start session avec historique
    	 * sinon start une nouvelle session et ajouter la session Ã  liste de sessions
    	 
    	if(session.CheckSession(m.getSender())==true) {
    		historyList=history.getHistory(m.getSender().getAddr());
    	}
    	else{
    		history.createConvHistoryFile(m.getSender().getAddr());
    	}; 
    	*/
    	
    }
    
}
    
  