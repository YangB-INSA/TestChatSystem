package Model.messages;

import Model.User;
import java.io.Serializable;

public class Message implements Serializable {
	    private User sender;
	    private String text;
	    private static final long serialVersionUID = 1L;
	    
	    public Message(User sender) {
	    	this.sender =sender;
	    }
	    
	    public Message(User sender, String text){
	        this.sender = sender;
	        this.text = text;
	    }
	      
	    public User getSender(){ 
	    	return this.sender;
	    }
	    public String getText(){ 
	    	return this.text;
	    }


}

