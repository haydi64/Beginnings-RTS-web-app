package gods;

public class MoveValidator
{

	public static boolean moveIsValid(int fromRow, int fromColumn, int toRow,
			int toColumn, Board theBoard) throws InvalidMoveException
	{
		boolean isValid = true;
		
		Unit fromUnit = theBoard.getUnitAt(fromRow, fromColumn);
		Unit toUnit = theBoard.getUnitAt(toRow, toColumn);
		Square from = new Square(fromRow, fromColumn);
		Square to = new Square(toRow, toColumn);
		
		if(fromUnit == null)
//			isValid = false;
			throw new InvalidMoveException("From unit was null at position " + fromRow + ", " + fromColumn);
		else if(fromUnit.getMoveLimit() < from.getManDistance(to))
//			isValid = false;
			throw new InvalidMoveException("Tried to move a distance of " + from.getManDistance(to));
		else if(toUnit != null)
			throw new InvalidMoveException("Tried to move onto another unit");
		return isValid;
	}

}
