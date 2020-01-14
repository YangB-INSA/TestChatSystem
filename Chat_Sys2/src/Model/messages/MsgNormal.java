package Model.messages;

import Model.User;

public class MsgNormal extends Message {
	
	private static final long serialVersionUID = 1L;

	private String date;
	static User sender;
	public String text;
	public MsgNormal(User sender,String msg,String date)
	{
      super(sender);
      this.text= msg;
      this.date = date;
	}

	public User getSender() {
		return this.sender;
	}
	public String getMessage() {
		return this.text;
	}
	public String getDate() {
		return this.date;
	}
	
}
