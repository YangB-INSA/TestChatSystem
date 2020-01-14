package Model.messages;
import Model.User;
public class FileRequest extends Message {
	private static final long serialVersionUID = 9L;
	static User sender;
	public String fileName;
    public FileRequest(User sender, String name) {
		super(sender);
		fileName= name;
	}
   
    public User getSender() {
		return this.sender;
	}
	
	public String getFileName() {
		return this.fileName;
	}

}