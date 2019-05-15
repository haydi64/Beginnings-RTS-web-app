package gods.Board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import gods.Entities.GameType;

public class RenderObject
{
	public static int tileSize = 48;

	public static void renderSquare(Board board, int row, int column, Graphics g,
			Terrain terrain)
	{
		int pixelX = row * (tileSize + 2);
		int pixelY = column * (tileSize + 2);
		g.setColor(terrain.getColor());
		g.fillRect(pixelX, pixelY, tileSize, tileSize);

		if (board.getSelectedSquare().equals(new Square(row, column))) {
			g.setColor(Color.white);
			g.drawRect(pixelX, pixelY, tileSize, tileSize);
		}
	}
	
	public static void renderUnit(int row, int column, GameType type, Graphics g)
	{
		int pixelX = row * (tileSize + 2);
		int pixelY = column * (tileSize + 2);
		BufferedImageLoader image = new BufferedImageLoader();
		BufferedImage icon = image.loadImage(getIconPath(type));
		g.drawImage(icon, pixelX+9, pixelY+9, null);
	}
	
	private static String getIconPath(GameType type)
	{
		return "/resources/icons/" + type.toString().toLowerCase() + ".png";
	}

}
