package test;
import Model.User;
public class Connected extends Message {
	private static final long serialVersionUID = 2L;

	private String nickname;
	static User sender;
    static String text;

    public Connected(String text, String nickname) {
		super(text);
		this.nickname = nickname;
	}
    
	public String getNickname(){
		return this.nickname;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "Connected [nickname=" + sender.getNom() + sender.getAddr() + "]";
	}
	
	


}

