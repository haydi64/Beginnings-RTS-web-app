package gods.Game;

import gods.InvalidMoveException;
import gods.Board.Board;
import gods.Board.Square;
import gods.Entities.Actions;
import gods.Entities.Building;
import gods.Entities.GameType;
import gods.Entities.Unit;

public class MoveValidator
{

	public static boolean moveIsValid(int fromRow, int fromColumn, int toRow,
			int toColumn, Board theBoard, PlayerColor color)
	{
		boolean isValid = true;

		Unit fromUnit = theBoard.getUnitAt(fromRow, fromColumn);
		Unit toUnit = theBoard.getUnitAt(toRow, toColumn);
		Square from = new Square(fromRow, fromColumn);
		Square to = new Square(toRow, toColumn);

		if (!theBoard.squaresInBounds(from, to))
			isValid = false;
		else if (fromUnit == null)
			isValid = false;
		else if (fromUnit.getColor() != color)
			isValid = false;
		else if(!fromUnit.getActions().contains(Actions.Move))
			isValid = false;
		else if (fromUnit.getMoveLimit() < from.getManDistance(to))
			isValid = false;
		else if (toUnit != null)
			isValid = false;
		else if (fromUnit.hasMoved())
			isValid = false;
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

	public static boolean buildIsValid(int row, int column, Board board, GameType type, Player player)
	{
		Unit unit = board.getUnitAt(row, column);
		Building building = board.getBuildingAt(row, column);
		int[] cost = Rules.getObjectCost(type);
		return unit != null && building == null
				&& unit.getActions().contains(Actions.Build)
				&& unit.hasAttacked() == false && player.canAfford(cost[0], cost[1]);
	}

	public static boolean trainIsValid(int row, int column, Board theBoard, GameType type, Player player)
	{
		Unit unit = theBoard.getUnitAt(row, column);
		Building building = theBoard.getBuildingAt(row, column);
		int[] cost = Rules.getObjectCost(type);
		return unit == null && building != null && player.canAfford(cost[0], cost[1]);
	}

}
