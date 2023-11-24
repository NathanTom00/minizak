package it.uniroma1.textadv.obj;

import it.uniroma1.textadv.interfaces.Bloccabile;
import it.uniroma1.textadv.obj.types.SbloccanteItem;

public class Martello extends SbloccanteItem {

	public Martello(String nome) {
		super(nome, new Salvadanaio(""));
	}
	
	@Override
	public void usa(Bloccabile item) {
		if(item instanceof Salvadanaio)
			this.setItemBloccato(item);
		super.usa(item);
	}
	

	
	
	

}
