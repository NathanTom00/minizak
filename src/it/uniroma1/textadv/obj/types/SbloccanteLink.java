package it.uniroma1.textadv.obj.types;

import it.uniroma1.textadv.Item;
import it.uniroma1.textadv.Link;
import it.uniroma1.textadv.interfaces.Bloccabile;

public class SbloccanteLink extends Item implements Sbloccante{
	private Link ln;
	
	public SbloccanteLink(String nome,Link ln)
	{
		super(nome,true,false);
		this.ln = ln;
	}
	
	public void usa(Bloccabile ln) {
		if(!this.ln.equals((Link)ln)) {
			System.out.println("non succede niente...");
			return;
		}
		
		
		this.ln.setBloccato(false);
		this.ln.setAperto(true);
		
	}
	
	public void apri(Bloccabile ln) {
		if(!this.ln.equals((Link)ln)) {
			System.out.println("non succede niente...");
			return;
		}
		
		this.ln.setBloccato(false);
		this.ln.setAperto(true);
		System.out.println("ora puoi accedere a "+ ln.getName());
	}
	
	
}
