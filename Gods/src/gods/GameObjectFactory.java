package gods;

public class GameObjectFactory {

	public static GameObject makeGameObject(GameType type, PlayerColor color) throws Exception {
		GameObject object;
		switch (type) {
		case SPEAR:
		case SWORD:
		case VILLAGER:
			object = new Unit(type, color);
			break;
		case TOWN_HALL:
		case BARRACKS:
			object = new Building(type, color);
			break;
		default:
			throw new MethodNotImplementedException("GameObjectFactory missing type: " + type.toString());
		}
		return object;
	}
}
