package Model.messages;

import Model.User;

public class Start_rq extends Message {

	private static final long serialVersionUID = 5L;

	static User sender;

	public Start_rq(User sender) {
		super(sender);
		
	}

	@Override
	public String toString() {
		return "Connexion_rq [nickname=" + sender.getNom() + sender.getAddr() + "]";
	}
	
}
