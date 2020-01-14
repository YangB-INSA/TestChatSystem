package Model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import View.ChatCard;
import View.ChatWindow;

public class Session {
	int id;
	public User user;
	//public ChatCard chat;
	
	public Session(User user) {
		this.user = user;
		//this.chat = new ChatCard();
		//chat.setName(this.user.getNom());
	}
	
	public int getId() {
		return this.id;
	}

	public User getUser( ) {
		return this.user;
	}
	
	public String toString() {
		return this.user.getNom();
	}
	
	/*
	public ChatCard getChat() {
		return this.chat;
	}
	*/
	
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
