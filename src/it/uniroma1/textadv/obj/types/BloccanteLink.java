package it.uniroma1.textadv.obj.types;

import it.uniroma1.textadv.Item;
import it.uniroma1.textadv.Link;

public class BloccanteLink extends Item implements Bloccante{
	private Link ln;
	
	public BloccanteLink(String name,Link ln) {
		super(name,false,true);
		this.ln = ln;
		ln.setBloccato(true);
		ln.setAperto(false);
	}
	
	/**
	 * Costruttore di un bloccante link che però non ha un link associato
	 * @param nome
	 */
	public BloccanteLink(String nome) {
		super(nome,false,true);
	}
	
	@Override
	public void setBloccato(boolean b) {
		if(!isBloccato()&&!b) {
			System.out.println(" è già sbloccato");
			return;
		}
		if(isBloccato() && b) {
			System.out.println();
			return;
		}
		if(!b) {
			System.out.println(getName()+" è stato rotto, ora puoi accedere a "+ln.getName());
			ln.setBloccato(false);
		}
		
		super.setBloccato(b);
		
	}
}
