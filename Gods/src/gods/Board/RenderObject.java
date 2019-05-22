package gods.Board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import gods.Entities.GameObject;
import gods.Entities.GameType;
import gods.Game.Player;
import gods.View.ButtonState;

/**
 * class for rendering all objects
 *
 */
public class RenderObject
{
	public static int tileSize = 48;
	public static int tileStart = 50;
	private static Font sanSerif = new Font("SanSerif", Font.PLAIN, 18);
	private static Map<GameType, BufferedImage> icons = new HashMap<GameType, BufferedImage>();
	
	public static void initializeIcons()
	{
		BufferedImageLoader loader = new BufferedImageLoader();
		for(GameType type: GameType.values())
			icons.put(type, loader.loadImage(getIconPath(type)));
	}

	/**
	 * Render a map tile
	 * @param row: row in map
	 * @param column: column in map
	 * @param g: graphic object
	 * @param color: color of the tile
	 */
	public static void renderSquare(int row, int column, Graphics g, Color color)
	{
		int pixelX = row * tileStart;
		int pixelY = column * tileStart;
		g.setColor(color);
		g.fillRect(pixelX, pixelY, tileSize, tileSize);
	}

	/**
	 * Outline a map tile
	 * @param g: graphics object
	 * @param square: the square to be outlined
	 * @param color: color of the outline
	 */
	public static void outlineSquare(Graphics g, Square square, Color color)
	{
		int pixelX = square.getRow() * tileStart;
		int pixelY = square.getColumn() * tileStart;
		g.setColor(color);
		g.drawRect(pixelX, pixelY, tileSize, tileSize);
	}

	/**
	 * Render a game object
	 * @param row: row game object is at
	 * @param column: column game object is at
	 * @param obj: Game object to be drawn
	 * @param g: graphics object
	 */
	public static void renderUnit(int row, int column, GameObject obj, Graphics g)
	{
		int pixelX = row * tileStart;
		int pixelY = column * tileStart;
		BufferedImage icon = icons.get(obj.getType());
		Color color = obj.getPlayerColor().getColor();
		//Change the color to match the player
		for(int i = 0; i < icon.getWidth(); i++)
			for(int j = 0; j < icon.getHeight(); j++)
			{
				Color pixel = new Color(icon.getRGB(i, j), true);
				if(pixel.getAlpha() > 0)
				{
					Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), pixel.getAlpha());
					icon.setRGB(i, j, newColor.getRGB());
				}
			}
		g.drawImage(icon, pixelX+9, pixelY+9, null);
	}

	/**
	 * Gets the path of the game objects sprite icon
	 * @param type: type of game object
	 * @return a String of the path
	 */
	private static String getIconPath(GameType type)
	{
		return "/resources/icons/" + type.toString().toLowerCase() + ".png";
	}

	/**
	 * Render a color over the map/units. If the game is in button state attackUnit, the overlay will be red.
	 * If the game is in buttonState moveUnit, the overlay will be blue.
	 * @param row: row of the map
	 * @param column: column of the map
	 * @param g: graphics object
	 * @param bState: which ButtonState the game is in
	 */
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

	/**
	 * Render the player information to be displayed on the top
	 * @param g: graphics object
	 * @param player: the current player whose information is displayed
	 * @param turnNumber: the current turn number
	 */
	public static void renderPlayerInfo(Graphics g, Player player, int turnNumber)
	{
		g.setColor(Color.black);
		g.setFont(sanSerif);
		String color = player.getPlayerColor().toString(); 
		String info = "Turn: " + turnNumber + "    Player: " + color + "    Gold: "
				+ player.getGold() + "    Food: " + player.getFood();
		g.drawString(info, 25, 25);
	}

}
