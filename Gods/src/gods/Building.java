package gods;

public class Building extends GameObject{

	public Building(GameType type, PlayerColor color) {
		super(type, color);
		this.setActions(Actions.Train);
	}
	
	public Unit train(GameType gameType) {
		Unit unit;
		switch(type)
		{
		case TOWN_HALL:
			unit = new Unit(GameType.VILLAGER, color);
			break;
		case BARRACKS:
			unit = new Unit(gameType, color);
			break;
		default:
			unit = null;
			break;
		}
		return unit;
	}
	
	@Override
	public double calcDefenseStrength() {
		return defenseRating;
	}

	@Override
	public double calcAttackStrength() {
		return 0;
	}
	
	@Override
	protected boolean canCounterAttack() {
		return false;
	}

	@Override
	public AttackResult attack(Unit defender) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
