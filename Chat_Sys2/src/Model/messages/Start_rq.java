package Model.messages;

import Model.User;

public class Start_rq extends Message {

	private static final long serialVersionUID = 5L;

	private String nickname;
	static User sender;
    static String text;

	public String getNickname(){
		return this.nickname;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public Start_rq(String nickname) {
		super(sender, text);
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "Connexion_rq [nickname=" + sender.getNom() + sender.getAddr() + "]";
	}
	
}
