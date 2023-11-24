package it.uniroma1.textadv.chars;

import it.uniroma1.textadv.Item;
import it.uniroma1.textadv.Personaggio;
import it.uniroma1.textadv.interfaces.Prendibile;

public class Guardiano extends Personaggio {
	private Prendibile distrattore;
	
	public Guardiano(String nome,Item itemCheProtegge,Gatto gatto) {
		super(nome);
		distrattore = gatto;
		this.addInventario(itemCheProtegge);
		itemCheProtegge.setBloccato(true);
		
	}
	
	public void dai(Personaggio p,Prendibile prendibile) {
		if(prendibile.equals(distrattore))
		{
			this.addInventario(p.popInInventario(prendibile));
			Item itemCheProtegge =(Item) getInventario().pop(this.getInventario().getItem(0));
			((Giocatore)p).getStanza().addItem(itemCheProtegge);
			
			System.out.println("Il guardiano è distratto... "+" ora puoi interagire con "+itemCheProtegge.getName());
		}
		else {
			System.out.println("non ha nessun effetto...");
		}
	}
	

	
	
	
	


}
