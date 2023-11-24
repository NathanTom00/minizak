package it.uniroma1.textadv.chars;

import it.uniroma1.textadv.Item;
import it.uniroma1.textadv.Personaggio;
import it.uniroma1.textadv.interfaces.Prendibile;
import it.uniroma1.textadv.obj.Soldi;

public class Venditore extends Personaggio {
	
	public Venditore(String nome,Item it1,Item it2) {
		super(nome,it1,it2);
		it1.setBloccato(true);
		it2.setBloccato(true);
		
	}
	
	@Override
	public void dai(Personaggio p,Prendibile soldi) {
		if(!(soldi instanceof Soldi))
		{
			super.dai(p, soldi);
			return;
		}
		
		
		String s="";
		for(int i=0;i <getInventario().size(); i++) {
			Prendibile it = getInventario().getItem(i);
			s+=it.getName()+",";
			p.addInventario(popInInventario(it));
		}
		addInventario(p.popInInventario(soldi)); 
		System.out.println(s.substring(0,s.length()-1)+" nell'inventario");
	}
	
	@Override
	public void interazione() {
		if(getInventario().getItem(0) instanceof Soldi)
			System.out.println("*soldi soldi soldi*.... HEYYY Siamo chiusi al momento!");
		else 
			System.out.println("Benvenuto! sono "+getName()+"\nIn che cosa ti posso essere utile?");
	}
}
