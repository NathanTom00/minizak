package it.uniroma1.textadv.obj.types;

import it.uniroma1.textadv.Item;
import it.uniroma1.textadv.Personaggio;

/**
 * Classe che consente di contenere un altro item
 */
public class Contenitore extends Item{
	
	
	private Item it;
	private boolean aperto;
	
	/**
	 * di default è un conenitore SBLOCCATO 
	 * @param nome, nome del Contenitore
	 * @param it, item che sarà contenuto dal Contenitore
	 */
	public Contenitore(String nome,Item it) {
		super(nome,false,false);
		
		//it viene messo invisibile e non interagibile se il Contenitore è chiuso
		it.setVisibile(false);
		it.setBloccato(true);
		this.it = it;
		aperto = false;
	}
	
	/**
	 * Crea contenitore inizialmente vuoto
	 * @param nome il nome del conenitore
	 */
	public Contenitore (String nome) {
		super(nome,false,false);
	}
	
	public Item getItem() {
		return it;
	}
	
	public void removeItem() {
		it = null;
	}
	
	public boolean isAperto() {
		return aperto;
	}
	
	/**
	 * A seconda del cmd viene applicato la chiusura/apertura del Contenitore
	 * @param cmd, comando che viene passato
	 * 
	 */
	private void apply(String cmd) {
		if(isBloccato())
		{
			System.out.println(getName() + " è bloccato");
		}
		aperto = !aperto;
		it.setBloccato(!it.isBloccato());
		it.setVisibile(!it.isVisibile());
		System.out.println(this.getName() + " è stato "+ cmd);
	}
	
	public void apri() {
		if(aperto){
			System.out.println(this.getName()+" già aperto");
			return;
		}
		apply("aperto");
	}
	
	public void chiudi() {
		if(!aperto) {
			System.out.println(this.getName()+" già chiuso");
			return;
		}
		apply("chiuso");
	}
	
	
	public void prendiDaContenitore(Personaggio p,Item it) {
		if(!aperto) {
			System.out.println("prima devi aprire "+getName());
			return;
		}
		
		if(this.it.equals(it)) {
			System.out.println(it.getName()+" aggiunto nell'inventario");
			p.getInventario().add(it);
		}
		else
			System.out.println(it.getName()+" non è in "+getName());
	}
	public void setAccessibile() {
		bloccato = false;
		aperto = true;
	}
	
	@Override
	public void setBloccato(boolean b) {
		if(!b)
		{
			System.out.println(getName()+" sbloccato, ora lo puoi aprire");
		}
		
		super.setBloccato(b);
	}
	
	/**
	 * Mette in stringa il nome del Contenitore e se aperto anche il nome dell'item contenuto
	 */
	@Override
	public String vedi() {
		String s = getName();
		if(aperto)
			s += " contiene "+it.getName();
		return s;
	}
	
	
}
