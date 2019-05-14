package gods;

import java.util.ArrayList;
import java.util.List;

public class Player
{

	private final PlayerColor color;
	private List<GameObject> objects;
	private int gold, food;

	public Player(PlayerColor color)
	{
		this.color = color;
		gold = 0;
		food = 0;
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
		//
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

}
