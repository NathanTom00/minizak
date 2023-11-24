package it.uniroma1.textadv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.uniroma1.textadv.chars.Guardiano;
import it.uniroma1.textadv.chars.Venditore;
import it.uniroma1.textadv.coll.Coordinata;
import it.uniroma1.textadv.interfaces.ElementoCoordinata;
import it.uniroma1.textadv.interfaces.Interagibile;
import it.uniroma1.textadv.interfaces.Prendibile;
import it.uniroma1.textadv.obj.Tesoro;
import it.uniroma1.textadv.obj.types.BloccanteItem;
import it.uniroma1.textadv.obj.types.Contenitore;

public class Stanza implements ElementoCoordinata,Interagibile {
	
	private String nome;
	private String descrizione;
	private ArrayList<Item> items;
	private ArrayList<Personaggio> personaggi;
	private Map<Coordinata,ElementoCoordinata> mappaCoordinate;
	
	
	public Stanza(String nome) {
		this.nome = nome;
		mappaCoordinate = new HashMap<>();
		items = new ArrayList<>();
		personaggi = new ArrayList<>();
		mappaCoordinate = new HashMap<>();
	}
	
	/**
	 * Dato una stringa e una stanza o link mette nella coordinata rispettiva alla stringa la stanza o link
	 * @param sCoordinata
	 * @param sl
	 */
	public void addLink(String sCoordinata,ElementoCoordinata sl) {
		Coordinata c = Coordinata.fromString(sCoordinata);
		mappaCoordinate.put(c, sl);
	}
	

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public void addItem(Item it) {
		items.add(it);
	}
	
	public void addPersonaggio(Personaggio p) {
		personaggi.add(p);
	}
	
	public void addDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public ElementoCoordinata getStanzaDaCoordinata(Coordinata c) {
		if(mappaCoordinate.containsKey(c))
			return mappaCoordinate.get(c);
		
		return null;
	}
	
	public Coordinata cercaLinkDaStanza2(ElementoCoordinata stanza2) {
		for(Coordinata c: Coordinata.values()) {
			if(mappaCoordinate.get(c) instanceof Link)
			{
				Link ln = (Link) mappaCoordinate.get(c);
				if(ln.getAltraStanza(this).equals(stanza2))
					return c;
			}
			
		}
		return null;
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	
	public void removeItem(Item it) {
		items.remove(it);
	}
	
	public void removePersonaggio(Personaggio p) {
		personaggi.remove(p);
	}
	
	public boolean containsItem(Item it) {
		return items.contains(it);
	}
	
	
	public boolean containsLink(Link link) {
		 return mappaCoordinate.values().contains(link);
	}
	
	public boolean containsPersonaggio(Personaggio p) {
		return personaggi.contains(p);
	}
	
	public boolean containsInteragibile(Interagibile interagibile) {
		if(interagibile instanceof Item)
			return containsItem((Item) interagibile);
		if(interagibile instanceof Link)
			return containsLink((Link) interagibile);
		if(interagibile instanceof Personaggio)
			return containsPersonaggio((Personaggio) interagibile);	
		
		return false;
	}
	
	public Set<Coordinata> getKeysCoordinate(){
		return mappaCoordinate.keySet();
	}
	
	public void prendiItemDaStanza(Personaggio player,Prendibile prendibile) {
		if(!prendibile.isPrendibile())
		{
			System.out.println("non puoi prendere "+prendibile.getName());
			return;
		}
		
		if(!containsInteragibile(prendibile))
		{
			
			for(Item c:items) {
				if(c instanceof Contenitore) {
					Contenitore contenitore = (Contenitore) c;
					if(contenitore.getItem().equals(prendibile))
					{
						player.addInventario(contenitore.getItem());
						contenitore.removeItem();
						System.out.println(prendibile.getName()+" aggiunto nell'inventario");
						return;
					}
				}
				if(c instanceof BloccanteItem) {
					BloccanteItem b = (BloccanteItem)c;
					if(b.getItemBloccato().equals(prendibile)) {
							
							if(!b.isBloccato()) {
								player.addInventario(b.getItemBloccato());
								b.removeItemBloccato();
								System.out.println(prendibile.getName()+" aggiunto nell'inventario");
								return;
							}
							else {
								System.out.println(b.getName()+" non ti permette di prendere "+prendibile.getName());
							}
					
					}
					
					
				
				}
			}
		}
		
		

		for(Personaggio p:personaggi) {
			if(p instanceof Venditore) {
				if(((Venditore) p).containsInInventario(prendibile)) {
					System.out.println("devi comprare "+prendibile.getName());
					return;
				}
			}
			
			if(p instanceof Guardiano) {
				if(((Guardiano)p).containsInInventario(prendibile)) {
					System.out.println("il guardiano ti impedisce di prendere "+ prendibile.getName());
					return;
				}
			}
		}
		
		
		System.out.println(prendibile.getName()+" aggiunto nell'inventario");
		player.addInventario(prendibile);
		
		if(prendibile instanceof Tesoro) {
			((Tesoro) prendibile).usa();
		}
		
		if(!player.containsInInventario(prendibile))
		{
			System.out.println(prendibile.getName()+" non è nella stanza");
		}
		
	}
	

	
	public void daiAPersonaggioInStanza(Personaggio player,Prendibile prendibile,Personaggio altroPersonaggio) {
		altroPersonaggio.dai(player, prendibile);
		
		if(altroPersonaggio instanceof Venditore) {
			for(int i=0;i<player.getInventario().size();i++) 
				if(items.contains(player.getInventario().getItem(i))) 
					removeItem((Item)player.getInventario().getItem(i));
		}
		
		
	}
	
	public void vedi() {
		
		System.out.println("nome stanza: "+nome);
		System.out.println("descrizione: "+descrizione);
		
		String s = "";
		for(Item it:items)
			if(it.isVisibile())
				s+= it.toString()+",";
		s = s.substring(0,s.length()-1);
		System.out.println("items: "+s);
		
		s="";
		for(Personaggio p:personaggi)
			s+= p.toString()+",";
		s=s.substring(0,s.length());
		System.out.println("personaggi: "+s);
		
		s="";
		for(Coordinata c:getKeysCoordinate())
			s+= c.toString()+"\t"+mappaCoordinate.get(c).getName()+"\n";
		System.out.println("links:\n"+s);
	}
	
	@Override
	public String getName() {return nome;}
	public String toString() {
		return nome;
	}

	@Override
	public String getSuperClass() {
		return "Stanza";
	}

	

	
	
	
}
