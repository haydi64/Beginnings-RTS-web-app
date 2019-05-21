package gods.Entities;

public enum Actions
{
	Move, Attack, Build, Train, Cancel;

	public static Actions stringToActions(String string)
	{
		Actions a = null;
		for(Actions action: Actions.values())
			if(action.toString() == string)
				a = action;
		return a;
	}
}
