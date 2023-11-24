package it.uniroma1.textadv.chars;

import it.uniroma1.textadv.Personaggio;

public class Cane extends Personaggio {

	public Cane(String nome) {
		super(nome);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void interazione() {
		System.out.println("BAU BAU");
	}
	

}
