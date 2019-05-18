package gods.View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import gods.Game.Game;

public class KeyInput implements KeyListener
{
	private Game game;

	public KeyInput(Game game)
	{
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		ButtonState state = game.getButtonState();
		switch (state) {
			case Normal:
				NavigateMap(key);

				if (key == KeyEvent.VK_Z)
					game.addPopup();
				if (key == KeyEvent.VK_ENTER)
					game.endTurn();
				break;
			case Popup:
				if (key == KeyEvent.VK_W)
					game.cycleActions(Direction.UP);
				if (key == KeyEvent.VK_S)
					game.cycleActions(Direction.DOWN);

				if (key == KeyEvent.VK_Z)
					game.selectAction();
				break;
			case MoveUnit:
				NavigateMap(key);

				if (key == KeyEvent.VK_Z)
					game.tryMove();
				break;
			case AttackUnit:
				if (key == KeyEvent.VK_W)
					game.changePossibleTile(Direction.UP);
				if (key == KeyEvent.VK_S)
					game.changePossibleTile(Direction.DOWN);
				if (key == KeyEvent.VK_D)
					game.changePossibleTile(Direction.UP);
				if (key == KeyEvent.VK_A)
					game.changePossibleTile(Direction.RIGHT);
				
				if(key == KeyEvent.VK_Z)
					game.tryAttack();
				break;
		}
		// if(game.hasPopup()) {
		// if(key == KeyEvent.VK_W) game.cycleActions(Direction.UP);
		// if(key == KeyEvent.VK_S) game.cycleActions(Direction.DOWN);
		//
		// if(key == KeyEvent.VK_Z) game.selectAction();
		//
		// } else {
		// if(key == KeyEvent.VK_W) game.changeSelectedTile(Direction.UP);
		// if(key == KeyEvent.VK_S) game.changeSelectedTile(Direction.DOWN);
		// if(key == KeyEvent.VK_D) game.changeSelectedTile(Direction.RIGHT);
		// if(key == KeyEvent.VK_A) game.changeSelectedTile(Direction.LEFT);
		//
		// if(key == KeyEvent.VK_Z) game.addPopup();
		// }

		if (key == KeyEvent.VK_ESCAPE)
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

	private void NavigateMap(int key)
	{
		if (key == KeyEvent.VK_W)
			game.changeSelectedTile(Direction.UP);
		if (key == KeyEvent.VK_S)
			game.changeSelectedTile(Direction.DOWN);
		if (key == KeyEvent.VK_D)
			game.changeSelectedTile(Direction.RIGHT);
		if (key == KeyEvent.VK_A)
			game.changeSelectedTile(Direction.LEFT);
	}

}
