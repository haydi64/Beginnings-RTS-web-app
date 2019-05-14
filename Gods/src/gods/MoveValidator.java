package gods;

public class MoveValidator
{

	public static boolean moveIsValid(int fromRow, int fromColumn, int toRow,
			int toColumn, Board theBoard, PlayerColor color)
			throws InvalidMoveException
	{
		boolean isValid = true;

		Unit fromUnit = theBoard.getUnitAt(fromRow, fromColumn);
		Unit toUnit = theBoard.getUnitAt(toRow, toColumn);
		Square from = new Square(fromRow, fromColumn);
		Square to = new Square(toRow, toColumn);

		if (!theBoard.squaresInBounds(from, to))
			throw new InvalidMoveException("Invalid squares");
		if (fromUnit == null)
			throw new InvalidMoveException(
					"From unit was null at position " + fromRow + ", " + fromColumn);
		if (fromUnit.getColor() != color)
			throw new InvalidMoveException("Tried to move a piece, not their own");
		if(!fromUnit.getActions().contains(Actions.Move))
			throw new InvalidMoveException("No actions");
		if (fromUnit.getMoveLimit() < from.getManDistance(to))
			throw new InvalidMoveException(
					"Tried to move a distance of " + from.getManDistance(to));
		if (toUnit != null)
			throw new InvalidMoveException("Tried to move onto another unit");
		if (fromUnit.hasMoved())
			throw new InvalidMoveException("Unit has already moved");
		return isValid;
	}

	public static void attackIsValid(int fromRow, int fromColumn, int toRow,
			int toColumn, Board theBoard, PlayerColor color)
			throws InvalidMoveException
	{

		Unit fromUnit = theBoard.getUnitAt(fromRow, fromColumn);
		Unit toUnit = theBoard.getUnitAt(toRow, toColumn);
		Square from = new Square(fromRow, fromColumn);
		Square to = new Square(toRow, toColumn);

		if (!theBoard.squaresInBounds(from, to))
			throw new InvalidMoveException("Invalid squares");
		if (fromUnit == null || toUnit == null)
			throw new InvalidMoveException("Must have a unit attack another unit");
		if (!fromUnit.getActions().contains(Actions.Attack))
			throw new InvalidMoveException("Cannot attack");
		if (fromUnit.getColor() != color)
			throw new InvalidMoveException(
					"Tried to attack with a piece, not their own");
		if (fromUnit.getColor() == toUnit.getColor())
			throw new InvalidMoveException("Cannot attack a friendly unit");
		if (fromUnit.getRange() < from.getManDistance(to))
			throw new InvalidMoveException(
					"Other unit out of range, " + from.getManDistance(to));
		if (fromUnit.hasAttacked())
			throw new InvalidMoveException("Unit has already attacked");
	}

	public static boolean buildIsValid(int row, int column, Board board)
	{
		Unit unit = board.getUnitAt(row, column);
		Building building = board.getBuildingAt(row, column);
		return unit != null && building == null
				&& unit.getActions().contains(Actions.Build)
				&& unit.hasAttacked() == false;
	}

	public static boolean trainIsValid(int row, int column, Board theBoard)
	{
		Unit unit = theBoard.getUnitAt(row, column);
		Building building = theBoard.getBuildingAt(row, column);
		return unit == null && building != null;
	}

}