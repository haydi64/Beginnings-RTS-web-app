package gods;

import java.util.ArrayList;
import java.util.List;

public class Building extends GameObject
{
	private List<GameType> listOfUnits;

	public Building(GameType type, PlayerColor color)
	{
		super(type, color);
		this.setActions(Actions.Train);
		listOfUnits = new ArrayList<GameType>();
		setStats();
	}

	private void setStats()
	{
		switch (type) {
			case TOWN_HALL:
//				this.defenseRating = 200;
				listOfUnits.add(GameType.VILLAGER);
				break;
			case BARRACKS:
//				this.defenseRating = 175;
				listOfUnits.add(GameType.SPEAR);
				listOfUnits.add(GameType.SWORD);
				break;
			default:
				break;
		}
	}

	public Unit train(GameType gameType)
	{
		Unit unit;
		if(listOfUnits.contains(gameType))
			unit = new Unit(gameType, color);
		else unit = null;
		return unit;
	}

	@Override
	public double calcDefenseStrength()
	{
		return defenseRating;
	}

	@Override
	public double calcAttackStrength()
	{
		return 0;
	}

	@Override
	protected boolean canCounterAttack()
	{
		return false;
	}

	@Override
	public AttackResult attack(Unit defender)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
