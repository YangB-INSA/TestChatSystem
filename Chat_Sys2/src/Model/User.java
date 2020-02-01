package Model;
import java.io.Serializable;

public class User implements Serializable{
	
	private String username;
    private String hostaddr; 
    private int port;

    public final static int portUDP = 52425;
    
    public User(String hostAddr) {
    	this.username="noname";
        this.hostaddr=hostAddr;
        this.port = portUDP;
    }
    
    public User(String nom, String addr){    
        this.username=nom;
        this.hostaddr=addr;
        this.port = portUDP;
    }
    
    /* method */
    
    public int getPort() {
    	return this.port;
    }
    
    public String getNom(){ 
    	return this.username;
    }
    
    public String getAddr(){ 
    	return this.hostaddr;
    }
       
    public void setNom(String name){ 
    	this.username = name;
    }
    
    public void setAddr(String hostaddr) { 
    	this.hostaddr=hostaddr;
    }
    
    @Override
    public String toString()
    {
        return this.getNom();
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
