package Model.messages;

import Model.User;

public class MsgNormal extends Message {
	
	private static final long serialVersionUID = 1L;

	//date
	private int id;
	static User sender;
	public String text;
	public MsgNormal(User sender,String msg, int id)
	{
      super(sender);
      this.text= msg;
      this.id = id;
	}

	public String getMessage() {
		return this.text;
	}
	public int getID() {
		return id;
	}
	
}
