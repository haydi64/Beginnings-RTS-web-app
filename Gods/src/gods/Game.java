package gods;

public class Game
{
	
	private Board theBoard;
	
	public Game(Board board)
	{
		theBoard = board;
	}

	public void moveUnit(int fromRow, int fromColumn, int toRow, int toColumn)
	{
		boolean validMove;
		try {
			validMove = MoveValidator.moveIsValid(fromRow, fromColumn, toRow, toColumn, theBoard);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
			validMove = false;
		}
		if(validMove)
		{
			Unit fromUnit = theBoard.getUnitAt(fromRow, fromColumn);
			theBoard.addUnit(toRow, toColumn, fromUnit);
			theBoard.addUnit(fromRow, fromColumn, null);
		}
	}
}
