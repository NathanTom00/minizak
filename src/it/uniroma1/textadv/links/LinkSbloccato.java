package it.uniroma1.textadv.links;

import it.uniroma1.textadv.Link;
import it.uniroma1.textadv.Stanza;

public class LinkSbloccato extends Link {

	public LinkSbloccato(String nome, Stanza stanza1, Stanza stanza2,boolean aperto) {
		super(nome, stanza1, stanza2, false,aperto);
	}
	

}
