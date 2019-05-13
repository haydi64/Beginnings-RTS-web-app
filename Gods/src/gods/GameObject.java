package gods;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject
{
	static double attackConst = 30;
	static double defenseConst = 24;

	protected final GameType type;
	protected final PlayerColor color;
	protected List<Actions> actions;
	protected int defenseRating, attackRating;
	protected int healthyUnits;
	protected int totalUnits;

	public GameObject(GameType type, PlayerColor color)
	{
		this.type = type;
		this.color = color;
		this.actions = new ArrayList<Actions>();
		this.healthyUnits = 100;
		this.totalUnits = 100;
		setRatings();
	}

	protected double healthyFactor()
	{
		double result = totalUnits - healthyUnits;
		result = result / 8;
		result = result + healthyUnits;
		result = result / totalUnits;
		return result;
	}

	public abstract double calcDefenseStrength();

	public abstract double calcAttackStrength();

	protected abstract boolean canCounterAttack();

	public abstract AttackResult attack(Unit defender);

	public abstract Unit train(GameType type);

	public Building build(GameType gameType)
	{
		return null;
	}

	public void takeDamage(int lostTroops)
	{
		System.out.println(
				color.toString() + " " + type.toString() + " lost: " + lostTroops);
		lostTroops = Math.max(0, lostTroops); // in case damage was negative
		this.healthyUnits -= lostTroops;
		// This is done in Game.attackUnit
		// if (this.healthyUnits <= 0)
		// deleteUnit();
	}

	private void setRatings()
	{
		switch (type) {
			case SPEAR:
				this.attackRating = 100;
				this.defenseRating = 110;
				break;
			case SWORD:
				this.attackRating = 120;
				this.defenseRating = 90;
				break;
			case VILLAGER:
				this.attackRating = 50;
				this.defenseRating = 75;
			case TOWN_HALL:
				this.attackRating = 0;
				this.defenseRating = 175;
			case BARRACKS:
				this.attackRating = 0;
				this.defenseRating = 150;
			default:
				this.attackRating = 100;
				this.defenseRating = 100;
				break;
		}
	}

	public boolean isDead()
	{
		return healthyUnits <= 0;
	}

	protected void setActions(Actions... actionArray)
	{
		for (Actions action : actionArray)
			this.actions.add(action);
	}

	public List<Actions> getActions()
	{
		return this.actions;
	}

	public GameType getType()
	{
		return this.type;
	}

	public PlayerColor getColor()
	{
		return this.color;
	}

	public void print()
	{
		System.out.print(color.toString().substring(0, 1)
				+ type.toString().substring(0, 4) + " ");
	}

	public void printHealth()
	{
		System.out.println(color.toString() + " " + type.toString() + ": "
				+ this.healthyUnits + "/" + this.totalUnits);
	}

	// Bad implementation
//	public boolean isUnit()
//	{
//		if (type == GameType.SPEAR || type == GameType.SWORD
//				|| type == GameType.VILLAGER)
//			return true;
//		return false;
//	}
}

enum GameType
{
	SWORD(true), SPEAR(true), VILLAGER(true), TOWN_HALL(false), BARRACKS(false);

	private final boolean unit;

	GameType(boolean unit)
	{
		this.unit = unit;
	}

	public boolean isUnit()
	{
		return unit;
	}
}

enum AttackResult
{
	AttackerDead, DefenderDead, BothAlive
}

enum Actions
{
	Move, Attack, Build, Train
}
