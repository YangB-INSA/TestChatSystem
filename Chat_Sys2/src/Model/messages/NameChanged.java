package Model.messages;

import Model.User;

public class NameChanged extends Message{
	private static final long serialVersionUID = 4L;

	static User sender;
   

    public NameChanged(User sender) {
		super(sender);
	}
	
}
