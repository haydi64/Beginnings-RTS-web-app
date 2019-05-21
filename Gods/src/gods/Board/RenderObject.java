package gods.Board;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import gods.Entities.Actions;
import gods.Entities.GameObject;
import gods.Entities.GameType;
import gods.Game.Player;
import gods.View.ButtonState;

public class RenderObject
{
	public static int tileSize = 48;
	public static int tileStart = 50;
	private static Font sanSerif = new Font("SanSerif", Font.PLAIN, 18);

	public static void renderSquare(int row, int column, Graphics g, Color color)
	{
		int pixelX = row * tileStart;
		int pixelY = column * tileStart;
		g.setColor(color);
		g.fillRect(pixelX, pixelY, tileSize, tileSize);
	}

	public static void outlineSquare(Graphics g, Square square, Color color)
	{
		int pixelX = square.getRow() * tileStart;
		int pixelY = square.getColumn() * tileStart;
		g.setColor(color);
		g.drawRect(pixelX, pixelY, tileSize, tileSize);
	}

	public static void renderUnit(int row, int column, GameObject obj, Graphics g)
	{
		int pixelX = row * tileStart;
		int pixelY = column * tileStart;
		BufferedImageLoader image = new BufferedImageLoader();
		BufferedImage icon = image.loadImage(getIconPath(obj.getType()));
		//almost working, need to find the bounds
//		int color = obj.getColor().getRGB();
		Color color = obj.getColor();
//		dye(icon, color, pixelX, pixelY);
		for(int i = 0; i < icon.getWidth(); i++)
			for(int j = 0; j < icon.getHeight(); j++)
			{
				Color pixel = new Color(icon.getRGB(i, j), true);
				if(pixel.getAlpha() > 0)
				{
					Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), pixel.getAlpha());
					icon.setRGB(i, j, newColor.getRGB());
				}
//				icon.setRGB(icon.getRGB(x, y), arg1, arg2);
//				if(icon.getRGB(i, j) == Color.black.getRGB())
//					icon.setRGB(i, j, color);
			}
		g.drawImage(icon, pixelX+9, pixelY+9, null);
	}

	public static String getIconPath(GameType type)
	{
		return "/resources/icons/" + type.toString().toLowerCase() + ".png";
	}

	public static void renderOverlay(int row, int column, Graphics g,
			ButtonState bState)
	{
		Color color;
		if (bState == ButtonState.AttackUnit)
			color = new Color(1.0f, 0.0f, 0.0f, 0.3f);
		else if (bState == ButtonState.MoveUnit)
			color = new Color(0.0f, 0.0f, 1.0f, 0.3f);
		else
			color = new Color(0, 0, 0, 0); // default nothing shows
		renderSquare(row, column, g, color);
	}

	public static void renderPlayerInfo(Graphics g, Player player, int turnNumber)
	{
		g.setColor(Color.black);
		g.setFont(sanSerif);
		String color = (player.getColor() == Color.red) ? "Red" : "Blue"; 
		// This could be better, only works for red and blue
		String info = "Turn: " + turnNumber + "    Player: " + color + "    Gold: "
				+ player.getGold() + "    Food: " + player.getFood();
		g.drawString(info, 25, 25);
	}

    private static BufferedImage dye(BufferedImage image, Color color, int pixelX, int pixelY)
    {
    	int w = image.getWidth();
    	int h = image.getHeight();
        BufferedImage dyed = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dyed.createGraphics();
        g.drawImage(image, pixelX, pixelY, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(color);
        g.fillRect(pixelX, pixelY, tileSize, tileSize);
//        g.dispose();
        return dyed;
    }

}
