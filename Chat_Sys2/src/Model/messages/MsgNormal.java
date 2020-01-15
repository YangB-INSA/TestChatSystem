package Model.messages;

import Model.User;

public class MsgNormal extends Message {
	
	private static final long serialVersionUID = 1L;

	private String date;
	public String message;

	static User sender;
	
	public MsgNormal(User sender,String msg,String Date)
	{
      super(sender);
      message= msg;
      date = Date;
	}
	
	public String getMessage() {
		return this.message;
	}
	public String getDate() {
		return this.date;
	}
	
}
