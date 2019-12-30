package Model;
import java.io.Serializable;
import java.util.*;

public class User implements Serializable{
	private String nom;
    private String hostaddr; //constant
    private int port;

    public final static int portUDP = 52425;
    
    /* constructor */
    
    public User(String host) {  
        this.hostaddr=host;
        this.port = portUDP;
    }
    
    public User(String nom, String addr){    
        this.nom=nom;
        this.hostaddr=addr;
        this.port = portUDP;
    }
    
    /* method */
    
    public int getPort() {
    	return this.port;
    }
    public String getNom(){ 
    	return this.nom;
    }
    
    public void setNom(String name){ 
    	this.nom = name;
    }
    
    public String getAddr(){ 
    	return this.hostaddr;
    }
    
    public void setAddr(String hostaddr) { 
    	this.hostaddr=hostaddr;
    }
    
    @Override
    public String toString()
    {
        return this.getNom()+" : "+this.getAddr();
    }
    
    
    @Override
    public boolean equals(Object o)
    {
        User u = null;
        if (o != null && o instanceof User){
            u = (User) o;
        }
        
        if(u != null)
            return ((this.hostaddr.equals( u.getAddr() ) ) );
        else
            return false;
    }
    /*
    //check if the user who notified us is already in our userlist, if not then add it 
    public void checkUserInUserList(User utilisateur ) {
		int username_ok=0;
		while(username_ok==0) {
			for (int i=0; i < Userlist.size(); i++) {
				if(Userlist.get(i) == utilisateur)
				{
					System.out.println("Username déjà existant !");
				}
				else {
					Userlist.add(utilisateur);
					username_ok=1;
				}
			}
		}
	}
   
    */
 
    
    	 
    	  

}
