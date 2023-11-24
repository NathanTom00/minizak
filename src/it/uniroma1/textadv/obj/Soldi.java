package it.uniroma1.textadv.obj;

import it.uniroma1.textadv.Item;

/*
 * moneta di gioco nella quale può essere scambiato tra vari pg
 */
public class Soldi extends Item{

	public Soldi(String name) {
		super(name,true,false);
	}
	
	public void usa(Item item) {
		System.out.println("C'è un luogo e un momento per ogni cosa! MA NON ORA");
	}
}
