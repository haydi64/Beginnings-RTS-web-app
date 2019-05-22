package gods.Game;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import gods.Entities.Building;
import gods.Entities.GameObject;
import gods.Entities.Unit;

public class Player implements Serializable
{

	private final PlayerColor color;
	private List<GameObject> objects;
	private int gold, food;

	public Player(PlayerColor color)
	{
		this.color = color;
		gold = 350;
		food = 350;
		objects = new ArrayList<GameObject>();
	}

	public PlayerColor getPlayerColor()
	{
		return color;
	}
	
	public Color getColor()
	{
		return color.getColor();
	}
	
	public void addObject(GameObject object)
	{
		this.objects.add(object);
	}
	
	public void removeObject(GameObject object)
	{
		this.objects.remove(object);
	}

	public void newTurn()
	{
		for (GameObject gObject : objects) {
			if (gObject.getType().isUnit()) {
				Unit unit = (Unit) gObject;
				unit.setAttacked(false);
				unit.setMoved(false);
			}
			else {
				Building building = (Building) gObject;
				this.gold += building.getGoldBonus();
				this.food += building.getFoodBonus();
			}
		}
	}
	
	public int[] getIncome()
	{
		int goldIncome = 0;
		int foodIncome = 0;
		for (GameObject gObject : objects)
		{
			if(!gObject.getType().isUnit())
			{
				Building building = (Building) gObject;
				goldIncome += building.getGoldBonus();
				foodIncome += building.getFoodBonus();
			}
		}
		int[] resources = new int[2];
		resources[0] = goldIncome;
		resources[1] = foodIncome;
		return resources;
	}

	public int objectsLeft()
	{
		return objects.size();
	}
	
	public boolean canAfford(int goldCost, int foodCost)
	{
		return gold >= goldCost && food >= foodCost;
	}
	
	public int getFood()
	{
		return this.food;
	}
	
	public void addFood(int value)
	{
		food += value;
	}
	
	public int getGold()
	{
		return this.gold;
	}
	
	public void addGold(int value)
	{
		gold += value;
	}

}
