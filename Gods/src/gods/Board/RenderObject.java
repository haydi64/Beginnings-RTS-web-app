package gods.Board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import gods.Entities.Actions;
import gods.Entities.GameObject;
import gods.Entities.GameType;

public class RenderObject
{
	public static int tileSize = 48;
	public static int tileStart = 50;
	private static Font sanSerif = new Font("SanSerif", Font.PLAIN, 18);

	public static void renderSquare(Board board, int row, int column, Graphics g,
			Terrain terrain)
	{
		int pixelX = row * tileStart;
		int pixelY = column * tileStart;
		g.setColor(terrain.getColor());
		g.fillRect(pixelX, pixelY, tileSize, tileSize);

		if (board.getSelectedSquare().equals(new Square(row, column))) {
			g.setColor(Color.white);
			g.drawRect(pixelX, pixelY, tileSize, tileSize);
		}
	}
	
	public static void renderUnit(int row, int column, GameObject obj, Graphics g)
	{
		int pixelX = row * tileStart;
		int pixelY = column * tileStart;
		BufferedImageLoader image = new BufferedImageLoader();
		BufferedImage icon = image.loadImage(getIconPath(obj.getType()));
		//almost working, need to find the bounds
		int color = obj.getColor().getRGB();
		for(int i = 0; i < icon.getWidth(); i++)
			for(int j = 0; j < icon.getHeight(); j++)
				if(icon.getRGB(i, j) == Color.black.getRGB())
					icon.setRGB(i, j, color);
		g.drawImage(icon, pixelX+9, pixelY+9, null);
	}
	
	private static String getIconPath(GameType type)
	{
//		if(type == GameType.SWORD)
//			return "/resources/icons/red" + type.toString().toLowerCase() + ".png";
//		else
		return "/resources/icons/" + type.toString().toLowerCase() + ".png";
	}

}
