package it.uniroma1.textadv.obj;

import it.uniroma1.textadv.interfaces.Bloccabile;
import it.uniroma1.textadv.obj.types.SbloccanteItem;

public class Cacciavite extends SbloccanteItem {

	public Cacciavite(String nome) {
		super(nome, new Vite(""));
	}

	@Override
	public void usa(Bloccabile item) {
		if(item instanceof Vite)
			this.setItemBloccato(item);
		super.usa(item);
	}
	
	//OKKKKKKKK
	
	/*public static void main(String args[]) {
		Botola b = new Botola("botola", new Stanza("prova1"),new Stanza("prova2"));
		Vite v = new Vite("v",b);
		Cacciavite cv = new Cacciavite("cacciavite");
		
		cv.usa(v);
		cv.usa(cv);
	}*/
}
