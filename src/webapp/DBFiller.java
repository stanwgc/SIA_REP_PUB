package webapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;

public class DBFiller {
	private Connection c = null;
	private Statement stmt = null;
	private String sql;
	private String SpielID;
	private String SpielzugID;
	int zug = 0;
	public int playerOn = 0;
	private int gameOver = 9;
	public String activeGame;
	
	public String fillAll() throws InstantiationException, IllegalAccessException {
		String ret = null;
		try {
			ret = UUID.randomUUID().toString();
			setSpieler2();
			// getZuege();
			// insertSpielzug();
			// getSpielzuege();
			// getSpieler();
			if (gameOver == 0) {
				stmt.close();
				c.close();
			}

		} catch (SQLException e) {
			System.out.println("SQLERROR");
			e.printStackTrace();
		}
		return ret;
	}

	public void conection(int con)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		if (con == 1) {
			connectionBuilder();
			stmt = c.createStatement();
			c.setAutoCommit(true);
		} else {
			stmt.close();
		}
	}

	public void connectionBuilder()
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("JDBC:sqlite:TicTacToeDB.db");
		System.out.println("New connection");
		try {
			stmt = c.createStatement();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("error" + e);
			e.printStackTrace();
		}
	}

	public void createGame() throws SQLException {
		SpielID = UUID.randomUUID().toString();
		sql = "INSERT INTO Spiel (SPIEL_ID, Spieler, Spieler2, Datum) VALUES ('" + SpielID + "', '" + 1 + "','" + 0
				+ "','" + createDate() + "' )";
		stmt.executeUpdate(sql);
	}

	public void insertSpielzug(int spielzug)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		activeGame = activeGame();
		SpielzugID = UUID.randomUUID().toString();
		sql = "INSERT INTO Spielzug (SPIEL_ID, Spielzug_ID ,Player, Zug) VALUES ('" + activeGame + "','" + SpielzugID
				+ "','" + 1 + "','" + spielzug + "')";
		System.out.println(stmt + " Statement");
		stmt.executeUpdate(sql);
	}

	public int activePlayer() {
		return 1;
	}

	public void setSpieler2() throws SQLException {
		if (GetFreeSlot()) {
			System.out.println("Player 2 Enter");
			stmt.execute("UPDATE Spiel SET Spieler2 ='1' WHERE Spiel_ID = '" + activeGame + "'");
			playerOn = 2;
		}
	}

	public int getSpieler() {
		return playerOn;
	}

	public String activeGame() throws SQLException {
		ResultSet res = stmt.executeQuery("SELECT * FROM Spiel");
		activeGame = "not found";
		int Slot2 = 0;
		while (res.next()) {
			Slot2 = res.getInt("Spieler2");
			if (Slot2 == 0) {
				activeGame = res.getString("SPIEL_ID");
				System.out.println("Active Game found!");
				return activeGame;
			}
		}
		res = stmt.executeQuery("SELECT * FROM Spiel ORDER BY SPIEL_ID DESC LIMIT 1");
		activeGame = res.getString("SPIEL_ID");
		res.close();
		return activeGame;
	}

	public Boolean GetFreeSlot() throws SQLException {
		ResultSet res = stmt.executeQuery("SELECT * FROM Spiel");
		int Slot1 = 0;
		int Slot2 = 0;
		while (res.next()) {
			Slot1 = res.getInt("Spieler");
			Slot2 = res.getInt("Spieler2");
			if (Slot1 == 1 && Slot2 == 0) {
				activeGame = res.getString("SPIEL_ID");
				System.out.println("Game found!");
				return true;
			}
		}
		res.close();
		System.out.println("No game found, making a new one");
		playerOn = 1;
		createGame();
		return false;
	}

	public String getZuege() throws SQLException {
		int[] array = new int[9];
		Arrays.fill(array, 0);
		System.out.println("Bin in getZuege");
		ResultSet res2, res;
		res2 = stmt.executeQuery("SELECT * FROM Spiel ORDER BY SPIEL_ID DESC LIMIT 1");
		activeGame = res2.getString("SPIEL_ID");
		res = stmt.executeQuery("SELECT * FROM Spielzug WHERE Spiel_ID = '" + activeGame + "'");
		System.out.println("Bin in getZuege sql ausgeführt");
		while (res.next()) {	
			array[res.getInt("Zug")] = 1;
			System.out.println(res.getInt("Zug") + " Wow ist das ein Zug");
		}
		System.out.println(Arrays.toString(array));
		String stupid = "wow";
		return stupid;
	}

	public String createDate() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return formater.format(date);
	}

}
