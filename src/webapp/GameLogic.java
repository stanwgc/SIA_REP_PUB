package webapp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class GameLogic {


		boolean activePlayer = true;
		int anzFeld = 9;
		int pC = 0;
		String[] fields = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		int[][] combo = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 }, { 1, 5, 9 }, { 3, 5, 7 } };
		int playerInput = 0;
		String xoro;
	


		public void game() throws IOException {
			while (anzFeld > 0) {
				if (activePlayer) {
					pC = pcMakeTheMove();
				} else {
					while (true) {
						System.out.println("Wählen Sie ein Feld aus:");
						int a = getInput();
						if (a > 9 || a < 1) {
							System.out.println("Wrong Imput! Enter a number between 1 and 9!");
						} else {
							pC = a - 1;
							break;
						}
					}
				}
				fields[pC] = makeTheMove();
				print();
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


		public String makeTheMove(int typedNumber) {
			if (!typedNumber.equals(fieldName) || fieldName == "x" || fieldName == "o") {
				return fieldName;
			} else if (typedNumber != one && typedNumber != two && activePlayer) {
				typedNumber = one;
			} else {
				typedNumber = two;
			}

			changePlayer();
			return typedNumber;
		}

		void changePlayer() {
			activePlayer = !activePlayer;
			anzFeld--;
		}

		public boolean winCheck() {
			
			if(activePlayer){
				xoro = "x";
			}else {
				xoro = "o";
			}
			
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
