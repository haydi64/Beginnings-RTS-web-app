package gods;

import java.util.List;

public abstract class GameObject
{
	protected final GameType type;
	protected final PlayerColor color;
	protected List<Actions> actions;
	protected int defenseRating, attackRating;
	protected int health;
	protected int totalHealth;

	public GameObject(GameType type, PlayerColor color)
	{
		this.type = type;
		this.color = color;
		this.actions = Rules.getActions(type);
		this.health = 100;
		this.totalHealth = 100;
		this.attackRating = Rules.getRating(type, true);
		this.defenseRating = Rules.getRating(type, false);
	}

	protected double healthyFactor()
	{
		double result = totalHealth - health;
		result = result / 8;
		result = result + health;
		result = result / totalHealth;
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
		this.health -= lostTroops;
		// This is done in Game.attackUnit
		// if (this.healthyUnits <= 0)
		// deleteUnit();
	}

	public boolean isDead()
	{
		return health <= 0;
	}

	protected void addActions(Actions... actionArray)
	{
		for (Actions action : actionArray)
			this.actions.add(action);
	}
	
	protected void setActions(List<Actions> actionsList)
	{
		this.actions = actionsList;
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
				+ this.health + "/" + this.totalHealth);
	}
}

enum GameType
{
	SWORD(true), SPEAR(true), VILLAGER(true), TOWN_HALL(false), BARRACKS(false), MINE(false), FARM(false);

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
