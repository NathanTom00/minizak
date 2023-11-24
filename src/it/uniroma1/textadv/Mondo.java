package it.uniroma1.textadv;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.uniroma1.textadv.chars.Giocatore;
import it.uniroma1.textadv.coll.MappaStanza;
import it.uniroma1.textadv.interfaces.Creabile;
import it.uniroma1.textadv.interfaces.ElementoCoordinata;
import it.uniroma1.textadv.interfaces.Interagibile;

public class Mondo {
	private Set<Stanza> stanze;
	private String nome;
	private String descrizione;
	private Giocatore player;
	private List<Interagibile> interagibili;
	
	public Mondo(String nome, String descrizione,Set<Stanza> stanze,Giocatore player,List<Interagibile> interagibili) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.stanze = stanze;
		this.player = player;
		this.interagibili = interagibili;
	}
	public String getNome() {
		return nome;
	}

	public Giocatore getGiocatore() {
		return player;
	}
	
	public Set<Stanza> getStanze(){return stanze;}
	
	public List<Interagibile> getInteragibili(){
		return interagibili;
	}
	
	public String getDescrizione(){return descrizione;}
	
	
	/**
	 * Metodo usato per caricare da un percorso il mondo .game.
	 * Inizialmente si legge tutto il file, andando ad eleminare i vari commenti scritti.
	 * Si procede poi, caricando su una Collection ad hoc, <b>MappaStanza</b> che ah come chiavi una stanza (inizialmente vuota) e come come value per ogni chiave una mappa annidata che a sua volta ha 
	 * come chiavi ["description","objects","links","characters"] e come value il valore dei nomi da caricare nelle varie stanze.
	 * 
	 * In seguito si istanziano i Link, dove dopo la loro creazione verranno salvate in un'altra mappa all'interno delle stanza con key di tipo Coordinata e di value la stanza/link di riferimento.
	 * 
	 * Da qui si vanno ad istanziare gli Oggetti (Item) e Personaggi che vengono istanziati sfruttando la Reflection
	 * 
	 * Creato tutti gli oggetti e i personaggi si popolano le stanze andando a controllare se il nome di oggetto/personaggio sia presente nell' items/chars della mappaStanza della rispettiva stanza.
	 * @param p del file .gg
	 * @return il Mondo che ha informazioni sulle stanze,link,personaggi,items e giocatore (player)
	 */
	public static Mondo fromFile(Path p) {
		List<String> stringFile = new ArrayList<>();
		try {
			for(String line : Files.readAllLines(p)) {
				String lineNoComment="";
				if(!line.contains("//"))
				{
					lineNoComment = line;
				}
				else
				{
					for(int i=0 ;i<line.length()-2;i++) 
					{
						String commentCheck = ""+line.charAt(i)+line.charAt(i+1)+line.charAt(i+2);
						if(commentCheck.equals(" //")) {
							break;
						}
						lineNoComment = lineNoComment + line.charAt(i);
					}
				}
				
				stringFile.add(lineNoComment);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Interagibile> interagibili = new ArrayList<>();
		
		List<Integer> iTags = new ArrayList<>();
		for(int i=0 ; i<stringFile.size();i++) {
			String line = stringFile.get(i);
			if(line.length()<2) continue;
			if(line.charAt(0) == '[' && line.charAt(line.length()-1) == ']')
				iTags.add(i);
		}
		List<Integer> iStanze = cerca(stringFile,iTags,"room");
		MappaStanza mapStanze = new MappaStanza();
		for(int i:iStanze) {
			List<String> lStanzaSplittato = tagToList(stringFile,i);
			Stanza st = new Stanza(lStanzaSplittato.get(1));
			mapStanze.add(st);
			i++;
			while(!stringFile.get(i).isBlank()) {
				String[] s = stringFile.get(i).split("\t");
				if(s.length ==2)
					mapStanze.add(st, s[0], s[1]);
				i++;
			}
		}
		
		
		int iLinks = cerca(stringFile,iTags,"links").get(0);
		List<Link> lLinks = new ArrayList<>(); //contiene la lista dei links
		while(!stringFile.get(++iLinks).isBlank()) {
			lLinks.add(creaLink(stringFile.get(iLinks),mapStanze.getKeys()));
		}
		
		interagibili.addAll(lLinks);
		
		List<String> lStringLinks;
		for(Stanza st:mapStanze.getKeys()) {
			lStringLinks = mapStanze.getLinkList(st);
			for(String sLink: lStringLinks) {
				
				ElementoCoordinata stOln = null;
				String coordinata = sLink.split(":")[0];
				String sLinkStanza = sLink.split(":")[1];
				
				if(mapStanze.getKeys().stream().map(x -> x.getName()).collect(Collectors.toSet()).contains(sLinkStanza))
				{
					//se il nome viene rilevato nelle stanze allora stOln è una stanza
					for(Stanza st2:mapStanze.getKeys())
						if(st2.getName().equals(sLinkStanza))
							stOln = st2;
					
				}
				if(lLinks.stream().map(x -> x.getName()).collect(Collectors.toSet()).contains(sLinkStanza)) {
					for(Link ln:lLinks) {
						if(ln.getName().equals(sLinkStanza))
							stOln = ln;
					}
				}
				
				st.addLink(coordinata, stOln);
			}
		}
		
		Set<Interagibile> sInteragibiliCreati = new HashSet<>();
		sInteragibiliCreati.addAll(lLinks);
		sInteragibiliCreati.addAll(mapStanze.getKeys());
		
		
		int iObjs = cerca(stringFile,iTags,"objects").get(0);
		ArrayList<String> lStringObjs = new ArrayList<>();
		
		
		while(!stringFile.get(++iObjs).isBlank()) {
			lStringObjs.add(stringFile.get(iObjs));
		}
		
		
		List<Creabile> lCreabili = creaDaLista(lStringObjs, sInteragibiliCreati,"obj");
		List<Item> lItem = new ArrayList<>();


		for(Creabile c:lCreabili)
		{
			lItem.add((Item) c);
		}
		sInteragibiliCreati.addAll(lItem);
		
		
		int iPsg = cerca(stringFile,iTags,"characters").get(0);
		ArrayList<String> lStringPsgs = new ArrayList<>();
		while(!stringFile.get(++iPsg).isBlank()) {
			//crea le stringhe item 
			lStringPsgs.add(stringFile.get(iPsg));
		}
		

		lCreabili = creaDaLista(lStringPsgs, sInteragibiliCreati,"chars");
		List<Personaggio> lPsgs = new ArrayList<>();


		for(Creabile c:lCreabili)
		{
			lPsgs.add((Personaggio) c);
		}
		sInteragibiliCreati.addAll(lPsgs);
		
		interagibili.addAll(lItem);
		interagibili.addAll(lPsgs);
		
		for(Stanza st:mapStanze.getKeys())
		{
			for(String sItem:mapStanze.getInfo(st, "objects").split(", ")) {
				for(Item it: lItem) {
					if(it.getName().equals(sItem)) {
						st.addItem(it);
						lItem.remove(it);
						break;
					}
				}
			}
			
			for(String sPsg:mapStanze.getInfo(st,"characters").split(", ")) {
				for(Personaggio ps: lPsgs)
				{
					if(ps.getName().equals(sPsg)) {
						st.addPersonaggio(ps);
						lPsgs.remove(ps);
						break;
					}
				}
			}
			st.addDescrizione(mapStanze.getInfo(st, "description"));
			
			
		}
		
		int iPlayer = cerca(stringFile,iTags,"player").get(0);
		
		Giocatore player = Giocatore.getInstance(stringFile.get(iPlayer+1).split("\t")[0]);
		
		Set<Stanza> setStanzeComplete = mapStanze.getKeys();
		
		int iMondo = cerca(stringFile,iTags,"world").get(0);
		String nomeMondo= tagToList(stringFile,iMondo).get(1);
		String descrizioneMondo = "";
		while (!stringFile.get(iMondo).isBlank()) {
			if(stringFile.get(iMondo).contains("description")) {
				descrizioneMondo = stringFile.get(iMondo).split("\t")[1];
			}
			if(stringFile.get(iMondo).contains("start")) {
				String nameStart = stringFile.get(iMondo).split("\t")[1];
				for(Stanza st:setStanzeComplete)
				{
					if(st.getName().equals(nameStart))
					{
						player.setStanza(st);
					}
				}
			}
			
			iMondo++;
		}
		interagibili.addAll(setStanzeComplete);
		
		Mondo m = new Mondo(nomeMondo,descrizioneMondo,setStanzeComplete,player,interagibili);
		
		
		
		return m;
	}
		
		
	/**
	 * metodo usato per cercare nel file l'indice che contiene s, se sono di più li metto tutti in una lista
		 * @param sF Lista di stringhe che 
		 * @param iT Lista degli indici che contengono Tags
		 * @param s Stringa da cercare
		 * @return Lista degli indici in sF che contiene s
		 */
	private static List<Integer> cerca(List<String> sF,List<Integer> iT,String s) 
	{
		ArrayList<Integer> l = new ArrayList<>();
		for(int i:iT) {
			if(sF.get(i).contains(s))
			l.add(i);
		}
		if(l.isEmpty())
			return null; //null in caso di errore
		return l;
	}
		

	private static List<String> tagToList(List<String> sF,int i) {
		return List.of(sF.get(i).substring(1, sF.get(i).length()-1).split(":"));
	}
		
	private static Link creaLink(String s,Set<Stanza> setStanze) {
		Link object = null;
		List<String> lStr = List.of(s.split("\t"));
		Stanza st1 = null,st2 = null;
		for(Stanza st:setStanze)
		{
			if(st.getName().equals(lStr.get(2)))
				st1 = st;
			if(st.getName().equals(lStr.get(3)))
				st2 = st;
		}
		try {
			Class<?> c = Class.forName("it.uniroma1.textadv.links."+lStr.get(1));
			Class[] pType = {String.class,Stanza.class,Stanza.class} ;
			Constructor<?> constr = c.getConstructor(pType);
			
			object = (Link) constr.newInstance(new Object[] { lStr.get(0),st1,st2 });
		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
			
		return object;
			
	}
		
		

	
	public static List<Creabile> creaDaLista(List<String> lStringCreabile,Set<Interagibile> sInteragibiliCreati,String tipo){
		List<Creabile> ris = new ArrayList<>();
		
		while(!lStringCreabile.isEmpty())
		{
			//prendo il primo elemento della lista di tu
			String sCreabile =lStringCreabile.get(0);
			List<String> lSplittedCreabile = Arrays.asList(sCreabile.split("\t"));
			String nome = lSplittedCreabile.get(0);
			String classe = lSplittedCreabile.get(1);
			List<String> lSRif = new ArrayList<>(); //la lista dei nomi in stringa dei riferimenti(se ne ha)
			for(int i=2;i<lSplittedCreabile.size();i++) {
				lSRif.add(lSplittedCreabile.get(i));
			}
			
			//prima di creare devo controllare se tutti gli rif sono già stati creati.
			List<String> lStringCreati = sInteragibiliCreati.stream().map(x -> x.getName()).collect(Collectors.toList()); //lista dei soli nomi dei rif creati
			if(!lStringCreati.containsAll(lSRif)) {
				//se tutti i rif non sono in quelli già creati, vada a mettere l'elemento in coda
				lStringCreabile.add(lStringCreabile.remove(0));
				continue;
			}
			
			Creabile creato = (Creabile) creaOggetto(sInteragibiliCreati, nome, tipo+"."+classe, lSRif);
			
			sInteragibiliCreati.add(creato);
			ris.add(creato);
			lStringCreabile.remove(0);
		}
		return ris;
	}

	public static Creabile creaOggetto(Set<Interagibile> lCreati,String nome,String classe,List<String> rifs) {
		Class<?> c;
		//TODO convertirlo in una cosa universale per creare sia item che personaggi
		//IDEA: crea interfaccia che accomuna item e personaggi così da far ritornare quella interfaccia
		
		/*
		try {
			c = Class.forName("it.uniroma1.textadv.obj."+ls.get(1));
			Class[] param = null;
			if(l.size()==0)
			{
				param = getParametri(ls,null);
				Constructor<?> constr = c.getConstructor(param);
				return (Item) constr.newInstance(new Object[] { ls.get(0)});
			}
			else
			{
				RiferibileSuItem rif = null;
				for(RiferibileSuItem el:l)
					if(el.getName().equals(ls.get(2)))
					{
						rif = el;
						break;
					}
				param = getParametri(ls,rif);
				Constructor<?> constr = c.getConstructor(param);
				return (Item) constr.newInstance(new Object[] { ls.get(0),rif});
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		
			
		}
		
		*/
		
		try {
			c = Class.forName("it.uniroma1.textadv."+classe);
			Class[] param = null;
			if(rifs.isEmpty())
			{
				param = getParametri(null);
				Constructor<?> constr = c.getConstructor(param);
				return (Creabile) constr.newInstance(new Object[] { nome });
			}
			else
			{
				//i rif possono essere più di 1 => lista di rif
				ArrayList<Interagibile> lParametri = new ArrayList<>();
				for(String rif:rifs)
				{
					for(Interagibile elCreato:lCreati)
						if(elCreato.getName().equals(rif))
						{
							lParametri.add(elCreato);
							break;
						}
				}
				
				param = getParametri(lParametri);
				Constructor<?> constr = c.getConstructor(param);
				
				List<Object> objs = new ArrayList<>();
				objs.add(nome);
				objs.addAll(lParametri);
				return (Creabile) constr.newInstance(objs.toArray());  //da provare se funziona
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		
			
		}
		
		return null;
	}
	
	public static Class[] getParametri(List<Interagibile> rifs) {
		List<Class<?>> lparam = new ArrayList<Class<?>>();
		
		lparam.add(String.class);
		
		if(rifs != null)
			for(Interagibile el : rifs)
				try {
					lparam.add(Class.forName("it.uniroma1.textadv."+ el.getSuperClass()));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		Class<?>[] param = new Class<?>[lparam.size()];
		for (int i = 0 ; i < lparam.size() ; i++) {
		    param[i] = lparam.get(i);
		}
		return param;
		
	}
	
	public static void main(String args[]) {
		Mondo is = Mondo.fromFile(Path.of(".//mondi//minizak.game"));
		Giocatore p = is.getGiocatore();
		
		
	}
	
	public static Mondo fromFile(String nomeFile) {
		return Mondo.fromFile(Paths.get(".//mondi//"+nomeFile));
	}
	
	
}
