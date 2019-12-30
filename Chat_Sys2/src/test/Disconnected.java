package test;

import Model.User;

public class Disconnected extends Message{
		private static final long serialVersionUID = 3L;
		private String nickname;
		static User sender;
	    static String text;

	    public Disconnected(String text, String nickname) {
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
			return "Disconnected [nickname=" + sender.getNom() + sender.getAddr() + "]";
		}

}
