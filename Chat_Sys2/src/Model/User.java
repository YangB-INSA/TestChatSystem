package Model;
import java.io.Serializable;
import java.util.*;

public class User implements Serializable{
	private String nom;
    private String hostaddr; //constant
    private boolean actif; //session active if true
    private int port;

    public final static int portUDP = 52425;
    
    /* constructor */
    
    public User(String hostAddr) {
    	this.nom="noname";
        this.hostaddr=hostAddr;
        this.port = portUDP;
        this.actif = false;
    }
    
    public User(String nom, String addr){    
        this.nom=nom;
        this.hostaddr=addr;
        this.port = portUDP;
        this.actif = false;
    }
    
    /* method */
    
    public int getPort() {
    	return this.port;
    }
    
    public String getNom(){ 
    	return this.nom;
    }
    
    public String getAddr(){ 
    	return this.hostaddr;
    }
    
    public boolean isActif() {
    	return this.actif;
    }
    
    public void setNom(String name){ 
    	this.nom = name;
    }
    
    public void setActif() {
    	this.actif =true;
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
   
}
