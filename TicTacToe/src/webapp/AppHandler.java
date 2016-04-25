package webapp;

import java.io.IOException;
import java.sql.SQLException;
import business.Spielzug;

public class AppHandler {
	DBFiller db = new DBFiller();
	Spielzug sz = new Spielzug();
	boolean activePlayer = true;
	int anzFeld = 9;
	int pC = 0;
	String[] fields = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	int[][] combo = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 }, { 1, 5, 9 },
			{ 3, 5, 7 } };
	int playerInput = 0;
	String xoro;

	public class GameLogic {

		boolean activePlayer = true;
		int anzFeld = 9;
		int pC = 0;
		String[] fields = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		int[][] combo = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 }, { 1, 5, 9 },
				{ 3, 5, 7 } };
		int playerInput = 0;
		String xoro;

		public void game() throws IOException, SQLException {
			while (anzFeld > 0) {
				pC = SpielZug();
				fields[pC] = makeTheMove();
				if (winCheck() && activePlayer) {
					System.out.println("Player 2 Wins!");
					break;
				} else if (winCheck() && !activePlayer) {
					System.out.println("Player 1 Wins!");
					break;
				}
				if (anzFeld == 0) {
					System.out.println("Game over. keine freie Felder mehr");
				}
			}
		}

		public String makeTheMove() {
			if (activePlayer) {
				xoro = "x";
			} else {
				xoro = "o";
			}
			changePlayer();
			return xoro;
		}

		void changePlayer() {
			activePlayer = !activePlayer;
			anzFeld--;
		}

		public boolean winCheck() {
			String aP;
			int gewinn = 0;
			for (int i = 0; i < combo.length && gewinn < 3; i++) {
				gewinn = 0;
				for (int j = 0; j < combo[i].length; j++) {
					if (fields[combo[i][j] - 1] == xoro) {
						gewinn++;
						if (gewinn == 3) {
							return true;
						}
					}
				}
			}
			return false;
		}

	}

	public Boolean CreateGame() throws InstantiationException, IllegalAccessException, SQLException {
		db.fillAll();
		if (db.getSpieler() == 2) {
			return true;
		}
		return false;
	}

	public int SpielZug() throws SQLException {
		Integer zug = sz.getZug();
		System.out.println("zug is " + zug + " Apphandler");
		return zug;
	}

}
