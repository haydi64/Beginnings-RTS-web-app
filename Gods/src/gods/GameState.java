package gods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameState {
	private List<PlayerColor> players;
	private List<List<GameObject>> gameObjects;
	private int turnNumber;
	private int currentPlayer;
	
	public GameState(PlayerColor ...colors)
	{
		players = new ArrayList<PlayerColor>(Arrays.asList(colors));
		gameObjects = new ArrayList<List<GameObject>>();
		for(PlayerColor color: players)
			gameObjects.add(new ArrayList<GameObject>());
		currentPlayer = 0;
		turnNumber = 1;
	}
	
	public PlayerColor nextPlayer()
	{
		if(++currentPlayer >= players.size())
		{
			currentPlayer = 0;
			turnNumber++;
		}
		int index = players.indexOf(getCurrentPlayer());
		for(GameObject gObject: gameObjects.get(index))
		{
			if(gObject.getType().isUnit())
			{
				Unit unit = (Unit) gObject;
				unit.setAttacked(false);
				unit.setMoved(false);
				
			}
		}
		return getCurrentPlayer();
	}
	
	public void addGameObject(GameObject gameObject) {
		PlayerColor color = gameObject.getColor();
		int index = players.indexOf(color);
		gameObjects.get(index).add(gameObject);
	}
	
	public void removeGameObject(GameObject gameObject) {
		PlayerColor color = gameObject.getColor();
		int index = players.indexOf(color);
		gameObjects.get(index).remove(gameObject);
	}
	
	public boolean hasPlayerLost()
	{
		boolean playerLost = false;
		List<PlayerColor> copy = new ArrayList<PlayerColor>();
		copy.addAll(players);
		for(PlayerColor color: copy)
		{
			if(getNumberOfUnits(color) == 0)
			{
				playerLost = true;
				playerLoses(color);
				System.out.println("Player " + color.toString() + " loses");
			}
		}
		return playerLost;
	}
	
	public int getNumberOfUnits(PlayerColor color)
	{
		int index = players.indexOf(color);
		return gameObjects.get(index).size();
	}
	
	public void playerLoses(PlayerColor color)
	{
		int index = players.indexOf(color);
		players.remove(color);
		gameObjects.remove(index);
	}
	
	public int playersLeft()
	{
		return players.size();
	}
	
	public PlayerColor getCurrentPlayer()
	{
		return players.get(currentPlayer);
	}
	
	public int getTurnNumber()
	{
		return this.turnNumber;
	}
}
