package it.uniroma1.textadv.coll;

import java.util.ArrayList;
import java.util.stream.Collectors;

import it.uniroma1.textadv.interfaces.Prendibile;



public class Inventario {
	private ArrayList<Prendibile> contenitore;
	
	public Inventario() { contenitore = new ArrayList<>(); }
	
	
	public Inventario(Prendibile... item) {
		for(Prendibile it:item)
			contenitore.add(it);
	}
	
	public void add(Prendibile it) {
		if(it.isPrendibile())
			contenitore.add(it);
		else
			System.out.println("Item non prendibile");
	}
	
	
	public Prendibile pop(int i) {
		Prendibile it = contenitore.get(i);
		contenitore.remove(i);
		return it;
	}
	
	public Prendibile pop(Prendibile it) {
		for(int i=0;i<contenitore.size();i++) {
			if(it.equals(contenitore.get(i)))
				return pop(i);
		}
		return null;
	}
	
	public boolean contains(Prendibile it) {
		return contenitore.contains(it);
	}
	
	public boolean containsNameItem(String sItem) {
		for(String nameItems : contenitore.stream().map(x->x.getName()).collect(Collectors.toList())) {
			if(nameItems.equals(sItem))
				return true;
		}
		return false;
	}
	
	public String toString() {
		
		if(contenitore.isEmpty())
			return "";
		
		String s="[";
		for(Prendibile it: contenitore) {
			s+=it.getName()+",";
		}
		return s.substring(0,s.length()-1)+"]";
	}
	
	public int size() {
		return contenitore.size();
	}
	
	public Prendibile getItem(int i) {
		return contenitore.get(i);
	}
}
