package Model.messages;
import Model.User;

public class OK extends Message {
	private static final long serialVersionUID = 6L;

	static User localUser;

    public OK(User localUser) {
		super(localUser);
	}
    
	
	


}
