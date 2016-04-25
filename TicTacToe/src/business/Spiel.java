package business;

public class Spiel {
	private String Spieler;
	private String Spieler2;
	private String Datum;
	private int SPIEL_ID;
	public String getSpieler() {
		return Spieler;
	}
	public void setSpieler(String spieler) {
		Spieler = spieler;
	}
	public String getSpieler2() {
		return Spieler2;
	}
	public void setSpieler2(String spieler2) {
		Spieler2 = spieler2;
	}
	public String getDatum() {
		return Datum;
	}
	public void setDatum(String datum) {
		Datum = datum;
	}
	public int getSPIEL_ID() {
		return SPIEL_ID;
	}
	public void setSPIEL_ID(int sPIEL_ID) {
		SPIEL_ID = sPIEL_ID;
	}
}
