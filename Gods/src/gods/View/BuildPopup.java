package gods.View;

import java.util.List;
import gods.Board.Square;
import gods.Entities.GameType;

public class BuildPopup extends MyPopupMenu
{
	private List<GameType> possibleBuildings;

	public BuildPopup(Square square, List<GameType> buildings)
	{
		super(square);
		this.possibleBuildings = buildings;
		this.initializeItems();
	}

	@Override
	protected void initializeItems()
	{
		//Later might need to change this to 
		for(GameType type: possibleBuildings)
			this.items.add(type.toString());
	}

}
