package it.uniroma1.textadv.obj;

import it.uniroma1.textadv.Item;
import it.uniroma1.textadv.interfaces.Bloccabile;
import it.uniroma1.textadv.obj.types.Contenitore;
import it.uniroma1.textadv.obj.types.SbloccanteItem;

public class Tronchesi extends SbloccanteItem {

	public Tronchesi(String name,Item itemSbloccato) {
		super(name,itemSbloccato);
		if(itemSbloccato instanceof Contenitore) {
			((Contenitore) itemSbloccato).setBloccato(true);
		}
		
	}
	
	@Override
	public void usa(Bloccabile b) {
		if(b instanceof Contenitore && b.equals(getItemSbloccato())) {
			Contenitore c = (Contenitore) b;
			c.setAccessibile();
			System.out.println(c.getName()+" è stato aperto, contiene "+c.getItem());
		}
	}
}
