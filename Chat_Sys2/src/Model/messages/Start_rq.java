package Model.messages;

import Model.User;

public class Start_rq extends Message {
	private static final long serialVersionUID = 5L;

	public Start_rq(User sender) {
		super(sender);
	}
}