package it.uniroma1.textadv.coll;


import java.util.HashMap;
import java.util.List;
import java.util.Set;

import it.uniroma1.textadv.Stanza;

public class MappaStanza {
	private HashMap<Stanza,HashMap<String,String>> mappa = new HashMap<>();
	
	
	public void istanziaMappaInterna(Stanza st) {
		HashMap<String,String> map = new HashMap<>();
		List<String> lString = List.of("description","objects","links","characters");
		for(String s: lString) {
			map.put(s, "");
		}
		mappa.put(st, map);
	}
	
	public void add(Stanza st) {
		istanziaMappaInterna(st);
	}
	
	/*
	 * metodo che si userà per aggiungere descrizione,item,personaggi o links
	 */
	public void add(Stanza st,String kString, String s) {
		if(!mappa.containsKey(st))
			istanziaMappaInterna(st);
		if(mappa.get(st).containsKey(kString))
			mappa.get(st).put(kString, s);
		
	}
	
	
	public HashMap<String,String> get(Stanza st) {
		return mappa.get(st);
	}
	
	public String getInfo(Stanza st,String kString) {
		return mappa.get(st).get(kString);
	}
	public List<String> getLinkList(Stanza st){
		return List.of(getInfo(st,"links").split(","));
	}
	public Set<Stanza> getKeys(){
		return mappa.keySet();
	}
	
	

}
