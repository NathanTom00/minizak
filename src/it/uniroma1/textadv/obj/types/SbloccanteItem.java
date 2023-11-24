package it.uniroma1.textadv.obj.types;

import it.uniroma1.textadv.Item;
import it.uniroma1.textadv.interfaces.Bloccabile;

public class SbloccanteItem extends Item implements Sbloccante{
	public Item b;
	
	public SbloccanteItem(String nome,Item b){
		super(nome,true,false);
		this.b = b;
		b.setBloccato(true);
		
	}
	
	/**
	 * Verifia che b passato come parametro e b come attributo siano uguali.
	 * se lo sono allora slocca b
	 * @param b
	 * @param cmd
	 */
	@Override
	public void usa(Bloccabile b) {
		if(!this.b.equals((Item)b)) {
			System.out.println("non succede niente...");
			return;
		}
		
		b.setBloccato(false);
		
		
	}
	
	public void setItemBloccato(Bloccabile b) {
		this.b = (Item) b;
	}
	
	public Item getItemSbloccato() {
		return b;
	}
}
