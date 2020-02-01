package Model.messages;
import Model.User;
public class Connected extends Message {
	private static final long serialVersionUID = 2L;

    public Connected(User sender) {
		super(sender);
	}
   

}

