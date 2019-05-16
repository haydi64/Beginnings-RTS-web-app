package gods.Entities;

public enum GameType
{
	SWORD(true), SPEAR(true), VILLAGER(true), TOWN_HALL(false), 
	BARRACKS(false), MINE(false), FARM(false);

	private final boolean unit;

	GameType(boolean unit)
	{
		this.unit = unit;
	}

	public boolean isUnit()
	{
		return unit;
	}

	public static GameType stringToType(String string)
	{
		GameType gType = null;
		for(GameType type: GameType.values())
			if(type.toString() == string)
				gType = type;
		return gType;
	}
}
