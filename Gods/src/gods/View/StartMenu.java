package gods.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import gods.GameLoop;
import gods.Scene;

public class StartMenu
{

	private Font sanSerif = new Font("SanSerif", Font.PLAIN, 32);
	private final int width = GameLoop.WIDTH;
	private final int height = GameLoop.HEIGHT;
	
	private List<String> menuButtons;
	private int marginY, marginX;
	private int currentItem;
	
	public StartMenu() {
//		this.addKeyListener(new MenuKeyInput());
		menuButtons = new ArrayList<String>();
		menuButtons.add("Start New Game");
		menuButtons.add("Load Save Game");
		menuButtons.add("Exit");
		marginY = 100;
		marginX = 200;
		currentItem = 0;
	}

	public void render(Graphics g)
	{
		int menuWidth = this.width - (2 * marginX);
		int buttonHeight = (this.height - (2 * marginY)) / menuButtons.size();
		int pixelY = marginY;
		int textStart = marginY + buttonHeight / 2;
		int textOffset = marginX + (width / 3);
		g.setFont(sanSerif);
		for(String item: menuButtons)
		{
			if(item == menuButtons.get(currentItem))
				g.setColor(Color.green);
			else
				g.setColor(Color.gray);
			g.fillRect(marginX, pixelY, menuWidth, buttonHeight);
			g.setColor(Color.white);
			g.drawRect(marginX, pixelY, menuWidth, buttonHeight);
			g.setColor(Color.black);
			g.drawString(item, textOffset, textStart);
			
			textStart += buttonHeight;
			pixelY += buttonHeight;
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
		currentItem += i;
		if(currentItem >= menuButtons.size())
			currentItem = 0;
		if(currentItem < 0)
			currentItem = menuButtons.size() - 1;
	}

	public Scene selectButton()
	{
		Scene scene = Scene.START;
		String item = menuButtons.get(currentItem);
		if(item.toLowerCase().contains("new"))
			scene = Scene.GAME;
		else if(item.toLowerCase().contains("load"))
			scene = Scene.LOAD;
		else if(item.toLowerCase().contains("exit"))
			System.exit(1);
		return scene;
	}
}
