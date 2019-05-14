package gods;

import java.util.List;

public class Building extends GameObject
{
	private List<GameType> listOfUnits;
	private int resourceBonus;

	public Building(GameType type, PlayerColor color)
	{
		super(type, color);
		listOfUnits = Rules.getTrainableUnits(type);
		resourceBonus = Rules.getResourceBonus(type);
	}

	public Unit train(GameType gameType)
	{
		Unit unit;
		if(listOfUnits.contains(gameType))
			unit = new Unit(gameType, color);
		else unit = null;
		return unit;
	}
	
	public int getResourceBonus()
	{
		return this.resourceBonus;
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
