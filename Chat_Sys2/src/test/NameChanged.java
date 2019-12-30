package test;

import Model.User;

public class NameChanged extends Message{
	private static final long serialVersionUID = 4L;
	private String nickname;
	static User sender;
    static String text;

	public String getNickname(){
		return this.nickname;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public NameChanged(String nickname) {
		super(text);
		this.nickname = nickname;
	}

	public String toString() {
		return "NameChanged [nickname=" + nickname + sender.getNom() + "]";
	}

}
