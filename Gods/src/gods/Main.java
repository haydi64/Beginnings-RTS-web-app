package gods;

import java.io.IOException;
import java.util.Scanner;
import gods.Board.Board;
import gods.Entities.GameType;
import gods.Entities.Unit;
import gods.Game.Game;
import gods.Game.PlayerColor;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Start Game:");
		Scanner command = new Scanner(System.in);
		boolean running = true;
		Board board = new Board(10, 10);
		board.setUnit(0, 0, new Unit(GameType.SPEAR, PlayerColor.RED));
		board.setUnit(0, 4, new Unit(GameType.SPEAR, PlayerColor.RED));
		board.setUnit(1, 1, new Unit(GameType.SWORD, PlayerColor.BLUE));
		Game game = new Game(board);
		
		game.printBoard();

		while (running) {
			String[] line = command.nextLine().split(" ");
			switch (line[0]) {
			case "exit":
				running = false;
				break;
			case "move":
				game.moveUnit(Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3]),
						Integer.parseInt(line[4]));
				break;
			case "attack":
				game.attackUnit(Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3]),
						Integer.parseInt(line[4]));
				break;
			case "end":
				game.endTurn();
				System.out.println("Ended Turn");
				break;
			default:
				System.out.println("Not reccognized");
				break;
			}
			game.printBoard();
		}
		command.close();
	}

}
