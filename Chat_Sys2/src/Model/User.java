package Model;
import java.io.Serializable;

public class User implements Serializable{
	
	private String username;
    private String hostAddr; 
    private int port;

    public final static int portUDP = 52425;
    
    public User(String hostAddr) {
    	this.username="noname";
        this.hostAddr=hostAddr;
        this.port = portUDP;
    }
    
    public User(String nom, String addr){    
        this.username=nom;
        this.hostAddr=addr;
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
    	return this.hostAddr;
    }
       
    public void setNom(String name){ 
    	this.username = name;
    }
    
    public void setAddr(String hostaddr) { 
    	this.hostAddr=hostaddr;
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
            return ((this.hostAddr.equals( u.getAddr() ) ) );
        else
            return false;
    }
   
}
