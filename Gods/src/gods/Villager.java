package gods;

import java.util.ArrayList;

public class Villager extends Unit {

	public Villager(PlayerColor color) {
		super(GameType.VILLAGER, color);
		this.actions = new ArrayList<Actions>();
		this.setActions(Actions.Build);
	}

	public Villager(GameType type, PlayerColor color) {
		super(GameType.VILLAGER, color);
		this.setActions(Actions.Build);
	}
	
	@Override
	public Building build(GameType gameType)
	{
		if(!gameType.isUnit())
			return new Building(gameType, color);
		return null;
	}
}