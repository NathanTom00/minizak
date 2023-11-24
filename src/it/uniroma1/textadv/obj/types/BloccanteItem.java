package it.uniroma1.textadv.obj.types;

import it.uniroma1.textadv.Item;

public class BloccanteItem extends Item implements Bloccante{
	private Item it;
	
	/**
	 * costruttore dove blocca l'item in questione e l'item it.
	 * @param nome il nome del BloccanteItem
	 * @param it Item che viene bloccato
	 */
	public BloccanteItem(String nome,Item it)
	{
		super(nome,false,true);
		it.setBloccato(true);
		this.it = it;
	}
	
	public BloccanteItem(String nome) {
		super(nome,false,false);
	}
	
	public Item getItemBloccato() {
		return it;
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
			System.out.println(getName()+" non ti blocca più, ora puoi prendere "+it.getName());
			it.setBloccato(false);
			
		}
		
		super.setBloccato(b);
		
	}
	
	public void removeItemBloccato() {
		it = null;
	}
	
}
