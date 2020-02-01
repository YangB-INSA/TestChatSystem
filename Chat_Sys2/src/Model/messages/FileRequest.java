package Model.messages;
import java.io.File;

import Model.User;
public class FileRequest extends Message {
	private static final long serialVersionUID = 9L;
	static User sender;
	public String fileName;
	public String date;
	public File myFile;
	
    public FileRequest(User sender, String name, String Date, File File) {
		super(sender);
		fileName= name;
		date = Date;
		myFile=File;
	}
   
	
	public String getFileName() {
		return this.fileName;
	}
	public String getDate() {
		return this.date;
	}
	
	public File getFile() {
		return this.myFile;
	}

}