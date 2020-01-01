package Model;

import java.util.ArrayList;
import java.util.List;

public class Session {
	int id;
	public User user;
	
	
	public Session(User user, int id) {
		this.user = user;
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

	public User getUser( ) {
		return this.user;
	}
	
	/*
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
	*/
}
