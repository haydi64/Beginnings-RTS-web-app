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

//		BufferedImageLoader image = new BufferedImageLoader();
//		BufferedImage icon = image.loadImage(RenderObject.getIconPath(GameType.SWORD));
//
//		for(int i = 0; i < icon.getWidth(); i++)
//		{
//			for(int j = 0; j < icon.getHeight(); j++)
//			{
//				Color c = new Color(icon.getRGB(i, j), true);
//				System.out.print("( " + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ", " + c.getAlpha() + "),  ");
////				System.out.print(icon.getRGB(i, j) + ", ");
//			}
//			System.out.print('\n');
//		}
		
		GameLoop gameLoop = new GameLoop();
		new Window(GameLoop.WIDTH, GameLoop.HEIGHT, "Game", gameLoop);
		gameLoop.start();
	}

}
