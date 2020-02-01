package Model.messages;

import Model.User;

public class GetUserList extends Message {
	private static final long serialVersionUID = 7L;
   
    public GetUserList(User sender) {
		super(sender);
	}
   
}