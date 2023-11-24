package it.uniroma1.textadv.obj;

import it.uniroma1.textadv.Link;
import it.uniroma1.textadv.obj.types.SbloccanteLink;

public class Chiave extends SbloccanteLink {

	public Chiave(String name, Link ln) {
		super(name, ln);
		ln.setHasChiave(true);
		ln.setAperto(false);
	}
	
	
	
}
