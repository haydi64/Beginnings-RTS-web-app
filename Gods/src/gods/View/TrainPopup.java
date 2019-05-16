package gods.View;

import java.util.List;
import gods.Board.Square;
import gods.Entities.Building;
import gods.Entities.GameType;
import gods.Game.Rules;

public class TrainPopup extends MyPopupMenu
{
	private Building building;

	public TrainPopup(Square square, Building building)
	{
		super(square);
		this.building = building;
		initializeItems();
	}

	@Override
	protected void initializeItems()
	{
		List<GameType> trainable = Rules.getTrainableUnits(building.getType());
		for(GameType type: trainable)
			this.items.add(type.toString());
	}

}
