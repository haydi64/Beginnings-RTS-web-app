package gods;

public class Unit
{

	private final UnitType type;
	private UnitColor color;
	private int moveLimit;

	public Unit(UnitType type, UnitColor color)
	{
		this.type = type;
		this.color = color;
		this.moveLimit = 7;
	}
	
	public UnitType getType() { return this.type; }
	public UnitColor getColor() { return this.color; }
	
	public void print()
	{
		System.out.print(type.toString() + " ");
	}

	public int getMoveLimit()
	{
		return this.moveLimit;
	}
}

enum UnitType
{
	SWORD, SPEAR
}

enum UnitColor
{
	RED, BLUE
}