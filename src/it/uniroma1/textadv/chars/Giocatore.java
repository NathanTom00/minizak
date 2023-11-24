package it.uniroma1.textadv.chars;

import it.uniroma1.textadv.Item;
import it.uniroma1.textadv.Link;
import it.uniroma1.textadv.Personaggio;
import it.uniroma1.textadv.Stanza;
import it.uniroma1.textadv.coll.Coordinata;
import it.uniroma1.textadv.interfaces.Bloccabile;
import it.uniroma1.textadv.interfaces.ElementoCoordinata;
import it.uniroma1.textadv.interfaces.Prendibile;
import it.uniroma1.textadv.obj.types.BloccanteItem;
import it.uniroma1.textadv.obj.types.BloccanteLink;
import it.uniroma1.textadv.obj.types.Contenitore;
import it.uniroma1.textadv.obj.types.SbloccanteItem;
import it.uniroma1.textadv.obj.types.SbloccanteLink;

public class Giocatore extends Personaggio{
	private Stanza st;
	static private Giocatore giocatore;
	
	
	static public Giocatore getInstance(String nome) {
		if(giocatore == null) giocatore = new Giocatore(nome);
		return giocatore;
	}
	
	
	private Giocatore(String nome)
	{
		super(nome);
	}
	
	public Stanza getStanza() {
		return st;
	}
	public void setStanza(Stanza st) {
		this.st = st;
	}
	
	public String getNomeStanza() {
		return st.getName();
	}
	
	public void vai(Coordinata c) {
		
		ElementoCoordinata sOl = st.getStanzaDaCoordinata(c);
		if(sOl == null) {
			System.out.println("non c'è niente in quella direzione...");
			return;
		}
		if(sOl instanceof Stanza) {
			System.out.println("sei entrato in "+ sOl.getName());
			setStanza((Stanza) sOl);
		}
			
		if(sOl instanceof Link) {
			setStanza(((Link)sOl).usaLink(st));
		}
	}
	
	public void vai(ElementoCoordinata sOl)  {
		//s può essere o un nome di link/stanza oppure una coordinata
		for(Coordinata c:st.getKeysCoordinate()) {
			if(st.getStanzaDaCoordinata(c).equals(sOl)) {
				vai(c);		
				return;
			}
		
		
		}
		
		Coordinata c = st.cercaLinkDaStanza2(sOl);
		if(c!=null)
		{
			vai(c);
		}
		else {
			System.out.println(" non c'è nessuna stanza collegata con quel nome");
		}
	}
	
	public void prendi(Prendibile prendibile) {
		st.prendiItemDaStanza(giocatore, prendibile);
	
	}
	
	
	
	public void prendi(Item prendibile,Contenitore contenitore) {
		if(!st.getItems().contains(contenitore)) {
			System.out.println(contenitore.getName()+" non presente nella stanza");
			return;
		}
		
		contenitore.prendiDaContenitore(this, prendibile);
	}
	

	public void apri(Contenitore item) {
		if(!st.containsItem(item))
		{
			System.out.println(item.getName()+" non presente nella stanza");
		}
		
		
		item.apri();
		/*
		if(item instanceof Contenitore)
			((Contenitore) item).apri();
		else
			System.out.println(item.getName()+" non è un oggetto apribile");*/
		
	}
	
	public void apri(Link ln) {
		ln.apri();
	}
	
	public void apri(Link ln,SbloccanteLink item) {
		item.apri(ln);
	}
	
	public void apri(Bloccabile b,SbloccanteItem it) {
		sblocca(b,it);
	}
	
	public void guarda() {
		st.vedi();
	}
	
	public void guarda(Item item) {
		if(!st.containsItem(item)||!item.isVisibile())
		{
			System.out.println(item.getName()+" non è visibile o non presente nella stanza");
			return;
		}
		
		System.out.println(item.vedi());
	}
	
	
	public void sblocca(Bloccabile bloccabile) {
		if(!st.containsInteragibile(bloccabile)) {
			System.out.println(bloccabile.getName()+" non è nella stanza");
		}
		
		bloccabile.sblocca();
		
	}
	
	
	public void sblocca(Bloccabile bloccabile,SbloccanteItem sbloccanteItem) {
		if(!getInventario().contains(sbloccanteItem)) {
			System.out.println(sbloccanteItem.getName()+" non è nell'inventario");
			return;
		}
		if(!st.containsInteragibile(bloccabile)) {
			System.out.println(bloccabile.getName()+" non è presente nella stanza");
			return;
		}
		
		sbloccanteItem.usa(bloccabile);
		
	}
	
	public void sblocca(BloccanteLink bloccabile,SbloccanteItem sbloccanteItem) {
		if(!getInventario().contains(sbloccanteItem)) {
			System.out.println(sbloccanteItem.getName()+" non è nell'inventario");
			return;
		}
		if(!st.containsInteragibile(bloccabile)) {
			System.out.println(bloccabile.getName()+" non è presente nella stanza");
			return;
		}
		
		sbloccanteItem.usa(bloccabile);
		
	}
	
	public void sblocca(Bloccabile bloccabile,SbloccanteLink sbloccanteLink) {
		if(!getInventario().contains(sbloccanteLink)) {
			System.out.println(sbloccanteLink.getName()+" non è nell'inventario");
			return;
		}
		if(!st.containsInteragibile(bloccabile)) {
			System.out.println(bloccabile.getName()+" non è presente nella stanza");
			return;
		}
		
		sbloccanteLink.usa(bloccabile);
	}
	
	public void usa(SbloccanteItem itSuCuiUsare,BloccanteItem bloccabile) {
		sblocca(bloccabile,itSuCuiUsare);
	}
	
	public void usa(SbloccanteItem itSuCuiUsare,BloccanteLink bloccabile) {
		sblocca(bloccabile,itSuCuiUsare);
	}

	public void usa(SbloccanteLink itSuCuiUsare,Link bloccabile) {
		sblocca(bloccabile,itSuCuiUsare);
		vai(bloccabile);
	}
	
	public void usa(Item it,Bloccabile bloccabile) {
		it.usa(bloccabile);
	}
	
	
	
	public void interagisci(Personaggio p) {
		p.interazione();
	}
	
	public void dai(Prendibile prendibile,Personaggio personaggio) {
		st.daiAPersonaggioInStanza(giocatore, prendibile, personaggio);
	}
	
	
	
	
	public void inventario() {
		System.out.println(getInventario().toString());
	}
	
	
}
