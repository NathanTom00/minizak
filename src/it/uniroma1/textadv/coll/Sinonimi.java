package it.uniroma1.textadv.coll;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import it.uniroma1.textadv.chars.Giocatore;

//dizionario dei comandi, dove come chiave ha un method (metodo da invocare) e come valore le parole (predisposte) con similitudini
public class Sinonimi {
	private Map<Method,Set<String>> contenitore;
	
	public Sinonimi() {
		instanceContenitore();
	}
	
	private void instanceContenitore() {
		//i comandi di un player sono, guardare (generale e non), prendere, andare, interagire.
		
		//aggiungo le chiavi.
		Map<Method,Set<String>> ris = new HashMap<>();
		List<String> s = List.of("prendi","vai","guarda","interagisci","sblocca","apri","dai","usa","inventario");
		List<Method> lKeys = List.of(Giocatore.class.getDeclaredMethods()).stream().filter(x -> s.contains(x.getName())).collect(Collectors.toList());
		for(Method m : lKeys) {
			ris.put(m, getSimilitudini(m.getName()));
			
		}
		
		contenitore = ris;
		
	}
	
	private Set<String> getSimilitudini(String s) {
	
		//nota che prendi può essere iteso sia prendi oggetto che prendi un link.
		Set<String> similitudini = switch (s) {
			case "prendi"		->  Set.of("prendi");
			case "vai"			->	Set.of("vai","entra","prendi","usa");
			case "guarda"		->	Set.of("guarda");
			case "interagisci"	->	Set.of("interagisci","parla","accarezza");
			case "sblocca"		->	Set.of("sblocca","rompi","apri");
			case "apri"			->	Set.of("apri");
			case "dai"			->	Set.of("dai");
			case "usa"			->	Set.of("usa");
			case "inventario"	->	Set.of("inventario");
			default -> throw new IllegalArgumentException("Unexpected value: " + s);
		};
		
		return similitudini;
	}
	
	public Set<Method> getKeys(){
		return contenitore.keySet();
	}
	
	public Set<String> get(Method m){
		return contenitore.get(m);
	}
	

}
