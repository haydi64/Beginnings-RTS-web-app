package gods;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import gods.Board.BufferedImageLoader;
import gods.Board.RenderObject;
import gods.Entities.GameType;
import gods.View.Window;

public class Main
{

	/**
	 * Main method that is used to start the game
	 * @param args: no arguments
	 */
	public static void main(String args[])
	{


		
		GameLoop gameLoop = new GameLoop();
		new Window(GameLoop.WIDTH, GameLoop.HEIGHT, "Game", gameLoop);
		gameLoop.start();
	}

}
