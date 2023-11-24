package it.uniroma1.textadv;

import java.util.Objects;
import java.util.Set;

import it.uniroma1.textadv.interfaces.Bloccabile;
import it.uniroma1.textadv.interfaces.ElementoCoordinata;

public class Link implements ElementoCoordinata,Bloccabile{
	//nota: il link è bidirezionale.

	private String nome;
	private Stanza stanza1;
	private Stanza stanza2;
	private boolean bloccato;
	private boolean aperto;
	private boolean hasChiave = false;
	
	public Link(String nome,Stanza stanza1,Stanza stanza2,boolean bloccato,boolean aperto) {
		this.nome = nome;
		this.stanza1 = stanza1;
		this.stanza2 = stanza2;
		this.bloccato = bloccato;
		this.aperto = aperto;
	}
	
	
	
	/*
	 * ritorna la stanza2 se chi lo chiama passa in input la stanza1 e il link non è bloccato
	 * TODO: idea. usare come input anche un personaggio così da trasferirlo nelle due stanze
	 * TODO : diventa void quando il personaggio sarà creato
	 * TODO: essendo bidirezionale quando usiamo un link possiamo avere sia stanza1 che va a stanza2 che stanza2 va in stanza1
	 */
	public Stanza usaLink(Stanza stanza) {
		
		if(stanza.equals(stanza1) && !bloccato && aperto) {
			System.out.println("sei entrato in "+stanza2);
			return stanza2;
		}
		if(stanza.equals(stanza2) && !bloccato && aperto) {
			System.out.println("sei entrato in "+stanza1);
			return stanza1;
		}
		
		if(bloccato) {
			System.out.println(nome+" è bloccata");
			return stanza;
		}
		if(!aperto)
		{
			System.out.println("devi prima aprire "+getName());
			return stanza;
		}
		return stanza;
		
	}
	
	
	public boolean isBloccato() {
		return bloccato;
	}
	public boolean isAperto() {
		return aperto;
	}
	public boolean isHasChiave() {
		return hasChiave;
	}
	public void sblocca() {
		if(!bloccato)
			System.out.println(nome + "è già sbloccato");
		else
			System.out.println("non succede niente... magari bisogna usare uno strumento");
	}
	

	
	public String toString() {
		return nome+": "+stanza2.getName();
	}
	
	/*
	 * metodo per vedere se il link contiene una stanza
	 */
	public boolean contains(Stanza st) {
		if(stanza1.equals(st))
			return true;
		if(stanza2.equals(st))
			return true;
		return false;
	}
	
	public Stanza getAltraStanza(Stanza st) {
		if(st.equals(stanza1))
			return stanza2;
		if(st.equals(stanza2))
			return stanza1;
		return null;
	}
	
	public void apri() {
		if(isBloccato())
		{
			System.out.println(nome+" è bloccato");
			return;
		}
		
		if(hasChiave) {
			System.out.println("devi usare una chiave per aprire "+getName());
		}
		else {
			System.out.println(nome+" è stato aperto");
			aperto = true;
		}
		
	}
	
	
	
	// un link si dice uguale quando hanno lo stesso set di stanze

	@Override
	public int hashCode() {
		return Objects.hash(stanza1, stanza2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		
		Set<Stanza> thisStanze = Set.of(stanza1,stanza2);
		Set<Stanza> otherStanze = Set.of(other.stanza1,other.stanza2);
		return Objects.equals(thisStanze,otherStanze);
	}

	@Override
	public String getName() {
		return nome;
	}

	@Override
	public String getSuperClass() {
		// TODO Auto-generated method stub
		return "Link";
	}



	@Override
	public void setBloccato(boolean b) {
		bloccato = b;
		
	}
	
	public void setAperto(boolean b) {
		aperto = b;
	}

	public void setHasChiave(boolean b) {
		hasChiave = b;
	}

	
	

}
