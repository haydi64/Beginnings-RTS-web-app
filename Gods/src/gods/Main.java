package gods;

import gods.View.StartMenu;
import gods.View.Window;

public class Main
{

	/**
	 * Main method that is used to start the game
	 * @param args: no arguments
	 */
	public static void main(String args[])
	{
//		StartMenu menu = new StartMenu();
//		new Window(GameLoop.WIDTH, GameLoop.HEIGHT, "Game", menu);
//		menu.start();
		
		GameLoop gameLoop = new GameLoop();
		new Window(GameLoop.WIDTH, GameLoop.HEIGHT, "Game", gameLoop);
		gameLoop.start();
	}

}
