package Model.messages;

import Model.User;

public class Disconnected extends Message{
		private static final long serialVersionUID = 3L;
		static User sender;
	   

	    public Disconnected(User sender) {
			super(sender);
		}


}
