package it.uniroma1.textadv.chars;

import it.uniroma1.textadv.Personaggio;
import it.uniroma1.textadv.interfaces.Prendibile;

public class Gatto extends Personaggio implements Prendibile{
	private boolean prendibile;
	
	public Gatto(String nome) {
		super(nome);
		prendibile = true;
	}

	public String getSuperClass() {
		return "chars.Gatto";
	}

	@Override
	public boolean isPrendibile() {
		return prendibile;
	}
	
	@Override
	public void interazione() {
		System.out.println("frrrr frrrrr");
	}
}
