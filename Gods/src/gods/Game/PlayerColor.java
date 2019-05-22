package gods.Game;

import java.awt.Color;

public enum PlayerColor {
	RED(Color.red), BLUE(Color.blue);

	private final Color color;
	PlayerColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
}
