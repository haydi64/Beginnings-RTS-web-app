package gods;

import gods.Entities.AttackResult;
import gods.Entities.GameObject;
import gods.Entities.GameType;
import gods.Entities.Unit;
import gods.Game.PlayerColor;

public class ResourceBuilding extends GameObject
{

	private int resourceBonus;
	
	public ResourceBuilding(GameType type, PlayerColor color)
	{
		super(type, color);
		resourceBonus = 100;
	}
	
	public int getResourceBonus()
	{
		return resourceBonus;
	}

	@Override
	public double calcDefenseStrength()
	{
		return this.defenseRating;
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
		return null;
	}

	@Override
	public Unit train(GameType type)
	{
		return null;
	}

}
