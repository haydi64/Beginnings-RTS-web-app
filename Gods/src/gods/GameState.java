package gods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameState {
	private List<PlayerColor> players;
	private List<List<Unit>> units;
	private int turnNumber;
	private int currentPlayer;
	
	public GameState(PlayerColor ...colors)
	{
		players = new ArrayList<PlayerColor>(Arrays.asList(colors));
		units = new ArrayList<List<Unit>>();
		for(PlayerColor color: players)
			units.add(new ArrayList<Unit>());
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
		for(Unit unit: units.get(index))
		{
			unit.setAttacked(false);
			unit.setMoved(false);
		}
		return getCurrentPlayer();
	}
	
	public void addUnit(Unit unit) {
		PlayerColor color = unit.getColor();
		int index = players.indexOf(color);
		units.get(index).add(unit);
	}
	
	public void removeUnit(Unit unit) {
		PlayerColor color = unit.getColor();
		int index = players.indexOf(color);
		units.get(index).remove(unit);
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
		return units.get(index).size();
	}
	
	public void playerLoses(PlayerColor color)
	{
		int index = players.indexOf(color);
		players.remove(color);
		units.remove(index);
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
