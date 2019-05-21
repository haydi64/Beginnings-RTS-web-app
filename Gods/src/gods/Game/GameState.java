package gods.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import gods.Entities.GameObject;
import gods.View.ButtonState;

public class GameState
{
	private List<Player> players;
	private int turnNumber;
	private int currentPlayer;
	private ButtonState buttonState;
	private boolean gameOver;
	private Font sanSerif = new Font("SanSerif", Font.BOLD, 20);

	public GameState(Color... colors)
	{
		players = new ArrayList<Player>();
		for (Color color : colors) {
			players.add(new Player(color));
		}

		currentPlayer = 0;
		turnNumber = 1;
		buttonState = ButtonState.Normal;
		gameOver = false;
	}

	public Player nextPlayer()
	{
		if (++currentPlayer >= players.size()) {
			currentPlayer = 0;
			turnNumber++;
		}
		players.get(currentPlayer).newTurn();
		return getCurrentPlayer();
	}

	public void addGameObject(GameObject gameObject)
	{
		Color color = gameObject.getColor();
		Player player = getPlayer(color);
		if (player != null)
			player.addObject(gameObject);
		else
			System.out.println("Player does not exist in GameState.addGameObject");
	}

	public void removeGameObject(GameObject gameObject)
	{
		Color color = gameObject.getColor();
		Player player = getPlayer(color);
		if (player != null)
			player.removeObject(gameObject);
		else
			System.out
					.println("Player does not exist in GameState.removeGameObject");
	}

	private Player getPlayer(Color color)
	{
		Player player = null;
		for (Player p : players) {
			if (p.getColor() == color)
				player = p;
		}
		return player;
	}

	public boolean hasPlayerLost()
	{
		boolean playerLost = false;
		List<Player> copy = new ArrayList<Player>();
		copy.addAll(players);
		for (Player player : copy) {
			if (player.objectsLeft() == 0) {
				playerLost = true;
				playerLoses(player.getColor());
				System.out.println(
						"Player " + player.getColor().toString() + " loses");
			}
		}
		return playerLost;
	}

	public void playerLoses(Color color)
	{
		Player player = getPlayer(color);
		players.remove(player);
	}

	public int playersLeft()
	{
		return players.size();
	}

	public Player getCurrentPlayer()
	{
		return players.get(currentPlayer);
	}

	public int getTurnNumber()
	{
		return this.turnNumber;
	}

	public void setButtonState(ButtonState state)
	{
		buttonState = state;
	}

	public ButtonState getButtonState()
	{
		return this.buttonState;
	}

	public void renderPlayerInfo(Graphics g)
	{
		g.setColor(Color.black);
		g.setFont(this.sanSerif);
		Player player = getCurrentPlayer();
		String color = (player.getColor() == Color.red) ? "Red" : "Blue";
		String info = "Turn: " + turnNumber + "    Player: " + color + "    Gold: "
				+ player.getGold() + "    Food: " + player.getFood();
		g.drawString(info, 25, 25);
	}

	public void takeCost(GameObject obj)
	{
		int[] cost = Rules.getObjectCost(obj.getType());
		Player player = getCurrentPlayer();
		player.addGold(-1 * cost[0]);
		player.addFood(-1 * cost[1]);
	}

	public boolean isGameOver()
	{
		return gameOver;
	}

	public void gameOver()
	{
		gameOver = true;
	}
}
