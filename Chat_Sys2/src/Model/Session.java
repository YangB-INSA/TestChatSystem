package Model;

import java.util.ArrayList;
import java.util.List;

public class Session {
	boolean is_in;
	private List<User> SessionList;
	
	public Session() {
		SessionList = new ArrayList<User>();
	}
	
	
	public boolean CheckSession(User utilisateur)
	{
		
		for (int i=0; i < SessionList.size(); i++) {
			if(SessionList.get(i) == utilisateur)
			{
				System.out.println("Il existe déjà une session avec " + utilisateur.getNom() + "lancement "
		+ "de la session avec historique ");
				is_in=true;
					
			}
			else {
				SessionList.add(utilisateur);
				is_in=false;
			}
		}
		return is_in;
	}
	

}
