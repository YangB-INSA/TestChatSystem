package Controller;
import java.util.*;


import Controller.Controller_reseau;
import java.net.SocketException;
import java.net.UnknownHostException;
import Model.messages.*;
import Model.Session;

import Model.Historique;
import Model.User;
public class Controleur_Processor {
    private Controller_reseau reseau;
    User user;
    Session session;
    Historique history;
    ArrayList<String> historyList ; 
    private List<User> userList;
    private List<User> sessionList;
    
    public Controleur_Processor(User utilisateur) throws SocketException, InterruptedException, UnknownHostException {
    
    	userList = new ArrayList<User>();
        this.reseau = new Controller_reseau(this, utilisateur);
        this.user = utilisateur;
        
       //userlist test
        userList.add(new User("bran","127.0.0.1"));
        userList.add(new User("bernard","127.0.0.1"));
        userList.add(new User("albert","127.0.0.1"));
        userList.add(new User("prout","127.0.0.1"));
        userList.add(new User("cobra","127.0.0.1"));
    }

    /* Method */ 
    
    public User getUser() {
    	return this.user;
    }
    
    public Controller_reseau getReseau( ){
    	return this.reseau;
    }
    
    public void addtoUserList(User user) {
    	this.userList.add(user);
    }
    
    public List<User> getUserList(){
    	return this.userList;
    }
    
    //check if username is already used
    public boolean checkUnicity(String nom ) {
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
    
    //check if the user who notified us is already in our userlist, if not then add it 
    public void addUserInUserList(User utilisateur ) {
    	if (!checkUnicity(utilisateur.getNom())) {
    		userList.add(utilisateur);
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
    
  