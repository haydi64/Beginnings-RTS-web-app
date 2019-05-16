package gods;

import gods.View.Window;

public class Main
{

	public static void main(String args[])
	{
		GameLoop gameLoop = new GameLoop();
		new Window(GameLoop.WIDTH, GameLoop.HEIGHT, "Game", gameLoop);
		gameLoop.start();
	}

}
