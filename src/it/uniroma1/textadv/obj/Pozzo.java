package it.uniroma1.textadv.obj;

import it.uniroma1.textadv.Item;

public class Pozzo extends Item{
	
	//una classe che contiene acqua che può passarlo al secchio
	private boolean contieneAcqua;
	
	public Pozzo(String name) {
		super(name,false,false);
		contieneAcqua = true;
	}
	
	public boolean isContieneAcqua() {
		return contieneAcqua;
	}
	
	public void svuotaPozzo() {
		contieneAcqua = false;
	}
	

}
