package gods;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import gods.View.Direction;
import gods.View.StartMenu;

public class MenuKeyInput implements KeyListener
{
	
	private GameLoop game;
	
	public MenuKeyInput(GameLoop game)
	{
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
			game.cycleButtons(Direction.UP);
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
			game.cycleButtons(Direction.DOWN);
		if(isSelectButton(key))
			game.selectButton();
		
		if(key == KeyEvent.VK_ESCAPE)
			System.exit(1);
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}
	
	private boolean isSelectButton(int key)
	{
		return (key == KeyEvent.VK_Z || key == KeyEvent.VK_SPACE);
	}

}
