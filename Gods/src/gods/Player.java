package gods;

import java.util.ArrayList;
import java.util.List;

public class Player
{

	private final PlayerColor color;
	private List<GameObject> objects;

	public Player(PlayerColor color)
	{
		this.color = color;
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
	
	public boolean hasObject(GameObject object)
	{
		return this.objects.contains(object);
	}

}
