package it.uniroma1.textadv.obj;

import it.uniroma1.textadv.Item;
import it.uniroma1.textadv.interfaces.Bloccabile;

public class Secchio extends Item{
	private boolean contieneAcqua;
	
	public Secchio(String name) {
		super(name,true,false);
		contieneAcqua = false;
	}
	
	@Override
	public void usa(Bloccabile b) {
		if(!((b instanceof Pozzo)||(b instanceof Camino)))
			super.usa(b);
		if(b instanceof Pozzo)
			usa((Pozzo) b);
		if(b instanceof Camino) 
			usa((Camino) b);
		
		
		
	}
	
	public void usa(Pozzo pozzo) {
		if(pozzo.isContieneAcqua()&&!contieneAcqua)
		{
			this.contieneAcqua = true;
			pozzo.svuotaPozzo();
			System.out.println("Hai raccolto l'acqua nel pozzo");
		}
		else {
			if(contieneAcqua)
				System.out.println("il secchio è già pieno");
			else
				System.out.println("il pozzo è vuoto");
		}
		
		
	}
	
	public void usa(Camino camino) {
		if(contieneAcqua) {
			this.contieneAcqua = false;
			camino.setBloccato(false);
		}
		else
			System.out.println("il secchio è vuoto");
	}
	
	
	
	

}
