package gods.Entities;

import java.awt.Color;
import java.io.Serializable;
import gods.Game.PlayerColor;
import gods.Game.Rules;

public class Unit extends GameObject implements Serializable {

	protected int moveLimit, range;
	protected double hunger;
	protected boolean hasAttacked, hasMoved;

	public Unit(GameType type, PlayerColor color) {
		super(type, color);
		this.moveLimit = 7;
		this.range = 1;
		this.hunger = 1.0;
		this.hasAttacked = false;
		this.hasMoved = false;
	}

	@Override
	public AttackResult attack(Unit defender) {
		AttackResult result = AttackResult.BothAlive;
		double attackBonus = 1;// unitClass.getBonus(defender.unitClass) + 1;
		double defenseBonus = 1;// defender.currentTile.getDefenseBonus() + 1;
		double counterAtkBonus = 1;// defender.unitClass.getBonus(unitClass) + 1;
		// counter defense bounus?

		double attackDMG = Rules.attackConst * (attackBonus * calcAttackStrength())
				/ (defender.calcDefenseStrength() * defenseBonus);
		double defenseDMG = counterAtkBonus * Rules.defenseConst * defender.calcAttackStrength() / calcDefenseStrength();
		defender.takeDamage(Math.toIntExact(Math.round(attackDMG)));
		defender.printHealth();
		if (defender.canCounterAttack()) // If defender can counter attack
		{
			if (defender.isDead()) {
				result = AttackResult.DefenderDead;
				while (defenseDMG > health)
					defenseDMG = defenseDMG / 2;
			}
			this.takeDamage(Math.toIntExact(Math.round(defenseDMG)));
			if(this.isDead())
				result = AttackResult.AttackerDead;
		}
		this.printHealth();
		
		return result;
	}

	public int getMoveLimit() {
		return this.moveLimit;
	}

	public int getRange() {
		return this.range;
	}
	
	public boolean hasAttacked()
	{
		return this.hasAttacked;
	}
	
	public void setAttacked(boolean bool)
	{
		this.hasAttacked = bool;
		if(hasAttacked)
			hasMoved = true;
//		if(hasAttacked)
//			actions.remove(Actions.Attack);
//		else if(!actions.contains(Actions.Attack))
//			actions.add(Actions.Attack);
	}
	
	public boolean hasMoved()
	{
		return this.hasMoved;
	}
	
	public void setMoved(boolean bool)
	{
		this.hasMoved = bool;
//		if(hasMoved)
//			actions.remove(Actions.Move);
//		else if(!actions.contains(Actions.Move))
//			actions.add(Actions.Move);
	}

	@Override
	public double calcDefenseStrength() {
		// add bonuses and hunger
		return defenseRating * healthyFactor();
	}

	@Override
	public double calcAttackStrength() {
		// add bonuses and hunger
		return attackRating * healthyFactor();
	}
	
	@Override
	protected boolean canCounterAttack() {
		return true;
	}

	@Override
	public Unit train(GameType type) {
		return null;
	}
	
	@Override
	public Building build(GameType gType)
	{
		Building building = null;
		if(this.type == GameType.VILLAGER && !gType.isUnit())
			building = new Building(gType, color);
		return building;
	}
}