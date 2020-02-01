package Model.messages;

import Model.User;

public class Disconnected extends Message{
		private static final long serialVersionUID = 3L;

	    public Disconnected(User sender) {
			super(sender);
		}


}
