package gods;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import gods.Board.RenderObject;
import gods.Board.Square;
import gods.Entities.Actions;

public class PopupMenu
{
	Square square;
	List<Actions> actions;
	Color color;
	int pixelX, pixelY;
	int width;
	int textOffsetX, textOffsetY;
	private static Font sanSerif = new Font("SanSerif", Font.PLAIN, 18);
	private int currentAction;
	
	public PopupMenu(Square square, List<Actions> actions, Color color)
	{
		this.square = square;
		this.actions = new ArrayList<Actions>();
		this.actions.addAll(actions);
		this.actions.add(Actions.Cancel);
		this.color = color;

		pixelX = (square.getRow() + 1) * RenderObject.tileStart;
		pixelY = square.getColumn() * RenderObject.tileStart;
//		boxHeight = actions.size() + 1;
//		height = RenderObject.tileSize * boxHeight + boxHeight;
		width = 100;
		textOffsetX = pixelX + 10;
		textOffsetY = pixelY + RenderObject.tileSize / 2;
		currentAction = 0;
	}
	
	public void render(Graphics g)
	{
		int textStart = textOffsetY;
		int borderStart = pixelY;
		g.setFont(sanSerif);
		for(Actions action: actions)
		{
			if(action == actions.get(currentAction))
				g.setColor(Color.green);
			else
				g.setColor(Color.gray);
			g.fillRect(pixelX, borderStart, width, RenderObject.tileStart);
			g.setColor(Color.white);
			g.drawRect(pixelX, borderStart, width, RenderObject.tileSize);
			g.setColor(Color.black);
			g.drawString(action.toString(), textOffsetX, textStart);
			
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
		if(currentAction >= actions.size())
			currentAction = 0;
		if(currentAction < 0)
			currentAction = actions.size() - 1;
	}

	public Actions getAction()
	{
		return actions.get(currentAction);
	}
}
