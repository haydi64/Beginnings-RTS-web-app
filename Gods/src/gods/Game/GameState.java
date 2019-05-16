package gods.Game;

import java.util.ArrayList;
import java.util.List;
import gods.Entities.GameObject;

public class GameState
{
	private List<Player> players;
	private int turnNumber;
	private int currentPlayer;

	public GameState(PlayerColor... colors)
	{
		players = new ArrayList<Player>();
		for (PlayerColor color : colors) {
			players.add(new Player(color));
		}

		currentPlayer = 0;
		turnNumber = 1;
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
		PlayerColor color = gameObject.getColor();
		Player player = getPlayer(color);
		if (player != null)
			player.addObject(gameObject);
		else
			System.out.println("Player does not exist in GameState.addGameObject");
	}

	public void removeGameObject(GameObject gameObject)
	{
		PlayerColor color = gameObject.getColor();
		Player player = getPlayer(color);
		if (player != null)
			player.removeObject(gameObject);
		else
			System.out.println("Player does not exist in GameState.removeGameObject");
	}

	private Player getPlayer(PlayerColor color)
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
				System.out.println("Player " + player.getColor().toString() + " loses");
			}
		}
		return playerLost;
	}

	public void playerLoses(PlayerColor color)
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
}
