package it.uniroma1.textadv;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.uniroma1.textadv.chars.Giocatore;
import it.uniroma1.textadv.coll.Coordinata;
import it.uniroma1.textadv.coll.Sinonimi;
import it.uniroma1.textadv.interfaces.Interagibile;
import it.uniroma1.textadv.obj.exceptions.ComandoNonRiconosciutoException;

public class MotoreTestuale {

	private Giocatore player;
	private List<Interagibile> interagibili = new ArrayList<>();
	private Sinonimi similitudini;
	
	public MotoreTestuale(Giocatore player,List<Interagibile> interagibili) {
		this.player = player;
		this.interagibili = interagibili;
		similitudini = new Sinonimi();
	}
	
	/**
	 * Metodo usato per convertire una stringa in comando player dovrà eseguire.
	 * se la stringa non contiene un comando, solleva ComandoNonRiconosciutoException
	 * @param la stringa che vogliamo convertire in comando player
	 * @throws ComandoNonRiconosciutoException
	 */
	public void traduciInComando(String oldLine) throws ComandoNonRiconosciutoException {	
		List<String> splittedLine = List.of(oldLine.split(" "));
		String cmd = splittedLine.get(0);
		
		
		List<String> parametri = cercaParametri(splittedLine.subList(1, splittedLine.size()));
		
		List<Interagibile> lParametriInteragibili = new ArrayList<>();
		for(String p:parametri) {
			for(Interagibile i:interagibili)
				if(p.equals(i.getName()))
					lParametriInteragibili.add(i);
		}
		Coordinata potenzialeCoordinata = null;
		for(String s:parametri) {
			if(Coordinata.isACoordinata(s))
				potenzialeCoordinata = Coordinata.fromString(s);
		}
		
		boolean riconosciuto = false;
		for(Method m:similitudini.getKeys()) {
			if(similitudini.get(m).contains(cmd)) {
				try {
					if(!lParametriInteragibili.isEmpty()) {
						m.invoke(player, lParametriInteragibili.toArray());
						riconosciuto = true;
						break;
					}
				}
				catch (Exception e) {}
				try {
					if(potenzialeCoordinata != null)
					{
						m.invoke(player, potenzialeCoordinata);
						riconosciuto = true;
						break;
					}
				}
				catch(Exception e) {}
				try {
					if(lParametriInteragibili.isEmpty() && potenzialeCoordinata == null)
					{
						m.invoke(player);
						riconosciuto = true;
						break;
					}
				}
				catch(Exception e) {}
			}
		}
		if(!riconosciuto)
			throw new ComandoNonRiconosciutoException();
	}
	
	
	/**
	 * cerca sequenze di stringhe in splittedLine che potrebbero far parte di riferimento a stanze,
	 * items,link o personaggi. Ognuni riferimento cerca quello più lungo accettato possibile, per evitare 
	 * ambiguità su invocazioni a methodo.
	 * @param splittedLine una stringa resa List <String> usando uno split di spazi
	 * @return lista dei nomi dei parametri del metodo
	 */
	public List<String> cercaParametri(List<String> splittedLine){
		List<String> ris = new ArrayList<>();
		if(splittedLine.isEmpty())
			return new ArrayList<String>();
		
		String s = ""; 
		String potenzialeParametro = "";
		List<String> listNomeInteragibili = interagibili.stream().map(x -> x.getName()).sorted().collect(Collectors.toList());
		
		
		
		int j = 0;
		for(int i=0;i<=splittedLine.size();i++) {
			
			for(j=i;j<splittedLine.size();j++) {
				s="";
				for(String parola:splittedLine.subList(i, j+1)) {
					s+=parola +" ";
				}
				s = s.substring(0,s.length()-1);
				
				if(Coordinata.isACoordinata(s)) {
					ris.add(s);
					break;
				}
				
				if(listNomeInteragibili.contains(s)) {
					potenzialeParametro = s;
				}
				else {
					if(potenzialeParametro.equals(""))
					{
						
						continue;
					}
					else
					{
						i=j;
						break;
					}
				}
			}
			if(!potenzialeParametro.equals(""))
			{
				i=j;
				ris.add(potenzialeParametro);
				potenzialeParametro = "";
				
			}
			
		}
		
		return ris;
	}
	

}
