package gods.Game;

import java.util.ArrayList;
import java.util.List;
import gods.Entities.Building;
import gods.Entities.GameObject;
import gods.Entities.Unit;

public class Player
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

	public PlayerColor getColor()
	{
		return this.color;
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
