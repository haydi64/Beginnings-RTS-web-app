package gods.View;

import java.awt.Color;
import gods.Board.Square;
import java.util.ArrayList;
import java.util.List;
import gods.Entities.Actions;
import gods.Entities.Unit;

public class UnitPopup extends MyPopupMenu
{
	private Unit unit;
	
	public UnitPopup(Square square, Unit unit)
	{
		super(square);
		this.unit = unit;
		initializeItems();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void initializeItems()
	{
		List<Actions> actions = new ArrayList<Actions>();
		actions.addAll(unit.getActions());
		actions.add(Actions.Cancel);
		if(unit.hasAttacked()) // or can attack
		{
			actions.remove(Actions.Attack);
			if(actions.contains(Actions.Build))
				actions.remove(Actions.Build);
		}
		if(unit.hasMoved())
			actions.remove(Actions.Move);
		
		for(Actions action: actions)
			this.items.add(action.toString());
	}

}
