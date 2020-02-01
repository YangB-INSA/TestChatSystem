package Model.messages;

import Model.User;

public class UserList extends Message {
	private static final long serialVersionUID = 6L;

    public UserList(User localUser) {
		super(localUser);
	}
    
}
