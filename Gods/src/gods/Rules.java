package gods;

import java.util.ArrayList;
import java.util.List;

public class Rules
{
	public static double attackConst = 30;
	public static double defenseConst = 24;

	public static List<Actions> getActions(GameType type)
	{
		List<Actions> actions = new ArrayList<Actions>();
		switch (type) {
			case VILLAGER:
				actions.add(Actions.Build);
			case SPEAR:
			case SWORD:
				actions.add(Actions.Attack);
				actions.add(Actions.Move);
				break;
			case TOWN_HALL:
			case BARRACKS:
				actions.add(Actions.Train);
				break;
			case MINE:
			case FARM:
				break;
		}
		return actions;
	}

	public static int getRating(GameType type, boolean isAttack)
	{
		int attack;
		int defense;
		switch (type) {
			case SPEAR:
				attack = 100;
				defense = 110;
				break;
			case SWORD:
				attack = 120;
				defense = 90;
				break;
			case VILLAGER:
				attack = 50;
				defense = 75;
				break;
			case TOWN_HALL:
				attack = 0;
				defense = 175;
			case BARRACKS:
				attack = 0;
				defense = 150;
			case MINE:
				attack = 0;
				defense = 150;
				break;
			case FARM:
				attack = 0;
				defense = 150;
				break;
			default:
				attack = 0;
				defense = 0;
				break;
		}
		if (isAttack)
			return attack;
		else
			return defense;
	}

	public static List<GameType> getTrainableUnits(GameType type)
	{
		List<GameType> trainableUnits = new ArrayList<GameType>();
		switch (type) {
			case TOWN_HALL:
				trainableUnits.add(GameType.VILLAGER);
				break;
			case BARRACKS:
				trainableUnits.add(GameType.SPEAR);
				trainableUnits.add(GameType.SWORD);
				break;
			default:
				break;
		}
		return trainableUnits;
	}

	public static int getFoodBonus(GameType type)
	{
		int bonus;
		switch (type) {
			case TOWN_HALL:
			case FARM:
				bonus = 100;
				break;
			default:
				bonus = 0;
		}
		return bonus;
	}

	public static int getGoldBonus(GameType type)
	{
		int bonus;
		switch (type) {
			case TOWN_HALL:
			case MINE:
				bonus = 100;
				break;
			default:
				bonus = 0;
		}
		return bonus;
	}

	public static int[] getObjectCost(GameType type)
	{
		int gold, food;

		switch (type) {
			case SPEAR:
				gold = 100;
				food = 100;
				break;
			case SWORD:
				gold = 100;
				food = 100;
				break;
			case VILLAGER:
				gold = 25;
				food = 75;
				break;
			case MINE:
				gold = 100;
				food = 125;
				break;
			case FARM:
				gold = 125;
				food = 100;
				break;
			case TOWN_HALL:
				gold = 200;
				food = 200;
				break;
			case BARRACKS:
				gold = 175;
				food = 175;
				break;
			default:
				gold = 0;
				food = 0;
				break;
		}

		int[] values = new int[2];
		values[0] = gold;
		values[1] = food;
		return values;
	}
}
