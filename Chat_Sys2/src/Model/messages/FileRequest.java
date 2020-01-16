package Model.messages;
import Model.User;
public class FileRequest extends Message {
	private static final long serialVersionUID = 9L;
	static User sender;
	public String fileName;
	public String date;
    public FileRequest(User sender, String name, String Date) {
		super(sender);
		fileName= name;
		date = Date;
	}
   
	
	public String getFileName() {
		return this.fileName;
	}
	public String getDate() {
		return this.date;
	}

}