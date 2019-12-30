package Model.messages;

import Model.User;

public class NameChanged extends Message{
	private static final long serialVersionUID = 4L;
	private String oldname;
	static User sender;
   

    public NameChanged(User sender, String oldname) {
		super(sender);
		this.oldname=oldname;
	}
    
	public String getOldname(){
		return this.oldname;
	}

	
}
