package it.uniroma1.textadv;

import it.uniroma1.textadv.coll.Inventario;
import it.uniroma1.textadv.interfaces.Creabile;
import it.uniroma1.textadv.interfaces.Prendibile;

public class Personaggio implements Creabile{
	private String nome;
	private Inventario inv;
	
	public Personaggio(String nome) {
		this.nome = nome;
		inv = new Inventario();
	}
	
	public Personaggio(String nome,Inventario inv) {
		this.nome = nome;
		this.inv = inv;
	}
	
	public Personaggio(String nome,Item...items ) {
		this(nome);
		for(Item it:items)
			inv.add(it);
		
	}
	
	public String getName() {
		return nome;
	}
	
	public Inventario getInventario() {
		return inv;
	}
	
	public boolean containsInInventario(Prendibile it) {
		return inv.contains(it);
	}
	
	public Prendibile popInInventario(Prendibile it) {
		return inv.pop(it);
	}
	
	public void addInventario(Prendibile it) {
		inv.add(it);
	}
	
	public void interazione() {
		System.out.println("sono "+nome);
	}
	
	public void dai(Personaggio p,Prendibile prendibile) {
		System.out.println("non succede niente...");
	}
	
	
	@Override
	public String getSuperClass() {
		// TODO Auto-generated method stub
		return "Personaggio";
	}
	@Override
	public String toString() {
		return nome;
	}
	
}
