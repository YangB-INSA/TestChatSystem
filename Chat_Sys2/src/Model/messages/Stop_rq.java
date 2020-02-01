package Model.messages;

import Model.User;

public class Stop_rq extends Message {
	private static final long serialVersionUID = 5L;
	
	public Stop_rq(User sender) {
		super(sender);
	}
}