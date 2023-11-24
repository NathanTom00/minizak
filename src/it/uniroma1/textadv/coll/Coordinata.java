package it.uniroma1.textadv.coll;

public enum Coordinata{
	NORD("N"),SUD("S"),EST("E"),OVEST("O");
	
	private String name;
	Coordinata(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static Coordinata fromString(String name) {
		for(Coordinata c : Coordinata.values()) {
			if(c.name.equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	public static boolean isACoordinata(String s) {
		for(Coordinata c: Coordinata.values()) {
			if(c.name.equals(s))
			{
				return true;
			}
		}
		return false;
	}
}
