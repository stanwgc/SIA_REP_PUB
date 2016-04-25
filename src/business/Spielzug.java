package business;

import java.sql.SQLException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Spielzug {
	private int Spielzug_ID;
	private int Spiel_ID;
	private int Felder;
	static int zug;


	public int getSpielzug_ID() {
		return Spielzug_ID;
	}

	public void setSpielzug_ID(int spielzug_ID) {
		Spielzug_ID = spielzug_ID;
	}

	public int getSpiel_ID() {
		return Spiel_ID;
	}

	public void setSpiel_ID(int spiel_ID) {
		Spiel_ID = spiel_ID;
	}

	public int getFelder() {
		return Felder;
	}

	public void setFelder(int felder) {
		Felder = felder;
	}

	public int getZug() {
		return zug;
	}

	public int ZugFormatter(String zug) throws JSONException, SQLException {
		JSONObject obj = new JSONObject(zug);
		int num = Integer.parseInt(obj.getString("FC"));
		System.out.println(obj.getString("FC") + " Ist dein Zug");
		return num;
	}
}
