package it.uniroma1.textadv;

import it.uniroma1.textadv.interfaces.Bloccabile;
import it.uniroma1.textadv.interfaces.Creabile;
import it.uniroma1.textadv.interfaces.Prendibile;

public class Item implements Creabile,Bloccabile,Prendibile {
	private String nome;
	private boolean prendibile;
	protected boolean bloccato;
	private boolean visibile;
	
	/**
	 * Costruttore dove si mette di default la visibilità a true
	 * @param nome
	 * @param prendibile
	 * @param bloccato
	 */
	public Item(String nome,boolean prendibile, boolean bloccato) {
		this.nome = nome;
		this.prendibile = prendibile;
		this.bloccato = bloccato;
		visibile = true;
	}
	



	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isPrendibile() {
		return prendibile;
	}

	public void setPrendibile(boolean prendibile) {
		this.prendibile = prendibile;
	}

	public boolean isBloccato() {
		return bloccato;
	}
	
	

	public void setBloccato(boolean bloccato) {
		this.bloccato = bloccato;
	}

	public boolean isVisibile() {
		return visibile;
	}

	public void setVisibile(boolean visibile) {
		this.visibile = visibile;
	}

	public String vedi() {
		if(visibile)
			return nome;
		return "";
	}
	
	public void sblocca() {
		if(!bloccato)
			System.out.println(nome + "è già sbloccato");
		else
			System.out.println("non succede niente... magari bisogna usare uno strumento");
			
	}
	
	public void usa(Bloccabile b) {
		System.out.println("non succede niente...");
	}
	
	@Override
	public String getName() {
		return nome;
	}



	@Override
	public String getSuperClass() {
		// TODO Auto-generated method stub
		return "Item";
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	
}
