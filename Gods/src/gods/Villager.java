package gods;

public class Villager extends Unit {

	public Villager(GameType type, PlayerColor color) {
		super(type, color);
		this.setActions(Actions.Build);
	}
	
	@Override
	public Building build(GameType gameType)
	{
		return new Building(gameType, color);
	}
}