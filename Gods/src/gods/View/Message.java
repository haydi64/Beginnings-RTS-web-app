package gods.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Message
{
	int startX, startY, width, height;
	Color background;
	private Font sanSerif; // = new Font("SanSerif", Font.PLAIN, 18);
	String text;
	
	public Message(int x, int y, int w, int h, int fontSize, Color color, String text)
	{
		startX = x;
		startY = y;
		width = w;
		height = h;
		background = color;
		sanSerif = new Font("SanSerif", Font.PLAIN, fontSize);
		this.text = text;
	}
	
	public void render(Graphics g)
	{
		int textX = startX + (width / 10);
		int textY = startY + (height / 2);
		g.setColor(background);
		g.fillRect(startX, startY, width, height);
		
		g.setColor(Color.black);
		g.setFont(sanSerif);
		g.drawString(text, textX, textY);
	}
}
