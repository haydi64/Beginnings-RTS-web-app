package gods.Board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import gods.Entities.Actions;
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
	
	public static void renderUnit(int row, int column, GameType type, Graphics g)
	{
		int pixelX = row * tileStart;
		int pixelY = column * tileStart;
		BufferedImageLoader image = new BufferedImageLoader();
		BufferedImage icon = image.loadImage(getIconPath(type));
		g.drawImage(icon, pixelX+9, pixelY+9, null);
	}
	
//	public static void renderPopup(Square square, List<Actions> actions, Graphics g, Color color)
//	{
//		if(actions.size() <= 0)
//			return;
//		int pixelX = (square.getRow() + 1) * tileStart;
//		int pixelY = square.getColumn() * tileStart;
//		int borderStart = pixelY;
//		int boxHeight = actions.size() + 1;
//		int height = tileSize * boxHeight + boxHeight;
//		int width = 100;
//		int textOffsetX = pixelX + 10;
//		int textOffsetY = pixelY + tileSize / 2;
//		g.setColor(color);
//		g.fillRect(pixelX, pixelY, width, height);
//		g.setFont(sanSerif);
//		for(int i = 0; i < actions.size(); i++)
//		{
//			g.setColor(Color.white);
//			g.drawRect(pixelX, borderStart, width, tileSize);
//			g.setColor(Color.black);
//			g.drawString(actions.get(i).toString(), textOffsetX, textOffsetY);
//			textOffsetY += tileSize;
//			borderStart += tileStart;
//		}
//		g.drawString("Cancel", textOffsetX, textOffsetY);
//		g.setColor(Color.white);
//		g.drawRect(pixelX, borderStart, width, tileSize);
//	}
	
	private static String getIconPath(GameType type)
	{
		return "/resources/icons/" + type.toString().toLowerCase() + ".png";
	}

}
