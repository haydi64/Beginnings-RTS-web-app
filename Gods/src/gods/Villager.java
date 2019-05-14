package gods;

import java.util.ArrayList;

public class Villager extends Unit {

	public Villager(PlayerColor color) {
		super(GameType.VILLAGER, color);
	}

	public Villager(GameType type, PlayerColor color) {
		super(GameType.VILLAGER, color);
		this.addActions(Actions.Build);
	}
	
	@Override
	public Building build(GameType gameType)
	{
		if(!gameType.isUnit())
			return new Building(gameType, color);
		return null;
	}
}