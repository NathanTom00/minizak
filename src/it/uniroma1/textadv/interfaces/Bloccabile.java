package it.uniroma1.textadv.interfaces;

public interface Bloccabile extends Interagibile{
	public boolean isBloccato();
	public String getName();
	public void setBloccato(boolean b);
	public void sblocca();
}
