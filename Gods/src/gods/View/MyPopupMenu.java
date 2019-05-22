package gods.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import gods.Board.RenderObject;
import gods.Board.Square;

public abstract class MyPopupMenu implements Serializable
{
	protected Square square;
	protected List<String> items;
	protected int pixelX, pixelY;
	protected int width;
	protected int textOffsetX, textOffsetY;
	private Font sanSerif = new Font("SanSerif", Font.PLAIN, 18);
	private int currentAction;

	public MyPopupMenu(Square square)
	{
		this.square = square;
		this.items = new ArrayList<String>();

		pixelX = (square.getRow() + 1) * RenderObject.tileStart;
		pixelY = square.getColumn() * RenderObject.tileStart;
		width = 100;
		textOffsetX = pixelX + 10;
		textOffsetY = pixelY + RenderObject.tileSize / 2;
		currentAction = 0;
	}
	
	protected abstract void initializeItems();

	public void render(Graphics g)
	{
		int textStart = textOffsetY;
		int borderStart = pixelY;
		g.setFont(sanSerif);
		for(String item: items)
		{
			if(item == items.get(currentAction))
				g.setColor(Color.green);
			else
				g.setColor(Color.gray);
			g.fillRect(pixelX, borderStart, width, RenderObject.tileStart);
			g.setColor(Color.white);
			g.drawRect(pixelX, borderStart, width, RenderObject.tileSize);
			g.setColor(Color.black);
			g.drawString(item, textOffsetX, textStart);
			
			textStart += RenderObject.tileSize;
			borderStart += RenderObject.tileStart;
		}
	}

	public void cycleActions(Direction dir)
	{
		if(dir == Direction.UP)
			increaseIndex(-1);
		else if (dir == Direction.DOWN)
			increaseIndex(1);
	}

	private void increaseIndex(int i)
	{
		currentAction += i;
		if(currentAction >= items.size())
			currentAction = 0;
		if(currentAction < 0)
			currentAction = items.size() - 1;
	}

	public String getCurrentItem()
	{
		return items.get(currentAction);
	}
}
