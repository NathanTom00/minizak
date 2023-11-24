package it.uniroma1.textadv.obj;

import it.uniroma1.textadv.Item;

/*
 * Una volta che il pg prende il tesoro, avrà vinto
 * TODO: durante la fase di game bisogna controllare se il pg ha preso il tesoro 
 */
public class Tesoro extends Item {

	public Tesoro(String name) {
		super(name,true,false);
	}
	
	public void usa() {
		System.out.println("hai vinto!");
	}
}
