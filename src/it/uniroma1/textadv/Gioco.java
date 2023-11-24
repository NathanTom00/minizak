package it.uniroma1.textadv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.uniroma1.textadv.obj.exceptions.ComandoNonRiconosciutoException;

public class Gioco {
	
	/**
	 * metodo usato per iniziare una partita normale del gioco testuale
	 * @param m il mondo che vogliamo caricare
	 */
	public void play(Mondo m) {
		
		MotoreTestuale mt = introGioco(m);
		String line = "";
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("scrivi un comando per iniziare a giocare (esci per uscire)");
		while(true) {
			line = scanner.nextLine();
			if(line.equals("esci"))
			{
				System.out.println("grazie per aver giocato!");
				break;
			}
			
			gioca(mt,line);
			
		}
	}
	
	
	/**
	 * metodo per iniziare una partita del gioco testuale in modalità fast forward
	 * @param m il mondo che vogliamo caricare
	 * @param p il percorso del file .ff
	 */
	public void play(Mondo m,Path p) {
		List<String >lLines = new ArrayList<>();
		List<String> lLinesWithComments = new ArrayList<>();
		try {
			lLinesWithComments = Files.readAllLines(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		lLines = removeComments(lLinesWithComments);
		
		MotoreTestuale mt = introGioco(m);
		for(String line:lLines) {
			gioca(mt,line);
		}
	}
	
	/**
	 * Metodo usato per rimuovere i commenti e i tab presenti nella lista di stringhe
	 * @param lLines la lista delle stringhe che ha i commenti
	 * @return la lista delle righe, senza i commenti e tab
	 */
	public List<String> removeComments(List<String> lLines) {
		List<String> l = new ArrayList<>();
		for(String elLines:lLines) {
			String line = elLines.replaceAll("\t", "");
			if(!(line.contains("//")))
			{
				l.add(line);
			}
			String s = "";
			for(int i=0;i<line.length()-1;i++) {
				if(line.charAt(i) == '/' && line.charAt(i+1) == '/')
				{
					l.add(s);
				}
				
				s+=line.charAt(i);
			}
		}
		return l;
	}
	
	
	public MotoreTestuale introGioco(Mondo m) {
		System.out.println("benvenuto in "+m.getNome());
		return new MotoreTestuale(m.getGiocatore(),m.getInteragibili());
	}
	
	public void gioca(MotoreTestuale mt,String line) {
		try {
			mt.traduciInComando(line);
		} catch (ComandoNonRiconosciutoException e) {
			e.printStackTrace();
		}
	}
}
